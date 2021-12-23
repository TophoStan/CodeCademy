package domain;

import java.sql.Date;

public class Module extends ContentItem {
    private String version;
    private int contactPersonId;
    private int trackingNumber;

    public Module(String title, String description, String subject, Date publicationDate, String status,
                  int percentage, String version) {
        super(title, description, subject, publicationDate, status, percentage);
        this.version = version;
    }

    public Module() {

    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getContactPersonId() {
        return contactPersonId;
    }

    public void setContactPersonId(int contactPersonId) {
        this.contactPersonId = contactPersonId;
    }

    public int getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(int trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

}