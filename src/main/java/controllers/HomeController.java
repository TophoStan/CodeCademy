package controllers;

import domain.Course;
import domain.Webcast;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import repository.DatabaseConnection;


public class HomeController {

    // Webcasts
    @FXML private TitledPane lBHomeWebcastCollapse1;
    @FXML private Label lBHomeWebcastTitle1;
    @FXML private Label lBHomeWebcastViews1;
    @FXML private TitledPane lBHomeWebcastCollapse2;
    @FXML private Label lBHomeWebcastTitle2;
    @FXML private Label lBHomeWebcastViews2;
    @FXML private TitledPane lBHomeWebcastCollapse3;
    @FXML private Label lBHomeWebcastTitle3;
    @FXML private Label lBHomeWebcastViews3;

    // Courses with certificates
    @FXML private TitledPane lBHomeCoursesCollapse1;
    @FXML private Label lBHomeCourseTitle1;
    @FXML private Label lBHomeCourseCertificates1;
    @FXML private TitledPane lBHomeCoursesCollapse2;
    @FXML private Label lBHomeCourseTitle2;
    @FXML private Label lBHomeCourseCertificates2;
    @FXML private TitledPane lBHomeCoursesCollapse3;
    @FXML private Label lBHomeCourseTitle3;
    @FXML private Label lBHomeCourseCertificates3;


    private Controller controller = new Controller();
    private DatabaseConnection databaseConnection = new DatabaseConnection();

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

    public void getTop3Webcasts() {
        Webcast[] webcastsTop3 = new Webcast[3];
        databaseConnection.connect();

        try {
            webcastsTop3 = databaseConnection.retrieveTop3Webcasts();

            // first
            lBHomeWebcastCollapse1.setText(webcastsTop3[0].getTitle());
            lBHomeWebcastTitle1.setText(webcastsTop3[0].getTitle());
            lBHomeWebcastViews1.setText("Views: " + String.valueOf(webcastsTop3[0].getViews()));

            // second
            lBHomeWebcastCollapse2.setText(webcastsTop3[1].getTitle());
            lBHomeWebcastTitle2.setText(webcastsTop3[1].getTitle());
            lBHomeWebcastViews2.setText("Views: " + String.valueOf(webcastsTop3[1].getViews()));

            // third
            lBHomeWebcastCollapse3.setText(webcastsTop3[2].getTitle());
            lBHomeWebcastTitle3.setText(webcastsTop3[2].getTitle());
            lBHomeWebcastViews3.setText("Views: " + String.valueOf(webcastsTop3[2].getViews()));

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void getTop3CourseCertificates() {
        Course[] coursesTop3 = new Course[3];
        databaseConnection.connect();

        try {
            coursesTop3 = databaseConnection.retrieveTop3Courses();

            // first
            lBHomeCoursesCollapse1.setText(coursesTop3[0].getName());
            lBHomeCourseTitle1.setText(coursesTop3[0].getName());
            lBHomeCourseCertificates1.setText("Certificates: " + String.valueOf(coursesTop3[0].getCertificates()));
            // second
            lBHomeCoursesCollapse2.setText(coursesTop3[1].getName());
            lBHomeCourseTitle2.setText(coursesTop3[1].getName());
            lBHomeCourseCertificates2.setText("Certificates: " + String.valueOf(coursesTop3[1].getCertificates()));
            // third
            lBHomeCoursesCollapse3.setText(coursesTop3[2].getName());
            lBHomeCourseTitle3.setText(coursesTop3[2].getName());
            lBHomeCourseCertificates3.setText("Certificates: " + String.valueOf(coursesTop3[2].getCertificates()));

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
