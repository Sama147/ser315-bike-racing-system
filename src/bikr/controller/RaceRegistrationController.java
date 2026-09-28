package bikr.controller;

import bikr.view.RaceRegistrationView;

import bikr.model.Race;
import bikr.model.Racer;
import bikr.model.Registration;
import bikr.repository.RaceRepository;
import bikr.repository.RegistrationRepository;
import bikr.repository.UserRepository;

import java.util.List;
import java.util.Scanner;

public class RaceRegistrationController {

    private final RaceRegistrationView raceRegistrationView;

    private final RaceRepository raceRepository;
    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;

    public RaceRegistrationController(RaceRepository raceRepository, RegistrationRepository registrationRepository,
                                       UserRepository userRepository) {
        this.raceRegistrationView = new RaceRegistrationView();

        this.raceRepository = raceRepository;
        this.registrationRepository = registrationRepository;
        this.userRepository = userRepository;
    }

    public boolean registerForRace(int racerId, Scanner scanner) {
        System.out.println("\n--- Race Registration ---");

        List<Race> races = raceRepository.findAll();
        if (races.isEmpty()) {
            System.out.println("No races available.");
            return false;
        }

        System.out.println("\n=== Available Races ===");
        for (Race r : races) {
            int registered = registrationRepository.countByRace(r.getRaceId());
            System.out.println(r.getRaceId() + " - " + r.getRaceName()
                    + " (" + registered + "/" + r.getRaceMaxRegistrations() + ")");
        }

        raceRegistrationView.clickRegisterRace();

        System.out.print("Enter Race ID to register: ");
        String raceIdStr = scanner.nextLine().trim();

        if (raceIdStr.isEmpty()) {
            System.out.println("\n[ERROR] Registration failed: Race ID is required.");
            return false;
        }

        int raceId = Integer.parseInt(raceIdStr);
        Race race = raceRepository.findById(raceId);
        if (race == null) {
            System.out.println("\n[ERROR] Race not found.");
            return false;
        }

        raceRegistrationView.displayRaceDetails();

        int registeredCount = registrationRepository.countByRace(raceId);
        if (registeredCount >= race.getRaceMaxRegistrations()) {
            System.out.println("\n[ERROR] Can't register, this race is fully registered.");
            return false;
        }

        Racer racer = userRepository.findRacerById(racerId);
        if (racer == null) {
            System.out.println("\n[ERROR] Racer not found.");
            return false;
        }

        Registration registration = new Registration(racerId, raceId, racer.getCategory());
        int registrationId = registrationRepository.insert(registration);

        System.out.println("\n[SUCCESS] Registered for '" + race.getRaceName()
                + "' successfully! (registration_id=" + registrationId + ")");
        return true;
    }

    public boolean waitlistForRace() {
        // Stubbed feature.
        System.out.println("Processing waitlist registration via RaceRegistrationController...");
        return true;
    }

    public boolean cancelRegistration() {
        // Stubbed feature.
        System.out.println("Processing registration cancellation via RaceRegistrationController...");
        return true;
    }

    public boolean checkEligibility(String raceId) {
        return raceId != null && !raceId.isBlank();
    }
}

