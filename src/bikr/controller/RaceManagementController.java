package bikr.controller;

import bikr.view.OrganizerMainView;
import bikr.view.OrganizerRaceManagementView;

import java.util.Scanner;

public class RaceManagementController {

    private final OrganizerMainView organizerMainView;
    private final OrganizerRaceManagementView raceManagementView;

    public RaceManagementController() {
        this.organizerMainView = new OrganizerMainView();
        this.raceManagementView = new OrganizerRaceManagementView();
    }

    public boolean createRace(Scanner scanner) {
        System.out.println("\n--- Create New Race ---");
        organizerMainView.clickCreateRace();
        raceManagementView.displayRaceForm();

        System.out.print("Enter Race Name: ");
        String raceName = scanner.nextLine().trim();

        System.out.print("Enter Race Date (YYYY-MM-DD): ");
        String raceDate = scanner.nextLine().trim();

        System.out.print("Enter Location: ");
        String location = scanner.nextLine().trim();

        System.out.print("Enter Registration Fee ($): ");
        String fee = scanner.nextLine().trim();

        if (raceName.isEmpty() || raceDate.isEmpty()) {
            System.out.println("\n[ERROR] Race creation failed: Name and Date are required.");
            return false;
        }

        System.out.println("\n[SUCCESS] Race '" + raceName + "' successfully created on " + raceDate + " at " + location + " ($" + fee + ")!");
        return true;
    }

    public void editRace() {
        // Stubbed feature.
        System.out.println("Editing race via RaceManagementController...");
    }

    public void cancelRace() {
        // Stubbed feature.
        System.out.println("Cancelling race via RaceManagementController...");
    }

    public void getOrganizedRaces() {
        // Stubbed feature.
        System.out.println("Retrieving organized races via RaceManagementController...");
    }
}
