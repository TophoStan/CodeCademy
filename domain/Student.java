package domain;

import java.sql.Date;

import repository.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class Student {
    private int id;
    private String emailAddress;
    private String name;
    private String gender;
    private Date birthDate;
    private String street;
    private int houseNumber;
    private String postalCode;
    private String city;
    private String country;
    private ArrayList<Certificate> certificates;
    private ArrayList<Enrollment> enrollments;

    public Student(String emailAddress, String name, String gender, Date birthDate, String street, int houseNumber,
            String postalCode, String city, String country) {
        this.emailAddress = emailAddress;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.certificates = new ArrayList<>();
    }

    public Student() {

    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ArrayList<Certificate> getCertificates() {
        return this.certificates;
    }

    public void addCertificate(Certificate certificate) {
        this.certificates.add(certificate);
    }

    public ArrayList<Enrollment> getEnrollments() {
        return this.enrollments;
    }

    public void addEnrollment(Enrollment enrollment) {
        this.enrollments.add(enrollment);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return getName() + ", " + getEmailAddress() + ", " + getGender();
    }

}