package org.group.project.classes;

/**
 * This class handles all users primary data
 *
 * @author azmi_maz
 */
public class User {
    protected String firstName;
    protected String lastName;
    protected String username;

    /**
     * This constructor creates a new user with basic data.
     *
     * @param firstName - First name of the user.
     * @param lastName  - Last name of the user.
     * @param username  - The username used to log in.
     */
    public User(String firstName, String lastName, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

    /**
     * Getter method to get the user first name.
     *
     * @return the first name of the user.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter method to get the user last name.
     *
     * @return the last name of the user.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Getter method to get the user's username.
     *
     * @return the username used by the user.
     */
    public String getUsername() {
        return username;
    }

}
