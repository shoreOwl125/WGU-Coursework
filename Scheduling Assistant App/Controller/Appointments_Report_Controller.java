package Controller;

import DAO.AppointmentsDaoImpl;
import Model.ApptReport;
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class Appointments_Report_Controller.java handles the functionality of the
 * display for the number of appointments by type and month report.
 */
public class Appointments_Report_Controller implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private TableView<ApptReport> Appointments_Table;

    @FXML
    private TableColumn<ApptReport, Integer> countCol;

    @FXML
    private TableColumn<ApptReport, Integer> monthNumCol;

    @FXML
    private TableColumn<ApptReport, String> typeCol;

    public ObservableList<ApptReport> allAppointments = FXCollections.observableArrayList();

    /**
     * Initialize the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //initialize report table
        countCol.setCellValueFactory(new PropertyValueFactory<>("count"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        monthNumCol.setCellValueFactory(new PropertyValueFactory<>("month"));

        try {
            allAppointments.addAll(AppointmentsDaoImpl.generateAppointmentsReport());
        } catch (Exception e) {
            Logger.getLogger(Application_Controller.class.getName()).log(Level.SEVERE, null, e);
        }
        Appointments_Table.setItems(allAppointments);
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
