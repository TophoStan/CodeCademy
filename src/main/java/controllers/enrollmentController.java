package controllers;

import com.example.codecademy.App;
import domain.Course;
import domain.Enrollment;
import domain.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import repository.DatabaseConnection;

import java.io.IOException;
import java.util.ArrayList;

public class enrollmentController {

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML private TextField tFEmailEnrollment;
    @FXML private ComboBox cbCourseEnrollment;
    private DatabaseConnection databaseConnection = new DatabaseConnection();

    public void toHome(ActionEvent event) {

    }

    public void toStudent(ActionEvent event) {

    }

    public void toCourse(ActionEvent event) {

    }

    public void toEnrollment(ActionEvent event) {

    }
    public void toContentItem(ActionEvent event) {

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
