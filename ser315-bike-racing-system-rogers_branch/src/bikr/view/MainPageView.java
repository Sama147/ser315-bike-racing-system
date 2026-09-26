package bikr.view;

import java.util.Scanner;

public class MainPageView {

    public void displayLogo() {
        System.out.println("=================================");
        System.out.println("            BikR App             ");
        System.out.println("=================================");
    }

    public void displayText() {
        System.out.println("Welcome! Please select an option to proceed.");
    }

    public void displaySignInBtn() {
        System.out.println("1. Sign In");
    }

    public void displaySignUpBtn() {
        System.out.println("2. Sign Up");
    }

    public int getUserChoice(Scanner scanner) {
        System.out.print("Enter option: ");
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Enter a valid number: ");
            scanner.next();
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); 
        return choice;
    }
}