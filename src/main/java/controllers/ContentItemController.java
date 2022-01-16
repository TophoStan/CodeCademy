package controllers;

import domain.*;
import domain.Module;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import repository.DatabaseConnection;
import validation.Validator;

import java.lang.reflect.Array;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/** Controller of the ContentItem view*/
public class ContentItemController {
    @FXML private ComboBox cbState;
    @FXML private TextField tfTitle;
    @FXML private TextArea taDescription;
    @FXML private ComboBox cbCourses;
    @FXML private ToggleGroup type;
    @FXML private RadioButton radWebcast;
    @FXML private RadioButton radModule;
    @FXML private Label failedURL;

    @FXML private Label lblVersion;
    @FXML private TextField tfVersion;
    @FXML private Label lblContactPerson;
    @FXML private ComboBox cbContactPerson;

    @FXML private Label lblURL;
    @FXML private TextField tfURL;
    @FXML private ComboBox cbSpeaker;
    @FXML private Label lblSpeaker;
    @FXML private Label lbDuration;
    @FXML private TextField tfDuration;

    @FXML private ListView listView;

    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private Controller controller = new Controller(databaseConnection);
    private Validator validator = new Validator();

    /**
     * This methode uses the Controller to go to a page.
     */
    public void toHome(ActionEvent event) {
        controller.toPage(event, "Home");
    }
    /**
     * This methode uses the Controller to go to a page.
     */
    public void toStudent(ActionEvent event){
        controller.toPage(event, "StudentAdd");
    }
    /**
     * This methode uses the Controller to go to a page.
     */
    public void toCourse(ActionEvent event) {
        controller.toPage(event, "CourseAdd");
    }
    /**
     * This methode uses the Controller to go to a page.
     */
    public void toEnrollment(ActionEvent event) {
        controller.toPage(event, "EnrollmentAdd");
    }
    /**
     * This methode uses the Controller to go to a page.
     */
    public void toContentItem(ActionEvent event) {
        controller.toPage(event, "ContentItemAdd");
    }
    /**
     * This methode uses the Controller to go to a page.
     */
    public void toCertificate(ActionEvent event) {
        controller.toPage(event, "CertificateAdd");
    }

    /**
     * Creates a content item with all the necessary information and
     * passes it to the databaseConnection.
     */
    public void addContentItem() {
        failedURL.setText("");
        Course course = new Course();
        if (!(cbCourses.getValue() == null)) {
            for (Course courseFromList : databaseConnection.retrieveCourses()) {
                if (cbCourses.getValue().toString().equals(courseFromList.getName())) {
                    course = courseFromList;
                    break;
                }
            }
        }

        ContentItem contentItem = new ContentItem() {};

        contentItem.setStatus(Status.valueOf(cbState.getValue().toString()));
        if (!(cbCourses.getValue() == null)) {
            contentItem.setCourseId(course.getId());
        }
        contentItem.setDescription(taDescription.getText());
        contentItem.setTitle(tfTitle.getText());
        Date date = Date.valueOf(LocalDate.now());
        contentItem.setPublicationDate(date);

        if(type.getSelectedToggle().equals(radModule)){
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
            if (validator.isURLValid(tfURL.getText())) {
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

                webcast.setUrl(tfURL.getText());
                webcast.setSpeakerId(speaker.getId());
                webcast.setContentItemId(id);
                webcast.setCourseId(courseId);
                webcast.setTitle(tfTitle.getText());
                webcast.setDuration(Integer.parseInt(tfDuration.getText()));
                webcast.setViews(0);
                databaseConnection.addWebcast(webcast);
            } else {
                failedURL.setText("Invalid URL");
            }
        }
        cbState.getItems().clear();
        tfTitle.clear();
        taDescription.clear();
        cbCourses.getItems().clear();
        cbContactPerson.getItems().clear();
        cbSpeaker.getItems().clear();
        tfURL.clear();
        tfVersion.clear();
        tfDuration.clear();
    }

    /**
     * Adds courses to a ComboBox.
     */
    public void addValuesTocbCourses() {
        databaseConnection.connect();
        ArrayList<String> coursesWithWebcasts = new ArrayList<>();
        for (Map.Entry<Course, ArrayList<ContentItem>> e: contentItemsWithCourse().entrySet()){
            for (ContentItem contentItem : e.getValue()) {
                for (Webcast webcast: databaseConnection.retrieveWebcasts()) {
                    if(contentItem.getContentItemId() == webcast.getContentItemId()){
                        coursesWithWebcasts.add(e.getKey().getName());
                    }
                }
            }
        }
        cbCourses.getItems().clear();
        for (Course course: databaseConnection.retrieveCourses()) {
            if(!coursesWithWebcasts.contains(course.getName())){
                cbCourses.getItems().add(course.getName());
            }
        }
    }

    /**
     * Adds States to a ComboBox.
     */
    public void addValuesTocbState(){
        cbState.getItems().clear();

        cbState.getItems().add(Status.ACTIVE.toString());
        cbState.getItems().add(Status.ARCHIVED.toString());
        cbState.getItems().add(Status.CONCEPT.toString());
    }
    /**
     * Adds Speakers to a ComboBox.
     */
    public void addValuesTocbSpeaker(){
        cbSpeaker.getItems().clear();
        databaseConnection.connect();
        for (Speaker speaker: databaseConnection.retrieveSpeakers()) {
            cbSpeaker.getItems().add(speaker.getName());
        }
    }
    /**
     * Adds ContactPersons to a ComboBox.
     */
    public void addValuesTocbContactPerson(){
        cbContactPerson.getItems().clear();
        databaseConnection.connect();
        for (ContactPerson person : databaseConnection.retrieveContactPersons()) {
            cbContactPerson.getItems().add(person.getName());

        }
    }

    /**
     * Sets fields and labels on visible or on invisible.
     */
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
            lbDuration.setVisible(true);
            tfDuration.setVisible(true);

            lblVersion.setVisible(false);
            tfVersion.setVisible(false);
            lblContactPerson.setVisible(false);
            cbContactPerson.setVisible(false);
        }
    }

    /**
     * Makes a hashmap with courses and the content items who belong to the course.
     * @return HashMap with Courses and ContentItems in an ArrayList
     */
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

    /**
     * Fills a listview with content items and if it is a
     * webcast or module.
     */
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

