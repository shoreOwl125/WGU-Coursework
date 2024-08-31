package Model;

/**
 * Class Users.java contains variables and methods for the Users objects.
 */
public class Users {

    private int userId;
    private String userName;
    private String password;

    /**
     * Constructor for instantiating new Users objects
     * @param userId index established by database
     * @param userName is the login name for the user
     * @param password is the user's login password
     */
    public Users(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /**
     * @return the user id / database index
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @return the user login id/name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @return the user login password
     */
    public String getPassword() {
        return password;
    }

}
