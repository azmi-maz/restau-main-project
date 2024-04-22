package org.group.project.controllers.waiter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.group.project.classes.auxiliary.DataFileStructure;
import org.group.project.classes.auxiliary.DataManager;

import java.io.FileNotFoundException;
import java.util.List;

public class WaiterDeliveryNavbarCounter {

    @FXML
    private HBox counterBox;

    @FXML
    private Label deliveryCounter1;

    @FXML
    private Label deliveryCounter2;

    public void refreshDeliveryCounter() throws FileNotFoundException {

        int newCounter = 0;

        List<String> pendingDeliveryList = DataManager.allDataFromFile("ORDERS");

        for (String delivery : pendingDeliveryList) {
            List<String> deliveryDetails = List.of(delivery.split(","));

            // order status
            String orderStatus = deliveryDetails.get(DataFileStructure.getIndexByColName("ORDERS", "orderStatus"));

            // TODO filter
            if (orderStatus.equalsIgnoreCase("pending-approval")) {
                newCounter++;
            }
        }

        if (newCounter == 0) {
            deliveryCounter1.setText("");
            deliveryCounter2.setText("");
            counterBox.getStyleClass().remove("counterBox");
        } else if (newCounter > 0 && newCounter <= 9) {
            deliveryCounter1.setText(String.valueOf(newCounter));
            deliveryCounter2.setText("");
            counterBox.getStyleClass().remove("counterBox");
            counterBox.getStyleClass().add("counterBox");
        } else if (newCounter > 9 && newCounter <= 99) {
            String count = String.valueOf(newCounter);
            deliveryCounter1.setText(String.valueOf(count.charAt(0)));
            deliveryCounter2.setText(String.valueOf(count.charAt(1)));
            counterBox.getStyleClass().remove("counterBox");
            counterBox.getStyleClass().add("counterBox");
        }
    }
}
