package Model;

/**
 * Class ApptReport.java contains the variables and methods for the appointment report objects. This data is required for the first report.
 */
public class ApptReport {

    private int count;
    private String type;
    private int month;

    /**
     * Constructor for instantiating new appointment report objects
     * @param count stores the number of appointments
     * @param type of appointment
     * @param month is the numeric value of the month between 1-12
     */
    public ApptReport(int count, String type, int month) {
        this.count = count;
        this.type = type;
        this.month = month;
    }
}
