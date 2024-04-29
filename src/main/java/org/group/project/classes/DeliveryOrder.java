package org.group.project.classes;

import org.group.project.classes.auxiliary.DataManager;
import org.group.project.exceptions.TextFileNotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * This class handles delivery orders.
 *
 * @author azmi_maz
 */
public class DeliveryOrder extends Order implements NotifyAction {
    private Driver assignedDriver;
    private String customerAddress;
    private LocalTime deliveryTime;

    /**
     * The constructor to initiate a delivery order with empty basket.
     *
     * @param orderId   - the unique id.
     * @param customer  - the customer who is making the order.
     * @param orderDate - the date of the order.
     * @param orderTime - the time of the order.
     */
    public DeliveryOrder(int orderId, Customer customer, LocalDate orderDate,
                         LocalTime orderTime) {
        super(orderId, customer, orderDate, orderTime, "delivery", "pending-approval");
        customerAddress = customer.getDeliveryAddress();
        // TODO review this is needed or not
        // Default 30 mins for now - restaurant policy to deliver within 30
        // mins.
//        deliveryTime = orderTime.plusMinutes(30);
    }

    // TODO comment to get updated data
    public DeliveryOrder(int orderId, Customer customer, LocalDate orderDate,
                         LocalTime orderTime, LocalTime deliveryTime,
                         String orderStatus, Driver assignedDriver,
                         List<FoodDrink> orderedList) {
        super(orderId, customer, orderDate, orderTime, "delivery", orderStatus, orderedList);
        customerAddress = customer.getDeliveryAddress();
        this.deliveryTime = deliveryTime;
        this.assignedDriver = assignedDriver;
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

    // TODO comment
    public String getDeliveryTimeInFormat() {
        return deliveryTime.format(DateTimeFormatter.ofPattern("hh:mm a"));
    }

    // TODO comment
    public String getEstimatedDeliveryTimeForDatabase() {
        // TODO final variable for added time 30 minutes
        LocalTime timeOfApproval = LocalTime.now();
        LocalTime estimatedDeliveryTime = timeOfApproval.plusMinutes(30);
        deliveryTime = estimatedDeliveryTime;
        // TODO note H-m if it can be applied elsewhere
        return estimatedDeliveryTime
                .format(DateTimeFormatter.ofPattern("H-m"));
    }

    // TODO comment
    public void cancelDeliveryOrder() throws TextFileNotFoundException {
        int orderId = super.getOrderId();
        try {
            DataManager.editColumnDataByUniqueId("ORDERS",
                    orderId, "orderStatus",
                    "failed");
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // TODO comment
    public void approveDeliveryOrder(
            int driverId
    ) throws TextFileNotFoundException {
        int orderId = super.getOrderId();
        try {
            DataManager.editColumnDataByUniqueId("ORDERS",
                    orderId, "orderStatus",
                    "pending-kitchen");
            DataManager.editColumnDataByUniqueId("ORDERS",
                    orderId, "assignedDriver",
                    String.valueOf(driverId));
            DataManager.editColumnDataByUniqueId("ORDERS",
                    orderId, "deliveryTime",
                    getEstimatedDeliveryTimeForDatabase());
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // TODO comment
    public boolean confirmDeliveryOrder()
            throws TextFileNotFoundException {
        int orderId = super.getOrderId();
        boolean isSuccessful = false;
        try {
            isSuccessful = DataManager.editColumnDataByUniqueId("ORDERS",
                    orderId, "orderStatus",
                    "completed");
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
        return isSuccessful;
    }

    // TODO comment
    public String approveDeliveryMessage() {
        return String.format(
                "Your order no.%d is on the way. Estimated time of delivery" +
                        " is %s.",
                super.getOrderId(),
                getDeliveryTimeInFormat()
        );
    }

    // TODO comment
    public String cancelDeliveryMessage() {
        return String.format(
                "We're sorry. Your delivery order no.%d was cancelled " +
                        "due to heavy demand in delivery orders. " +
                        "Please try again later.",
                super.getOrderId()
        );
    }

    // TODO comment
    public String confirmDeliveryMessage() {
        return String.format(
                "Your order no.%d was delivered successfully. " +
                        "Enjoy your meal!",
                super.getOrderId()
        );
    }

    @Override
    public void notifyCustomer(Customer customer,
                               boolean isSuccessfulRequest)
            throws TextFileNotFoundException {

        try {

            NotificationBoard notificationBoard = new NotificationBoard();
            String notificationType = "delivery";
            Notification newNotification = notificationBoard.createNewNotification(
                    customer.getCustomerId(),
                    notificationType
            );

            if (isSuccessfulRequest) {
                if (super.getOrderStatus().equalsIgnoreCase("pending-approval")) {
                    newNotification.setMessageBody(
                            approveDeliveryMessage()
                    );
                } else {
                    newNotification.setMessageBody(
                            confirmDeliveryMessage()
                    );
                }
            } else {
                newNotification.setMessageBody(
                        cancelDeliveryMessage()
                );
            }

            notificationBoard.addNotificationToDatabase(
                    newNotification
            );

        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
