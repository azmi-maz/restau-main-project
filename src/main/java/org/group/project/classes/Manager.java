package org.group.project.classes;

import javafx.scene.control.TextArea;
import org.group.project.classes.auxiliary.DataManager;
import org.group.project.exceptions.TextFileNotFoundException;

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

    // TODO
    public Manager(String firstName, String lastName, String username,
                   boolean hasAdminRight, int numOfHoursToWork,
                   int numOfTotalHoursWorked) {
        super(firstName, lastName, username, hasAdminRight,
                numOfHoursToWork, numOfTotalHoursWorked);
    }

    /**
     * This method adds a new staff member to the user list.
     *
     * @param userManagement - contains the list of active users.
     * @param newStaff       - a new staff member.
     * @return true if the new user is added successfully.
     */
    public boolean addNewStaffMember(
            UserManagement userManagement,
            Staff newStaff)
            throws TextFileNotFoundException {

        try {
            return userManagement.addNewStaffToDatabase(
                    newStaff
            );
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method remove a former staff member from the user list.
     *
     * @param userId - the user id of selected staff.
     * @return true if the removal was done successfully.
     */
    public boolean removeStaffMember(int userId)
            throws TextFileNotFoundException {
        try {
            return DataManager.deleteUniqueIdFromFile("USERS",
                    userId);
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method allows a manager to edit staff member details by specific
     * attribute.
     *
     * @param userManagement   - contains the list of active users.
     * @param userId           - the id of the selected user.
     * @param firstName        - the new first name of the selected user.
     * @param lastName         - the new last name of the selected user.
     * @param username         - the new username of the selected user.
     * @param hoursLeft        - the updated hours left of the selected user.
     * @param totalHoursWorked - the updated total hours worked of the selected user.
     * @param position         - the new position of the selected user.
     * @return true if all the new fields are updated successfully.
     * @throws TextFileNotFoundException
     */
    public boolean editStaffMemberDetails(
            UserManagement userManagement,
            String userId,
            String firstName,
            String lastName,
            String username,
            String hoursLeft,
            String totalHoursWorked,
            String position)
            throws TextFileNotFoundException {

        try {

            return userManagement.editExistingUserProfile(
                    userId,
                    firstName,
                    lastName,
                    username,
                    hoursLeft,
                    totalHoursWorked,
                    position
            );

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method allows the manager to view reports by type.
     *
     * @param currentUser      - the current manager who generates reports.
     * @param chosenReportType - the specific report type selected.
     * @param reportTextArea   - the textarea to display report.
     * @throws TextFileNotFoundException
     */
    public void viewReports(
            User currentUser,
            String chosenReportType,
            TextArea reportTextArea)
            throws TextFileNotFoundException {

        try {
            ReportManager reportManager = new ReportManager();

            switch (chosenReportType) {
                case "Most popular item":
                    KitchenReport mostPopularItem = reportManager
                            .getMostPopularItemReport(
                                    currentUser
                            );
                    reportTextArea.setText(
                            mostPopularItem.generateReport()
                    );
                    reportManager.addReportToDatabase(
                            mostPopularItem
                    );
                    break;

                case "Busiest periods":
                    FloorReport busiestPeriod = reportManager
                            .getBusiestPeriodReport(
                                    currentUser
                            );
                    reportTextArea.setText(
                            busiestPeriod.generateReport()
                    );
                    reportManager.addReportToDatabase(
                            busiestPeriod
                    );
                    break;

                case "Most active customer":
                    Report mostActiveCustomer = reportManager
                            .getMostActiveCustomerReport(
                                    currentUser
                            );
                    reportTextArea.setText(
                            mostActiveCustomer.generateReport()
                    );
                    reportManager.addReportToDatabase(
                            mostActiveCustomer
                    );
                    break;

                case "Staff worked hours":
                    UserReport staffReport = reportManager
                            .getStaffWorkedHoursReport(
                                    currentUser
                            );
                    reportTextArea.setText(
                            staffReport.generateReport()
                    );
                    reportManager.addReportToDatabase(
                            staffReport
                    );
                    break;

                case "Outstanding orders":
                    KitchenReport outstandingOrders = reportManager
                            .getOutstandingOrdersReport(
                                    currentUser
                            );
                    reportTextArea.setText(
                            outstandingOrders.generateReport()
                    );
                    reportManager.addReportToDatabase(
                            outstandingOrders
                    );
                    break;

                default:
                    reportTextArea.setText("No report chosen.");
                    break;
            }

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // TODO
    public void deleteReportFromList(
            int reportId
    ) throws TextFileNotFoundException {
        try {
            DataManager.deleteUniqueIdFromFile(
                    "REPORTS",
                    reportId
            );
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
