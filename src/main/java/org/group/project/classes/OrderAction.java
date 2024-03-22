package org.group.project.classes;

/**
 * This interface class handles order creation and update.
 *
 * @author azmi_maz
 */
public interface OrderAction {

    /**
     * This method creates a new order.
     *
     * @param newOrder - a new order.
     * @return an order to confirm order was made successfully and display to
     * UI.
     */
    public Order createAnOrder(Order newOrder);

    /**
     * This method updates an order as complete.
     *
     * @param completedOrder - the order to be marked off as complete.
     * @return true if the update was made successfully.
     */
    public boolean updateOrderStatus(Order completedOrder);
}
