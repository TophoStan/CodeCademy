package domain;

import java.util.Date;

public class Certificate {
    private Date enrollmentDate;
    private double grade;
    private String courseName;
    private String studentName;

    public Certificate(Date enrollmentDate, double grade, String courseName, String studentName) {
        this.enrollmentDate = enrollmentDate;
        this.grade = grade;
        this.courseName = courseName;
        this.studentName = studentName;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

}
