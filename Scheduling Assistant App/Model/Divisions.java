package Model;

/**
 * Class Divisions.java contains variables and methods for the Divisions objects.
 */
public class Divisions {
    private int Division_ID;
    private String Division_Name;
    private int Country_ID;

    /**
     * Constructor for instantiating new Divisions objects
     * @param id index established by database
     * @param name is the name of the division
     * @param countryId is the database index of the country the division belongs to
     */
    public Divisions(int id, String name, int countryId) {
        this.Division_ID = id;
        this.Division_Name = name;
        this.Country_ID = countryId;
    }

    /**
     * @return the division id / database index
     */
    public int getId() {
        return Division_ID;
    }

    /**
     * @return the division name
     */
    public String getName() {
        return Division_Name;
    }

    /**
     * @return the database index of the country which the division is a part of
     */
    public int getCountryId() {
        return Country_ID;
    }

    /**
     * Implemented with the assistance of instructor Juan Ruiz. Utilizes the JavaFX PropertyValueFactory method behavior
     * to override the toString method to return the division name from the division object
     * @return the division name
     */
    @Override
    public String toString() {
        return Division_Name;
    }
}
