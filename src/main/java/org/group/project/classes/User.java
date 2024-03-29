package org.group.project.classes;

import java.util.List;

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

    /**
     * This method converts a string array back to user.
     * @param rawData - the user data in string array format.
     * @return a user object.
     */
    public static User convertStringToUser(String rawData) {

        List<String> userColumns = DataFileStructure.getValues("USERS");
        String[] rawDataArr = rawData.split(",");
        String firstName = null;
        String lastName = null;
        String username = null;
        for (int i = 0; i < userColumns.size(); i++) {
            String colName = userColumns.get(i);
            String dataVal = rawDataArr[i];
            switch (colName) {
                case "firstName":
                    firstName = dataVal;
                    break;
                case "lastName":
                    lastName = dataVal;
                    break;
                case "username":
                    username = dataVal;
                    break;
            }
        }
        return new User(firstName, lastName, username);
    }

    /**
     * Get the user data and format it to fit with list display.
     * @return A user data.
     */
    public String getDataForListDisplay() {
        return firstName + " " + lastName;
    }

}
