package org.group.project.controllers.waiter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.group.project.classes.auxiliary.DataFileStructure;
import org.group.project.classes.auxiliary.DataManager;

import java.io.FileNotFoundException;
import java.util.List;

public class WaiterBookingNavbarCounter {

    @FXML
    private HBox counterBox;

    @FXML
    private Label bookingCounter1;

    @FXML
    private Label bookingCounter2;

    public void refreshBookingCounter() throws FileNotFoundException {

        int newCounter = 0;

        List<String> pendingBookingList = DataManager.allDataFromFile("BOOKINGS");

        for (String booking : pendingBookingList) {
            List<String> bookingDetails = List.of(booking.split(","));

            // booking status
            String bookingStatus = bookingDetails.get(DataFileStructure.getIndexByColName("BOOKINGS", "bookingStatus"));

            // TODO filter
            if (bookingStatus.equalsIgnoreCase("pending-approval")) {
                newCounter++;
            }
        }

        if (newCounter == 0) {
            bookingCounter1.setText("");
            bookingCounter2.setText("");
            counterBox.getStyleClass().remove("counterBox");
        } else if (newCounter > 0 && newCounter <= 9) {
            bookingCounter1.setText(String.valueOf(newCounter));
            bookingCounter2.setText("");
            counterBox.getStyleClass().remove("counterBox");
            counterBox.getStyleClass().add("counterBox");
        } else if (newCounter > 9 && newCounter <= 99) {
            String count = String.valueOf(newCounter);
            bookingCounter1.setText(String.valueOf(count.charAt(0)));
            bookingCounter2.setText(String.valueOf(count.charAt(1)));
            counterBox.getStyleClass().remove("counterBox");
            counterBox.getStyleClass().add("counterBox");
        }
    }
}
