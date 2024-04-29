package org.group.project.classes;

import org.group.project.classes.auxiliary.DataManager;
import org.group.project.exceptions.TextFileNotFoundException;

/**
 * This class handles customer data and all the methods that a customer can
 * perform.
 *
 * @author azmi_maz
 */
public class Customer extends User {
    private int customerId;
    private String deliveryAddress;

    /**
     * This constructor creates a new customer.
     *
     * @param firstName       - the first name of the customer.
     * @param lastName        - the last name of the customer.
     * @param username        - the username given by the customer.
     * @param customerId      - the customer id, unique for each customer.
     * @param deliveryAddress - the delivery address in one single string.
     */
    public Customer(String firstName, String lastName, String username,
                    int customerId, String deliveryAddress) {
        super(firstName, lastName, username);
        this.customerId = customerId;
        this.deliveryAddress = deliveryAddress;
    }

    // TODO
    public Customer(
            String firstName,
            String lastName,
            String username,
            String deliveryAddress
    ) throws TextFileNotFoundException {

        super(firstName, lastName, username);
        UserManagement userManagement = new UserManagement();
        this.customerId = userManagement.getNewUserId();
        this.deliveryAddress = formatAddressToWrite(deliveryAddress);

    }

    /**
     * Getter method to get the customer id.
     *
     * @return the id of the customer.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Getter method to get the delivery address of the customer.
     *
     * @return the delivery address of the customer.
     */
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    // TODO
    public String getDeliveryAddressToRead() {

        return deliveryAddress
                .replaceAll(";", ",");
    }

    /**
     * This method adds a new booking to the list that each customer holds.
     *
     * @param booking - the newly made booking.
     * @param floor   - the list of bookings.
     * @return true if booking was made successfully.
     */
    public boolean addNewBooking(
            Booking booking,
            Floor floor) throws TextFileNotFoundException {

        try {
            floor.addBookingToDatabase(
                    booking
            );
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }

        return true;
    }

    // TODO
    public boolean editBooking(
            Booking booking,
            Floor floor
    ) throws TextFileNotFoundException {

        try {
            floor.editBookingDataInDatabase(
                    booking
            );
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }

        return true;
    }

    // TODO
    public boolean deleteBooking(
            Booking booking
    ) throws TextFileNotFoundException {

        int bookingId = booking.getBookingId();
        try {
            return DataManager.deleteUniqueIdFromFile(
                    "BOOKINGS",
                    bookingId);
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method adds a new order to the list that each customer holds.
     *
     * @param order - the new order made.
     * @return true of the order was made successfully.
     */
    public boolean addNewOrder(Order order)
            throws TextFileNotFoundException {

        try {

            Kitchen kitchen = new Kitchen();
            return kitchen.addOrderToDatabase(
                    order
            );

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // TODO
    public void updateNotificationReadStatus(
            int notificationId
    ) throws TextFileNotFoundException {
        try {
            DataManager.editColumnDataByUniqueId(
                    "NOTIFICATION",
                    notificationId,
                    "readStatus",
                    "true");
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method formats address to transform any symbols incompatible for
     * data storage.
     *
     * @param address - the unformatted address taken from textfield.
     * @return an address with the correct format.
     */
    public String formatAddressToWrite(String address) {
        return address.replaceAll(",", ";");
    }

    /**
     * To print the first name and last name of a customer.
     *
     * @return the first and last name of a customer.
     */
    @Override
    public String toString() {
        return super.getDataForListDisplay();
    }

}
