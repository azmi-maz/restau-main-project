package org.group.project.classes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles booking details made by customer.
 *
 * @author azmi_maz
 */
public class Booking {
    private final Customer customer;
    private final LocalDate bookingDate;
    private final LocalTime bookingTime;
    private final int numOfGuests;
    private final int bookingLengthInHour;
    private final List<Table> tablePreference = new ArrayList<Table>();
    private String bookingStatus;

    /**
     * Booking constructor where the customer did not specify booking length.
     * Default of booking length is one hour.
     *
     * @param customer     - customer who is making a table reservation.
     * @param bookingDate  - the date for the table reservation.
     * @param bookingTime  - the time for the table reservation.
     * @param numOfGuests  - number of guests for the table reservation.
     * @param tableRequest - list of one table or more for the guests.
     */
    public Booking(Customer customer, LocalDate bookingDate,
                   LocalTime bookingTime, int numOfGuests,
                   List<Table> tableRequest) {
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
     * @param customer      - customer who is making a table reservation.
     * @param bookingDate   - the date for the table reservation.
     * @param bookingTime   - the time for the table reservation.
     * @param numOfGuests   - number of guests for the table reservation.
     * @param tableRequest  - list of one table or more for the guests.
     * @param bookingLength - the booking length requested by customer.
     */
    public Booking(Customer customer, LocalDate bookingDate,
                   LocalTime bookingTime, int numOfGuests,
                   List<Table> tableRequest, int bookingLength) {
        this.customer = customer;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.numOfGuests = numOfGuests;
        this.tablePreference.addAll(tableRequest);
        this.bookingLengthInHour = bookingLength;
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
     * Getter method to get the booking time.
     *
     * @return The booking time.
     */
    public LocalTime getBookingTime() {
        return bookingTime;
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


}
