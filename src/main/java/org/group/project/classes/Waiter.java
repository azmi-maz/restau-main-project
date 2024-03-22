package org.group.project.classes;

import java.util.List;

/**
 * This class represents waiter role in the restaurant.
 */
public class Waiter extends Staff {

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
     * This method approves a table reservation.
     *
     * @param booking - the selected table reservation.
     * @return true if the table reservation is approved successfully.
     */
    public boolean approveTableReservation(Booking booking) {
        booking.updateBookingStatus("approved");
        return true;
    }

    /**
     * This method create a dine-in order for customers in the restaurant.
     *
     * @param orderLists - the list of orders from the dine-in customers.
     * @return true if the order was created successfully.
     */
    public boolean takeDineInOrders(List<FoodDrink> orderLists) {
        // to code
        return true;
    }

    /**
     * This method approves a deliver order and assign a driver to perform
     * the delivery.
     *
     * @param deliveryOrder - the selected delivery order.
     * @return true if the approval and driver assignment were made
     * successfully.
     */
    public boolean approveDeliveryOrder(DeliveryOrder deliveryOrder) {
        // need OrderUpdate method from DeliveryOrder
        // deliveryOrder.

        // get any available drivers from userlist
        // set this delivery order with that driver
        return true;
    }

    /**
     * This method assigns a driver to a delivery order.
     *
     * @param deliveryOrder - the selected delivery order.
     * @return true if the driver assignment was successfully.
     */
    public boolean assignDriverForDelivery(DeliveryOrder deliveryOrder) {
        // to code
        return true;
    }

}
