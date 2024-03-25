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
    USER(new ArrayList<>(Arrays.asList("firstName", "lastName", "username",
            "isCustomer", "customerId")));

    private List<String> structure;

    DataFileStructure(List<String> structure) {
        List<String> fileStructure = new ArrayList<>();
        fileStructure.addAll(structure);
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
