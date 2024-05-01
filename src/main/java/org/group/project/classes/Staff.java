package org.group.project.classes;

/**
 * This class handles data and methods for all staff subclasses.
 *
 * @author azmi_maz
 */
public class Staff extends User {
    private static final int MAX_STAFF_HOURS = 44;
    private static final int MIN_STAFF_HOURS = 0;
    protected int numOfHoursToWork;
    protected int numOfTotalHoursWorked;
    protected boolean hasAdminRight;


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
        this.numOfHoursToWork = MAX_STAFF_HOURS;
        this.hasAdminRight = hasAdminRight;
    }

    /**
     * This constructor creates a staff with updated data from database.
     *
     * @param firstName             - the first name of the staff.
     * @param lastName              - the last name of the staff.
     * @param username              - the username chosen by the staff.
     * @param hasAdminRight         - the right to access the system as an
     *                              admin.
     * @param numOfHoursToWork      - the number of work hours remaining.
     * @param numOfTotalHoursWorked - the total number of hours worked.
     */
    public Staff(String firstName, String lastName, String username,
                 boolean hasAdminRight, int numOfHoursToWork,
                 int numOfTotalHoursWorked) {
        super(firstName, lastName, username);
        this.hasAdminRight = hasAdminRight;
        this.numOfHoursToWork = numOfHoursToWork;
        this.numOfTotalHoursWorked = numOfTotalHoursWorked;
    }

    /**
     * This constructor creates a staff with basic info.
     *
     * @param firstName - the first name of the staff.
     * @param lastName  - the last name of the staff.
     * @param username  - the username chosen by the staff.
     */
    public Staff(
            String firstName, String lastName, String username
    ) {
        super(firstName, lastName, username);
        hasAdminRight = false;
        numOfHoursToWork = MAX_STAFF_HOURS;
        numOfTotalHoursWorked = MIN_STAFF_HOURS;
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

    /**
     * Getter method to get the user admin right.
     *
     * @return true if user has admin right.
     */
    public boolean getHasAdminRight() {
        return hasAdminRight;
    }
}
