package org.group.project.classes;

import java.time.LocalDate;
import java.time.LocalTime;

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
     * @param user      - the customer who is making the order.
     * @param orderDate - the date of the order.
     * @param orderTime - the time of the order.
     */
    public DeliveryOrder(User user, LocalDate orderDate,
                         LocalTime orderTime) {
        super(user, orderDate, orderTime, "delivery", "pending-approval");
    }
}
