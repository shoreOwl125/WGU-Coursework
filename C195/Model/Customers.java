package Model;

import DAO.DivisionDaoImpl;

/**
 * Class Customers.java contains variables and methods for the customers objects.
 */
public class Customers {
    private int Customer_ID;
    private String Customer_Name;
    private String Address;
    private String Postal_Code;
    private String Phone;
    private int Division_ID;

    /**
     * Constructor for instantiating new customers objects
     * @param customer_ID index established by database
     * @param customer_Name is the customer name
     * @param address is customer address
     * @param postal_Code is customer postal code
     * @param phone is customer phone number
     * @param division_ID is the customer's state/territory/province id
     */
    public Customers(int customer_ID, String customer_Name, String address, String postal_Code, String phone, int division_ID) {
        this.Customer_ID = customer_ID;
        this.Customer_Name = customer_Name;
        this.Address = address;
        this.Postal_Code = postal_Code;
        this.Phone = phone;
        this.Division_ID = division_ID;
    }

    /**
     * @return the customer id / database index
     */
    public int getCustomer_ID() {
        return Customer_ID;
    }

    /**
     * @return the customer name
     */
    public String getCustomer_Name() {
        return Customer_Name;
    }

    /**
     * @return the customer street address
     */
    public String getAddress() {
        return Address;
    }

    /**
     * @return the customer postal code
     */
    public String getPostal_Code() {
        return Postal_Code;
    }

    /**
     * @return the customer phone number
     */
    public String getPhone() {
        return Phone;
    }

    /**
     * @return the customer division id established by database
     */
    public int getDivision_ID() {
        return Division_ID;
    }

    /**
     * Implemented with the assistance of instructor Juan Ruiz. Utilizes the JavaFX PropertyValueFactory
     * method behavior with a call to getDivision in order to return the division object in the DivisionDaoImpl.
     * @return the division object
     */
    public Divisions getDivision() {
        return DivisionDaoImpl.get(Division_ID);
    }

    /**
     * Implemented with the assistance of instructor Juan Ruiz. Utilizes the JavaFX PropertyValueFactory
     * method behavior with a call to getCountry in order to return the country object in the DivisionDaoImpl.
     * @return the country object
     */
    public Countries getCountry() {
        return DivisionDaoImpl.getCountry(Division_ID);
    }
}
