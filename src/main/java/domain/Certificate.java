package domain;
/** Certificate class*/
public class Certificate {
    private int enrollmentId;
    private Enrollment enrollment;
    private double grade;
    private int employeeId;
    private Employee employee;

    public Certificate(Enrollment enrollment, double grade) {
        this.enrollment = enrollment;
        this.grade = grade;
    }

    public Certificate() {

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


    public int getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

}
