package controllers;


import domain.*;

import domain.ContentItem;
import domain.Course;
import domain.Difficulty;
import domain.Module;
import domain.Webcast;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.*;

import javafx.scene.layout.AnchorPane;
import repository.DatabaseConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CourseController {

    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private Controller controller = new Controller(databaseConnection);
    private String[] difficulties = {Difficulty.EASY.toString(), Difficulty.NORMAL.toString(), Difficulty.HARD.toString(), Difficulty.EXPERT.toString()};

    // for course add page
    @FXML private AnchorPane anchorPane;
    @FXML private ListView courseList;
    @FXML private TextField tFCourseAddName;
    @FXML private TextField tFCourseAddSubject;
    @FXML private TextArea tACourseAddIntroText;
    @FXML private ComboBox cBCourseAddDifficulty;
    @FXML private ComboBox cbContentItem;

    // for course edit page
    @FXML private TextField tFNameEditCourse;
    @FXML private Label lBSubjectEditCourse;
    @FXML private Label lBIntroTextEditCourse;
    @FXML private Label lBDifficultyEditCourse;
    @FXML private TextField tFSubjectEditCourse;
    @FXML private TextArea tAIntroTextEditCourse;
    @FXML private ComboBox cBDifficultyEditCourse;
    @FXML private Button btnSubmitEditCourse;
    @FXML private ListView courseInfoList;

    // for course delete page
    @FXML private TextField tFNameDeleteCourse;

    // for course select page
    @FXML private TextField tFNameSelectCourse;
    @FXML private Label lBCourseSelectionAmount;
    @FXML private Label lBCourseSelectionProgressTitle;
    @FXML private ListView progressList;
    @FXML private Label lBCourseSelectionGender;
    @FXML private RadioButton rbCourseSelectionMen;
    @FXML private RadioButton rbCourseSelectionWomen;
    @FXML private RadioButton rbCourseSelectionOther;
    @FXML private ProgressBar pbCourseSelectionGender;
    @FXML private Label lBCourseSelectionPercentage;
    @FXML private ToggleGroup gender;



    public void toHome(ActionEvent event) {
        controller.toPage(event, "Home");
    }

    public void toStudent(ActionEvent event){
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

    public void toCourseEdit(ActionEvent event) {
        controller.toPage(event, "CourseEdit");
    }
    public void toCourseDelete(ActionEvent event) {
        controller.toPage(event,  "CourseDelete");
    }
    public void toCourseSelection(ActionEvent event) {
        controller.toPage(event, "CourseSelection");
    }


    public void showCourses() {
        databaseConnection.connect();
        try {
            ArrayList<Course> courses = new ArrayList<>();
            courses = databaseConnection.retrieveCourses();
            courseList.getItems().clear();
            for (Course course: courses) {
                courseList.getItems().add(course.getName());
            }
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public void addCourseToDatabase() {
        databaseConnection.connect();
        Course course = new Course();
        course.setName(tFCourseAddName.getText());
        course.setText(tACourseAddIntroText.getText());
        course.setDifficulty(Difficulty.valueOf(cBCourseAddDifficulty.getValue().toString()));
        course.setSubject(tFCourseAddSubject.getText());
        String[] splitter = cbContentItem.getValue().toString().split("-");


        try {
            databaseConnection.addCourseToDatabase(course);
            Course courseFromDatabase = (Course) controller.giveIdentifierReturnObject(course.getName(), "Course");
            ContentItem contentItem = (ContentItem) controller.giveIdentifierReturnObject(splitter[1], "ContentItem");
            contentItem.setCourseId(courseFromDatabase.getId());
            databaseConnection.editContentItem(contentItem);
            showCourses();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void loadContentItems(){
        databaseConnection.connect();
        cbContentItem.getItems().clear();
        try {
            for (ContentItem contentItem : databaseConnection.retrieveContentItems()) {
                if(contentItem.getCourseId() == 0){
                    String contentItemType ="";
                    for (Webcast webcast : databaseConnection.retrieveWebcasts()) {
                        if(webcast.getContentItemId() == contentItem.getContentItemId()){
                            contentItemType = "Web";
                        }
                    }
                    for (Module module : databaseConnection.retrieveModules()) {
                        if(module.getContentItemId() == contentItem.getContentItemId()){
                            contentItemType = "Mod";
                        }
                    }
                    cbContentItem.getItems().add(contentItemType + " - " + contentItem.getTitle());
                }
            }
        } catch (Exception e){

        }
    }

    public void loadDifficulties() {
        try {
            cBCourseAddDifficulty.getItems().clear();
            cBCourseAddDifficulty.getItems().addAll(difficulties);
        } catch (Exception e) {
        }

        try {
            cBDifficultyEditCourse.getItems().clear();
            cBDifficultyEditCourse.getItems().addAll(difficulties);
        } catch (Exception e) {
        }
    }

    public void showCourseEditPlace() {
        if (!(courseList.getSelectionModel().isEmpty())) {
            tFNameEditCourse.setText((String) courseList.getSelectionModel().getSelectedItem());
        }

        String courseName = tFNameEditCourse.getText();

        lBSubjectEditCourse.setVisible(false);
        lBDifficultyEditCourse.setVisible(false);
        lBIntroTextEditCourse.setVisible(false);
        tFSubjectEditCourse.setVisible(false);
        tAIntroTextEditCourse.setVisible(false);
        cBDifficultyEditCourse.setVisible(false);
        btnSubmitEditCourse.setVisible(false);

        if (checkCourseName(courseName)) {
            lBSubjectEditCourse.setVisible(true);
            lBDifficultyEditCourse.setVisible(true);
            lBIntroTextEditCourse.setVisible(true);
            tFSubjectEditCourse.setVisible(true);
            tAIntroTextEditCourse.setVisible(true);
            cBDifficultyEditCourse.setVisible(true);
            btnSubmitEditCourse.setVisible(true);

            fillCourseTF(courseName);
            showCourseInfo(courseName);
        } else {
            tFNameEditCourse.setText("Wrong name!");
        }
    }

    public void fillCourseTF(String courseName) {
        databaseConnection.connect();

        try {
            Course course = (Course) controller.giveIdentifierReturnObject(courseName, "Course");

            tFSubjectEditCourse.setText(course.getSubject());
            cBDifficultyEditCourse.setPromptText(course.getDifficulty().toString());
            cBDifficultyEditCourse.setValue(course.getDifficulty());
            tAIntroTextEditCourse.setText(course.getText());

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void editCourse() {
        String courseName = tFNameEditCourse.getText();
        databaseConnection.connect();
        ArrayList<Course> editCourse = new ArrayList<>();

        try {
            editCourse = databaseConnection.retrieveCourses();

            for (Course course : editCourse) {
                if (course.getName().equals(courseName)) {
                    course.setSubject(tFSubjectEditCourse.getText());
                    course.setDifficulty(Difficulty.valueOf(cBDifficultyEditCourse.getValue().toString()));
                    course.setText(tAIntroTextEditCourse.getText());

                    databaseConnection.editCourseInformation(course);
                    tFNameEditCourse.setText("Course info changed!");
                    Thread.sleep(90);
                    showCourseInfo(courseName);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void showCourseInfo(String courseName) {
        courseInfoList.getItems().clear();
        databaseConnection.connect();
        ArrayList<Course> arrayListForCourseInfo = new ArrayList<>();

        try {
            arrayListForCourseInfo = databaseConnection.retrieveCourses();
            for (Course course : arrayListForCourseInfo) {
                if (course.getName().equals(courseName)) {
                    courseInfoList.getItems().add("Name: " + course.getName());
                    courseInfoList.getItems().add("Subject: " + course.getSubject());
                    courseInfoList.getItems().add("Text: " + course.getText());
                    courseInfoList.getItems().add("Difficulty: " + course.getDifficulty());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCourse() {
        String courseName = tFNameDeleteCourse.getText();
        ArrayList<Course> courseToDelete = new ArrayList<>();

        if (checkCourseName(courseName)) {
            try {
                databaseConnection.connect();
                courseToDelete = databaseConnection.retrieveCourses();
                for (Course i : courseToDelete) {
                    if (i.getName().equals(courseName)) {
                        databaseConnection.deleteCourse(i);
                        tFNameDeleteCourse.setText("Course removed");
                        showCourses();
                        showCourseInfo(courseName);
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            tFNameDeleteCourse.setText("Unknown course");
        }
    }

    public boolean checkCourseName(String name) {
        boolean output = false;
        try {
            databaseConnection.connect();
            ArrayList<Course> coursesFromDatabase = new ArrayList<>();
            coursesFromDatabase = databaseConnection.retrieveCourses();

            for (Course course : coursesFromDatabase) {
                if (name.equals(course.getName())) {
                    output = true;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return output;
    }

    public void selectCourse() {
        if (!(courseList.getSelectionModel().isEmpty())) {
            tFNameSelectCourse.setText((String) courseList.getSelectionModel().getSelectedItem());
        }
        String courseName = tFNameSelectCourse.getText();

        lBCourseSelectionPercentage.setText("0%");
        pbCourseSelectionGender.setProgress(0.0);
        gender.selectToggle(null);

        if (checkCourseName(courseName)) {
            // Shows how many students finished a course
            makeVisible(true);
            int amountOfStudents = databaseConnection.studentsFinishedCourse(courseName);


            lBCourseSelectionAmount.setText("Finished by " + String.valueOf(amountOfStudents) + " students");

            // Shows for every module the progress percentage of all students
            progressList.getItems().clear();
            HashMap<Integer, Integer> progressHashMap = databaseConnection.getProgressForCourse(courseName);
            ArrayList<ContentItem> allContentItems = databaseConnection.retrieveContentItems();

            for (Map.Entry<Integer, Integer> i : progressHashMap.entrySet()) {
                int contentItemId = i.getKey();
                int percentage = i.getValue();
                String contentItemTitle = "";

                for (ContentItem contentItem : allContentItems) {
                    if (contentItemId == contentItem.getContentItemId()) {
                        contentItemTitle = contentItem.getTitle();
                        break;
                    }
                }
                progressList.getItems().add(contentItemTitle + ": " + percentage + "%");
            }
        } else {
            makeVisible(false);
            tFNameSelectCourse.setText("Wrong course name!");
        }
    }

    public void selectProgressGender() {
        String genderType = "";
        String selectedCourse = tFNameSelectCourse.getText();

        int countOfCertificates = 0;
        int amountOfEnrollments = 0;
        double percentForLabel = 0;
        double percentForProgressBar = 0.0;

        if (gender.getSelectedToggle().equals(rbCourseSelectionMen)) {
            genderType = "M";
        } else if (gender.getSelectedToggle().equals(rbCourseSelectionWomen)) {
            genderType = "W";
        } else if (gender.getSelectedToggle().equals(rbCourseSelectionOther)) {
            genderType = "O";
        }

        try {
            ArrayList<Enrollment> enrollments = databaseConnection.getEnrollmentIDFromSpecificGenderStudents(genderType);
            ArrayList<Course> courses = databaseConnection.retrieveCourses();
            ArrayList<Certificate> certificates = databaseConnection.retrieveCertificates();

            for (Enrollment enrollment : enrollments) {
                int theEnrollmentId = 0;
                for (Course course : courses) {
                    if (course.getName().equals(selectedCourse) && course.getId()  == enrollment.getCourseId()) {
                        theEnrollmentId = enrollment.getEnrollmentId();
                        amountOfEnrollments++;

                        for (Certificate certificate : certificates) {
                            if (certificate.getEnrollmentId() == theEnrollmentId) {
                                countOfCertificates++;
                                break;
                            }
                        }
                    }
                }
            }

            if (!(amountOfEnrollments == 0)) {
                percentForLabel =  ((double) countOfCertificates / amountOfEnrollments) * 100;
                percentForProgressBar = (double) countOfCertificates / amountOfEnrollments;
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        pbCourseSelectionGender.setProgress(percentForProgressBar);
        lBCourseSelectionPercentage.setText(percentForLabel + "%");
    }


    public void makeVisible(boolean bool) {
        lBCourseSelectionAmount.setVisible(bool);
        lBCourseSelectionProgressTitle.setVisible(bool);
        progressList.setVisible(bool);
        lBCourseSelectionGender.setVisible(bool);
        rbCourseSelectionMen.setVisible(bool);
        rbCourseSelectionWomen.setVisible(bool);
        rbCourseSelectionOther.setVisible(bool);
        pbCourseSelectionGender.setVisible(bool);
        lBCourseSelectionPercentage.setVisible(bool);
    }
    //TODO deze in controller class zetten
    public HashMap<Course, ArrayList<ContentItem>> contentItemsWithCourse() {
        HashMap<Course, ArrayList<ContentItem>> map = new HashMap<>();
        for (Course course : databaseConnection.retrieveCourses()) {
            ArrayList<ContentItem> contentItems = new ArrayList<>();
            for (ContentItem contentItem : databaseConnection.retrieveContentItems()) {
                if (course.getId() == contentItem.getCourseId()) {
                    contentItems.add(contentItem);
                }
            }
            map.put(course, contentItems);
        }
        return map;
    }
    public void clearFields(){
        controller.clear(anchorPane);
    }
}
