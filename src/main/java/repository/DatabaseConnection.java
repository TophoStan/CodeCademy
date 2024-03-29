package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;

import domain.*;
import domain.Module;
/** Class to connect to database and execute queries*/
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
                this.conn = DriverManager.getConnection(dbURL, user, pass);
                System.out.println("Connected to database");
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
    public boolean deleteCourse(Course course) {
        try {
            PreparedStatement preparedStatement = conn
                    .prepareStatement("DELETE FROM Course WHERE CourseName='" + course.getName() + "'");
            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
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
     * This method uses the <code>Statement</code> and <code>execute()</code> to
     * push the Progress to the database.
     */
    public void addProgress(Progress progress) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO Progress(ContentItemID, StudentID, Percentage) VALUES (?,?,?)");
            preparedStatement.setInt(1, progress.getContentItemId());
            preparedStatement.setInt(2, progress.getStudentId());
            preparedStatement.setInt(3, progress.getPercentage());

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

    /**
     * Edits the enrollment's information.
     * After execution the changes will take effect immediately.
     */
    public void editEnrollment(Enrollment enrollment) throws SQLException {

        PreparedStatement preparedStatement = conn.prepareStatement("UPDATE EnrollmentData " +
                "SET studentId=" + enrollment.getStudentId() + ", CourseId=" + enrollment.getCourseId() + " ,EnrollmentDate ='" + enrollment.getEnrollmentDate()
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
     * Deletes a progress from the database if the course table contains the
     * courseId of the given <code>Progress</code> in the parameters
     */
    public void deleteProgress(Progress progress) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(
                "DELETE FROM Progress WHERE ProgressID = " + progress.getId());
        preparedStatement.executeUpdate();
    }

    /**
     * Uses the <code>Statement</code> and <code>execute()</code> to
     * push the Certificite to the database.
     */
    public void addCertificate(Certificate certificate) throws SQLException {
        PreparedStatement preparedStatement = conn
                .prepareStatement("INSERT INTO Certificate(Grade, EmployeeId, EnrollmentId) VALUES (?,?,?)");
        preparedStatement.setDouble(1, certificate.getGrade());
        preparedStatement.setInt(2, certificate.getEmployeeId());
        preparedStatement.setInt(3, certificate.getEnrollmentId());
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
                certificate.setGrade(rs.getInt("Grade"));
                certificate.setEnrollmentId(rs.getInt("EnrollmentId"));
                certificate.setEmployeeId(rs.getInt("EmployeeId"));
                certificates.add(certificate);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return certificates;
    }

    /**
     * Edits the certificate's information.
     * After execution the changes will take effect immediately.
     */
    public void editCertificate(Certificate certificate) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE Certificate SET Grade="
                    + certificate.getGrade() + ",EmployeeId=" + certificate.getEmployeeId() + " WHERE EnrollmentId = " + certificate.getEnrollmentId());
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

    /**
     * This method uses the <code>Statement</code> and <code>execute()</code> to
     * push the contentItem to the database
     */
    public void addContentItem(ContentItem content) {
        try {
            PreparedStatement preparedStatement;
            if (content.getCourseId() == 0) {
                preparedStatement = conn.prepareStatement(
                        "INSERT INTO ContentItem(PublicationDate,State,Title,Description) VALUES(?,?,?,?)");
            } else {
                preparedStatement = conn.prepareStatement(
                        "INSERT INTO ContentItem(PublicationDate,State,Title,Description,CourseId) VALUES(?,?,?,?,?)");
                preparedStatement.setInt(5, content.getCourseId());
            }
            preparedStatement.setDate(1, content.getPublicationDate());
            preparedStatement.setString(2, content.getStatus().toString());
            preparedStatement.setString(3, content.getTitle());
            preparedStatement.setString(4, content.getDescription());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Retrieves all the records from the ContentItem table on the SQL
     * server.
     * The method works by using <code>Statement </code> and <code>Resultset </code>
     * to execute the query.
     */
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
                contentItem.setStatus(Status.valueOf(rs.getString("State")));
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

    /**
     * Edits the ContentItem's information.
     * After execution the changes will take effect immediately.
     */
    public void editContentItem(ContentItem contentItem) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE ContentItem SET CourseId="
                    + contentItem.getCourseId() + " WHERE ContentItemId = " + contentItem.getContentItemId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {

        }
    }

    /**
     * This method uses the <code>Statement</code> and <code>execute()</code> to
     * push the Module to the database.
     */
    public void addModule(Module module) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO Module(ContentItemId,Version, ContactPersonId, TrackingNumber) VALUES(?,?,?,?)");
            preparedStatement.setInt(1, module.getContentItemId());
            preparedStatement.setString(2, module.getVersion());
            preparedStatement.setInt(3, module.getContactPersonId());
            preparedStatement.setInt(4, module.getCourseId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Retrieves all the records from the Modules table on the SQL
     * server.
     * The method works by using <code>Statement </code> and <code>Resultset </code>
     * to execute the query.
     */
    public ArrayList<Module> retrieveModules() {
        ArrayList<Module> modules = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Module");
            while (rs.next()) {
                Module module = new Module();
                module.setContactPersonId(rs.getInt("ContactPersonId"));
                module.setVersion(rs.getString("Version"));
                module.setContentItemId(rs.getInt("ContentItemId"));
                module.setTrackingNumber(rs.getInt("TrackingNumber"));

                modules.add(module);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return modules;

    }

    /**
     * This method uses the <code>Statement</code> and <code>execute()</code> to
     * push the Webcast to the database.
     */
    public void addWebcast(Webcast webcast) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO Webcast(ContentItemId,SpeakerId, Views, URL, Duration) VALUES(?,?,?,?,?)");
            preparedStatement.setInt(1, webcast.getContentItemId());
            preparedStatement.setInt(2, webcast.getSpeakerId());
            preparedStatement.setInt(3, webcast.getViews());
            preparedStatement.setString(4, webcast.getUrl());
            preparedStatement.setInt(5, webcast.getDuration());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Retrieves all the records from the Webcast table on the SQL
     * server.
     * The method works by using <code>Statement </code> and <code>Resultset </code>
     * to execute the query.
     */
    public ArrayList<Webcast> retrieveWebcasts() {
        ArrayList<Webcast> webcasts = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Webcast");
            while (rs.next()) {
                Webcast webcast = new Webcast();
                webcast.setContentItemId(rs.getInt("ContentItemId"));
                webcast.setSpeakerId(rs.getInt("SpeakerId"));
                webcast.setUrl(rs.getString("Url"));
                webcast.setViews(rs.getInt("Views"));
                webcast.setDuration(rs.getInt("Duration"));
                webcasts.add(webcast);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return webcasts;
    }

    /**
     * Retrieves the top 3 Webcasts based on views from the webcast table on the SQL
     * server.
     * The method works by using <code>Statement </code> and <code>Resultset </code>
     * to execute the query.
     */
    public ContentItem[] retrieveTop3Webcasts() {
        ContentItem[] webcasts = new ContentItem[3];
        int i = 0;

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT TOP 3 t2.ContentItemId, t2.Title\n" +
                    "FROM webcast AS t1\n" +
                    "JOIN ContentItem AS t2 ON t2.ContentItemID = t1.ContentItemId\n" +
                    "ORDER BY t1.views DESC");

            while (rs.next()) {
                ContentItem contentItem = new ContentItem() {
                };
                contentItem.setContentItemId(rs.getInt("ContentItemId"));
                contentItem.setTitle(rs.getString("Title"));

                webcasts[i] = contentItem;
                i++;
            }
        } catch (SQLException e) {
            System.out.println(e);
            ;
        }

        return webcasts;
    }

    /**
     * Retrieves the top 3 courses based on certificates achieved from the course table on the SQL
     * server.
     * The method works by using <code>Statement </code> and <code>Resultset </code>
     * to execute the query.
     */
    public Course[] retrieveTop3Courses() {
        Course[] courses = new Course[3];
        int i = 0;

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT TOP 3 Coursename, COUNT(T3.EnrollmentId) AS Certificates FROM Course AS T1\n" +
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
            System.out.println(e + " in database connection");

        }
        return courses;
    }

    /**
     * Retrieves all the records from the Speakers table on the SQL
     * server.
     * The method works by using <code>Statement </code> and <code>Resultset </code>
     * to execute the query.
     */
    public ArrayList<Speaker> retrieveSpeakers() {

        ArrayList<Speaker> speakers = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Speaker");
            while (rs.next()) {
                Speaker speaker = new Speaker();

                speaker.setName(rs.getString("SpeakerName"));
                speaker.setId(rs.getInt("SpeakerId"));
                speaker.setOrganisation(rs.getString("Organisation"));

                speakers.add(speaker);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return speakers;
    }

    /**
     * Retrieves all the records from the ContactPerson table on the SQL
     * server.
     * The method works by using <code>Statement </code> and <code>Resultset </code>
     * to execute the query.
     */
    public ArrayList<ContactPerson> retrieveContactPersons() {

        ArrayList<ContactPerson> contactPeople = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Contactperson");
            while (rs.next()) {
                ContactPerson contactPerson = new ContactPerson();
                contactPerson.setId(rs.getInt("ContactPersonId"));
                contactPerson.setName(rs.getString("Name"));
                contactPerson.setEmailAddress(rs.getString("EmailAddress"));

                contactPeople.add(contactPerson);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return contactPeople;
    }

    /**
     * Retrieves an arraylist of the certificates of the passed studentemail
     */
    public ArrayList<Integer> certificatesFromStudent(String email) {
        ArrayList<Integer> certificatesFromStudentList = new ArrayList<>();


        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Student AS t1\n" +
                    " INNER JOIN EnrollmentData AS t2 ON t1.StudentId = t2.StudentId\n" +
                    " INNER JOIN Certificate AS t3 ON t3.EnrollmentId = t2.EnrollmentId\n" +
                    "WHERE EmailAddress = '" + email + "'");


            while (rs.next()) {
                certificatesFromStudentList.add(rs.getInt("CourseId"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return certificatesFromStudentList;
    }

    /**
     * Returns the amount of students who finished a course
     */
    public int studentsFinishedCourse(String courseName) {
        int output = 0;

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Coursename, COUNT(DISTINCT t4.StudentId) AS Amount FROM Course AS T1\n" +
                    " JOIN EnrollmentData AS T2 ON t1.CourseID = t2.CourseId\n" +
                    " JOIN Certificate AS T3 ON t3.EnrollmentId = t2.EnrollmentId\n" +
                    " JOIN Student AS T4 ON t4.StudentId = t2.StudentId\n WHERE Coursename = '" + courseName + "'" +
                    " GROUP BY Coursename");

            while (rs.next()) {
                output = rs.getInt("Amount");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return output;
    }

    /**
     * Retrieves a hashmap with the contentItemId as the key and the average progress is its value of the passed course
     */
    public HashMap<Integer, Integer> getProgressForCourse(String courseName) {
        HashMap<Integer, Integer> progressForCourse = new HashMap<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(" SELECT t4.CourseID, t3.ContentItemId, AVG(t1.Percentage) AS average_progress FROM progress AS t1\n" +
                    "  JOIN ContentItem AS t2 ON t2.ContentItemId = t1.ContentItemID\n" +
                    "JOIN module AS t3 ON t3.ContentItemId = t2.ContentItemId\n" +
                    "JOIN course AS t4 ON t4.CourseID = t2.CourseId\n" +
                    "WHERE t4.CourseName = '" + courseName + "'\n" +
                    "GROUP BY t4.CourseID, t3.ContentItemId");
            while (rs.next()) {
                progressForCourse.put(rs.getInt("ContentItemId"), rs.getInt("average_progress"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return progressForCourse;
    }

    /**
     * Returns a hashmap with the contentItem as the index and the percentage of progress as the value for a specific student
     */
    public HashMap<ContentItem, Integer> getContentItemsWithPercent(int id) {
        HashMap<ContentItem, Integer> contentItemAndProgress = new HashMap<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT t1.StudentID, t2.Title, t1.Percentage\n" +
                    "FROM Progress AS t1\n" +
                    "JOIN ContentItem AS t2 ON t2.ContentItemID = t1.ContentItemID\n" +
                    "WHERE t1.StudentID = '" + id + "'");
            while (rs.next()) {
                ContentItem contentItem = new ContentItem() {
                };
                contentItem.setTitle(rs.getString("Title"));
                contentItemAndProgress.put(contentItem, rs.getInt("Percentage"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return contentItemAndProgress;
    }

    /**
     * Returns the enrollments of the specified gender
     */
    public ArrayList<Enrollment> getEnrollmentIDFromSpecificGenderStudents(String gender) {
        ArrayList<Enrollment> enrollmentsForSpecificGenderStudents = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT EnrollmentId, CourseId\n" +
                    "FROM EnrollmentData\n" +
                    "WHERE StudentId IN (SELECT StudentId\n" +
                    "FROM Student\n" +
                    "WHERE Gender = '" + gender + "')");
            while (rs.next()) {
                Enrollment enrollment = new Enrollment();
                enrollment.setEnrollmentId(rs.getInt("EnrollmentId"));
                enrollment.setCourseId(rs.getInt("CourseId"));

                enrollmentsForSpecificGenderStudents.add(enrollment);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return enrollmentsForSpecificGenderStudents;
    }

    /**
     * Retrieves all the records from the Progress table on the SQL
     * server.
     * The method works by using <code>Statement </code> and <code>Resultset </code>
     * to execute the query.
     */
    public ArrayList<Progress> retrieveProgress() {

        ArrayList<Progress> progresses = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Progress");
            while (rs.next()) {
                Progress progress = new Progress();
                progress.setContentItemId(rs.getInt("ContentItemId"));
                progress.setId(rs.getInt("ProgressId"));
                progress.setStudentId(rs.getInt("StudentId"));
                progress.setPercentage(rs.getInt("Percentage"));

                progresses.add(progress);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return progresses;

    }

    /**
     * Retrieves all the records from the Employee table on the SQL
     * server.
     * The method works by using <code>Statement </code> and <code>Resultset </code>
     * to execute the query.
     */
    public ArrayList<Employee> retrieveEmployee() {
        ArrayList<Employee> employees = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Employee");
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getInt("EmployeeId"));
                employee.setName(rs.getString("Name"));
                employees.add(employee);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return employees;
    }
}
