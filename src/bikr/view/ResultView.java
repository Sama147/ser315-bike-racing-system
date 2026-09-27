package bikr.view;

import java.util.Scanner;

public class ResultView {

    public void displayRegisteredRacers() {
        System.out.println("\n=== Registered Racers ===");
        System.out.println("1. Racer ID: 10 | Name: John Doe");
    }

    public int enterFinishingPosition(Scanner scanner) {
        System.out.print("Enter finishing position: ");
        int position = scanner.nextInt();
        scanner.nextLine();
        return position;
    }

    public void clickSaveResults() {
        // Stubbed feature.
        System.out.println("Save results stubbed.");
    }
}