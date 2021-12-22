package Gui;

import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.sql.SQLException;

import domain.Course;
import domain.Difficulty;
import domain.Student;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import repository.DatabaseConnection;

public class App extends Application {

    private DatabaseConnection dbcn = new DatabaseConnection();
    private ArrayList<Button> menuButtons = new ArrayList<>();
    private Difficulty dif;

    public void start(Stage window) {

        // Different layouts
        VBox layoutHome = new VBox();
        layoutHome.setPadding(new Insets(0, 0, 0, 10));
        VBox layoutStudent = new VBox();
        layoutStudent.setPadding(new Insets(0, 0, 0, 10));
        VBox layoutCourse = new VBox();
        layoutCourse.setPadding(new Insets(0, 0, 0, 10));

        // Title from application
        Label title = new Label("Codecademy");
        title.setFont(Font.font("Monospaced", 40));

        // Standard menu (shown in home)
        HBox menu = new HBox();
        Button homeButton = new Button("Home");
        homeButton.setDefaultButton(true);
        Button coursesButton = new Button("Courses");
        Button studentButton = new Button("Student");
        menuButtons.add(homeButton);
        menuButtons.add(coursesButton);
        menuButtons.add(studentButton);

        menu.setPadding(new Insets(10));
        menu.getChildren().addAll(homeButton, coursesButton, studentButton);

        // Edit, Add and Delete Student buttons
        // add button is selected default
        HBox editAddButtons = new HBox();
        Button addBtn = new Button("Add");
        addBtn.setDefaultButton(true);
        Button editBtn = new Button("Edit");
        Button delBtn = new Button("Delete");

        // added to the standard menu and placed on the right side
        editAddButtons.setPadding(new Insets(0, 0, 0, 450));
        editAddButtons.getChildren().addAll(addBtn, editBtn, delBtn);

        // -----------------------------------------------------------------------------------------------
        // Home page

        VBox homeContent = new VBox();

        // Different Paragrafs
        Label webcastsParagraf = new Label("Most watched webcasts");
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
        // Student page

        // Content student
        HBox studentContent = new HBox();

        // Add content-------------------------------------------------
        VBox studentAddPlaces = new VBox();
        studentAddPlaces.setPadding(new Insets(0, 150, 0, 20));

        Label studentTitleAdd = new Label("Student - add");
        studentTitleAdd.setFont(Font.font("Monospaced", 30));

        // Add form
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

        studentAddPlaces.getChildren().addAll(studentTitleAdd, addName, addNameField, addEmail, addEmailField,
                addGender, addGenderField, addbirthDate, addBirthDateField, addStreet, addStreetField,
                addHouseNumber, addHouseNumberField, addPostalCode, addPostalCodeField, addCity, addCityField,
                addCountry, addCountryField, submitButton, alert);

        // List of student names
        VBox studentList = new VBox();
        studentList.setStyle("-fx-padding: 10;" +
                "-fx-border-width: 1;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 15;" +
                "-fx-border-color: black;");

        Label studentListTitle = new Label("Name list:");
        studentListTitle.setFont(Font.font("Monospaced", 18));

        getStudentList(studentList, studentListTitle);

        // the newest added content
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

        // Edit content ---------------------------------------------
        VBox studentEditPlaces = new VBox();
        studentEditPlaces.setPadding(new Insets(0, 90, 0, 20));

        Label studentTitleEdit = new Label("Student - edit");
        studentTitleEdit.setFont(Font.font("Monospaced", 30));

        // Place to find content for the student
        Label editInstruction = new Label("Your email");
        TextField editInput = new TextField();
        Button editGoBtn = new Button("Go");
        editGoBtn.setTranslateY(10);

        Label editAlert = new Label("");
        editAlert.setStyle("-fx-text-fill: RED;" +
                "-fx-padding: 15;");

        // Content from database for the specific student
        VBox infoFromEditInput = new VBox();
        infoFromEditInput.setStyle("-fx-padding: 10;" +
                "-fx-border-width: 1;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 15;" +
                "-fx-border-color: black;");
        infoFromEditInput.setMaxHeight(80);

        Label infoFromEditInputListTitle = new Label("Your info:");
        infoFromEditInputListTitle.setFont(Font.font("Monospaced", 18));

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
                dataBirthDate, dataStreet, dataHouseNumber, dataPostalCode, dataCity, dataCounty);

