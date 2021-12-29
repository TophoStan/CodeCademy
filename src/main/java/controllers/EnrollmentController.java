package controllers;

import domain.Course;
import domain.Enrollment;
import domain.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import repository.DatabaseConnection;

import java.sql.Date;
import java.util.ArrayList;

public class EnrollmentController {

    private Controller controller = new Controller();

    @FXML private TextField tFEmailEnrollment;
    @FXML private ComboBox cbCourseEnrollment;
    @FXML private TextField tFEmailEnrollmentDelete;
    @FXML private ComboBox cbCourseEnrollmentDelete;
    @FXML private ComboBox cbDateEnrollment;
    @FXML private ListView enrollmentList;

    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private Course selectedCourse = new Course();
    private ArrayList<Enrollment> enrollmentsOfStudent = new ArrayList<>();
    private ArrayList<Course> enrolledCoursesOfStudent = new ArrayList<>();

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
    public void toDelete(ActionEvent event){
        controller.toPage(event, "EnrollmentDelete");
    }

    public void addEnrollment(){
        databaseConnection.connect();
        Enrollment enrollment = new Enrollment();
        try {
            ArrayList<Student> students = databaseConnection.retrieveStudents();
            ArrayList<Course> courses = databaseConnection.retrieveCourses();
            for (Student student: students) {
                if(student.getEmailAddress().equals(tFEmailEnrollment.getText())){
                    enrollment.setStudent(student);
                }
            }
            for (Course course: courses) {
                if(course.getName().equals(cbCourseEnrollment.getValue())){
                    enrollment.setCourse(course);
                }
            }

        }catch (Exception e) {
            System.out.println(e);
        }
        databaseConnection.addEnrollment(enrollment);
        tFEmailEnrollment.clear();
        cbCourseEnrollment.getItems().clear();
    }

    public void addValuesToComboBox() {
        databaseConnection.connect();
        ArrayList<Course> courses = databaseConnection.retrieveCourses();
        cbCourseEnrollment.getItems().clear();
        for (Course course : courses) {
            cbCourseEnrollment.getItems().add(course.getName());
        }
    }

    public void listEnrollments(){
        databaseConnection.connect();
        try {
            ArrayList<Enrollment> enrollments = new ArrayList<>();
            enrollments = databaseConnection.retrieveEnrollments();
            enrollmentList.getItems().clear();
            for (Enrollment enrollment : enrollments) {
                enrollmentList.getItems().add(enrollment.toString());
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }
    public void addCoursesToComboBoxOfStudent(){
        databaseConnection.connect();
        Student selectedStudent = new Student();

        try {
            ArrayList<Student> students = databaseConnection.retrieveStudents();
            ArrayList<Enrollment> enrollments = databaseConnection.retrieveEnrollments();

            this.enrollmentsOfStudent = new ArrayList<>();

            for (Student studentFromList : students) {
                if(studentFromList.getEmailAddress().equals(tFEmailEnrollmentDelete.getText())){
                    selectedStudent = studentFromList;
                }
            }
            //Gets all the enrollments of the student
            cbCourseEnrollmentDelete.getItems().clear();
            for (Enrollment enrollment: enrollments) {
                if(enrollment.getStudentId() == selectedStudent.getId()){
                    enrolledCoursesOfStudent.add(enrollment.getCourse());
                    cbCourseEnrollmentDelete.getItems().add(enrollment.getCourse().getName());
                    this.enrollmentsOfStudent.add(enrollment);
                }
            }

        } catch (Exception e){

            e.printStackTrace();
            System.out.println(e);
        }
    }
    public void addDatesToComboBoxOfCourse(){

        //Sets the selected course into a course Object
        for (Course course: enrolledCoursesOfStudent) {
            if(course.getName().equals(cbCourseEnrollmentDelete.getValue().toString())){
                this.selectedCourse = course;

            }
        }
        cbDateEnrollment.getItems().clear();
        for (Enrollment enrollment : enrollmentsOfStudent) {
            if(enrollment.getCourseId() == selectedCourse.getId()){
                cbDateEnrollment.getItems().add(enrollment.getEnrollmentDate().toString());
            }
        }
    }

    public void deleteEnrollment(){
        databaseConnection.connect();
        Enrollment enrollmentToDelete = new Enrollment();
        Student studentToDelete = new Student();

        try {
            Date date = Date.valueOf(cbDateEnrollment.getValue().toString());
            enrollmentToDelete.setEnrollmentDate(date);
            enrollmentToDelete.setCourse(selectedCourse);

            ArrayList<Student> students = databaseConnection.retrieveStudents();

            for (Student studentFromList : students) {
                if(studentFromList.getEmailAddress().equals(tFEmailEnrollmentDelete.getText())){
                    studentToDelete = studentFromList;
                }
            }
            enrollmentToDelete.setStudent(studentToDelete);
            ArrayList<Enrollment> enrollments = databaseConnection.retrieveEnrollments();

            for (Enrollment enrollment: enrollments) {

                if(enrollment.getCourseName().equals(enrollmentToDelete.getCourseName()) && enrollment.getStudentName().equals(enrollmentToDelete.getStudentName()) && enrollment.getEnrollmentDate().toString().equals(enrollmentToDelete.getEnrollmentDate().toString())){
                   databaseConnection.deleteEnrollment(enrollment);
                }
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }


}
