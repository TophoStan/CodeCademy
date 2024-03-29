package controllers;

import domain.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import repository.DatabaseConnection;
import validation.Validator;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/** Controller of the Certificate views*/
public class CertificateController {
    @FXML private Label resultLabel;
    @FXML private AnchorPane anchorPane;
    @FXML private TextField tfEmail;
    @FXML private ComboBox cbCompleted;
    @FXML private ComboBox cbEmployee;
    @FXML private ComboBox cbDate;
    @FXML private Label lbDate;
    @FXML private Label lbEmployee;
    @FXML private Label lbCompleted;
    @FXML private Label lbGrade;
    @FXML private TextField tfGrade;
    @FXML private Button btnSubmit;
    @FXML private ListView listCertificates;


    private Validator validator = new Validator();
    private ArrayList<Node> nodes = new ArrayList<>();
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private Controller controller = new Controller(databaseConnection);

    public CertificateController() {}

    /**
     * This method uses the Controller to go to a page.
     */
    public void toHome(ActionEvent event) {
        controller.toPage(event, "Home");
    }
    /**
     * This method uses the Controller to go to a page.
     */
    public void toStudent(ActionEvent event) {
        controller.toPage(event, "StudentAdd");
    }
    /**
     * This method uses the Controller to go to a page.
     */
    public void toCourse(ActionEvent event) {
        controller.toPage(event, "CourseAdd");
    }
    /**
     * This method uses the Controller to go to a page.
     */
    public void toEnrollment(ActionEvent event) {
        controller.toPage(event, "EnrollmentAdd");
    }
    /**
     * This method uses the Controller to go to a page.
     */
    public void toContentItem(ActionEvent event) {
        controller.toPage(event, "ContentItemAdd");
    }
    /**
     * This method uses the Controller to go to a page.
     */
    public void toCertificate(ActionEvent event) {
        controller.toPage(event, "CertificateAdd");
    }
    /**
     * This method uses the Controller to go to a page.
     */
    public void toEdit(ActionEvent event) {
        controller.toPage(event, "CertificateEdit");
    }
    /**
     * This method uses the Controller to go to a page.
     */
    public void toDelete(ActionEvent event) {
        controller.toPage(event, "CertificateDelete");
    }

    /**
     * Shows the courses from a student who completed a course.
     * @param studentEmailAddress
     * @return Course ArrayList
     */
    public ArrayList<Course> returnCompletedCoursesFromStudent(String studentEmailAddress) {
        databaseConnection.connect();
        cbCompleted.getItems().clear();
        ArrayList<Course> completedCourses = new ArrayList<>();

        Student thisStudent = (Student) controller.giveIdentifierReturnObject(studentEmailAddress, "Student");
        for (Map.Entry<Course, ArrayList<ContentItem>> e : controller.contentItemsWithCourse().entrySet()) {
            int passedContentItems = 0;
            for (Progress progress : databaseConnection.retrieveProgress()) {
                if (progress.getStudentId() == thisStudent.getId() && progress.getPercentage() == 100) {

                    for (ContentItem contentItem : e.getValue()) {
                        if (contentItem.getContentItemId() == progress.getContentItemId()) {
                            passedContentItems++;
                        }
                    }
                    if (e.getValue().size() == passedContentItems && passedContentItems > 0) {
                        completedCourses.add(e.getKey());
                        cbCompleted.getItems().add(e.getKey());
                    }
                }
            }
        }

        if (completedCourses.isEmpty()) {
            tfEmail.setText("No completed courses");
            isVisible(false);
        } else {
            isVisible(true);
        }
        return completedCourses;
    }

