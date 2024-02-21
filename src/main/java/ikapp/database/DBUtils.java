package ikapp.database;

import ikapp.Admin;
import ikapp.Person;
import ikapp.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {

    private static final String SELECT_USERS = "SELECT * FROM tbl_person";
    private static final String SELECT_ADMINS = "SELECT * FROM tbl_admin";
    private static final String SELECT_STUDENTS = "SELECT * FROM tbl_student";
    private static final String SELECT_ACADEMICS = "SELECT * FROM tbl_academic";

    private static final String CREATE_USER =
            "INSERT INTO tbl_person(person_id, firstName, lastName, userName, password) VALUES (?, ?, ?, ?, ?);";
    private static final String CREATE_ADMIN =
            "INSERT INTO tbl_admin(admin_id, person_id, id) VALUES (?, ?, ?);";
    private static final String CREATE_STUDENT =
            "INSERT INTO tbl_student(student_id, person_id, academic_id, id) VALUES (?, ?, ?, ?);";
    private static final String CREATE_ACADEMIC_EMPTY_ENROLL =
            "INSERT INTO tbl_academic(academic_id) VALUES (?);";
    private static final String INSERT_COURSE = "INSERT INTO tbl_course(course_id, course_name, price) VALUES (?, ?, ?);";

    private static final String DROP_TBL_PERSON = "DROP TABLE IF EXISTS tbl_person";
    private static final String DROP_TBL_ADMIN = "DROP TABLE IF EXISTS tbl_admin";

    public static void dropTablePerson() {
        try(Connection connection = DBConnect.getConnection();
            PreparedStatement statement = connection.prepareStatement(DROP_TBL_PERSON);)
        {
            statement.execute();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void dropTableAdmin() {
        try(Connection connection = DBConnect.getConnection();
            PreparedStatement statement = connection.prepareStatement(DROP_TBL_ADMIN);)
        {
            statement.execute();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Person> getTblPersonData() {
        List<Person> dbUsers = new ArrayList<>();

        //connect to DB and send request
        try (Connection connection = DBConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USERS);)
        {
            ResultSet result = statement.executeQuery();  // поток данных с БД, сохр в спец класс ResultSet

            while (result.next()) {
                int person_id = result.getInt("person_id");
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");
                String username = result.getString("userName");
                String password = result.getString("password");

                Person person = new Admin();
                person.setTblPersonId(person_id);
                person.setFirstName(firstName);
                person.setLastName(lastName);
                person.setUserName(username);
                person.setPassword(password);

                dbUsers.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dbUsers;
    }


    public static List<Admin> getTblAdminData() {
        List<Admin> dbAdmins = new ArrayList<>();

        try(Connection connection = DBConnect.getConnection();
            PreparedStatement statementPerson = connection.prepareStatement(SELECT_USERS);
            PreparedStatement statementAdmin = connection.prepareStatement(SELECT_ADMINS);)
        {
            ResultSet resultPerson = statementPerson.executeQuery();
            ResultSet resultAdmin = statementAdmin.executeQuery();

            while (resultPerson.next() && resultAdmin.next()) {
                int tblAdminId = resultAdmin.getInt("admin_id");
                int tblAdminPersonId = resultAdmin.getInt("person_id");
                String id = resultAdmin.getString("id");

                int tblPersonId = resultPerson.getInt("person_id");
                String firstName = resultPerson.getString("firstName");
                String lastName = resultPerson.getString("lastName");
                String userName = resultPerson.getString("userName");
                String password = resultPerson.getString("password");

                Admin admin = new Admin();
                admin.setTblAdminId(tblAdminId);
                admin.setTblAdminPersonId(tblAdminPersonId);
                admin.setId(id);
                admin.setTblPersonId(tblPersonId);
                admin.setFirstName(firstName);
                admin.setLastName(lastName);
                admin.setUserName(userName);
                admin.setPassword(password);

                dbAdmins.add(admin);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return dbAdmins;
    }

    public static void createAdmin(Admin admin) {
        try(Connection connection = DBConnect.getConnection();
            PreparedStatement statementPerson = connection.prepareStatement(CREATE_USER);
            PreparedStatement statementAdmin = connection.prepareStatement(CREATE_ADMIN);)
        {
            //"INSERT INTO tbl_person(person_id, firstName, lastName, userName, password) VALUES (?, ?, ?, ?, ?);";
            //"INSERT INTO tbl_admin(admit_id, person_id, id) VALUES (?, ?, ?);";
            statementPerson.setInt(1, admin.getTblPersonId());
            statementPerson.setString(2, admin.getFirstName());
            statementPerson.setString(3, admin.getLastName());
            statementPerson.setString(4, admin.getUserName());
            statementPerson.setString(5, admin.getPassword());

            statementPerson.executeUpdate();

            statementAdmin.setInt(1, admin.getTblAdminId());
            statementAdmin.setInt(2, admin.getTblAdminPersonId());
            statementAdmin.setString(3, admin.getId());

            statementAdmin.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createStudent(Student student) {
        try(Connection connection = DBConnect.getConnection();
            PreparedStatement statementPerson = connection.prepareStatement(CREATE_USER);
            PreparedStatement statementStudent = connection.prepareStatement(CREATE_STUDENT);
            PreparedStatement statementAcademic = connection.prepareStatement(CREATE_ACADEMIC_EMPTY_ENROLL);)
        {
            //"INSERT INTO tbl_person(person_id, firstName, lastName, userName, password) VALUES (?, ?, ?, ?, ?);";
            //"INSERT INTO tbl_admin(admit_id, person_id, id) VALUES (?, ?, ?);";
            statementPerson.setInt(1, student.getTblPersonId());
            statementPerson.setString(2, student.getFirstName());
            statementPerson.setString(3, student.getLastName());
            statementPerson.setString(4, student.getUserName());
            statementPerson.setString(5, student.getPassword());
            statementPerson.executeUpdate();

            statementAcademic.setInt(1, student.getTblStudentAcademicId());
            statementAcademic.executeUpdate();

            statementStudent.setInt(1, student.getTblStudentId());
            statementStudent.setInt(2, student.getTblStudentPersonId());
            statementStudent.setInt(3, student.getTblStudentAcademicId());
            statementStudent.setString(4, student.getId());

            statementStudent.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }


    public static List<Student> getTblStudentData() {
        List<Student> dbStudents = new ArrayList<>();

        try(Connection connection = DBConnect.getConnection();
            PreparedStatement statementPerson = connection.prepareStatement(SELECT_USERS);
            PreparedStatement statementStudent = connection.prepareStatement(SELECT_STUDENTS);
            PreparedStatement statementAcademic = connection.prepareStatement(SELECT_ACADEMICS);)
        {
            ResultSet resultPerson = statementPerson.executeQuery();
            ResultSet resultStudent = statementStudent.executeQuery();
            ResultSet resultAcademic = statementAcademic.executeQuery();

            while (resultStudent.next()) {
                int tblStudentId = resultStudent.getInt("student_id");
                int tblStudentPersonId = resultStudent.getInt("person_id");
                int tblStudentAcademicId = resultStudent.getInt("academic_id");
                String roleId = resultStudent.getString("role_id");

                while (resultPerson.next()) {
                    int tblPersonId = resultPerson.getInt("person_id");

                    if (tblStudentPersonId == tblPersonId) {
                        String firstName = resultPerson.getString("firstName");
                        String lastName = resultPerson.getString("lastName");
                        String userName = resultPerson.getString("userName");
                        String password = resultPerson.getString("password");

                        while (resultAcademic.next()) {
                            int tblAcademicId = resultAcademic.getInt("academic_id");

                            if (tblStudentAcademicId == tblAcademicId) {
                                int course1Id = resultAcademic.getInt("course1_id");
                                int course2Id = resultAcademic.getInt("course2_id");
                                int course3Id = resultAcademic.getInt("course3_id");
                                int course4Id = resultAcademic.getInt("course4_id");
                                int course5Id = resultAcademic.getInt("course5_id");
                                int course6Id = resultAcademic.getInt("course6_id");

                                Student student = new Student();
                                student.setTblStudentId(tblStudentId);
                                student.setTblStudentPersonId(tblStudentPersonId);
                                student.setTblStudentAcademicId(tblStudentAcademicId);
                                student.setId(roleId);

                                student.setTblPersonId(tblPersonId);
                                student.setFirstName(firstName);
                                student.setLastName(lastName);
                                student.setUserName(userName);
                                student.setPassword(password);

                                student.setTblAcademicId(tblAcademicId);
                                student.setCourse1(course1Id);
                                student.setCourse2(course2Id);
                                student.setCourse3(course3Id);
                                student.setCourse4(course4Id);
                                student.setCourse5(course5Id);
                                student.setCourse6(course6Id);

                                dbStudents.add(student);
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dbStudents;
    }

}
