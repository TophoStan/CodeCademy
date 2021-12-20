package Gui;

import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import domain.Student;
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
import repository.DatabaseConnection;

public class App extends Application {

    private ArrayList<Student> st = new ArrayList<>();
    private DatabaseConnection dbcn = new DatabaseConnection();
    private Student stud = new Student();

    public void start(Stage window) {

        // Home layout
        VBox layoutHome = new VBox();
        layoutHome.setPadding(new Insets(0, 0, 0, 10));
        VBox layoutStudent = new VBox();
        layoutStudent.setPadding(new Insets(0, 0, 0, 10));

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

        // Edit, Add and delete Student buttons

        HBox editAddButtons = new HBox();
        Button addBtn = new Button("Add");
        addBtn.setDefaultButton(true);
        Button editBtn = new Button("Edit");
        Button delBtn = new Button("Delete");

        editAddButtons.setPadding(new Insets(0, 0, 0, 450));
        editAddButtons.getChildren().addAll(addBtn, editBtn, delBtn);

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

        Label webcastTitle = new Label("Test webcast ");
        webcastTitle.setFont(Font.font("Monospaced", 18));
        Button toCourseButton = new Button(">");

        webcastPlace.getChildren().addAll(webcastTitle, toCourseButton);
        webcastPlace.setStyle("-fx-padding: 10;" +
                "-fx-border-width: 1;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 15;" +
                "-fx-border-color: black;");

        webcastGrid.add(webcastPlace, 0, 0);

        // Certificates
        GridPane certificateGrid = new GridPane();

        // A webcast place
        HBox certiPlace = new HBox();

        Label certiTitle = new Label("Test certificate ");
        certiTitle.setFont(Font.font("Monospaced", 18));
        Button toCertiButton = new Button(">");

        certiPlace.getChildren().addAll(certiTitle, toCertiButton);
        certiPlace.setStyle("-fx-padding: 10;" +
                "-fx-border-width: 1;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 15;" +
                "-fx-border-color: black;");
        certificateGrid.add(certiPlace, 0, 0);

        // Set home content
        homeContent.getChildren().addAll(webcastsParagraf, webcastGrid, certificateParagraf, certificateGrid);

        // ------------------------------------------------------------------------------------------------------------------------------------

        // content student
        HBox studentContent = new HBox();

        // Add content -----------------------------
        // add places
        VBox studentAddPlaces = new VBox();
        studentAddPlaces.setPadding(new Insets(0, 150, 0, 20));

        Label studentTitleAdd = new Label("Student - add");
        studentTitleAdd.setFont(Font.font("Monospaced", 30));

        // form
        Label addName = new Label("Name");
        TextField addNameField = new TextField();

        Label addEmail = new Label("Email");
        TextField addEmailField = new TextField();

        Label addGender = new Label("Gender");
        TextField addGenderField = new TextField("M/W/O");

        Label addbirthDate = new Label("Birthdate");
        TextField addBirthDateField = new TextField("DD-MM-YYYY");

        Label addStreet = new Label("Street");
        TextField addStreetField = new TextField();

        Label addHouseNumber = new Label("Housenumber");
        TextField addHouseNumberField = new TextField();

        Label addPostalCode = new Label("Postalcode");
        TextField addPostalCodeField = new TextField();

        Label addCity = new Label("City");
        TextField addCityField = new TextField();

        Label addCountry = new Label("Country");
        TextField addCountryField = new TextField();

        Button submitButton = new Button("Submit");
        submitButton.setTranslateY(10);

        Label alert = new Label();
        alert.setStyle("-fx-text-fill: RED;" +
                "-fx-padding: 15;");

        studentAddPlaces.getChildren().addAll(studentTitleAdd, addName, addNameField, addEmail, addEmailField, addGender,
                addGenderField, addbirthDate, addBirthDateField, addStreet, addStreetField,
                addHouseNumber, addHouseNumberField, addPostalCode, addPostalCodeField, addCity, addCityField,
                addCountry, addCountryField, submitButton, alert);

        // student list
        VBox studentList = new VBox();
        studentList.setStyle("-fx-padding: 10;" +
                "-fx-border-width: 1;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 15;" +
                "-fx-border-color: black;");

        Label studentListTitle = new Label("Name list:");
        studentListTitle.setFont(Font.font("Monospaced", 18));

        studentList.getChildren().add(studentListTitle);

        DatabaseConnection database = new DatabaseConnection();
        database.connect();

        try {
            ArrayList<Student> students = database.retrieveStudents();
            for (Student name : students) {
                Label newStud = new Label(name.getName());
                studentList.getChildren().add(newStud);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        // your added content
        VBox yourAddedContent = new VBox();
        yourAddedContent.setStyle("-fx-padding: 10;" +
                "-fx-border-width: 1;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 15;" +
                "-fx-border-color: black;");

        Label yourAddedContentListTitle = new Label("Your newest info:");
        yourAddedContentListTitle.setFont(Font.font("Monospaced", 18));

        Label newestName = new Label();
        Label newestEmail = new Label();
        Label newestGender = new Label();
        Label newestBirthDate = new Label();
        Label newestStreet = new Label();
        Label newestHouseNumber = new Label();
        Label newestPostalCode = new Label();
        Label newestCity = new Label();
        Label newestCounty = new Label();

        yourAddedContent.getChildren().addAll(yourAddedContentListTitle, newestName, newestEmail, newestGender,
                newestBirthDate, newestStreet, newestHouseNumber, newestPostalCode, newestCity, newestCounty);

        // edit content ----------------------------------------
        VBox studentEditPlaces = new VBox();
        studentEditPlaces.setPadding(new Insets(0, 90, 0, 20));

        Label studentTitleEdit = new Label("Student - edit");
        studentTitleEdit.setFont(Font.font("Monospaced", 30));

        Label editInstruction = new Label("Your email");
        TextField editInput = new TextField();
        Button editGoBtn = new Button("Go");
        editGoBtn.setTranslateY(10);

        Label editAlert = new Label("");
        editAlert.setStyle("-fx-text-fill: RED;" +
        "-fx-padding: 15;");

        VBox infoFromEditInput = new VBox();
        infoFromEditInput.setStyle("-fx-padding: 10;" +
                "-fx-border-width: 1;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 15;" +
                "-fx-border-color: black;");

        Label infoFromEditInputListTitle = new Label("Your info:");
        infoFromEditInputListTitle.setFont(Font.font("Monospaced", 18));
        infoFromEditInput.setMaxHeight(80);

        Label dataName = new Label();
        Label dataEmail = new Label();
        Label dataGender = new Label();
        Label dataBirthDate = new Label();
        Label dataStreet = new Label();
        Label dataHouseNumber = new Label();
        Label dataPostalCode = new Label();
        Label dataCity = new Label();
        Label dataCounty = new Label();

        infoFromEditInput.getChildren().addAll(infoFromEditInputListTitle, dataName, dataEmail, dataGender,
        dataBirthDate, dataStreet, dataHouseNumber, dataPostalCode ,dataCity, dataCounty);

        studentEditPlaces.getChildren().addAll(studentTitleEdit, editInstruction, editInput, editGoBtn, editAlert);

        // actual edit place

        HBox actualEditPlace = new HBox();
        actualEditPlace.setPadding(new Insets(80, 0, 0, 0));

        Label editTitle = new Label("Edit here");
        editTitle.setFont(Font.font("Monospaced", 14));
        editTitle.setPadding(new Insets(4, 0, 0, 0));
        

        VBox firstRow = new VBox();
        firstRow.setPadding(new Insets(0, 20, 0, 0));

        Label editNameLabel = new Label("Name");
        TextField  editName = new TextField();

        Label editGenderLabel = new Label("Gender");
        TextField  editGender = new TextField("M/W/O");

        Label editBdateLabel = new Label("Birthdate");
        TextField  editBdate = new TextField("DD-MM-YYYY");

        Label editStreetLabel = new Label("Street");
        TextField  editStreet = new TextField();

        Button editSubmitBtn = new Button("Submit");
        editSubmitBtn.setTranslateY(10);

        firstRow.getChildren().addAll(editTitle, editNameLabel, editName, editGenderLabel, editGender, editBdateLabel, editBdate, editStreetLabel, editStreet ,editSubmitBtn);

        VBox secondRow = new VBox();
        secondRow.setPadding(new Insets(20, 0, 0, 0));

        Label editHousnumberLabel = new Label("Housenumber");
        TextField  editHouseNumber = new TextField();
        Label editPostalCodeLabel = new Label("Postal Code");
        TextField editPostalCode = new TextField();
        Label editCityLabel = new Label("City");
        TextField  editCity = new TextField();
        Label editCountryLabel = new Label("Country");
        TextField  editCountry = new TextField();
        
        secondRow.getChildren().addAll(editHousnumberLabel, editHouseNumber, editPostalCodeLabel, editPostalCode, editCityLabel, editCity, editCountryLabel, editCountry);


        actualEditPlace.getChildren().addAll(firstRow, secondRow);


        // delete content --------------------------------------




        // set total layout
        layoutHome.getChildren().addAll(title, menu, homeContent);

        // set home view
        Scene home = new Scene(layoutHome, 800, 600);

        // view set student
        Scene student = new Scene(layoutStudent, 800, 600);



        // actions
        addBirthDateField.setOnMouseClicked((event) -> {
            addBirthDateField.setText("");
        });

        addGender.setOnMouseClicked((event) -> {
            addGender.setText("");
        });

        addNameField.setOnKeyTyped((event) -> {
            alert.setText("Not saved yet!");
        });

        editGender.setOnMouseClicked((event) -> {
            editGender.setText("");
        });

        editBdate.setOnMouseClicked((event) -> {
            editBdate.setText("");
        });


        studentButton.setOnAction((event) -> {
            if (!studentButton.isDefaultButton()) {
                layoutStudent.getChildren().clear();
                menu.getChildren().add(editAddButtons);
                studentContent.getChildren().clear();
                studentContent.getChildren().addAll(studentAddPlaces, studentList, yourAddedContent);
                layoutStudent.getChildren().addAll(title, menu, studentContent);
                studentButton.setDefaultButton(true);
                homeButton.setDefaultButton(false);
                editBtn.setDefaultButton(false);
                delBtn.setDefaultButton(false);
                addBtn.setDefaultButton(true);
                window.setScene(student);
            }
        });

        homeButton.setOnAction((event) -> {
            layoutHome.getChildren().clear();
            menu.getChildren().clear();
            menu.getChildren().addAll(homeButton, coursesButton, studentButton);
            layoutHome.getChildren().addAll(title, menu, homeContent);
            homeButton.setDefaultButton(true);
            studentButton.setDefaultButton(false);
            addBirthDateField.setText("DD-MM-YYYY");
            addGenderField.setText("M/W/O");
            alert.setText("");
            window.setScene(home);
        });

        addBtn.setOnAction((event) -> {
            studentContent.getChildren().clear();
            editBtn.setDefaultButton(false);
            delBtn.setDefaultButton(false);
            studentContent.getChildren().addAll(studentAddPlaces, studentList, yourAddedContent);
            addBtn.setDefaultButton(true);
        });

        editBtn.setOnAction((event) -> {
            studentContent.getChildren().clear();
            addBtn.setDefaultButton(false);
            delBtn.setDefaultButton(false);
            editBdate.setText("DD-MM-YYYY");
            editGender.setText("M/W/O");
            actualEditPlace.getChildren().clear();
            studentContent.getChildren().add(studentEditPlaces);
            editBtn.setDefaultButton(true);
        });

        delBtn.setOnAction((event) -> {
            studentContent.getChildren().clear();
            addBtn.setDefaultButton(false);
            editBtn.setDefaultButton(false);
            studentContent.getChildren().addAll();
            delBtn.setDefaultButton(true);
        });

        editGoBtn.setOnAction((event) -> {
            studentContent.getChildren().clear();
            studentContent.getChildren().add(studentEditPlaces);
            String emailAddress = editInput.getText();
            ArrayList<Student> studInfo = new ArrayList<>();
            try {
                studInfo = database.retrieveStudents();
                Thread.sleep(90);
                
                for (Student i : studInfo) {
                    if (i.getEmailAddress().equals(emailAddress)) {
                        dataName.setText("Name: " + i.getName());
                        dataEmail.setText("Mail: "+ i.getEmailAddress());
                        dataGender.setText("Gender: " + i.getGender());
                        dataBirthDate.setText("Birthday: " + String.valueOf(i.getBirthDate()));
                        dataStreet.setText("Street: " + i.getStreet());
                        dataHouseNumber.setText("Housenumber: " + String.valueOf(i.getHouseNumber()));
                        dataPostalCode.setText("Postal code: " + i.getPostalCode());
                        dataCity.setText("City: " + i.getCity());
                        dataCounty.setText("Country: " + i.getCountry());
                        studentContent.getChildren().add(infoFromEditInput);
                        editAlert.setText("");
                        actualEditPlace.getChildren().clear();
                        actualEditPlace.getChildren().addAll(firstRow, secondRow);
                        studentEditPlaces.getChildren().add(actualEditPlace);
                    }
                }

                if (!dataEmail.getText().equals("Mail: " + emailAddress)) {
                    editAlert.setText("No falid Email");
                }

            } catch (Exception e) {
                System.out.println(e);
            } 
        });

        editSubmitBtn.setOnAction((event) -> {
            String email = editInput.getText();
            String newName = editName.getText();
            String gender = editGender.getText();
            String birthDate = editBdate.getText();
            String street = editStreet.getText();
            int houseNumber = Integer.parseInt(editHouseNumber.getText());
            String postalCode = editPostalCode.getText();
            String city = editCity.getText();
            String country = editCountry.getText();

            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

            String[] splits = birthDate.split("-");
            int[] intDate = new int[3];
            for (int i = 0; i < 3; i++) {
                intDate[i] = Integer.parseInt(splits[i]);
            }

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, intDate[2]);
            cal.set(Calendar.MONTH, intDate[1] - 1); // <-- months start
            // at 0.
            cal.set(Calendar.DAY_OF_MONTH, intDate[0]);

            java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());
            

            ArrayList<Student> editStudent = new ArrayList<>();
            try {
                editStudent = database.retrieveStudents();
                Thread.sleep(90);
                
                for (Student i : editStudent) {
                    if (i.getEmailAddress().equals(email)) {
                        i.setName(newName);
                        i.setGender(gender);
                        i.setBirthDate(date);
                        i.setStreet(street);
                        i.setHouseNumber(houseNumber);
                        i.setPostalCode(postalCode);
                        i.setCity(city);
                        i.setCountry(country);
                        
                        dbcn.editStudentInformation(i);
                        alert.setText("Done");
                    }
                }

            } catch (Exception e) {
                System.out.println(e);
            }

            System.out.println(editStudent);
        });

        submitButton.setOnAction((event) -> {
            // add to database
            String emailAddress = addEmailField.getText();
            String newName = addNameField.getText();
            String gender = addGenderField.getText();
            String birthDate = addBirthDateField.getText();
            String street = addStreetField.getText();
            int houseNumber = Integer.parseInt(addHouseNumberField.getText());
            String postalCode = addPostalCodeField.getText();
            String city = addCityField.getText();
            String country = addCountryField.getText();

            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

            String[] splits = birthDate.split("-");
            int[] intDate = new int[3];
            for (int i = 0; i < 3; i++) {
                intDate[i] = Integer.parseInt(splits[i]);
            }

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, intDate[2]);
            cal.set(Calendar.MONTH, intDate[1] - 1); // <-- months start
            // at 0.
            cal.set(Calendar.DAY_OF_MONTH, intDate[0]);

            java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());
            System.out.println(sdf.format(date));

            Student newStudent = new Student(emailAddress, newName, gender, date, street, houseNumber, postalCode,
                    city, country);

            System.out.println(newStudent);

            try {
                database.addStudentToDatabase(newStudent);
            } catch (NullPointerException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            alert.setStyle("-fx-text-fill: GREEN;" + "-fx-padding: 15;");
            alert.setText("Succesfull added!");

            try {
                Thread.sleep(90);
                studentList.getChildren().clear();
                studentList.getChildren().add(studentListTitle);
                ArrayList<Student> students = database.retrieveStudents();
                for (Student name : students) {
                    Label newStud = new Label("- " + name.getName());
                    studentList.getChildren().add(newStud);
                }
            } catch (Exception e) {
                System.out.println(e);
            }

            if (gender.equals("M")) {
                gender += "an";
            } else if (gender.equals("W")) {
                gender += "omen";
            } else {
                gender = "Other";
            }

            newestName.setText("Name: " + newName);
            newestEmail.setText("Email: " + emailAddress);
            newestGender.setText("Gender: " + gender);
            newestBirthDate.setText("Birthdate: " + birthDate);
            newestStreet.setText("Street: " + street);
            newestHouseNumber.setText("Housenumber: " + String.valueOf(houseNumber));
            newestPostalCode.setText("Postal code: " + postalCode);
            newestCity.setText("City: " + city);
            newestCounty.setText("Country: " + country);

            addNameField.clear();
            addEmailField.clear();
            addBirthDateField.clear();
            addCityField.clear();
            addGenderField.clear();
            addHouseNumberField.clear();
            addPostalCodeField.clear();
            addCountryField.clear();
            addPostalCodeField.clear();
            addStreetField.clear();

        });

        // window set
        window.setTitle("Thomas Quartel, Mickel de Coo, Stijn Spanjers en Stan Tophoven");
        window.setScene(home);
        window.show();
    }
}
