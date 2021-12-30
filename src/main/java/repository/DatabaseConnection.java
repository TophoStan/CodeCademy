package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;

import domain.Certificate;
import domain.ContentItem;
import domain.Course;
import domain.Difficulty;
import domain.Employee;
import domain.Enrollment;
import domain.Student;
import domain.Webcast;
import domain.Module;

/**
 * This program demonstrates how to establish database connection to Microsoft
 * SQL Server.
 *
 * @author www.codejava.net
 */

public class DatabaseConnection {
    private Connection conn;

    /**
     * Constructor for the <code>DatabaseConnection</code> class. Initializes
     * <code>Connection</code> object to null.
     */

    public DatabaseConnection() {
        this.conn = null;
    }

    /**
     * Connects to the SQL Server Database using <code>DriverManager</code>.
     */
    public void connect() {

        try {

            String dbURL = "jdbc:sqlserver://aei-sql2.avans.nl\\studenten:1443;databaseName=CodeCademy7";
            String user = "group7";
            String pass = "groepje7";

            if (conn == null) {
                System.out.println("Connecting to database");
                this.conn = DriverManager.getConnection(dbURL, user, pass);
                System.out.println("Connection successful");
            }

        } catch (SQLException ex) {
            System.out.println("Something went wrong");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }


    /**
     * This method uses the <code>Statement</code> and <code>execute()</code> to
     * push the data to the database with a number of
     * <code>setString(x, student.getMethod);</code>
     *
     * @param student
     * @return True || False
     * @throws NullPointerException student == null;
     * @throws SQLException         conn == null;
     * @requires conn != null && student != null;
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
            System.out.println("Successfully added student to database");

            return true;
        } catch (Exception e) {
            System.out.println("Was not able to add student to database");
            System.out.println(e);
        }
        return false;
    }

    /**
     * This function retrieves all the records from the Student table on the SQL
     * server.
     * The method works by using <code>Statement </code> and <code>Resultset </code>
     * to execute the query.
     *
     * @return ArrayList<Student>
     * @throws SQLException
     * @requires conn != null
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
     * Deletes a student from the database if the Student table contains the
     * EmailAddress of the given <code>student</code> in the parameters
     *
     * @param student
     * @return boolean
     * @throws SQLException         conn == null
     * @throws NullPointerException student == null
     * @requires student != null && conn != null
     */
    public boolean deleteStudentFromDatabase(Student student) throws SQLException, NullPointerException {
        boolean wasSuccesful = false;
        connect();
        try {
            PreparedStatement st = conn
                    .prepareStatement("DELETE FROM Student WHERE EmailAddress = '" + student.getEmailAddress() + "';");
            st.executeUpdate();
            wasSuccesful = true;
            System.out.println("Successfully deleted student from the database");
        } catch (Exception e) {
            System.out.println(e);
            wasSuccesful = false;
        }

        return wasSuccesful;
    }

    /**
     * Edits the student's information except for the EmailAddress.
     * After this method has been executed the changes will take affect
     * immediately.
     *
     * @param student
     * @return boolean
     * @throws SQLException         conn == null
     * @throws NullPointerException student == null
     * @Requires student != null && conn != null
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

    /**
     * This method uses the <code>Statement</code> and <code>execute()</code> to
     * push the course to the database with a number of
     * <code>setString(x, student.getMethod)</code>.
     *
     * @param course
     * @throws SQLException         conn == null
     * @throws NullPointerException course == null
     */
    public void addCourseToDatabase(Course course) {
        String query = "INSERT INTO Course(CourseName, Subject, IntroductoryText, Difficulty) VALUES(?,?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, course.getName());
            stmt.setString(2, course.getSubject());
            stmt.setString(3, course.getText());
            stmt.setString(4, course.getDifficulty().toString());

            stmt.execute();
            System.out.println("Successfully added course to database");

        } catch (Exception e) {
            System.out.println("Was not able to add course to database");
            System.out.println(e);

        }
    }

