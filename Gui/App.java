package Gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {

    public void start(Stage window) {

        // Home layout
        VBox layoutHome = new VBox();
        VBox layoutStudent = new VBox();

        // Title
        Label title = new Label("Codecademy");
        title.setFont(Font.font("Monospaced", 40));

        // Menu
        HBox menu = new HBox();
        Button homeButton = new Button("Home");
        homeButton.setDefaultButton(true);
        Button coursesButton = new Button("Courses");
        Button studentButton = new Button("Student");

        menu.getTypeSelector();

        menu.setPadding(new Insets(10));
        menu.getChildren().addAll(homeButton, coursesButton, studentButton);   

        // Content Home
        VBox homeContent = new VBox();

        // Different Paragrafs
        Label webcastsParagraf = new Label("Best watched webcasts");
        webcastsParagraf.setFont(Font.font("Monospaced", 20));

        Label certificateParagraf = new Label("Most certificates");
        certificateParagraf.setFont(Font.font("Monospaced", 20));

        // Webcasts
        GridPane webcastGrid = new GridPane();

        // A webcast place
        HBox webcastPlace = new HBox();

        Label webcastTitle = new Label("Test webcast");
        webcastTitle.setFont(Font.font("Monospaced", 13));
        Button toCourseButton = new Button("Watch");

        webcastPlace.getChildren().addAll(webcastTitle, toCourseButton);
        webcastGrid.add(webcastPlace, 0, 0);

        // Certificates
        GridPane certificateGrid = new GridPane();

        // A webcast place
        HBox certiPlace = new HBox();

        Label certiTitle = new Label("Test certificate");
        certiTitle.setFont(Font.font("Monospaced", 13));
        Button toCertiButton = new Button("Start");

        certiPlace.getChildren().addAll(certiTitle, toCertiButton);
        certificateGrid.add(certiPlace, 0, 0);

        // Set home content
        homeContent.getChildren().addAll(webcastsParagraf, webcastGrid, certificateParagraf, certificateGrid);

        
        

        //------------------------------------------------------------------------------------------------------------------------------------
        
        // content student
        HBox studentContent = new HBox();
        
        // add places
        VBox studentAddPlaces = new VBox();
        studentAddPlaces.setPadding(new Insets(0, 150, 0, 20));

        Label studentTitle = new Label("Student");
        studentTitle.setFont(Font.font("Monospaced", 30));

        // form
        Label addName = new Label("Name");
        TextField addNameField = new TextField();
        
        Label addEmail = new Label("Email");
        TextField addEmailField = new TextField();
        
        studentAddPlaces.getChildren().addAll(studentTitle, addName, addNameField, addEmail, addEmailField);
        
        // student list
        VBox studentList = new VBox();
        studentList.setStyle( "-fx-padding: 10;" +
        "-fx-border-width: 1;" +
        "-fx-border-insets: 5;" + 
            "-fx-border-radius: 15;" + 
            "-fx-border-color: black;");
            
            Label studentListTitle = new Label("List:");
        studentListTitle.setFont(Font.font("Monospaced", 18));
        Label testStudent = new Label("Stan Tophoven");
        Label testStudent2 = new Label("Thomas Quartel");
        Label testStudent3 = new Label("Stijn Spanjers");
        Label testStudent4 = new Label("Mickel de Coo");
        Label testStudent5 = new Label("Stijn Spanjers");
        Label testStudent6 = new Label("Mickel de Coo");

        studentList.getChildren().addAll(studentListTitle, testStudent, testStudent2, testStudent3, testStudent4, testStudent6, testStudent5);
        
        // set student content
        studentContent.getChildren().addAll(studentAddPlaces, studentList);
        
        // set total layout
        layoutHome.getChildren().addAll(title, menu, homeContent);
        
        // set home view
        Scene home = new Scene(layoutHome, 700, 500);
        
        // view set student
        Scene student = new Scene(layoutStudent, 700, 500);
        
        // actions
        studentButton.setOnAction((event) -> {
            layoutStudent.getChildren().clear();
            layoutStudent.getChildren().addAll(title, menu, studentContent);
            studentButton.setDefaultButton(true);
            homeButton.setDefaultButton(false);
            window.setScene(student);
        });

        homeButton.setOnAction((event) -> {
            layoutHome.getChildren().clear();
            layoutHome.getChildren().addAll(title, menu, homeContent);
            homeButton.setDefaultButton(true);
            studentButton.setDefaultButton(false);
            window.setScene(home);
        });
        
        // window set
        window.setTitle("Thomas Quartel, Mickel de Coo, Stijn Spanjers en Stan Tophoven");
        window.setScene(home);
        window.show();
    }
}
