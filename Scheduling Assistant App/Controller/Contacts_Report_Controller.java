package Controller;

import DAO.AppointmentsDaoImpl;
import Model.ContactReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class Contacts_Report_Controller.java handles the functionality of the
 * display for the contacts schedules report.
 */
public class Contacts_Report_Controller implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private TableColumn<ContactReport, Integer> apptIdCol;

    @FXML
    private TableColumn<ContactReport, String> contactCol;

    @FXML
    private TableColumn<ContactReport, Integer> custIdCol;

    @FXML
    private TableColumn<ContactReport, String> descCol;

    @FXML
    private TableColumn<ContactReport, LocalDateTime> endCol;

    @FXML
    private TableColumn<ContactReport, LocalDateTime> startCol;

    @FXML
    private TableColumn<ContactReport, String> titleCol;

    @FXML
    private TableColumn<ContactReport, String> typeCol;

    @FXML
    private TableView<ContactReport> contactsTable;

    public ObservableList<ContactReport> allAppointments = FXCollections.observableArrayList();

    /**
     * Initialize the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //initialize report table
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("apptId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("desc"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("custId"));

        try {
            allAppointments.addAll(AppointmentsDaoImpl.generateContactsReport());
        } catch (Exception e) {
            Logger.getLogger(Application_Controller.class.getName()).log(Level.SEVERE, null, e);
        }
        contactsTable.setItems(allAppointments);
    }

    /**
     * Exit button returns to main application window
     */
    @FXML
    void appt_exit_btn(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Application_View.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
