package Model;

/**
 * Class Outsourced.java extends class Part.java and contains variables and methods for the Outsourced objects.
 * Outsourced objects are a subclass of Part objects.
 */
public class Outsourced extends Part {
    private String companyName;

    public Outsourced (int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @param companyName the company name to set for Outsourced part special field.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }
}
