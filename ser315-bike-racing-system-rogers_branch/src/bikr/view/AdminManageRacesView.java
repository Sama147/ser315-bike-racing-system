package bikr.view;

import java.util.Scanner;

public class AdminManageRacesView {

    public String searchRaces(Scanner scanner) {
        System.out.print("Enter race search query: ");
        return scanner.nextLine().trim();
    }

    public void displayRaceList() {
        System.out.println("\n=== Race List ===");
        System.out.println("1. Race ID: 101 | Name: South Mountain Park | Organizer ID: 5");
    }

    public void clickCancelAllOrganizerRaces() {
        // Stubbed feature.
        System.out.println("Cancel all organizer races stubbed.");
    }

    public void clickCancelRace() {
        // Stubbed feature.
        System.out.println("Cancel race stubbed.");
    }
}