        studentEditPlaces.getChildren().addAll(studentTitleEdit, editInstruction, editInput, editGoBtn, editAlert);

        // Actual edit place-----
        // The student will edit their content here

        HBox actualEditPlace = new HBox();
        actualEditPlace.setPadding(new Insets(80, 0, 0, 0));

        Label editTitle = new Label("Edit here");
        editTitle.setFont(Font.font("Monospaced", 14));
        editTitle.setPadding(new Insets(4, 0, 0, 0));

        // Input fields are split in a first and second row for a compact view
        VBox firstRow = new VBox();
        firstRow.setPadding(new Insets(0, 20, 0, 0));

        Label editNameLabel = new Label("Name");
        TextField editName = new TextField();

        Label editGenderLabel = new Label("Gender");
        TextField editGender = new TextField("M/W/O");

        Label editBdateLabel = new Label("Birthdate");
        TextField editBdate = new TextField("DD-MM-YYYY");

        Label editStreetLabel = new Label("Street");
        TextField editStreet = new TextField();

        Button editSubmitBtn = new Button("Submit");
        editSubmitBtn.setTranslateY(10);

        firstRow.getChildren().addAll(editTitle, editNameLabel, editName, editGenderLabel, editGender, editBdateLabel,
                editBdate, editStreetLabel, editStreet, editSubmitBtn);

        // Second row will be created here
        VBox secondRow = new VBox();
        secondRow.setPadding(new Insets(20, 0, 0, 0));

        Label editHousnumberLabel = new Label("Housenumber");
        TextField editHouseNumber = new TextField();
        Label editPostalCodeLabel = new Label("Postal Code");
        TextField editPostalCode = new TextField();
        Label editCityLabel = new Label("City");
        TextField editCity = new TextField();
        Label editCountryLabel = new Label("Country");
        TextField editCountry = new TextField();

        secondRow.getChildren().addAll(editHousnumberLabel, editHouseNumber, editPostalCodeLabel, editPostalCode,
                editCityLabel, editCity, editCountryLabel, editCountry);

        // Combines the rows together
        actualEditPlace.getChildren().addAll(firstRow, secondRow);

        // Delete content ----------------------------------------
        VBox studentDelPlaces = new VBox();
        studentDelPlaces.setPadding(new Insets(0, 90, 0, 20));

        Label studentTitleDel = new Label("Student - delete");
        studentTitleDel.setFont(Font.font("Monospaced", 30));

        // Student types his or her email here
        Label delInstruction = new Label("Your email");
        TextField delInput = new TextField();
        Button delGoBtn = new Button("Go");
        delGoBtn.setTranslateY(10);

        Label delAlert = new Label("");
        delAlert.setStyle("-fx-text-fill: RED;" +
                "-fx-padding: 15;");

        studentDelPlaces.getChildren().addAll(studentTitleDel, delInstruction, delInput, delGoBtn, delAlert);

        // ---------------------------------------------------------------------------------------------------

        // Course page
        HBox courseContent = new HBox();

        HBox courseEditAddBtns = new HBox();
        Button courseAddBtn = new Button("Add");
        courseAddBtn.setDefaultButton(true);
        Button courseEditBtn = new Button("Edit");
        Button courseDelBtn = new Button("Delete");

        courseEditAddBtns.setPadding(new Insets(0, 0, 0, 450));
        courseEditAddBtns.getChildren().addAll(courseAddBtn, courseEditBtn, courseDelBtn);

        // Course add page---------------------
        VBox addPlaceCourses = new VBox();
        addPlaceCourses.setPadding(new Insets(0, 150, 0, 20));

        Label coursesTitle = new Label("Courses - add");
        coursesTitle.setFont(Font.font("Monospaced", 30));

        Label nameInstruction = new Label("Name");
        TextField nameInput = new TextField();

        Label subjectInstruction = new Label("Subject");
        TextField subjectInput = new TextField();

        Label instructionText = new Label("Introduction text");
        TextArea instructionTextInput = new TextArea("");
        instructionTextInput.setMaxWidth(280);

        Label difficultyInstruction = new Label("Difficulty");
        String[] difficulties = { dif.EASY.toString(), dif.NORMAL.toString(), dif.HARD.toString(),
                dif.EXPERT.toString() };
        ComboBox difficultyDropdown = new ComboBox(FXCollections.observableArrayList(difficulties));

