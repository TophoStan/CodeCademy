package domain;

import controllers.Controller;
/** Class for progress*/
public class Progress {
    private int id;
    private ContentItem contentItem;
    private Student student;
    private int percentage;

    public Progress() {
        this.student = new Student();
        this.contentItem = new ContentItem(){};
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContentItemId() {
        return this.contentItem.getContentItemId();
    }

    public void setContentItemId(int contentItemId) {
        this.contentItem.setContentItemId(contentItemId);
    }

    public ContentItem getContentItem() {
        return contentItem;
    }

    public void setContentItem(ContentItem contentItem) {
        this.contentItem = contentItem;
    }

    public int getStudentId() {
        return this.student.getId();
    }

    public void setStudentId(int studentId) {
        this.student.setId(studentId);
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public void correctObjects(Controller controller){

        this.contentItem = (ContentItem)   controller.giveIdentifierReturnObject(this.contentItem.getContentItemId(), "ContentItem");
        this.student = (Student) controller.giveIdentifierReturnObject(this.student.getId(), "Student");
    }

    @Override
    public String toString() {
        return "Progress{" +
                "id=" + id +
                ", contentItem=" + contentItem.getTitle() +
                ", student=" + student.getName() +
                ", percentage=" + percentage +
                '}';
    }
}
