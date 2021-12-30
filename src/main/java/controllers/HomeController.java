package controllers;

import domain.Webcast;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import repository.DatabaseConnection;


public class HomeController {
    @FXML TitledPane lBHomeWebcastCollapse1;
    @FXML Label lBHomeWebcastTitle1;
    @FXML Label lBHomeWebcastViews1;
    @FXML TitledPane lBHomeWebcastCollapse2;
    @FXML Label lBHomeWebcastTitle2;
    @FXML Label lBHomeWebcastViews2;
    @FXML TitledPane lBHomeWebcastCollapse3;
    @FXML Label lBHomeWebcastTitle3;
    @FXML Label lBHomeWebcastViews3;

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
}
