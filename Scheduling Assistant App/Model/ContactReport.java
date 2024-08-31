package Model;

import java.time.LocalDateTime;

/**
 * Class ContactReport.java contains the variables and methods for the contact report objects. This data is required for the second report.
 */
public class ContactReport {

    String contact;
    int apptId;
    String title;
    String type;
    String desc;
    LocalDateTime start;
    LocalDateTime end;
    int custId;

    /**
     * Constructor for instantiating new contact report objects
     * @param contact is the contact name
     * @param apptId index established by database
     * @param title is the appointment name
     * @param type of appointment
     * @param desc description of appointment
     * @param start LocalDateTime type indicating start date and time
     * @param end LocalDateTime type indicating end date and time
     * @param custId customer index established by database
     */
    public ContactReport(String contact, int apptId, String title, String type, String desc, LocalDateTime start, LocalDateTime end, int custId) {
        this.contact = contact;
        this.apptId = apptId;
        this.title = title;
        this.type = type;
        this.desc = desc;
        this.start = start;
        this.end = end;
        this.custId = custId;
    }
}
