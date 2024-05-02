package org.group.project.classes;

import org.group.project.exceptions.TextFileNotFoundException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class reports on busiest periods and most active customer.
 *
 * @author azmi_maz
 */
public class FloorReport extends Report {
    private static final String BUSIEST_PERIOD = "busiest periods";
    private static final String MOST_ACTIVE_CUSTOMER = "most active customer";
    private static final String APPROVED_STATUS = "approved";
    private static final String PENDING_STATUS = "pending-approval";
    private static final String LONG_DATE_FORMAT = "EEEE, dd MMMM yyyy";
    private static final String BUSIEST_PERIOD_REPORT = "According to " +
            "previous records, the %s stood " +
            "out as the most bustling day, with a total " +
            "of %d table " +
            "reservations."
            + System.lineSeparator() + System.lineSeparator() +
            "Based on upcoming reservations, it seems that " +
            "%s will be a particularly busy day, with a " +
            "total of %d table reservations."
            + System.lineSeparator() +
            "%s"
            + System.lineSeparator() +
            "It would be wise to plan ahead.";
    private static final String ALL_TABLES_CONFIRMED = "On that day, all " +
            "table reservations are confirmed.";
    private static final String FEW_TABLES_PENDING = "On that day, there " +
            "are %d confirmed table reservations"
            + System.lineSeparator() +
            "with %d reservations pending for approval.";
    private static final String MOST_ACTIVE_CUSTOMER_REPORT = "The most " +
            "active customer is %s with %d table " +
            "reservations approved.";

    /**
     * This constructor creates a new report with timestamp.
     *
     * @param reportType - the type of the report.
     * @param user       - the user who generated the report.
     */
    public FloorReport(String reportType,
                       User user)
            throws TextFileNotFoundException {

        super(reportType, user);

        if (reportType.equalsIgnoreCase(
                BUSIEST_PERIOD)) {
            super.setReportData(getBusiestPeriodReport());
        } else if (reportType.equalsIgnoreCase(
                MOST_ACTIVE_CUSTOMER)) {
            super.setReportData(getMostActiveCustomerReport());
        }

    }

