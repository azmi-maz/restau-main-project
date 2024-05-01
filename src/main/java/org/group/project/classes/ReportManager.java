package org.group.project.classes;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import org.group.project.Main;
import org.group.project.classes.auxiliary.DataFileStructure;
import org.group.project.classes.auxiliary.DataManager;
import org.group.project.exceptions.TextFileNotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class handles all the reports made by the manager.
 *
 * @author azmi_maz
 */
public class ReportManager {

    private List<Report> reportList;
    private static final List<String> reportTypes =
            new ArrayList<>(Arrays.asList(
                    "Busiest periods",
                    "Most active customer",
                    "Most popular item",
                    "Outstanding orders",
                    "Staff worked hours"
            ));

    /**
     * This constructor sets up the report manager and updates its data
     * from the database.
     *
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public ReportManager() throws TextFileNotFoundException {

        reportList = new ArrayList<>();

        try {
            reportList = getReportDataFromDatabase();
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method gets all the reports from the database.
     *
     * @return - the list of reports.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public List<Report> getReportDataFromDatabase()
            throws TextFileNotFoundException {

        try {
            List<Report> reportList = new ArrayList<>();
            List<String> allReportsFromDatabase = DataManager
                    .allDataFromFile("REPORTS");
            UserManagement userManagement = new UserManagement();

            for (String report : allReportsFromDatabase) {
                reportList.add(
                        getReportFromString(
                                userManagement,
                                report
                        )
                );
            }
            return reportList;

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method gets the report from report data string.
     *
     * @param userManagement - the Class to get the user info.
     * @param report         - the report data string.
     * @return the report object with updated data.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public Report getReportFromString(
            UserManagement userManagement,
            String report
    ) throws TextFileNotFoundException {
        try {

            List<String> reportDetails = List.of(report.split(","));

            int reportId = Integer.parseInt(reportDetails
                    .get(DataFileStructure.getIndexColOfUniqueId(
                            "REPORTS"
                    )));

            String reportType = reportDetails
                    .get(DataFileStructure.getIndexByColName(
                            "REPORTS",
                            "reportType"
                    ));
            String reportData = reportDetails
                    .get(DataFileStructure.getIndexByColName(
                            "REPORTS",
                            "reportData"
                    ));
            String generatedById = reportDetails
                    .get(DataFileStructure.getIndexByColName(
                            "REPORTS",
                            "generatedBy"
                    ));
            String generatedBy = userManagement
                    .getUserByUserId(generatedById)
                    .getDataForListDisplay();
            LocalDate generatedOnDate = getLocalDateFromString(
                    reportDetails
                            .get(DataFileStructure.getIndexByColName(
                                    "REPORTS",
                                    "generatedOnDate"
                            )));
            LocalTime generatedOnTime = getLocalTimeFromString(
                    reportDetails
                            .get(DataFileStructure.getIndexByColName(
                                    "REPORTS",
                                    "generatedOnTime"
                            )));

            return new Report(
                    reportId,
                    reportType,
                    reportData,
                    generatedBy,
                    generatedOnDate,
                    generatedOnTime
            );

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method gets LocalDate from a date string.
     *
     * @param reportDate - the report date string.
     * @return the LocalDate of the report date.
     */
    public LocalDate getLocalDateFromString(
            String reportDate
    ) {
        List<String> reportDateDetails =
                List.of(reportDate.split("-"));
        return LocalDate.of(Integer.parseInt(reportDateDetails.get(0)),
                Integer.parseInt(reportDateDetails.get(1)),
                Integer.parseInt(reportDateDetails.get(2)));
    }

    /**
     * This method gets LocalTime from a time string.
     *
     * @param reportTime - the report time string.
     * @return the LocalTime of the report time.
     */
    public LocalTime getLocalTimeFromString(
            String reportTime
    ) {
        if (!reportTime.isBlank()) {
            List<String> reportTimeDetails =
                    List.of(reportTime.split("-"));
            return LocalTime.of(Integer.parseInt(reportTimeDetails.get(0)),
                    Integer.parseInt(reportTimeDetails.get(1)));
        }
        return null;
    }

    /**
     * This method adds a new report to the database.
     *
     * @param newReport - the new report to be added.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public void addReportToDatabase(
            Report newReport
    ) throws TextFileNotFoundException {
        UserManagement userManagement = new UserManagement();
        String reportId = String.valueOf(newReport.getReportId());
        String reportType = newReport.getReportType();
        String userId = String.valueOf(userManagement.getUserIdByUsername(
                Main.getCurrentUser().getUsername()));
        String reportDate = newReport.getGeneratedOnDateForDatabase();
        String reportTime = newReport.getGeneratedOnTimeForDatabase();
        String reportData = newReport.getReportDataForDatabase();

        List<String> reportFormat = new ArrayList<>(Arrays.asList(
                reportId,
                reportType,
                userId,
                reportDate,
                reportTime,
                reportData
        ));

        try {
            DataManager.appendDataToFile(
                    "REPORTS",
                    reportFormat
            );
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method populates a list view with all the generated reports.
     *
     * @param generatedReportList - the list view to be updated.
     */
    public void updateReportList(
            ListView<Report> generatedReportList
    ) {

        for (Report report : reportList) {
            generatedReportList.getItems().add(
                    report
            );
        }
    }

    /**
     * This method gets the most popular item kitchen report.
     *
     * @param currentUser - the user who generates the report.
     * @return the kitchen report.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public KitchenReport getMostPopularItemReport(
            User currentUser
    ) throws TextFileNotFoundException {

        try {

            return new KitchenReport(
                    "Most popular item",
                    currentUser
            );

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method gets the busiest period floor report.
     *
     * @param currentUser - the user who generates the report.
     * @return the floor report.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public FloorReport getBusiestPeriodReport(
            User currentUser
    ) throws TextFileNotFoundException {

        try {

            return new FloorReport(
                    "Busiest periods",
                    currentUser
            );

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method gets the most active customer floor report.
     *
     * @param currentUser - the use who generates the report.
     * @return the combined report.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public Report getMostActiveCustomerReport(
            User currentUser
    ) throws TextFileNotFoundException {

        try {

            FloorReport mostActive = new FloorReport(
                    "Most active customer",
                    currentUser
            );
            KitchenReport mostLoyal = new KitchenReport(
                    "Most active customer",
                    currentUser
            );
            mostActive.appendReportData(
                    mostLoyal
            );
            return mostActive;

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method gets the staff worked hours user report.
     *
     * @param currentUser - the user who generates the report.
     * @return the user report.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public UserReport getStaffWorkedHoursReport(
            User currentUser
    ) throws TextFileNotFoundException {
        try {

            return new UserReport(
                    "Staff worked hours",
                    currentUser
            );

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method gets the outstanding order kitchen report.
     *
     * @param currentUser - the user who generates the report.
     * @return the kitchen report.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public KitchenReport getOutstandingOrdersReport(
            User currentUser
    ) throws TextFileNotFoundException {

        try {

            return new KitchenReport(
                    "Outstanding orders",
                    currentUser
            );

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method populates a choice box with report types for user to select
     * from.
     *
     * @param choiceBox - the choice box to be updated.
     */
    public void updateReportTypeChoiceBox(
            ChoiceBox<String> choiceBox
    ) {
        for (String reportType : reportTypes) {
            choiceBox.getItems()
                    .add(reportType);
        }
        choiceBox.setValue("Choose report type");
    }
}
