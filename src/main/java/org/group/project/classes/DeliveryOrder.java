package org.group.project.classes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * This class handles delivery orders.
 *
 * @author azmi_maz
 */
public class DeliveryOrder extends Order {
    private Driver assignedDriver;
    private String customerAddress;
    private LocalTime deliveryTime;

    /**
     * The constructor to initiate a delivery order with empty basket.
     *
     * @param customer  - the customer who is making the order.
     * @param orderDate - the date of the order.
     * @param orderTime - the time of the order.
     */
    public DeliveryOrder(Customer customer, LocalDate orderDate,
                         LocalTime orderTime) {
        super(customer, orderDate, orderTime, "delivery", "pending-approval");
        customerAddress = customer.getDeliveryAddress();
        // Default 30 mins for now - restaurant policy to deliver within 30
        // mins.
        deliveryTime = orderTime.plusMinutes(30);
    }

    // TODO comment to get updated data
    public DeliveryOrder(Customer customer, LocalDate orderDate,
                         LocalTime orderTime, LocalTime deliveryTime,
                         String orderStatus, Driver assignedDriver,
                         List<FoodDrink> orderedList) {
        super(customer, orderDate, orderTime, "delivery", orderStatus, orderedList);
        customerAddress = customer.getDeliveryAddress();
        this.deliveryTime = deliveryTime;
        this.assignedDriver = assignedDriver;
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
     * Getter method to get the address of the customer.
     *
     * @return the delivery address.
     */
    public String getCustomerAddress() {
        return getCustomer().getDeliveryAddress();
    }

    /**
     * This method assigns a driver to the order for delivery arrangement.
     *
     * @param driver - the driver tasked to deliver the order.
     * @return the driver for the UI.
     */
    public Driver assignDriver(Driver driver) {
        assignedDriver = driver;
        return driver;
    }

    /**
     * Getter method to get the assigned driver.
     *
     * @return the driver.
     */
    public Driver getDriver() {
        return assignedDriver;
    }

    /**
     * This method adds delivery time in minutes.
     *
     * @param addTimeInMinutes - the time in minutes.
     * @return the new delivery time for UI.
     */
    public LocalTime addDeliveryTime(int addTimeInMinutes) {
        deliveryTime = deliveryTime.plusMinutes(addTimeInMinutes);
        return deliveryTime;
    }

    /**
     * Getter method to get the delivery time.
     *
     * @return the delivery time.
     */
    public LocalTime getDeliveryTime() {
        return deliveryTime;
    }
}
