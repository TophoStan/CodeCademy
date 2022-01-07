package controllers;

import domain.*;
import domain.Module;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;

import repository.DatabaseConnection;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class EnrollmentController {

    private Controller controller = new Controller();

    @FXML private TextField tFEmailEnrollment;
    @FXML private ComboBox cbCourseEnrollment;
    @FXML private TextField tFEmailEnrollmentDelete;
    @FXML private ComboBox cbCourseEnrollmentDelete;
    @FXML private ComboBox cbDateEnrollment;
    @FXML private ListView enrollmentList;
    @FXML private ComboBox cbCourseEnrollmentToEdit;
    @FXML private Label lbCourseEdit;
    @FXML private Label lbDateEdit;
    @FXML private TextField tfDateDay;
    @FXML private TextField tfDateMonth;
    @FXML private TextField tfDateYear;
    @FXML private Button btnEditEnrollment;

    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private Course selectedCourse = new Course();
    private ArrayList<Enrollment> enrollmentsOfStudent = new ArrayList<>();
    private ArrayList<Course> enrolledCoursesOfStudent = new ArrayList<>();
    private Enrollment enrollmentFromDatabaseThatWillBeEdited = new Enrollment();

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

    public void toDelete(ActionEvent event){
        controller.toPage(event, "EnrollmentDelete");
    }
    public void toEdit(ActionEvent event){ controller.toPage(event, "EnrollmentEdit");}

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
        System.out.println(enrollment);
        addProgresses(enrollment);
        tFEmailEnrollment.clear();
        cbCourseEnrollment.getItems().clear();
        listEnrollments();
    }

    public void addProgresses(Enrollment enrollment) {
        try {
            databaseConnection.connect();
            Course course = (Course) controller.giveIdentifierReturnObject(enrollment.getCourseId(), "Course");

            ArrayList<Module> modules = databaseConnection.retrieveModules();

            for (Module module : modules) {
                ContentItem contentItem = (ContentItem) controller.giveIdentifierReturnObject(module.getContentItemId(), "ContentItem");
                if (contentItem.getCourseId() == course.getId()) {

                    Progress progress = new Progress();
                    progress.setStudentId(enrollment.getStudentId());
                    progress.setContentItemId(module.getContentItemId());
                    progress.setPercentage(0);

                    databaseConnection.addProgress(progress);
                    System.out.println(module.getTitle() + ": progress added");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
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

            enrollmentList.getItems().clear();
            for (Enrollment enrollment : databaseConnection.retrieveEnrollments()) {
                enrollmentList.getItems().add(enrollment.toString());
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }
    public void addCoursesToComboBoxOfStudent(){
        try {
            databaseConnection.connect();
            Student selectedStudent = new Student();
            this.enrollmentsOfStudent = new ArrayList<>();

            for (Student studentFromList : databaseConnection.retrieveStudents()) {
                if(studentFromList.getEmailAddress().equals(tFEmailEnrollmentDelete.getText())){
                    selectedStudent = studentFromList;
                }
            }
            //Gets all the enrollments of the student
            cbCourseEnrollmentDelete.getItems().clear();
            for (Enrollment enrollment: databaseConnection.retrieveEnrollments()) {
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

            //Turns the selected course into a Student Object
            studentToDelete = (Student) controller.giveIdentifierReturnObject(tFEmailEnrollmentDelete.getText(), "Student");

            enrollmentToDelete.setStudent(studentToDelete);
            ArrayList<Enrollment> enrollments = databaseConnection.retrieveEnrollments();

            for (Enrollment enrollment: enrollments) {
                if(enrollment.getCourseName().equals(enrollmentToDelete.getCourseName()) && enrollment.getStudentName().equals(enrollmentToDelete.getStudentName()) && enrollment.getEnrollmentDate().toString().equals(enrollmentToDelete.getEnrollmentDate().toString())){
                    databaseConnection.deleteEnrollment(enrollment);
                    System.out.println("Enrollment deleted");
                    deleteProgresses(enrollment);
                    break;
                }
            }
            tFEmailEnrollmentDelete.clear();
            cbDateEnrollment.getItems().clear();
            cbCourseEnrollmentDelete.getItems().clear();
            listEnrollments();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void deleteProgresses(Enrollment enrollment) {
        Course course = (Course) controller.giveIdentifierReturnObject(enrollment.getCourseId(), "Course");
        try {
            int contentItemID = -1;
            for (Module module : databaseConnection.retrieveModules()) {
                ContentItem contentItem = (ContentItem) controller.giveIdentifierReturnObject(module.getContentItemId(), "ContentItem");

                if (contentItem.getCourseId() == course.getId()) {
                    contentItemID = contentItem.getContentItemId();
                    for (Progress progress : databaseConnection.retrieveProgress()) {
                        if (progress.getContentItemId() == contentItemID && progress.getStudentId() == enrollment.getStudentId()) {
                            databaseConnection.deleteProgress(progress);
                            System.out.println("Progress deleted");
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void editEnrollment(){
        databaseConnection.connect();
        Date date = convertDate(Integer.parseInt(tfDateDay.getText()),Integer.parseInt(tfDateMonth.getText()), Integer.parseInt(tfDateYear.getText()));
        Enrollment enrollmentToEdit = new Enrollment();
        Course courseToEdit = new Course();
        Student studentToEdit = new Student();
        try {
            //Turns the selected course into a Course object
            courseToEdit = (Course) controller.giveIdentifierReturnObject(cbCourseEnrollmentToEdit.getValue().toString(), "Course");
            //Turns the selected course into a Student object
            studentToEdit = (Student) controller.giveIdentifierReturnObject(tFEmailEnrollmentDelete.getText(), "Student");

            enrollmentToEdit.setCourse(courseToEdit);
            enrollmentToEdit.setStudent(studentToEdit);
            enrollmentToEdit.setEnrollmentDate(date);
            enrollmentToEdit.setEnrollmentId(enrollmentFromDatabaseThatWillBeEdited.getEnrollmentId());
            databaseConnection.editEnrollment(enrollmentToEdit);
            tFEmailEnrollmentDelete.clear();
            cbCourseEnrollmentDelete.getItems().clear();
            cbDateEnrollment.getItems().clear();
            setVisible(false);
            listEnrollments();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void checkEnrollment() {
        Student studentToCheck = new Student();
        Enrollment enrollmentToCheck = new Enrollment();

        databaseConnection.connect();
        try {
            Date date = Date.valueOf(cbDateEnrollment.getValue().toString());
            enrollmentToCheck.setEnrollmentDate(date);
            enrollmentToCheck.setCourse(selectedCourse);

            studentToCheck = (Student) controller.giveIdentifierReturnObject(tFEmailEnrollmentDelete.getText(), "Student");

            enrollmentToCheck.setStudent(studentToCheck);
            ArrayList<Enrollment> enrollments = databaseConnection.retrieveEnrollments();

            for (Enrollment enrollment: enrollments) {
                setVisible(false);

                if(enrollment.getCourseName().equals(enrollmentToCheck.getCourseName()) && enrollment.getStudentName().equals(enrollmentToCheck.getStudentName()) && enrollment.getEnrollmentDate().toString().equals(enrollmentToCheck.getEnrollmentDate().toString())){
                    setVisible(true);
                    enrollmentFromDatabaseThatWillBeEdited = enrollment;
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addAllCoursesToCombobox() {
        databaseConnection.connect();
        try {
            cbCourseEnrollmentToEdit.getItems().clear();
            for (Course course : databaseConnection.retrieveCourses()) {
                cbCourseEnrollmentToEdit.getItems().add(course.getName());
            }
        } catch (Exception e) {

        }
    }
    public void setVisible(boolean truOrFalse){
        if(truOrFalse){
            cbCourseEnrollmentToEdit.setVisible(true);
            lbCourseEdit.setVisible(true);
            lbDateEdit.setVisible(true);
            tfDateDay.setVisible(true);
            tfDateMonth.setVisible(true);
            tfDateYear.setVisible(true);
            btnEditEnrollment.setVisible(true);
        } else {
            cbCourseEnrollmentToEdit.setVisible(false);
            lbCourseEdit.setVisible(false);
            lbDateEdit.setVisible(false);
            tfDateDay.setVisible(false);
            tfDateMonth.setVisible(false);
            tfDateYear.setVisible(false);
            btnEditEnrollment.setVisible(false);
        }
    }

    public Date convertDate(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());
        return date;
    }




}
