package controllers;

import domain.ContentItem;
import domain.Course;
import domain.Progress;
import domain.Student;
import javafx.event.ActionEvent;
import repository.DatabaseConnection;

import java.util.ArrayList;
import java.util.HashMap;

public class CertificateController {

    private Controller controller = new Controller();
    private DatabaseConnection databaseConnection = new DatabaseConnection();

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

    public void checkIfCertificateCanBeGiven(){
        databaseConnection.connect();
        try {
            HashMap<Student, ArrayList<Course>> studentsPassedCourses = new HashMap<>();
            for (Progress progress: databaseConnection.retrieveProgress()) {

            }



        } catch (Exception e){
            System.out.println(e);
        }
    }

    public HashMap<Course, ArrayList<ContentItem>> putContentItemsWithCourse(){
        HashMap<Course, ArrayList<ContentItem>> map = new HashMap<>();
        for (Course course : databaseConnection.retrieveCourses()) {
            ArrayList<ContentItem> contentItems = new ArrayList<>();
            for (ContentItem contentItem : databaseConnection.retrieveContentItems()) {
                if(course.getId() == contentItem.getCourseId()){
                    contentItems.add(contentItem);
                }
            }
            map.put(course,contentItems);
        }
        return new HashMap<>();
    }
}
