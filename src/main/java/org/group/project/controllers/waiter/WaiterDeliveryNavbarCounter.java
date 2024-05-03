package org.group.project.controllers.waiter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.group.project.classes.Kitchen;
import org.group.project.classes.Order;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.exceptions.TextFileNotFoundException;

import java.util.List;

/**
 * This class loads up the counter of pending delivery orders for approval.
 *
 * @author azmi_maz
 */
public class WaiterDeliveryNavbarCounter {
    private static final String PENDING = "pending-approval";
    private static final String COUNTERBOX_STYLE = "counterBox";
    private static final int LESS_THAN_TEN = 9;
    private static final int LESS_THAN_HUNDRED = 99;
    @FXML
    private HBox counterBox;
    @FXML
    private Label deliveryCounter1;
    @FXML
    private Label deliveryCounter2;

    /**
     * This refreshes the pending delivery order counter.
     */
    public void refreshDeliveryCounter() {

        int newCounter = 0;

        try {

            Kitchen kitchen = new Kitchen();
            List<Order> pendingDeliveryList = kitchen.getAllOrderTickets();

            for (Order delivery : pendingDeliveryList) {

                if (kitchen.isDeliveryOrderClass(delivery)
                        && delivery.getOrderStatus()
                        .equalsIgnoreCase(PENDING)) {
                    newCounter++;
                }
            }

        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }

        if (newCounter == 0) {
            deliveryCounter1.setText("");
            deliveryCounter2.setText("");
            counterBox.getStyleClass().clear();
        } else if (newCounter > 0 && newCounter <= LESS_THAN_TEN) {
            deliveryCounter1.setText(String.valueOf(newCounter));
            deliveryCounter2.setText("");
            counterBox.getStyleClass().clear();
            counterBox.getStyleClass().add(COUNTERBOX_STYLE);
        } else if (newCounter > LESS_THAN_TEN
                && newCounter <= LESS_THAN_HUNDRED) {
            String count = String.valueOf(newCounter);
            deliveryCounter1.setText(String.valueOf(count.charAt(0)));
            deliveryCounter2.setText(String.valueOf(count.charAt(1)));
            counterBox.getStyleClass().clear();
            counterBox.getStyleClass().add(COUNTERBOX_STYLE);
        }

    }
}
