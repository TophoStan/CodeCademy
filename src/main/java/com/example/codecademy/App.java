package com.example.codecademy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        try {
            //Parent root = FXMLLoader.load(getClass().getResource("test.fxml"));
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/codecademy/StudentAdd.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 100, 100);
            stage.setTitle("test");
            stage.setScene(scene);
            stage.show();

        }catch (Exception e){
            System.out.println(e);

        }

    }

    public static void main(String[] args) {
        launch();
    }
}
