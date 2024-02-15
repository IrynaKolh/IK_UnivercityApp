package ikapp.database;

import ikapp.Admin;
import ikapp.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {

    private static final String SELECT_USERS = "SELECT * FROM tbl_person";
    private static final String SELECT_ADMINS = "SELECT * FROM tbl_admin";

    private static final String CREATE_USER =
            "INSERT INTO tbl_person(person_id, firstName, lastName, userName, password) VALUES (?, ?, ?, ?, ?);";
    private static final String CREATE_ADMIN =
            "INSERT INTO tbl_admin(admin_id, person_id, id) VALUES (?, ?, ?);";

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
                int personId = result.getInt("person_id");
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");
                String username = result.getString("userName");
                String password = result.getString("password");

                Person person = new Admin();
                person.setTblPersonId(personId);
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
}
