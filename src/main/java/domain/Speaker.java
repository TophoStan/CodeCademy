package domain;

import java.util.ArrayList;
/** Class for speaker*/
public class Speaker {
    private String name;
    private String organisation;
    private int id;
    private ArrayList<Webcast> voicedWebcasts;

    public Speaker(String name, String organisation) {
        this.name = name;
        this.organisation = organisation;
        voicedWebcasts = new ArrayList<>();
    }

    public Speaker() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public ArrayList<Webcast> getVoicedWebcasts() {
        return voicedWebcasts;
    }

    public void addAVoicedWebcast(Webcast webcast) {
        voicedWebcasts.add(webcast);
    }
}