        Label moduleInstruction = new Label("Add a module");
        String[] modules = { "Test1", "Test2" };
        ComboBox moduleDropdown = new ComboBox(FXCollections.observableArrayList(modules));

        Button courseSubmitButton = new Button("Submit");
        courseSubmitButton.setTranslateY(10);

        Label courseSubmitAlert = new Label("");
        courseSubmitAlert.setStyle("-fx-text-fill: RED;" +
                "-fx-padding: 15;");

        addPlaceCourses.getChildren().addAll(coursesTitle, nameInstruction, nameInput, subjectInstruction, subjectInput,
                instructionText, instructionTextInput, difficultyInstruction, difficultyDropdown, moduleInstruction,
                moduleDropdown, courseSubmitButton, courseSubmitAlert);

        VBox courseList = new VBox();
        courseList.setStyle("-fx-padding: 10;" +
                "-fx-border-width: 1;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 15;" +
                "-fx-border-color: black;");

        Label courseListTitle = new Label("Course list:");
        courseListTitle.setFont(Font.font("Monospaced", 18));

        // Course edit page -----------------------
        VBox editPlaceCourse = new VBox();
        editPlaceCourse.setPadding(new Insets(0, 150, 0, 20));

        Label coursesEditTitle = new Label("Courses - edit");
        coursesEditTitle.setFont(Font.font("Monospaced", 30));

        Label courseName = new Label("Course name");
        TextField courseNameEditInput = new TextField();
        Button goCourseEditBtn = new Button("Go");
        goCourseEditBtn.setTranslateY(10);

        Label goCourseEditAlert = new Label("");
        goCourseEditAlert.setStyle("-fx-text-fill: RED;" +
                "-fx-padding: 15;");

        editPlaceCourse.getChildren().addAll(coursesEditTitle, courseName, courseNameEditInput, goCourseEditBtn,
                goCourseEditAlert);

        // actual edit page----
        HBox actualCourseEditPlace = new HBox();
        actualEditPlace.setPadding(new Insets(80, 0, 0, 0));

        // Input fields are split in a first and second row for a compact view
        VBox firstCourseRow = new VBox();
        firstCourseRow.setPadding(new Insets(0, 20, 0, 0));

        Label editCourseNameLabel = new Label("Name");
        TextField editCourseName = new TextField();

        Label editSubjectLabel = new Label("Subject");
        TextField editSubject = new TextField();

        Button editCourseSubmitBtn = new Button("Submit");
        editCourseSubmitBtn.setTranslateY(10);

        firstCourseRow.getChildren().addAll(editTitle, editCourseNameLabel, editCourseName, editSubjectLabel, editSubject, editCourseSubmitBtn);

        // Second row will be created here
        VBox secondCourseRow = new VBox();
        secondCourseRow.setPadding(new Insets(20, 0, 0, 0));

        Label editCourseInstructionLabel = new Label("Instruction");
        TextArea editCourseInstruction = new TextArea();

        secondCourseRow.getChildren().addAll(editCourseInstructionLabel, editCourseInstruction);

        // Combines the rows together
        actualCourseEditPlace.getChildren().addAll(firstCourseRow, secondCourseRow);


        // Set first layout when starting the application
        layoutHome.getChildren().addAll(title, menu, homeContent);

        // Sets scenes for home, courses and student
        Scene home = new Scene(layoutHome, 800, 600);
        Scene student = new Scene(layoutStudent, 800, 600);
        Scene course = new Scene(layoutCourse, 800, 600);

        // All actions below --------------------------------------------------------

        // removes text when pressing the field
        addBirthDateField.setOnMouseClicked((event) -> {
            addBirthDateField.setText("");
        });

        addGender.setOnMouseClicked((event) -> {
            addGender.setText("");
        });

        editBdate.setOnMouseClicked((event) -> {
            editBdate.setText("");
        });

        editGender.setOnMouseClicked((event) -> {
            editGender.setText("");
        });

        // gives warning the user haven't saved his changes
        addNameField.setOnKeyTyped((event) -> {
            alert.setText("Not saved yet!");
            alert.setStyle("-fx-text-fill: RED;" +
                    "-fx-padding: 15;");
        });

