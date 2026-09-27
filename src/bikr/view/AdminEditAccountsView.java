package bikr.view;

import java.util.Scanner;

public class AdminEditAccountsView {

    public void displayUserInfo() {
        System.out.println("\n=== Edit Account Information ===");
        System.out.println("User ID: 1 | Name: Jane Doe | Email: jane@bikr.com");
    }

    public void displayCurrentLicense() {
        System.out.println("Current License: Active | Exp: 2026-12-31");
    }

    public void displayCurrentCategory() {
        System.out.println("Current Category: Category 3");
    }

    public void displayCurrentPodiums() {
        System.out.println("Current Podiums: 4");
    }

    public String captureCategoryChange(Scanner scanner) {
        // Stubbed feature.
        System.out.println("Capture category change stubbed.");
        return "";
    }

    public int capturePodiumsChange(Scanner scanner) {
        // Stubbed feature.
        System.out.println("Capture podiums change stubbed.");
        return 0;
    }

    public String captureLicenseExpDateChange(Scanner scanner) {
        // Stubbed feature.
        System.out.println("Capture license exp date change stubbed.");
        return "";
    }

    public void displayDeactivateAccount() {
        // Stubbed feature.
        System.out.println("Deactivate account display stubbed.");
    }

    public void displayChangeRole() {
        // Stubbed feature.
        System.out.println("Change role display stubbed.");
    }

    public void displaySaveBtn() {
        System.out.println("[Save Changes]");
    }

    public void displayCancelBtn() {
        System.out.println("[Cancel]");
    }
}