    /**
     * This method generates the busiest period report.
     *
     * @return the report for busiest period.
     */
    public String getBusiestPeriodReport()
            throws TextFileNotFoundException {
        try {
            return prepareReportData();
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method generates the most active customer report.
     *
     * @return the report for most active customer.
     */
    public String getMostActiveCustomerReport()
            throws TextFileNotFoundException {
        try {
            return prepareReportData();
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method generates a report based on report type. The report is based
     * on table reservations data.
     *
     * @return either busiest period or most active customer report.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public String prepareReportData() throws TextFileNotFoundException {

        Floor floor = null;
        try {
            floor = new Floor();
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
        List<Booking> bookingList = floor.getAllUniqueBookings();

        Map<LocalDate, Integer> dateRangeMap = new HashMap<>();
        Map<Integer, Integer> customerCountMap = new HashMap<>();
        for (Booking booking : bookingList) {
            LocalDate currentDate = booking.getBookingDate();
            if (dateRangeMap.containsKey(currentDate)) {
                int currentCount = dateRangeMap.get(currentDate);
                currentCount++;
                dateRangeMap.put(currentDate, currentCount);
            } else {
                dateRangeMap.put(currentDate, 1);
            }
            int currentCustomerId = booking.getCustomerId();
            if (booking.getBookingStatus().equalsIgnoreCase(
                    APPROVED_STATUS)) {
                if (customerCountMap.containsKey(currentCustomerId)) {
                    int currentCustomerScore = customerCountMap
                            .get(currentCustomerId);
                    currentCustomerScore++;
                    customerCountMap.put(currentCustomerId,
                            currentCustomerScore);
                } else {
                    customerCountMap.put(currentCustomerId, 1);
                }
            }
        }

        int pastMaxNumTableReservations = 0;
        LocalDate pastDateWithMaxNum = null;
        int futureMaxNumTableReservations = 0;
        LocalDate futureDateWithMaxNum = null;
        for (Map.Entry<LocalDate, Integer> entry : dateRangeMap.entrySet()) {
            LocalDate currentDate = entry.getKey();
            int currentTableReservations = entry.getValue();
            if (currentDate.isBefore(LocalDate.now())) {
                if (pastMaxNumTableReservations < currentTableReservations) {
                    pastMaxNumTableReservations = currentTableReservations;
                    pastDateWithMaxNum = currentDate;
                } else if (pastMaxNumTableReservations == 0) {
                    pastMaxNumTableReservations = currentTableReservations;
                    pastDateWithMaxNum = currentDate;
                }
            } else {
                if (futureMaxNumTableReservations < currentTableReservations) {
                    futureMaxNumTableReservations = currentTableReservations;
                    futureDateWithMaxNum = currentDate;
                } else if (futureMaxNumTableReservations == 0) {
                    futureMaxNumTableReservations = currentTableReservations;
                    futureDateWithMaxNum = currentDate;
                }
            }
        }

        int maxNumBookings = 0;
        int mostActiveCustomerId = 0;
        for (Map.Entry<Integer, Integer> entry : customerCountMap.entrySet()) {
            int currentCustomerId = entry.getKey();
            int currentCustomerBookings = entry.getValue();
            if (maxNumBookings < currentCustomerBookings) {
                maxNumBookings = currentCustomerBookings;
                mostActiveCustomerId = currentCustomerId;
            } else if (maxNumBookings == 0) {
                maxNumBookings = currentCustomerBookings;
                mostActiveCustomerId = currentCustomerId;
            }
        }

        Customer mostActiveCustomer = null;
        int numOfPendingBookings = 0;
        int numOfApprovedBookings = 0;
        for (Booking booking : bookingList) {
            if (booking.getCustomerId() == mostActiveCustomerId) {
                mostActiveCustomer = booking.getCustomer();
            }
            if (futureDateWithMaxNum != null
                    && booking.getBookingDate().isEqual(futureDateWithMaxNum)) {
                if (booking.getBookingStatus()
                        .equalsIgnoreCase(PENDING_STATUS)) {
                    numOfPendingBookings++;
                } else if (booking.getBookingStatus()
                        .equalsIgnoreCase(APPROVED_STATUS)) {
                    numOfApprovedBookings++;
                }
            }
        }

        String finalFutureBookings = handleBookingStatusReport(
                numOfPendingBookings,
                numOfApprovedBookings
        );

        String busiestPeriodReport = String.format(
                BUSIEST_PERIOD_REPORT,
                pastDateWithMaxNum
                        .format(DateTimeFormatter.ofPattern(
                                LONG_DATE_FORMAT)),
                pastMaxNumTableReservations,
                futureDateWithMaxNum
                        .format(DateTimeFormatter.ofPattern(
                                LONG_DATE_FORMAT)),
                futureMaxNumTableReservations,
                finalFutureBookings
        );

        String mostActiveCustomerReport = String.format(
                MOST_ACTIVE_CUSTOMER_REPORT,
                mostActiveCustomer,
                maxNumBookings
        );

        if (getReportType().equalsIgnoreCase(
                BUSIEST_PERIOD)) {
            return busiestPeriodReport;
        } else if (getReportType().equalsIgnoreCase(
                MOST_ACTIVE_CUSTOMER)) {
            return mostActiveCustomerReport;
        }
        return null;
    }

    /**
     * This method provides remark on any pending table reservation approvals,
     * if there are any.
     *
     * @param numOfPendings - the number of pending approvals.
     * @param numOfApproved - the number of approved bookings.
     * @return the remark on the status of pending bookings.
     */
    private String handleBookingStatusReport(
            int numOfPendings,
            int numOfApproved
    ) {

        if (numOfPendings == 0) {
            return String.format(
                    ALL_TABLES_CONFIRMED,
                    numOfApproved
            );
        } else {
            return String.format(
                    FEW_TABLES_PENDING,
                    numOfApproved,
                    numOfPendings
            );
        }
    }
}
