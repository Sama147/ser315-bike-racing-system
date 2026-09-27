package bikr.controller;

import bikr.view.AdminMainView;
import bikr.view.AdminManageAccountsView;
import bikr.view.AdminEditAccountsView;

import java.util.Scanner;

public class AdminController {

    private final AdminMainView adminMainView;
    private final AdminManageAccountsView manageAccountsView;
    private final AdminEditAccountsView editAccountsView;

    public AdminController() {
        this.adminMainView = new AdminMainView();
        this.manageAccountsView = new AdminManageAccountsView();
        this.editAccountsView = new AdminEditAccountsView();
    }

    
    public void reviewAccessRequests(Scanner scanner) {
        System.out.println("\n=== Pending Organizer Access Requests ===");
        System.out.println("1. Pending Request: Jane Doe (Jane@organizer.com)");
        System.out.print("Action [1: Approve, 2: Decline, 3: Skip]: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                approveAccessRequest("Jane Doe (Jane@organizer.com)");
                break;
            case "2":
                declineAccessRequest("Jane Doe (Jane@organizer.com)");
                break;
            default:
                System.out.println("Skipped access request review.");
                break;
        }
    }

    public void approveAccessRequest(String userDetails) {
        System.out.println("\n[SUCCESS] Access request APPROVED for: " + userDetails);
        System.out.println("Role updated to: ORGANIZER.");
    }

    public void declineAccessRequest(String userDetails) {
        System.out.println("\n[INFO] Access request DECLINED for: " + userDetails);
        System.out.println("Applicant notified via email.");
    }

    public void editUserAccount() {
        // Stubbed feature.
        System.out.println("Editing user account via AdminController...");
    }

    public void deactivateAccount() {
        // Stubbed feature.
        System.out.println("Deactivating account via AdminController...");
    }

    public void changeUserRole() {
        // Stubbed feature.
        System.out.println("Changing user role via AdminController...");
    }

    public void cancelAllOrganizerRaces() {
        // Stubbed feature.
        System.out.println("Cancelling all organizer races via AdminController...");
    }

    public void cancelRace() {
        // Stubbed feature.
        System.out.println("Cancelling race via AdminController...");
    }

    public void addAdministrator() {
        // Stubbed feature.
        System.out.println("Adding new administrator via AdminController...");
    }

    public void postAnnouncement() {
        // Stubbed feature.
        System.out.println("Posting announcement via AdminController...");
    }

    public void postMaintenanceAlert() {
        // Stubbed feature.
        System.out.println("Posting maintenance alert via AdminController...");
    }
}
