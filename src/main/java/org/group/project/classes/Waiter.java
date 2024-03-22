package org.group.project.classes;

/**
 * This class represents waiter role in the restaurant.
 */
public class Waiter extends Staff {

    /**
     * The constructor to create new staff and provide admin rights for
     * specified staff.
     *
     * @param firstName     - the first name of the staff.
     * @param lastName      - the last name of the staff.
     * @param username      - the username chosen by the staff.
     * @param hasAdminRight - the right to access the system as an admin.
     */
    public Waiter(String firstName, String lastName, String username,
                  boolean hasAdminRight) {
        super(firstName, lastName, username, hasAdminRight);
    }
}
