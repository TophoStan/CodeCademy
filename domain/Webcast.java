package domain;

import java.util.Date;

public class Webcast extends ContentItem {
    private int duration;
    private String url;

    public Webcast(String title, String description, String subject, Date publicationDate, String status,
            int percentage, int duration, String url)
            
            {
        super(title, description, subject, publicationDate, status, percentage);
        this.duration = duration;
        this.url = url;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    


}
