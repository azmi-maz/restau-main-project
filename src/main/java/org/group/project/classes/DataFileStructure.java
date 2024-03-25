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

    DataFileStructure(List<String> structure) {
        List<String> fileStructure = new ArrayList<>();
        fileStructure.addAll(structure);
    }
}
