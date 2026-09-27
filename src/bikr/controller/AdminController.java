package bikr.controller;

import bikr.view.AdminMainView;
import bikr.view.AdminManageAccountsView;
import bikr.view.AdminEditAccountsView;

import bikr.model.AccessRequest;
import bikr.model.enums.RequestStatus;
import bikr.repository.AccessRequestRepository;

import java.util.List;
import java.util.Scanner;

public class AdminController {

    private final AdminMainView adminMainView;
    private final AdminManageAccountsView manageAccountsView;
    private final AdminEditAccountsView editAccountsView;

    private final AccessRequestRepository accessRequestRepository;

    public AdminController(AccessRequestRepository accessRequestRepository) {
        this.adminMainView = new AdminMainView();
        this.manageAccountsView = new AdminManageAccountsView();
        this.editAccountsView = new AdminEditAccountsView();

        this.accessRequestRepository = accessRequestRepository;
    }

    public void reviewAccessRequests(Scanner scanner) {
        List<AccessRequest> pending = accessRequestRepository.findPending();

        if (pending.isEmpty()) {
            System.out.println("\nNo pending organizer access requests.");
            return;
        }

        System.out.println("\n=== Pending Organizer Access Requests ===");
        for (AccessRequest ar : pending) {
            System.out.println(ar.getRequestId() + ". Pending Request: user_id=" + ar.getUserId());
        }

        System.out.print("Enter Request ID to review: ");
        int requestId = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Action [1: Approve, 2: Decline, 3: Skip]: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                approveAccessRequest(requestId);
                break;
            case "2":
                declineAccessRequest(requestId);
                break;
            default:
                System.out.println("Skipped access request review.");
                break;
        }
    }

    public void approveAccessRequest(int requestId) {
        accessRequestRepository.updateStatus(requestId, RequestStatus.APPROVED);
        System.out.println("\n[SUCCESS] Access request #" + requestId + " APPROVED. Organizer is now active.");
    }

    public void declineAccessRequest(int requestId) {
        accessRequestRepository.updateStatus(requestId, RequestStatus.DENIED);
        System.out.println("\n[INFO] Access request #" + requestId + " DECLINED. Applicant notified via email.");
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
