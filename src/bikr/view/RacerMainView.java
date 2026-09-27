package bikr.view;

import java.util.Scanner;

public class RacerMainView {

    public void displayAvailableRaces() {
        System.out.println("\n=== Available Races ===");
        System.out.println("1. Race ID: 101 | Spring Criterium | Date: 2026-10-15");
    }

    public String searchRaces(Scanner scanner) {
        System.out.print("Enter search: ");
        return scanner.nextLine().trim();
    }

    public void displayMyRaces() {
        System.out.println("\n=== My Registered Races ===");
        System.out.println("1. Race ID: 101 | Spring Criterium");
    }

    public void displayReviewTab() {
        // Stubbed feature.
        System.out.println("Review tab stubbed.");
    }

    public void clickSettings() {
        // Stubbed feature.
        System.out.println("Click settings stubbed.");
    }
}