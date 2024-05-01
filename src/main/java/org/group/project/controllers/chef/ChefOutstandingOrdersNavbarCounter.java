package org.group.project.controllers.chef;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.group.project.classes.Kitchen;
import org.group.project.classes.Order;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.exceptions.TextFileNotFoundException;

import java.util.List;

/**
 * This class loads up the pending orders count for chef.
 */
public class ChefOutstandingOrdersNavbarCounter {

    @FXML
    private HBox counterBox;

    @FXML
    private Label outstandingCounter1;

    @FXML
    private Label outstandingCounter2;

    /**
     * This refreshes the counter when chef updates any outstanding orders.
     */
    public void refreshOutstandingCounter() {

        int newCounter = 0;

        Kitchen kitchen = null;
        try {
            kitchen = new Kitchen();
        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    "Error",
                    e.getMessage()
            );
            e.printStackTrace();
        }
        List<Order> outstandingList = kitchen.getAllOrderTickets();

        for (Order outstandingOrder : outstandingList) {

            // order status
            String orderStatus = outstandingOrder.getOrderStatus();

            if (orderStatus.equalsIgnoreCase("pending-kitchen")) {
                newCounter++;
            }
        }

        if (newCounter == 0) {
            outstandingCounter1.setText("");
            outstandingCounter2.setText("");
            counterBox.getStyleClass().clear();
        } else if (newCounter > 0 && newCounter <= 9) {
            outstandingCounter1.setText(String.valueOf(newCounter));
            outstandingCounter2.setText("");
            counterBox.getStyleClass().clear();
            counterBox.getStyleClass().add("counterBox");
        } else if (newCounter > 9 && newCounter <= 99) {
            String count = String.valueOf(newCounter);
            outstandingCounter1.setText(String.valueOf(count.charAt(0)));
            outstandingCounter2.setText(String.valueOf(count.charAt(1)));
            counterBox.getStyleClass().clear();
            counterBox.getStyleClass().add("counterBox");
        }
    }
}
