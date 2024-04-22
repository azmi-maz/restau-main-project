package org.group.project.controllers.waiter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.group.project.classes.auxiliary.DataFileStructure;
import org.group.project.classes.auxiliary.DataManager;

import java.io.FileNotFoundException;
import java.util.List;

public class WaiterMainNavbarCounter {

    @FXML
    private HBox counterBox;

    @FXML
    private Label mainCounter1;

    @FXML
    private Label mainCounter2;

    public void refreshMainCounter() throws FileNotFoundException {

        int newCounter = 0;

        List<String> pendingBookingList = DataManager.allDataFromFile("BOOKINGS");
        List<String> pendingDeliveryList = DataManager.allDataFromFile("ORDERS");

        for (String booking : pendingBookingList) {
            List<String> bookingDetails = List.of(booking.split(","));

            // booking status
            String bookingStatus = bookingDetails.get(DataFileStructure.getIndexByColName("BOOKINGS", "bookingStatus"));

            // TODO filter
            if (bookingStatus.equalsIgnoreCase("pending-approval")) {
                newCounter++;
            }
        }

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
            mainCounter1.setText("");
            mainCounter2.setText("");
            counterBox.getStyleClass().remove("counterBox");
        } else if (newCounter > 0 && newCounter <= 9) {
            mainCounter1.setText(String.valueOf(newCounter));
            mainCounter2.setText("");
            counterBox.getStyleClass().remove("counterBox");
            counterBox.getStyleClass().add("counterBox");
        } else if (newCounter > 9 && newCounter <= 99) {
            String count = String.valueOf(newCounter);
            mainCounter1.setText(String.valueOf(count.charAt(0)));
            mainCounter2.setText(String.valueOf(count.charAt(1)));
            counterBox.getStyleClass().remove("counterBox");
            counterBox.getStyleClass().add("counterBox");
        }
    }
}
