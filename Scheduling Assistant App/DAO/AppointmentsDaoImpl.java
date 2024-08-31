package DAO;

import Model.Appointments;
import Model.ApptReport;
import Model.ContactReport;
import Model.DivisionReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Class AppointmentsDaoImpl.java has the Create, Read, Update and Delete
 * functionalities for the appointments table.
 */
public class AppointmentsDaoImpl {

    /**
     * @return an ObservableList<Appointments> that contains appointments objects with all the
     * DB appointment table data in them
     */
    public static ObservableList<Appointments> getAllAppointments() {
        ObservableList<Appointments> alist = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM Appointments";
            PreparedStatement ps = DB_Connection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int Appointment_ID = rs.getInt("Appointment_ID");
                String Title = rs.getString("Title");
                String Description = rs.getString("Description");
                String Location = rs.getString("Location");
                String Type = rs.getString("Type");
                LocalDateTime Start = (rs.getTimestamp("Start")).toLocalDateTime();
                LocalDateTime End = (rs.getTimestamp("End")).toLocalDateTime();;
                int Customer_ID = rs.getInt("Customer_ID");
                int User_ID = rs.getInt("User_ID");
                int Contact_ID = rs.getInt("Contact_ID");
                Appointments a = new Appointments(Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID);
                alist.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alist;
    }

