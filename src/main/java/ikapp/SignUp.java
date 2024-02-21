package ikapp;

import ikapp.database.DBUtils;
import java.util.Scanner;
import static ikapp.Admin.admins;

public class SignUp implements IExit {

    private void printWelcomeMessage() {
        System.out.println("        Welcome to IKApp!");
        System.out.println();
    }

    private void signUp() {
        admins = DBUtils.getTableAdminData();  // get admins from DB

        if (admins.size() == 0) {
            Admin admin = new Admin("Ivan", "Sidorov");   // is no admins - create new
            Admin.addAdmin(admin);
        }
        admins.get(0).printAdminsList(); // print admins list

        printQForExit();

        Scanner in = new Scanner(System.in);

        System.out.print("Enter username: ");
        String input = in.nextLine();
        exitIfQ(input);
        String username = input;

        System.out.print("Enter password: ");
        input = in.nextLine();
        exitIfQ(input);
        String password = input;

        checkCredentials(username, password);

    }

    private void checkCredentials(String userName, String password) {
        for(Admin admin: admins) {
            if(admin.getUserName().equals(userName) && admin.getPassword().equals(password) && admin.getRoleId().startsWith("A")) {
                System.out.println("Welcome, " + admin.getFirstName() + " " + admin.getLastName() + "!");
                admin.runAdmin();
            }
        }

        exitIfAuthorizedUser();
    }

    public void runIKApp(){
        printWelcomeMessage();
        signUp();
    }
}
