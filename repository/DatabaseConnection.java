package repository;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;

import domain.Student;

/**
 * This program demonstrates how to establish database connection to Microsoft
 * SQL Server.
 * 
 * @author www.codejava.net
 *
 */

public class DatabaseConnection {
    private Connection conn;

    public DatabaseConnection() {
        this.conn = null;
    }

    public void connect() {
        System.out.println("Connecting to database");

        try {

            String dbURL = "jdbc:sqlserver://aei-sql2.avans.nl\\studenten:1443;databaseName=CodeCademy7";
            String user = "group7";
            String pass = "groepje7";
            this.conn = DriverManager.getConnection(dbURL, user, pass);
            if (conn != null) {
                System.out.println("Connection succesfull");
            }

        } catch (SQLException ex) {
            System.out.println("Something went wrong");
            ex.printStackTrace();
        }

    }

    public void viewTable() {
        String query = "select EmployeeId, Name from Employee";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String employeeName = rs.getString("Name");
                int employeeID = rs.getInt("EmployeeId");

                System.out.println(employeeID + ", " + employeeName);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void addToToble(String table) {

        try {
            String query = "INSERT INTO " + table + " (Name)" + "Values (?)";
            PreparedStatement preparedStmt = conn.prepareStatement(query);

            preparedStmt.setString(1, "Stijn");

            preparedStmt.execute();
            System.out.println("Succesfully executed");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    /**
     * This method uses the <code>Statement</code> and <code>execute()</code>
     * 
     * @Requires <code>student</code> != null
     * @signals (NullPointerException) student == null;
     * @param student
     * 
     */
    public boolean addStudentToDatabase(Student student) {
        String query = "INSERT INTO Student(Emailaddress, Name, Gender, Birthdate, Street, HouseNumber, PostalCode, City, Country) VALUES(?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, student.getEmailAddress());
            stmt.setString(2, student.getName());
            stmt.setString(3, student.getGender());
            stmt.setDate(4, student.getBirthDate());
            stmt.setString(5, student.getStreet());
            stmt.setInt(6, student.getHouseNumber());
            stmt.setString(7, student.getPostalCode());
            stmt.setString(8, student.getCity());
            stmt.setString(9, student.getCountry());

            stmt.execute();
            System.out.println("Succesfully added student to database");

            return true;
        } catch (Exception e) {
            System.out.println("Was not able to add student to database");
            System.out.println(e);
            return false;
        }
    }

    public ArrayList<Student> retrieveStudents() throws SQLException {

        Statement stmt = this.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Student");
        ArrayList<Student> students = new ArrayList<>();
        while (rs.next()) {
            Student student = new Student();
            student.setId(rs.getInt("StudentId"));
            student.setName(rs.getString("Name"));
            student.setEmailAddress(rs.getString("EmailAddress"));
            student.setBirthDate(rs.getDate("Birthdate"));
            student.setGender(rs.getString("Gender"));
            student.setStreet(rs.getString("Street"));
            student.setHouseNumber(rs.getInt("HouseNumber"));
            student.setPostalCode(rs.getString("PostalCode"));
            student.setCity(rs.getString("City"));
            student.setCountry(rs.getString("Country"));
            students.add(student);
        }

        return students;
    }

    public boolean deleteStudentFromDatabase(Student student) {
        boolean wasSuccesful = false;

        try {
            PreparedStatement st = conn
                    .prepareStatement("DELETE FROM Student WHERE EmailAddress = '" + student.getEmailAddress() + "';");
            st.executeUpdate();
            wasSuccesful = true;
            System.out.println("Succesfully deleted student from the database");
        } catch (Exception e) {
            System.out.println(e);
            wasSuccesful = false;
        }

        return wasSuccesful;
    }

    public boolean editStudentInformation(Student student) {
        boolean wasSuccesful = false;

        return wasSuccesful;
    }

}