        // action on student button
        studentButton.setOnAction((event) -> {

            // only when you are NOT on the student page yet
            if (!studentButton.isDefaultButton()) {
                // adds the edit add and delete buttons
                menu.getChildren().clear();
                menu.getChildren().addAll(homeButton, coursesButton, studentButton, editAddButtons);

                // clears student content and fills it with the good content
                studentContent.getChildren().clear();
                studentContent.getChildren().addAll(studentAddPlaces, studentList, yourAddedContent);

                // clears the student layour and fills it with the good layout
                layoutStudent.getChildren().clear();
                layoutStudent.getChildren().addAll(title, menu, studentContent);

                // loads student names from database
                getStudentList(studentList, studentListTitle);

                // sets text for bdate, gender and alert back to normal text
                addBirthDateField.setText("DD-MM-YYYY");
                addGenderField.setText("M/W/O");
                alert.setText("");

                // sets default button good according to the page
                defaultButtonToFalse();
                studentButton.setDefaultButton(true);

                addBtn.setDefaultButton(true);
                editBtn.setDefaultButton(false);
                delBtn.setDefaultButton(false);

                // finally sets window to student
                window.setScene(student);
            }
        });

        // action on home button
        homeButton.setOnAction((event) -> {

            // clears the menu and fills the right buttons
            menu.getChildren().clear();
            menu.getChildren().addAll(homeButton, coursesButton, studentButton);

            // clears the layout and fills it with the good layout
            layoutHome.getChildren().clear();
            layoutHome.getChildren().addAll(title, menu, homeContent);

            // sets default buttons good according to the page
            defaultButtonToFalse();
            homeButton.setDefaultButton(true);

            // finally sets window to home
            window.setScene(home);
        });

        // action on course button
        coursesButton.setOnAction((event) -> {
            // clears the menu and fills the right buttons
            menu.getChildren().clear();
            menu.getChildren().addAll(homeButton, coursesButton, studentButton, courseEditAddBtns);

            // set add page as default
            courseContent.getChildren().clear();
            getCourseList(courseList, courseListTitle);
            courseContent.getChildren().addAll(addPlaceCourses, courseList);

            // clears the layout and fills it with the good layout
            layoutCourse.getChildren().clear();
            layoutCourse.getChildren().addAll(title, menu, courseContent);

            // sets default buttons good according to the page
            defaultButtonToFalse();
            coursesButton.setDefaultButton(true);
            courseAddBtn.setDefaultButton(true);
            courseEditBtn.setDefaultButton(false);
            courseDelBtn.setDefaultButton(false);

            // clears fields
            clearCourseFields(nameInput, subjectInput, instructionTextInput, difficultyDropdown);
            courseSubmitAlert.setText("");
            courseSubmitAlert.setStyle("-fx-text-fill: RED;" +
                    "-fx-padding: 15;");

            // finally sets window to courses
            window.setScene(course);
        });

        courseAddBtn.setOnAction((event) -> {
            courseContent.getChildren().clear();
            getCourseList(courseList, courseListTitle);
            courseContent.getChildren().addAll(addPlaceCourses, courseList);

            courseAddBtn.setDefaultButton(true);
            courseEditBtn.setDefaultButton(false);
            courseDelBtn.setDefaultButton(false);

            // clears fields
            clearCourseFields(nameInput, subjectInput, instructionTextInput, difficultyDropdown);
            courseSubmitAlert.setText("");
            courseSubmitAlert.setStyle("-fx-text-fill: RED;" +
                    "-fx-padding: 15;");
        });

        courseEditBtn.setOnAction((event) -> {
            courseContent.getChildren().clear();
            courseContent.getChildren().addAll(editPlaceCourse);

            courseEditBtn.setDefaultButton(true);
            courseAddBtn.setDefaultButton(false);
            courseDelBtn.setDefaultButton(false);
        });

        // action on add button
        addBtn.setOnAction((event) -> {

            // clears content and fills it with the right content
            studentContent.getChildren().clear();
            studentContent.getChildren().addAll(studentAddPlaces, studentList, yourAddedContent);

            // sets default buttons good according to the page
            addBtn.setDefaultButton(true);
            editBtn.setDefaultButton(false);
            delBtn.setDefaultButton(false);

            // loads student names from database
            getStudentList(studentList, studentListTitle);
        });

