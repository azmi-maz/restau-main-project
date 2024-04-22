package org.group.project.controllers.driver;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.group.project.Main;
import org.group.project.classes.auxiliary.DataFileStructure;
import org.group.project.classes.auxiliary.DataManager;
import org.group.project.classes.auxiliary.HelperMethods;

import java.io.FileNotFoundException;
import java.util.List;

public class DriverPendingDeliveryNavbarCounter {

    private int userId;

    @FXML
    private HBox counterBox;

    @FXML
    private Label pendingDeliveryCounter1;

    @FXML
    private Label pendingDeliveryCounter2;

    private void getUserId() throws FileNotFoundException {
        userId = HelperMethods
                .findUserIdByUsername(Main
                        .getCurrentUser().getUsername());
    }

    public void refreshPendingDeliveryCounter() throws FileNotFoundException {

        int newCounter = 0;
        getUserId();

        List<String> pendingDeliveryList = DataManager.allDataFromFile("ORDERS");

        for (String delivery : pendingDeliveryList) {
            List<String> deliveryDetails = List.of(delivery.split(","));

            // driverId
            boolean isDriverAssignedToOrder = !deliveryDetails
                    .get(DataFileStructure
                            .getIndexByColName("ORDERS", "assignedDriver")).isBlank();
            int searchDriverId = -1;
            if (isDriverAssignedToOrder) {
                searchDriverId = Integer.parseInt(deliveryDetails
                        .get(DataFileStructure
                                .getIndexByColName("ORDERS", "assignedDriver")));

            }

            // order status
            String orderStatus = deliveryDetails.get(DataFileStructure.getIndexByColName("ORDERS", "orderStatus"));

            // TODO filter driver id here
            if (searchDriverId == userId
                    && orderStatus.equalsIgnoreCase("pending-delivery")) {
                newCounter++;
            }
        }

        if (newCounter == 0) {
            pendingDeliveryCounter1.setText("");
            pendingDeliveryCounter2.setText("");
            counterBox.getStyleClass().remove("counterBox");
        } else if (newCounter > 0 && newCounter <= 9) {
            pendingDeliveryCounter1.setText(String.valueOf(newCounter));
            pendingDeliveryCounter2.setText("");
            counterBox.getStyleClass().remove("counterBox");
            counterBox.getStyleClass().add("counterBox");
        } else if (newCounter > 9 && newCounter <= 99) {
            String count = String.valueOf(newCounter);
            pendingDeliveryCounter1.setText(String.valueOf(count.charAt(0)));
            pendingDeliveryCounter2.setText(String.valueOf(count.charAt(1)));
            counterBox.getStyleClass().remove("counterBox");
            counterBox.getStyleClass().add("counterBox");
        }
    }
}
