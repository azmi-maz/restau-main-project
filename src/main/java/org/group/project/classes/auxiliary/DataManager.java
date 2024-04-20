package org.group.project.classes.auxiliary;

import org.group.project.classes.auxiliary.DataFileStructure;
import org.group.project.classes.auxiliary.FileNames;

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
     * This method gets the bookings file.
     *
     * @return the bookings file.
     */
    public static File getBookingsFile() {
        return new File(FileNames.DATA.getDataFile("BOOKINGS"));
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
     * This method appends a new line of data into a file.
     * @param fileType - specify the data file to append the new line.
     * @param newLine - the new line of data.
     * @throws IOException - if the fileType is non-existent.
     */
    public static void appendDataToFile(
            String fileType,
            List<String> newLine) throws IOException {

        String getFile = FileNames.DATA.getDataFile(fileType);
        FileWriter fileWriter = new FileWriter(getFile, true);

        String formattedData = formatArrayToString(newLine);
        fileWriter.write(formattedData + System.lineSeparator());
        fileWriter.close();
    }

    /**
     * This method is to format array to string separated with commas.
     * @param line - the array to be formatted.
     * @return - the string format of the input array.
     * @param <T> - either Array or List/ArrayList.
     */
    public static <T> String formatArrayToString(T line) {
        String formattedLine = "";
        if (line instanceof List) {
            List<T> lineArray = (List<T>) line;
            for (int i = 0; i < lineArray.size(); i++) {
                if (i == lineArray.size() - 1) {
                    formattedLine += lineArray.get(i);
                } else {
                    formattedLine = formattedLine + lineArray.get(i) + ",";
                }
            }
        } else if (line instanceof String[]) {
            String[] lineArray = (String[]) line;
            for (int i = 0; i < lineArray.length; i++) {
                if (i == lineArray.length - 1) {
                    formattedLine += lineArray[i];
                } else {
                    formattedLine = formattedLine + lineArray[i] + ",";
                }
            }
        }
        return formattedLine;
    }

    public static <T> String formatLongArrayToOneColumnString(T line) {
        String formattedLine = "";
        if (line instanceof List) {
            List<T> lineArray = (List<T>) line;
            for (int i = 0; i < lineArray.size(); i++) {
                if (i == lineArray.size() - 1) {
                    formattedLine += lineArray.get(i);
                } else {
                    formattedLine = formattedLine + lineArray.get(i) + ";";
                }
            }
        } else if (line instanceof String[]) {
            String[] lineArray = (String[]) line;
            for (int i = 0; i < lineArray.length; i++) {
                if (i == lineArray.length - 1) {
                    formattedLine += lineArray[i];
                } else {
                    formattedLine = formattedLine + lineArray[i] + ";";
                }
            }
        }
        return formattedLine;
    }


    /**
     * This method deletes a data in a file based on unique id.
     * @param fileType - specify the data file to append the new line.
     * @param uniqueId - the unique id of the data to be deleted.
     * @param <T> - either Integer or String type of id.
     * @throws IOException - if the fileType is non-existent.
     */
    public static <T> void deleteUniqueIdFromFile(String fileType,
                                                  T uniqueId) throws IOException {
        File inputFile = new File(FileNames.DATA.getDataFile(fileType));
        File tempFile = new File("myTempFile.txt");

        FileWriter fileWriter = new FileWriter(tempFile, true);

        Scanner fileReader = new Scanner(inputFile);
        while (fileReader.hasNextLine()) {
            String data = fileReader.nextLine();
            String[] dataDetails = data.split(",");
            int uniqueIdIndex = DataFileStructure.getIndexColOfUniqueId(
                    fileType);
            if (uniqueId instanceof Integer) {
                int getUniqueId = Integer.parseInt(dataDetails[uniqueIdIndex]);
                int deleteUniqueId = Integer.parseInt(String.valueOf(uniqueId));
                if (getUniqueId != deleteUniqueId) {
                    fileWriter.write(data + System.lineSeparator());
                } else {
                    // TODO to return this to UI?
//                    System.out.println("Deleted: " + data);
                }
            } else if (uniqueId instanceof String) {
                String getUniqueId = dataDetails[uniqueIdIndex];
                String deleteUniqueId = String.valueOf(uniqueId);
                if (!getUniqueId.equalsIgnoreCase(deleteUniqueId)) {
                    fileWriter.write(data + System.lineSeparator());
                } else {
//                    System.out.println("Deleted: " + data);
                }
            }

        }

        fileReader.close();
        fileWriter.close();
        tempFile.renameTo(inputFile);

    }

    /**
     * This method replace a column data in a file based on a unique id.
     * @param fileType - specify the data file to append the new line.
     * @param uniqueId - the unique id of the data to be deleted.
     * @param columnName - the attribute/column name to edit.
     * @param newData - the new value/data for the specified column.
     * @param <T> - either Integer or String of id.
     * @throws IOException - if the fileType is non-existent.
     */
    public static <T> void editColumnDataByUniqueId(
            String fileType,
            T uniqueId,
            String columnName,
            String newData
    ) throws IOException {
        File inputFile = new File(FileNames.DATA.getDataFile(fileType));
        File tempFile = new File("myTempFile.txt");

        FileWriter fileWriter = new FileWriter(tempFile, true);
        Scanner fileReader = new Scanner(inputFile);

        while (fileReader.hasNextLine()) {
            String data = fileReader.nextLine();
            String[] dataDetails = data.split(",");
            int columnIndexToEdit =
                    DataFileStructure.getIndexByColName(fileType, columnName);
            int uniqueIdIndex = DataFileStructure.getIndexColOfUniqueId(
                    fileType);
            if (uniqueId instanceof Integer) {
                int getUniqueId = Integer.parseInt(dataDetails[uniqueIdIndex]);
                int uniqueIdToEdit = Integer.parseInt(String.valueOf(uniqueId));
                if (getUniqueId != uniqueIdToEdit) {
                    fileWriter.write(data + System.lineSeparator());
                } else {
                    dataDetails[columnIndexToEdit] = newData;
                    String editedDate = formatArrayToString(dataDetails);
                    fileWriter.write(editedDate + System.lineSeparator());
                }
            } else if (uniqueId instanceof String) {
                String getUniqueId = dataDetails[uniqueIdIndex];
                String uniqueIdToEdit = String.valueOf(uniqueId);
                if (!getUniqueId.equalsIgnoreCase(uniqueIdToEdit)) {
                    fileWriter.write(data + System.lineSeparator());
                } else {
                    dataDetails[columnIndexToEdit] = newData;
                    String editedDate = formatArrayToString(dataDetails);
                    fileWriter.write(editedDate + System.lineSeparator());
                }
            }
        }

        fileReader.close();
        fileWriter.close();
        tempFile.renameTo(inputFile);
    }

    // TODO comment
    public static void clearFileData(String fileType) throws IOException {
        File inputFile = new File(FileNames.DATA.getDataFile(fileType));
        File tempFile = new File("myTempFile.txt");
        FileWriter fileWriter = new FileWriter(tempFile, true);
        fileWriter.write("");
        fileWriter.close();
        tempFile.renameTo(inputFile);
    }
}
