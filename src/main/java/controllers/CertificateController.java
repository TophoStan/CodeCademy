package controllers;

import domain.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import repository.DatabaseConnection;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CertificateController {
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


    private Controller controller = new Controller();
    private DatabaseConnection databaseConnection = new DatabaseConnection();

    public void toHome(ActionEvent event) {
        controller.toPage(event, "Home");
    }

    public void toStudent(ActionEvent event) {
        controller.toPage(event, "StudentAdd");
    }

    public void toCourse(ActionEvent event) {
        controller.toPage(event, "CourseAdd");
    }

    public void toEnrollment(ActionEvent event) {
        controller.toPage(event, "EnrollmentAdd");
    }

    public void toContentItem(ActionEvent event) {
        controller.toPage(event, "ContentItemAdd");
    }

    public void toCertificate(ActionEvent event) {
        controller.toPage(event, "CertificateAdd");
    }

    public void toEdit(ActionEvent event) {
        controller.toPage(event, "CertificateEdit");
    }

    public void toDelete(ActionEvent event) {
        controller.toPage(event, "CertificateDelete");
    }


    public HashMap<Student, ArrayList<Course>> getCompletedCoursesByStudent() {
        databaseConnection.connect();
        HashMap<Student, ArrayList<Course>> studentsPassedCourses = new HashMap<>();
        HashMap<Student, ArrayList<Course>> test = new HashMap<>();
        try {
            ArrayList<Course> passedCourses = new ArrayList<>();

            /*for (Map.Entry<Course, ArrayList<ContentItem>> e : contentItemsWithCourse().entrySet()) {
                //Loops through ContentItems Of the Course of the hashmap
                int passedContentItems = 0;
                for (Progress progress : databaseConnection.retrieveProgress()) {
                    if (progress.getPercentage() == 100) {
                        for (ContentItem contentItem : e.getValue()) {
                            if (contentItem.getContentItemId() == progress.getContentItemId()) {
                                passedContentItems += 1;
                            }
                        }

                        if (passedContentItems == e.getValue().size() && passedContentItems > 0) {
                            passedCourses.add(e.getKey());
                            studentsPassedCourses.put((Student) controller.giveIdentifierReturnObject(progress.getStudentId(), "Student"), passedCourses);
                            passedContentItems = 0;
                            break;
                        }
                    }
                }
            }*/
            /*for (Student student : databaseConnection.retrieveStudents()) {

                for(Progress progress : databaseConnection.retrieveProgress()){

                    progress.correctObjects();
                    if(progress.getStudentId() == student.getId() && progress.getPercentage() == 100){
                        for (Map.Entry<Course, ArrayList<ContentItem>> e : contentItemsWithCourse().entrySet()) {

                            int passedContentItems = 0;
                            for (ContentItem contentItem: e.getValue()) {
                                System.out.println(contentItem.getTitle() + " : " + progress.getContentItem().getTitle() + "\n");
                                if(contentItem.getContentItemId() == progress.getContentItemId()){
                                    passedContentItems++;
                                }
                            }
                            if (passedContentItems == e.getValue().size() && passedContentItems > 0) {
                                passedCourses.add(e.getKey());
                                test.put((Student) controller.giveIdentifierReturnObject(progress.getStudentId(), "Student"), passedCourses);

                                break;
                            }

                        }
                    }
                }
            }*/
            for (Student student: databaseConnection.retrieveStudents()) {
                for (Map.Entry<Course, ArrayList<ContentItem>> e : contentItemsWithCourse().entrySet()) {

                    for (Progress progress: databaseConnection.retrieveProgress()) {
                        progress.correctObjects(controller);
                        if(progress.getPercentage() == 100 && student.getId() == progress.getStudentId()){
                            int passedContentItems = 0;
                            for (ContentItem contentItem: e.getValue()) {

                                if(contentItem.getContentItemId() == progress.getContentItemId()){
                                System.out.println(contentItem.getTitle() + " : " + progress.getContentItem().getTitle() + "\n");
                                    passedContentItems++;
                                }
                            }
                            if (passedContentItems == e.getValue().size() && passedContentItems > 0) {
                                passedCourses.add(e.getKey());
                                test.put((Student) controller.giveIdentifierReturnObject(progress.getStudentId(), "Student"), passedCourses);
                                break;
                            }
                        }
                    }

                }
            }

            System.out.println( test + "test");

        } catch (Exception e) {
            System.out.println(e);
        }
        return test;
    }

    public HashMap<Student, ArrayList<Course>> returnCompletedCoursesFromStudent(String studentEmailAddress) {
        HashMap<Student, ArrayList<Course>> studentsPassedCourses = getCompletedCoursesByStudent();

        HashMap<Student, ArrayList<Course>> selectedStudentsPassedCourses = new HashMap<>();
        ArrayList<Course> coursesFromStudent = new ArrayList<>();
        try {
            Student student = new Student();
            for (Map.Entry<Student, ArrayList<Course>> e : studentsPassedCourses.entrySet()) {
                if (e.getKey().getEmailAddress().equals(studentEmailAddress)) {

                    student = e.getKey();
                    cbCompleted.getItems().clear();
                    for (Course course : e.getValue()) {
                        cbCompleted.getItems().add(course.getName());
                        coursesFromStudent.add(course);
                    }
                    isVisible(true);
                    break;
                }
            }
            if (student.getName() == null) {
                tfEmail.setText("No finished courses");
            }
            selectedStudentsPassedCourses.put(student, coursesFromStudent);
        } catch (Exception e) {
            System.out.println(e);
        }
        return selectedStudentsPassedCourses;
    }

    public void addDatesTocbDate() {
        String email = tfEmail.getText();
        if (controller.checkEmail(email)) {
            HashMap<Student, ArrayList<Course>> map = returnCompletedCoursesFromStudent(email);

            cbDate.getItems().clear();
            try {
                for (Enrollment enrollment : databaseConnection.retrieveEnrollments()) {
                    for (Map.Entry<Student, ArrayList<Course>> e : map.entrySet()) {
                        if (enrollment.getStudentId() == e.getKey().getId()) {
                            for (Course course : e.getValue()) {
                                if (course.getId() == enrollment.getCourseId()) {
                                    cbDate.getItems().add(enrollment.getEnrollmentDate().toString());
                                }
                            }
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            tfEmail.setText("Unknown email");
        }
    }

    public void isVisible(boolean bool) {
        cbCompleted.setVisible(bool);
        cbDate.setVisible(bool);
        lbDate.setVisible(bool);
        if (lbEmployee != null) {
            cbEmployee.setVisible(bool);
            lbEmployee.setVisible(bool);
        }
        lbCompleted.setVisible(bool);
        btnSubmit.setVisible(bool);
        lbGrade.setVisible(bool);
        tfGrade.setVisible(bool);
    }

    public HashMap<Course, ArrayList<ContentItem>> contentItemsWithCourse() {
        HashMap<Course, ArrayList<ContentItem>> map = new HashMap<>();
        for (Course course : databaseConnection.retrieveCourses()) {
            ArrayList<ContentItem> contentItems = new ArrayList<>();
            for (ContentItem contentItem : databaseConnection.retrieveContentItems()) {
                if (course.getId() == contentItem.getCourseId()) {
                    contentItems.add(contentItem);
                }
            }
            map.put(course, contentItems);
        }
        return map;
    }

    public void addEmployeesToCombobox() {
        cbEmployee.getItems().clear();
        for (Employee employee : databaseConnection.retrieveEmployee()) {
            cbEmployee.getItems().add(employee.getName() + "-" + employee.getEmployeeId());
        }
    }

    public void addCertificate() {
        databaseConnection.connect();
        Certificate certificate = new Certificate();
        try {
            String[] splitter = cbEmployee.getValue().toString().split("-");
            certificate.setEmployeeId(Integer.parseInt(splitter[1]));
            certificate.setGrade(Integer.parseInt(tfGrade.getText()));
            certificate.setEnrollmentId(returnEnrollmentId());
            databaseConnection.addCertificate(certificate);
            controller.clear(anchorPane);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

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

    public void getCertificates() {
        databaseConnection.connect();
        listCertificates.getItems().clear();

        try {
            for (Certificate certificate : databaseConnection.retrieveCertificates()) {
                Enrollment thisEnrollment = (Enrollment) controller.giveIdentifierReturnObject(certificate.getEnrollmentId(), "Enrollment");
                Course thisCourse = (Course) controller.giveIdentifierReturnObject(thisEnrollment.getCourseId(), "Course");
                Student thisStudent = (Student) controller.giveIdentifierReturnObject(thisEnrollment.getStudentId(), "Student");
                listCertificates.getItems().add(thisCourse.getName() + " by: " + thisStudent.getName() + " | " + certificate.getGrade());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteCertificate() {

        try {
            for (Certificate certificate : databaseConnection.retrieveCertificates()) {
                Enrollment thisEnrollment = (Enrollment) controller.giveIdentifierReturnObject(certificate.getEnrollmentId(), "Enrollment");
                Course thisCourse = (Course) controller.giveIdentifierReturnObject(thisEnrollment.getCourseId(), "Course");
                Student thisStudent = (Student) controller.giveIdentifierReturnObject(thisEnrollment.getStudentId(), "Student");
                listCertificates.getItems().add(thisCourse.getName() + " by: " + thisStudent.getName() + " | " + certificate.getGrade());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void editCertificate() {
        databaseConnection.connect();
        int id = returnEnrollmentId();
        Certificate certificate = (Certificate) controller.giveIdentifierReturnObject(id, "Certificate");
        certificate.setGrade(Integer.parseInt(tfGrade.getText()));
        databaseConnection.editCertificate(certificate);
        getCertificates();
        controller.clear(anchorPane);
        isVisible(false);
    }
}
