package controllers;

import domain.*;
import domain.Module;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import repository.DatabaseConnection;

import java.lang.reflect.Array;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class ContentItemController {
    @FXML private ComboBox cbState;
    @FXML private TextField tfTitle;
    @FXML private TextArea taDescription;
    @FXML private ComboBox cbCourses;
    @FXML private ToggleGroup type;
    @FXML private RadioButton radWebcast;
    @FXML private RadioButton radModule;

    @FXML private Label lblVersion;
    @FXML private TextField tfVersion;
    @FXML private Label lblContactPerson;
    @FXML private ComboBox cbContactPerson;

    @FXML private Label lblURL;
    @FXML private TextField tfURL;
    @FXML private ComboBox cbSpeaker;
    @FXML private Label lblSpeaker;

    @FXML private ListView listView;

    Controller controller = new Controller();
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

    public void toCertificate(ActionEvent event) {
        controller.toPage(event, "CertificateAdd");
    }

    public void addContentItem(){
        Course course = new Course();
        for (Course courseFromList : databaseConnection.retrieveCourses()) {
            if (cbCourses.getValue().toString().equals(courseFromList.getName())) {
                course = courseFromList;
                break;
            }
        }

        ContentItem contentItem = new ContentItem() {};
        contentItem.setStatus(Status.valueOf(cbState.getValue().toString()));
        contentItem.setCourseId(course.getId());
        contentItem.setDescription(taDescription.getText());
        contentItem.setTitle(tfTitle.getText());
        Date date = Date.valueOf(LocalDate.now());
        contentItem.setPublicationDate(date);

        databaseConnection.addContentItem(contentItem);



        int id = 0;
        int courseId = 0;
        for (ContentItem contentItemFromDatabase:databaseConnection.retrieveContentItems()) {
            if(contentItemFromDatabase.getTitle().equals(contentItem.getTitle())){
                id = contentItemFromDatabase.getContentItemId();
                courseId = contentItemFromDatabase.getCourseId();
                break;
            }
        }

        if(type.getSelectedToggle().equals(radModule)){

            ContactPerson contactPerson = new ContactPerson();
            for (ContactPerson contactPersonFromDatabase : databaseConnection.retrieveContactPersons()) {
                if(cbContactPerson.getValue().toString().equals(contactPersonFromDatabase.getName())){
                    contactPerson = contactPersonFromDatabase;
                    break;
                }
            }

            Module module = new Module();
            module.setContentItemId(id);
            module.setCourseId(courseId);
            module.setTitle(tfTitle.getText());
            module.setVersion(tfVersion.getText());
            module.setContactPersonId(contactPerson.getId());
            databaseConnection.addModule(module);
        } else {

            Speaker speaker = new Speaker();
            for (Speaker speakerFromDatabase: databaseConnection.retrieveSpeakers()) {
                if(cbSpeaker.getValue().toString().equals(speakerFromDatabase.getName())){
                    speaker =speakerFromDatabase;
                    break;
                }
            }

            Webcast webcast = new Webcast();
            webcast.setUrl(tfURL.getText());
            webcast.setSpeakerId(speaker.getId());
            webcast.setContentItemId(id);
            webcast.setCourseId(courseId);
            webcast.setTitle(tfTitle.getText());
            webcast.setViews(0);
            databaseConnection.addWebcast(webcast);
        }
        cbState.getItems().clear();
        tfTitle.clear();
        taDescription.clear();
        cbCourses.getItems().clear();
        cbContactPerson.getItems().clear();
        cbSpeaker.getItems().clear();
        tfURL.clear();
        tfVersion.clear();
    }

    public void addValuesTocbCourses() {
        databaseConnection.connect();
        cbCourses.getItems().clear();
        for (Course course : databaseConnection.retrieveCourses()) {
            cbCourses.getItems().add(course.toString());
        }
    }
    public void addValuesTocbState(){
        cbState.getItems().clear();

        cbState.getItems().add(Status.ACTIVE.toString());
        cbState.getItems().add(Status.ARCHIVED.toString());
        cbState.getItems().add(Status.CONCEPT.toString());
    }
    public void addValuesTocbSpeaker(){
        cbSpeaker.getItems().clear();
        databaseConnection.connect();
        for (Speaker speaker: databaseConnection.retrieveSpeakers()) {
            cbSpeaker.getItems().add(speaker.getName());
        }
    }
    public void addValuesTocbContactPerson(){
        cbContactPerson.getItems().clear();
        databaseConnection.connect();
        for (ContactPerson person : databaseConnection.retrieveContactPersons()) {
            cbContactPerson.getItems().add(person.getName());

        }
    }

    public void setVisible(){
        if(type.getSelectedToggle().equals(radModule)){
            lblVersion.setVisible(true);
            tfVersion.setVisible(true);
            lblContactPerson.setVisible(true);
            cbContactPerson.setVisible(true);

            lblURL.setVisible(false);
            tfURL.setVisible(false);
            cbSpeaker.setVisible(false);
            lblSpeaker.setVisible(false);

        } else if(type.getSelectedToggle().equals(radWebcast)){
            lblURL.setVisible(true);
            tfURL.setVisible(true);
            cbSpeaker.setVisible(true);
            lblSpeaker.setVisible(true);

            lblVersion.setVisible(false);
            tfVersion.setVisible(false);
            lblContactPerson.setVisible(false);
            cbContactPerson.setVisible(false);
        }
    }



    public void listContentItems(){
        listView.getItems().clear();
        databaseConnection.connect();


        for (ContentItem contentItem : databaseConnection.retrieveContentItems()) {
            String sort = "";
            for (Webcast webcast : databaseConnection.retrieveWebcasts()) {
                if (webcast.getContentItemId() == contentItem.getContentItemId()) {
                    sort = "W";
                    break;
                } else {
                    sort = "M";
                }
            }
            listView.getItems().add(contentItem.getTitle() + " - " + sort);
        }
    }
}

