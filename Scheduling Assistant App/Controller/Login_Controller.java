package Controller;

import DAO.AppointmentsDaoImpl;
import DAO.UsersDaoImpl;
import Model.Appointments;
import Model.Users;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * Class Login_Controller.java handles the functionality of the Login Form.
 */
public class Login_Controller implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private Button exitButton;

    @FXML
    private Label locationLabel;

    @FXML
    private Label location_text;

    @FXML
    private Label passLabel;

    @FXML
    private TextField pass_text;

    @FXML
    private Button submitButton;

    @FXML
    private Label titleLabel;

    @FXML
    private Label userIdLabel;

    @FXML
    private TextField user_id_text;

    static int ALERT_TIMEFRAME = 15;

    //French language version of text strings
    static String titleFrench = "Connexion à l'assistant de planification";
    static String userFrench = "Identifiant d'utilisateur";
    static String passFrench = "Mot de passe";
    static String locationFrench = "Emplacement";
    static String submitFrench = "Soumettre";
    static String exitFrench = "Sortir";

    /**
     * Initialize the controller class. Translate form text to French if the user's
     * system default language is set to French. Display local zone ID.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //get user location and update label on form
        ZoneId timeZoneLocal = ZoneId.of(TimeZone.getDefault().getID());
        location_text.setText(String.valueOf(timeZoneLocal));

        //If users system language is French translate to French
        if (Locale.getDefault().getLanguage().equals("fr")) {
            titleLabel.setText(titleFrench);
            userIdLabel.setText(userFrench);
            passLabel.setText(passFrench);
            locationLabel.setText(locationFrench);
            submitButton.setText(submitFrench);
            exitButton.setText(exitFrench);
        }
    }

    //lamba expression to get the current stage
    public Login_Stage_Interface getStage = (event -> (Stage) ((Button) event.getSource()).getScene().getWindow());

    /**
     * Submit button performs input validations and then validates the users
     * login ID and password for a match in the DB through the UsersDaoImpl.
     * If login credentials are authenticated then an alert is displayed for each
     * meeting occurring within fifteen minutes of the login time in user's local time.
     * User valid or invalid login attempts are recorded in login_activity.txt
     * Three Lambda expressions were used in this method:
     * 1. meetingAlert was used to add a default number of minutes (ALERT_TIMEFRAME) to the login time
     * in order to establish the window to check if a meeting alert is appropriate. This expression could be used
     * to create additional alerts timeframes given other requirements in minutes.
     * 2. message was used to format an alert message which notifies the user of each meeting occurring within
     * the fifteen minutes of login.
     * 3. getStage is used to shorten the amount of code needed to prepare the stage for loading a new window
     */
    @FXML
    void submit_btn(ActionEvent event) throws IOException {

        String userId = user_id_text.getText();
        String password = pass_text.getText();
        LocalDateTime userDateTime = LocalDateTime.now();
        BufferedWriter writer = null;

        //input validation
        if (userId.isBlank()) {
            //alert blank
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("User ID Field Empty");
            alert.setContentText("Please enter a User ID.");
            alert.showAndWait();
            return;
        }

        if (password.isBlank()) {
            //alert blank
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Password Field Empty");
            alert.setContentText("Please enter a password.");
            alert.showAndWait();
            return;
        }

        //check DB for match. If matched open application window else alert.
        Users currentUser = UsersDaoImpl.getUser(userId);
        if (currentUser.getPassword().equals(password)) {
            //Alert if user has appointment scheduled within the next 15 minutes
            ObservableList<Appointments> userAppointments  = AppointmentsDaoImpl.getUserAppointments(currentUser.getUserId());

            //Lambda expression used to add fifteen minutes or any default timeframe
            Login_Time_Interface meetingAlert = t -> t.plusMinutes(ALERT_TIMEFRAME);
            LocalDateTime userTimeFrame = meetingAlert.addToLocal(userDateTime);

            boolean userHasAppts = false;
            for (Appointments appointment : userAppointments) {
                if (userTimeFrame.isAfter(appointment.getStart()) && userDateTime.isBefore(appointment.getEnd())) {
                    userHasAppts = true;
                    //lambda expression used to alert user of meeting occurring within 15 minutes of login
                    Login_Alert_Interface message = (id, start, end) -> "You have a meeting scheduled with Appointment ID: " + id + "  from  " + start + "  until  " + end;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Meeting Scheduled Within Next 15 Minutes");
                    alert.setContentText(message.meetingMsg(appointment.getAppointment_ID(), appointment.getStart(), appointment.getEnd()));
                    alert.showAndWait();
                }
            }
            if (!userHasAppts) {
                //alert no appointments occurring within 15 minutes of logging in
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Meetings Scheduled Within Next 15 Minutes");
                alert.setContentText("You have no meetings scheduled within the next 15 minutes.");
                alert.showAndWait();
            }

            String loginSuccess = "User " + userId + " had a valid login on " + userDateTime + "\n";
            try {
                writer = new BufferedWriter(new FileWriter("login_activity.txt", true));
                writer.write(loginSuccess);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            stage = getStage.thisStage(event);
            scene = FXMLLoader.load(getClass().getResource("/View/Application_View.fxml"));
        } else {
            //Alert invalid login
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Login");
            alert.setContentText("Invalid User ID or Password");
            String loginFailure = "User " + userId + " had a invalid login on " + userDateTime + "\n";
            try {
                writer = new BufferedWriter(new FileWriter("login_activity.txt", true));
                writer.write(loginFailure);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                stage = getStage.thisStage(event);
                scene = FXMLLoader.load(getClass().getResource("/View/Login_Form.fxml"));
            }
        }
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
                System.out.println(e.getMessage());
        }
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * When Exit button is pressed, this method confirms with the user before
     * exiting program.
     */
    @FXML
    void exit_btn(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm exit");
        alert.setContentText("Are you sure you want to exit program?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }
}
