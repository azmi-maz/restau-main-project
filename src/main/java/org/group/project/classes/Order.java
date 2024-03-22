package org.group.project.classes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class stores order information made to the restaurant.
 *
 * @author azmi_maz
 */
public class Order {
    private User user;
    private LocalDate orderDate;
    private LocalTime orderTime;
    private String orderType;
    private String orderStatus;
    private List<FoodDrink> orderedFoodDrinkLists;

    /**
     * The constructor to initiate an order with empty basket.
     *
     * @param user        - the customer or waiter making an order.
     * @param orderDate   - the date of the order.
     * @param orderTime   - the time of the order.
     * @param orderType   - the type of order.
     * @param orderStatus - the initial status when an order is created.
     */
    public Order(User user, LocalDate orderDate, LocalTime orderTime,
                 String orderType, String orderStatus) {
        this.user = user;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.orderType = orderType;
        this.orderStatus = orderStatus;
        this.orderedFoodDrinkLists = new ArrayList<FoodDrink>();
    }

    /**
     * Getter method to get the user who made the order
     *
     * @return User as either customer or waiter, depends on the order.
     */
    public User getUser() {
        return user;
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
     * Getter method to get the order time.
     *
     * @return the time of order.
     */
    public LocalTime getOrderTime() {
        return orderTime;
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
     * This method adds a food drink to the order list.
     *
     * @param newItem - a new item to be added to the list.
     * @return true if item was added successfully.
     */
    public boolean addItemToOrderList(FoodDrink newItem) {
        orderedFoodDrinkLists.add(newItem);
        return true;
    }
}
