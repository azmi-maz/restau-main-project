package org.group.project.classes;

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
    NOTIFICATION("notification-data.txt"),
    ORDERS("order-data.txt"),
    REPORTS("report-data.txt");

    private final String fileName;

    FileNames(String file) {
        this.fileName = file;
    }

    private String getValue(String name) {
        for (FileNames file : FileNames.values()) {
            if (file.name().equals(name)) {
                return file.fileName;
            }
        }
        return "";
    }

    public String getDataFile(String specificFile) {
        return DATA.fileName.concat(getValue(specificFile));
    }
}
