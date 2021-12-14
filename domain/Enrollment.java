package domain;

import java.util.Date;

public class Enrollment {
    private Date enrollmentDate;
    private String courseName;
    private String studentName;

    public Enrollment(Date enrollmentDate, String courseName, String studentName) {
        this.enrollmentDate = enrollmentDate;
        this.courseName = courseName;
        this.studentName = studentName;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
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
