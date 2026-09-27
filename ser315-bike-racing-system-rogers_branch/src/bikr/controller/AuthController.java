<<<<<<< HEAD
package bikr.controller; public class AuthController { }
=======
package bikr.controller;

import bikr.view.SignInView;
import bikr.view.RacerSignUpView;
import bikr.view.OrganizerSignUpView;
import bikr.view.MainPageView;

import java.util.Scanner;

public class AuthController {

    private final SignInView signInView;
    private final RacerSignUpView racerSignUpView;
    private final OrganizerSignUpView organizerSignUpView;
    private final MainPageView mainPageView;

    public AuthController() {
        this.signInView = new SignInView();
        this.racerSignUpView = new RacerSignUpView();
        this.organizerSignUpView = new OrganizerSignUpView();
        this.mainPageView = new MainPageView();
    }

    /**
     * Entry point for authentication flow from the Main Page.
     */
    public void startAuthFlow(Scanner scanner) {
        mainPageView.displayLogo();
        mainPageView.displayText();

        System.out.println("\nSelect an option:");
        System.out.println("1. Sign In");
        System.out.println("2. Sign Up as Racer");
        System.out.println("3. Sign Up as Organizer");
        System.out.print("Choice: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                signIn(scanner);
                break;
            case "2":
                signUpRacer(scanner);
                break;
            case "3":
                signUpOrganizer(scanner);
                break;
            default:
                System.out.println("Invalid selection. Returning to main menu.");
                break;
        }
    }

    public boolean signIn(Scanner scanner) {
        System.out.println("\n--- Sign In ---");
        String email = signInView.enterEmail(scanner);
        String password = signInView.enterPassword(scanner);

        if (validateCredentials(email, password)) {
            System.out.println("\n[SUCCESS] Authentication successful! Welcome back, " + email + ".");
            return true;
        } else {
            System.out.println("\n[ERROR] Invalid email or password.");
            return false;
        }
    }

    public boolean signUpRacer(Scanner scanner) {
        System.out.println("\n--- Racer Sign Up ---");
        racerSignUpView.enterPersonalInfo(scanner);
        racerSignUpView.enterCardInfo(scanner);
        
        System.out.println("\n[SUCCESS] Racer registration complete!");
        racerSignUpView.displayRegistrationComplete();
        return true;
    }

    public boolean signUpOrganizer(Scanner scanner) {
        System.out.println("\n--- Organizer Sign Up ---");
        organizerSignUpView.enterPersonalInfo(scanner);
        organizerSignUpView.clickSendAccessRequest();
        
        System.out.println("\n[SUCCESS] Organizer access request submitted for admin approval.");
        return true;
    }

    public boolean validateCredentials(String email, String password) {
        // Demo credential check
        return email != null && !email.isBlank() && password != null && !password.isBlank();
    }
}
>>>>>>> 9c1a770b4afad00ab8b522c108ab9db9aa65faa2
