package bikr.view;

import java.util.Scanner;

public class OrganizerSignUpView {

    public String[] enterPersonalInfo(Scanner scanner) {
        System.out.println("\n=== Organizer Sign Up ===");
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine().trim();

        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine().trim();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter SSN: ");
        String ssn = scanner.nextLine().trim();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();

        return new String[]{firstName, lastName, email, ssn, password};
    }

    public void clickSendAccessRequest() {
        System.out.println("Access request submitted! Pending admin approval.");
    }

    public void clickCancel() {
        // Stubbed feature
        System.out.println("Registration cancelled. Feature stubbed: returning to main menu.");
    }
}