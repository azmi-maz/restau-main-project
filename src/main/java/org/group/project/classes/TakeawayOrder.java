package org.group.project.classes;

import org.group.project.classes.auxiliary.DataManager;
import org.group.project.exceptions.TextFileNotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * This class handles takeaway orders.
 *
 * @author azmi_maz
 */
public class TakeawayOrder extends Order implements NotifyAction {
    private static final String TAKEAWAY_TYPE = "takeaway";
    private static final String ORDER_FILE = "ORDERS";
    private static final String PENDING_KITCHEN = "pending-kitchen";
    private static final String PICKUP_TIME = "estimatedPickupTime";
    private static final int HALF_HOUR = 30;
    private static final String TIME_FORMAT = "hh:mm a";
    private static final String TIME_FORMAT_DATABASE = "H-m";
    private static final String PICKUP_UPDATE_MESSAGE = "Your order no.%d " +
            "is ready for pickup at %s.";
    private LocalTime estimatedPickupTime;

    /**
     * The constructor to initiate a takeaway order with empty basket.
     *
     * @param orderId   - the unique id.
     * @param customer  - the customer who made the order.
     * @param orderDate - the date of the order.
     * @param orderTime - the time of the order.
     */
    public TakeawayOrder(int orderId, Customer customer, LocalDate orderDate,
                         LocalTime orderTime) {
        super(orderId, customer, orderDate, orderTime,
                TAKEAWAY_TYPE, PENDING_KITCHEN);
    }

    /**
     * This constructor creates a takeaway order with updated date from
     * database.
     *
     * @param orderId             - the unique id.
     * @param customer            - the customer who made the order.
     * @param orderDate           - the date of the order.
     * @param orderTime           - the time of the order.
     * @param estimatedPickupTime - the estimated pickup time.
     * @param orderStatus         - the order status.
     * @param orderedList         - the list of items ordered.
     */
    public TakeawayOrder(int orderId, Customer customer, LocalDate orderDate,
                         LocalTime orderTime, LocalTime estimatedPickupTime,
                         String orderStatus, List<FoodDrink> orderedList) {
        super(orderId, customer, orderDate, orderTime,
                TAKEAWAY_TYPE, orderStatus, orderedList);
        this.estimatedPickupTime = estimatedPickupTime;
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
    public LocalTime newEstimatedPickupTime(int addTimeInMinutes) {
        return LocalTime.now().plusMinutes(addTimeInMinutes);
    }

    /**
     * This method sets the estimated time for the customer to pick-up.
     */
    public void setEstimatedPickupTime() {
        estimatedPickupTime = newEstimatedPickupTime(HALF_HOUR);
    }

    /**
     * Getter method to get the pickup time.
     *
     * @return the pickup time.
     */
    public LocalTime getPickupTime() {
        return estimatedPickupTime;
    }

    /**
     * This method gets the pickup time in hh:mm a format.
     *
     * @return the pickup time in the desired format.
     */
    public String getPickupTimeInFormat() {
        return estimatedPickupTime.format(
                DateTimeFormatter.ofPattern(TIME_FORMAT));
    }

    /**
     * This method gets the pickup time in database compatible format.
     *
     * @return the pickup time in the desired format.
     */
    public String getPickupTimeForDatabase() {
        return estimatedPickupTime.format(
                DateTimeFormatter.ofPattern(TIME_FORMAT_DATABASE));
    }

    /**
     * This method updates the database of the pickup time.
     *
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public void updateDatabaseOfPickupTime()
            throws TextFileNotFoundException {
        DataManager.editColumnDataByUniqueId(ORDER_FILE,
                getOrderId(), PICKUP_TIME,
                getPickupTimeForDatabase());
    }

    /**
     * This method gets the order info for user to be notified.
     *
     * @return the string format of the order info.
     */
    public String completeTakeawayOrderMessage() {
        return String.format(
                PICKUP_UPDATE_MESSAGE,
                super.getOrderId(),
                getPickupTimeInFormat()
        );
    }

    /**
     * This method notifies the customer after an order status update.
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
            String notificationType = TAKEAWAY_TYPE;
            Notification newNotification = notificationBoard
                    .createNewNotification(
                            customer.getCustomerId(),
                            notificationType
                    );

            if (isSuccessfulRequest) {
                newNotification.setMessageBody(
                        completeTakeawayOrderMessage()
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
