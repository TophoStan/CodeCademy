package controllers;

import domain.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import repository.DatabaseConnection;
import validation.Validator;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/** Controller of the student views*/
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
    @FXML private ListView percentContentItemList;

    private CertificateController certificateController = new CertificateController();
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private Controller controller = new Controller(databaseConnection);
    private Validator validator = new Validator();
    /**
     * This method uses the Controller to go to a page.
     */
    public void toHome(ActionEvent event) {
        controller.toPage(event, "Home");
    }
    /**
     * This method uses the Controller to go to a page.
     */
    public void toStudent(ActionEvent event) {
        controller.toPage(event, "StudentAdd");
    }
    /**
     * This method uses the Controller to go to a page.
     */
    public void toCourse(ActionEvent event) {
        controller.toPage(event, "CourseAdd");
    }
    /**
     * This method uses the Controller to go to a page.
     */
    public void toEnrollment(ActionEvent event) {
        controller.toPage(event, "EnrollmentAdd");
    }
    /**
     * This method uses the Controller to go to a page.
     */
    public void toContentItem(ActionEvent event) {
        controller.toPage(event, "ContentItemAdd");
    }
    /**
     * This method uses the Controller to go to a page.
     */
    public void toCertificate(ActionEvent event) {
        controller.toPage(event, "CertificateAdd");
    }
    /**
     * This method uses the Controller to go to a page.
     */
    public void toStudentEdit(ActionEvent event) {
        controller.toPage(event, "StudentEdit");
    }
    /**
     * This method uses the Controller to go to a page.
     */
    public void toStudentDelete(ActionEvent event){
        controller.toPage(event, "StudentDelete");
    }
    /**
     * This method uses the Controller to go to a page.
     */
    public void toStudentSelection(ActionEvent event) {
        controller.toPage(event, "StudentSelection");
    }

    /**
     * makes all the fields visible if the studentemail exists
    * */
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
    /** fills the student fields with the students information*/
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
    /** creates a student object with the changed values then passes it to the DatabaseConnection class*/
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
                    if (validator.isDateValid(convertDate(day, month, year).toString(), "Student")) {
                        student.setBirthDate(convertDate(day, month, year));
                    }
                    student.setStreet(tFStudentEditStreet.getText());
                    student.setHouseNumber(Integer.parseInt(tFStudentEditHousenumber.getText()));

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
    /** adds the student info of the passed student to a listview*/
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
    /** creates a student object with the given values then passes it to the DatabaseConnection class*/
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
        if (validator.isDateValid(convertDate(day, month, year).toString(), "Student")) {
            student.setBirthDate(convertDate(day, month, year));
        }
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
    /** adds all the students from the database to a listview*/
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
    /** deletes a student from the database*/
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
    /** shows all field of the selection view if the student exists*/
    public void selectStudent() {
        String email = tFStudentSelectionEmail.getText();

        if (controller.checkEmail(email)) {
            databaseConnection.connect();
            showCertificates(email);
            showContentItemsPercent(email);

        } else {
            tFStudentSelectionEmail.setText("Wrong email!");
        }
    }
    /** shows all contentItems their percentage of the passed student*/
    public void showContentItemsPercent(String email) {
        percentContentItemList.getItems().clear();
        Student student = (Student) controller.giveIdentifierReturnObject(email, "Student");
        HashMap<ContentItem, Integer> percentForContentItems = new HashMap<>();
        try {
            percentForContentItems = databaseConnection.getContentItemsWithPercent(student.getId());

            for (Map.Entry<ContentItem, Integer> e : percentForContentItems.entrySet()) {
                percentContentItemList.getItems().add(e.getKey().getTitle() + ": " + e.getValue() + "%");
            }
            if (percentContentItemList.getItems().isEmpty()) {
                percentContentItemList.getItems().add("Did not enroll a course..");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /** shows all the certificates achieved by the passed student*/
    public void showCertificates(String email) {
        ArrayList<Course> allCourses = new ArrayList<>();
        ArrayList<Integer> courseIdList = new ArrayList<>();
        certificatesList.getItems().clear();
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
    }
    /** converts the values of the textfields to sql.date*/
    public Date convertDate(int day, int month, int year) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());
        return date;
    }
    /** converts a date to 3 different integers*/
    public void convertDateToInt(Date date) {
        String[] split = date.toString().split("-");
        tFStudentEditDay.setText(split[2]);
        tFStudentEditMonth.setText(split[1]);
        tFStudentEditYear.setText(split[0]);
    }
}
