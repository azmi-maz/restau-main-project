package org.group.project.controllers.waiter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.group.project.classes.Kitchen;
import org.group.project.classes.Order;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.exceptions.TextFileNotFoundException;

import java.util.List;

public class WaiterDeliveryNavbarCounter {

    @FXML
    private HBox counterBox;

    @FXML
    private Label deliveryCounter1;

    @FXML
    private Label deliveryCounter2;

    public void refreshDeliveryCounter() {

        int newCounter = 0;

        try {

            Kitchen kitchen = new Kitchen();
            List<Order> pendingDeliveryList = kitchen.getAllOrderTickets();

            for (Order delivery : pendingDeliveryList) {

                // TODO filter
                if (kitchen.isDeliveryOrderClass(delivery)
                        && delivery.getOrderStatus()
                        .equalsIgnoreCase("pending-approval")) {
                    newCounter++;
                }
            }

        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    "Error",
                    e.getMessage()
            );
            e.printStackTrace();
        }

        if (newCounter == 0) {
            deliveryCounter1.setText("");
            deliveryCounter2.setText("");
            counterBox.getStyleClass().clear();
        } else if (newCounter > 0 && newCounter <= 9) {
            deliveryCounter1.setText(String.valueOf(newCounter));
            deliveryCounter2.setText("");
            counterBox.getStyleClass().clear();
            counterBox.getStyleClass().add("counterBox");
        } else if (newCounter > 9 && newCounter <= 99) {
            String count = String.valueOf(newCounter);
            deliveryCounter1.setText(String.valueOf(count.charAt(0)));
            deliveryCounter2.setText(String.valueOf(count.charAt(1)));
            counterBox.getStyleClass().clear();
            counterBox.getStyleClass().add("counterBox");
        }

    }
}
