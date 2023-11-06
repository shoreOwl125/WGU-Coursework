package Controller;
import DAO.AppointmentsDaoImpl;
import DAO.ContactsDaoImpl;
import DAO.CustomersDaoImpl;
import DAO.UsersDaoImpl;
import Model.Appointments;
import Model.Contacts;
import Model.Customers;
import Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class Appointment_Update_Controller.java handles the functionality of the
 * display for the update appointments window.
 */
public class Appointment_Update_Controller implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private ComboBox<String> contactComboBox;

    @FXML
    private ComboBox<String> customerIdCombo;

    @FXML
    private ComboBox<String> userIdCombo;

    @FXML
    private TextField descText;

    @FXML
    private TextField endText;

    @FXML
    private TextField locationText;

    @FXML
    private TextField startText;

    @FXML
    private TextField titleText;

    @FXML
    private TextField typeText;

    public ObservableList<Contacts> allContacts = FXCollections.observableArrayList();
    public ObservableList<Users> allUsers = FXCollections.observableArrayList();
    public ObservableList<Customers> allCustomers = FXCollections.observableArrayList();

    int tempAppointmentId;
    int tempContactId;
    int tempUserId;
    int tempCustomerId;

    /**
     * Initialize the controller class. Load data for ComboBoxes.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            allContacts.addAll(ContactsDaoImpl.getAllContacts());
            allUsers.addAll(UsersDaoImpl.getAllUsers());
            allCustomers.addAll(CustomersDaoImpl.getAllCustomers());
        } catch (Exception e) {
            Logger.getLogger(Application_Controller.class.getName()).log(Level.SEVERE, null, e);
        }
        for (Contacts contact : allContacts) {
            contactComboBox.getItems().add(contact.getContact_Name());
        }
        for (Users user : allUsers) {
            userIdCombo.getItems().add(user.getUserName());
        }
        for (Customers customer : allCustomers) {
            customerIdCombo.getItems().add(customer.getCustomer_Name());
        }
    }

    /**
     * Exit button returns to main application window
     */
    @FXML
    void cancelButtonPressed(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Application_View.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Submit button initiates input validations of the form data and sends the validated
     * appointment data to the AppointmentsDaoImpl.
     */
    @FXML
    void submitBtnPressed(ActionEvent event) throws IOException {
        String contactName = contactComboBox.getSelectionModel().getSelectedItem();
        String userName = userIdCombo.getSelectionModel().getSelectedItem();
        String customerName = customerIdCombo.getSelectionModel().getSelectedItem();
        String title = titleText.getText();
        String desc = descText.getText();
        String location = locationText.getText();
        String type = typeText.getText();
        int id = tempAppointmentId;
        int contactId = 0;
        int userId = 0;
        int customerId = 0;
        LocalDateTime start;
        LocalDateTime end;

        for (Contacts contact : allContacts) {
            if (contact.getContact_Name().equals(contactName)) {
                contactId = contact.getContact_ID();
            }
        }

        for (Users user : allUsers) {
            if (user.getUserName().equals(userName)) {
                userId = user.getUserId();
            }
        }

        for (Customers customer : allCustomers) {
            if (customer.getCustomer_Name().equals(customerName)) {
                customerId = customer.getCustomer_ID();
            }
        }

        //check for error looking up contact ID or no contact selected
        if (contactId == 0) {
            //alert blank
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Contact Field Error");
            alert.setContentText("Contact not found. Please select a contact.");
            alert.showAndWait();
            return;
        }

        //check for error looking up user ID or no user selected
        if (userId == 0) {
            //alert blank
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("User ID Field Error");
            alert.setContentText("User not found. Please select a user.");
            alert.showAndWait();
            return;
        }

        //check for error looking up customer ID or no customer selected
        if (customerId == 0) {
            //alert blank
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Customer ID Field Error");
            alert.setContentText("Customer not found. Please select a customer.");
            alert.showAndWait();
            return;
        }

        try {
            start = LocalDateTime.parse(startText.getText());
        } catch (DateTimeParseException e) {
            //alert format
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Start Date/Time Format");
            alert.setContentText("Please use the format: yyyy-mm-ddThh:mm for example 2022-01-31T12:00");
            alert.showAndWait();
            return;
        }

        try {
            end = LocalDateTime.parse(endText.getText());
        } catch (DateTimeParseException e) {
            //alert format
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect End Date/Time Format");
            alert.setContentText("Please use the format: yyyy-mm-ddThh:mm for example 2022-01-31T12:00");
            alert.showAndWait();
            return;
        }

        //check if end time is before start time
        if (end.isBefore(start)) {
            //alert end before start
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Start or End Date/Time");
            alert.setContentText("Start Date/Time must be before end Date/Time.");
            alert.showAndWait();
            return;
        }

        //check if appointment is outside of business hours 8 AM - 10 PM EST Everyday
        ZoneId timeZoneOffice = (ZoneId.of("America/New_York"));
        ZoneId timeZoneLocal = ZoneId.of(TimeZone.getDefault().getID());

        LocalDate startDate = start.toLocalDate();
        LocalTime startTime = LocalTime.parse("08:00");
        ZonedDateTime startOfficeTZ = ZonedDateTime.of(startDate, startTime, timeZoneOffice).withZoneSameInstant(timeZoneLocal);

        LocalDate endDate = end.toLocalDate();
        LocalTime endTime = LocalTime.parse("22:00");
        ZonedDateTime endOfficeTZ = ZonedDateTime.of(endDate, endTime, timeZoneOffice).withZoneSameInstant(timeZoneLocal);

        if (start.toLocalTime().isBefore(startOfficeTZ.toLocalTime()) | end.toLocalTime().isAfter(endOfficeTZ.toLocalTime())) {
            //alert outside of buisness hours
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Outside of Buisness Hours");
            alert.setContentText("Appointment scheduled outside of business hours: 8AM - 10PM EST including weekends");
            alert.showAndWait();
            return;
        }

        //check if customer has schedule conflict (only for customer)
        ObservableList<Appointments> customerAppointments = AppointmentsDaoImpl.getCustomerAppointments(customerId);
        //dont count current appointment
        for (Appointments appointment : customerAppointments) {
            if (appointment.getAppointment_ID() == tempAppointmentId) {
                continue;
            }
            if (end.isAfter(appointment.getStart()) && start.isBefore(appointment.getEnd())) {
                //alert schedule conflict
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Customer Schedule Conflict");
                alert.setContentText("Customer already has a meeting scheduled from: " + appointment.getStart() + " until " + appointment.getEnd());
                alert.showAndWait();
                return;
            }
        }

        //text input validations
        if (title.isBlank()) {
            //alert blank
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Title Field Empty");
            alert.setContentText("Please enter a title.");
            alert.showAndWait();
            return;
        }

        if (desc.isBlank()) {
            //alert blank
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Description Field Empty");
            alert.setContentText("Please enter a description.");
            alert.showAndWait();
            return;
        }

        if (location.isBlank()) {
            //alert blank
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Location Field Empty");
            alert.setContentText("Please enter a location.");
            alert.showAndWait();
            return;
        }

        if (type.isBlank()) {
            //alert blank
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Type Field Empty");
            alert.setContentText("Please enter a type.");
            alert.showAndWait();
            return;
        }

        Appointments appointment = new Appointments(id, title, desc, location, type, start, end, customerId, userId, contactId);
        AppointmentsDaoImpl.updateAppointment(appointment);

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Application_View.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Loads appointment data into form from the selected appointment in
     * the main application window.
     */
    public void sendAppointment(Appointments appointment) {
        //tempAppointment = appointment;
        tempAppointmentId = appointment.getAppointment_ID();
        tempContactId = appointment.getContact_ID();
        tempUserId = appointment.getUser_ID();
        tempCustomerId = appointment.getCustomer_ID();
        titleText.setText(String.valueOf(appointment.getTitle()));
        descText.setText(String.valueOf(appointment.getDescription()));
        locationText.setText(String.valueOf(appointment.getLocation()));
        typeText.setText(String.valueOf(appointment.getType()));
        startText.setText(String.valueOf(appointment.getStart()));
        endText.setText(String.valueOf(appointment.getEnd()));

        String contactName = null;
        String userName = null;
        String customerName = null;

        //iterate through lists to pull names to set combo boxes
        for (Contacts contact : allContacts) {
            if (contact.getContact_ID() == tempContactId) {
                contactName = contact.getContact_Name();
            }
        }
        contactComboBox.setValue(contactName);

        for (Users user : allUsers) {
            if (user.getUserId() == tempUserId) {
                userName = user.getUserName();
            }
        }
        userIdCombo.setValue(userName);

        for (Customers customer : allCustomers) {
            if (customer.getCustomer_ID() == tempCustomerId) {
                customerName = customer.getCustomer_Name();
            }
        }
        customerIdCombo.setValue(customerName);
    }

}

