package bikr.view;

import java.util.Scanner;

public class AdminManageAccountsView {

    public String searchUsers(Scanner scanner) {
        System.out.print("Enter user to search: ");
        return scanner.nextLine().trim();
    }

    public void displayUserList() {
        System.out.println("\n=== User Accounts ===");
        System.out.println("1. User ID: 1 | Name: Jane Doe | Role: Racer");
    }

    public void clickEditAccount() {
        // Stubbed feature.
        System.out.println("Edit account stubbed.");
    }
}