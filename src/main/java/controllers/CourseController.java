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
import java.util.Random;
/** Controller of the course views*/
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
    @FXML private ListView interestingCourses;
    @FXML private Label lbInterestingCourses;

    /**
     * This method uses the Controller to go to a page.
     */
    public void toHome(ActionEvent event) {
        controller.toPage(event, "Home");
    }
    /**
     * This method uses the Controller to go to a page.
     */
    public void toStudent(ActionEvent event){
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
    public void toCourseEdit(ActionEvent event) {
        controller.toPage(event, "CourseEdit");
    }
    /**
     * This method uses the Controller to go to a page.
     */
    public void toCourseDelete(ActionEvent event) {
        controller.toPage(event,  "CourseDelete");
    }
    /**
     * This method uses the Controller to go to a page.
     */
    public void toCourseSelection(ActionEvent event) {
        controller.toPage(event, "CourseSelection");
    }

    /** Shows all the courses in a listview*/
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
    /** creates a Course object with the given values then passes it to the DatabaseConnection class*/
    public void addCourseToDatabase() {
        databaseConnection.connect();
        Course course = new Course();
        course.setName(tFCourseAddName.getText());
        course.setText(tACourseAddIntroText.getText());
        course.setDifficulty(Difficulty.valueOf(cBCourseAddDifficulty.getValue().toString()));
        course.setSubject(tFCourseAddSubject.getText());
        String[] splitter = cbContentItem.getValue().toString().split(" - ");

        try {
            databaseConnection.addCourseToDatabase(course);
            Course courseFromDatabase = (Course) controller.giveIdentifierReturnObject(course.getName(), "Course");
            ContentItem contentItem = (ContentItem) controller.giveIdentifierReturnObject(splitter[1], "ContentItem");
            contentItem.setCourseId(courseFromDatabase.getId());
            databaseConnection.editContentItem(contentItem);
            showCourses();
            clearFields();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /** Adds the contentItems that don't have a course assigned to them yet*/
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
    /** Adds the difficulties from the Difficulty Enum*/
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
    /** Turns all the fields of the edit view visible if the */
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
    /** Fills the course fields with the selected course*/
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
    /** Recreates a Course object with the given values then passes it to the DatabaseConnection class*/
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
                    clearFields();
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
    /** Shows the values of the selected course in a listview*/
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
    /** Recreates a Course object with the given values then passes it to the DatabaseConnection class*/
    public void deleteCourse() {
        String courseName = tFNameDeleteCourse.getText();
        ArrayList<Course> courseToDelete = new ArrayList<>();

        if (checkCourseName(courseName)) {
            try {
                databaseConnection.connect();
                courseToDelete = databaseConnection.retrieveCourses();
                for (Course i : courseToDelete) {
                    if (i.getName().equals(courseName)) {
                        if (databaseConnection.deleteCourse(i)) {
                            tFNameDeleteCourse.setText("Course removed");
                        } else {
                            tFNameDeleteCourse.setText("Course NOT removed");
                        }
                        showCourses();
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
    /** Checks if a course exists*/
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
    /** Shows the select view fields if the course exists */
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
            showInterestingCourses();

            lBCourseSelectionAmount.setText("Finished by " + String.valueOf(amountOfStudents) + " students");

            // Shows for every module the progress percentage of all students
            progressList.getItems().clear();
            try {
                ArrayList<Enrollment> allEnrollments = databaseConnection.retrieveEnrollments();
                Course course = (Course) controller.giveIdentifierReturnObject(courseName, "Course");
                int count = 0;

                for (Enrollment e : allEnrollments) {
                    if (e.getCourseId() == course.getId()) {
                        count++;
                    }
                }

                ArrayList<ContentItem> allContentItems = databaseConnection.retrieveContentItems();
                if (count != 0) {
                    HashMap<Integer, Integer> progressHashMap = databaseConnection.getProgressForCourse(courseName);


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
                    for (ContentItem c : allContentItems) {
                        if (c.getCourseId() == course.getId()) {
                            progressList.getItems().add(c.getTitle()+ ": 0%");
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            makeVisible(false);
            tFNameSelectCourse.setText("Wrong course name!");
        }
    }
    /** Shows the correct progress for the selected course and gender*/
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
    /** Makes all fields (in)visible*/
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
        lbInterestingCourses.setVisible(bool);
        interestingCourses.setVisible(bool);
    }
    /** Clears all fields*/
    public void clearFields(){
        controller.clear(anchorPane);
    }
    /** Shows courses that might be interesting*/
    public void showInterestingCourses() {
        Random random = new Random();
        ArrayList<Course> courses = databaseConnection.retrieveCourses();
        interestingCourses.getItems().clear();
        ArrayList<Integer> usedNumbers = new ArrayList<>();
        while (true) {
            int j = random.nextInt(courses.size());
            if (!(usedNumbers.contains(j))) {
                if(!courses.get(j).getName().equals(tFNameSelectCourse.getText())){
                    interestingCourses.getItems().add(courses.get(j).getName());
                }
            }
            usedNumbers.add(j);
            if (interestingCourses.getItems().size() == 3) {
                break;
            }
        }
        usedNumbers.clear();
    }
}
