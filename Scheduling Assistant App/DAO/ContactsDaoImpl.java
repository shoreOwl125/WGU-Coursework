package DAO;

import Model.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class ContactsDaoImpl.java has the Create, Read, Update and Delete
 * functionalities for the contacts table.
 */
public class ContactsDaoImpl {

    /**
     * @return an ObservableList<Contacts> that contains contacts objects with all the
     * DB contacts table data in them
     */
    public static ObservableList<Contacts> getAllContacts() {
        ObservableList<Contacts> clist = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM Contacts";
            PreparedStatement ps = DB_Connection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                Contacts c = new Contacts(contactID, contactName);
                clist.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clist;
    }
}