    /**
     * @param selectedAppointmentID a appointment's id to search in the database appointments table
     * @return appointments object with the associated appointment DB data
     */
    public static Appointments getAppointment(int selectedAppointmentID) {
        try {
            String sql = "SELECT * FROM Appointments WHERE Appointment_ID =?";
            PreparedStatement ps = DB_Connection.getConnection().prepareStatement(sql);
            ps.setInt(1, selectedAppointmentID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int Appointment_ID = rs.getInt("Appointment_ID");
                String Title = rs.getString("Title");
                String Description = rs.getString("Description");
                String Location = rs.getString("Location");
                String Type = rs.getString("Type");
                LocalDateTime Start = (rs.getTimestamp("Start")).toLocalDateTime();
                LocalDateTime End = (rs.getTimestamp("End")).toLocalDateTime();;
                int Customer_ID = rs.getInt("Customer_ID");
                int User_ID = rs.getInt("User_ID");
                int Contact_ID = rs.getInt("Contact_ID");
                return new Appointments(Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param customerId a customer's id to search in the database appointments table
     * @return ObservableList<Appointments> with the associated appointment DB data for the selected customer
     */
    public static ObservableList<Appointments> getCustomerAppointments (int customerId) {
        ObservableList<Appointments> alist = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM Appointments WHERE Customer_ID = ? ORDER BY Start";
            PreparedStatement ps = DB_Connection.getConnection().prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int Appointment_ID = rs.getInt("Appointment_ID");
                String Title = rs.getString("Title");
                String Description = rs.getString("Description");
                String Location = rs.getString("Location");
                String Type = rs.getString("Type");
                LocalDateTime Start = (rs.getTimestamp("Start")).toLocalDateTime();
                LocalDateTime End = (rs.getTimestamp("End")).toLocalDateTime();
                int Customer_ID = rs.getInt("Customer_ID");
                int User_ID = rs.getInt("User_ID");
                int Contact_ID = rs.getInt("Contact_ID");
                Appointments a = new Appointments(Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID);
                alist.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alist;
    }

    /**
     * @param userId a user's id to search in the database appointments table
     * @return ObservableList<Appointments> with the associated appointment DB data for the selected user
     */
    public static ObservableList<Appointments> getUserAppointments (int userId) {
        ObservableList<Appointments> alist = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM Appointments WHERE User_ID = ? ORDER BY Start";
            PreparedStatement ps = DB_Connection.getConnection().prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int Appointment_ID = rs.getInt("Appointment_ID");
                String Title = rs.getString("Title");
                String Description = rs.getString("Description");
                String Location = rs.getString("Location");
                String Type = rs.getString("Type");
                LocalDateTime Start = (rs.getTimestamp("Start")).toLocalDateTime();
                LocalDateTime End = (rs.getTimestamp("End")).toLocalDateTime();
                int Customer_ID = rs.getInt("Customer_ID");
                int User_ID = rs.getInt("User_ID");
                int Contact_ID = rs.getInt("Contact_ID");
                Appointments a = new Appointments(Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID);
                alist.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alist;
    }

    /**
     * Runs the prepared SQL statement to generate the number of appointments by type and month report
     */
    public static ObservableList<ApptReport> generateAppointmentsReport () {
        ObservableList<ApptReport> alist = FXCollections.observableArrayList();
        try {
            String sql = "SELECT COUNT(Appointment_ID), Type, MONTH(Start) FROM Appointments GROUP BY Type, MONTH(Start)";
            PreparedStatement ps = DB_Connection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int count = rs.getInt("COUNT(Appointment_ID)");
                String type = rs.getString("Type");
                int month = rs.getInt("MONTH(Start)");
                ApptReport a = new ApptReport(count, type, month);
                alist.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alist;
    }

    /**
     * Runs the prepared SQL statement to generate the contacts schedules report
     */
    public static ObservableList<ContactReport> generateContactsReport () {
        ObservableList<ContactReport> alist = FXCollections.observableArrayList();
        try {
            String sql = "SELECT c.Contact_Name, a.Appointment_ID, a.Title, a.Type, a.Description, a.Start, a.End, a.Customer_ID FROM Appointments AS a INNER JOIN Contacts AS c ON a.Contact_ID = c.Contact_ID ORDER BY c.Contact_Name, a.Start";
            PreparedStatement ps = DB_Connection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("Contact_Name");
                int apptId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String type = rs.getString("Type");
                String desc = rs.getString("Description");
                LocalDateTime Start = (rs.getTimestamp("Start")).toLocalDateTime();
                LocalDateTime End = (rs.getTimestamp("End")).toLocalDateTime();
                int custId = rs.getInt("Customer_ID");
                ContactReport c = new ContactReport(name, apptId, title, type, desc, Start, End, custId);
                alist.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alist;
    }

    /**
     * Runs the prepared SQL statement to generate the contacts number of customers per each divisions report
     */
    public static ObservableList<DivisionReport> generateDivisionsReport () {
        ObservableList<DivisionReport> alist = FXCollections.observableArrayList();
        try {
            String sql = "SELECT contact.Contact_Name, division.Division, country.Country, COUNT(DISTINCT appt.Customer_ID) FROM Appointments AS appt INNER JOIN Contacts AS contact ON appt.Contact_ID = contact.Contact_ID INNER JOIN Customers AS cust ON appt.Customer_ID = cust.Customer_ID INNER JOIN First_Level_Divisions AS division ON cust.Division_ID = division.Division_ID INNER JOIN Countries AS country ON division.Country_ID = country.Country_ID GROUP BY appt.Contact_ID, cust.Division_ID ORDER BY contact.Contact_Name, country.Country, division.Division";
            PreparedStatement ps = DB_Connection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String contactName = rs.getString("contact.Contact_Name");
                String divisionName = rs.getString("division.Division");
                String countryName = rs.getString("country.Country");
                int numAppts = rs.getInt("COUNT(DISTINCT appt.Customer_ID)");
                DivisionReport d = new DivisionReport(contactName, divisionName, countryName, numAppts);
                alist.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alist;
    }

    /**
     * @param selectedAppointmentID a appointment's id to search in the database appointments table
     * @return true/false if the appointment has been successfully deleted in the database
     */
    public static boolean deleteAppointment (int selectedAppointmentID) {
        try {
            String sql = "DELETE FROM Appointments WHERE Appointment_ID = " + selectedAppointmentID;
            PreparedStatement ps = DB_Connection.getConnection().prepareStatement(sql);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getAppointment(selectedAppointmentID) == null;
    }

    /**
     * @param appointment a appointments object to pull data from and create new entry in appointments DB table
     */
    public static void addAppointment(Appointments appointment) {
        String title = appointment.getTitle();
        String desc = appointment.getDescription();
        String location = appointment.getLocation();
        String type = appointment.getType();
        LocalDateTime start = appointment.getStart();
        LocalDateTime end = appointment.getEnd();
        int customerId = appointment.getCustomer_ID();
        int userId = appointment.getUser_ID();
        int contactId = appointment.getContact_ID();

        try {
            String sql = "INSERT INTO Appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = DB_Connection.getConnection().prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, desc);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setInt(7, customerId);
            ps.setInt(8, userId);
            ps.setInt(9, contactId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param appointment a appointments object to pull data from and update an existing entry in appointments DB table
     */
    public static void updateAppointment(Appointments appointment) {
        String title = appointment.getTitle();
        String desc = appointment.getDescription();
        String location = appointment.getLocation();
        String type = appointment.getType();
        LocalDateTime start = appointment.getStart();
        LocalDateTime end = appointment.getEnd();
        int customerId = appointment.getCustomer_ID();
        int userId = appointment.getUser_ID();
        int contactId = appointment.getContact_ID();
        int appointmentId = appointment.getAppointment_ID();

        try {
            String sql = "UPDATE Appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_Id = ?";
            PreparedStatement ps = DB_Connection.getConnection().prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, desc);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setInt(7, customerId);
            ps.setInt(8, userId);
            ps.setInt(9, contactId);
            ps.setInt(10,appointmentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
