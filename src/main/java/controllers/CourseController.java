package controllers;

import domain.Course;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import repository.DatabaseConnection;
import java.util.ArrayList;

public class CourseController {

    private Controller controller = new Controller();

    // for course add page
    @FXML ListView courseList;
    @FXML TextField tFCourseAddName;
    @FXML TextField tFCourseAddSubject;
    @FXML TextArea tACourseAddIntroText;
    @FXML ComboBox cBCourseAddDifficulty;

    DatabaseConnection databaseConnection = new DatabaseConnection();

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

    public void toCourseEdit(ActionEvent event) {
        controller.toPage(event, "CourseEdit");
    }

    public void toCourseDelete(ActionEvent event) {
        controller.toPage(event,  "CourseDelete");
    }

    public void showCourses() {
        databaseConnection.connect();
        try {
            ArrayList<Course> courses = new ArrayList<>();
            courses = databaseConnection.retrieveCourses();
            courseList.getItems().clear();
            for (Course course: courses) {
                this.courseList.getItems().add(course.getName());
            }
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public void addCourseToDatabase() {

    }

    public void showCourseEditPlace() {

    }
    public void deleteCourse() {

    }
}
