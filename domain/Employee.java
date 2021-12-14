package domain;

import java.util.ArrayList;

public class Employee {
    private String name;
    private ArrayList<Certificate> givenCertificates;

    public Employee(String name){
        this.name = name;
        this.givenCertificates = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addGivenCertificate(Certificate certificate){
        this.givenCertificates.add(certificate);
    }
    public void giveCertificate(Student student, Certificate certificate){
        student.addCertificate(certificate);
        addGivenCertificate(certificate);
    }
}
