package DAO;

import Model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class CountriesDaoImpl.java has the Create, Read, Update and Delete
 * functionalities for the countries table.
 */
public class CountriesDaoImpl {

    /**
     * @return an ObservableList<Countries> that contains countries objects with all the
     * DB country table data in them
     */
    public static ObservableList<Countries> getAllCountries() {
        ObservableList<Countries> clist = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM Countries";
            PreparedStatement ps = DB_Connection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Countries c = new Countries(countryID, countryName);
                clist.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clist;
    }
}
