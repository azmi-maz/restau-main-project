package org.group.project.classes;

import java.time.LocalDate;
import java.time.LocalTime;
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
    public Kitchen() {}

    /**
     * Getter method to get all the order tickets ever existed from the kitchen.
     *
     * @return the list of all order tickets ever made.
     */
    public List<Order> getAllOrderTickets() {
        return orderTickets;
    }

    /**
     *
     *
     * @return
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
        // to code
        return true;
    }

    /**
     *
     *
     * @param searchDate
     * @return
     */
    public List<Order> getOrderListByDate(LocalDate searchDate) {
        // to code
        return orderTickets;
    }

    public List<Order> getOrderListByDateRange(LocalDate dateFrom,
                                               LocalDate dateTo) {
        // to code
        return orderTickets;
    }

    public List<Order> getOrderListByDateAndTimeRange(LocalDate searchDate,
                                                      LocalTime startTime,
                                                      LocalTime endTime) {
        // to code
        return orderTickets;
    }

    public List<Order> getOrderListByStatus(String searchStatus) {
        // to code
        return orderTickets;
    }

    public List<Order> getOrderListByOrderType(String searchType) {
        // to code
        return orderTickets;
    }

    public List<FoodDrink> getDescendingListOfMostItemOrdered(int numberLimit) {
        // to do
        return null;
    }

    public List<FoodDrink> getAscendingListOfLeastItemOrdered(int numberLimit) {
        // to do
        return null;
    }

    public FoodDrink getLessPopularItemOrdered() {
        // to do
        return null;
    }

    public FoodDrink getMostPopularItemByType(String itemType) {
        // to do
        return null;
    }

    public FoodDrink getItemAsTheMostDailySpecial() {
        // to do
        return null;
    }

    public Customer getTheMostActiveCustomer() {
        // to do
        return null;
    }











}
