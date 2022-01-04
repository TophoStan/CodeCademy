package controllers;

import domain.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import repository.DatabaseConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CertificateController {

    @FXML private TextField tfEmail;
    @FXML private ComboBox cbCompleted;
    @FXML private ComboBox cbEmployee;
    @FXML private ComboBox cbDate;
    @FXML private Label lbDate;
    @FXML private Label lbEmployee;
    @FXML private Label lbCompleted;


    private Controller controller = new Controller();
    private DatabaseConnection databaseConnection = new DatabaseConnection();

    public void toHome(ActionEvent event) {
        controller.toPage(event, "Home");
    }

    public void toStudent(ActionEvent event){
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
        controller.toPage(event, "CertificateAdd");
    }
    public void toDelete(ActionEvent event) {
        controller.toPage(event, "CertificateAdd");
    }
    public void toAdd(ActionEvent event) {
        controller.toPage(event, "CertificateAdd");
    }





    public HashMap<Student, ArrayList<Course>> getCompletedCoursesByStudent(){
        databaseConnection.connect();
        HashMap<Student, ArrayList<Course>> studentsPassedCourses = new HashMap<>();
        try {
            ArrayList<Course> passedCourses = new ArrayList<>();
            //Loops through all progresses
            for (Progress progress: databaseConnection.retrieveProgress()) {
                if(progress.getPercentage() == 100){
                //Loops through Courses of hashmap
                    int passedContentItems = 0;
                    for (Map.Entry<Course, ArrayList<ContentItem>> e: contentItemsWithCourse().entrySet()) {
                        //Loops through ContentItems Of the Course of the hashmap
                        for (ContentItem contentItem: e.getValue()) {
                            if(contentItem.getContentItemId() == progress.getContentItemId()){
                                passedContentItems+= 1;
                            }
                        }
                        if(passedContentItems == e.getValue().size()){
                            passedCourses.add(e.getKey());
                            studentsPassedCourses.put((Student) controller.giveIdentifierReturnObject(progress.getStudentId(), "Student"), passedCourses);
                        }
                    }
                }
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return studentsPassedCourses;
    }

    public HashMap<Student, ArrayList<Course>> returnCompletedCoursesFromStudent(String studentEmailAddress) {
        HashMap<Student, ArrayList<Course>> studentsPassedCourses = getCompletedCoursesByStudent();
        HashMap<Student, ArrayList<Course>> selectedStudentsPassedCourses = new HashMap<>();
        ArrayList<Course> coursesFromStudent = new ArrayList<>();
        try {
                Student student = new Student();
                for (Map.Entry<Student, ArrayList<Course>> e:studentsPassedCourses.entrySet()) {
                    if(e.getKey().getEmailAddress() == studentEmailAddress){
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
                selectedStudentsPassedCourses.put(student, coursesFromStudent);
        } catch (Exception e) {
            System.out.println(e);
        }
        return  selectedStudentsPassedCourses;
    }

    public void addDatesTocbDate() {
        HashMap<Student, ArrayList<Course>> map = returnCompletedCoursesFromStudent(tfEmail.getText());
        cbDate.getItems().clear();
        try {
            for (Enrollment enrollment : databaseConnection.retrieveEnrollments()) {
                for (Map.Entry<Student, ArrayList<Course>> e: map.entrySet()) {
                    if(enrollment.getStudentId() == e.getKey().getId()){
                        for (Course course: e.getValue()) {
                            if(course.getId() == enrollment.getCourseId()){
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
    }

    public void isVisible(boolean bool) {
        cbCompleted.setVisible(bool);
        cbEmployee.setVisible(bool);
        cbDate.setVisible(bool);
        lbDate.setVisible(bool);
        lbEmployee.setVisible(bool);
        lbCompleted.setVisible(bool);
    }

    public HashMap<Course, ArrayList<ContentItem>> contentItemsWithCourse(){
        HashMap<Course, ArrayList<ContentItem>> map = new HashMap<>();
        for (Course course : databaseConnection.retrieveCourses()) {
            ArrayList<ContentItem> contentItems = new ArrayList<>();
            for (ContentItem contentItem : databaseConnection.retrieveContentItems()) {
                if(course.getId() == contentItem.getCourseId()){
                    contentItems.add(contentItem);
                }
            }
            map.put(course,contentItems);
        }
        return map;
    }
}
