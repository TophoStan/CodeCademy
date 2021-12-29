package controllers;

import com.example.codecademy.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class homeController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void toStudent(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(App.class.getResource("StudentAdd.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void toHome(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(App.class.getResource("Home.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public void toCourse(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(App.class.getResource("CourseAdd.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toEnrollment(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(App.class.getResource("EnrollmentAdd.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toContentItem(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(App.class.getResource("ContentItemAdd.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
