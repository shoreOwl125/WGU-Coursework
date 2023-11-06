package DAO;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Class DB_Connection.java has the Open, Get, Close connection
 * functionalities for the database.
 */
public class DB_Connection {
    private static final String DB_Name = "client_schedule";
    private static final String DB_Url = "jdbc:mysql://localhost:3306/" + DB_Name + "?connectionTimeZone=SERVER";
    private static final String userName = "sqlUser";
    private static final String password = "Passw0rd!";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static Connection conn;

    /**
     * Establish connection with DB server. When successful prints connection successful.
     */
    public static void openConnection() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(DB_Url, userName, password);
            System.out.println("Connection Successful");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @return conn is open connection to database
     */
    public static Connection getConnection() {
        return conn;
    }

    /**
     * Close connection to database
     */
    public static void closeConnection() {
        try {
            conn.close();
        } catch (Exception ignored) { }
    }
}
