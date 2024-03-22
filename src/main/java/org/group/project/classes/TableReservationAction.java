package org.group.project.classes;

/**
 * This interface class
 */
public interface TableReservationAction {

    /**
     * This method creates a new table reservation.
     *
     * @param newBooking - the new request made by customer.
     * @return the booking back for the UI, if made successfully.
     */
    public Booking requestTableReservation(Booking newBooking);

    /**
     * This method cancels a table reservation requested by a customer.
     *
     * @param booking  - the booking to be cancelled.
     * @param customer - the customer who made that cancellation request.
     * @return true if the request is made successfully.
     */
    public boolean cancelTableReservation(Booking booking, Customer customer);

    /**
     * This method cancels a table reservation.
     *
     * @param booking - the booking to be cancelled.
     * @return true if the cancellation is made successfully.
     */
    public boolean cancelTableReservation(Booking booking);
}
