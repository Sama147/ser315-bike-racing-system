package bikr;

import bikr.pattern.CategoryUpgradeService;
import bikr.pattern.RacerNotifyObserver;
import bikr.pattern.LicenseCategoryObserver;
import bikr.repository.UserRepository;
import bikr.repository.RaceRepository;
import bikr.repository.RegistrationRepository;
import bikr.repository.AccessRequestRepository;
import bikr.repository.RaceResultRepository;
import bikr.repository.LicenseRepository;
import bikr.controller.AuthController;
import bikr.controller.AdminController;
import bikr.controller.RaceManagementController;
import bikr.controller.RaceRegistrationController;
import bikr.controller.ResultController;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Bikr demo!\n");

        // 1. Repositories
        UserRepository userRepo = new UserRepository();
        RaceRepository raceRepo = new RaceRepository();
        RegistrationRepository regRepo = new RegistrationRepository();
        AccessRequestRepository reqRepo = new AccessRequestRepository();
        RaceResultRepository resultRepo = new RaceResultRepository();
        LicenseRepository licenseRepo = new LicenseRepository();

        // 2. Observer wiring (once)
        CategoryUpgradeService upgradeService = new CategoryUpgradeService(userRepo);
        upgradeService.attach(new RacerNotifyObserver());
        upgradeService.attach(new LicenseCategoryObserver(licenseRepo));

        // 3. Controllers
        AuthController auth = new AuthController(userRepo, licenseRepo, reqRepo);
        AdminController adminCtrl = new AdminController(reqRepo);
        RaceManagementController raceMgmtCtrl = new RaceManagementController(raceRepo);
        RaceRegistrationController raceRegCtrl = new RaceRegistrationController(raceRepo, regRepo, userRepo);
        ResultController resultCtrl = new ResultController(raceRepo, regRepo, resultRepo, userRepo, upgradeService);

        Scanner sc = new Scanner(System.in);

        //Demo walkthrough

        System.out.println("=== STEP 1: Racer Sign-Up ===");
        auth.signUpRacer(sc);

        System.out.println("\n=== STEP 2: Organizer Sign-Up ===");
        auth.signUpOrganizer(sc);

        System.out.println("\n=== STEP 3: Admin Approves Organizer ===");
        adminCtrl.reviewAccessRequests(sc);

        System.out.println("\n=== STEP 4: Organizer Creates a Race ===");
        System.out.print("Enter the Organizer's user_id (printed in Step 2 above): ");
        int organizerId = Integer.parseInt(sc.nextLine().trim());
        raceMgmtCtrl.createRace(organizerId, sc);

        System.out.println("\n=== STEP 5: Racer Registers for the Race ===");
        System.out.print("Enter the Racer's user_id (printed in Step 1 above): ");
        int racerId = Integer.parseInt(sc.nextLine().trim());
        raceRegCtrl.registerForRace(racerId, sc);

        System.out.println("\n=== STEP 6 & 7: Organizer Posts Results (triggers category upgrade) ===");
        resultCtrl.postResults(sc);

        System.out.println("\n=== STEP 8: Racer Signs In to View New Category ===");
        auth.signIn(sc);

        System.out.println("\nDemo complete. Bye!");
        sc.close();
    }
}
