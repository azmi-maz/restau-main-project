package org.group.project.classes;

import org.group.project.classes.auxiliary.HelperMethods;

import java.io.FileNotFoundException;
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

    /**
     * This constructor creates a new report with timestamp.
     *
     * @param reportType - the type of the report.
     * @param user       - the user who generated the report.
     */
    public FloorReport(String reportType,
                       User user) {
        super(reportType, user);
        int nextReportId = 0;
        try {
            nextReportId = HelperMethods.getNewIdByFile("REPORTS");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        super.setReportId(nextReportId);
        if (reportType.equalsIgnoreCase("busiest period")) {
            super.setReportData(getBusiestPeriodReport());
        } else if (reportType.equalsIgnoreCase("most active customer")) {
            super.setReportData(getMostActiveCustomerReport());
        }
    }

    /**
     * This method generates the busiest period report.
     *
     * @return the report for busiest period.
     */
    public String getBusiestPeriodReport() {
        return prepareReportData();
    }

    /**
     * This method generates the most active customer report.
     *
     * @return the report for most active customer.
     */
    public String getMostActiveCustomerReport() {
        return prepareReportData();
    }

    // TODO
    public String prepareReportData() {

        Floor floor = new Floor();
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
                // TODO magic number
                dateRangeMap.put(currentDate, 1);
            }
            int currentCustomerId = booking.getCustomerId();
            if (booking.getBookingStatus().equalsIgnoreCase("approved")) {
                if (customerCountMap.containsKey(currentCustomerId)) {
                    int currentCustomerScore = customerCountMap.get(currentCustomerId);
                    currentCustomerScore++;
                    customerCountMap.put(currentCustomerId, currentCustomerScore);
                } else {
                    // TODO magic number
                    customerCountMap.put(currentCustomerId, 1);
                }
            }
        }

        // TODO magic numbers
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
                        .equalsIgnoreCase("pending-approval")) {
                    numOfPendingBookings++;
                } else if (booking.getBookingStatus()
                        .equalsIgnoreCase("approved")) {
                    numOfApprovedBookings++;
                }
            }
        }

        String finalFutureBookings = handleBookingStatusReport(
                numOfPendingBookings,
                numOfApprovedBookings
        );

        String busiestPeriodReport = String.format(
                "Past data showed that %s was the busiest day with %d table reservations."
                        + System.lineSeparator() + System.lineSeparator() +
                        "Future bookings indicate that %s will the busiest day with %d table reservations."
                        + System.lineSeparator() +
                        "%s" + System.lineSeparator() +
                        "So, please plan ahead.",
                pastDateWithMaxNum
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                pastMaxNumTableReservations,
                futureDateWithMaxNum
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                futureMaxNumTableReservations,
                finalFutureBookings
        );

        String mostActiveCustomerReport = String.format(
                "The most active customer is %s with %d table reservations approved.",
                mostActiveCustomer,
                maxNumBookings
        );

        if (getReportType().equalsIgnoreCase("busiest period")) {
            return busiestPeriodReport;
        } else if (getReportType().equalsIgnoreCase("most active customer")) {
            return mostActiveCustomerReport;
        }
        return null;
    }

    private String handleBookingStatusReport(
            int numOfPendings,
            int numOfApproved
    ) {
        // TODO magic
        if (numOfPendings == 0) {
            return String.format(
                    "On that day, all table reservations are confirmed.",
                    numOfApproved
            );
        } else {
            return String.format(
                    "On that day, there are %d confirmed table reservations"
                            + System.lineSeparator() +
                            "with %d reservations pending for approval.",
                    numOfApproved,
                    numOfPendings
            );
        }
    }
}
