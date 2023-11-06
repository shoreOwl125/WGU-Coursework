package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
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
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Class Add_Product_Controller.java handles the functionality of the Add Product Form.
 */
public class Add_Product_Controller implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TableColumn<Part, Integer> allPartIdCol;

    @FXML
    private TableColumn<Part, Integer> allPartInvCol;

    @FXML
    private TableColumn<Part, String> allPartNameCol;

    @FXML
    private TableColumn<Part, Double> allPartPriceCol;

    @FXML
    private TableView<Part> allPartTable;

    @FXML
    private TableColumn<Part, Integer> ascPartIdCol;

    @FXML
    private TableColumn<Part, Integer> ascPartInvCol;

    @FXML
    private TableColumn<Part, String> ascPartNameCol;

    @FXML
    private TableColumn<Part, Double> ascPartPriceCol;

    @FXML
    private TableView<Part> ascPartTable;

    @FXML
    private TextField idText;

    @FXML
    private TextField inventoryText;

    @FXML
    private TextField maxText;

    @FXML
    private TextField minText;

    @FXML
    private TextField nameText;

    @FXML
    private TextField partSearchText;

    @FXML
    private TextField priceText;

    private ObservableList<Part> newAssociatedParts = FXCollections.observableArrayList();

    /**
     * Initialize the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        allPartTable.setItems(Inventory.getAllParts());
        allPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        ascPartTable.setItems(newAssociatedParts);
        ascPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ascPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ascPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ascPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * When Search button is pressed above the top part table, this method searches for parts with
     * matching name or ID.
     * It alerts user if nothing is entered in text field or if there are no matching parts.
     * It populates the table with a part containing the searched ID or if no ID matches then
     * returns a list of parts with the searched string contained in the part name.
     */
    @FXML
    void onActionFilterParts(ActionEvent event) {
        String name = partSearchText.getText();
        if (name.isBlank()) {
            allPartTable.setItems(Inventory.getAllParts());
            return;
        }
        try {
            int id = Integer.parseInt(name);
            Part tempPart = Inventory.lookupPart(id);
            if (tempPart != null) {
                allPartTable.getSelectionModel().select(tempPart);
            } else {
                //sends to search for number in name
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            ObservableList<Part> displayParts = Inventory.lookupPart(name);
            if (displayParts.isEmpty()) {
                //alert no matches found
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No Matching Parts");
                alert.setContentText("No parts found matching name or ID.");
                alert.showAndWait();
                return;
            }
            allPartTable.setItems(displayParts);
        }
    }

    /**
     * If Add button is pressed below top part table then selected part is added to bottom part table.
     * Alerts user if no part is selected.
     */
    @FXML
    void onActionAddToAscParts(ActionEvent event) {
        Part ascPart = allPartTable.getSelectionModel().getSelectedItem();
        if (ascPart == null) {
            //alert no matches found
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Part cannot be added");
            alert2.setContentText("Part cannot be found.");
            alert2.showAndWait();
            return;
        }
        newAssociatedParts.add(ascPart);
        ascPartTable.setItems(newAssociatedParts);
    }

    /**
     * If Remove button is pressed below bottom part table then selected part is removed from bottom part table.
     * Alerts user if no part is selected. Confirms before removing part.
     */
    @FXML
    void onActionRemoveFromAscParts(ActionEvent event) {
        Part removePart = ascPartTable.getSelectionModel().getSelectedItem();
        if (removePart == null) {
            //alert no matches found
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Part cannot be deleted");
            alert2.setContentText("Part cannot be found.");
            alert2.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm remove Part");
        alert.setContentText("Are you sure you want to remove this part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (newAssociatedParts.remove(removePart)) {
                ascPartTable.setItems(newAssociatedParts);
            }
        }
    }

    /**
     * If Cancel button is pressed then Main Form is loaded.
     */
    @FXML
    void onActionReturnMain(ActionEvent event) throws IOException {
        stage =(Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * If Save button is pressed then form data is used to create a new product and add to allProducts list.
     * Alerts user if any of the fields are left blank. Alerts user if field data types are incorrect.
     * Creates incrementally larger product ID value for new product.
     * Alerts user if min, max and inventory values are not within logical ranges.
     * Add parts in associated parts table to products associated parts list.
     * Loads Main Form after product is created and added to allProducts list.
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        String name = nameText.getText();
        int inventory;
        double price;
        int min;
        int max;

        if (name.isBlank()) {
            //alert blank name
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Name Field Empty");
            alert.setContentText("Please enter a product name.");
            alert.showAndWait();
            return;
        }

        try {
            price = Double.parseDouble(priceText.getText());
        } catch (NullPointerException e) {
            //alert blank price
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Price Field Empty");
            alert.setContentText("Please enter a Price.");
            alert.showAndWait();
            return;
        } catch (NumberFormatException e){
            //alert non-numeric value
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Price Field Value");
            alert.setContentText("Please enter a positive numeric value for Price.");
            alert.showAndWait();
            return;
        }

        try {
            min = Integer.parseInt(minText.getText());
            max = Integer.parseInt(maxText.getText());
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
            inventory = Integer.parseInt(inventoryText.getText());
            if (inventory < min || inventory > max) {
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
        for (Product product : Inventory.getAllProducts()) {
            if (product.getId() >= id) {
                id = product.getId();
            }
        }
        id++;

        Product newProduct = new Product(id, name, price, inventory, min, max);
        for (Part part : newAssociatedParts) {
            newProduct.addAssociatedPart(part);
        }
        Inventory.addProduct(newProduct);

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

}