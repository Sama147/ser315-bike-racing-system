package bikr.controller;

import bikr.view.RaceRegistrationView;

import java.util.Scanner;

public class RaceRegistrationController {

    private final RaceRegistrationView raceRegistrationView;

    public RaceRegistrationController() {
        this.raceRegistrationView = new RaceRegistrationView();
    }

    
    public boolean registerForRace(Scanner scanner) {
        System.out.println("\n--- Race Registration ---");
        raceRegistrationView.displayRaceDetails();
        raceRegistrationView.clickRegisterRace();

        System.out.print("Enter Race ID to register: ");
        String raceId = scanner.nextLine().trim();

        if (raceId.isEmpty()) {
            System.out.println("\n[ERROR] Registration failed: Race ID is required.");
            return false;
        }

        // Demo check for eligibility
        if (!checkEligibility(raceId)) {
            System.out.println("\n[ERROR] You are not eligible for this race category.");
            return false;
        }

        System.out.println("\n[SUCCESS] Successfully registered for Race #" + raceId + "!");
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