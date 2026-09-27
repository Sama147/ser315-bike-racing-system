package bikr.controller;

import bikr.view.SignInView;
import bikr.view.RacerSignUpView;
import bikr.view.OrganizerSignUpView;
import bikr.view.MainPageView;
import bikr.model.Racer;
import bikr.model.Organizer;
import bikr.model.License;
import bikr.model.AccessRequest;
import bikr.model.User;
import bikr.repository.UserRepository;
import bikr.repository.LicenseRepository;
import bikr.repository.AccessRequestRepository;
import java.time.LocalDate;
import java.util.Scanner;

public class AuthController {

    private final SignInView signInView;
    private final RacerSignUpView racerSignUpView;
    private final OrganizerSignUpView organizerSignUpView;
    private final MainPageView mainPageView;

    private final UserRepository userRepository;
    private final LicenseRepository licenseRepository;
    private final AccessRequestRepository accessRequestRepository;

    public AuthController(UserRepository userRepository, LicenseRepository licenseRepository,
                           AccessRequestRepository accessRequestRepository) {
        this.signInView = new SignInView();
        this.racerSignUpView = new RacerSignUpView();
        this.organizerSignUpView = new OrganizerSignUpView();
        this.mainPageView = new MainPageView();

        this.userRepository = userRepository;
        this.licenseRepository = licenseRepository;
        this.accessRequestRepository = accessRequestRepository;
    }

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

    public User signIn(Scanner scanner) {
        System.out.println("\n--- Sign In ---");
        String email = signInView.enterEmail(scanner);
        String password = signInView.enterPassword(scanner);

        User user = userRepository.findByEmail(email);

        if (user == null) {
            System.out.println("\n[ERROR] Account does not exist. Please sign up instead.");
            return null;
        }

        if (!user.getPassword().equals(password)) {
            System.out.println("\n[ERROR] Invalid email or password.");
            return null;
        }

        signInView.clickSignIn();
        System.out.println("\n[SUCCESS] Authentication successful! Welcome back, " + user.getFullName() + ".");

        if (user instanceof Racer) {
            Racer racer = (Racer) user;
            System.out.println("Category: " + racer.getCategory() + " | Podiums: " + racer.getCurrentPodiums());
        }

        return user;
    }

    public boolean signUpRacer(Scanner scanner) {
        String[] info = racerSignUpView.enterPersonalInfo(scanner);
        String firstName = info[0];
        String lastName = info[1];
        String email = info[2];
        String ssn = info[3];
        String password = info[4];

        racerSignUpView.enterCardInfo(scanner); // stubbed, out of scope for demo

        Racer racer = new Racer(firstName, lastName, email, ssn, password);
        int userId = userRepository.insertRacer(racer);

        License license = new License(userId, LocalDate.now().plusYears(1), racer.getCategory());
        licenseRepository.insert(license);

        System.out.println("\n[SUCCESS] Racer registration complete! (user_id=" + userId + ")");
        racerSignUpView.displayRegistrationComplete();
        return true;
    }

    public boolean signUpOrganizer(Scanner scanner) {
        String[] info = organizerSignUpView.enterPersonalInfo(scanner);
        String firstName = info[0];
        String lastName = info[1];
        String email = info[2];
        String ssn = info[3];
        String password = info[4];

        Organizer organizer = new Organizer(firstName, lastName, email, ssn, password);
        int userId = userRepository.insertOrganizer(organizer);

        AccessRequest request = new AccessRequest(userId); // defaults to PENDING
        accessRequestRepository.insert(request);

        organizerSignUpView.clickSendAccessRequest();
        System.out.println("\n[SUCCESS] Organizer access request submitted for admin approval. (user_id=" + userId + ")");
        return true;
    }
}
