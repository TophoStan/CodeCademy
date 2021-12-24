package controllers;

import com.example.codecademy.App;
import domain.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import repository.DatabaseConnection;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Locale;

public class studentController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField tfStudentAddName;
    @FXML
    private TextField tFStudentAddEmail;
    @FXML
    private TextField tFStudentAddGender;
    @FXML
    private TextField tFStudentAddStreet;
    @FXML
    private DatePicker tFStudentAddBirthdate;
    @FXML
    private TextField tFStudentAddDay;
    @FXML
    private TextField tFStudentAddMonth;
    @FXML
    private TextField tFStudentAddYear;
    @FXML
    private TextField tFStudentAddHousenumber;
    @FXML
    private TextField tFStudentAddPostalCode;
    @FXML
    private TextField tFStudentAddCity;
    @FXML
    private TextField tFStudentAddCountry;

    DatabaseConnection databaseConnection = new DatabaseConnection();


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

    public void toStudent(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(App.class.getResource("StudentAdd.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
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

    public void toStudentEdit(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(App.class.getResource("StudentEdit.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStudentEditPlace(ActionEvent event) {

    }


    public void addStudentToDatabase() {
        databaseConnection.connect();
        Student student = new Student();
        student.setName(tfStudentAddName.getText());
        student.setEmailAddress(tFStudentAddEmail.getText());
        student.setGender(tFStudentAddGender.getText());\
        //Converts the
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        int year = Integer.parseInt(tFStudentAddYear.getText());
        int month = Integer.parseInt(tFStudentAddMonth.getText());
        int day = Integer.parseInt(tFStudentAddDay.getText());
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());
        student.setBirthDate(date);
        student.setStreet(tFStudentAddStreet.getText());
        student.setHouseNumber(Integer.parseInt(tFStudentAddHousenumber.getText()));
        student.setPostalCode(tFStudentAddPostalCode.getText());
        student.setCity(tFStudentAddCity.getText());
        student.setCountry(tFStudentAddCountry.getText());
        try {
            databaseConnection.addStudentToDatabase(student);
        } catch (Exception e) {

        }
    }
}
