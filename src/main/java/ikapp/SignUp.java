package ikapp;

import java.util.List;
import java.util.Scanner;

public class SignUp {

    private void printWelcomeMessage() {
        System.out.println("        Welcome to AJU!");
        System.out.println();
    }

    private void signUp() {
        Admin.addAdmin(new Admin("Ivan", "Ivanov"));

        Admin.ptintAdmins();

        System.out.println();
        System.out.println("Enter 'Q' for quit or");
        System.out.println();

        Scanner in = new Scanner(System.in);

        System.out.print("Enter username: ");
        String input = in.nextLine();
        if (input.equals("Q") || input.equals("q")) {
            System.out.println("Goodbye!");
            System.exit(0);
        }
        String username = input;

        System.out.print("Enter password: ");
        input = in.nextLine();
        if (input.equals("Q") || input.equals("q")) {
            System.out.println("Goodbye!");
            System.exit(0);
        }
        String password = input;

        checkCredentials(username, password);

    }

    private void checkCredentials(String userName, String password) {
        List<Admin> adminList = Admin.getAdmins();
        for (Admin admin : adminList) {
            if (admin.getUserName().equals(userName) && admin.getPassword().equals(password)) {
                System.out.println("Welcome," + admin.getFirstName() + " " + admin.getLastName() + "!");
                Admin.runAdmin();
            } else {
                System.out.println("Sorry, we can't recognize you. Check your credentials and try again later.");
                System.out.println("Goodbye!");
                System.exit(0);
            }
        }
    }

    public void runIKApp(){
        printWelcomeMessage();
        signUp();
    }
}
