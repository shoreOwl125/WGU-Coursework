package Controller;
import DAO.CountriesDaoImpl;
import DAO.CustomersDaoImpl;
import DAO.DivisionDaoImpl;
import Model.Countries;
import Model.Customers;
import Model.Divisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class Customer_Add_Controller.java handles the functionality of the
 * display for the add customer window.
 */
public class Customer_Add_Controller implements Initializable {
        Stage stage;
        Parent scene;

        @FXML
        private TextField nameText;

        @FXML
        private TextField phoneText;

        @FXML
        private TextField addressText;

        @FXML
        private TextField postalText;

        @FXML
        private ComboBox<String> customerAddDivisionCombo;

        public ObservableList<Divisions> allDivisions = FXCollections.observableArrayList();

        @FXML
        private ComboBox<String> customerAddFormCountryCombo;

        public ObservableList<Countries> allCountries = FXCollections.observableArrayList();

        /**
         * Initialize the controller class. Load data into ComboBox.
         */
        @Override
        public void initialize(URL url, ResourceBundle rb) {
                try {
                        allCountries.addAll(CountriesDaoImpl.getAllCountries());
                } catch (Exception e) {
                        Logger.getLogger(Application_Controller.class.getName()).log(Level.SEVERE, null, e);
                }
                for (Countries country : allCountries)
                        customerAddFormCountryCombo.getItems().add(country.getName());

        }

        /**
         * Once a country is selected in the country ComboBox, load appropriate data into Divisions ComboBox
         */
        @FXML
        void customerAddFormCountrySelected(ActionEvent event) {
                String countryName = customerAddFormCountryCombo.getSelectionModel().getSelectedItem();
                int countryId = 0;
                for (Countries country : allCountries) {
                        if (country.getName().equals(countryName)) {
                                countryId = country.getId();
                        }
                }
                try {
                        allDivisions.clear();
                        allDivisions.addAll(DivisionDaoImpl.getAllDivisionsByCountry(countryId));
                } catch (Exception e) {
                        Logger.getLogger(Application_Controller.class.getName()).log(Level.SEVERE, null, e);
                }
                customerAddDivisionCombo.getItems().clear();
                for (Divisions division : allDivisions) {
                        customerAddDivisionCombo.getItems().add(division.getName());
                }
        }

        /**
         * Submit button initiates input validations of the form data and sends the validated
         * customer data to the CustomersDaoImpl.
         */
        @FXML
        void submitBtnPressed(ActionEvent event) throws IOException {
                int id = 0;
                String name = nameText.getText();
                String phone = phoneText.getText();
                String address = addressText.getText();
                String postal = postalText.getText();
                String divisionName = customerAddDivisionCombo.getSelectionModel().getSelectedItem();
                int divisionId = 0;
                if (divisionName.isBlank()) {
                        //alert blank
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Division Field Empty");
                        alert.setContentText("Please select a division.");
                        alert.showAndWait();
                        return;
                }
                for (Divisions division : allDivisions) {
                        if (division.getName().equals(divisionName)) {
                                divisionId = division.getId();
                        }
                }
                //input validations
                if (name.isBlank()) {
                        //alert blank
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Name Field Empty");
                        alert.setContentText("Please enter a customer name.");
                        alert.showAndWait();
                        return;
                }

                if (phone.isBlank()) {
                        //alert blank
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Phone Field Empty");
                        alert.setContentText("Please enter a phone number.");
                        alert.showAndWait();
                        return;
                }

                if (address.isBlank()) {
                        //alert blank
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Address Field Empty");
                        alert.setContentText("Please enter an address.");
                        alert.showAndWait();
                        return;
                }

                if (postal.isBlank()) {
                        //alert blank
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Postal Field Empty");
                        alert.setContentText("Please enter a postal code.");
                        alert.showAndWait();
                        return;
                }
                Customers customer = new Customers(id, name, address, postal, phone, divisionId);
                CustomersDaoImpl.addCustomer(customer);

                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/Application_View.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
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
}
