package Model;

/**
 * Class DivisionReport.java contains the variables and methods for the division report objects. This data is required for the third report.
 */
public class DivisionReport {

    private String contactName;
    private String divisionName;
    private String countryName;
    private int numAppts;

    /**
     * Constructor for instantiating new division report objects
     * @param contactName is the contact name
     * @param divisionName is the division name
     * @param countryName is the country name
     * @param numAppts is number of appointments for contact in a division
     */
    public DivisionReport(String contactName, String divisionName, String countryName, int numAppts) {
        this.contactName = contactName;
        this.divisionName = divisionName;
        this.countryName = countryName;
        this.numAppts = numAppts;
    }
}
