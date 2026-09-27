package bikr.controller;

import bikr.view.ResultView;

import bikr.model.Race;
import bikr.model.Registration;
import bikr.model.Racer;
import bikr.model.RaceResult;
import bikr.model.ResultEntry;
import bikr.model.enums.CategoryLevel;
import bikr.repository.RaceRepository;
import bikr.repository.RegistrationRepository;
import bikr.repository.RaceResultRepository;
import bikr.repository.UserRepository;
import bikr.pattern.CategoryUpgradeService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ResultController {

    private final ResultView resultView;

    private final RaceRepository raceRepository;
    private final RegistrationRepository registrationRepository;
    private final RaceResultRepository raceResultRepository;
    private final UserRepository userRepository;
    private final CategoryUpgradeService upgradeService;

    public ResultController(RaceRepository raceRepository, RegistrationRepository registrationRepository, RaceResultRepository raceResultRepository, UserRepository userRepository, CategoryUpgradeService upgradeService) {
        this.resultView = new ResultView();

        this.raceRepository = raceRepository;
        this.registrationRepository = registrationRepository;
        this.raceResultRepository = raceResultRepository;
        this.userRepository = userRepository;
        this.upgradeService = upgradeService;
    }

    public boolean postResults(Scanner scanner) {
        System.out.println("\n--- Post Race Results ---");

        List<Race> races = raceRepository.findAll();
        if (races.isEmpty()) {
            System.out.println("No races available.");
            return false;
        }

        System.out.println("\n=== Races ===");
        for (Race r : races) {
            System.out.println(r.getRaceId() + " - " + r.getRaceName());
        }

        System.out.print("Enter Race ID to post results for: ");
        int raceId = Integer.parseInt(scanner.nextLine().trim());

        Race race = raceRepository.findById(raceId);
        if (race == null) {
            System.out.println("\n[ERROR] Race not found.");
            return false;
        }

        List<Registration> regs = registrationRepository.findByRace(raceId);
        if (regs.isEmpty()) {
            System.out.println("\n[ERROR] No registered racers for this race.");
            return false;
        }

        resultView.displayRegisteredRacers();

        System.out.println("Select Result Category: 1) CAT_1  2) CAT_2  3) CAT_3  4) CAT_4  5) CAT_5");
        System.out.print("Choice: ");
        CategoryLevel category = parseCategory(scanner.nextLine().trim());

        RaceResult raceResult = new RaceResult(raceId, category, LocalDate.now());
        int resultId = raceResultRepository.insertResult(raceResult);

        List<ResultEntry> entries = new ArrayList<>();
        for (Registration reg : regs) {
            Racer racer = userRepository.findRacerById(reg.getRacerId());
            if (racer == null) continue;

            System.out.print(racer.getFullName() + "'s finishing position: ");
            int position = Integer.parseInt(scanner.nextLine().trim());

            ResultEntry entry = new ResultEntry(resultId, racer.getUserId(), position);
            raceResultRepository.insertEntry(entry);
            entries.add(entry);
        }

        resultView.clickSaveResults();

        upgradeService.processResult(entries);

        System.out.println("\n[SUCCESS] Results posted for '" + race.getRaceName()
                + "' and category upgrades processed!");
        return true;
    }

    private CategoryLevel parseCategory(String choice) {
        switch (choice) {
            case "1": return CategoryLevel.CAT_1;
            case "2": return CategoryLevel.CAT_2;
            case "3": return CategoryLevel.CAT_3;
            case "4": return CategoryLevel.CAT_4;
            case "5": return CategoryLevel.CAT_5;
            default:
                System.out.println("Unrecognized choice, defaulting to CAT_5.");
                return CategoryLevel.CAT_5;
        }
    }

    public void rankRacers() {
        
        System.out.println("\n--- Racer Standings ---");
        System.out.println("Category1:1st Place: Racer #101");
        System.out.println("Category1:2nd Place: Racer #104");
        System.out.println("Category1:3rd Place: Racer #102");
        System.out.println("Category2:....");
        System.out.println("Category5: 3rd Place: Racer #105");
    }

    public void getRaceResults() {
        System.out.println("\nRetrieving published race results via ResultController...");
    }
}
