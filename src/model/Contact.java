package model;

public class Contact {
    private int contactID;
    private String name;

    public Contact(int contactID, String name) {
        this.contactID = contactID;
        this.name = name;
    }

    public int getContactID() {
        return contactID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
