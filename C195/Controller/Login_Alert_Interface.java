package Controller;

import java.time.LocalDateTime;

/**
 * Interface Login_Alert_Interface.java implements the functional interface
 * used by the message lambda expression to format the alert messages for current meetings.
 * Inputs: int, LocalDateTime and LocalDateTime returns a String.
 */
public interface Login_Alert_Interface {
    //format alert message for upcoming meetings
    String meetingMsg(int id, LocalDateTime start, LocalDateTime end);
}
