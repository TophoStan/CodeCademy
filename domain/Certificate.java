package domain;

import java.sql.Date;

public class Certificate {

    private Enrollment enrollment;
    private double grade;

    public Certificate(Enrollment enrollment, double grade) {
        this.enrollment = enrollment;
        this.grade = grade;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String toString(){
        return "Course:" + enrollment.getCourseName() + "\nGrade: " + this.grade + "\nEnrollmentDate: " + enrollment.getEnrollmentDate();
    }
    

}
