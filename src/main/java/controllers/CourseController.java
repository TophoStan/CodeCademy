package controllers;

import domain.Course;
import domain.Difficulty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;

import repository.DatabaseConnection;
import java.util.ArrayList;

public class CourseController {

    private Controller controller = new Controller();
    private String[] difficulties = {Difficulty.EASY.toString(), Difficulty.NORMAL.toString(), Difficulty.HARD.toString(), Difficulty.EXPERT.toString()};

    // for course add page
    @FXML ListView courseList;
    @FXML TextField tFCourseAddName;
    @FXML TextField tFCourseAddSubject;
    @FXML TextArea tACourseAddIntroText;
    @FXML ComboBox cBCourseAddDifficulty;

    // for course edit page
    @FXML TextField tFNameEditCourse;
    @FXML Label lBSubjectEditCourse;
    @FXML Label lBIntroTextEditCourse;
    @FXML Label lBDifficultyEditCourse;
    @FXML TextField tFSubjectEditCourse;
    @FXML TextArea tAIntroTextEditCourse;
    @FXML ComboBox cBDifficultyEditCourse;
    @FXML Button btnSubmitEditCourse;
    @FXML ListView coursesList;
    @FXML ListView courseInfoList;

    // for course delete page
    @FXML TextField tFNameDeleteCourse;

    DatabaseConnection databaseConnection = new DatabaseConnection();

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

    public void toCourseEdit(ActionEvent event) {
        controller.toPage(event, "CourseEdit");
    }

    public void toCourseDelete(ActionEvent event) {
        controller.toPage(event,  "CourseDelete");
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

        try {
            databaseConnection.addCourseToDatabase(course);
            showCourses();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void loadDifficulties() {
        try {
            cBCourseAddDifficulty.getItems().clear();
            cBCourseAddDifficulty.getItems().addAll(difficulties);
        } catch (Exception e) {
            System.out.println("Used for edit difficulties");
        }

        try {
            cBDifficultyEditCourse.getItems().clear();
            cBDifficultyEditCourse.getItems().addAll(difficulties);
        } catch (Exception e) {
            System.out.println("Used for add difficulties");
        }
    }

    public void showCourseEditPlace() {
        String courseName = tFNameEditCourse.getText();
        if (checkCourseName(courseName)) {
            lBSubjectEditCourse.setVisible(true);
            lBDifficultyEditCourse.setVisible(true);
            lBIntroTextEditCourse.setVisible(true);
            tFSubjectEditCourse.setVisible(true);
            tAIntroTextEditCourse.setVisible(true);
            cBDifficultyEditCourse.setVisible(true);
            btnSubmitEditCourse.setVisible(true);

            showCourseInfo(courseName);
        } else {
            tFNameEditCourse.setText("Wrong name!");
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
}
