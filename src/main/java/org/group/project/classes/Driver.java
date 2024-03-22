package org.group.project.classes;

public class Driver extends Staff {
    private boolean isAvailable;

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
}
