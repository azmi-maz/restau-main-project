package org.group.project.classes;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import org.group.project.classes.auxiliary.DataFileStructure;
import org.group.project.classes.auxiliary.DataManager;
import org.group.project.exceptions.TextFileNotFoundException;
import org.group.project.scenes.MainScenesMap;

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
    private static final String REPORT_FILE = "REPORTS";
    private static final String TYPE_COLUMN = "reportType";
    private static final String DATA_COLUMN = "reportData";
    private static final String BY_COLUMN = "generatedBy";
    private static final String DATE_COLUMN = "generatedOnDate";
    private static final String TIME_COLUMN = "generatedOnTime";
    private static final String MOST_POPULAR_ITEM = "Most popular item";
    private static final String BUSIEST_PERIOD = "Busiest periods";
    private static final String MOST_ACTIVE_CUSTOMER = "Most active customer";
    private static final String STAFF_HOURS = "Staff worked hours";
    private static final String OUTSTANDING_ORDERS = "Outstanding orders";
    private static final String CHOOSE_REPORT = "Choose report type";
    private List<Report> reportList;
    private static final List<String> reportTypes =
            new ArrayList<>(Arrays.asList(
                    BUSIEST_PERIOD,
                    MOST_ACTIVE_CUSTOMER,
                    MOST_POPULAR_ITEM,
                    OUTSTANDING_ORDERS,
                    STAFF_HOURS
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
                    .allDataFromFile(REPORT_FILE);
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
                            REPORT_FILE
                    )));

            String reportType = reportDetails
                    .get(DataFileStructure.getIndexByColName(
                            REPORT_FILE,
                            TYPE_COLUMN
                    ));
            String reportData = reportDetails
                    .get(DataFileStructure.getIndexByColName(
                            REPORT_FILE,
                            DATA_COLUMN
                    ));
            String generatedById = reportDetails
                    .get(DataFileStructure.getIndexByColName(
                            REPORT_FILE,
                            BY_COLUMN
                    ));
            String generatedBy = userManagement
                    .getUserByUserId(generatedById)
                    .getDataForListDisplay();
            LocalDate generatedOnDate = getLocalDateFromString(
                    reportDetails
                            .get(DataFileStructure.getIndexByColName(
                                    REPORT_FILE,
                                    DATE_COLUMN
                            )));
            LocalTime generatedOnTime = getLocalTimeFromString(
                    reportDetails
                            .get(DataFileStructure.getIndexByColName(
                                    REPORT_FILE,
                                    TIME_COLUMN
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
                MainScenesMap.getCurrentUser().getUsername()));
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
                    REPORT_FILE,
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
                    MOST_POPULAR_ITEM,
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
                    BUSIEST_PERIOD,
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
                    MOST_ACTIVE_CUSTOMER,
                    currentUser
            );
            KitchenReport mostLoyal = new KitchenReport(
                    MOST_ACTIVE_CUSTOMER,
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
                    STAFF_HOURS,
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
                    OUTSTANDING_ORDERS,
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
        choiceBox.setValue(CHOOSE_REPORT);
    }
}
