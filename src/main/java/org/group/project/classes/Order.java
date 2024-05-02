package org.group.project.classes;

import org.group.project.classes.auxiliary.DataManager;
import org.group.project.exceptions.TextFileNotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * This class stores order information made by customer or waiter.
 *
 * @author azmi_maz
 */
public class Order implements NotifyAction {
    private static final String ORDER_FILE = "ORDERS";
    private static final String DELIVERY_ORDER = "delivery order";
    private static final String TAKEAWAY_ORDER = "takeaway order";
    private static final String DELIVERY_TYPE = "delivery";
    private static final String TAKEAWAY_TYPE = "takeaway";
    private static final String DINEIN_TYPE = "dinein";
    private static final String STATUS_COLUMN = "orderStatus";
    private static final String PENDING_STATUS = "pending-approval";
    private static final String PENDING_KITCHEN = "pending-kitchen";
    private static final String PENDING_DELIVERY = "pending-delivery";
    private static final String COMPLETED_STATUS = "completed";
    private static final String ITEM_DISPLAY_FORMAT = "%dx %s"
            + System.lineSeparator();
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String TIME_FORMAT = "hh:mm a";
    private User user;
    private int orderId;
    private LocalDate orderDate;
    private LocalTime orderTime;
    private String orderType;
    private String orderStatus;
    private List<FoodDrink> orderedFoodDrinkLists;

    /**
     * The constructor to initiate an order with empty basket.
     *
     * @param orderId     - the order unique id.
     * @param user        - the customer making the order.
     * @param orderDate   - the date of the order.
     * @param orderTime   - the time of the order.
     * @param orderType   - the type of order.
     * @param orderStatus - the initial status when an order is created.
     */
    public Order(int orderId, User user, LocalDate orderDate,
                 LocalTime orderTime,
                 String orderType, String orderStatus) {
        this.orderId = orderId;
        this.user = user;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.orderType = orderType;
        this.orderStatus = orderStatus;
        this.orderedFoodDrinkLists = new ArrayList<>();
    }

    /**
     * This constructor creates an order with updated data from database.
     *
     * @param orderId     - the order unique id.
     * @param user        - the customer making the order.
     * @param orderDate   - the date of the order.
     * @param orderTime   - the time of the order.
     * @param orderType   - the type of order.
     * @param orderStatus - the status of order.
     * @param orderList   - the list of items ordered.
     */
    public Order(int orderId, User user, LocalDate orderDate,
                 LocalTime orderTime,
                 String orderType, String orderStatus,
                 List<FoodDrink> orderList) {
        this.orderId = orderId;
        this.user = user;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.orderType = orderType;
        this.orderStatus = orderStatus;
        this.orderedFoodDrinkLists = new ArrayList<>();
        orderedFoodDrinkLists.addAll(orderList);
    }

    /**
     * This constructor creates a new order made by a user.
     *
     * @param orderType  - the type of order.
     * @param customerId - the customer making the order.
     * @param orderList  - the list of items ordered.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public Order(String orderType,
                 int customerId,
                 List<FoodDrink> orderList)
            throws TextFileNotFoundException {

        Kitchen kitchen = new Kitchen();
        orderId = kitchen.getNewOrderId();
        UserManagement userManagement = new UserManagement();
        user = userManagement.getCustomerById(customerId);
        orderDate = LocalDate.now();
        orderTime = LocalTime.now();
        if (orderType.equalsIgnoreCase(DELIVERY_ORDER)) {
            this.orderType = DELIVERY_TYPE;
            this.orderStatus = PENDING_STATUS;
        } else if (orderType
                .equalsIgnoreCase(TAKEAWAY_ORDER)) {
            this.orderType = TAKEAWAY_TYPE;
            this.orderStatus = PENDING_KITCHEN;
        } else {
            this.orderType = DINEIN_TYPE;
            this.orderStatus = PENDING_KITCHEN;
        }
        this.orderedFoodDrinkLists = new ArrayList<>();
        orderedFoodDrinkLists.addAll(orderList);
    }

    /**
     * Getter method to get the order id.
     *
     * @return the id of the order.
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Getter method to get the customer who made the order.
     *
     * @return the customer.
     */
    public Customer getCustomer() {
        return (Customer) user;
    }

