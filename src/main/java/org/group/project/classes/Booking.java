package org.group.project.classes;

import org.group.project.classes.auxiliary.DataManager;
import org.group.project.exceptions.TextFileNotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles booking details made by customer.
 *
 * @author azmi_maz
 */
public class Booking implements NotifyAction {

    private static final String PENDING_APPROVAL = "pending-approval";
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String TIME_FORMAT = "hh:mm a";
    private static final String SUCCESS_MESSAGE = "Good news! " +
            "Your table reservation "
            + "for %s at %s "
            + "is now confirmed.";
    private static final String FAILURE_MESSAGE = "We're terribly sorry! " +
            "Unfortunately; your table reservation "
            + "for %s at %s is not successful.";
    private static final String BOOKING_FILE = "BOOKINGS";
    private static final String BOOKING_TYPE = "booking";
    private static final String BOOKING_STATUS_COLUMN = "bookingStatus";
    private static final String APPROVED_STATUS = "approved";
    private static final String FAILED_STATUS = "approved";

    private int bookingId;
    private Customer customer;
    private LocalDate bookingDate;
    private LocalTime bookingTime;
    private int numOfGuests;
    private int bookingLengthInHour;
    private List<Table> tablePreference = new ArrayList<>();
    private String bookingStatus;

    /**
     * Booking constructor where the customer did not specify booking length.
     * Default of booking length is one hour.
     *
     * @param bookingId    - the unique id.
     * @param customer     - customer who is making a table reservation.
     * @param bookingDate  - the date for the table reservation.
     * @param bookingTime  - the time for the table reservation.
     * @param numOfGuests  - number of guests for the table reservation.
     * @param tableRequest - list of one table or more for the guests.
     */
    public Booking(int bookingId, Customer customer, LocalDate bookingDate,
            LocalTime bookingTime, int numOfGuests,
            List<Table> tableRequest) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.numOfGuests = numOfGuests;
        this.tablePreference.addAll(tableRequest);
        this.bookingLengthInHour = 1;
    }

    /**
     * Booking constructor where the customer did specify the booking length.
     *
     * @param bookingId     - the unique id.
     * @param customer      - customer who is making a table reservation.
     * @param bookingDate   - the date for the table reservation.
     * @param bookingTime   - the time for the table reservation.
     * @param numOfGuests   - number of guests for the table reservation.
     * @param tableRequest  - list of one table or more for the guests.
     * @param bookingLength - the booking length requested by customer.
     */
    public Booking(int bookingId, Customer customer, LocalDate bookingDate,
            LocalTime bookingTime, int numOfGuests,
            List<Table> tableRequest, int bookingLength) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.numOfGuests = numOfGuests;
        this.tablePreference.addAll(tableRequest);
        this.bookingLengthInHour = bookingLength;
    }

    /**
     * Booking constructor where booking data is fully complete for viewing
     * purpose.
     *
     * @param bookingId     - the unique id.
     * @param customer      - customer who is making a table reservation.
     * @param bookingDate   - the date for the table reservation.
     * @param bookingTime   - the time for the table reservation.
     * @param numOfGuests   - number of guests for the table reservation.
     * @param tableRequest  - list of one table or more for the guests.
     * @param bookingLength - the booking length requested by customer.
     * @param status        - the status of the booking.
     */
    public Booking(int bookingId, Customer customer, LocalDate bookingDate,
            LocalTime bookingTime, int numOfGuests,
            List<Table> tableRequest, int bookingLength, String status) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.numOfGuests = numOfGuests;
        this.tablePreference.addAll(tableRequest);
        this.bookingLengthInHour = bookingLength;
        this.bookingStatus = status;
    }

    /**
     * Booking constructor to create booking from existing data taken from
     * database.
     *
     * @param newBookingId    - the unique id.
     * @param customerId      - the customer id who made the booking.
     * @param bookingDate     - the date of the booking.
     * @param bookingTime     - the time of the booking.
     * @param numOfGuests     - the number of guests for that booking.
     * @param tablePreference - the table reserved for the customer.
     * @param bookingLength   - the length of the booking time.
     * @throws TextFileNotFoundException - if the booking data file is
     *                                   non-existent
     */
    public Booking(
            int newBookingId,
            int customerId,
            LocalDate bookingDate,
            LocalTime bookingTime,
            int numOfGuests,
            Table tablePreference,
            int bookingLength) throws TextFileNotFoundException {

        try {

            bookingId = newBookingId;
            UserManagement userManagement = new UserManagement();
            customer = userManagement.getCustomerById(customerId);
            this.bookingDate = bookingDate;
            this.bookingTime = bookingTime;
            this.numOfGuests = numOfGuests;
            this.tablePreference.add(tablePreference);
            this.bookingLengthInHour = bookingLength;
            bookingStatus = PENDING_APPROVAL;

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }

    }

    /**
     * Getter method to get the booking id.
     *
     * @return - the booking id.
     */
    public int getBookingId() {
        return bookingId;
    }

    /**
     * Getter method to get customer.
     *
     * @return Customer who made the booking.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Getter method to get the customer id.
     *
     * @return The customer id who made the booking.
     */
    public int getCustomerId() {
        return customer.getCustomerId();
    }

    /**
     * Getter method to get the booking date.
     *
     * @return The booking date.
     */
    public LocalDate getBookingDate() {
        return bookingDate;
    }

    /**
     * Getter method to get the booking date in dd/mm/yyyy format.
     *
     * @return - the booking date in the desired format.
     */
    public String getBookingDateInFormat() {
        return bookingDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    /**
     * Getter method to get the booking time.
     *
     * @return The booking time.
     */
    public LocalTime getBookingTime() {
        return bookingTime;
    }

    /**
     * Getter method to get the booking time in hh:mm a format.
     *
     * @return - the booking time in the desired format.
     */
    public String getBookingTimeInFormat() {
        return bookingTime.format(DateTimeFormatter.ofPattern(TIME_FORMAT));
    }

    /**
     * Getter method to get the number of guests.
     *
     * @return The number of guests of the booking.
     */
    public int getNumOfGuests() {
        return numOfGuests;
    }

    /**
     * Getter method to get the booking length.
     *
     * @return The booking length in hour.
     */
    public int getBookingLengthInHour() {
        return bookingLengthInHour;
    }

    /**
     * Getter method to get the table preferences.
     *
     * @return The table preferences of the booking.
     */
    public List<Table> getTablePreference() {
        return tablePreference;
    }

    /**
     * This method prints out the table lists in TableView friendly-format.
     *
     * @return the list of table preferences.
     */
    public String getTableNames() {
        return getTablePreference().toString()
                .replace("[", "")
                .replace("]", "");
    }

    /**
     * Getter method to get the booking status.
     *
     * @return The current booking status.
     */
    public String getBookingStatus() {
        return bookingStatus;
    }

    /**
     * This method checks if the number of guests matches or less than the
     * tables requested.
     *
     * @param tableRequest - List of tables that are requested by the customer.
     * @return true if booking can be made, false if not.
     */
    public boolean checksTableAvailability(List<Table> tableRequest) {
        int seatsAvailable = 0;
        int seatsNeeded = getNumOfGuests();
        for (Table table : tableRequest) {
            seatsAvailable += table.getNumberOfSeats();
        }

        // If the no. of seats available is more than the no. of seats needed
        // then it's ok to approve the booking.
        if (seatsAvailable >= seatsNeeded) {
            return true;
        }
        return false;
    }

    /**
     * This method updates the booking status.
     *
     * @param newStatus The new status to update the booking status.
     */
    public void updateBookingStatus(String newStatus) {
        this.bookingStatus = newStatus;
    }

    /**
     * This method gets the time in string format when a booking ends.
     *
     * @return the time a booking ends in string format.
     */
    public String getEndBookingTime() {
        return bookingTime
                .plusHours(bookingLengthInHour)
                .format(DateTimeFormatter.ofPattern(TIME_FORMAT));
    }

    /**
     * This method gets the LocalTime when a booking ends.
     * 
     * @return the LocalTime a booking ends.
     */
    public LocalTime getEndTimeOfBooking() {
        return bookingTime
                .plusHours(bookingLengthInHour);
    }

    /**
     * This method show the period of booking time from start to end.
     *
     * @return the time period of a booking.
     */
    public String getTimePeriodOfBooking() {
        return bookingTime.format(DateTimeFormatter.ofPattern(TIME_FORMAT))
                + " - "
                + getEndBookingTime();
    }

    /**
     * This method creates a message to confirm a reservation was made
     * successfully.
     *
     * @return - the formatted successful message.
     */
    public String successfulBookingMessage() {
        return String.format(
                SUCCESS_MESSAGE,
                getBookingDateInFormat(),
                getBookingTimeInFormat());
    }

    /**
     * This method creates a message to indicate a reservation was
     * cancelled by the waiter.
     *
     * @return - the formatted message that informs the cancellation.
     */
    public String failedBookingMessage() {
        return String.format(
                FAILURE_MESSAGE,
                getBookingDateInFormat(),
                getBookingTimeInFormat());
    }

    /**
     * This method approves a table reservation by changing its status to
     * approved.
     *
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public void approveBooking() throws TextFileNotFoundException {

        try {
            DataManager.editColumnDataByUniqueId(BOOKING_FILE,
                    bookingId, BOOKING_STATUS_COLUMN,
                    APPROVED_STATUS);
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }

    }

    /**
     * This method cancels a table reservation by changing its status to
     * failed.
     *
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public void cancelBooking() throws TextFileNotFoundException {
        try {
            DataManager.editColumnDataByUniqueId(BOOKING_FILE,
                    bookingId, BOOKING_STATUS_COLUMN,
                    FAILED_STATUS);
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method deletes a table reservation by deleting the specific
     * data in the database by its booking id.
     *
     * @throws TextFileNotFoundException - if the text file is non-existent.
     */
    public void deleteBooking() throws TextFileNotFoundException {
        try {
            DataManager.deleteUniqueIdFromFile(BOOKING_FILE,
                    bookingId);
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method updates the customer who made the booking according to
     * the status changes.
     *
     * @param customer            - The customer to be notified.
     * @param isSuccessfulRequest - successful request or not.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    @Override
    public void notifyCustomer(Customer customer,
            boolean isSuccessfulRequest)
            throws TextFileNotFoundException {

        try {

            NotificationBoard notificationBoard = new NotificationBoard();
            String notificationType = BOOKING_TYPE;
            Notification newNotification = notificationBoard
                    .createNewNotification(
                            customer.getCustomerId(),
                            notificationType);

            if (isSuccessfulRequest) {
                newNotification.setMessageBody(
                        successfulBookingMessage());
            } else {
                newNotification.setMessageBody(
                        failedBookingMessage());
            }

            notificationBoard.addNotificationToDatabase(
                    newNotification);

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }

    }
}
