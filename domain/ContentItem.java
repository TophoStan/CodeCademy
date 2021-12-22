package domain;

import java.sql.*;

public abstract class ContentItem {
    private String title;
    private String description;
    private String subject;
    private Date publicationDate;
    private String status;
    private int percentage;

    public ContentItem(String title, String description, String subject, Date publicationDate, String status,
            int percentage) {
        this.title = title;
        this.description = description;
        this.subject = subject;
        this.publicationDate = Date.valueOf(publicationDate.toLocalDate());
        this.status = status;
        this.percentage = 0;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

}