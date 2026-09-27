package bikr.view;

import java.util.Scanner;

public class RacerSignUpView {

    public String[] enterPersonalInfo(Scanner scanner) {
        System.out.println("\n=== Racer Sign Up ===");
        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();

        return new String[]{name, email, password};
    }

    public void enterCardInfo(Scanner scanner) {
        // Stubbed feature
        System.out.println("Card information entry stubbed: out of scope for this phase.");
    }

    public void clickBuyLicense() {
        // Stubbed feature
        System.out.println("License purchase action stubbed: out of scope for this phase.");
    }

    public void displayRegistrationComplete() {
        System.out.println("Racer registration completed successfully!");
    }

    public void clickCancel() {
        // Stubbed feature
        System.out.println("Registration cancelled. Feature stubbed: returning to main menu.");
    }
}