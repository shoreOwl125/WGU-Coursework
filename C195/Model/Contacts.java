package Model;

/**
 * Class Contacts.java contains variables and methods for the contacts objects.
 */
public class Contacts {
    private int Contact_ID;
    private String Contact_Name;

    /**
     * Constructor for instantiating new contacts objects
     * @param contact_ID index established by database
     * @param contact_Name is the contact name
     */
    public Contacts(int contact_ID, String contact_Name) {
        this.Contact_ID = contact_ID;
        this.Contact_Name = contact_Name;
    }

    /**
     * @return the contact id / database index
     */
    public int getContact_ID() {
        return Contact_ID;
    }

    /**
     * @return the contact name
     */
    public String getContact_Name() {
        return Contact_Name;
    }

}
