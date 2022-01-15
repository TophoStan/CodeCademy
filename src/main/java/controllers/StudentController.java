package controllers;

import domain.Certificate;
import domain.Course;
import domain.Enrollment;
import domain.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import repository.DatabaseConnection;
import validation.Validator;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;

public class StudentController {

    // for student add page
    @FXML private TextField tfStudentAddName;
    @FXML private TextField tFStudentAddEmail;
    @FXML private TextField tFStudentAddGender;
    @FXML private TextField tFStudentAddStreet;
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
    @FXML private TextField tFStudentEditDay;
    @FXML private TextField tFStudentEditYear;
    @FXML private TextField tFStudentEditMonth;
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

    // for Student delete page
    @FXML private TextField tFStudentDeleteEmail;

    // for Student selection page
    @FXML private TextField tFStudentSelectionEmail;
    @FXML private ListView certificatesList;

    private CertificateController certificateController = new CertificateController();
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private Controller controller = new Controller(databaseConnection);
    private Validator validator = new Validator();

    public void toHome(ActionEvent event) {
        controller.toPage(event, "Home");
    }

    public void toStudent(ActionEvent event) {
        controller.toPage(event, "StudentAdd");
    }

    public void toCourse(ActionEvent event) {
        controller.toPage(event, "CourseAdd");
    }

    public void toEnrollment(ActionEvent event) {
        controller.toPage(event, "EnrollmentAdd");
    }

    public void toContentItem(ActionEvent event) {
        controller.toPage(event, "ContentItemAdd");
    }

    public void toCertificate(ActionEvent event) {
        controller.toPage(event, "CertificateAdd");
    }

    public void toStudentEdit(ActionEvent event) {
        controller.toPage(event, "StudentEdit");
    }
    public void toStudentDelete(ActionEvent event){
        controller.toPage(event, "StudentDelete");
    }
    public void toStudentSelection(ActionEvent event) {
        controller.toPage(event, "StudentSelection");
    }

    public void showStudentEditPlace() {
        String email = tFStudentEditEmail.getText();
        if (controller.checkEmail(email)) {
            studentInfoList.setVisible(true);
            lBStudentEditYourInfo.setVisible(true);
            lBStudentEditName.setVisible(true);
            tFStudentEditName.setVisible(true);
            lBStudentEditGender.setVisible(true);
            tFStudentEditGender.setVisible(true);
            lBStudentEditBirthdate.setVisible(true);
            tFStudentEditDay.setVisible(true);
            tFStudentEditMonth.setVisible(true);
            tFStudentEditYear.setVisible(true);
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

            fillStudentTF(email);
            showStudentInfo(email);
        } else {
            tFStudentEditEmail.setText("Wrong email!");
        }
    }

