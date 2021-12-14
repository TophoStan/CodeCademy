package Gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    
    public void start(Stage window) {
        
        VBox test = new VBox();


        // view and window set
        Scene view = new Scene(test, 700, 500);

        window.setTitle("Thomas Quartel, Mickel de Coo, Stijn Spanjers en Stan Tophoven");
        window.setScene(view);
        window.show();
    }
}