    /**
     * Getter method to get the order date.
     *
     * @return the date of order.
     */
    public LocalDate getOrderDate() {
        return orderDate;
    }

    /**
     * This method gets the notification date in dd/mm/yyyy format.
     *
     * @return the notification date in the desired format.
     */
    public String getOrderDateInFormat() {
        return orderDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    /**
     * Getter method to get the order time.
     *
     * @return the time of order.
     */
    public LocalTime getOrderTime() {
        return orderTime;
    }

    /**
     * This method gets the notification time in hh:mm a format.
     *
     * @return the notification time in the desired format.
     */
    public String getOrderTimeInFormat() {
        return orderTime.format(DateTimeFormatter.ofPattern(TIME_FORMAT));
    }

    /**
     * Getter method to get the order type.
     *
     * @return the type of order.
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * Getter method to get the order status.
     *
     * @return the status of order.
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * Getter method to get list of food drink ordered.
     *
     * @return the list of food drink ordered.
     */
    public List<FoodDrink> getOrderedFoodDrinkLists() {
        return orderedFoodDrinkLists;
    }

    /**
     * This method gets all the ordered items in an order.
     *
     * @return the list of item strings.
     */
    public List<String> getListOfItemNamesInOrderList() {
        List<String> tempList = new ArrayList<>();
        for (FoodDrink item : orderedFoodDrinkLists) {
            for (int i = 0; i < item.getQuantity(); i++) {
                tempList.add(item.getItemName().trim());
            }
        }
        return tempList;
    }

    /**
     * This method adds a food drink to the order list.
     *
     * @param newItem - a new item to be added to the list.
     * @return true if item was added successfully.
     */
    public boolean addItemToOrderList(FoodDrink newItem) {
        orderedFoodDrinkLists.add(newItem);
        return true;
    }

    /**
     * This method compile same items by updating the item quantity.
     *
     * @return the list of compile items with the quantity correctly updated.
     */
    public List<FoodDrink> compiledOrderList() {
        List<FoodDrink> compiledList = new ArrayList<>();
        for (FoodDrink item : orderedFoodDrinkLists) {
            boolean isNewItem = true;
            for (FoodDrink itemInList : compiledList) {
                if (itemInList.getItemName().equalsIgnoreCase(
                        item.getItemName())) {
                    itemInList.incrementQuantity();
                    isNewItem = false;
                }
            }
            if (isNewItem) {
                compiledList.add(item);
            }
        }
        return compiledList;
    }

    /**
     * This method displays the list of ordered item in table view format.
     *
     * @return the string format suitable for table view.
     */
    public String getListOfItemsForDisplay() {
        String displayList = "";
        List<FoodDrink> compiledList = compiledOrderList();
        for (FoodDrink item : compiledList) {
            displayList += String.format(
                    ITEM_DISPLAY_FORMAT,
                    item.getQuantity(),
                    item.getItemNameForDisplay()
            );
        }
        return displayList;
    }

    /**
     * This method updates an order status based on the user.
     *
     * @return true if the status update was made successfully.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public boolean markOffOrderAsComplete()
            throws TextFileNotFoundException {
        try {
            if (orderType.equalsIgnoreCase(TAKEAWAY_TYPE)
                    || orderType.equalsIgnoreCase(DINEIN_TYPE)) {
                return DataManager.editColumnDataByUniqueId(ORDER_FILE,
                        orderId, STATUS_COLUMN,
                        COMPLETED_STATUS);
            } else if (orderType.equalsIgnoreCase(DELIVERY_TYPE)) {
                return DataManager.editColumnDataByUniqueId(ORDER_FILE,
                        orderId, STATUS_COLUMN,
                        PENDING_DELIVERY);
            }
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
        return false;
    }

}
