package main;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
/**
 * @author Andrew Dahlstrom
 */

/**
 * Class Main.java launches the program and displays the Main Form.
 * FUTURE ENHANCEMENT REPORT: Increasing the search filter options would be an
 * excellent area to expand functionality. If you could search for parts or
 * products where the inventory is close to or at minimum levels then you
 * could more easily determine which parts or products to restock.
 */
public class Main extends Application {
/**
 * This method loads the Main Form onto the screen.
 */
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
            primaryStage.setTitle("Inventory Management System");
            primaryStage.setScene(new Scene(root, 820, 380));
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
/**
 *  This method launches the program.
 *  javadoc index is located at PA_TestProject/src/javadoc/index.html
 */
    public static void main(String[] args) {
        launch(args);
    }
}
