package Controller;

import DAO.AppointmentsDaoImpl;
import Model.DivisionReport;
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
 * Class Divisions_Report_Controller.java handles the functionality of the
 * display for the contacts number of appointments by division report.
 */
public class Divisions_Report_Controller implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private TableView<DivisionReport> divisionsTable;

    @FXML
    private TableColumn<DivisionReport, String> contactCol;

    @FXML
    private TableColumn<DivisionReport, String> countryCol;

    @FXML
    private TableColumn<DivisionReport, String> divisionCol;

    @FXML
    private TableColumn<DivisionReport, Integer> totalCol;

    public ObservableList<DivisionReport> allAppointments = FXCollections.observableArrayList();

    /**
     * Initialize the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //initialize report table
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("numAppts"));

        try {
            allAppointments.addAll(AppointmentsDaoImpl.generateDivisionsReport());
        } catch (Exception e) {
            Logger.getLogger(Application_Controller.class.getName()).log(Level.SEVERE, null, e);
        }
        divisionsTable.setItems(allAppointments);
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
