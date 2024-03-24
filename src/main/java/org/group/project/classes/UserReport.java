package org.group.project.classes;

/**
 * This class reports on staff who worked the most hours.
 *
 * @author azmi_maz
 */
public class UserReport extends Report {

    /**
     * This constructor creates a new report with timestamp.
     *
     * @param reportId   - the unique report id.
     * @param reportType - the type of the report.
     * @param reportData - the main content of the report.
     * @param staff      - the staff who generated the report.
     */
    public UserReport(int reportId, String reportType, String reportData,
                      Staff staff) {
        super(reportId, reportType, reportData, staff);
    }

    /**
     * This method generates the staff who worked the most hours report.
     *
     * @return the report for staff who worked the most.
     */
    public UserReport getStaffWhoWorkedTheMostHoursReport() {
        // to code
        return null;
    }
}
