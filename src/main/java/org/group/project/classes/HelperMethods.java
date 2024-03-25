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

    public static boolean isUsernameExist(String username) throws FileNotFoundException {

        File myFile = new File(FileNames.DATA.getDataFile("USERS"));
        Scanner myReader = new Scanner(myFile);

        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            String[] splitData = data.split(",");
            String getUsername = splitData[2];
            if (getUsername.equalsIgnoreCase(username)) {
                return true;
            }
        }
        myReader.close();
        return false;
    }
}
