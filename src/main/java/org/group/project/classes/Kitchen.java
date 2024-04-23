package org.group.project.classes;

import javafx.collections.ObservableList;
import org.group.project.classes.auxiliary.DataFileStructure;
import org.group.project.classes.auxiliary.DataManager;
import org.group.project.classes.auxiliary.HelperMethods;

import java.io.FileNotFoundException;
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

        try {
            orderTickets = getOrderDataFromDatabase();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

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

    // TODO
    public List<Order> getOrderDataFromDatabase() throws FileNotFoundException {

        List<Order> orderList = new ArrayList<>();
        List<String> allOrdersFromDatabase = DataManager.allDataFromFile("ORDERS");

        for (String order : allOrdersFromDatabase) {
            List<String> orderDetails = List.of(order.split(","));
            // orderId
            int orderId = Integer.parseInt(orderDetails.get(DataFileStructure.getIndexByColName("ORDERS", "orderId")));

            // user
            Customer customer;

            // TODO
            List<String> customerString = HelperMethods.getDataById("USERS",
                    orderDetails.get(DataFileStructure.getIndexByColName(
                            "BOOKINGS", "userId")));

            // orderDate
            List<String> orderDateDetails = List.of(orderDetails.get(2).split("-"));

            LocalDate orderDate =
                    LocalDate.of(Integer.parseInt(orderDateDetails.get(0)),
                            Integer.parseInt(orderDateDetails.get(1)),
                            Integer.parseInt(orderDateDetails.get(2)));

            // orderTime
            List<String> orderTimeDetails = List.of(orderDetails.get(3).split("-"));

            LocalTime orderTime =
                    LocalTime.of(Integer.parseInt(orderTimeDetails.get(0)),
                            Integer.parseInt(orderTimeDetails.get(1)));

            // orderType
            String orderType =
                    orderDetails.get(DataFileStructure.getIndexByColName(
                            "ORDERS", "orderType"));

            // orderStatus
            String orderStatus =
                    orderDetails.get(DataFileStructure.getIndexByColName(
                            "ORDERS", "orderStatus"));

            // orderedFoodDrinkLists
            String[] orderListArray =
                    orderDetails.get(DataFileStructure.getIndexByColName(
                            "ORDERS", "orderedLists")).split(";");
            List<FoodDrink> orderFoodDrinkList = new ArrayList<>();
            Menu menu = new Menu();
            for (String item : orderListArray) {
                // TODO find item type by item name
                String itemType = menu.findTypeByItemName(
                        item
                );
                FoodDrink newItem = new FoodDrink(item, itemType);
                boolean isNewItem = true;
                for (FoodDrink currentItem : orderFoodDrinkList) {
                    if (currentItem.getItemName().equalsIgnoreCase(item)) {
                        currentItem.incrementQuantity();
                        isNewItem = false;
                    }
                }
                if (isNewItem) {
                    orderFoodDrinkList.add(newItem);
                }
            }

            // assignedDriver and deliveryTime
            LocalTime deliveryTime = null;
            Driver assignedDriver = null;

            if (orderType.equalsIgnoreCase("delivery")) {
                List<String> deliveryTimeDetails =
                        List.of(orderDetails.get(DataFileStructure.getIndexByColName(
                                "ORDERS", "deliveryTime")).split(
                                "-"));
                if (deliveryTimeDetails.size() == 2) {
                    deliveryTime = LocalTime.of(Integer.parseInt(deliveryTimeDetails.get(0)),
                            Integer.parseInt(deliveryTimeDetails.get(1)));
                }
                List<String> driverString = HelperMethods.getDataById("USERS",
                        orderDetails.get(DataFileStructure.getIndexByColName(
                                "ORDERS", "assignedDriver")));

                if (driverString != null) {
                    assignedDriver = new Driver(
                            Integer.parseInt(driverString.get(DataFileStructure.getIndexByColName(
                                    "USERS", "userId"))),
                            driverString.get(DataFileStructure.getIndexByColName(
                                    "USERS", "firstName")),
                            driverString.get(DataFileStructure.getIndexByColName(
                                    "USERS", "lastName")),
                            driverString.get(DataFileStructure.getIndexByColName(
                                    "USERS", "username"))
                    );
                }
            }

            // estimatedPickupTime
            LocalTime pickupTime = null;

            if (orderType.equalsIgnoreCase("takeaway")) {
                List<String> pickupTimeDetails =
                        List.of(orderDetails.get(DataFileStructure.getIndexByColName(
                                "ORDERS", "estimatedPickupTime")).split(
                                "-"));
                if (pickupTimeDetails.size() == 2) {
                    pickupTime =
                            LocalTime.of(Integer.parseInt(pickupTimeDetails.get(0)),
                                    Integer.parseInt(pickupTimeDetails.get(1)));
                }
            }

            // TODO comment here the filter applies to pending-kitchen
            if (customerString != null) {
                customer = new Customer(
                        customerString.get(DataFileStructure.getIndexByColName("USERS", "firstName")),
                        customerString.get(DataFileStructure.getIndexByColName("USERS", "lastName")),
                        customerString.get(DataFileStructure.getIndexByColName("USERS", "username")),
                        Integer.parseInt(customerString.get(DataFileStructure.getIndexByColName("USERS", "userId"))),
                        HelperMethods.formatAddressToRead(customerString.get(DataFileStructure.getIndexByColName("USERS", "address")))
                );
                if (orderType.equalsIgnoreCase("delivery")) {
                    orderList.add(new DeliveryOrder(
                            orderId,
                            customer,
                            orderDate,
                            orderTime,
                            deliveryTime,
                            orderStatus,
                            assignedDriver,
                            orderFoodDrinkList
                    ));

                } else if (orderType.equalsIgnoreCase("takeaway")) {
                    orderList.add(new TakeawayOrder(
                            orderId,
                            customer,
                            orderDate,
                            orderTime,
                            pickupTime,
                            orderStatus,
                            orderFoodDrinkList
                    ));

                } else {
                    orderList.add(new Order(
                            orderId,
                            customer,
                            orderDate,
                            orderTime,
                            orderType,
                            orderStatus,
                            orderFoodDrinkList
                    ));

                }
            }
        }
        return orderList;
    }

    // TODO comment
    public void getPendingOrderData(
            ObservableList<Order> data
    ) throws FileNotFoundException {

        // TODO to filter
        List<Order> orderData = getOrderDataFromDatabase();
        for (Order order : orderData) {

            // TODO
            if (order.getOrderStatus()
                    .equalsIgnoreCase("pending-approval")) {
                data.add(order);
            }
        }
    }


}
