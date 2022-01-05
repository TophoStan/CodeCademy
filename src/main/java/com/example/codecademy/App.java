package com.example.codecademy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        try {
            //Parent root = FXMLLoader.load(getClass().getResource("test.fxml"));
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Home.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.getIcons().add(new Image("https://cdn.discordapp.com/attachments/920258969673547786/928312278913282168/E0rSNE5UcAMHbXP.jpg"));
            stage.setTitle("Thomas Quartel, Mickel de Coo, Stijn Spanjers en Stan Tophoven");
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
