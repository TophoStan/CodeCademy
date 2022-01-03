package domain;

import java.util.ArrayList;

public class ContactPerson {
    private String name;
    private String emailAddress;
    private int id;
    private ArrayList<Module> modules;

    public ContactPerson(String name, String emailAddress) {
        this.name = name;
        this.emailAddress = emailAddress;
        modules = new ArrayList<>();
    }

    public ContactPerson() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public void removeModuleFromContactPerson(Module module) {
        this.modules.remove(module);
    }


}
