package domain;

import java.sql.Date;
/** Class for enrollment*/
public class Enrollment {
    private int enrollmentId;
    private Date enrollmentDate;
    private Course course;
    private Student student;

    public Enrollment(Course course, Student student) {
        this.course = course;
        this.student = student;
    }

    public Enrollment() {
        this.course = new Course();
        this.student = new Student();
    }

    public int getStudentId() {
        return student.getId();
    }

    public void setStudentId(int studentId) {
        student.setId(studentId);
    }

    public int getCourseId() {
        return course.getId();
    }

    public void setCourseId(int courseId) {
        this.course.setId(courseId);
    }

    public int getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getCourseName() {
        return this.course.getName();
    }

    public String getStudentName() {
        return this.student.getName();
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return student.getEmailAddress() + " - " + course.getName();
    }
}
