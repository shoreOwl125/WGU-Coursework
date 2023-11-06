package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class Modify_Part_Controller.java handles the functionality of the Modify Part Form.
 */
public class Modify_Part_Controller implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private TextField idTxt;

    @FXML
    private RadioButton inHouse;

    @FXML
    private TextField inventoryTxt;

    @FXML
    private Label machineIdLabel;

    @FXML
    private TextField machineIdTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField minTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private ToggleGroup origin;

    @FXML
    private RadioButton outsource;

    @FXML
    private TextField priceTxt;

    Part tempPart;

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
     * If Save button is pressed then the part is updated in allParts list with data from this form.
     * It alerts user if a radio button is not selected. It alerts user if not all fields
     * contain values. It alerts user if field data is incorrect type.
     * Alerts user if min, max and inventory values are not within logical ranges.
     * It returns to Main Form after part is created and added to allParts list.
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {
        int id = Integer.parseInt(idTxt.getText());
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
            alert.setTitle("Price Field Empty");
            alert.setContentText("Please enter a Price/Cost.");
            alert.showAndWait();
            return;
        } catch (NumberFormatException e){
            //alert non-numeric value
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Non-Numeric Value");
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
            alert.setTitle("Invalid Range");
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
            alert.setTitle("Invalid Range");
            alert.setContentText("Please enter a positive integer value for Inv in range of min and max values.");
            alert.showAndWait();
            return;
        }

        String lastValue = machineIdTxt.getText();
        if (lastValue.isBlank()) {
            //alert blank name
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Name Field Empty");
            alert.setContentText("Please enter a part name.");
            alert.showAndWait();
            return;
        }

        //get index to update with modified part
        int index = Inventory.getAllParts().indexOf(tempPart);

        if(inHouse.isSelected()) {
            int machineId;
            try {
                machineId = Integer.parseInt(machineIdTxt.getText());
                Inventory.updatePart(index, new InHouse(id, name, price, stock, min, max, machineId));
            } catch (NumberFormatException e) {
                //alert enter integer for machine ID
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Non-Integer Value");
                alert.setContentText("Please enter positive integer value for Machine ID field.");
                alert.showAndWait();
                return;
            }
        } else {
            String companyName = machineIdTxt.getText();
            if (companyName.isBlank()) {
                //alert blank name
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Company Name Field Empty");
                alert.setContentText("Please enter a Company Name.");
                alert.showAndWait();
                return;
            }
            Inventory.updatePart(index, new Outsourced(id, name, price, stock, min, max, companyName));
        }

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method uses the part selected in the main form to populate the Modify Part form.
     * If the part is an In-House or Outsourced part it adjusts the last field label.
     */
    public void sendPart(Part part) {
        tempPart = part;
        if (part instanceof InHouse) {
            inHouse.setSelected(true);
            machineIdLabel.setText("Machine ID");
            machineIdTxt.setText(String.valueOf(((InHouse) part).getMachineId()));
        } else {
            outsource.setSelected(true);
            machineIdLabel.setText("Company Name");
            machineIdTxt.setText(String.valueOf(((Outsourced) part).getCompanyName()));
        }
        idTxt.setText(String.valueOf(part.getId()));
        nameTxt.setText(String.valueOf(part.getName()));
        inventoryTxt.setText(String.valueOf(part.getStock()));
        priceTxt.setText(String.valueOf(part.getPrice()));
        maxTxt.setText(String.valueOf(part.getMax()));
        minTxt.setText(String.valueOf(part.getMin()));
    }

}