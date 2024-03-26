package org.group.project.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This enum class helps keep track of the data file structure used in this
 * program.
 *
 * @author azmi_maz
 */
public enum DataFileStructure {
    USERS(Arrays.asList("firstName", "lastName", "username",
            "isCustomer", "customerId", "address").toArray(new String[0]));

    private final List<String> structure = new ArrayList<>();

    DataFileStructure(String[] structure) {
        this.structure.addAll(Arrays.asList(structure));
    }

    /**
     * Getter method to get the list of strings by file structure name.
     *
     * @param name - the name of the file structure.
     * @return the list of strings based on the name.
     */
    public static List<String> getValues(String name) {
        for (DataFileStructure file : DataFileStructure.values()) {
            if (file.name().equalsIgnoreCase(name)) {
                return file.structure;
            }
        }
        return null;
    }

    /**
     * This method helps to get the index of a column in a database.
     * @param fileName - the file that contains the data.
     * @param colName - the column header in that database.
     * @return column index of the column header.
     */
    public static int getIndexByColName(String fileName, String colName) {
        for (DataFileStructure file : DataFileStructure.values()) {
            if (file.name().equalsIgnoreCase(fileName)) {
                return file.structure.indexOf(colName);
            }
        }
        return -1;
    }
}
