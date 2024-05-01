package org.group.project.classes;

import org.group.project.exceptions.TextFileNotFoundException;

/**
 * This interface class handles notification to customer.
 *
 * @author azmi_maz
 */
public interface NotifyAction {

    /**
     * This method notify customer with new notification.
     *
     * @param customer            - The customer to be notified.
     * @param isSuccessfulRequest - successful request or not.
     * @throws TextFileNotFoundException - if text file is non-existent.
     */
    public default void notifyCustomer(
            Customer customer,
            boolean isSuccessfulRequest) throws TextFileNotFoundException {
    }
}
