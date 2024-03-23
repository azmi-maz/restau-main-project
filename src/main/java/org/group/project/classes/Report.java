package org.group.project.classes;

import java.time.LocalDate;
import java.time.LocalTime;

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

    /**
     * This constructor creates a new report with timestamp.
     *
     * @param reportId   - the unique report id.
     * @param reportType - the type of the report.
     * @param reportData - the main content of the report.
     */
    public Report(int reportId, String reportType, String reportData,
                  Staff staff) {
        this.reportId = reportId;
        this.reportType = reportType;
        this.reportData = reportData;
        this.generatedBy = String.format("%s %s", staff.getFirstName(),
                staff.getLastName());
        // How to fix date and time?
        this.generatedOnDate = LocalDate.now();
        this.generatedOnTime = LocalTime.now();
    }

    /**
     * Getter method to get the report id.
     * @return the report id.
     */
    public int getReportId() {
        return reportId;
    }

    /**
     * Getter method to get the report type.
     * @return the report type.
     */
    public String getReportType() {
        return reportType;
    }

    /**
     * Getter method to get who generated the report.
     * @return the staff who made the report.
     */
    public String getGeneratedBy() {
        return generatedBy;
    }

    /**
     * Getter method to get the date the report was made.
     * @return the report date.
     */
    public LocalDate getGeneratedOnDate() {
        return generatedOnDate;
    }

    /**
     * Getter method to get the time the report was made.
     * @return the report time.
     */
    public LocalTime getGeneratedOnTime() {
        return generatedOnTime;
    }

    /**
     * Getter method to get the report data.
     * @return the report data.
     */
    public String getReportData() {
        return reportData;
    }

    /**
     * This method generates the report in a standardised format.
     *
     * @return a standardised report for the UI.
     */
    public String generateReport() {
        // to code
        return null;
    }

}
