package org.group.project.classes;

import org.group.project.exceptions.TextFileNotFoundException;

import java.util.List;

/**
 * This class represents waiter role in the restaurant.
 *
 * @author azmi_maz
 */
public class Waiter extends Staff {
    private static final String DINEIN_TYPE = "dinein";

    /**
     * The constructor to create new waiter.
     *
     * @param firstName - the first name of the staff.
     * @param lastName  - the last name of the staff.
     * @param username  - the username chosen by the staff.
     */
    public Waiter(String firstName, String lastName, String username) {
        super(firstName, lastName, username, false);
    }

    /**
     * This constructor creates a waiter with updated data from database.
     *
     * @param firstName             - the first name of the staff.
     * @param lastName              - the last name of the staff.
     * @param username              - the username chosen by the staff.
     * @param hasAdminRight         - the right to admin access.
     * @param numOfHoursToWork      - the number of work hours remaining.
     * @param numOfTotalHoursWorked - the total number of hours worked.
     */
    public Waiter(String firstName, String lastName, String username,
                  boolean hasAdminRight, int numOfHoursToWork,
                  int numOfTotalHoursWorked) {
        super(firstName, lastName, username, hasAdminRight,
                numOfHoursToWork, numOfTotalHoursWorked);
    }

    /**
     * This method approves a table reservation.
     *
     * @param booking  - the selected table reservation.
     * @param customer - the customer who made the table reservation.
     * @return true if the table reservation is approved successfully.
     */
    public boolean approveTableReservation(
            Booking booking,
            Customer customer) throws TextFileNotFoundException {

        try {

            booking.approveBooking();
            booking.notifyCustomer(customer,
                    true);

            return true;

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method cancels a table reservation made by customer.
     *
     * @param booking  - the selected table reservation to be cancelled.
     * @param customer - the customer who made the booking.
     * @return true if the cancellation was done successfully.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public boolean cancelTableReservation(
            Booking booking,
            Customer customer) throws TextFileNotFoundException {

        try {
            booking.cancelBooking();
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
        booking.notifyCustomer(customer,
                false);

        return true;
    }

    /**
     * This method create a dine-in order for customers in the restaurant.
     *
     * @param customerId - the id of the dinein customer.
     * @param orderList  - the list of orders from the dine-in customers.
     * @return true if the order was created successfully.
     */
    public boolean takeDineInOrders(
            int customerId,
            List<FoodDrink> orderList)
            throws TextFileNotFoundException {

        try {

            Kitchen kitchen = new Kitchen();

            Order newOrder = kitchen.createNewOrder(
                    DINEIN_TYPE,
                    customerId,
                    orderList
            );
            kitchen.addOrderToDatabase(
                    newOrder
            );

            return true;

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method approves a deliver order and assign a driver to perform
     * the delivery.
     *
     * @param deliveryOrder - the selected delivery order.
     * @param driverId      - the id of the assigned driver.
     * @return true if the approval and driver assignment were made
     * successfully.
     */
    public boolean approveDeliveryOrder(
            DeliveryOrder deliveryOrder,
            int driverId) throws TextFileNotFoundException {

        try {

            assignDriverForDelivery(
                    deliveryOrder,
                    driverId
            );
            deliveryOrder.notifyCustomer(
                    deliveryOrder.getCustomer(),
                    true
            );

            return true;

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method cancels a delivery order.
     *
     * @param deliveryOrder - the selected delivery order to cancel.
     * @return true if the cancellation was made successfully.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public boolean cancelDeliveryOrder(
            DeliveryOrder deliveryOrder)
            throws TextFileNotFoundException {

        try {

            deliveryOrder.cancelDeliveryOrder();
            deliveryOrder.notifyCustomer(
                    deliveryOrder.getCustomer(),
                    false
            );

            return true;

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method assigns a driver to a delivery order.
     *
     * @param deliveryOrder - the selected delivery order.
     * @param driverId      - the id of the assigned driver.
     * @return true if the driver assignment was successfully.
     */
    public void assignDriverForDelivery(
            DeliveryOrder deliveryOrder,
            int driverId) throws TextFileNotFoundException {

        try {
            deliveryOrder.approveDeliveryOrder(driverId);
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }

    }

}
