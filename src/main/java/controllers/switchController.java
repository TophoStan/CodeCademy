package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class switchController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void toHome(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(".StudentAdd.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("hier");
            e.printStackTrace();
        }
    }
    public void toStudent(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(".Home.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
