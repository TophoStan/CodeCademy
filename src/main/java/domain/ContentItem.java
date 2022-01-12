package domain;

import java.sql.*;

public abstract class ContentItem {
    private String title;
    private String description;
    private String subject;
    private Date publicationDate;
    private Status status;
    private int courseId;
    private int contentItemId;

    public ContentItem(String title, String description, String subject, Date publicationDate, Status status) {
        this.title = title;
        this.description = description;
        this.subject = subject;
        this.publicationDate = Date.valueOf(publicationDate.toLocalDate());
        this.status = status;
    }

    public ContentItem() {

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getContentItemId() {
        return contentItemId;
    }

    public void setContentItemId(int contentItemId) {
        this.contentItemId = contentItemId;
    }


}