    /**
     * This methode adds dates to the date ComboBox so the employee
     * can pick a date from the ComboBox
     */
    public void addDatesTocbDate() {
        String email = tfEmail.getText();
        if (controller.checkEmail(email)) {
            ArrayList<Course> courses = returnCompletedCoursesFromStudent(email);
            Student student = (Student) controller.giveIdentifierReturnObject(email, "Student");
            cbDate.getItems().clear();
            if (!courses.isEmpty()) {
                try {
                    for (Enrollment enrollment : databaseConnection.retrieveEnrollments()) {
                        for (Course course : courses) {
                            if (course.getId() == enrollment.getCourseId() && student.getId() == enrollment.getStudentId()) {
                                cbDate.getItems().add(enrollment.getEnrollmentDate());
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } else {
            tfEmail.setText("Unknown email");
        }
    }

    /**
     * Sets nodes on visible or on invisible.
     * @param bool
     */
    public void isVisible(boolean bool) {

        for (Node node: anchorPane.getChildren()) {
            if(!node.isVisible()){
                nodes.add(node);
            }
        }
        for (Node node: nodes) {
            node.setVisible(bool);
        }


    }

    /**
     * This methode adds the employees to a combobox.
     * The employee can set the employee who checked the certificate.
     */
    public void addEmployeesToCombobox() {
        cbEmployee.getItems().clear();
        for (Employee employee : databaseConnection.retrieveEmployee()) {
            cbEmployee.getItems().add(employee.getName() + "-" + employee.getEmployeeId());
        }
    }

    /**
     * This methode creates the certificate and passes is to the databaseConnection.
     * It also checks if the certificate already exists.
     */
    public void addCertificate() {
        databaseConnection.connect();
        Certificate certificate = new Certificate();
        try {
            String[] splitter = cbEmployee.getValue().toString().split("-");
            certificate.setEmployeeId(Integer.parseInt(splitter[1]));
            if (validator.isGradeValid(Double.parseDouble(tfGrade.getText()))) {
                certificate.setGrade(Double.parseDouble(tfGrade.getText()));
            }
            certificate.setEnrollmentId(returnEnrollmentId());
            databaseConnection.addCertificate(certificate);
            controller.clear(anchorPane);
            isVisible(false);
            getCertificates();
        } catch (Exception e) {
            System.out.println(e);
            tfEmail.setText("Certificate already exists");
            isVisible(false);
        }
    }

    /**
     * Returns the enrollment id of the student of the selected course & date
     * @return enrollmentId
     */
    public int returnEnrollmentId() {
        Enrollment enrollment = new Enrollment();
        try {
            enrollment.setEnrollmentDate(Date.valueOf(cbDate.getValue().toString()));
            for (Course course : databaseConnection.retrieveCourses()) {
                if (course.getName().equals(cbCompleted.getValue().toString())) {
                    enrollment.setCourse(course);
                    break;
                }
            }
            for (Student student : databaseConnection.retrieveStudents()) {
                if (student.getEmailAddress().equals(tfEmail.getText())) {
                    enrollment.setStudent(student);
                    break;
                }
            }
            for (Enrollment enrollmentFromDatabase : databaseConnection.retrieveEnrollments()) {
                if (enrollment.getCourseName().equals(enrollmentFromDatabase.getCourseName()) && enrollment.getStudentName().equals(enrollmentFromDatabase.getStudentName()) && enrollment.getEnrollmentDate().equals(enrollmentFromDatabase.getEnrollmentDate())) {
                    enrollment = enrollmentFromDatabase;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return enrollment.getEnrollmentId();
    }

    /**
     * Retrieves the certificates from all students and puts it
     * in a ListView.
     */
    public void getCertificates() {
        databaseConnection.connect();
        listCertificates.getItems().clear();
        try {
            for (Certificate certificate : databaseConnection.retrieveCertificates()) {
                Enrollment thisEnrollment = (Enrollment) controller.giveIdentifierReturnObject(certificate.getEnrollmentId(), "Enrollment");
                Course thisCourse = (Course) controller.giveIdentifierReturnObject(thisEnrollment.getCourseId(), "Course");
                Student thisStudent = (Student) controller.giveIdentifierReturnObject(thisEnrollment.getStudentId(), "Student");
                listCertificates.getItems().add(thisCourse.getName() + " by: " + thisStudent.getName() + " - " + certificate.getGrade());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Gives information to databaseConnection to delete a
     * specific certificate.
     */
    public void deleteCertificate() {
        Certificate certificate = new Certificate();
        try {
            certificate.setEnrollmentId(returnEnrollmentId());
            databaseConnection.deleteCertificate(certificate);
            controller.clear(anchorPane);
            isVisible(false);
            getCertificates();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * This methode gives information to databaseConnection to edit a
     * specific certificate.
     * It also gives an alert if something isn't right.
     */
    public void editCertificate() {
        databaseConnection.connect();
        int id = returnEnrollmentId();
        Certificate certificate = (Certificate) controller.giveIdentifierReturnObject(id, "Certificate");
        if (certificate == null) {
            resultLabel.setText("Add the certificate first before trying to edit the email ");
            tfEmail.clear();
            isVisible(false);
        } else {
            String[] splitter = cbEmployee.getValue().toString().split("-");
            certificate.setEmployeeId(Integer.parseInt(splitter[1]));
            double grade = Double.parseDouble(tfGrade.getText());

            if (validator.isGradeValid(Double.parseDouble(tfGrade.getText()))) {
                certificate.setGrade(Double.parseDouble(tfGrade.getText()));
                certificate.setGrade(grade);
                databaseConnection.editCertificate(certificate);
                getCertificates();
                controller.clear(anchorPane);
            } else {
                controller.clear(anchorPane);
                tfEmail.setText("No valid grade");
            }
            isVisible(false);
        }
        getCertificates();
    }
}
