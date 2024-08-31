package Model;

import java.time.LocalDateTime;
/**
 * Class Appointments.java contains variables and methods for the appointment objects.
 */
public class Appointments {

    private int Appointment_ID;
    private String Title;
    private String Description;
    private String Location;
    private String Type;
    private LocalDateTime Start;
    private LocalDateTime End;
    private int Customer_ID;
    private int User_ID;
    private int Contact_ID;

    /**
     * Constructor for instantiating new appointment objects
     * @param appointment_ID index established by database
     * @param title is the appointment name
     * @param description of appointment
     * @param location of appointment
     * @param type of appointment
     * @param start LocalDateTime type indicating start date and time
     * @param end LocalDateTime type indicating end date and time
     * @param customer_ID index established by database
     * @param contact_ID index established by database
     * @param user_ID index established by database
     */
    public Appointments(int appointment_ID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customer_ID, int user_ID, int contact_ID) {
        this.Appointment_ID = appointment_ID;
        this.Title = title;
        this.Description = description;
        this.Location = location;
        this.Type = type;
        this.Start = start;
        this.End = end;
        this.Customer_ID = customer_ID;
        this.User_ID = user_ID;
        this.Contact_ID = contact_ID;
    }

    /**
     * @return the appointment id / database index
     */
    public int getAppointment_ID() {
        return Appointment_ID;
    }

    /**
     * @return the appointment title
     */
    public String getTitle() {
        return Title;
    }

    /**
     * @return the appointment description
     */
    public String getDescription() {
        return Description;
    }

    /**
     * @return the appointment location
     */
    public String getLocation() {
        return Location;
    }

    /**
     * @return the appointment type
     */
    public String getType() {
        return Type;
    }

    /**
     * @return the appointment start date and time converted to local user time zone.
     */
    public LocalDateTime getStart() {
        return Start;
    }

    /**
     * @return the appointment end date and time converted to local user time zone.
     */
    public LocalDateTime getEnd() {
        return End;
    }

    /**
     * @return the customer id / database index
     */
    public int getCustomer_ID() {
        return Customer_ID;
    }

    /**
     * @return the user id / database index
     */
    public int getUser_ID() {
        return User_ID;
    }

    /**
     * @return the contact id / database index
     */
    public int getContact_ID() {
        return Contact_ID;
    }
}
