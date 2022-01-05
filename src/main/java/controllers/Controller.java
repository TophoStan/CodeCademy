package controllers;

import com.example.codecademy.App;
import domain.Certificate;
import domain.Course;
import domain.Enrollment;
import domain.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.DatabaseConnection;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private DatabaseConnection databaseConnection = new DatabaseConnection();

    public void toPage(ActionEvent event, String page) {
        try {
            root = FXMLLoader.load(App.class.getResource(page + ".fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Object giveIdentifierReturnObject(int id, String type) {
        databaseConnection.connect();
        try {

            if (type.equals("Student")) {
                for (Student student : databaseConnection.retrieveStudents()) {
                    if (student.getId() == id) {
                        return student;
                    }
                }
            } else if(type.equals("Enrollment")){
                for (Enrollment enrollment : databaseConnection.retrieveEnrollments()) {
                    if (enrollment.getEnrollmentId() == id) {
                        return enrollment;
                    }
                }
            } else if(type.equals("Course")){
                for (Course course: databaseConnection.retrieveCourses()) {
                    if (course.getId() == id) {
                        return course;
                    }
                }
            } else if(type.equals("Certificate")){
                for (Certificate certificate : databaseConnection.retrieveCertificates()) {
                    if (certificate.getEnrollmentId() == id) {
                        return certificate;
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

    public boolean checkEmail(String email){
        boolean output = false;
        try {
            databaseConnection.connect();
            ArrayList<Student> studentsFromDatabase = new ArrayList<>();
            studentsFromDatabase = databaseConnection.retrieveStudents();

            for (Student student : studentsFromDatabase) {
                if (email.equals(student.getEmailAddress())) {
                    output = true;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return output;
    }
}
