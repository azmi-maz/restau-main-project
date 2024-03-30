package org.group.project.classes;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles customer data and all the methods that a customer can
 * perform.
 *
 * @author azmi_maz
 */
public class Customer extends User {
    private int customerId;
    private String deliveryAddress;
    private List<Booking> listOfBookingsMade;
    private List<Order> listOfOrdersMade;
    private List<Notification> listOfNotifications;

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

    /**
     * Getter method to get the list of bookings made by the customer.
     *
     * @return the list of bookings previously made.
     */
    public List<Booking> getListOfBookingsMade() {
        return listOfBookingsMade;
    }

    /**
     * This method adds a new booking to the list that each customer holds.
     *
     * @param booking - the newly made booking.
     * @return true if booking was made successfully.
     */
    public boolean addNewBooking(Booking booking) {
        // to code
        return true;
    }

    /**
     * Getter method to get the list of orders made by the customer.
     *
     * @return the list of orders previously made.
     */
    public List<Order> getOrdersMade() {
        return listOfOrdersMade;
    }

    /**
     * This method adds a new order to the list that each customer holds.
     *
     * @param order - the new order made.
     * @return true of the order was made successfully.
     */
    public boolean addNewOrder(Order order) {
        // to code
        return true;
    }

    /**
     * Getter method to get the list of notifications for a customer.
     *
     * @return the list of notifications.
     */
    public List<Notification> getListOfNotifications() {
        return listOfNotifications;
    }

    /**
     * This method add a new notification to a customer.
     *
     * @param notification - the new notification to be added.
     * @return true if the new notification was added successfully.
     */
    public boolean addNewNotification(Notification notification) {
        // to code
        return true;
    }

    /**
     * This method allows the customer to view the current menu of the
     * restaurant.
     *
     * @return the current menu list of available foods and drinks.
     */
    public List<FoodDrink> viewMenuList() {
        // to code
        return new ArrayList<>() {
        };
    }

    /**
     * To print the first name and last name of a customer.
     *
     * @return the first and last name of a customer.
     */
    @Override
    public String toString() {
        return String.format("%s%s %s%s",
                firstName.substring(0, 1).toUpperCase(),
                firstName.substring(1),
                lastName.substring(0, 1).toUpperCase(),
                lastName.substring(1));
    }

}
