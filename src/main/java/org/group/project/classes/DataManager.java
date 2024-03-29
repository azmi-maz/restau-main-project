package org.group.project.classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class handles file reading and writing to store/persist data in this
 * program.
 *
 * @author azmi_maz
 */
public abstract class DataManager {

    /**
     * This method gets the users file.
     *
     * @return the users file.
     */
    public static File getUserFile() {
        return new File(FileNames.DATA.getDataFile("USERS"));
    }

    /**
     * This method checks if a value exist in a database.
     *
     * @param fileToRead - the file loaded on Scanner.
     * @param index      - the column index of a database.
     * @param value      - the value to compare against.
     * @return true if the value already exist in the database.
     */
    public static boolean checksOneValueExist(Scanner fileToRead,
                                              int index, String value) {

        while (fileToRead.hasNextLine()) {
            String data = fileToRead.nextLine();
            String[] splitData = data.split(",");
            String getUsername = splitData[index];

            if (getUsername.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method gets all the data from a file.
     *
     * @param fileName - the name of the file.
     * @return the list of strings that represent rows of data.
     * @throws FileNotFoundException - if file does not exist.
     */
    public static List<String> allDataFromFile(
            String fileName) throws FileNotFoundException {
        File file = new File(FileNames.DATA.getDataFile(fileName));
        List<String> resultArray = new ArrayList<>();
        Scanner fileReader = new Scanner(file);
        while (fileReader.hasNextLine()) {
            String data = fileReader.nextLine();
            resultArray.add(data);
        }
        fileReader.close();
        return resultArray;
    }

    /**
     * This method appends new user data to the database.
     *
     * @param newUser - the new user details in an array of String format.
     * @throws IOException - if file to append does not exist.
     */
    public static void appendNewUserToFile(
            List<String> newUser) throws IOException {

        String fileName = FileNames.DATA.getDataFile("USERS");
        FileWriter fileWriter = new FileWriter(fileName, true);

        String formattedData = "";
        for (int i = 0; i < newUser.size(); i++) {
            if (i == newUser.size() - 1) {
                formattedData += newUser.get(i) + "\n";
            } else {
                formattedData = formattedData + newUser.get(i) + ",";
            }
        }
        fileWriter.write(formattedData);
        fileWriter.close();
    }

    /**
     * This method deletes a user from the database.
     *
     * @param userId - the id of the user to be deleted.
     */
    public static void deleteUserFromFile(int userId) {

    }

    // Temporarily - to be reviewed
    public static void createFile() throws IOException {

        File myFile = new File(FileNames.DATA.getDataFile("USERS"));
        Scanner myReader = new Scanner(myFile);

        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            String[] splitData = data.split(",");
            String username = splitData[2];
            System.out.println(username);
            if (username.equalsIgnoreCase("kyle.broflovski")) {
                System.out.println("Hi, Kyle!");
                break;
            }
        }
        myReader.close();

        String fileName = FileNames.DATA.getDataFile("USERS");
        FileWriter myWriter = new FileWriter(fileName, true);
//        myWriter.write("eric, cartman, eric.cartman\n");
//        myWriter.write("stan, marsh, stan.marsh\n");
//        myWriter.write("kyle, broflovski, kyle.broflovski\n");
//        myWriter.write("kenny, mccormick, kenny.mccormick\n");
        myWriter.close();

    }

    public static void main(String[] args) throws IOException {
        createFile();
    }
}
