package org.group.project.classes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles all the orders made by the customer and waiter.
 *
 * @author azmi_maz
 */
public class Kitchen {
    private List<Order> orderTickets;

    /**
     * This constructor is default without any parameters.
     */
    public Kitchen() {
        orderTickets = new ArrayList<>();
    }

    /**
     * Getter method to get all the order tickets ever existed from the kitchen.
     *
     * @return the list of all order tickets ever made.
     */
    public List<Order> getAllOrderTickets() {
        if (orderTickets != null) {
            return orderTickets;
        }
        return null;
    }

    /**
     * Getter method to get all pending order tickets.
     *
     * @return the list of pending orders.
     */
    public List<Order> getPendingOrderTickets() {
        // To code
        return orderTickets;
    }

    /**
     * This method adds a new order ticket to the kitchen.
     *
     * @param newOrder - New order ticket
     * @return true if the ticket was added successfully.
     */
    public boolean addNewOrderTicket(Order newOrder) {

        orderTickets.add(newOrder);
        return true;
    }

    /**
     * Getter method to get orders by date.
     *
     * @param searchDate - the date to match.
     * @return the list of all orders made on that date.
     */
    public List<Order> getOrderListByDate(LocalDate searchDate) {
        // to code
        return orderTickets;
    }

    /**
     * Getter method to get order by date range.
     *
     * @param dateFrom - the start date to search from.
     * @param dateTo   - the end date to end the search.
     * @return the list of orders made between two dates, inclusive.
     */
    public List<Order> getOrderListByDateRange(LocalDate dateFrom,
                                               LocalDate dateTo) {
        // to code
        return orderTickets;
    }

    /**
     * Getter method to get orders made on a date and search by time range.
     *
     * @param searchDate - the date orders are made.
     * @param startTime  - the start time to search orders.
     * @param endTime    - the end time to search orders.
     * @return the list of orders made on a date and between two order time,
     * inclusive.
     */
    public List<Order> getOrderListByDateAndTimeRange(LocalDate searchDate,
                                                      LocalTime startTime,
                                                      LocalTime endTime) {
        // to code
        return orderTickets;
    }

    /**
     * Getter method to get orders by their status.
     *
     * @param searchStatus - the order status to filter orders.
     * @return - the list of orders matched by their status.
     */
    public List<Order> getOrderListByStatus(String searchStatus) {
        // to code
        return orderTickets;
    }

    /**
     * Getter method to get orders by their type.
     *
     * @param searchType - the order type to filter orders.
     * @return - the list of orders matched by their type.
     */
    public List<Order> getOrderListByOrderType(String searchType) {
        // to code
        return orderTickets;
    }

    /**
     * Getter method to get items most ordered and arranged them in
     * descending order, and then limit that list needed.
     *
     * @param numberLimit - the number of items needed.
     * @return - the list of items most ordered in descending order.
     */
    public List<FoodDrink> getDescendingListOfMostItemOrdered(int numberLimit) {
        // to do
        return null;
    }

    /**
     * Getter method to get items least ordered and arranged them in
     * ascending order, and then limit that list needed.
     *
     * @param numberLimit - the number of items needed.
     * @return - the list of items least ordered in ascending order.
     */
    public List<FoodDrink> getAscendingListOfLeastItemOrdered(int numberLimit) {
        // to do
        return null;
    }

    /**
     * Getter method to get the least popular item ordered.
     *
     * @return the item the least popular ordered.
     */
    public FoodDrink getLeastPopularItemOrdered() {
        // to do
        return null;
    }

    /**
     * Getter method to get the most popular item ordered.
     *
     * @param itemType - the type of item.
     * @return the most popular item ordered by its type.
     */
    public FoodDrink getMostPopularItemByType(String itemType) {
        // to do
        return null;
    }

    /**
     * Getter method to get the daily special item that was ordered the most.
     *
     * @return the daily special item that was ordered the most.
     */
    public FoodDrink getItemAsTheMostDailySpecial() {
        // to do
        return null;
    }

    /**
     * Getter method to get the most active customer.
     *
     * @return the most active customer.
     */
    public Customer getTheMostActiveCustomer() {
        // to do
        return null;
    }


}
