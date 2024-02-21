package ikapp;

import static ikapp.Admin.admins;
import static ikapp.Student.students;
import static ikapp.Course.courses;
import ikapp.database.DBUtils;

public interface IPrintAdmin {

    default void printAdminsList() {
        admins = DBUtils.getTableAdminData();
        System.out.println(admins);
    }

    default void printFirstAdmin() {
        admins = DBUtils.getTableAdminData();
        System.out.println(admins.get(0));
    }

    default void printLastAdmin() {
        admins = DBUtils.getTableAdminData();
        System.out.println(admins.get(admins.size() - 1));
    }

    default void printStudentsList() {
        students = DBUtils.getTableStudentData();
        System.out.println(students);
    }

    default void printLastStudent() {
        students = DBUtils.getTableStudentData();
        System.out.println(students.get(students.size() - 1));
    }

    default void printCoursesList() {
        System.out.println("Available Courses:");
        courses = DBUtils.getTableCourseData();
        System.out.println(courses);
        System.out.println();
    }
}
