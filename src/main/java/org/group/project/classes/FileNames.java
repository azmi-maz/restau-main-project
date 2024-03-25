package org.group.project.classes;

/**
 * This enum class is to get filenames path in the project.
 *
 * @author azmi_maz
 */
public enum FileNames {
    DATA("src/main/java/org/group/project/textfiles/"),
    USERS("username-data.txt");

    private final String fileName;

    FileNames(String file) {
        this.fileName = file;
    }

    private String getValue(String name) {
        for (FileNames each : FileNames.values()) {
            if (each.name().equals(name)) {
                return each.fileName;
            }
        }
        return "";
    }

    public String getDataFile(String specificFile) {
        return DATA.fileName.concat(getValue(specificFile));
    }
}
