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
    USERS("userId", Arrays.asList("userId", "firstName", "lastName",
            "username", "userType", "address", "numOfHoursToWork",
            "numOfTotalHoursWorked", "hasAdminRight", "isAvailable",
            "maxDeliveries").toArray(new String[0])),
    TABLES("tableName",
            Arrays.asList("tableName", "numOfSeats").toArray(new String[0])),
    BOOKINGS("bookingId", Arrays.asList("bookingId", "userId",
            "bookingDate", "bookingTime", "numOfGuests", "bookingLength",
            "tablePreference", "bookingStatus").toArray(new String[0])),
    MENU("itemName",
            Arrays.asList("itemName", "itemType", "isDailySpecial").toArray(new String[0])),
    NOTIFICATION("notificationId",
            Arrays.asList("notificationId", "userId", "notificationDate",
                    "notificationTime", "notificationType", "readStatus",
                    "messageBody").toArray(new String[0])),
    ORDERS("orderId",
            Arrays.asList("orderId", "userId", "orderDate", "orderTime",
                    "orderType", "orderStatus", "estimatedPickupTime",
                    "assignedDriver", "deliveryTime", "orderedLists"
            ).toArray(new String[0])),
    REPORTS("reportId",
            Arrays.asList("reportId", "reportType", "generatedBy",
                    "generatedOnDate", "generatedOnTime", "reportData"
            ).toArray(new String[0]));

    private final String uniqueId;
    private final List<String> structure = new ArrayList<>();

    DataFileStructure(String uniqueId, String[] structure) {
        this.uniqueId = uniqueId;
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
     *
     * @param fileName - the file that contains the data.
     * @param colName  - the column header in that database.
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

    /**
     * This method gets the uniqueId column of a database.
     *
     * @param fileName - the name of the database.
     * @return the index column that contains the uniqueId.
     */
    public static int getIndexColOfUniqueId(String fileName) {
        for (DataFileStructure file : DataFileStructure.values()) {
            if (file.name().equalsIgnoreCase(fileName)) {
                return file.structure.indexOf(file.uniqueId);
            }
        }
        return -1;
    }

}
