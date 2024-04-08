package org.group.project.classes;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * This class stores order information made to the restaurant.
 *
 * @author azmi_maz
 */
public class Order implements NotifyAction {
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
     * @param user        - the customer or waiter making an order.
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
        this.orderedFoodDrinkLists = new ArrayList<FoodDrink>();
    }

    // TODO comment to get updated data
    public Order(int orderId, User user, LocalDate orderDate,
                 LocalTime orderTime,
                 String orderType, String orderStatus,
                 List<FoodDrink> orderedList) {
        this.orderId = orderId;
        this.user = user;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.orderType = orderType;
        this.orderStatus = orderStatus;
        this.orderedFoodDrinkLists = orderedList;
    }

    // TODO comment
    public int getOrderId() {
        return orderId;
    }

    /**
     * Getter method to get the customer who made the order
     *
     * @return the customer.
     */
    public Customer getCustomer() {
        return (Customer) user;
    }

    /**
     * Getter method to get the waiter who made the order
     *
     * @return the waiter.
     */
    public Waiter getWaiter() {
        return (Waiter) user;
    }

    /**
     * Getter method to get the order date.
     *
     * @return the date of order.
     */
    public LocalDate getOrderDate() {
        return orderDate;
    }

    // TODO comment
    public String getOrderDateInFormat() {
        return orderDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    /**
     * Getter method to get the order time.
     *
     * @return the time of order.
     */
    public LocalTime getOrderTime() {
        return orderTime;
    }

    // TODO comment
    public String getOrderTimeInFormat() {
        return orderTime.format(DateTimeFormatter.ofPattern("hh:mm a"));
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

    // TODO comment
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

    // TODO comment, this method may be used elsewhere to compile orderList, look for isNewItem
    public List<FoodDrink> compiledOrderList() {
        List<FoodDrink> compiledList = new ArrayList<>();
        for (FoodDrink item : orderedFoodDrinkLists) {
            boolean isNewItem = true;
            for (FoodDrink itemInList : compiledList) {
                if (itemInList.getItemName().equalsIgnoreCase(item.getItemName())) {
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

    // TODO comment
    public String getListOfItemsForDisplay() {
        String displayList = "";
        List<FoodDrink> compiledList = compiledOrderList();
        for (FoodDrink item : compiledList) {
            displayList += item.getQuantity()
                    + "x "
                    + item.getItemNameForDisplay()
                    + System.lineSeparator();
        }
        return displayList;
    }

    // TODO comment
    public void markOffOrderAsComplete() throws IOException {
        if (orderType.equalsIgnoreCase("takeaway")
                || orderType.equalsIgnoreCase("dinein")) {
            DataManager.editColumnDataByUniqueId("ORDERS",
                    orderId, "orderStatus",
                    "completed");
        } else if (orderType.equalsIgnoreCase("delivery")) {
            DataManager.editColumnDataByUniqueId("ORDERS",
                    orderId, "orderStatus",
                    "pending-delivery");
        }
    }

}