        // action on edit button
        editBtn.setOnAction((event) -> {

            // clears content and fills it with the right content
            studentContent.getChildren().clear();
            studentContent.getChildren().add(studentEditPlaces);

            // clears the edit page, now you can only see the email input
            actualEditPlace.getChildren().clear();

            // sets default buttons good according to the page
            editBtn.setDefaultButton(true);
            addBtn.setDefaultButton(false);
            delBtn.setDefaultButton(false);

            // sets text for bdate, gender, alert and input back to normal text
            editBdate.setText("DD-MM-YYYY");
            editGender.setText("M/W/O");
            editAlert.setText("");
            editInput.setText("");

            // sets style for alert back
            editAlert.setStyle("-fx-text-fill: RED;" +
                    "-fx-padding: 15;");
        });

        // action on delete button
        delBtn.setOnAction((event) -> {

            // clears content and fills it with the right content
            studentContent.getChildren().clear();
            studentContent.getChildren().addAll(studentDelPlaces);

            // sets default buttons good according to the page
            delBtn.setDefaultButton(true);
            addBtn.setDefaultButton(false);
            editBtn.setDefaultButton(false);

            // sets text and style back to normal state
            delAlert.setText("");
            delAlert.setStyle("-fx-text-fill: RED;" +
                    "-fx-padding: 15;");
        });

        // action on go button from delete page
        delGoBtn.setOnAction((event) -> {

            // gets input from TextField
            String emailAddress = delInput.getText();

            // try's to put all students in an Arraylist
            ArrayList<Student> studInfo = new ArrayList<>();
            try {
                studInfo = dbcn.retrieveStudents();
                // waits 90ms to retieve the students
                Thread.sleep(90);

                // loops trough the Arraylist
                for (Student i : studInfo) {

                    // when the email is the same it will delete the student and gives a
                    // confirmation
                    if (i.getEmailAddress().equals(emailAddress)) {

                        dbcn.deleteStudentFromDatabase(i);
                        delInput.setText("");
                        delAlert.setStyle("-fx-text-fill: GREEN;" + "-fx-padding: 15;");
                        delAlert.setText("Student removed!");
                    }
                }

                // if the email hasn't been found it will show 'Unknown mail..'
                if (!delAlert.getText().equals("Student removed!")) {
                    delAlert.setText("Unknown mail..");
                }

                // if something fails the error will show 'Something is wrong..'
            } catch (Exception e) {
                delAlert.setText("Something is wrong..");
            }
        });

        // action on go button from edit page
        editGoBtn.setOnAction((event) -> {

            // clears content and fills it with the right content
            studentContent.getChildren().clear();
            studentContent.getChildren().add(studentEditPlaces);

            // gets input from TextField
            String emailAddress = editInput.getText();

            // try's to put all students in an ArrayList
            ArrayList<Student> studInfo = new ArrayList<>();
            try {
                studInfo = dbcn.retrieveStudents();

                // waits 90ms to retrieve the studens
                Thread.sleep(90);

                for (Student i : studInfo) {

                    // this will only happen when an emailadress equals the emailadress
                    if (i.getEmailAddress().equals(emailAddress)) {

                        // sets text for all the labels to show information
                        dataName.setText("Name: " + i.getName());
                        dataEmail.setText("Mail: " + i.getEmailAddress());
                        dataGender.setText("Gender: " + i.getGender());
                        dataBirthDate.setText("Birthday: " + String.valueOf(i.getBirthDate()));
                        dataStreet.setText("Street: " + i.getStreet());
                        dataHouseNumber.setText("Housenumber: " + String.valueOf(i.getHouseNumber()));
                        dataPostalCode.setText("Postal code: " + i.getPostalCode());
                        dataCity.setText("City: " + i.getCity());
                        dataCounty.setText("Country: " + i.getCountry());

                        // add infoFromEditInput to show in the student content
                        studentContent.getChildren().add(infoFromEditInput);

                        // clears the edit place from previous times and adds the first and second row
                        actualEditPlace.getChildren().clear();
                        actualEditPlace.getChildren().addAll(firstRow, secondRow);

                        // next the edit place will be shown on the page
                        studentEditPlaces.getChildren().add(actualEditPlace);

                        // removes text from the alert
                        editAlert.setText("");
                    }
                }

                // if the mail hasn't been found the alert will show 'no falid email'
                if (!dataEmail.getText().equals("Mail: " + emailAddress)) {
                    editAlert.setText("No falid Email");
                }

                // if something fails an error will be shown in the terminal
            } catch (Exception e) {
                System.out.println(e);
            }
        });

