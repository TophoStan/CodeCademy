package domain;

import java.sql.Date;

public class Webcast extends ContentItem {
    private int duration;
    private String url;
    private int speakerId;
    private int views;

    public Webcast(String title, String description, String subject, Date publicationDate, String status,
                   int percentage, int duration, String url)

    {
        super(title, description, subject, publicationDate, status, percentage);
        this.duration = duration;
        this.url = url;
        this.views = 0;
    }

    public Webcast() {

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

    public void addView() {
        this.views++;
    }

    public int getSpeakerId() {
        return speakerId;
    }

    public void setSpeakerId(int speakerId) {
        this.speakerId = speakerId;
    }
    public int getViews() {
        return views;
    }
    public void setViews(int views) {
        this.views = views;
    }
}
