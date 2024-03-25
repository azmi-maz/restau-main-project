package org.group.project.classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class contains helper functions to assist in data handling.
 *
 * @author azmi_maz
 */
public class HelperMethods {

    /**
     * This methods checks if a username is already registered or not.
     * @param username - the username to check against the database.
     * @return true if the username exist.
     * @throws FileNotFoundException - if user file does not exist.
     */
    public static boolean isUsernameExist(String username) throws FileNotFoundException {

        File file = DataManager.getUserFile();
        Scanner fileReader = new Scanner(file);
        boolean isUsernameExist = DataManager.checksOneValueExist(fileReader,
                2, username);
        fileReader.close();
        return isUsernameExist;
    }
}
