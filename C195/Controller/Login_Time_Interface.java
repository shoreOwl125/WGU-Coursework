package Controller;

import java.time.LocalDateTime;
/**
 * Interface Login_Time_Interface.java implements the functional interface
 * used by the meetingAlert lambda expression to add minutes to the login time
 * Inputs a LocalDateTime, returns a LocalDateTime
 */
public interface Login_Time_Interface {
    LocalDateTime addToLocal(LocalDateTime curDateTime);
}
