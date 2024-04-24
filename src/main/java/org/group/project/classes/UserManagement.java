package org.group.project.classes;

import javafx.collections.ObservableList;
import org.group.project.classes.auxiliary.DataFileStructure;
import org.group.project.classes.auxiliary.DataManager;

import java.io.FileNotFoundException;
import java.util.ArrayList;
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

        userList = new ArrayList<>();

        try {
            userList = getUserDataFromDatabase();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
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

    // TODO
    public List<User> getUserDataFromDatabase() throws FileNotFoundException {

        List<User> userList = new ArrayList<>();

        List<String> rawUserList = DataManager.allDataFromFile("USERS");

        for (String user : rawUserList) {
            List<String> userDetails = List.of(user.split(","));
            int userId = Integer.parseInt(userDetails.get(DataFileStructure.getIndexByColName("USERS", "userId")));
            String firstName = userDetails.get(DataFileStructure.getIndexByColName("USERS", "firstName"));
            String lastName = userDetails.get(DataFileStructure.getIndexByColName("USERS", "lastName"));
            String username = userDetails.get(DataFileStructure.getIndexByColName("USERS", "username"));
            String userType = userDetails.get(DataFileStructure.getIndexByColName("USERS", "userType"));
            String address = userDetails.get(DataFileStructure.getIndexByColName("USERS", "address"));
            int numOfHoursToWork = Integer.parseInt(userDetails.get(DataFileStructure.getIndexByColName("USERS", "numOfHoursToWork")));
            int numOfTotalHoursWorked = Integer.parseInt(userDetails.get(DataFileStructure.getIndexByColName("USERS", "numOfTotalHoursWorked")));
            boolean hasAdminRight = Boolean.parseBoolean(userDetails.get(DataFileStructure.getIndexByColName("USERS", "hasAdminRight")));
            boolean isAvailable = Boolean.parseBoolean(userDetails.get(DataFileStructure.getIndexByColName("USERS", "isAvailable")));
            int maxDeliveries = Integer.parseInt(userDetails.get(DataFileStructure.getIndexByColName("USERS", "maxDeliveries")));

            if (userType.equalsIgnoreCase("customer")) {
                userList.add(new Customer(
                        firstName,
                        lastName,
                        username,
                        userId,
                        address
                ));
            } else if (userType.equalsIgnoreCase("chef")) {
                userList.add(new Chef(
                        firstName,
                        lastName,
                        username,
                        hasAdminRight,
                        numOfHoursToWork,
                        numOfTotalHoursWorked
                ));
            } else if (userType.equalsIgnoreCase("driver")) {
                userList.add(new Driver(
                        userId,
                        firstName,
                        lastName,
                        username,
                        hasAdminRight,
                        numOfHoursToWork,
                        numOfTotalHoursWorked,
                        isAvailable,
                        maxDeliveries
                ));
            } else if (userType.equalsIgnoreCase("manager")) {
                userList.add(new Manager(
                        firstName,
                        lastName,
                        username,
                        hasAdminRight,
                        numOfHoursToWork,
                        numOfTotalHoursWorked
                ));
            } else if (userType.equalsIgnoreCase("waiter")) {
                userList.add(new Waiter(
                        firstName,
                        lastName,
                        username,
                        hasAdminRight,
                        numOfHoursToWork,
                        numOfTotalHoursWorked
                ));
            }
        }
        return userList;
    }

    // TODO comment
    public void getStaffData(
            ObservableList<Staff> data
    ) throws FileNotFoundException {

        // TODO to filter
        List<User> staffData = getUserDataFromDatabase();
        for (User staff : staffData) {
            Object classType = staff.getClass();
            String objectType = List.of(classType.toString().split("\\.")).getLast();

            // TODO
            if (!objectType.equalsIgnoreCase("customer")
            && !objectType.equalsIgnoreCase("manager")) {
                data.add((Staff) staff);
            }
        }
    }


}
