package ikapp;

import ikapp.database.DBUtils;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin extends Person implements IPrintAdmin, IExit {
    private String id = "A";
    private static int adminID = 1000001;
    private int tblAdminId;
    private int tblAdminPersonId;
    public static List<Admin> admins = new ArrayList<>();
    private static int idAdmin = 1;

    public Admin(String firstName, String lastName) {
        super(firstName, lastName);
        this.id = id + adminID;
        adminID++;
        this.tblAdminId = idAdmin;
        idAdmin++;
        this.tblAdminPersonId = getTblPersonId();
    }

    public Admin(){};

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTblAdminId() {
        return tblAdminId;
    }

    public void setTblAdminId(int tblAdminId) {
        this.tblAdminId = tblAdminId;
    }

    public int getTblAdminPersonId() {
        return tblAdminPersonId;
    }

    public void setTblAdminPersonId(int tblAdminPersonId) {
        this.tblAdminPersonId = tblAdminPersonId;
    }   //?????

    @Override
    public String toString() {    // динамичный полиморфизм
        return "Admin {" +
                "tblAdminId = " + getTblAdminId() +
                ", tblPersonAdminId = " + getTblAdminPersonId() +
                ", tblPersonId = " + getTblPersonId() +
                ", firstName = '" + getFirstName() + "'" +
                ", lastName = '" + getLastName() + "'" +
                ", username = '" + getUserName() + "'" +
                ", password = '" + getPassword() + "'" +
                ", id = " + getId() +
                "}";
    }

    public static void addAdmin(Admin admin) {
        DBUtils.createAdmin(admin);
        admins =  DBUtils.getTblAdminData();  // get admins from DB after creating
    }

    public void runAdmin(){
        printQforExit();

        Scanner in = new Scanner(System.in);

        System.out.println("What would you like: ");
        System.out.println("1. Register new user");
        System.out.println("2. Print existing data from DB ");
        System.out.println();

        String input = in.nextLine();
        switch (input) {
            case "Q", "q" -> {
                exitIfQ();
            }
            case "1" -> runRegistration();
            case "2" -> runPrintInformation();
        }
    }

    private void runRegistration() {
        System.out.println("Running registration");
        printQforExit();

        Scanner in = new Scanner(System.in);

        System.out.println("What would you like: ");
        System.out.println("1. Register new Student");
        System.out.println("2. Register new Professor");
        System.out.println("3. Register new Admin");

        String input = in.nextLine();
        switch (input) {
            case "Q", "q" -> {
                exitIfQ();
            }
            case "1" -> runRegisterNewStudent();
            case "2" -> registerNewProfessor();
            case "3" -> registerNewAdmin();
        }
    }

    private void runPrintInformation() {
        System.out.println("Run print information");
        printQforExit();
    }

    private void runRegisterNewStudent(){
        System.out.println("Register New student");
        printQforExit();

        Scanner in = new Scanner(System.in);

        System.out.println("Enter first name: ");
        String input = in.nextLine();
        exitIfQ(input);
        String firstName = input;

        System.out.println("Enter last name: ");
        input = in.nextLine();
        exitIfQ(input);
        String lastName = input;

        Student student = new Student(firstName, lastName);
        Student.addStudent(student);
        printStudentsList();

        System.out.println("Would you like to register student for courses? ");
        System.out.println("1 - Yes");
        System.out.println("2 - No");
        System.out.println("Enter 'Q' for quit or exit");
        input = in.nextLine();

        switch (input) {
            case "Q", "q" -> {
                System.out.println("Goodbye!");
                System.exit(0);
            }
            case "1" -> runCourseRegistration();
            case "2" -> {
//                Student student = new Student(firstName, lastName, new ArrayList<>());
//                student.addStudent(student);
                runRegistration();
            }
        }
    }

    private void registerNewProfessor(){
        System.out.println("Register New Professor");
        Utils.printExitMsg();
    }

    private void registerNewAdmin(){
        System.out.println("Register New Admin");
        Utils.printExitMsg();
    }

    private void runCourseRegistration() {
        System.out.println("Register Student for Courses");
        Utils.printExitMsg();

        List<String> courses = new ArrayList<>();

        Scanner in = new Scanner(System.in);
        boolean flag = true;

        do{
            System.out.println("Enter course name OR 'Q' for exit: ");
            String input = in.nextLine();
            switch (input) {
                case "Q", "q" -> {
                    Utils.printList(courses);
                    flag = false;
                    runRegistration();
                }
                default  -> {
                    courses.add(input);
                    Utils.printList(courses);
                }
            }
        } while (flag);

//        Student student = new Student(firstName, lastName, new ArrayList<>());
//        student.addStudent(student);
    }

}