    public void fillStudentTF(String email) {
        databaseConnection.connect();

        try {
            Student student = (Student) controller.giveIdentifierReturnObject(email, "Student");
            tFStudentEditCity.setText(student.getCity());
            tFStudentEditCountry.setText(student.getCountry());
            tFStudentEditGender.setText(student.getGender());
            tFStudentEditHousenumber.setText(String.valueOf(student.getHouseNumber()));
            tFStudentEditName.setText(student.getName());
            tFStudentEditPostalCode.setText(student.getPostalCode());
            tFStudentEditStreet.setText(student.getStreet());

            convertDateToInt(student.getBirthDate());

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void editStudentToDatabase() {
        String email = tFStudentEditEmail.getText();
        databaseConnection.connect();
        ArrayList<Student> editStudent = new ArrayList<>();

        try {
            editStudent = databaseConnection.retrieveStudents();

            for (Student student : editStudent) {
                if (student.getEmailAddress().equals(email)) {
                    student.setName(tFStudentEditName.getText());
                    student.setGender(tFStudentEditGender.getText());
                    int day = Integer.parseInt(tFStudentEditDay.getText());
                    int month = Integer.parseInt(tFStudentEditMonth.getText());
                    int year = Integer.parseInt(tFStudentEditYear.getText());
                    student.setBirthDate(convertDate(day, month, year));
                    student.setStreet(tFStudentEditStreet.getText());
                    student.setHouseNumber(Integer.parseInt(tFStudentEditHousenumber.getText()));

                    // Ik probeer hier de spaties tussen de getallen en letters weg te halen waardoor de validator het wel doet
                    String postalCodeForValidator = tFStudentEditPostalCode.getText().replaceAll("\\s+", "");
                    student.setPostalCode(validator.formatPostalCode(postalCodeForValidator));

                    student.setCity(tFStudentEditCity.getText());
                    student.setCountry(tFStudentEditCountry.getText());

                    databaseConnection.editStudentInformation(student);
                    tFStudentEditEmail.setText("Student info changed!");
                    Thread.sleep(90);
                    showStudentInfo(email);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void showStudentInfo(String email) {
        studentInfoList.getItems().clear();
        databaseConnection.connect();

            try {
                Student student = (Student) controller.giveIdentifierReturnObject(email, "Student");
                studentInfoList.getItems().add("Name: " + student.getName());
                studentInfoList.getItems().add("Email: " + student.getEmailAddress());
                studentInfoList.getItems().add("Gender: " + student.getGender());
                studentInfoList.getItems().add("Birthdate: " + student.getBirthDate());
                studentInfoList.getItems().add("Street: " + student.getStreet());
                studentInfoList.getItems().add("Housenumber: " + student.getHouseNumber());
                studentInfoList.getItems().add("Postalcode: " + student.getPostalCode());
                studentInfoList.getItems().add("City: " + student.getCity());
                studentInfoList.getItems().add("Country: " + student.getCountry());
            } catch (Exception e) {
                System.out.println(e);
            }
    }


    public void addStudentToDatabase() {
        databaseConnection.connect();
        Student student = new Student();
        student.setName(tfStudentAddName.getText());
        if (validator.isEmailAddressValid(tFStudentAddEmail.getText())) {
            student.setEmailAddress(tFStudentAddEmail.getText());
        }
        student.setGender(tFStudentAddGender.getText());
        int year = Integer.parseInt(tFStudentAddYear.getText());
        int month = Integer.parseInt(tFStudentAddMonth.getText());
        int day = Integer.parseInt(tFStudentAddDay.getText());
        student.setBirthDate(convertDate(day, month, year));
        student.setStreet(tFStudentAddStreet.getText());
        student.setHouseNumber(Integer.parseInt(tFStudentAddHousenumber.getText()));
        student.setPostalCode(validator.formatPostalCode(tFStudentAddPostalCode.getText()));
        student.setCity(tFStudentAddCity.getText());
        student.setCountry(tFStudentAddCountry.getText());
        try {
            databaseConnection.addStudentToDatabase(student);
            showStudent();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void showStudent(){
        databaseConnection.connect();
        try {
            ArrayList<Student> students = new ArrayList<>();
            students = databaseConnection.retrieveStudents();
            listStudent.getItems().clear();
            for (Student student: students) {
                this.listStudent.getItems().add(student.getName());
            }
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public void deleteStudent(){
        String email = tFStudentDeleteEmail.getText();
        ArrayList<Student> studentsToDelete = new ArrayList<>();
        if (controller.checkEmail(email)) {
            try {
                databaseConnection.connect();
                studentsToDelete = databaseConnection.retrieveStudents();
                for (Student i : studentsToDelete) {
                    if (i.getEmailAddress().equals(email)) {
                        databaseConnection.deleteStudentFromDatabase(i);
                        tFStudentDeleteEmail.setText("Student removed");
                        showStudent();
                        break;
                    }
                }
            } catch (Exception e){
                System.out.println(e);
            }
        } else {
            tFStudentDeleteEmail.setText("Unknown email");
        }
    }

    public void selectStudent() {
        String email = tFStudentSelectionEmail.getText();
        ArrayList<Course> allCourses = new ArrayList<>();
        ArrayList<Integer> courseIdList = new ArrayList<>();

        if (controller.checkEmail(email)) {
            certificatesList.getItems().clear();
            databaseConnection.connect();
            try {
                allCourses = databaseConnection.retrieveCourses();
                courseIdList = databaseConnection.certificatesFromStudent(email);

                for (Course course : allCourses) {
                    for (Integer i : courseIdList) {
                        if (i == course.getId()) {
                            certificatesList.getItems().add(course.getName());
                        }
                    }
                }
                if (certificatesList.getItems().isEmpty()) {
                    certificatesList.getItems().add("No certificates");
                }

            } catch (Exception e) {
                System.out.println(e);
            }

        } else {
            tFStudentSelectionEmail.setText("Wrong email!");
        }
    }


    public Date convertDate(int day, int month, int year) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());
        return date;
    }

    public void convertDateToInt(Date date) {
        String[] split = date.toString().split("-");
        tFStudentEditDay.setText(split[2]);
        tFStudentEditMonth.setText(split[1]);
        tFStudentEditYear.setText(split[0]);
    }
}
