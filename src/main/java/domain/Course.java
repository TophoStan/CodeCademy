package domain;

import java.util.ArrayList;

public class Course {
    private int id;
    private String name;
    private String subject;
    private String text;
    private Difficulty difficulty;
    private int certificates;

    public Course(String name, String subject, String text, Difficulty difficulty) {
        this.name = name;
        this.subject = subject;
        this.text = text;
        this.difficulty = difficulty;
    }

    public Course() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCertificates(int certificates) {
        this.certificates = certificates;
    }

    public int getCertificates() {
        return this.certificates;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
