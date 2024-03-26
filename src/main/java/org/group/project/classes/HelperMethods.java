package org.group.project.classes;

import java.io.File;
import java.io.FileNotFoundException;
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

}
