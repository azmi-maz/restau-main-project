package org.group.project.classes;

import org.group.project.exceptions.TextFileNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class reports on staff who worked the most hours.
 *
 * @author azmi_maz
 */
public class UserReport extends Report {

    /**
     * This constructor creates a new report with timestamp.
     *
     * @param reportType - the type of the report.
     * @param user       - the user who generated the report.
     */
    public UserReport(String reportType,
                      User user)
            throws TextFileNotFoundException {

        super(reportType, user);
        super.setReportData(getStaffWhoWorkedTheMostHoursReport());

    }

    /**
     * This method generates the staff who worked the most hours report.
     *
     * @return the report for staff who worked the most.
     */
    public String getStaffWhoWorkedTheMostHoursReport()
            throws TextFileNotFoundException {
        try {
            return prepareReportData();
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method prepares the user report data.
     *
     * @return the main message of the report.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public String prepareReportData()
            throws TextFileNotFoundException {

        UserManagement userManagement;
        try {
            userManagement = new UserManagement();
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }

        List<User> userList = userManagement.getUserList();
        List<Staff> staffList = new ArrayList<>();

        // Hours worked by staff
        Map<String, Integer> staffHoursWorkedMap = new HashMap<>();
        for (User user : userList) {

            boolean isCustomer = userManagement.isCustomer(user);

            if (!isCustomer) {
                Staff staff = (Staff) user;
                staffList.add(staff);
                String currentUsername = staff.getUsername();
                int currentWorkedHours = staff.getNumOfTotalHoursWorked();

                if (staffHoursWorkedMap.containsKey(
                        currentUsername)) {
                    int cummulativeWorkedHours = staffHoursWorkedMap.get(
                            currentUsername
                    );
                    cummulativeWorkedHours += currentWorkedHours;
                    staffHoursWorkedMap.put(currentUsername,
                            cummulativeWorkedHours);
                } else {
                    staffHoursWorkedMap.put(currentUsername,
                            currentWorkedHours);
                }
            }
        }

        String mostWorkedHoursStaffUsername = "";
        int numOfHoursWorked = 0;
        for (Map.Entry<String, Integer> entry :
                staffHoursWorkedMap.entrySet()) {
            String currentStaffUsername = entry.getKey();
            int currentStaffCummulativeHours = entry.getValue();
            if (numOfHoursWorked < currentStaffCummulativeHours) {
                numOfHoursWorked = currentStaffCummulativeHours;
                mostWorkedHoursStaffUsername = currentStaffUsername;
            } else if (numOfHoursWorked == 0) {
                numOfHoursWorked = currentStaffCummulativeHours;
                mostWorkedHoursStaffUsername = currentStaffUsername;
            }
        }

        String staffWithFullName = "";
        String staffPosition = "";
        User staffWithTheMostWorkedHours = userManagement
                .getUserByUsername(mostWorkedHoursStaffUsername);

        staffWithFullName = staffWithTheMostWorkedHours
                .getDataForListDisplay();
        staffPosition = userManagement.getStaffClass(
                staffWithTheMostWorkedHours).toLowerCase();

        String mostWorkedHoursStaffReport = String.format(
                "%s, is an exceptional %s, having dedicated %d hours to " +
                        "their work.",
                staffWithFullName,
                staffPosition,
                numOfHoursWorked
        );

        String finalUserReport = addTheRemainingStaff(
                mostWorkedHoursStaffReport,
                mostWorkedHoursStaffUsername,
                staffList);

        return finalUserReport;
    }

    /**
     * This method reports the remaining staff details to the main report.
     *
     * @param mainReport           - the report to be updated.
     * @param excludeStaffUsername - the staff to exclude to prevent
     *                             duplication.
     * @param staffList            - the list of staff to search from.
     * @return the string of modified report with all staff details added.
     */
    public String addTheRemainingStaff(String mainReport,
                                       String excludeStaffUsername,
                                       List<Staff> staffList) {

        mainReport += System.lineSeparator();
        mainReport += System.lineSeparator();

        for (Staff staff : staffList) {
            String fullName = staff.getDataForListDisplay();
            String username = staff.getUsername();
            int numOfHoursWorked = staff.getNumOfTotalHoursWorked();
            int numOfHoursToWork = staff.getNumOfHoursToWork();
            String staffPosition = List.of(staff
                            .getClass().toString().split("\\."))
                    .getLast()
                    .toLowerCase();

            if (!username.equalsIgnoreCase(excludeStaffUsername)
                    && !staffPosition.equalsIgnoreCase("manager")) {
                if (numOfHoursWorked > 30) {
                    String goodStaffWorkReport = String.format(
                            "%s, %s, put in a solid %d hours of work this " +
                                    "week. There are still %d hours left.",
                            fullName, staffPosition,
                            numOfHoursWorked, numOfHoursToWork
                    );
                    mainReport += goodStaffWorkReport;
                } else if (numOfHoursWorked > 20) {
                    String averageStaffWorkReport = String.format(
                            "%s is a %s who has put in %d hours this " +
                                    "week. You should motivate them to " +
                                    "take on additional shifts.",
                            fullName, staffPosition, numOfHoursWorked
                    );
                    mainReport += averageStaffWorkReport;
                } else {
                    String underworkedStaffWorkReport = String.format(
                            "%s, %s, only managed to work %d hours this " +
                                    "week. You should probably check in on " +
                                    "them and see how they're doing.",
                            fullName, staffPosition, numOfHoursWorked
                    );
                    mainReport += underworkedStaffWorkReport;
                }

                mainReport += System.lineSeparator();
                mainReport += System.lineSeparator();
            }
        }
        return mainReport;
    }
}
