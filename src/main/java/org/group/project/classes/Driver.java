package org.group.project.classes;

import org.group.project.exceptions.TextFileNotFoundException;

/**
 * This class represents driver role in the restaurant.
 *
 * @author azmi_maz
 */
public class Driver extends Staff {

    private boolean isAvailable;
    private int maxDeliveries;

    /**
     * The constructor to create new driver
     *
     * @param firstName - the first name of the staff.
     * @param lastName  - the last name of the staff.
     * @param username  - the username chosen by the staff.
     */
    public Driver(String firstName,
                  String lastName,
                  String username) {
        super(firstName, lastName, username, false);
        this.isAvailable = true;
        this.maxDeliveries = 5;
    }

    /**
     * This constructor creates a driver from the database.
     *
     * @param firstName             - the first name of the staff.
     * @param lastName              - the last name of the staff.
     * @param username              - the username chosen by the staff.
     * @param hasAdminRight         - the right for admin access.
     * @param numOfHoursToWork      - the number of work hours remaining.
     * @param numOfTotalHoursWorked - the total hours worked.
     * @param isAvailable           - the driver's availability.
     * @param maxDeliveries         - the max number of active deliveries.
     */
    public Driver(String firstName, String lastName,
                  String username, boolean hasAdminRight,
                  int numOfHoursToWork, int numOfTotalHoursWorked,
                  boolean isAvailable, int maxDeliveries) {
        super(firstName, lastName, username, hasAdminRight,
                numOfHoursToWork, numOfTotalHoursWorked);
        this.isAvailable = isAvailable;
        this.maxDeliveries = maxDeliveries;
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

    /**
     * This method confirms a delivery order was delivered successfully.
     *
     * @param order - the current order being delivered.
     * @return true if the confirmation update was made successfully.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public boolean confirmDeliveryOrder(DeliveryOrder order)
            throws TextFileNotFoundException {
        try {
            return order.confirmDeliveryOrder();
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This display the driver full name when printed out as string.
     *
     * @return the driver full name.
     */
    @Override
    public String toString() {
        return super.getDataForListDisplay();
    }
}
