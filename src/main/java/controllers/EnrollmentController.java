package controllers;

import domain.Course;
import domain.Enrollment;
import domain.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import repository.DatabaseConnection;

import java.util.ArrayList;

public class EnrollmentController {

    private Controller controller = new Controller();

    @FXML private TextField tFEmailEnrollment;
    @FXML private ComboBox cbCourseEnrollment;

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

    public void addEnrollment(){
        databaseConnection.connect();
        Enrollment enrollment = new Enrollment();
        try {
            ArrayList<Student> students = databaseConnection.retrieveStudents();
            ArrayList<Course> courses = databaseConnection.retrieveCourses();
            for (Student student: students) {
                if(student.getEmailAddress().equals(tFEmailEnrollment.getText())){
                    enrollment.setStudent(student);
                }
            }
            for (Course course: courses) {
                if(course.getName().equals(cbCourseEnrollment.getValue())){
                    enrollment.setCourse(course);
                }
            }

        }catch (Exception e) {
            System.out.println(e);
        }
        databaseConnection.addEnrollment(enrollment);
        tFEmailEnrollment.clear();
        cbCourseEnrollment.getItems().clear();
    }

    public void addValuesToComboBox() {
        databaseConnection.connect();
        ArrayList<Course> courses = databaseConnection.retrieveCourses();
        cbCourseEnrollment.getItems().clear();
        for (Course course : courses) {
            cbCourseEnrollment.getItems().add(course.getName());
        }
    }

}
