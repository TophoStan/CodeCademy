package domain;

import java.sql.Date;

public class Enrollment {
    private int studentId;
    private int courseId;
    private int enrollmentId;
    private Date enrollmentDate;
    private Course course;
    private Student student;

    public Enrollment(Course course, Student student) {
        this.course = course;
        this.student = student;
        Date date = new Date(0);
        date = Date.valueOf(date.toLocalDate());
    }

    public Enrollment() {

    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
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

}
