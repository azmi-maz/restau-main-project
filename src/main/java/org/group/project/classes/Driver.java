package org.group.project.classes;

/**
 * This class represents driver role in the restaurant.
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
     * @return the number of max deliveries.
     */
    public int getMaxDeliveries() {
        return maxDeliveries;
    }

    /**
     * Setter method to set a new maximum number of deliveries.
     * @param maxDeliveries - the number of max deliveries.
     */
    public void setMaxDeliveries(int maxDeliveries) {
        this.maxDeliveries = maxDeliveries;
    }
}
