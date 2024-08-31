package Controller;

import DAO.AppointmentsDaoImpl;
import DAO.CustomersDaoImpl;
import Model.Appointments;
import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class Application_Controller.java handles the functionality of the
 * display for the main application window.
 */
public class Application_Controller implements Initializable {

    Stage stage;
    Parent scene;

    //JavaFX references for Customer Tab

    @FXML
    private TableView<Customers> Customers_Table;

    @FXML
    private TableColumn<Customers, Integer> customerIdCol;

    @FXML
    private TableColumn<Customers, String> customerNameCol;

    @FXML
    private TableColumn<Customers, String> customerAddressCol;

    @FXML
    private TableColumn<Customers, String> customerPostalCodeCol;

    @FXML
    private TableColumn<Customers, String> customerPhoneCol;

    @FXML
    private TableColumn<Customers, Integer> customerDivisionCol;

    @FXML
    private TableColumn<Customers, Integer> customerCountryCol;

    public ObservableList<Customers> allCustomers = FXCollections.observableArrayList();

    //JavaFX references for Appointments tab

    @FXML
    private TableView<Appointments> Appointments_Table;

    @FXML
    private TableColumn<Appointments, Integer> apptIdCol;

    @FXML
    private TableColumn<Appointments, String> apptTitleCol;

    @FXML
    private TableColumn<Appointments, String> apptDescCol;

    @FXML
    private TableColumn<Appointments, String> apptLocationCol;

    @FXML
    private TableColumn<Appointments, String> apptTypeCol;

    @FXML
    private TableColumn<Appointments, LocalDateTime> apptStartCol;

    @FXML
    private TableColumn<Appointments, LocalDateTime> apptEndCol;

    @FXML
    private TableColumn<Appointments, Integer> apptCustIdCol;

    @FXML
    private TableColumn<Appointments, Integer> apptUserIdCol;

    @FXML
    private TableColumn<Appointments, Integer> apptContactCol;

    public ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();


    /**
     * Initialize the controller class. Load data into the customers and appointments table views.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //initialize customer table
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
        customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("Postal_Code"));
        customerDivisionCol.setCellValueFactory(new PropertyValueFactory<>("Division"));
        customerCountryCol.setCellValueFactory(new PropertyValueFactory<>("Country"));
        try {
            allCustomers.addAll(CustomersDaoImpl.getAllCustomers());
        } catch (Exception e) {
            Logger.getLogger(Application_Controller.class.getName()).log(Level.SEVERE, null, e);
        }
        Customers_Table.setItems(allCustomers);

        //initialize appointments table
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        apptDescCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("Start"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("End"));
        apptCustIdCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        apptUserIdCol.setCellValueFactory(new PropertyValueFactory<>("User_ID"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));

        try {
            allAppointments.addAll(AppointmentsDaoImpl.getAllAppointments());
        } catch (Exception e) {
            Logger.getLogger(Application_Controller.class.getName()).log(Level.SEVERE, null, e);
        }
        Appointments_Table.setItems(allAppointments);
    }

    //Customer tab action events

    /**
     * Customer Add button opens the customer add form window.
     */
    @FXML
    void cust_add_btn(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Customer_Add_Form.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Customer update button opens the customer update form window.
     */
    @FXML
    void cust_update_btn(ActionEvent event) throws IOException {
        Customers tempCustomer = Customers_Table.getSelectionModel().getSelectedItem();
        if (tempCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update Error");
            alert.setContentText("No Customer Selected");
            alert.showAndWait();
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/Customer_Update_Form.fxml"));
        loader.load();
        Customer_Update_Controller customer_controller = loader.getController();
        customer_controller.sendCustomer(tempCustomer);

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent root = loader.getRoot();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Customer delete button performs data validations and sends customer data
     * to CustomersDaoImpl
     */
    @FXML
    void cust_delete_btn(ActionEvent event) {
        Customers selectedCustomer = Customers_Table.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            //alert no matches found
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Customer cannot be deleted");
            alert2.setContentText("Customer cannot be found.");
            alert2.showAndWait();
            return;
        }

        //Check data mismatch between app and database
        if (CustomersDaoImpl.getCustomer(selectedCustomer.getCustomer_ID()) == null) {
            //alert no matches found
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Customer cannot be deleted");
            alert2.setContentText("Customer cannot be found.");
            alert2.showAndWait();
            ObservableList<Customers> refreshCustomers = FXCollections.observableArrayList();
            try {
                refreshCustomers.addAll(CustomersDaoImpl.getAllCustomers());
            } catch (Exception e) {
                Logger.getLogger(Application_Controller.class.getName()).log(Level.SEVERE, null, e);
            }
            allCustomers = refreshCustomers;
            Customers_Table.setItems(allCustomers);
            return;
        }

        //Check if customer has appointments
        if (CustomersDaoImpl.hasAppointments(selectedCustomer.getCustomer_ID())) {
            //alert customer has appointments
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Customer cannot be deleted");
            alert2.setContentText("Customer's appointments must be canceled before deleting customer record.");
            alert2.showAndWait();
            return;
        }
        //Delete customer record
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm delete Customer");
        alert.setContentText("Are you sure you want to delete this customer record?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (CustomersDaoImpl.deleteCustomer(selectedCustomer.getCustomer_ID())) {
                allCustomers.remove(selectedCustomer);
                Customers_Table.setItems(allCustomers);
            } else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Problem deleting record");
                alert2.setContentText("Unknown error deleting record, record still exists in database.");
                alert2.showAndWait();
            }
        }
    }

