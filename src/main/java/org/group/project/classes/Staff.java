package org.group.project.classes;

/**
 * This class handles data and methods for all staff subclasses.
 *
 * @author azmi_maz
 */
public class Staff extends User {
    protected int numOfHoursToWork;
    protected int numOfTotalHoursWorked;
    protected boolean hasAdminRight;

    // TODO MAX_STAFF_HOURS = 44, also in ManagerStaffManagementDetailsController
    // TODO MIN_STAFF_HOURS = 0, also in ManagerStaffManagementDetailsController

    /**
     * The constructor to create new staff and provide admin rights for
     * specified staff.
     *
     * @param firstName     - the first name of the staff.
     * @param lastName      - the last name of the staff.
     * @param username      - the username chosen by the staff.
     * @param hasAdminRight - the right to access the system as an admin.
     */
    public Staff(String firstName, String lastName, String username,
                 boolean hasAdminRight) {
        super(firstName, lastName, username);
        this.numOfHoursToWork = 44;
        this.hasAdminRight = hasAdminRight;
    }

    // TODO comment
    public Staff(String firstName, String lastName, String username,
                 boolean hasAdminRight, int numOfHoursToWork,
                 int numOfTotalHoursWorked) {
        super(firstName, lastName, username);
        this.hasAdminRight = hasAdminRight;
        this.numOfHoursToWork = numOfHoursToWork;
        this.numOfTotalHoursWorked = numOfTotalHoursWorked;
    }

    // TODO
    public Staff(
            String firstName, String lastName, String username
    ) {
        super(firstName, lastName, username);
        hasAdminRight = false;
        numOfHoursToWork = 44;
        numOfTotalHoursWorked = 0;
    }

    /**
     * This method sets the number of hours to work by a staff.
     *
     * @param hours - the hours that a staff need to spend working.
     * @return true if the update was made successfully.
     */
    public boolean setNumOfHoursToWork(int hours) {
        this.numOfHoursToWork = hours;
        return true;
    }

    /**
     * This method sets the number of total hours worked by a staff.
     *
     * @param hours - the hours that was spent working.
     * @return true if the update was made successfully.
     */
    public boolean setNumOfTotalHoursWorked(int hours) {
        this.numOfTotalHoursWorked = hours;
        return true;
    }

    /**
     * Getter method to get the number of hours remaining for a staff to work.
     *
     * @return the number of hours to work.
     */
    public int getNumOfHoursToWork() {
        return numOfHoursToWork;
    }

    /**
     * Getter method to get the number of hours worked by a staff.
     *
     * @return the number of hours worked.
     */
    public int getNumOfTotalHoursWorked() {
        return numOfTotalHoursWorked;
    }

    // TODO comment
    public boolean getHasAdminRight() {
        return hasAdminRight;
    }
}
