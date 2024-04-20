package org.group.project.classes;

import javafx.scene.control.ComboBox;
import org.group.project.classes.auxiliary.DataFileStructure;
import org.group.project.classes.auxiliary.DataManager;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * This class represents driver role in the restaurant.
 *
 * @author azmi_maz
 */
public class Driver extends Staff {

    private int driverId;
    private boolean isAvailable;
    private int maxDeliveries;

    /**
     * The constructor to create new driver
     *
     * @param driverId  - the unique id.
     * @param firstName - the first name of the staff.
     * @param lastName  - the last name of the staff.
     * @param username  - the username chosen by the staff.
     */
    public Driver(int driverId, String firstName, String lastName,
                  String username) {
        super(firstName, lastName, username, false);
        this.driverId = driverId;
        this.isAvailable = true;
        this.maxDeliveries = 5;
    }

    /**
     * Getter method to get driver's free to do a round of deliveries.
     *
     * @return true if driver is available.
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * This method sets the driver to be available or not for deliveries.
     *
     * @param newStatus - true for available, false for out for deliveries.
     */
    public void setAvailability(boolean newStatus) {
        isAvailable = newStatus;
    }

    /**
     * Getter method to get the maximum deliveries a driver can handle.
     *
     * @return the number of max deliveries.
     */
    public int getMaxDeliveries() {
        return maxDeliveries;
    }

    /**
     * Setter method to set a new maximum number of deliveries.
     *
     * @param maxDeliveries - the number of max deliveries.
     */
    public void setMaxDeliveries(int maxDeliveries) {
        this.maxDeliveries = maxDeliveries;
    }

    // TODO comment
    public static void getUpdatedDriverList(
            ComboBox<Driver> driverComboBox) throws FileNotFoundException {
        List<String> driverList = DataManager.allDataFromFile("USERS");
        for (String driver : driverList) {
            List<String> driverDetails = List.of(driver.split(","));
            String userType = driverDetails
                    .get(DataFileStructure
                            .getIndexByColName("USERS", "userType"));
            if (userType.equalsIgnoreCase("driver")) {
                driverComboBox.getItems().add(new Driver(
                        Integer.parseInt(driverDetails
                                .get(DataFileStructure
                                        .getIndexColOfUniqueId("USERS"))),
                        driverDetails.get(DataFileStructure
                                .getIndexByColName("USERS", "firstName")),
                        driverDetails.get(DataFileStructure
                                .getIndexByColName("USERS", "lastName")),
                        driverDetails.get(DataFileStructure
                                .getIndexByColName("USERS", "username"))
                ));
            }
        }
    }

    // TODO comment
    @Override
    public String toString() {
        return super.getDataForListDisplay();
    }
}
