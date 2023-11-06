package Model;

/**
 * Class Countries.java contains variables and methods for the Countries objects.
 */
public class Countries {
    private int Country_ID;
    private String Country_Name;

    /**
     * Constructor for instantiating new countries objects
     * @param id index established by database
     * @param name is the country name
     */
    public Countries(int id, String name) {
        this.Country_ID = id;
        this.Country_Name = name;
    }

    /**
     * @return the country id / database index
     */
    public int getId() {
        return Country_ID;
    }

    /**
     * @return the country name
     */
    public String getName() {
        return Country_Name;
    }

    /**
     * Implemented with the assistance of instructor Juan Ruiz. Utilizes the JavaFX PropertyValueFactory method behavior
     * to override the toString method to return the country name from the country object
     * @return the country name
     */
    @Override
    public String toString() {
        return Country_Name;
    }
}
