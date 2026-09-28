package bikr;

//the plain data classes that mirror DB rows
import bikr.model.*;
import bikr.model.enums.*;
//the Observer pattern implementation
import bikr.pattern.CategoryUpgradeService;   // the Subject that fires events
import bikr.pattern.LicenseCategoryObserver;  // Observer that updates the license table
import bikr.pattern.RacerNotifyObserver;      // Observer that prints a message
// Repositories: the classes that talk to the SQLite database
import bikr.repository.*;

// Standard Java time / collections
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
 runs racer-registration + podium-upgrade flow
 with no user input. Prints what happens at each step to see
 the design patterns (Builder, Observer) and the DB writes happening.
 What happens:
    1. Create a racer that already has 4 podiums
    2. Register that racer for the first race in the DB
    3. Organizer posts results: racer finishes 2nd (a podium)
    4. Observer pattern fires -> notification printed, license updated
    5. racer's category is upgraded, podiums reset to 0
 */
public class Main {

    public static void main(String[] args)
    {
        // create one instance of every repository we need
        UserRepository         userRepo    = new UserRepository();
        RaceRepository         raceRepo    = new RaceRepository();
        RegistrationRepository regRepo     = new RegistrationRepository();
        RaceResultRepository   resultRepo  = new RaceResultRepository();
        LicenseRepository      licenseRepo = new LicenseRepository();

        // The observer pattern
        // CategoryUpgradeService is the Subject, it holds a list of observers.
        // Whenever a racer hits 5 podiums, it calls update() on every observer.
        //   1. RacerNotifyObserver      ->  prints "Racer X promoted to CAT_Y"
        //   2. LicenseCategoryObserver  ->  updates the licenses table in the DB
        CategoryUpgradeService upgradeService = new CategoryUpgradeService(userRepo);
        upgradeService.attach(new RacerNotifyObserver());
        upgradeService.attach(new LicenseCategoryObserver(licenseRepo));



        // 1) Create a racer and insert into the DB
        System.out.println("\nstep 1: Creating a racer");

        // Unique email so re-running the test doesn't hit the UNIQUE constraint
        // on the users.email column. Without this, the second run would fail.
        String uniqueEmail = "testracer" + System.currentTimeMillis() + "@bikr.com";

        // Create a Racer model object in memory. Nothing is saved yet.
        // The constructor sets currentPodiums = 0 and category = CAT_5.
        Racer racer = new Racer("Test", "Racer", uniqueEmail, "555000111", "pass");

        // setting podiums to 4 to trigger the cat upgrade and fire the observer pattern design
        racer.setCurrentPodiums(4);

        // insertRacer inserts 2 things:
        //  1. row in `users` (role='RACER', name, email, ssn, password)
        //  2. row in `racers` (user_id, current_podiums=4, category='CAT_5')
        // Returns the generated user_id, which we also store on the model object.
        int racerId = userRepo.insertRacer(racer);

        System.out.println("  Racer: " + racer.getFullName() + " (id=" + racerId + ")");
        System.out.println("  Starting podiums: " + racer.getCurrentPodiums());
        System.out.println("  Starting category: " + racer.getCategory());

        // Give the racer a license so the LicenseCategoryObserver has a row
        // to update later.
        // expires one year from today, same category as the racer.
        License license = new License(racerId, LocalDate.now().plusYears(1), racer.getCategory());
        licenseRepo.insert(license);


        // 2) — List races and register for one
        System.out.println("\nStep 2: Available races:");

        // findAll() executes: SELECT * FROM races ORDER BY race_id
        // Each row is reconstructed into a Race object via RaceBuilder (Builder pattern).
        List<Race> races = raceRepo.findAll();

        for (Race r : races) {
            // countByRace(): SELECT COUNT(*) FROM registrations WHERE race_id=? AND status='CONFIRMED'
            int seats = regRepo.countByRace(r.getRaceId());
            System.out.println("  " + r.getRaceId() + ". " + r.getRaceName()
                    + " (" + seats + "/" + r.getRaceMaxRegistrations() + ")");
        }

        // Pick the first seeded race (Grand Canyonic heated wheels, max 7 seats)
        Race race = races.get(0);
        System.out.println("\n  Registering for: " + race.getRaceName());

        // Build a Registration model. The constructor automatically sets
        // status = CONFIRMED. Category is the racer's current category.
        Registration reg = new Registration(racerId, race.getRaceId(), racer.getCategory());

        // insert() writes to the registrations table:
        // INSERT INTO registrations (racer_id, race_id, status, category) VALUES (...)
        regRepo.insert(reg);

        // Re-count to prove the insert worked
        int seatsAfter = regRepo.countByRace(race.getRaceId());
        System.out.println("  Registered! Seats now: " + seatsAfter + "/"
                + race.getRaceMaxRegistrations());

        // 3) — Organizer posts results
        System.out.println("\nStep 3: Organizer posting results");

        // Create the parent result row for this race.
        // INSERT INTO race_results (race_id, category, posted_date) VALUES (...)
        // One RaceResult per race/category combination.
        RaceResult result = new RaceResult(race.getRaceId(), CategoryLevel.CAT_5, LocalDate.now());
        int resultId = resultRepo.insertResult(result);

        // Create one ResultEntry for our racer. We say they finished 2nd.
        // The ResultEntry constructor automatically sets:
        // podiumCounted = (finishingPosition >= 1 && finishingPosition <= 3)
        // So position 2 → podiumCounted = true.
        // INSERT INTO result_entries (result_id, racer_id, finishing_position, podium_counted)
        ResultEntry entry = new ResultEntry(resultId, racerId, 2);
        resultRepo.insertEntry(entry);

        System.out.println("  " + racer.getFullName() + "'s rank: " + entry.getFinishingPosition()
                + " (podiumCounted=" + entry.isPodiumCounted() + ")");


        // 4) Fire the observers
        //  The Observer pattern's part
        // CategoryUpgradeService.processResult() does this for each entry:
        //   1. Load the racer from the DB
        //   2. If podiumCounted: increment their podium count
        //   3. If new count reaches 5:
        //        a) Promote to next CategoryLevel
        //        b) Reset podiums to 0
        //        c) Call notifyObservers(racer, newCategory)
        //             -> RacerNotifyObserver.update()      prints a message
        //             -> LicenseCategoryObserver.update()  updates the DB
        //   4. Else: just save the new count
        // Our racer starts at 4 podiums, gets +1 → 5 → triggers the promotion.
        System.out.println("\n4) Processing results (running observers)");

        List<ResultEntry> entries = new ArrayList<>();
        entries.add(entry);
        upgradeService.processResult(entries);

        // 5)Verify the DB actually reflects all of the above
        // Re-fetch from the DB so we're reading persisted state, not in-memory.
        System.out.println("\n5) Verifying state");
        Racer updatedRacer = userRepo.findRacerById(racerId);
        License updatedLicense = licenseRepo.findByUserId(racerId);

        //category now CAT_4 (upgraded)
        //podiums back to 0 (reset after promotion)
        //license category also CAT_4 (observer updated it)
        System.out.println("Racer category: " + updatedRacer.getCategory());
        System.out.println("Racer podiums: " + updatedRacer.getCurrentPodiums()
                + "  (reset to 0 after upgrade)");
        System.out.println("License category: " + updatedLicense.getCategory());

        //end of the demo
        System.out.println("\nDEMO COMPLETE");
    }
}