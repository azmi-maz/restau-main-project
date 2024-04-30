package org.group.project.classes.auxiliary;

/**
 * This enum class is to get filenames path in the project.
 *
 * @author azmi_maz
 */
public enum FileNames {
    DATA("src/main/java/org/group/project/textfiles/"),
    USERS("user-data.txt"),
    ACTIVE_USER("active-user.txt"),
    TABLES("tables.txt"),
    BOOKINGS("booking-data.txt"),
    MENU("menu.txt"),
    PRESET_ITEMS("new-menu-item.txt"),
    NOTIFICATION("notification-data.txt"),
    ORDERS("order-data.txt"),
    REPORTS("report-data.txt");

    private final String fileName;

    /**
     * The constructor to create the FileNames enum attributes.
     *
     * @param file - the name of the text files.
     */
    FileNames(String file) {
        this.fileName = file;
    }

    /**
     * This method get the file name by the enum key.
     *
     * @param name - the enum key.
     * @return - the file name.
     */
    private String getValue(String name) {
        for (FileNames file : FileNames.values()) {
            if (file.name().equals(name)) {
                return file.fileName;
            }
        }
        return "";
    }

    /**
     * This method gets the data based on file name.
     *
     * @param specificFile - the file name.
     * @return - the data from that file.
     */
    public String getDataFile(String specificFile) {
        return DATA.fileName.concat(getValue(specificFile));
    }
}
