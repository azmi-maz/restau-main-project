package org.group.project.classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

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
    public static int getNewCustomerId() throws FileNotFoundException {

        List<String> listOfUsers = DataManager.allDataFromFile("USERS");
        int lastCustomerId = -1;
        for (String user : listOfUsers) {
            String[] userDetails = user.split(",");
            int isCustomerIndex = DataFileStructure.getIndexByColName(
                    "USERS",
                    "isCustomer");
            int customerIdIndex = DataFileStructure.getIndexByColName(
                    "USERS",
                    "customerId");
            boolean isCustomer =
                    Boolean.parseBoolean(userDetails[isCustomerIndex]);
            if (isCustomer) {
                lastCustomerId = Integer.parseInt(userDetails[customerIdIndex]);
            }
        }
        if (lastCustomerId > -1) {
            lastCustomerId++;
            return lastCustomerId;
        }
        return lastCustomerId;
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

}
