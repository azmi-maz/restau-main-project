package org.group.project.classes;

import java.util.List;

/**
 * This class handles all the users registered to use the application.
 *
 * @author azmi_maz
 */
public class UserManagement {
    private List<User> userList;

    /**
     * This constructor is default without any parameters.
     */
    public UserManagement() {
    }

    /**
     * Getter method to get the list of all registered users.
     *
     * @return the list of all registered users.
     */
    public List<User> getListOfAllUsers() {
        return userList;
    }

    /**
     * This method add new users to the list.
     *
     * @param user - new user
     */
    public boolean addNewUser(User user) {
        if (!userList.contains(user)) {
            userList.add(user);
            return true;
        }
        return false;
    }

    /**
     * This method removes user from the list, if the user exist.
     *
     * @param searchUser - the user to be deleted.
     * @return true if the user is successfully deleted, else it returns false.
     */
    public boolean removeUser(User searchUser) {
        if (userList.contains(searchUser)) {
            for (User user : userList) {
                if (user.username.equalsIgnoreCase(searchUser.username)) {
                    userList.remove(searchUser);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method edits the user details.
     *
     * @param searchUser    - the user being edited.
     * @param attributeName - the attribute name of the user details.
     * @param newValue      - the new value for the attribute to be edited.
     * @return true if the user details was successful.
     */
    public boolean editUserDetails(User searchUser, String attributeName,
                                   Object newValue) {
        // To code
        return true;
    }


}
