package org.group.project.classes;

/**
 * This class reports on busiest periods and most active customer.
 *
 * @author azmi_maz
 */
public class FloorReport extends Report {

    /**
     * This constructor creates a new report with timestamp.
     *
     * @param reportId   - the unique report id.
     * @param reportType - the type of the report.
     * @param reportData - the main content of the report.
     * @param staff      - the staff who generated the report.
     */
    public FloorReport(int reportId, String reportType, String reportData,
                       Staff staff) {
        super(reportId, reportType, reportData, staff);
    }

    /**
     * This method generates the busiest period report.
     *
     * @return the report for busiest period.
     */
    public FloorReport getBusiestPeriodReport() {
        // to code
        return null;
    }

    /**
     * This method generates the most active customer report.
     *
     * @return the report for most active customer.
     */
    public FloorReport getMostActiveCustomer() {
        // to code
        return null;
    }
}
