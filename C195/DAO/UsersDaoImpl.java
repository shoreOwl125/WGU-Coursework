package DAO;

import Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class UserDaoImpl.java has the Create, Read, Update and Delete
 * functionalities for the user table.
 */
public class UsersDaoImpl {
    /**
     * @param userName a user's name to search in the database users table
     * @return user object with the associated user DB data
     */
    public static Users getUser(String userName) {
        try {
            String sql = "SELECT * FROM Users WHERE User_Name =?";
            PreparedStatement ps = DB_Connection.getConnection().prepareStatement(sql);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("User_ID");
                String userDBName = rs.getString("User_Name");
                String password = rs.getString("Password");
                return new Users(userId, userDBName, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return an ObservableList<Users> that contains users objects with all the
     * DB users table data in them
     */
    public static ObservableList<Users> getAllUsers() {
        ObservableList<Users> allUsers = FXCollections.observableArrayList();
        try {
            String sqlStatement = "SELECT * FROM Users";
            PreparedStatement ps = DB_Connection.getConnection().prepareStatement(sqlStatement);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                int userId = result.getInt("User_ID");
                String userName = result.getString("User_Name");
                String password = result.getString("Password");
                Users usersResult = new Users(userId, userName, password);
                allUsers.add(usersResult);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsers;
    }
}
