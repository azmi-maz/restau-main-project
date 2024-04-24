package org.group.project.classes;

import org.group.project.classes.auxiliary.HelperMethods;

import java.io.FileNotFoundException;
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
                      User user) {
        super(reportType, user);
        int nextReportId = 0;
        try {
            nextReportId = HelperMethods.getNewIdByFile("REPORTS");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        super.setReportId(nextReportId);
        super.setReportData(getStaffWhoWorkedTheMostHoursReport());
    }

    /**
     * This method generates the staff who worked the most hours report.
     *
     * @return the report for staff who worked the most.
     */
    public String getStaffWhoWorkedTheMostHoursReport() {
        return prepareReportData();
    }

    // TODO
    public String prepareReportData() {

        UserManagement userManagement = new UserManagement();
        List<User> userList = userManagement.getListOfAllUsers();
        List<Staff> staffList = new ArrayList<>();

        // Hours worked by staff
        Map<String, Integer> staffHoursWorkedMap = new HashMap<>();
        for (User user : userList) {

            Object classType = user.getClass();
            String objectType = List.of(classType
                    .toString().split("\\.")).getLast();

            if (!objectType.equalsIgnoreCase("customer")) {
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
        for (Map.Entry<String, Integer> entry : staffHoursWorkedMap.entrySet()) {
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
        User staffWithTheMostWorkedHours = null;
        for (User user : userList) {
            if (user.getUsername().equalsIgnoreCase(
                    mostWorkedHoursStaffUsername
            )) {
                staffWithTheMostWorkedHours = user;
            }
        }

        staffWithFullName = staffWithTheMostWorkedHours
                .getDataForListDisplay();
        staffPosition = List.of(staffWithTheMostWorkedHours
                        .getClass().toString().split("\\."))
                .getLast()
                .toLowerCase();

        String mostWorkedHoursStaffReport = String.format(
                "%s, is an exceptional %s, having dedicated %d hours to " +
                        "their work.",
                staffWithFullName,
                staffPosition,
                numOfHoursWorked
        );

        String finalUserReport = addTheRemainingStaff(mostWorkedHoursStaffReport,
                mostWorkedHoursStaffUsername,
                staffList);

        return finalUserReport;
    }

    // TODO
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
                            "%s, %s, put in a solid %d hours of work this week. " +
                                    "There are still %d hours left.",
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
                            "%s, %s, only managed to work %d hours this week. " +
                                    "You should probably check in on them " +
                                    "and see how they're doing.",
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
