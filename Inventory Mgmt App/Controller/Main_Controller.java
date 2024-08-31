package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
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
 * Class Main_Controller.java handles the functionality of the Main Form.
 */
public class Main_Controller implements Initializable {
    Stage stage;
    Parent scene;
    @FXML
    private TextField partSearchText;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TableColumn<Part, Integer> partID;

    @FXML
    private TableColumn<Part, String> partName;

    @FXML
    private TableColumn<Part, Integer> partInvLevel;

    @FXML
    private TableColumn<Part, Double> partUnitPrice;

    @FXML
    private TextField productLookup;

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TableColumn<Product, Integer> productID;

    @FXML
    private TableColumn<Product, String> productName;

    @FXML
    private TableColumn<Product, Integer> productInvLevel;

    @FXML
    private TableColumn<Product, Double> productUnitPrice;


    /**
     * Initialize the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partTableView.setItems(Inventory.getAllParts());
        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partUnitPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTableView.setItems(Inventory.getAllProducts());
        productID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productUnitPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * When Add button under part table is pressed, this method loads Add_Part form.
     */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Add_Part.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * When Add button under product table is pressed, this method loads Add_Product form.
     */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Add_Product.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * When Delete button under part table is pressed, this method attempts to deleted selected part.
     * It alerts user if no part is selected and confirms before removing part.
     */
    @FXML
    void onActionDeletePart(ActionEvent event) {
        Part deletePart = partTableView.getSelectionModel().getSelectedItem();
        if (deletePart == null) {
            //alert no matches found
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Part cannot be deleted");
            alert2.setContentText("Part cannot be found.");
            alert2.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm delete Part");
        alert.setContentText("Are you sure you want to delete this part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (Inventory.deletePart(deletePart)) {
                partTableView.setItems(Inventory.getAllParts());
            }
        }
    }

    /**
     * When Delete button under product table is pressed, this method attempts to deleted selected product.
     * It alerts user if no product is selected or if the product has associated parts.
     * It confirms before removing product.
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        Product deleteProduct = productTableView.getSelectionModel().getSelectedItem();
        if (deleteProduct == null) {
            //alert no matches found
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Product cannot be deleted");
            alert2.setContentText("Product cannot be found.");
            alert2.showAndWait();
            return;
        }
        if (!deleteProduct.getAllAssociatedParts().isEmpty()) {
            //alert associated parts
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product cannot be deleted");
            alert.setContentText("Product still has parts associated with it.");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm delete product");
        alert.setContentText("Are you sure you want to delete this product?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (Inventory.deleteProduct(deleteProduct)) {
                productTableView.setItems(Inventory.getAllProducts());
            }
        }
    }

    /**
     * When Exit button is pressed, this method confirms with the user before
     * exiting program.
     */
    @FXML
    void onActionExit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm exit");
        alert.setContentText("Are you sure you want to exit program?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    /**
     * When Modify button is pressed under part table, this method loads Modify_Part form.
     * It sends the selected part information to populate the Modify_Part form.
     * It alerts user if no part is selected.
     */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {
        Part tempPart = partTableView.getSelectionModel().getSelectedItem();
        if (tempPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modify Error");
            alert.setContentText("No Part Selected");
            alert.showAndWait();
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/Modify_Part.fxml"));
        loader.load();
        Modify_Part_Controller part_controller = loader.getController();
        part_controller.sendPart(tempPart);

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent root = loader.getRoot();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * When Modify button is pressed under product table, this method loads Modify_Product form.
     * It sends the selected product information to populate the Modify_Product form.
     * It alerts user if no product is selected.
     */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {
        Product tempProduct = productTableView.getSelectionModel().getSelectedItem();
        if (tempProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modify Error");
            alert.setContentText("No Product Selected");
            alert.showAndWait();
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/Modify_Product.fxml"));
        loader.load();
        Modify_Product_Controller product_controller = loader.getController();
        product_controller.sendProduct(tempProduct);

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent root = loader.getRoot();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * When Search button is pressed above part table, this method searches for parts with
     * matching name or ID.
     * It alerts user if nothing is entered in text field or if there are no matching parts.
     * It populates the table with a part containing the searched ID or if no ID matches then
     * returns a list of parts with the searched string contained in the part name.
     */
    @FXML
    void onActionFilterParts(ActionEvent event) {
        String name = partSearchText.getText();
        if (name.isBlank()) {
            partTableView.setItems(Inventory.getAllParts());
            return;
        }
        try {
            int id = Integer.parseInt(name);
            Part tempPart = Inventory.lookupPart(id);
            if (tempPart != null) {
                partTableView.getSelectionModel().select(tempPart);
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
            partTableView.setItems(displayParts);
        }
    }

    /**
     * When Search button is pressed above product table, this method searches for products with
     * matching name or ID.
     * It alerts user if nothing is entered in text field or if there are no matching products.
     * It populates the table with a product containing the searched ID or if no ID matches then
     * returns a list of products with the searched string contained in the product name.
     */
    @FXML
    void onActionFilterProducts(ActionEvent event) {
        String name = productLookup.getText();
        if (name.isBlank()) {
            productTableView.setItems(Inventory.getAllProducts());
            return;
        }
        try {
            int id = Integer.parseInt(name);
            Product tempProduct = Inventory.lookupProduct(id);
            if (tempProduct != null) {
                productTableView.getSelectionModel().select(tempProduct);
            } else {
                //sends to search for number in name
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            ObservableList<Product> displayProducts = Inventory.lookupProduct(name);
            if (displayProducts.isEmpty()) {
                //alert no matches found
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No Matching Products");
                alert.setContentText("No products found matching name or ID.");
                alert.showAndWait();
                return;
            }
            productTableView.setItems(displayProducts);
        }
    }
}