        // action for the submit button on the edit page
        editSubmitBtn.setOnAction((event) -> {

            // gets all the text what has been filled in
            String email = editInput.getText();
            String newName = editName.getText();
            String gender = editGender.getText();
            String birthDate = editBdate.getText();
            String street = editStreet.getText();
            int houseNumber = Integer.parseInt(editHouseNumber.getText());
            String postalCode = editPostalCode.getText();
            String city = editCity.getText();
            String country = editCountry.getText();

            // try's to pull all students in an ArrayList
            ArrayList<Student> editStudent = new ArrayList<>();
            try {
                editStudent = dbcn.retrieveStudents();

                // waits 90ms to retrieve the students
                Thread.sleep(90);

                for (Student i : editStudent) {
                    // when an email equals the right email it wil set all the new values to the
                    // Student
                    if (i.getEmailAddress().equals(email)) {
                        i.setName(newName);
                        i.setGender(gender);
                        i.setBirthDate(setDate(birthDate));
                        i.setStreet(street);
                        i.setHouseNumber(houseNumber);
                        i.setPostalCode(postalCode);
                        i.setCity(city);
                        i.setCountry(country);

                        // the student will be changes in the database
                        dbcn.editStudentInformation(i);

                        // the new values will be shown in the GUI too
                        infoFromEditInputListTitle.setText("Your new info:");
                        dataName.setText("Name: " + newName);
                        dataEmail.setText("Mail: " + email);
                        dataGender.setText("Gender: " + gender);
                        dataBirthDate.setText("Birthdate: " + String.valueOf(setDate(birthDate)));
                        dataStreet.setText("Street: " + street);
                        dataHouseNumber.setText("Housenumber: " + String.valueOf(houseNumber));
                        dataPostalCode.setText("Postalcode: " + postalCode);
                        dataCity.setText("City: " + city);
                        dataCounty.setText("Country: " + country);

                        // the alert text will be update to give a confirmation
                        editAlert.setStyle("-fx-text-fill: GREEN;" + "-fx-padding: 15;");
                        editAlert.setText("Info changed!");
                    }
                }

                // if something fails an error will be shown in the terminal
            } catch (Exception e) {
                System.out.println(e);
            }
        });

        goCourseEditBtn.setOnAction((event) -> {
            editPlaceCourse.getChildren().add(actualCourseEditPlace);
        });

        // action on submit button in the add page
        submitButton.setOnAction((event) -> {

            // gets all the text what has been filled in
            String emailAddress = addEmailField.getText();
            String newName = addNameField.getText();
            String gender = addGenderField.getText();
            String birthDate = addBirthDateField.getText();
            String street = addStreetField.getText();
            int houseNumber = Integer.parseInt(addHouseNumberField.getText());
            String postalCode = addPostalCodeField.getText();
            String city = addCityField.getText();
            String country = addCountryField.getText();

            // a new student will be created
            Student newStudent = new Student(emailAddress, newName, gender, setDate(birthDate), street, houseNumber,
                    postalCode, city, country);

            // try's to add the student, if it fails it will give an error
            try {
                dbcn.addStudentToDatabase(newStudent);
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // alert will be update in a green color and a confirmation
            alert.setStyle("-fx-text-fill: GREEN;" + "-fx-padding: 15;");
            alert.setText("Succesfully added!");

            // the list of student names will be updated
            getStudentList(studentList, studentListTitle);

            // for styling the M, W or O will become a full word
            if (gender.equals("M")) {
                gender += "an";
            } else if (gender.equals("W")) {
                gender += "omen";
            } else {
                gender = "Other";
            }

            // an extra list will be added to view your newest content you added
            newestName.setText("Name: " + newName);
            newestEmail.setText("Email: " + emailAddress);
            newestGender.setText("Gender: " + gender);
            newestBirthDate.setText("Birthdate: " + birthDate);
            newestStreet.setText("Street: " + street);
            newestHouseNumber.setText("Housenumber: " + String.valueOf(houseNumber));
            newestPostalCode.setText("Postal code: " + postalCode);
            newestCity.setText("City: " + city);
            newestCounty.setText("Country: " + country);

            // the TextField will be cleared for a new student
            clearFields(addNameField, addEmailField, addBirthDateField, addCityField, addGenderField, addStreetField,
                    addHouseNumberField, addPostalCodeField, addCountryField);

        });

        // action on submit button in add course page
        courseSubmitButton.setOnAction((event) -> {

            String courseTitle = nameInput.getText();
            String courseSubject = subjectInput.getText();
            String courseDescription = instructionTextInput.getText();
            Difficulty courseDifficulty = Difficulty.valueOf(String.valueOf(difficultyDropdown.getValue()));
            // String courseModule = moduleDropdown.getPromptText();

            Course newCourse = new Course(courseTitle, courseSubject, courseDescription, courseDifficulty);

            try {
                dbcn.addCourseToDatabase(newCourse);
                courseSubmitAlert.setText("Course added!");
                courseSubmitAlert.setStyle("-fx-text-fill: GREEN;" + "-fx-padding: 15;");
            } catch (Exception e) {
                System.out.println("Er is een fout opgetreden");
            }
            getCourseList(courseList, courseListTitle);
            clearCourseFields(nameInput, subjectInput, instructionTextInput, difficultyDropdown);
        });

        // window set and show
        window.setTitle("Thomas Quartel, Mickel de Coo, Stijn Spanjers en Stan Tophoven");
        window.setScene(home);
        window.show();
    }

