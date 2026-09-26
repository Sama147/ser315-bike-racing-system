package bikr.view;

import java.util.Scanner;

public class SignInView {

    public String enterEmail(Scanner scanner) {
        System.out.print("Enter Email: ");
        return scanner.nextLine().trim();
    }

    public String enterPassword(Scanner scanner) {
        System.out.print("Enter Password: ");
        return scanner.nextLine().trim();
    }

    public void clickSignIn() {
        System.out.println("Signing in...");
    }

    public void clickCancel() {
        // Stubbed feature: out of scope for demo flow
        System.out.println("Sign in cancelled. Feature stubbed: returning to main menu.");
    }
}