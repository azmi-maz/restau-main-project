package org.group.project.classes;

/**
 * This class reports on most popular menu items and outstanding orders.
 *
 * @author azmi_maz
 */
public class KitchenReport extends Report {

    /**
     * This constructor creates a new report with timestamp.
     *
     * @param reportId   - the unique report id.
     * @param reportType - the type of the report.
     * @param reportData - the main content of the report.
     * @param staff      - the staff who generated the report.
     */
    public KitchenReport(int reportId, String reportType, String reportData,
                         Staff staff) {
        super(reportId, reportType, reportData, staff);
    }

    /**
     * This method generates the most popular menu items report.
     *
     * @return the report for most popular menu items.
     */
    public KitchenReport getMostPopularMenuItemsReport() {
        // to code
        return null;
    }

    /**
     * This method generates the outstanding orders report.
     *
     * @return the report for outstanding orders.
     */
    public KitchenReport getOutstandingOrdersReport() {
        // to code
        return null;
    }
}
