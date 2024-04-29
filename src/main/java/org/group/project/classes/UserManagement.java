package org.group.project.classes;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import org.group.project.classes.auxiliary.DataFileStructure;
import org.group.project.classes.auxiliary.DataManager;
import org.group.project.exceptions.ClearFileFailedException;
import org.group.project.exceptions.TextFileNotFoundException;

import java.util.*;

/**
 * This class handles all the users registered to use the application.
 *
 * @author azmi_maz
 */
public class UserManagement {
    private List<User> userList;
    private static final List<String> staffTypes =
            new ArrayList<>(Arrays.asList(
                    "Chef",
                    "Driver",
                    "Waiter"
            ));

    /**
     * This constructor is default without any parameters.
     */
    public UserManagement() throws TextFileNotFoundException {

        userList = new ArrayList<>();

        try {
            userList = getUserDataFromDatabase();
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }

    }

    /**
     * Getter method to get the list of all registered users.
     *
     * @return the list of all registered users.
     */
    public List<User> getUserList() {
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
    public List<User> getUserDataFromDatabase()
            throws TextFileNotFoundException {

        try {
            List<User> userList = new ArrayList<>();
            List<String> rawUserList = DataManager
                    .allDataFromFile("USERS");

            for (String user : rawUserList) {
                userList.add(
                        getUserFromString(user)
                );
            }
            return userList;

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // TODO
    public User getUserFromString(
            String user
    ) {
        List<String> userDetails = List.of(user.split(","));
        int userId = Integer.parseInt(
                userDetails.get(DataFileStructure
                        .getIndexByColName("USERS",
                                "userId")));
        String firstName = userDetails.get(
                DataFileStructure.getIndexByColName("USERS",
                        "firstName"));
        String lastName = userDetails.get(
                DataFileStructure.getIndexByColName("USERS",
                        "lastName"));
        String username = userDetails.get(
                DataFileStructure.getIndexByColName("USERS",
                        "username"));
        String userType = userDetails.get(
                DataFileStructure.getIndexByColName("USERS",
                        "userType"));
        String address = userDetails.get(
                DataFileStructure.getIndexByColName("USERS",
                        "address"));
        int numOfHoursToWork = Integer.parseInt(
                userDetails.get(DataFileStructure
                        .getIndexByColName("USERS",
                                "numOfHoursToWork")));
        int numOfTotalHoursWorked = Integer.parseInt(
                userDetails.get(DataFileStructure
                        .getIndexByColName("USERS",
                                "numOfTotalHoursWorked")));
        boolean hasAdminRight = Boolean.parseBoolean(
                userDetails.get(DataFileStructure
                        .getIndexByColName("USERS",
                                "hasAdminRight")));
        boolean isAvailable = Boolean.parseBoolean(
                userDetails.get(DataFileStructure
                        .getIndexByColName("USERS",
                                "isAvailable")));
        int maxDeliveries = Integer.parseInt(
                userDetails.get(DataFileStructure
                        .getIndexByColName("USERS",
                                "maxDeliveries")));

        if (userType.equalsIgnoreCase("customer")) {
            return new Customer(
                    firstName,
                    lastName,
                    username,
                    userId,
                    address
            );
        } else if (userType.equalsIgnoreCase("chef")) {
            return new Chef(
                    firstName,
                    lastName,
                    username,
                    hasAdminRight,
                    numOfHoursToWork,
                    numOfTotalHoursWorked
            );
        } else if (userType.equalsIgnoreCase("driver")) {
            return new Driver(
                    userId,
                    firstName,
                    lastName,
                    username,
                    hasAdminRight,
                    numOfHoursToWork,
                    numOfTotalHoursWorked,
                    isAvailable,
                    maxDeliveries
            );
        } else if (userType.equalsIgnoreCase("manager")) {
            return new Manager(
                    firstName,
                    lastName,
                    username,
                    hasAdminRight,
                    numOfHoursToWork,
                    numOfTotalHoursWorked
            );
        } else if (userType.equalsIgnoreCase("waiter")) {
            return new Waiter(
                    firstName,
                    lastName,
                    username,
                    hasAdminRight,
                    numOfHoursToWork,
                    numOfTotalHoursWorked
            );
        }
        return null;
    }

    // TODO
    public User getUserByUsername(
            String username
    ) {
        for (User user : userList) {
            if (user.getUsername()
                    .equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    // TODO
    public User getUserByUserId(
            String userId
    ) throws TextFileNotFoundException {
        try {
            for (User user : userList) {
                if (user.getUserId() == Integer.parseInt(userId)) {
                    return user;
                }
            }
            return null;

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // TODO
    public int getUserIdByUsername(
            String username
    ) throws TextFileNotFoundException {
        try {
            List<String> listOfUsers = DataManager
                    .allDataFromFile("USERS");
            int searchUserId = -1;
            for (String user : listOfUsers) {
                String[] userDetails = user.split(",");
                int userIdIndex = DataFileStructure.getIndexByColName(
                        "USERS",
                        "userId");
                int usernameIndex = DataFileStructure.getIndexByColName(
                        "USERS",
                        "username");
                if (userDetails[usernameIndex].equalsIgnoreCase(username)) {
                    searchUserId = Integer.parseInt(userDetails[userIdIndex]);
                }
            }
            if (searchUserId > -1) {
                return searchUserId;
            }
            return searchUserId;
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // TODO comment
    public void getStaffData(
            ObservableList<Staff> data
    ) {

        // TODO to filter
        List<User> staffData = getUserList();
        for (User staff : staffData) {

            String userType = getStaffClass(staff);

            // TODO
            if (!userType.equalsIgnoreCase("customer")
                    && !userType.equalsIgnoreCase("manager")) {
                data.add((Staff) staff);
            }
        }
    }

    // TODO
    public Customer getCustomerById(
            int customerId
    ) throws TextFileNotFoundException {
        try {
            List<User> userList = getUserList();
            for (User user : userList) {
                if (isCustomer(user)
                        && user.getUserId() == customerId) {
                    return (Customer) user;
                }
            }
            return null;

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // TODO
    public Driver getDriverById(
            int driverId
    ) throws TextFileNotFoundException {
        try {
            List<User> userList = getUserList();
            for (User user : userList) {
                if (isDriver(user)
                        && user.getUserId() == driverId) {
                    return (Driver) user;
                }
            }
            return null;

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // TODO
    public String getStaffClass(User user) {
        Object classType = user.getClass();
        String objectType = List.of(
                classType.toString()
                        .split("\\.")).getLast();
        return objectType;
    }

    // TODO
    public boolean isCustomer(User user) {
        if (getStaffClass(user)
                .equalsIgnoreCase("customer")) {
            return true;
        }
        return false;
    }

    // TODO
    public boolean isDriver(User user) {
        if (getStaffClass(user)
                .equalsIgnoreCase("driver")) {
            return true;
        }
        return false;
    }

    // TODO
    public void updateDriverList(
            ComboBox<Driver> drivers
    ) {

        List<User> staffData = getUserList();
        for (User staff : staffData) {

            String userType = getStaffClass(staff);

            if (userType.equalsIgnoreCase("driver")) {
                drivers.getItems().add(
                        (Driver) staff
                );
            }
        }
    }

    // TODO
    public void updateCustomerList(
            List<Customer> customerList
    ) {

        List<User> userList = getUserList();
        List<Customer> unsortedCustomerList = new ArrayList<>();
        for (User user : userList) {
            if (isCustomer(user)) {
                unsortedCustomerList.add((Customer) user);
            }
        }
        Collections.sort(
                unsortedCustomerList,
                Comparator.comparing(Customer::getDataForListDisplay)
        );
        customerList.addAll(unsortedCustomerList);

    }

    /**
     * This method gets a new customer id which is incremented from the last
     * one.
     *
     * @return the new customer id for new customer.
     */
    public int getNewUserId() throws TextFileNotFoundException {

        try {
            int lastCustomerId = -1;
            for (User user : userList) {
                lastCustomerId = user.getUserId();
            }
            if (lastCustomerId > -1) {
                lastCustomerId++;
                return lastCustomerId;
            }
            return lastCustomerId;

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // TODO
    public User createNewUser(
            String firstName,
            String lastName,
            String username
    ) {
        return new User(
                firstName,
                lastName,
                username
        );
    }

    // TODO
    public void addNewCustomerToDatabase(
            Customer newCustomer
    ) throws TextFileNotFoundException, ClearFileFailedException {
        List<String> userDetails = new ArrayList<>();

        String customerId = String.valueOf(newCustomer.getCustomerId());
        String firstName = newCustomer.getFirstName();
        String lastName = newCustomer.getLastName();
        String username = newCustomer.getUsername();
        String userType = "customer";
        String address = newCustomer.getDeliveryAddress();
        userDetails.addAll(Arrays.asList(
                customerId,
                firstName,
                lastName,
                username,
                userType,
                address,
                "0", "0", "false", "false",
                "0"
        ));
        try {
            DataManager.appendDataToFile("USERS", userDetails);
            persistActiveUserData(newCustomer);
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch (ClearFileFailedException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // TODO
    public boolean addNewStaffToDatabase(
            Staff newStaff
    ) throws TextFileNotFoundException {
        List<String> userDetails = new ArrayList<>();
        String staffType = getStaffClass(newStaff).toLowerCase();
        String userId = String.valueOf(getNewUserId());
        String firstName = newStaff.getFirstName();
        String lastName = newStaff.getLastName();
        String username = newStaff.getUsername();
        String userType = staffType;
        String address = "";
        String numOfHoursToWork = String.valueOf(newStaff
                .getNumOfHoursToWork());
        String numOfTotalHoursWorked = String.valueOf(newStaff
                .getNumOfTotalHoursWorked());
        String hasAdminRight = String.valueOf(
                newStaff.getHasAdminRight());
        String isAvailable = "";
        String maxDeliveries = "";
        if (staffType
                .equalsIgnoreCase("driver")) {
            Driver driver = (Driver) newStaff;
            isAvailable = String.valueOf(driver.getIsAvailable());
            maxDeliveries = String.valueOf(driver.getMaxDeliveries());
        } else {
            isAvailable = "false";
            maxDeliveries = "0";
        }
        userDetails.addAll(Arrays.asList(
                userId,
                firstName,
                lastName,
                username,
                userType,
                address,
                numOfHoursToWork,
                numOfTotalHoursWorked,
                hasAdminRight,
                isAvailable,
                maxDeliveries
        ));
        try {
            return DataManager.appendDataToFile("USERS", userDetails);
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // TODO
    public void persistActiveUserData(
            User user
    ) throws TextFileNotFoundException, ClearFileFailedException {
        try {

            List<String> currentUser = new ArrayList<>();
            String userId = String.valueOf(user.getUserId());
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String username = user.getUsername();
            String userType = getStaffClass(user).toLowerCase();
            currentUser.addAll(Arrays.asList(
                    userId,
                    firstName,
                    lastName,
                    username,
                    userType
            ));

            DataManager.clearFileData("ACTIVE_USER");
            DataManager.appendDataToFile("ACTIVE_USER", currentUser);

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch (ClearFileFailedException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // TODO
    public Customer createNewCustomer(
            String firstName,
            String lastName,
            String username,
            String address
    ) throws TextFileNotFoundException {
        try {
            return new Customer(
                    firstName,
                    lastName,
                    username,
                    address
            );
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // TODO
    public Staff createNewStaff(
            String firstName,
            String lastName,
            String username,
            String staffType
    ) {
        if (staffType.equalsIgnoreCase(
                "chef"
        )) {
            return new Chef(
                    firstName,
                    lastName,
                    username
            );
        } else if (staffType.equalsIgnoreCase(
                "waiter"
        )) {
            return new Waiter(
                    firstName,
                    lastName,
                    username
            );
        } else {
            return new Driver(
                    firstName,
                    lastName,
                    username
            );
        }
    }

    // TODO
    public boolean editExistingUserProfile(
            int userId,
            String firstName,
            String lastName,
            String username
    ) throws TextFileNotFoundException {

        try {
            DataManager.editColumnDataByUniqueId(
                    "USERS", userId,
                    "firstName", firstName
            );
            DataManager.editColumnDataByUniqueId(
                    "USERS", userId,
                    "lastName", lastName
            );
            DataManager.editColumnDataByUniqueId(
                    "USERS", userId,
                    "username", username
            );
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
        return true;
    }

    /**
     * This method checks if a username is already registered or not.
     *
     * @param username - the username to check against the database.
     * @return true if the username exist.
     */
    public boolean isUsernameAlreadyExist(
            String username
    ) {
        boolean isUsernameExist = false;
        for (User user : userList) {
            if (user.getUsername()
                    .equalsIgnoreCase(username)) {
                isUsernameExist = true;
            }
        }
        return isUsernameExist;
    }

    // TODO
    public boolean editExistingUserProfile(
            int userId,
            String firstName,
            String lastName,
            String username,
            String address
    ) throws TextFileNotFoundException {

        try {
            DataManager.editColumnDataByUniqueId(
                    "USERS", userId,
                    "firstName", firstName
            );
            DataManager.editColumnDataByUniqueId(
                    "USERS", userId,
                    "lastName", lastName
            );
            DataManager.editColumnDataByUniqueId(
                    "USERS", userId,
                    "username", username
            );

            String formattedAddress = formatAddressToWrite(address);
            DataManager.editColumnDataByUniqueId(
                    "USERS", userId,
                    "address", formattedAddress
            );
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
        return true;
    }

    // TODO
    public boolean editExistingUserProfile(
            String userId,
            String firstName,
            String lastName,
            String username,
            String hoursLeft,
            String totalHoursWorked,
            String position
    ) throws TextFileNotFoundException {

        try {
            DataManager.editColumnDataByUniqueId("USERS", userId,
                    "firstName", firstName);
            DataManager.editColumnDataByUniqueId("USERS", userId,
                    "lastName", lastName);
            DataManager.editColumnDataByUniqueId("USERS", userId,
                    "username", username);
            DataManager.editColumnDataByUniqueId("USERS", userId,
                    "numOfHoursToWork", hoursLeft);
            DataManager.editColumnDataByUniqueId("USERS", userId,
                    "numOfTotalHoursWorked", totalHoursWorked);
            DataManager.editColumnDataByUniqueId("USERS", userId,
                    "userType", position);
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
        return true;
    }

    /**
     * This method formats address to transform any symbols incompatible for
     * data storage.
     *
     * @param address - the unformatted address taken from textfield.
     * @return an address with the correct format.
     */
    public String formatAddressToWrite(String address) {
        return address.replaceAll(",", ";");
    }

    // TODO
    public User getActiveUser()
            throws TextFileNotFoundException {
        try {
            List<String> data = DataManager
                    .allDataFromFile("ACTIVE_USER");
            if (!data.isEmpty()) {
                List<String> activeUserDetails = List.of(
                        data.get(0).split(","));
                String userId = activeUserDetails
                        .get(DataFileStructure
                                .getIndexColOfUniqueId("ACTIVE_USER"));
                return getUserByUserId(userId);
            }
            return null;
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // TODO
    public void updateStaffTypeChoiceBox(
            ChoiceBox<String> choiceBox
    ) {
        for (String staffType : staffTypes) {
            choiceBox.getItems()
                    .add(staffType);
        }
    }


}
