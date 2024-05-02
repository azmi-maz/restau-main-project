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
    private static final String ORDER_FILE = "ORDERS";
    private static final String DELIVERY_TYPE = "delivery";
    private static final String ORDER_STATUS_COLUMN = "orderStatus";
    private static final String ASSIGNED_DRIVER_COLUMN = "assignedDriver";
    private static final String DELIVERY_TIME_COLUMN = "deliveryTime";
    private static final String PENDING_STATUS = "pending-approval";
    private static final String FAILED_STATUS = "failed";
    private static final String COMPLETED_STATUS = "completed";
    private static final String PENDING_KITCHEN_STATUS = "pending-kitchen";
    private static final String TIME_FORMAT = "hh:mm a";
    private static final String TIME_FORMAT_DATABASE = "H-m";
    private static final String CONFIRM_DELIVERY_MESSAGE = "Your order no.%d " +
            "was delivered successfully. " +
            "Enjoy your meal!";
    private static final String CANCEL_DELIVERY_MESSAGE = "We're sorry. " +
            "Your delivery order no.%d was cancelled " +
            "due to heavy demand in delivery orders. " +
            "Please try again later.";
    private static final String APPROVE_DELIVERY_MESSAGE = "Your order no.%d " +
            "is on the way. Estimated time of delivery is %s.";
    private static final int DELIVERY_TIME_ESTIMATE = 30;
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
        super(orderId, customer, orderDate,
                orderTime, DELIVERY_TYPE, PENDING_STATUS);
        customerAddress = customer.getDeliveryAddress();
    }

    /**
     * The constructor to create DeliveryOrder from a database.
     *
     * @param orderId        - the unique id.
     * @param customer       - the customer who is making the order.
     * @param orderDate      - the date of the order.
     * @param orderTime      - the time of the order.
     * @param deliveryTime   - the estimated delivery time.
     * @param orderStatus    - the status of the order.
     * @param assignedDriver - the driver assigned to deliver the order.
     * @param orderedList    - the list of food/drink items ordered.
     */
    public DeliveryOrder(int orderId, Customer customer, LocalDate orderDate,
                         LocalTime orderTime, LocalTime deliveryTime,
                         String orderStatus, Driver assignedDriver,
                         List<FoodDrink> orderedList) {
        super(orderId, customer, orderDate, orderTime,
                DELIVERY_TYPE, orderStatus, orderedList);
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

    /**
     * Getter method to get the delivery time in hh:mm a format.
     *
     * @return the delivery time in the desired format.
     */
    public String getDeliveryTimeInFormat() {
        return deliveryTime.format(DateTimeFormatter.ofPattern(TIME_FORMAT));
    }

    /**
     * This method formats the estimated delivery time in database-friendly
     * format.
     *
     * @return - the estimated delivery time in the desired format.
     */
    public String getEstimatedDeliveryTimeForDatabase() {

        LocalTime timeOfApproval = LocalTime.now();
        LocalTime estimatedDeliveryTime =
                timeOfApproval.plusMinutes(DELIVERY_TIME_ESTIMATE);
        deliveryTime = estimatedDeliveryTime;

        return estimatedDeliveryTime
                .format(DateTimeFormatter.ofPattern(TIME_FORMAT_DATABASE));
    }

    /**
     * This method cancels the delivery order made by customer.
     *
     * @throws TextFileNotFoundException - if the text file is non-existent.
     */
    public void cancelDeliveryOrder() throws TextFileNotFoundException {
        int orderId = super.getOrderId();
        try {
            DataManager.editColumnDataByUniqueId(ORDER_FILE,
                    orderId, ORDER_STATUS_COLUMN,
                    FAILED_STATUS);
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method updates the delivery order status, driver and
     * its estimated delivery time.
     *
     * @param driverId - the id of the assigned driver.
     * @throws TextFileNotFoundException - the text file is non-existent.
     */
    public void approveDeliveryOrder(
            int driverId
    ) throws TextFileNotFoundException {
        int orderId = super.getOrderId();
        try {
            DataManager.editColumnDataByUniqueId(ORDER_FILE,
                    orderId, ORDER_STATUS_COLUMN,
                    PENDING_KITCHEN_STATUS);
            DataManager.editColumnDataByUniqueId(ORDER_FILE,
                    orderId, ASSIGNED_DRIVER_COLUMN,
                    String.valueOf(driverId));
            DataManager.editColumnDataByUniqueId(ORDER_FILE,
                    orderId, DELIVERY_TIME_COLUMN,
                    getEstimatedDeliveryTimeForDatabase());
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This method completes the delivery order by delivering it to the
     * customer successfully.
     *
     * @return true if the status update is made successfully to the database.
     * @throws TextFileNotFoundException - if the text file is non-existent.
     */
    public boolean confirmDeliveryOrder()
            throws TextFileNotFoundException {
        int orderId = super.getOrderId();
        try {
            return DataManager.editColumnDataByUniqueId(ORDER_FILE,
                    orderId, ORDER_STATUS_COLUMN,
                    COMPLETED_STATUS);
        } catch (TextFileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This creates a message to inform delivery order was approved.
     *
     * @return the main message.
     */
    public String approveDeliveryMessage() {
        return String.format(
                APPROVE_DELIVERY_MESSAGE,
                super.getOrderId(),
                getDeliveryTimeInFormat()
        );
    }

    /**
     * This creates a failure message to inform that delivery order was
     * cancelled.
     *
     * @return the main message.
     */
    public String cancelDeliveryMessage() {
        return String.format(
                CANCEL_DELIVERY_MESSAGE,
                super.getOrderId()
        );
    }

    /**
     * This creates a successful message to inform that delivery order
     * was delivered successfully.
     *
     * @return the main message,
     */
    public String confirmDeliveryMessage() {
        return String.format(
                CONFIRM_DELIVERY_MESSAGE,
                super.getOrderId()
        );
    }

    /**
     * This notifies the customer who made the delivery order of any
     * status changes.
     *
     * @param customer            - The customer to be notified.
     * @param isSuccessfulRequest - successful request or not.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    @Override
    public void notifyCustomer(Customer customer,
                               boolean isSuccessfulRequest)
            throws TextFileNotFoundException {

        try {

            NotificationBoard notificationBoard = new NotificationBoard();
            String notificationType = "delivery";
            Notification newNotification = notificationBoard
                    .createNewNotification(
                            customer.getCustomerId(),
                            notificationType
                    );

            if (isSuccessfulRequest) {
                if (super.getOrderStatus().equalsIgnoreCase(
                        PENDING_STATUS)) {
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
