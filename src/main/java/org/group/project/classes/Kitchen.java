package org.group.project.classes;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import org.group.project.classes.auxiliary.DataFileStructure;
import org.group.project.classes.auxiliary.DataManager;
import org.group.project.exceptions.TextFileNotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class handles all the orders made by the customer and waiter.
 *
 * @author azmi_maz
 */
public class Kitchen {
    private List<Order> orderTickets;
    private static final List<String> orderTypes =
            new ArrayList<>(Arrays.asList(
                    "Delivery Order",
                    "Takeaway Order"
            ));

    /**
     * This constructor is default without any parameters.
     */
    public Kitchen() throws TextFileNotFoundException {

        orderTickets = new ArrayList<>();

        try {
            orderTickets = getOrderDataFromDatabase();
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
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
    public Order createNewOrder(
            String orderType,
            int customerId,
            List<FoodDrink> orderList
    ) throws TextFileNotFoundException {
        try {
            return new Order(
                    orderType,
                    customerId,
                    orderList
            );
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // TODO
    public int getNewOrderId() {

        Order lastOrder = orderTickets.getLast();
        int lastOrderId = lastOrder.getOrderId();
        lastOrderId++;
        return lastOrderId;
    }

    // TODO
    public List<Order> getOrderDataFromDatabase()
            throws TextFileNotFoundException {

        try {

            List<Order> orderList = new ArrayList<>();
            List<String> allOrdersFromDatabase = DataManager
                    .allDataFromFile("ORDERS");
            UserManagement userManagement = new UserManagement();
            Menu menu = new Menu();

            for (String order : allOrdersFromDatabase) {
                orderList.add(
                        getOrderFromString(
                                userManagement,
                                menu,
                                order
                        )
                );
            }
            return orderList;

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }

    }

    // TODO
    public Order getOrderFromString(
            UserManagement userManagement,
            Menu menu,
            String order
    ) throws TextFileNotFoundException {
        try {

            List<String> orderDetails = List.of(order.split(","));

            // orderId
            int orderId = Integer.parseInt(orderDetails
                    .get(DataFileStructure.getIndexColOfUniqueId(
                            "ORDERS"
                    )));

            // user
            int customerId = Integer.parseInt(orderDetails.get(
                    DataFileStructure.getIndexByColName(
                            "ORDERS", "userId")));
            Customer customer = userManagement.getCustomerById(
                    customerId
            );

            // orderDate
            LocalDate orderDate = getLocalDateFromString(
                    orderDetails.get(DataFileStructure
                            .getIndexByColName(
                                    "ORDERS",
                                    "orderDate"
                            ))
            );

            // orderTime
            LocalTime orderTime = getLocalTimeFromString(
                    orderDetails.get(DataFileStructure
                            .getIndexByColName(
                                    "ORDERS",
                                    "orderTime"
                            ))
            );

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
            getMenuItemFromString(
                    menu,
                    orderListArray,
                    orderFoodDrinkList
            );

            // assignedDriver and deliveryTime
            LocalTime deliveryTime = null;
            Driver assignedDriver = null;

            if (orderType.equalsIgnoreCase("delivery")) {

                deliveryTime = getLocalTimeFromString(
                        orderDetails.get(DataFileStructure
                                .getIndexByColName(
                                        "ORDERS",
                                        "deliveryTime"
                                ))
                );

                String driverId = orderDetails.get(
                        DataFileStructure.getIndexByColName(
                                "ORDERS", "assignedDriver"));
                if (!driverId.isBlank()) {
                    assignedDriver = userManagement.getDriverById(
                            Integer.parseInt(driverId)
                    );
                }
            }

            // estimatedPickupTime
            LocalTime pickupTime = null;

            if (orderType.equalsIgnoreCase("takeaway")) {
                pickupTime = getLocalTimeFromString(
                        orderDetails.get(DataFileStructure
                                .getIndexByColName(
                                        "ORDERS",
                                        "estimatedPickupTime"
                                ))
                );
            }

            // TODO
            if (orderType.equalsIgnoreCase("delivery")) {
                return new DeliveryOrder(
                        orderId,
                        customer,
                        orderDate,
                        orderTime,
                        deliveryTime,
                        orderStatus,
                        assignedDriver,
                        orderFoodDrinkList
                );

            } else if (orderType.equalsIgnoreCase("takeaway")) {
                return new TakeawayOrder(
                        orderId,
                        customer,
                        orderDate,
                        orderTime,
                        pickupTime,
                        orderStatus,
                        orderFoodDrinkList
                );

            } else {
                return new Order(
                        orderId,
                        customer,
                        orderDate,
                        orderTime,
                        orderType,
                        orderStatus,
                        orderFoodDrinkList
                );

            }

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // TODO
    public LocalDate getLocalDateFromString(
            String orderDate
    ) {
        List<String> orderDateDetails =
                List.of(orderDate.split("-"));
        return LocalDate.of(Integer.parseInt(orderDateDetails.get(0)),
                Integer.parseInt(orderDateDetails.get(1)),
                Integer.parseInt(orderDateDetails.get(2)));
    }

    // TODO
    public LocalTime getLocalTimeFromString(
            String orderTime
    ) {
        if (!orderTime.isBlank()) {
            List<String> orderTimeDetails =
                    List.of(orderTime.split("-"));
            return LocalTime.of(Integer.parseInt(orderTimeDetails.get(0)),
                    Integer.parseInt(orderTimeDetails.get(1)));
        }
        return null;
    }

    // TODO
    public void getMenuItemFromString(
            Menu menu,
            String[] orderListArray,
            List<FoodDrink> orderFoodDrinkList
    ) {
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
    }

    // TODO
    public boolean addOrderToDatabase(
            Order newOrder
    ) throws TextFileNotFoundException {
        String orderId = String.valueOf(newOrder.getOrderId());
        String userId = String.valueOf(newOrder.getCustomer().getCustomerId());
        String orderDate = newOrder.getOrderDate()
                .format(DateTimeFormatter.ofPattern("yyyy-M-d"));
        String orderTime =
                newOrder.getOrderTime()
                        .format(DateTimeFormatter.ofPattern("H-m"));
        String orderType = newOrder.getOrderType();
        String orderStatus = newOrder.getOrderStatus();
        String estimatedPickupTime = "";
        String assignedDriver = "";
        String deliveryTime = "";
        String orderLists = DataManager.formatLongArrayToOneColumnString(
                newOrder.getListOfItemNamesInOrderList());

        List<String> newOrderForDatabase = new ArrayList<>(Arrays.asList(
                orderId,
                userId,
                orderDate,
                orderTime,
                orderType,
                orderStatus,
                estimatedPickupTime,
                assignedDriver,
                deliveryTime,
                orderLists
        ));

        try {
            return DataManager.appendDataToFile("ORDERS",
                    newOrderForDatabase);
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // TODO comment
    public void getPendingApprovalOrderData(
            ObservableList<DeliveryOrder> data
    ) {

        // TODO to filter
        for (Order order : orderTickets) {

            // TODO
            if (isDeliveryOrderClass(order)
                    && order.getOrderStatus()
                    .equalsIgnoreCase("pending-approval")) {
                data.add((DeliveryOrder) order);
            }
        }
    }

    // TODO comment
    public void getPendingKitchenOrderData(
            ObservableList<Order> data
    ) {

        for (Order order : orderTickets) {
            if (order.getOrderStatus()
                    .equalsIgnoreCase("pending-kitchen")) {
                data.add(order);
            }
        }

    }

    // TODO comment
    public List<DeliveryOrder> getPendingDeliveryDataByDriverId(
            int userId
    ) throws TextFileNotFoundException {
        try {

            List<DeliveryOrder> pendingDeliveryData = new ArrayList<>();
            for (Order order : orderTickets) {

                // TODO pending delivery by driver id
                if (isDeliveryOrderClass(order)) {
                    DeliveryOrder deliveryOrder = (DeliveryOrder) order;

                    if (deliveryOrder.getOrderStatus()
                            .equalsIgnoreCase("pending-delivery")) {
                        int driverId = 0;
                        driverId = deliveryOrder
                                .getDriver().getUserId();
                        if (driverId == userId) {
                            pendingDeliveryData.add((DeliveryOrder) order);
                        }
                    }
                }
            }
            return pendingDeliveryData;

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // TODO comment
    public void getPendingDeliveryDataByDriverId(
            ObservableList<DeliveryOrder> data,
            int driverId
    ) throws TextFileNotFoundException {

        try {

            for (Order order : orderTickets) {

                if (isDeliveryOrderClass(order)) {
                    DeliveryOrder deliveryOrder = (DeliveryOrder) order;
                    Driver driver = deliveryOrder.getDriver();
                    if (driver != null) {
                        if (driver.getUserId() == driverId
                                && deliveryOrder.getOrderStatus()
                                .equalsIgnoreCase("pending-delivery")) {
                            data.add(deliveryOrder);
                        }
                    }
                }
            }

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // TODO
    public boolean isDeliveryOrderClass(Order order) {
        Object classType = order.getClass();
        String objectType = List.of(classType.toString().split("\\.")).getLast();
        if (objectType.equalsIgnoreCase("deliveryorder")) {
            return true;
        }
        return false;
    }

    // TODO comment
    public void getOrderDataByUserId(
            ObservableList<Order> data,
            int userId
    ) {

        for (Order order : orderTickets) {
            Customer customer = order.getCustomer();
            if (customer != null
                    && customer.getCustomerId() == userId) {
                data.add(order);
            }
        }
    }

    // TODO
    public void updateOrderTypeChoiceBox(
            ChoiceBox<String> choiceBox
    ) {
        for (String orderType : orderTypes) {
            choiceBox.getItems()
                    .add(orderType);
        }
        choiceBox.setValue(orderTypes.getFirst());
    }

    /**
     * This method formats address to transform any symbols compatible for
     * users to read.
     *
     * @param address - the formatted address taken from database.
     * @return an address with the correct format.
     */
    public String formatAddressToRead(String address) {
        return address.replaceAll(";", ",");
    }

}
