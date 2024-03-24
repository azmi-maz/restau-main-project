package org.group.project.classes;

/**
 * This class keeps track of a seat being available or not.
 *
 * @author azmi_maz
 */
public class Seat {
    private boolean isAvailable;

    /**
     * This constructor sets up a seat for a table.
     */
    public Seat() {
        this.isAvailable = true;
    }

    /**
     * This method sets a new status for a seat.
     *
     * @param status - the new status.
     * @return true if set status was updated successfully.
     */
    public boolean setAvailability(boolean status) {
        isAvailable = status;
        return true;
    }

    /**
     * This getter method checks if a seat is available or not.
     *
     * @return the boolean value of the seat availability.
     */
    public boolean isAvailable() {
        return isAvailable;
    }
}
