package bikr.controller;

import bikr.view.ResultView;

import java.util.Scanner;

public class ResultController {

    private final ResultView resultView;

    public ResultController() {
        this.resultView = new ResultView();
    }

    public boolean postResults(Scanner scanner) {
        System.out.println("\n--- Post Race Results ---");
        resultView.displayRegisteredRacers();

        System.out.print("Enter Racer Name/ID: ");
        String racerId = scanner.nextLine().trim();

        if (racerId.isEmpty()) {
            System.out.println("\n[ERROR] Result posting failed: Racer identifier required.");
            return false;
        }

        int position = resultView.enterFinishingPosition(scanner);

        resultView.clickSaveResults();
        System.out.println("\n[SUCCESS] Position " + position + " recorded for Racer '" + racerId + "'. Results posted successfully!");
        return true;
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
