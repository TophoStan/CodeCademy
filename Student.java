
import java.sql.Date;
import java.util.ArrayList;

public class Student {
    private String emailAddress;
    private String name;
    private String gender;
    private Date birthDate;
    private String street;
    private String houseNumber;
    private String postalCode;
    private String city;
    private String country;
    private ArrayList<Certificate> certificates;

    public Student(String emailAddress, String name, String gender, Date birthDate, String street, String houseNumber,
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

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
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

<<<<<<< HEAD
    public ArrayList<Certificate> getCertificates() {
        return certificates;
    }

    public void addCertificate(Certificate certificate){
        certificates.add(certificate);
    }
    

=======
    public void testStijn() {
        System.out.println("Stijn");        
    }

>>>>>>> 1fe0d4bb1101410635a8ced8942e11505b18af7e
}