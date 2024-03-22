package org.group.project.classes;

/**
 * This class represents the manager role in the restaurant.
 *
 * @author azmi_maz
 */
public class Manager extends Staff {

    /**
     * The constructor to create a manager.
     *
     * @param firstName - the first name of the staff.
     * @param lastName  - the last name of the staff.
     * @param username  - the username chosen by the staff.
     */
    public Manager(String firstName, String lastName, String username) {
        super(firstName, lastName, username, true);
    }

    /**
     * This method adds a new staff member to the user list.
     *
     * @param userList - the list of active users.
     * @param newStaff - a new staff member.
     * @return true if the new user is added successfully.
     */
    public boolean addNewStaffMember(UserManagement userList, Staff newStaff) {
        userList.addNewUser(newStaff);
        // check if this is correct?
        return true;
    }

    /**
     * This method remove a former staff member from the user list.
     *
     * @param userList    - the list of active users.
     * @param formerStaff - the former staff.
     * @return true if the removal was done successfully.
     */
    public boolean removeStaffMember(UserManagement userList,
                                     Staff formerStaff) {
        userList.removeUser(formerStaff);
        return true;
    }

    /**
     * This method allows a manager to edit staff member details by specific
     * attribute.
     *
     * @param userList      - the list of active users.
     * @param searchStaff   - the selected staff.
     * @param attributeName - the attribute name to be edited.
     * @param newValue      - the new value of the attribute being edited.
     * @return true if the edit was done successfully.
     */
    public boolean editStaffMemberDetails(UserManagement userList,
                                          Staff searchStaff,
                                          String attributeName,
                                          Object newValue) {
        userList.editUserDetails(searchStaff, attributeName, newValue);
        return true;
    }

    /**
     * This method allows the manager to view reports by type.
     *
     * @param reportType - the specific type of report.
     */
    public void viewReports(Report reportType) {
        // to code
    }
}
