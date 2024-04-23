package org.group.project.classes.auxiliary;

import org.group.project.classes.Customer;
import org.group.project.classes.User;
import org.group.project.classes.auxiliary.DataFileStructure;
import org.group.project.classes.auxiliary.DataManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * This class contains helper functions to assist in data handling.
 *
 * @author azmi_maz
 */
public class HelperMethods {

    /**
     * This method checks if a username is already registered or not.
     *
     * @param username - the username to check against the database.
     * @return true if the username exist.
     * @throws FileNotFoundException - if user file does not exist.
     */
    public static boolean isUsernameExist(
            String username) throws FileNotFoundException {

        File file = DataManager.getUserFile();
        Scanner fileReader = new Scanner(file);
        boolean isUsernameExist = DataManager.checksOneValueExist(fileReader,
                DataFileStructure.getIndexByColName("USERS", "username"),
                username);
        fileReader.close();
        return isUsernameExist;
    }

    /**
     * This method gets a new customer id which is incremented from the last
     * one.
     *
     * @return the new customer id for new customer.
     */
    public static int getNewUserId() throws FileNotFoundException {

        List<String> listOfUsers = DataManager.allDataFromFile("USERS");
        int lastCustomerId = -1;
        for (String user : listOfUsers) {
            String[] userDetails = user.split(",");
            int customerIdIndex = DataFileStructure.getIndexByColName(
                    "USERS",
                    "userId");
            lastCustomerId = Integer.parseInt(userDetails[customerIdIndex]);
        }
        if (lastCustomerId > -1) {
            lastCustomerId++;
            return lastCustomerId;
        }
        return lastCustomerId;
    }

    // TODO note this only works if unique id is Integer type
    public static int getNewIdByFile(
            String fileType) throws FileNotFoundException {
        List<String> listOfData = DataManager.allDataFromFile(fileType);
        int lastId = -1;
        for (String data : listOfData) {
            String[] dataDetails = data.split(",");
            int uniqueIdIndex = DataFileStructure.getIndexColOfUniqueId(fileType);
            lastId = Integer.parseInt(dataDetails[uniqueIdIndex]);
        }
        if (lastId > -1) {
            lastId++;
            return lastId;
        } else {
            // TODO have not tested if there are no lines at all, should
            //  return 1?
            lastId = 1;
        }
        return lastId;
    }

    /**
     * This method gets a row of data that matches an id from a specified
     * database.
     *
     * @param fileName - the name of the database.
     * @param findId   - the unique id that identify a row of data.
     * @return list of string of one row of data.
     */
    public static List<String> getDataById(String fileName,
                                           String findId) throws FileNotFoundException {

        List<String> fileData = DataManager.allDataFromFile(fileName);
        List<String> rowFound = new ArrayList<>();
        int colIndex = DataFileStructure.getIndexColOfUniqueId(fileName);

        for (String row : fileData) {
            String[] rowDetails = row.split(",");
            if (rowDetails[colIndex].equalsIgnoreCase(findId)) {
                String[] data = row.split(",");
                Collections.addAll(rowFound, data);
            }
        }
        if (!rowFound.isEmpty()) {
            return rowFound;
        }
        return null;
    }

    /**
     * This method formats address to transform any symbols incompatible for
     * data storage.
     *
     * @param address - the unformatted address taken from textfield.
     * @return an address with the correct format.
     */
    public static String formatAddressToWrite(String address) {
        return address.replaceAll(",", ";");
    }

    /**
     * This method formats address to transform any symbols compatible for
     * users to read.
     *
     * @param address - the formatted address taken from database.
     * @return an address with the correct format.
     */
    public static String formatAddressToRead(String address) {
        return address.replaceAll(";", ",");
    }

    // TODO comment
    public static int findUserIdByUsername(
            String username) throws FileNotFoundException {
        List<String> listOfUsers = DataManager.allDataFromFile("USERS");
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
    }

    // TODO comment
    public static List<String> getUserDataByUsername(
            String username) throws FileNotFoundException {
        if (!isUsernameExist(username)) {
            return null;
        } else {
            String userId = Integer.toString(findUserIdByUsername(username));
            return getDataById("USERS", userId);
        }
    }

    // TODO comment
    public static String getUserTypeFromDataString(List<String> userData) {
        int userTypeIndex = DataFileStructure.getIndexByColName("USERS",
                "userType");

        return userData.get(userTypeIndex);
    }

    // TODO comment
    public static User getActiveUser() throws FileNotFoundException {
        List<String> data = DataManager.allDataFromFile("ACTIVE_USER");
        if (!data.isEmpty()) {
            List<String> activeUserDetails = List.of(data.get(0).split(","));
            return new User(
                    activeUserDetails.get(DataFileStructure.getIndexByColName("ACTIVE_USER", "firstName")),
                    activeUserDetails.get(DataFileStructure.getIndexByColName("ACTIVE_USER", "lastName")),
                    activeUserDetails.get(DataFileStructure.getIndexByColName("ACTIVE_USER", "username"))
            );
        }
        return null;
    }

    // TODO comment
    public static void clearActiveUser() throws IOException {
        DataManager.clearFileData("ACTIVE_USER");
    }

    // TODO to transfer to Customer class
    public static List<Customer> getCustomers() throws FileNotFoundException {
        List<Customer> updatedCustomerList = new ArrayList<>();
        List<String> userFile = DataManager.allDataFromFile("USERS");
        for (String userDetails : userFile) {
            List<String> user = List.of(userDetails.split(","));
            boolean isCustomer = user
                    .get(DataFileStructure
                            .getIndexByColName("USERS",
                                    "userType"))
                    .equalsIgnoreCase("customer");
            if (isCustomer) {
                updatedCustomerList.add(new Customer(
                        user.get(DataFileStructure
                                .getIndexByColName("USERS",
                                        "firstName")),
                        user.get(DataFileStructure
                                .getIndexByColName("USERS",
                                        "lastName")),
                        user.get(DataFileStructure
                                .getIndexByColName("USERS",
                                        "username")),
                        Integer.parseInt(user.get(DataFileStructure
                                .getIndexColOfUniqueId("USERS"))),
                        user.get(DataFileStructure
                                .getIndexByColName("USERS",
                                        "address"))
                ));
            }
        }
        return updatedCustomerList;
    }
}