    /**
     * Retrieves all the records from the Student table on the SQL
     * server.
     * The method works by using <code>Statement </code> and <code>Resultset </code>
     * to execute the query.
     *
     * @return ArrayList < Course >
     * @throws SQLException conn == null
     * @requires conn != null
     */
    public ArrayList<Course> retrieveCourses() {
        ArrayList<Course> courses = new ArrayList<>();
        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Course");
            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("CourseId"));
                course.setSubject(rs.getString("Subject"));
                course.setName(rs.getString("CourseName"));
                course.setText(rs.getString("IntroductoryText"));
                course.setDifficulty(Difficulty.valueOf(rs.getString("Difficulty").trim()));
                courses.add(course);

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return courses;

    }

    /**
     * Edits the course's information.
     * After execution the changes will take effect immediately.
     *
     * @throws SQLException         conn == null
     * @throws NullPointerException course == null
     * @Requires course != null && conn != null
     */
    public void editCourseInformation(Course course) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE Course SET CourseName = '" + course.getName()
                    + "', Difficulty= '" + course.getDifficulty().toString() + "', Subject= '" + course.getSubject()
                    + "', IntroductoryText ='" + course.getText() + "' WHERE CourseId=" + course.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Deletes a course from the databse if the course table contains the
     * courseId of the given <code>course</code> in the parameters
     *
     * @param course
     * @return boolean
     * @throws SQLException         conn == null
     * @throws NullPointerException course == null
     * @requires course != null && conn != null
     */
    public void deleteCourse(Course course) {
        try {
            PreparedStatement preparedStatement = conn
                    .prepareStatement("DELETE FROM Course WHERE CourseName='" + course.getName() + "'");
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * This method uses the <code>Statement</code> and <code>execute()</code> to
     * push the Enrollment to the database with a number of
     * <code>setString(x, student.getMethod)</code>.
     *
     * @param enrollment
     * @throws SQLException         conn == null
     * @throws NullPointerException Enrollment == null
     */
    public void addEnrollment(Enrollment enrollment) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO EnrollmentData(StudentId, CourseId, EnrollmentDate) VALUES (?,?,?)");
            preparedStatement.setInt(1, enrollment.getStudentId());
            preparedStatement.setInt(2, enrollment.getCourseId());
            preparedStatement.setDate(3, Date.valueOf(java.time.LocalDate.now()));

            preparedStatement.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Retrieves all the records from the Enrollment table on the SQL
     * server.
     * The method works by using <code>Statement </code> and <code>Resultset </code>
     * to execute the query.
     *
     * @return <code>ArrayList <Enrollment></code>
     * @throws SQLException conn == null
     * @require conn != null
     */
    public ArrayList<Enrollment> retrieveEnrollments() throws SQLException {
        ArrayList<Enrollment> enrollments = new ArrayList<>();

        Statement stmt = this.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM EnrollmentData");
        ArrayList<Student> students = retrieveStudents();
        ArrayList<Course> courses = retrieveCourses();

        while (rs.next()) {
            Enrollment enrollment = new Enrollment();
            enrollment.setEnrollmentId(rs.getInt("enrollmentId"));
            enrollment.setCourseId(rs.getInt("courseId"));
            enrollment.setStudentId(rs.getInt("studentId"));
            enrollment.setEnrollmentDate(rs.getDate("EnrollmentDate"));
            for (Student student : students) {
                if (student.getId() == enrollment.getStudentId()) {
                    enrollment.setStudent(student);
                }
            }
            for (Course course : courses) {
                if (course.getId() == enrollment.getCourseId()) {
                    enrollment.setCourse(course);
                }
            }

            enrollments.add(enrollment);
        }

        return enrollments;
    }

    public void editEnrollment(Enrollment enrollment) throws SQLException {

        PreparedStatement preparedStatement = conn.prepareStatement("UPDATE EnrollmentData " +
                  "SET studentId=" + enrollment.getStudentId() + ", CourseId="+ enrollment.getCourseId() + " ,EnrollmentDate ='" + enrollment.getEnrollmentDate()
                + "' WHERE EnrollmentId =" + enrollment.getEnrollmentId());
        preparedStatement.executeUpdate();

    }

    /**
     * Deletes an Enrollment from the database if the course table contains the
     * courseId of the given <code>Enrollment</code> in the parameters
     *
     * @param enrollment
     * @return boolean
     * @throws SQLException         conn == null
     * @throws NullPointerException enrollment == null
     * @requires enrollment != null && conn != null
     */
    public void deleteEnrollment(Enrollment enrollment) throws SQLException {

        PreparedStatement preparedStatement = conn.prepareStatement(
                "DELETE FROM EnrollmentData WHERE EnrollmentId = " + enrollment.getEnrollmentId());
        preparedStatement.executeUpdate();
    }

    /**
     * Uses the <code>Statement</code> and <code>execute()</code> to
     * push the Certificite to the database with a number of
     * <code>setString(x, student.getMethod)</code>.
     *
     * @param certificate
     * @param employee
     * @throws SQLException         conn == null
     * @throws NullPointerException Certificate == null
     */
    public void addCertificate(Certificate certificate, Employee employee) throws SQLException {

        PreparedStatement preparedStatement = conn
                .prepareStatement("INSERT INTO Certificate(Grade, EmployeeId) VALUES (?,?)");
        preparedStatement.setDouble(1, certificate.getGrade());
        preparedStatement.setInt(2, employee.getEmployeeId());

        preparedStatement.executeUpdate();
    }

    /**
     * Retrieves all the records from the Enrollment table on the SQL
     * server.
     * The method works by using <code>Statement </code> and <code>Resultset </code>
     * to execute the query.
     *
     * @return <code>ArrayList <Enrollment></code>
     * @throws SQLException conn == null
     * @requires conn != null
     */
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

    public void editCertificate(Certificate certificate) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE Certificate SET Grade="
                    + certificate.getGrade() + " WHERE EnrollmentId = " + certificate.getEnrollmentId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Deletes a Certificate from the database if the course table contains the
     * courseId of the given <code>certificate</code> in the parameters
     *
     * @param certificate
     * @return boolean
     * @throws SQLException         conn == null
     * @throws NullPointerException certificate == null
     * @requires certificate != null && conn != null
     */
    public void deleteCertificate(Certificate certificate) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "DELETE FROM Certificate WHERE EnrollmentId = " + certificate.getEnrollmentId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addContentItem(ContentItem content, Course course) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO ContentItem(PublicationDate,State,Title,Description,CourseId) VALUES(?,?,?,?,?)");
            preparedStatement.setDate(1, content.getPublicationDate());
            preparedStatement.setString(2, content.getStatus());
            preparedStatement.setString(3, content.getTitle());
            preparedStatement.setString(4, content.getDescription());
            preparedStatement.setInt(5, course.getId());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ArrayList<ContentItem> retrieveContentItems() {
        ArrayList<ContentItem> contentItems = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ContentItem");
            while (rs.next()) {
                ContentItem contentItem = new ContentItem() {
                };
                contentItem.setDescription(rs.getString("Description"));
                contentItem.setPublicationDate(rs.getDate("PublicationDate"));
                contentItem.setStatus(rs.getString("Status"));
                contentItem.setSubject(rs.getString("Subject"));
                contentItem.setTitle(rs.getString("Title"));
                contentItem.setCourseId(rs.getInt("CourseId"));
                contentItem.setContentItemId(rs.getInt("ContentItemId"));
                contentItems.add(contentItem);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return contentItems;
    }

    public void addModule(Module module, ContentItem contentItem) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO Module(ContentItemId,Title,Version, ContactPersonId, TrackingNumber) VALUES(?,?,?,?,?)");
            preparedStatement.setInt(1, contentItem.getContentItemId());
            preparedStatement.setString(2, module.getTitle());
            preparedStatement.setString(3, module.getVersion());
            preparedStatement.setInt(4, module.getContactPersonId());
            preparedStatement.setInt(5, contentItem.getCourseId());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ArrayList<Module> retrieveModules() {
        ArrayList<Module> modules = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Module");
            while (rs.next()) {
                Module module = new Module();
                module.setContactPersonId(rs.getInt("ContactPersonId"));
                module.setTitle(rs.getString("Title"));
                module.setVersion(rs.getString("Version"));
                module.setContentItemId(rs.getInt("ContentItemId"));
                module.setTrackingNumber(rs.getInt("TrackingNumber"));

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return modules;

    }

    public void addWebcast(Webcast webcast, ContentItem contentItem) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO Webcast(ContentItemId,Title,SpeakerId, Views) VALUES(?,?,?,?)");
            preparedStatement.setInt(1, contentItem.getContentItemId());
            preparedStatement.setString(2, webcast.getTitle());
            preparedStatement.setInt(3, webcast.getSpeakerId());
            preparedStatement.setInt(3, webcast.getViews());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ArrayList<Webcast> retrieveWebcasts() {
        ArrayList<Webcast> webcasts = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Webcast");
            while (rs.next()) {
                Webcast webcast = new Webcast();
                webcast.setContentItemId(rs.getInt("ContentItemId"));
                webcast.setTitle(rs.getString("Title"));
                webcast.setSpeakerId(rs.getInt("SpeakerId"));
                webcast.setViews(rs.getInt("Views"));

                webcasts.add(webcast);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return webcasts;
    }

    public Webcast[] retrieveTop3Webcasts() {
        Webcast[] webcasts = new Webcast[3];
        int i = 0;

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs =  stmt.executeQuery("SELECT TOP 3 title, views FROM webcast\n" +
                    "ORDER BY Webcast.views DESC");
            while (rs.next()) {
                Webcast webcast = new Webcast();
                webcast.setTitle(rs.getString("Title"));
                webcast.setViews(rs.getInt("Views"));

                webcasts[i] = webcast;
                i++;
            }
        } catch (SQLException e) {
            System.out.println(e);;
        }

        return webcasts;
    }

    public Course[] retrieveTop3Courses() {
        Course[] courses = new Course[3];
        int i = 0;

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs =  stmt.executeQuery("SELECT TOP 3 Coursename, COUNT(T3.EnrollmentId) AS Certificates FROM Course AS T1\n" +
                    " JOIN EnrollmentData AS T2 ON t1.CourseID = t2.CourseId\n" +
                    " JOIN Certificate AS T3 ON t3.EnrollmentId = t2.EnrollmentId\n" +
                    " GROUP BY Coursename;");
            while (rs.next()) {
                Course course = new Course();
                course.setName(rs.getString("CourseName"));
                course.setCertificates(rs.getInt("Certificates"));

                courses[i] = course;
                i++;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return courses;
    }

}
