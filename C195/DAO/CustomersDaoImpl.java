package DAO;

import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class CustomersDaoImpl.java has the Create, Read, Update and Delete
 * functionalities for the customers table.
 */
public class CustomersDaoImpl {

    /**
     * @return an ObservableList<Customers> that contains customers objects with all the
     * DB customer table data in them
     */
    public static ObservableList<Customers> getAllCustomers() {
        ObservableList<Customers> clist = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM Customers";
            PreparedStatement ps = DB_Connection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int Customer_ID = rs.getInt("Customer_ID");
                String Customer_Name = rs.getString("Customer_Name");
                String Address = rs.getString("Address");
                String Postal_Code = rs.getString("Postal_Code");
                String Phone = rs.getString("Phone");
                int Division_ID = rs.getInt("Division_ID");
                Customers c = new Customers(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID);
                clist.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clist;
    }

    /**
     * @param selectedCustomerID a customer's id to search in the database customers table
     * @return customer object with the associated customer DB data
     */
    public static Customers getCustomer(int selectedCustomerID) {
        try {
            String sql = "SELECT * FROM Customers WHERE Customer_ID =?";
            PreparedStatement ps = DB_Connection.getConnection().prepareStatement(sql);
            ps.setInt(1, selectedCustomerID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int Customer_ID = rs.getInt("Customer_ID");
                String Customer_Name = rs.getString("Customer_Name");
                String Address = rs.getString("Address");
                String Postal_Code = rs.getString("Postal_Code");
                String Phone = rs.getString("Phone");
                int Division_ID = rs.getInt("Division_ID");
                return new Customers(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param selectedCustomerID a customer's id to search in the database customers table
     * @return true/false if the customer has any appointments scheduled
     */
    public static boolean hasAppointments (int selectedCustomerID) {
        int count = 0;
        try {
            String sql = "SELECT COUNT(*) Appointment_ID FROM Appointments WHERE Customer_ID =?";
            PreparedStatement ps = DB_Connection.getConnection().prepareStatement(sql);
            ps.setInt(1, selectedCustomerID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("Appointment_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count > 0;
    }

    /**
     * @param selectedCustomerID a customer's id to search in the database customers table
     * @return true/false if the customer has been successfully deleted in the database
     */
    public static boolean deleteCustomer (int selectedCustomerID) {
        try {
            String sql = "DELETE FROM Customers WHERE Customer_ID =?";
            PreparedStatement ps = DB_Connection.getConnection().prepareStatement(sql);
            ps.setInt(1, selectedCustomerID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getCustomer(selectedCustomerID) == null;
    }

    /**
     * @param customer a customer object to pull data from and create new entry in customer DB table
     */
    public static void addCustomer (Customers customer) {
        String name = customer.getCustomer_Name();
        String address = customer.getAddress();
        String postal = customer.getPostal_Code();
        String phone = customer.getPhone();
        int divisionId = customer.getDivision_ID();
        try {
            String sql = "INSERT INTO Customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?,?,?,?,?)";
            PreparedStatement ps = DB_Connection.getConnection().prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postal);
            ps.setString(4, phone);
            ps.setInt(5, divisionId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param customer a customer object to pull data from and update an existing entry in customer DB table
     */
    public static void updateCustomer (Customers customer) {
        String name = customer.getCustomer_Name();
        String address = customer.getAddress();
        String postal = customer.getPostal_Code();
        String phone = customer.getPhone();
        int divisionId = customer.getDivision_ID();
        try {
            String sql = "UPDATE Customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_Id = ?";
            PreparedStatement ps = DB_Connection.getConnection().prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postal);
            ps.setString(4, phone);
            ps.setInt(5, divisionId);
            ps.setInt(6, customer.getCustomer_ID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
