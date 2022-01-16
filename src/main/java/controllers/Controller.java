package controllers;

import com.example.codecademy.App;
import domain.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import repository.DatabaseConnection;

import java.io.IOException;
import java.util.ArrayList;
/** Universal controller*/
public class Controller {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private DatabaseConnection databaseConnection;
    public Controller(DatabaseConnection databaseConnection){
        this.databaseConnection = databaseConnection;
    }

    /**
     * Opens a page
     * @param event
     * @param page
     */
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

    /**
     * Get all the information from a specific object with an integer
     * @param id
     * @param type
     * @return an Object dependent on the type
     */
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
            } else if(type.equals("Employee")) {
                for (Employee employee : databaseConnection.retrieveEmployee()) {
                    if (employee.getEmployeeId() == id) {
                        return employee;
                    }
                }
            } else if(type.equals("Progress")) {
                for (Progress progress : databaseConnection.retrieveProgress()) {
                    if (progress.getId() == id) {
                        return progress;
                    }
                }
            } else if(type.equals("ContentItem")) {
                for (ContentItem contentItem : databaseConnection.retrieveContentItems()) {
                    if (contentItem.getContentItemId() == id) {
                        return contentItem;
                    }
                }
            } else if(type.equals("Speaker")) {
                for (Speaker speaker : databaseConnection.retrieveSpeakers()) {
                    if (speaker.getId() == id) {
                        return speaker;
                    }
                }
            } else if(type.equals("ContactPerson")) {
                for (ContactPerson contactPerson : databaseConnection.retrieveContactPersons()) {
                    if (contactPerson.getId() == id) {
                        return contactPerson;
                    }
                }
            } else if (type.equals("Webcast")) {
                for (Webcast webcast : databaseConnection.retrieveWebcasts()) {
                    if (webcast.getContentItemId() == id) {
                        return webcast;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * Get all the information from a specific object with a string
     * @param id
     * @param type
     * @return an Object dependent on the type
     */
    public Object giveIdentifierReturnObject(String id, String type) {
        databaseConnection.connect();
        try {
            if (type.equals("Student")) {
                for (Student student : databaseConnection.retrieveStudents()) {
                    if(student.getEmailAddress().equals(id)){
                        return student;
                    }
                }
            } else if (type.equals("Course")){
                for (Course course: databaseConnection.retrieveCourses()) {
                    if(course.getName().equals(id)){
                        return course;
                    }
                }
            } else if(type.equals("ContentItem")){
                for (ContentItem contentItem: databaseConnection.retrieveContentItems()) {
                    if(contentItem.getTitle().equals(id)){
                        return contentItem;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * Checks if an email exists
     * @param email
     * @return true or false
     */
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

    /**
     * Clear TextFields of TextArea's in an anchorPane
     * @param anchorPane
     */
    public void clear(AnchorPane anchorPane){
        for (Node node : anchorPane.getChildren()) {

            if (node instanceof TextField) {
                // clear
                ((TextField)node).clear();
            }
            if(node instanceof ComboBox){
                ((ComboBox)node).getItems().clear();
            }
        }

    }
}
