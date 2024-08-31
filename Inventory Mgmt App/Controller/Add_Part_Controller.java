package Controller;

import Model.*;
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

/**
 * Class Add_Part_Controller.java handles the functionality of the Add Part Form.
 */
public class Add_Part_Controller implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private ToggleGroup origin;

    @FXML
    private RadioButton inHouse;

    @FXML
    private RadioButton outsource;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField inventoryTxt;

    @FXML
    private TextField priceTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField minTxt;

    @FXML
    private TextField machineIdTxt;

    @FXML
    private Label machineIdLabel;

    /**
     * Initialize the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    /**
     * If In-House radio button is selected then updated bottom field label to Machine ID.
     */
    @FXML
    void inhouseSelected(ActionEvent event) {
        machineIdLabel.setText("Machine ID");
    }

    /**
     * If Outsourced radio button is selected then updated bottom field label to Company Name.
     */
    @FXML
    void outsourceSelected(ActionEvent event) {
        machineIdLabel.setText("Company Name");
    }

    /**
     * If Cancel button is pressed then loads Main Form.
     */
    @FXML
    void onActionReturnMain(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * If Save button is pressed then the new part is created and added to allParts list.
     * It alerts user if a radio button is not selected. It alerts user if not all fields
     * contain values. It alerts user if field data is incorrect type.
     * It alerts user if field data is incorrect type.
     * Creates incrementally larger part ID value for new part.
     * Returns to Main Form after part is created and added to allParts list.
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {
        //Make sure radio button is selected
        if (!inHouse.isSelected() && !outsource.isSelected()) {
            //alert select radio button
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Toggle In-House / Outsource");
            alert.setContentText("Please select In-House or Outsource.");
            alert.showAndWait();
            return;
        }
        
        String name = nameTxt.getText();
        double price;
        int stock;
        int min;
        int max;

        if (name.isBlank()) {
            //alert blank name
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Name Field Empty");
            alert.setContentText("Please enter a part name.");
            alert.showAndWait();
            return;
        }
        try {
            price = Double.parseDouble(priceTxt.getText());
        } catch (NullPointerException e) {
            //alert blank price
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Price/Cost Field Empty");
            alert.setContentText("Please enter a Price/Cost.");
            alert.showAndWait();
            return;
        } catch (NumberFormatException e){
            //alert non-numeric value
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Price/Cost Field Value");
            alert.setContentText("Please enter a positive numeric value for Price/Cost.");
            alert.showAndWait();
            return;
        }
        try {
            min = Integer.parseInt(minTxt.getText());
            max = Integer.parseInt(maxTxt.getText());
            if (min > max) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            //alert non-integer value and min < max
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Min/Max Field Value");
            alert.setContentText("Please enter positive integer values for min and max where min is less than max.");
            alert.showAndWait();
            return;
        }
        try {
            stock = Integer.parseInt(inventoryTxt.getText());
            if (stock < min || stock > max) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            //alert non-integer value and in min-max range
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inv Field Value");
            alert.setContentText("Please enter a positive integer value for Inv in range of min and max values.");
            alert.showAndWait();
            return;
        }

        int id = 1;
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() >= id) {
                id = part.getId();
            }
        }
        id++;

        String lastValue = machineIdTxt.getText();
        if (lastValue.isBlank()) {
            //alert blank name
            Alert alert = new Alert(Alert.AlertType.ERROR);
            if (inHouse.isSelected()) {
                alert.setTitle("Machine ID Field Empty");
                alert.setContentText("Please enter value in the Machine ID field.");
            } else {
                alert.setTitle("Company Name Field Value");
                alert.setContentText("Please enter a Company Name.");
            }
            alert.showAndWait();
            return;
        }

        if (inHouse.isSelected()) {
            int machineId;
            try {
                machineId = Integer.parseInt(lastValue);
                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
            } catch (NumberFormatException e) {
                //alert enter integer for machine ID
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Machine ID Value");
                alert.setContentText("Please enter a positive integer value for Machine ID field.");
                alert.showAndWait();
                return;
            }
        } else if (outsource.isSelected()) {
            Inventory.addPart(new Outsourced(id, name, price, stock, min, max, lastValue));
        }

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

}