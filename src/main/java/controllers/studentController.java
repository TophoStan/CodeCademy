package controllers;

import com.example.codecademy.App;
import domain.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import repository.DatabaseConnection;

import java.io.IOException;
import java.sql.Array;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Locale;

import java.sql.SQLException;
import java.util.ArrayList;


public class studentController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    // for student add page
    @FXML private TextField tfStudentAddName;
    @FXML private TextField tFStudentAddEmail;
    @FXML private TextField tFStudentAddGender;
    @FXML private TextField tFStudentAddStreet;
    @FXML private DatePicker tFStudentAddBirthdate;
    @FXML private TextField tFStudentAddDay;
    @FXML private TextField tFStudentAddMonth;
    @FXML private TextField tFStudentAddYear;
    @FXML private TextField tFStudentAddHousenumber;
    @FXML private TextField tFStudentAddPostalCode;
    @FXML private TextField tFStudentAddCity;
    @FXML private TextField tFStudentAddCountry;
    @FXML private ListView listStudent;
    // for student edit page
    @FXML private TextField tFStudentEditEmail;
    @FXML private Label lBStudentEditYourInfo;
    @FXML private ListView studentInfoList;
    @FXML private Label lBStudentEditName;
    @FXML private TextField tFStudentEditName;
    @FXML private Label lBStudentEditGender;
    @FXML private TextField tFStudentEditGender;
    @FXML private Label lBStudentEditBirthdate;
    @FXML private DatePicker tFStudentEditBirthdate;
    @FXML private Label lBStudentEditStreet;
    @FXML private TextField tFStudentEditStreet;
    @FXML private Label lBStudentEditHousenumber;
    @FXML private TextField tFStudentEditHousenumber;
    @FXML private Label lBStudentEditPostalCode;
    @FXML private TextField tFStudentEditPostalCode;
    @FXML private Label lBStudentEditCity;
    @FXML private TextField tFStudentEditCity;
    @FXML private Label lBStudentEditCountry;
    @FXML private TextField tFStudentEditCountry;
    @FXML private Button btnEditStudent;

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

    public void showStudentEditPlace() {
        String email = tFStudentEditEmail.getText();
        if (checkEmail(email)) {
            studentInfoList.setVisible(true);
            lBStudentEditYourInfo.setVisible(true);
            lBStudentEditName.setVisible(true);
            tFStudentEditName.setVisible(true);
            lBStudentEditGender.setVisible(true);
            tFStudentEditGender.setVisible(true);
            lBStudentEditBirthdate.setVisible(true);
            tFStudentEditBirthdate.setVisible(true);
            lBStudentEditStreet.setVisible(true);
            tFStudentEditStreet.setVisible(true);
            lBStudentEditHousenumber.setVisible(true);
            tFStudentEditHousenumber.setVisible(true);
            lBStudentEditPostalCode.setVisible(true);
            tFStudentEditPostalCode.setVisible(true);
            lBStudentEditCity.setVisible(true);
            tFStudentEditCity.setVisible(true);
            lBStudentEditCountry.setVisible(true);
            tFStudentEditCountry.setVisible(true);
            btnEditStudent.setVisible(true);
        } else {
            tFStudentEditEmail.setText("Wrong email!");
        }
    }

    public boolean checkEmail(String email){
        boolean output = false;
        try {
            databaseConnection.connect();
            ArrayList<Student> studentsFromDatabase = new ArrayList<>();
            studentsFromDatabase = databaseConnection.retrieveStudents();

            for (Student student : studentsFromDatabase) {
                if (email.equals(student.getEmailAddress())) {
                    output = true;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return output;
    }


    public void addStudentToDatabase() {
        databaseConnection.connect();
        Student student = new Student();
        student.setName(tfStudentAddName.getText());
        student.setEmailAddress(tFStudentAddEmail.getText());
        student.setGender(tFStudentAddGender.getText());
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
    public void showStudent(){
        databaseConnection.connect();
        try {
            ArrayList<Student> students = new ArrayList<>();
            students = databaseConnection.retrieveStudents();
            for (Student student: students) {
                this.listStudent.getItems().add(student.getName());
            }
        } catch(Exception e) {
            System.out.println(e);
        }

    }
}
