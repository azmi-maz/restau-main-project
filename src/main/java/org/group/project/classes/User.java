package org.group.project.classes;

import org.group.project.classes.auxiliary.DataFileStructure;
import org.group.project.exceptions.TextFileNotFoundException;

import java.util.List;

/**
 * This class handles all users primary data
 *
 * @author azmi_maz
 */
public class User {
    private static final String USER_FILE = "USERS";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String USERNAME = "username";
    private static final String DISPLAY_USER_INFO = "%s%s %s%s";
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
     * This method gets capitalised first name of the user.
     *
     * @return the user first name.
     */
    public String getFirstNameForDisplay() {
        return firstName.substring(0, 1).toUpperCase()
                + firstName.substring(1);
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
     * This method gets the capitalised last name of the user.
     *
     * @return the last name of the user.
     */
    public String getLastNameForDisplay() {
        return lastName.substring(0, 1).toUpperCase()
                + lastName.substring(1);
    }

    /**
     * Getter method to get the user's username.
     *
     * @return the username used by the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method gets the user id.
     *
     * @return - the id of the user.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public int getUserId() throws TextFileNotFoundException {

        try {
            UserManagement userManagement = new UserManagement();
            return userManagement.getUserIdByUsername(
                    getUsername());
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method converts a string array back to user.
     *
     * @param rawData - the user data in string array format.
     * @return a user object.
     */
    public static User convertStringToUser(String rawData) {

        List<String> userColumns = DataFileStructure.getValues(USER_FILE);
        String[] rawDataArr = rawData.split(",");
        String firstName = null;
        String lastName = null;
        String username = null;
        for (int i = 0; i < userColumns.size(); i++) {
            String colName = userColumns.get(i);
            String dataVal = rawDataArr[i];
            switch (colName) {
                case FIRST_NAME:
                    firstName = dataVal;
                    break;
                case LAST_NAME:
                    lastName = dataVal;
                    break;
                case USERNAME:
                    username = dataVal;
                    break;
            }
        }
        return new User(firstName, lastName, username);
    }

    /**
     * Get the user data and format it to fit with list display.
     *
     * @return A user data.
     */
    public String getDataForListDisplay() {
        return String.format(DISPLAY_USER_INFO,
                firstName.substring(0, 1).toUpperCase(),
                firstName.substring(1),
                lastName.substring(0, 1).toUpperCase(),
                lastName.substring(1));
    }

}
