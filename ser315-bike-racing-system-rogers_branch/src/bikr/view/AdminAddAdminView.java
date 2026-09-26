package bikr.view;

import java.util.Scanner;

public class AdminAddAdminView {

    public String[] enterAdminDetails(Scanner scanner) {
        System.out.println("\n=== Add New Admin ===");
        System.out.print("Enter Admin Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter Admin Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine().trim();

        return new String[]{name, email, password};
    }

    public void clickSaveBtn() {
        System.out.println("New admin account created successfully.");
    }
}