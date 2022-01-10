package controllers;

import domain.ContentItem;
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
    @FXML private Label lBHomeWebcastUrl1;
    @FXML private TitledPane lBHomeWebcastCollapse2;
    @FXML private Label lBHomeWebcastTitle2;
    @FXML private Label lBHomeWebcastViews2;
    @FXML private Label lBHomeWebcastUrl2;
    @FXML private TitledPane lBHomeWebcastCollapse3;
    @FXML private Label lBHomeWebcastTitle3;
    @FXML private Label lBHomeWebcastViews3;
    @FXML private Label lBHomeWebcastUrl3;


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

    public void toCertificate(ActionEvent event) {
        controller.toPage(event, "CertificateAdd");
    }

    public void getTop3Webcasts() {
        ContentItem[] webcastsTop3 = new ContentItem[3];
        databaseConnection.connect();

        try {
            webcastsTop3 = databaseConnection.retrieveTop3Webcasts();

            int webcastTop3Passed = -1;
            for (ContentItem webcast : webcastsTop3) {
                if (webcast == null) {
                    webcastTop3Passed++;
                    Webcast newWebcast = new Webcast();
                    newWebcast.setTitle("No more webcasts");
                    newWebcast.setViews(0);
                    newWebcast.setUrl("");
                    webcastsTop3[webcastTop3Passed] = newWebcast;
                } else {
                    webcastTop3Passed++;
                }
            }
            Webcast  webcast1 = (Webcast) controller.giveIdentifierReturnObject(webcastsTop3[0].getContentItemId(), "Webcast");
            Webcast  webcast2 = (Webcast) controller.giveIdentifierReturnObject(webcastsTop3[1].getContentItemId(), "Webcast");
            Webcast  webcast3 = (Webcast) controller.giveIdentifierReturnObject(webcastsTop3[2].getContentItemId(), "Webcast");
            System.out.println(webcast1.getUrl());

            // first
            lBHomeWebcastCollapse1.setText(webcastsTop3[0].getTitle());
            lBHomeWebcastTitle1.setText(webcastsTop3[0].getTitle());
            lBHomeWebcastViews1.setText("Views: " + String.valueOf(webcast1.getViews()));
            lBHomeWebcastUrl1.setText(webcast1.getUrl());

            // second
            lBHomeWebcastCollapse2.setText(webcastsTop3[1].getTitle());
            lBHomeWebcastTitle2.setText(webcastsTop3[1].getTitle());
            lBHomeWebcastViews2.setText("Views: " + String.valueOf(webcast2.getViews()));
            lBHomeWebcastUrl2.setText(webcast2.getUrl());

            // third
            lBHomeWebcastCollapse3.setText(webcastsTop3[2].getTitle());
            lBHomeWebcastTitle3.setText(webcastsTop3[2].getTitle());
            lBHomeWebcastViews3.setText("Views: " + String.valueOf(webcast3.getViews()));
            lBHomeWebcastUrl3.setText(webcast3.getUrl());

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void getTop3CourseCertificates() {
        Course[] coursesTop3 = new Course[3];
        databaseConnection.connect();

        try {
            coursesTop3 = databaseConnection.retrieveTop3Courses();

            int coursesTop3Passed = -1;
            for (Course course : coursesTop3) {
                if (course == null) {
                    coursesTop3Passed++;
                    Course newCourse = new Course();
                    newCourse.setName("No more certificates");
                    newCourse.setCertificates(0);
                    coursesTop3[coursesTop3Passed] = newCourse;
                } else {
                    coursesTop3Passed++;
                }
            }


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
