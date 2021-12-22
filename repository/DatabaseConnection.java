package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;

import domain.Certificate;
import domain.Course;
import domain.Difficulty;
import domain.Employee;
import domain.Enrollment;
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

    /**
     * Constructor for the <code>DatabaseConnection</code> class. Initializes
     * <code>Connection</code> object to null.
     * 
     */

    public DatabaseConnection() {
        this.conn = null;
    }

    /**
     * Connects to the SQL Server Database using <code>DriverManager</code>.
     * 
     */
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
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }

    public Connection getConn() {
        return conn;
    }

    /**
     * This method uses the <code>Statement</code> and <code>execute()</code> to
     * push the data to the database with a number of
     * <code>setString(x, student.getMethod);</code>
     * 
     * @requires conn != null && student != null;
     * @throws NullPointerException student == null;
     * @throws SQLException         conn == null;
     * @return True || False
     * @param student
     */

    public boolean addStudentToDatabase(Student student) throws SQLException, NullPointerException {
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

    /**
     * This function retrieves all the records from the Student table on the SQL
     * server.
     * The method works by using <code>Statement </code> and <code>Resultset </code>
     * to execute the query.
     * 
     * @requires conn != null
     * @return ArrayList<Student>
     * @throws SQLException
     */
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

    /**
     * Deletes a student from the databse if the Student table contains the
     * EmailAddress of the given <code>student</code> in the parameters
     * 
     * @requires student != null && conn != null
     * @param student
     * @return boolean
     * @throws SQLException         conn == null
     * @throws NullPointerException student == null
     */
    public boolean deleteStudentFromDatabase(Student student) throws SQLException, NullPointerException {
        boolean wasSuccesful = false;
        connect();
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

    /**
     * Edits the student's information except for the EmailAddress.
     * After this method has been executed the changes will take affect immeadiatly.
     * 
     * @Requires student != null && conn != null
     * @param student
     * @return boolean
     * @throws SQLException         conn == null
     * @throws NullPointerException student == null
     */
    public boolean editStudentInformation(Student student) {
        boolean wasSuccesful = false;
        connect();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE Student " + "SET Name ='"
                    + student.getName() + "', Gender ='" + student.getGender() + "', Birthdate = '"
                    + student.getBirthDate() + "', Street = '" + student.getStreet() + "', Housenumber = "
                    + student.getHouseNumber() + ", PostalCode = '" + student.getPostalCode() + "', City = '"
                    + student.getCity() + "', Country = '" + student.getCountry() + "' WHERE EmailAddress = " + "'"
                    + student.getEmailAddress() + "'");

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Something went wrong with updating the student");
            System.out.println(e);
        }

        return wasSuccesful;
    }

    public void addCourseToDatabase(Course course) {
        String query = "INSERT INTO Course(CourseName, Subject, IntroductoryText, Difficulty) VALUES(?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, course.getName());
            stmt.setString(2, course.getSubject());
            stmt.setString(3, course.getText());
            stmt.setString(4, course.getDifficulty().toString());

            stmt.execute();
            System.out.println("Succesfully added course to database");

        } catch (Exception e) {
            System.out.println("Was not able to add course to database");
            System.out.println(e);

        }
    }

    public ArrayList<Course> retrieveCourses() {
        ArrayList<Course> courses = new ArrayList<>();
        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Course");
            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("courseid"));
                course.setDifficulty(Difficulty.valueOf((rs.getString("difficulty").toUpperCase())));
                course.setSubject(rs.getString("Subject"));
                course.setName(rs.getString("name"));
                course.setText(rs.getString("IntroductoryText"));
                courses.add(course);

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return courses;

    }

    public void editCourseInformation(Course course) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE Course SET Name = '" + course.getName()
                    + "', Difficulty= '" + course.getDifficulty().toString() + "', Subject= '" + course.getSubject()
                    + "', IntroductoryText ='" + course.getText() + "'");
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteCourse(Course course) {
        try {
            PreparedStatement preparedStatement = conn
                    .prepareStatement("DELETE FROM Course WHERE id=" + course.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addEnrollment(Enrollment enrollment, Student student, Course course) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO EnrollmentData(StudentId, CourseId, EnrollmentDate) VALUES (?,?,?)");
            preparedStatement.setInt(1, student.getId());
            preparedStatement.setInt(2, course.getId());
            Date date = new Date(0);

            preparedStatement.setDate(3, Date.valueOf(date.toLocalDate()));

            preparedStatement.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ArrayList<Enrollment> retrieveEnrollments() {
        ArrayList<Enrollment> enrollments = new ArrayList<>();
        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM EnrollmentData");

            while (rs.next()) {
                Enrollment enrollment = new Enrollment();
                enrollment.setEnrollmentId(rs.getInt("enrollmentId"));
                enrollment.setCourseId(rs.getInt("courseId"));
                enrollment.setStudentId(rs.getInt("studentId"));
                enrollment.setEnrollmentDate(rs.getDate("EnrollmentDate"));
                enrollments.add(enrollment);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return enrollments;
    }

    public void deleteEnrollment(Enrollment enrollment) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "DELETE FROM EnrollmentData WHERE EnrollmentId = " + enrollment.getEnrollmentId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addCertificate(Certificate certificate, Employee employee) {
        try {
            PreparedStatement preparedStatement = conn
                    .prepareStatement("INSERT INTO Certificate(Grade, EmployeeId) VALUES (?,?)");
            preparedStatement.setDouble(1, certificate.getGrade());
            preparedStatement.setInt(2, employee.getEmployeeId());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ArrayList<Certificate> retrieveCertificates() {
        ArrayList<Certificate> certificates = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Certificate");
            while (rs.next()) {
                Certificate certificate = new Certificate();
                certificate.setGrade(rs.getDouble("Grade"));
                certificate.setEnrollmentId(rs.getInt("EnrollmentId"));
                certificate.setEmployeeId(rs.getInt("EmployeeId"));
                certificates.add(certificate);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return certificates;
    }

    public void deleteCertificate(Certificate certificate) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "DELETE FROM Certificate WHERE EnrollmentId = " + certificate.getEnrollmentId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
