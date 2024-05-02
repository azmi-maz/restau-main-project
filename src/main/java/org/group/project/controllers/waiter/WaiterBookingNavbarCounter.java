package org.group.project.controllers.waiter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.group.project.classes.Booking;
import org.group.project.classes.Floor;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.exceptions.TextFileNotFoundException;

import java.util.List;

/**
 * This class loads up the counter of pending table reservations.
 *
 * @author azmi_maz
 */
public class WaiterBookingNavbarCounter {

    @FXML
    private HBox counterBox;

    @FXML
    private Label bookingCounter1;

    @FXML
    private Label bookingCounter2;

    /**
     * This method refreshes the pending table reservation counter.
     */
    public void refreshBookingCounter() {

        int newCounter = 0;

        try {

            Floor floor = new Floor();
            List<Booking> pendingBookingList = floor.getAllUniqueBookings();

            for (Booking booking : pendingBookingList) {

                // booking status
                String bookingStatus = booking.getBookingStatus();

                // TODO filter
                if (bookingStatus.equalsIgnoreCase(
                        "pending-approval")) {
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
            bookingCounter1.setText("");
            bookingCounter2.setText("");
            counterBox.getStyleClass().clear();
        } else if (newCounter > 0 && newCounter <= 9) {
            bookingCounter1.setText(String.valueOf(newCounter));
            bookingCounter2.setText("");
            counterBox.getStyleClass().clear();
            counterBox.getStyleClass().add("counterBox");
        } else if (newCounter > 9 && newCounter <= 99) {
            String count = String.valueOf(newCounter);
            bookingCounter1.setText(String.valueOf(count.charAt(0)));
            bookingCounter2.setText(String.valueOf(count.charAt(1)));
            counterBox.getStyleClass().clear();
            counterBox.getStyleClass().add("counterBox");
        }
    }
}
