package org.group.project.classes;

/**
 * This interface class handles notification to customer.
 *
 * @author azmi_maz
 */
public interface NotifyAction {

    /**
     * This method notify customer with new notification.
     * @param customer - The customer to be notified.
     * @param newNotification - Message body of the notification.
     */
    public default void notifyCustomer(Customer customer,
                                       Notification newNotification) {}
}
