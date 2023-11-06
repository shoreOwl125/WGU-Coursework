package Controller;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

/**
 * Interface Login_Stage_Interface.java implements the functional interface
 * used by the getStage lambda expression to prepare the current stage for a new scene.
 * Inputs: ActionEvent returns Stage.
 */
public interface Login_Stage_Interface {
    Stage thisStage(ActionEvent event);
}
