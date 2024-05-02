package org.group.project.classes.auxiliary;

import org.group.project.exceptions.ClearFileFailedException;
import org.group.project.exceptions.TextFileNotFoundException;

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

    private static final String TEMP_FILE = "myTempFile.txt";

    /**
     * This method gets all the data from a file.
     *
     * @param fileName - the name of the file.
     * @return the list of strings that represent rows of data.
     * @throws TextFileNotFoundException - if file does not exist.
     */
    public static List<String> allDataFromFile(
            String fileName) throws TextFileNotFoundException {
        try {
            File file = new File(FileNames.DATA.getDataFile(fileName));
            List<String> resultArray = new ArrayList<>();
            Scanner fileReader = new Scanner(file);
            while (fileReader.hasNextLine()) {
                String data = fileReader.nextLine();
                resultArray.add(data);
            }
            fileReader.close();
            return resultArray;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new TextFileNotFoundException(fileName);
        }
    }

    /**
     * This method appends a new line of data into a file.
     *
     * @param fileType - specify the data file to append the new line.
     * @param newLine  - the new line of data.
     * @throws TextFileNotFoundException - if the fileType is non-existent.
     */
    public static boolean appendDataToFile(
            String fileType,
            List<String> newLine) throws TextFileNotFoundException {

        try {
            String getFile = FileNames.DATA.getDataFile(fileType);
            FileWriter fileWriter = new FileWriter(getFile, true);

            String formattedData = formatArrayToString(newLine);
            fileWriter.write(formattedData + System.lineSeparator());
            fileWriter.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            throw new TextFileNotFoundException(fileType);
        }
    }

    /**
     * This method is to format array to string separated with commas.
     *
     * @param line - the array to be formatted.
     * @param <T>  - either Array or List/ArrayList.
     * @return - the string format of the input array.
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

    /**
     * This method formats array type to be a string type for database.
     *
     * @param line - the unformatted array.
     * @param <T>  - the array type should be List or array only.
     * @return - String format of the array.
     */
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
     *
     * @param fileType - specify the data file to append the new line.
     * @param uniqueId - the unique id of the data to be deleted.
     * @param <T>      - either Integer or String type of id.
     * @throws TextFileNotFoundException - if the fileType is non-existent.
     */
    public static <T> boolean deleteUniqueIdFromFile(
            String fileType,
            T uniqueId) throws TextFileNotFoundException {

        try {

            File inputFile = new File(FileNames.DATA.getDataFile(fileType));
            File tempFile = new File(TEMP_FILE);

            FileWriter fileWriter = new FileWriter(tempFile, true);

            Scanner fileReader = new Scanner(inputFile);
            while (fileReader.hasNextLine()) {
                String data = fileReader.nextLine();
                String[] dataDetails = data.split(",");
                int uniqueIdIndex = DataFileStructure
                        .getIndexColOfUniqueId(fileType);
                if (uniqueId instanceof Integer) {
                    int getUniqueId = Integer.parseInt(
                            dataDetails[uniqueIdIndex]);
                    int deleteUniqueId = Integer.parseInt(
                            String.valueOf(uniqueId));
                    if (getUniqueId != deleteUniqueId) {
                        fileWriter.write(data
                                + System.lineSeparator());
                    }
                } else if (uniqueId instanceof String) {
                    String getUniqueId = dataDetails[uniqueIdIndex];
                    String deleteUniqueId = String.valueOf(uniqueId);
                    if (!getUniqueId.equalsIgnoreCase(deleteUniqueId)) {
                        fileWriter.write(data + System.lineSeparator());
                    }
                }

            }

            fileReader.close();
            fileWriter.close();
            return tempFile.renameTo(inputFile);

        } catch (IOException e) {
            e.printStackTrace();
            throw new TextFileNotFoundException(fileType);
        }
    }

    /**
     * This method replace a column data in a file based on a unique id.
     *
     * @param fileType   - specify the data file to append the new line.
     * @param uniqueId   - the unique id of the data to be deleted.
     * @param columnName - the attribute/column name to edit.
     * @param newData    - the new value/data for the specified column.
     * @param <T>        - either Integer or String of id.
     * @throws TextFileNotFoundException - if the fileType is non-existent.
     */
    public static <T> boolean editColumnDataByUniqueId(
            String fileType,
            T uniqueId,
            String columnName,
            String newData
    ) throws TextFileNotFoundException {

        try {

            File inputFile = new File(FileNames.DATA.getDataFile(fileType));
            File tempFile = new File(TEMP_FILE);

            FileWriter fileWriter = new FileWriter(tempFile, true);
            Scanner fileReader = new Scanner(inputFile);

            while (fileReader.hasNextLine()) {
                String data = fileReader.nextLine();
                String[] dataDetails = data.split(",");
                int columnIndexToEdit =
                        DataFileStructure.getIndexByColName(
                                fileType, columnName);
                int uniqueIdIndex = DataFileStructure
                        .getIndexColOfUniqueId(fileType);
                if (uniqueId instanceof Integer) {
                    int getUniqueId = Integer.parseInt(
                            dataDetails[uniqueIdIndex]);
                    int uniqueIdToEdit = Integer.parseInt(
                            String.valueOf(uniqueId));
                    if (getUniqueId != uniqueIdToEdit) {
                        fileWriter.write(
                                data + System.lineSeparator());
                    } else {
                        dataDetails[columnIndexToEdit] = newData;
                        String editedDate = formatArrayToString(dataDetails);
                        fileWriter.write(editedDate
                                + System.lineSeparator());
                    }
                } else if (uniqueId instanceof String) {
                    String getUniqueId = dataDetails[uniqueIdIndex];
                    String uniqueIdToEdit = String.valueOf(uniqueId);
                    if (!getUniqueId.equalsIgnoreCase(uniqueIdToEdit)) {
                        fileWriter.write(data + System.lineSeparator());
                    } else {
                        dataDetails[columnIndexToEdit] = newData;
                        String editedDate = formatArrayToString(dataDetails);
                        fileWriter.write(editedDate
                                + System.lineSeparator());
                    }
                }
            }

            fileReader.close();
            fileWriter.close();
            return tempFile.renameTo(inputFile);

        } catch (IOException e) {
            e.printStackTrace();
            throw new TextFileNotFoundException(fileType);
        }
    }

    /**
     * This deletes all the data in a text file.
     *
     * @param fileType - the name of the text file or filetype.
     * @throws ClearFileFailedException - The specific file is non-existent.
     */
    public static void clearFileData(
            String fileType) throws ClearFileFailedException {

        try {

            File inputFile = new File(FileNames.DATA.getDataFile(fileType));
            File tempFile = new File(TEMP_FILE);
            FileWriter fileWriter = new FileWriter(tempFile, true);
            fileWriter.write("");
            fileWriter.close();
            tempFile.renameTo(inputFile);

        } catch (IOException e) {
            e.printStackTrace();
            throw new ClearFileFailedException();
        }
    }
}
