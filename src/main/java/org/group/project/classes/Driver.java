package org.group.project.classes;

import org.group.project.exceptions.TextFileNotFoundException;

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

    // TODO
    public Driver(int driverId, String firstName, String lastName,
                  String username, boolean hasAdminRight,
                  int numOfHoursToWork, int numOfTotalHoursWorked,
                  boolean isAvailable, int maxDeliveries) {
        super(firstName, lastName, username, hasAdminRight,
                numOfHoursToWork, numOfTotalHoursWorked);
        this.driverId = driverId;
        this.isAvailable = isAvailable;
        this.maxDeliveries = maxDeliveries;
    }

    // TODO
    public Driver(String firstName, String lastName, String username) {
        super(firstName, lastName, username, false);
        this.isAvailable = true;
        this.maxDeliveries = 5;
    }

    /**
     * Getter method to get driver's free to do a round of deliveries.
     *
     * @return true if driver is available.
     */
    public boolean getIsAvailable() {
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

    // TODO
    public boolean confirmDeliveryOrder(DeliveryOrder order)
            throws TextFileNotFoundException {
        try {
            return order.confirmDeliveryOrder();
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // TODO comment
    @Override
    public String toString() {
        return super.getDataForListDisplay();
    }
}
