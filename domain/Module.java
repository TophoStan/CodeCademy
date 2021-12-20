package domain;

import java.sql.Date;

public class Module extends ContentItem {
    private String version;

    public Module(String title, String description, String subject, Date publicationDate, String status,
            int percentage, String version) {
        super(title, description, subject, publicationDate, status, percentage);
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
