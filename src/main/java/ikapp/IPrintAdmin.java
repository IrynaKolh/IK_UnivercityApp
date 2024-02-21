package ikapp;

import static ikapp.Admin.admins;
import static ikapp.Student.students;

public interface IPrintAdmin {
    default  void printAdminsList() {
        System.out.println(admins);
    }

    default  void printStudentsList() {
        System.out.println(students);
    }
}
