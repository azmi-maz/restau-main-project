package org.group.project.classes;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * This class handles takeaway orders.
 */
public class TakeawayOrder extends Order {
    private LocalTime estimatedPickupTime;

    /**
     * The constructor to initiate a takeaway order with empty basket.
     *
     * @param customer  - the customer who is making the order.
     * @param orderDate - the date of the order.
     * @param orderTime - the time of the order.
     */
    public TakeawayOrder(Customer customer, LocalDate orderDate,
                         LocalTime orderTime) {
        super(customer, orderDate, orderTime, "takeaway", "pending-kitchen");
        // Default 30 mins for now - restaurant policy to prepare within 30
        // mins.
        estimatedPickupTime = orderTime.plusMinutes(30);
    }

    /**
     * Getter method to get the customer who made the delivery order.
     *
     * @return the customer.
     */
    public Customer getCustomer() {
        return super.getCustomer();
    }

    /**
     * Getter method to get the date of the order.
     *
     * @return the order date.
     */
    public LocalDate getOrderDate() {
        return super.getOrderDate();
    }

    /**
     * Getter method to get the time of the order.
     *
     * @return the order time.
     */
    public LocalTime getOrderTime() {
        return super.getOrderTime();
    }

    /**
     * Getter method to get the type of the order.
     *
     * @return the order type.
     */
    public String getOrderType() {
        return super.getOrderType();
    }

    /**
     * Getter method to get the status of the order.
     *
     * @return the order status.
     */
    public String getOrderStatus() {
        return super.getOrderStatus();
    }

    /**
     * This method adds pickup time in minutes.
     *
     * @param addTimeInMinutes - the time in minutes.
     * @return the new pickup time for UI.
     */
    public LocalTime addPickupTime(int addTimeInMinutes) {
        estimatedPickupTime = estimatedPickupTime.plusMinutes(addTimeInMinutes);
        return estimatedPickupTime;
    }

    /**
     * Getter method to get the pickup time.
     *
     * @return the pickup time.
     */
    public LocalTime getPickupTime() {
        return estimatedPickupTime;
    }
}
