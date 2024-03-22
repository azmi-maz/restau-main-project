package org.group.project.classes;

public class Driver extends Staff {


    /**
     * The constructor to create new driver
     *
     * @param firstName     - the first name of the staff.
     * @param lastName      - the last name of the staff.
     * @param username      - the username chosen by the staff.
     */
    public Driver(String firstName, String lastName, String username) {
        super(firstName, lastName, username, false);
    }
}