    // methodes

    /**
     * hiero stan
     * 
     * @param studentList
     * @param studentListTitle
     */

    public void getStudentList(VBox studentList, Label studentListTitle) {

        // only connects again when the connection is lost
        if (dbcn.getConn() == null) {
            dbcn.connect();
        }

        try {
            // waits 90ms to be sure the connection is on
            Thread.sleep(90);

            // clears the studentlist from previous names and will add the title already
            studentList.getChildren().clear();
            studentList.getChildren().add(studentListTitle);

            // all the names from the database will be added to the list
            ArrayList<Student> students = dbcn.retrieveStudents();
            for (Student name : students) {
                Label newStud = new Label("- " + name.getName());
                studentList.getChildren().add(newStud);
            }

            // when something goes wrong an error in the terminal will be shown
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void getCourseList(VBox courseList, Label courseListTitle) {

        // only connects again when the connection is lost
        if (dbcn.getConn() == null) {
            dbcn.connect();
        }

        try {
            // waits 90ms to be sure the connection is on
            Thread.sleep(90);

            // clears the courselist from previous names and will add the title already
            courseList.getChildren().clear();
            courseList.getChildren().add(courseListTitle);

            // all the names from the database will be added to the list
            ArrayList<Course> courses = dbcn.retrieveCourses();
            for (Course name : courses) {
                Label newCourse = new Label("- " + name.getName());
                courseList.getChildren().add(newCourse);
            }

            // when something goes wrong an error in the terminal will be shown
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * hiero ook stan
     * 
     * @param birthDate
     * @return
     */

    public Date setDate(String birthDate) {
        // split the input to make the date in a format for the database
        String[] splits = birthDate.split("-");
        int[] intDate = new int[3];
        for (int i = 0; i < 3; i++) {
            intDate[i] = Integer.parseInt(splits[i]);
        }

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, intDate[2]);
        cal.set(Calendar.MONTH, intDate[1] - 1);
        cal.set(Calendar.DAY_OF_MONTH, intDate[0]);

        java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());
        return date;
    }

    /**
     * deze trouwens ook
     */
    public void clearFields(TextField nameField, TextField emailField, TextField birthDateField, TextField cityField,
            TextField genderField, TextField streetField, TextField houseNrField, TextField postalCodeField,
            TextField countryField) {

        // clears all the fields
        nameField.clear();
        emailField.clear();
        birthDateField.setText("DD-MM-YYYY");
        cityField.clear();
        genderField.setText("M/W/O");
        houseNrField.clear();
        postalCodeField.clear();
        countryField.clear();
        streetField.clear();
    }

    public void defaultButtonToFalse() {
        for (Button button : menuButtons) {
            button.setDefaultButton(false);
        }
    }

    public void clearCourseFields(TextField nameInput, TextField subjectInput, TextArea instructionTextInput,
            ComboBox difficultyDropdown) {
        nameInput.clear();
        subjectInput.clear();
        instructionTextInput.clear();
        difficultyDropdown.getSelectionModel().clearSelection();
        ;
    }
}
