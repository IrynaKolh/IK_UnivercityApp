package ikapp;

import ikapp.database.DBUtils;

import java.util.List;
import java.util.Scanner;

public class SignUp {

    private void printWelcomeMessage() {
        System.out.println("        Welcome to IKApp!");
        System.out.println();
    }

    private void signUp() {
        Admin admin = new Admin("Ivan", "Sidorov");
        admin.addAdmin(admin);

        System.out.println();
        System.out.println("______________________________________");
        System.out.println("Local Admins List");
        Admin.printAdmins();

        System.out.println();
        System.out.println("______________________________________");
        System.out.println("DB Users List");
        List<Person> dbTablePerson = DBUtils.getTblPersonData();
        System.out.println(dbTablePerson);

        System.out.println();
        System.out.println("______________________________________");
        System.out.println("DB Admins List");
        List<Admin> dbTableAdmin = DBUtils.getTblAdminData();
        System.out.println(dbTableAdmin);


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
                admin.runAdmin();
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
