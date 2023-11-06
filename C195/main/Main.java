package main;

import DAO.DB_Connection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * @author Andrew Dahlstrom
 * javadoc index is located at C195_PA/javadoc/index.html
 */

/**
 * Class Main.java launches the program and displays the Login Form.
 */
public class Main extends Application {
    /**
     * This method loads the Log-In Form onto the screen.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/Login_Form.fxml"));
            primaryStage.setTitle("Scheduling Assistant");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     *  This method launches the program.
     */
    public static void main(String[] args) {
        DB_Connection.openConnection();
        launch(args);
        DB_Connection.closeConnection();
    }
}