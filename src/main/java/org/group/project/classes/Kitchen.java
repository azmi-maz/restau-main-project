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
    private static final String ORDER_FILE = "ORDERS";
    private static final String USER_ID_COLUMN = "userId";
    private static final String ORDER_DATE_COLUMN = "orderDate";
    private static final String ORDER_TIME_COLUMN = "orderTime";
    private static final String ORDER_TYPE_COLUMN = "orderType";
    private static final String ORDER_STATUS_COLUMN = "orderStatus";
    private static final String ORDER_LISTS_COLUMN = "orderedLists";
    private static final String DELIVERY_TYPE = "delivery";
    private static final String DELIVERY_TIME_COLUMN = "deliveryTime";
    private static final String ASSIGNED_DRIVER_COLUMN = "assignedDriver";
    private static final String TAKEAWAY_TYPE = "takeaway";
    private static final String PICKUP_TIME_COLUMN = "estimatedPickupTime";
    private static final String DATE_FORMAT_DATABASE = "yyyy-M-d";
    private static final String TIME_FORMAT_DATABASE = "H-m";
    private static final String PENDING_STATUS = "pending-approval";
    private static final String PENDING_KITCHEN = "pending-kitchen";
    private static final String PENDING_DELIVERY = "pending-delivery";
    private static final String DELIVERY_ORDER_CLASS = "deliveryorder";
    private static final List<String> orderTypes =
            new ArrayList<>(Arrays.asList(
                    "Delivery Order",
                    "Takeaway Order"
            ));
    private List<Order> orderTickets;

    /**
     * This constructor sets up the kitchen and updates its data
     * from the database.
     *
     * @throws TextFileNotFoundException - if text file is non-existent
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
    public List<Order> getOrderListByDateAndTimeRange(
            LocalDate searchDate,
            LocalTime startTime,
            LocalTime endTime) {
        return orderTickets;
    }

    /**
     * Getter method to get orders by their status.
     *
     * @param searchStatus - the order status to filter orders.
     * @return - the list of orders matched by their status.
     */
    public List<Order> getOrderListByStatus(String searchStatus) {
        return orderTickets;
    }

    /**
     * Getter method to get orders by their type.
     *
     * @param searchType - the order type to filter orders.
     * @return - the list of orders matched by their type.
     */
    public List<Order> getOrderListByOrderType(String searchType) {
        return orderTickets;
    }

    /**
     * Getter method to get items most ordered and arranged them in
     * descending order, and then limit that list needed.
     *
     * @param numberLimit - the number of items needed.
     * @return - the list of items most ordered in descending order.
     */
    public List<FoodDrink> getDescendingListOfMostItemOrdered(
            int numberLimit) {
        return null;
    }

    /**
     * Getter method to get items least ordered and arranged them in
     * ascending order, and then limit that list needed.
     *
     * @param numberLimit - the number of items needed.
     * @return - the list of items least ordered in ascending order.
     */
    public List<FoodDrink> getAscendingListOfLeastItemOrdered(
            int numberLimit) {
        return null;
    }

    /**
     * Getter method to get the least popular item ordered.
     *
     * @return the item the least popular ordered.
     */
    public FoodDrink getLeastPopularItemOrdered() {
        return null;
    }

    /**
     * Getter method to get the most popular item ordered.
     *
     * @param itemType - the type of item.
     * @return the most popular item ordered by its type.
     */
    public FoodDrink getMostPopularItemByType(String itemType) {
        return null;
    }

    /**
     * Getter method to get the daily special item that was ordered the most.
     *
     * @return the daily special item that was ordered the most.
     */
    public FoodDrink getItemAsTheMostDailySpecial() {
        return null;
    }

    /**
     * Getter method to get the most active customer.
     *
     * @return the most active customer.
     */
    public Customer getTheMostActiveCustomer() {
        return null;
    }

    /**
     * This method creates a new order made by a customer or waiter.
     *
     * @param orderType  - the type of order.
     * @param customerId - the id of the customer.
     * @param orderList  - the list of food/drink items in the order.
     * @return the new order made.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
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

    /**
     * This method gets a unique id for a new order.
     *
     * @return - a unique id.
     */
    public int getNewOrderId() {

        Order lastOrder = orderTickets.getLast();
        int lastOrderId = lastOrder.getOrderId();
        lastOrderId++;
        return lastOrderId;
    }

    /**
     * This method gets all order data from database.
     *
     * @return list of all the orders made.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public List<Order> getOrderDataFromDatabase()
            throws TextFileNotFoundException {

        try {

            List<Order> orderList = new ArrayList<>();
            List<String> allOrdersFromDatabase = DataManager
                    .allDataFromFile(ORDER_FILE);
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

    /**
     * This method gets an order from the data in string format.
     *
     * @param userManagement - the Class that handles customer information.
     * @param menu           - the Class that gets the food/drink object
     *                       from string.
     * @param order          - the order data in string format.
     * @return the order with updated data.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
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
                            ORDER_FILE
                    )));

            // user
            int customerId = Integer.parseInt(orderDetails.get(
                    DataFileStructure.getIndexByColName(
                            ORDER_FILE, USER_ID_COLUMN)));
            Customer customer = userManagement.getCustomerById(
                    customerId
            );

            // orderDate
            LocalDate orderDate = getLocalDateFromString(
                    orderDetails.get(DataFileStructure
                            .getIndexByColName(
                                    ORDER_FILE,
                                    ORDER_DATE_COLUMN
                            ))
            );

            // orderTime
            LocalTime orderTime = getLocalTimeFromString(
                    orderDetails.get(DataFileStructure
                            .getIndexByColName(
                                    ORDER_FILE,
                                    ORDER_TIME_COLUMN
                            ))
            );

            // orderType
            String orderType =
                    orderDetails.get(DataFileStructure.getIndexByColName(
                            ORDER_FILE, ORDER_TYPE_COLUMN));

            // orderStatus
            String orderStatus =
                    orderDetails.get(DataFileStructure.getIndexByColName(
                            ORDER_FILE, ORDER_STATUS_COLUMN));

            // orderedFoodDrinkLists
            String[] orderListArray =
                    orderDetails.get(DataFileStructure.getIndexByColName(
                                    ORDER_FILE, ORDER_LISTS_COLUMN))
                            .split(";");
            List<FoodDrink> orderFoodDrinkList = new ArrayList<>();
            getMenuItemFromString(
                    menu,
                    orderListArray,
                    orderFoodDrinkList
            );

            // assignedDriver and deliveryTime
            LocalTime deliveryTime = null;
            Driver assignedDriver = null;

            if (orderType.equalsIgnoreCase(DELIVERY_TYPE)) {

                deliveryTime = getLocalTimeFromString(
                        orderDetails.get(DataFileStructure
                                .getIndexByColName(
                                        ORDER_FILE,
                                        DELIVERY_TIME_COLUMN
                                ))
                );

                String driverId = orderDetails.get(
                        DataFileStructure.getIndexByColName(
                                ORDER_FILE, ASSIGNED_DRIVER_COLUMN));
                if (!driverId.isBlank()) {
                    assignedDriver = userManagement.getDriverById(
                            Integer.parseInt(driverId)
                    );
                }
            }

            // estimatedPickupTime
            LocalTime pickupTime = null;

            if (orderType.equalsIgnoreCase(TAKEAWAY_TYPE)) {
                pickupTime = getLocalTimeFromString(
                        orderDetails.get(DataFileStructure
                                .getIndexByColName(
                                        ORDER_FILE,
                                        PICKUP_TIME_COLUMN
                                ))
                );
            }

            // Based on the order type, this creates different type accordingly.
            if (orderType.equalsIgnoreCase(DELIVERY_TYPE)) {
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

            } else if (orderType.equalsIgnoreCase(TAKEAWAY_TYPE)) {
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

    /**
     * This method gets the LocalDate from a given string.
     *
     * @param orderDate - the order date in string format.
     * @return the LocalDate of that order date.
     */
    public LocalDate getLocalDateFromString(
            String orderDate
    ) {
        List<String> orderDateDetails =
                List.of(orderDate.split("-"));
        return LocalDate.of(Integer.parseInt(orderDateDetails.get(0)),
                Integer.parseInt(orderDateDetails.get(1)),
                Integer.parseInt(orderDateDetails.get(2)));
    }

    /**
     * This method gets the LocalTime from a given string.
     *
     * @param orderTime - the order time in string format.
     * @return the LocalTime of that order time.
     */
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

    /**
     * This method gets all the food/drink items from an array of strings.
     *
     * @param menu               - the Class that handles menu items.
     * @param orderListArray     - the list of food/drink item strings.
     * @param orderFoodDrinkList - the output list of food/drink items
     */
    public void getMenuItemFromString(
            Menu menu,
            String[] orderListArray,
            List<FoodDrink> orderFoodDrinkList
    ) {
        for (String item : orderListArray) {

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

    /**
     * This method adds a new order to the database.
     *
     * @param newOrder - the new order made.
     * @return true if the order was added successfully.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public boolean addOrderToDatabase(
            Order newOrder
    ) throws TextFileNotFoundException {
        String orderId = String.valueOf(newOrder.getOrderId());
        String userId = String.valueOf(
                newOrder.getCustomer().getCustomerId());
        String orderDate = newOrder.getOrderDate()
                .format(DateTimeFormatter.ofPattern(DATE_FORMAT_DATABASE));
        String orderTime =
                newOrder.getOrderTime()
                        .format(DateTimeFormatter
                                .ofPattern(TIME_FORMAT_DATABASE));
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
            return DataManager.appendDataToFile(ORDER_FILE,
                    newOrderForDatabase);
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method gets all the delivery orders pending for waiters approval.
     *
     * @param data - the table view list to be updated.
     */
    public void getPendingApprovalOrderData(
            ObservableList<DeliveryOrder> data
    ) {

        for (Order order : orderTickets) {
            if (isDeliveryOrderClass(order)
                    && order.getOrderStatus()
                    .equalsIgnoreCase(PENDING_STATUS)) {
                data.add((DeliveryOrder) order);
            }
        }
    }

    /**
     * This method gets all the pending orders for chef to cook and prepare.
     *
     * @param data - the table view list to be updated.
     */
    public void getPendingKitchenOrderData(
            ObservableList<Order> data
    ) {

        for (Order order : orderTickets) {
            if (order.getOrderStatus()
                    .equalsIgnoreCase(PENDING_KITCHEN)) {
                data.add(order);
            }
        }

    }

    /**
     * This method gets all the pending delivery orders by driver id.
     *
     * @param userId - the id of the driver.
     * @return all the pending delivery orders for the specific driver.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
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
                            .equalsIgnoreCase(PENDING_DELIVERY)) {
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

    /**
     * This method populates a table view list with all the pending delivery
     * orders by driver id.
     *
     * @param data     - the table view list to be updated.
     * @param driverId - the id of the driver.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
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
                                .equalsIgnoreCase(
                                        PENDING_DELIVERY)) {
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

    /**
     * This method checks if a class is a delivery order or not.
     *
     * @param order - the order being checked.
     * @return true if the order is a delivery order.
     */
    public boolean isDeliveryOrderClass(Order order) {
        Object classType = order.getClass();
        String objectType = List.of(classType.toString()
                .split("\\.")).getLast();
        if (objectType.equalsIgnoreCase(DELIVERY_ORDER_CLASS)) {
            return true;
        }
        return false;
    }

    /**
     * This method populates a table view list with all the orders made
     * by a specific customer.
     *
     * @param data   - the table view list to be updated.
     * @param userId - the id of the customer.
     */
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

    /**
     * This method populates a choice box with the order types for
     * user to select from.
     *
     * @param choiceBox - the choice box to be updated.
     */
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
     * This method formats address for users to read.
     *
     * @param address - the formatted address taken from database.
     * @return an address with the correct format.
     */
    public String formatAddressToRead(String address) {
        return address.replaceAll(";", ",");
    }

}
