package org.group.project.classes;

import org.group.project.classes.auxiliary.DataFileStructure;
import org.group.project.classes.auxiliary.DataManager;
import org.group.project.exceptions.TextFileNotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * This class standardises all reports in a clear and informative manner for
 * viewing or printing.
 *
 * @author azmi_maz
 */
public class Report {
    protected int reportId;
    protected String reportType;
    protected String reportData;
    protected String generatedBy;
    protected LocalDate generatedOnDate;
    protected LocalTime generatedOnTime;

    // TODO
    public Report(
            int reportId,
            String reportType,
            String reportData,
            String generatedBy,
            LocalDate generatedOnDate,
            LocalTime generatedOnTime
    ) {
        this.reportId = reportId;
        this.reportType = reportType;
        this.reportData = reportData;
        this.generatedBy = generatedBy;
        this.generatedOnDate = generatedOnDate;
        this.generatedOnTime = generatedOnTime;
    }

    /**
     * This constructor creates a new report with timestamp.
     *
     * @param reportId   - the unique report id.
     * @param reportType - the type of the report.
     * @param reportData - the main content of the report.
     * @param user       - the user who generated the report.
     */
    public Report(int reportId, String reportType, String reportData,
                  User user) {
        this.reportId = reportId;
        this.reportType = reportType;
        this.reportData = reportData;
        this.generatedBy = user.getDataForListDisplay();
        this.generatedOnDate = LocalDate.now();
        this.generatedOnTime = LocalTime.now();
    }

    // TODO
    public Report(String reportType, User user)
            throws TextFileNotFoundException {

        this.reportId = getNewReportId();
        this.reportType = reportType;
        this.generatedBy = user.getDataForListDisplay();
        this.generatedOnDate = LocalDate.now();
        this.generatedOnTime = LocalTime.now();

    }

    /**
     * Getter method to get the report id.
     *
     * @return the report id.
     */
    public int getReportId() {
        return reportId;
    }

    // TODO
    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    // TODO
    public int getNewReportId() throws TextFileNotFoundException {
        try {
            List<String> listOfData = DataManager
                    .allDataFromFile("REPORTS");
            int lastId = -1;
            String lastReport = listOfData.getLast();
            String[] reportDetails = lastReport.split(",");
            int reportIdIndex = DataFileStructure
                    .getIndexColOfUniqueId("REPORTS");
            lastId = Integer.parseInt(reportDetails[reportIdIndex]);
            if (lastId > -1) {
                lastId++;
                return lastId;
            }
            return lastId;

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Getter method to get the report type.
     *
     * @return the report type.
     */
    public String getReportType() {
        return reportType;
    }

    /**
     * Getter method to get who generated the report.
     *
     * @return the staff who made the report.
     */
    public String getGeneratedBy() {
        return generatedBy;
    }

    /**
     * Getter method to get the date the report was made.
     *
     * @return the report date.
     */
    public LocalDate getGeneratedOnDate() {
        return generatedOnDate;
    }

    // TODO
    public String getGeneratedOnDateInFormat() {
        return getGeneratedOnDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    // TODO comment
    public String getGeneratedOnDateForDatabase() {
        return getGeneratedOnDate().format(DateTimeFormatter.ofPattern("yyyy-M-d"));
    }

    /**
     * Getter method to get the time the report was made.
     *
     * @return the report time.
     */
    public LocalTime getGeneratedOnTime() {
        return generatedOnTime;
    }

    // TODO
    public String getGeneratedOnTimeInFormat() {
        return getGeneratedOnTime().format(DateTimeFormatter.ofPattern("hh:mm a"));
    }

    // TODO comment
    public String getGeneratedOnTimeForDatabase() {
        return getGeneratedOnTime().format(DateTimeFormatter.ofPattern("H-m"));
    }

    /**
     * Getter method to get the report data.
     *
     * @return the report data.
     */
    public String getReportData() {
        return reportData;
    }

    // TODO
    public void setReportData(String newReport) {
        this.reportData = newReport;
    }

    // TODO
    public void appendReportData(Report additionalReport) {
        this.reportData += " " + additionalReport.getReportData();
    }

    // TODO
    public String getReportDataForDatabase() {
        String compactedReportData = "";
        List<String> splitUp =
                List.of(getReportData().split(
                        System.lineSeparator()));

        for (int i = 0; i < splitUp.size(); i++) {
            String currentLine = splitUp.get(i);
            if (i == splitUp.size() - 1) {
                compactedReportData += currentLine;
            } else {
                compactedReportData += currentLine + ";";
            }
        }
        return compactedReportData.replaceAll(",", "}");
    }

    // TODO
    public String uncompactReportData(
            String compactedData
    ) {
        String uncompactedData = "";
        List<String> rawDataArray = List.of(compactedData.split(";"));
        for (String line : rawDataArray) {
            uncompactedData += line + System.lineSeparator();
        }

        return uncompactedData.replaceAll("}", ",");
    }

    /**
     * This method generates the report in a standardised format.
     *
     * @return a standardised report for the UI.
     */
    public String generateReport() {
        return String.format(
                "Report no. %d" + System.lineSeparator() +
                        "Type: %s" + System.lineSeparator() +
                        "Generated by: %s" + System.lineSeparator() +
                        "Date: %s %s" + System.lineSeparator() +
                        " " + System.lineSeparator() +
                        "%s",
                getReportId(),
                getReportType(),
                getGeneratedBy(),
                getGeneratedOnDateInFormat(),
                getGeneratedOnTimeInFormat(),
                uncompactReportData(getReportData())
        );
    }

    @Override
    public String toString() {
        return String.format(
                "No.%s, %s, %s %s",
                getReportId(),
                getReportType(),
                getGeneratedOnDateInFormat(),
                getGeneratedOnTimeInFormat()
        );
    }

}
