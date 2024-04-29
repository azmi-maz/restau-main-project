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

    // TODO
    public ReportManager() throws TextFileNotFoundException {

        reportList = new ArrayList<>();

        try {
            reportList = getReportDataFromDatabase();
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // TODO
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

    // TODO
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

    // TODO
    public LocalDate getLocalDateFromString(
            String reportDate
    ) {
        List<String> reportDateDetails =
                List.of(reportDate.split("-"));
        return LocalDate.of(Integer.parseInt(reportDateDetails.get(0)),
                Integer.parseInt(reportDateDetails.get(1)),
                Integer.parseInt(reportDateDetails.get(2)));
    }

    // TODO
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

    // TODO
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

    // TODO
    public void updateReportList(
            ListView<Report> generatedReportList
    ) {

        for (Report report : reportList) {
            generatedReportList.getItems().add(
                    report
            );
        }
    }

    // TODO
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

    public Report getMostActiveCustomerReport(
            User currentUser
    ) throws TextFileNotFoundException {

        try {

            FloorReport mostActive = null;
            mostActive = new FloorReport(
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

    // TODO
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
