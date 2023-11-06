package DAO;

import Model.Countries;
import Model.Divisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class DivisionDaoImpl.java has the Create, Read, Update and Delete
 * functionalities for the divisions table.
 */
public class DivisionDaoImpl {

    /**
     * @param countryId the country id to filter the divisions by
     * @return an ObservableList<Divisions> that contains divisions objects with the
     * DB divisions table data in them which correspond to a particular country.
     */
    public static ObservableList<Divisions> getAllDivisionsByCountry(int countryId) {
        ObservableList<Divisions> dlist = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM First_Level_Divisions WHERE Country_ID =?";
            PreparedStatement ps = DB_Connection.getConnection().prepareStatement(sql);
            ps.setInt(1, countryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryID = rs.getInt("Country_ID");
                Divisions d = new Divisions(divisionID, divisionName, countryID);
                dlist.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dlist;
    }

    /**
     * @param divisionId a division id to search in the database divisions table
     * @return divisions object with the associated division DB data
     */
    public static Divisions get(int divisionId) {
        try {
            String sql = "SELECT * FROM First_Level_Divisions WHERE DIVISION_ID =?";
            PreparedStatement ps = DB_Connection.getConnection().prepareStatement(sql);
            ps.setInt(1, divisionId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                return new Divisions(divisionId, name, countryId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param divisionId a division id to get the country id to search in
     * the database countries table
     * @return countries object with the associated country DB data
     */
    public static Countries getCountry(int divisionId) {
        try {
            int countryId = get(divisionId).getCountryId();
            String sql = "SELECT * FROM Countries WHERE Country_ID =?";
            PreparedStatement ps = DB_Connection.getConnection().prepareStatement(sql);
            ps.setInt(1, countryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("Country");
                return new Countries(countryId, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
