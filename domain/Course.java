package domain;

import java.util.ArrayList;

public class Course {
    private int id;
    private int trackingNumber;
    private String name;
    private String subject;
    private String text;
    private Difficulty difficulty;
    private ArrayList<Module> modules;
    private ArrayList<Webcast> webcast;
    private ArrayList<Enrollment> enrollments;

    public Course(String name, String subject, String text, Difficulty difficulty) {
        this.name = name;
        this.subject = subject;
        this.text = text;
        this.difficulty = difficulty;
        this.modules = new ArrayList<>();
        this.webcast = new ArrayList<>();
    }
    public Course(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void addWebcastToCourse(Webcast webcast) {
        this.webcast.add(webcast);
    }

    public void removeWebcastFromCourse(Webcast webcast) {
        this.webcast.remove(webcast);
    }

    public void addModuleCourse(Module module) {
        this.modules.add(module);
    }

    public void removeModuleFromCourse(Module module) {
        this.modules.remove(module);
    }
    public ArrayList<Enrollment> listEnrollments(){
        return this.enrollments;
    }
    public void addEnrolledStudentToCourse(Student student, Enrollment enrollment){
        student.addEnrollment(enrollment);
        this.enrollments.add(enrollment);
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setTrackingNumber(int trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
    public int getTrackingNumber() {
        return trackingNumber;
    }

}