    /**
     * Exit button confirms exit then exits application.
     */
    @FXML
    void cust_exit_btn(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm exit");
        alert.setContentText("Are you sure you want to exit program?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    //Appointments tab action events
    @FXML
    void radioButtonAll(ActionEvent event) {
        Appointments_Table.setItems(allAppointments);
    }

    @FXML
    void radioButtonMonth(ActionEvent event) {
        ObservableList<Appointments> monthAppointments = FXCollections.observableArrayList();
        Month currentMonth = LocalDate.now().getMonth();
        int currentYear = LocalDate.now().getYear();
        for (Appointments appointment : allAppointments) {
            if (appointment.getStart().getMonth() == currentMonth
                && appointment.getStart().getYear() == currentYear) {
                monthAppointments.add(appointment);
            }
        }
        Appointments_Table.setItems(monthAppointments);
    }

    @FXML
    void radioButtonWeek(ActionEvent event) {
        ObservableList<Appointments> weekAppointments = FXCollections.observableArrayList();
        LocalDate currentWeek = LocalDate.now().with(DayOfWeek.SUNDAY);
        for (Appointments appointment : allAppointments) {
            if (!appointment.getStart().toLocalDate().isBefore(currentWeek)) {
                weekAppointments.add(appointment);
            }
        }
        Appointments_Table.setItems(weekAppointments);
    }

    /**
     * Appointment Add button opens the appointment add form window.
     */
    @FXML
    void appt_add_btn(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Appointment_Add_Form.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Appointment update button opens the appointment update form window.
     */
    @FXML
    void appt_update_btn(ActionEvent event) throws IOException {
        Appointments tempAppointment = Appointments_Table.getSelectionModel().getSelectedItem();
        if (tempAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update Error");
            alert.setContentText("No Appointment Selected");
            alert.showAndWait();
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/Appointment_Update_Form.fxml"));
        loader.load();
        Appointment_Update_Controller appointment_controller = loader.getController();
        appointment_controller.sendAppointment(tempAppointment);

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent root = loader.getRoot();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Appointment delete button performs data validations and sends appointment data
     * to AppointmentsDaoImpl
     */
    @FXML
    void appt_delete_btn(ActionEvent event) {
        Appointments selectedAppointment = Appointments_Table.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            //alert no matches found
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Appointment cannot be canceled");
            alert2.setContentText("Appointment cannot be found.");
            alert2.showAndWait();
            return;
        }

        //Check data mismatch between app and database
        if (AppointmentsDaoImpl.getAppointment(selectedAppointment.getAppointment_ID()) == null) {
            //alert no matches found
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Appointment cannot be canceled");
            alert2.setContentText("Appointment cannot be found.");
            alert2.showAndWait();
            ObservableList<Appointments> refreshAppointments = FXCollections.observableArrayList();
            try {
                refreshAppointments.addAll(AppointmentsDaoImpl.getAllAppointments());
            } catch (Exception e) {
                Logger.getLogger(Application_Controller.class.getName()).log(Level.SEVERE, null, e);
            }
            allAppointments = refreshAppointments;
            Appointments_Table.setItems(allAppointments);
            return;
        }

        //Cancel appointment
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel Appointment");
        alert.setContentText("Are you sure you want to cancel this appointment?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            String currentApptId = String.valueOf(selectedAppointment.getAppointment_ID());
            String currentApptType = selectedAppointment.getType();
            if (AppointmentsDaoImpl.deleteAppointment(selectedAppointment.getAppointment_ID())) {
                allAppointments.remove(selectedAppointment);
                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                alert3.setTitle("Appointment Canceled");
                alert3.setContentText("Appointment ID: " + currentApptId + "      " + "Type: " + currentApptType + " has been canceled.");
                alert3.showAndWait();
                Appointments_Table.setItems(allAppointments);
            } else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Problem deleting record");
                alert2.setContentText("Unknown error deleting record, record still exists in database.");
                alert2.showAndWait();
            }
        }
    }

    /**
     * Exit button confirms exit then exits application.
     */
    @FXML
    void appt_exit_btn(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm exit");
        alert.setContentText("Are you sure you want to exit program?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    //Reports tab action events
    /**
     * Report1 button opens the appointments by type and month report window.
     */
    @FXML
    void report1_btn(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Appointments_Report_View.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Report2 button opens the contacts schedules report window.
     */
    @FXML
    void report2_btn(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Contacts_Report_View.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Report3 button opens the contacts number of appointments by division report window.
     */
    @FXML
    void report3_btn(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Divisions_Report_View.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Exit button confirms exit then exits application.
     */
    @FXML
    void reports_exit_btn(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm exit");
        alert.setContentText("Are you sure you want to exit program?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }
}
