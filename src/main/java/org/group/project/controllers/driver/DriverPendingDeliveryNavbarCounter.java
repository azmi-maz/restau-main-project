package org.group.project.controllers.driver;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.group.project.classes.DeliveryOrder;
import org.group.project.classes.Kitchen;
import org.group.project.classes.User;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.exceptions.TextFileNotFoundException;
import org.group.project.scenes.MainScenesMap;

import java.util.List;

/**
 * This class loads up the counter of pending deliveries for specific driver.
 *
 * @author azmi_maz
 */
public class DriverPendingDeliveryNavbarCounter {
    private static final int LESS_THAN_TEN = 9;
    private static final int LESS_THAN_HUNDRED = 99;
    private static final String COUNTERBOX_STYLE = "counterBox";
    private int userId;

    @FXML
    private HBox counterBox;

    @FXML
    private Label pendingDeliveryCounter1;

    @FXML
    private Label pendingDeliveryCounter2;

    /**
     * This method gets the driver user id currently logged in.
     */
    private void getUserId() {
        try {
            User user = MainScenesMap.getCurrentUser();
            userId = user.getUserId();
        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }
    }

    /**
     * This method refreshes the pending delivery counter.
     */
    public void refreshPendingDeliveryCounter() {

        int newCounter = 0;
        getUserId();

        try {
            Kitchen kitchen = new Kitchen();

            List<DeliveryOrder> pendingDeliveryList = kitchen
                    .getPendingDeliveryDataByDriverId(
                            userId
                    );

            newCounter = pendingDeliveryList.size();

        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }

        if (newCounter == 0) {
            pendingDeliveryCounter1.setText("");
            pendingDeliveryCounter2.setText("");
            counterBox.getStyleClass().clear();
        } else if (newCounter > 0 && newCounter <= LESS_THAN_TEN) {
            pendingDeliveryCounter1.setText(String.valueOf(newCounter));
            pendingDeliveryCounter2.setText("");
            counterBox.getStyleClass().clear();
            counterBox.getStyleClass().add(COUNTERBOX_STYLE);
        } else if (newCounter > LESS_THAN_TEN
                && newCounter <= LESS_THAN_HUNDRED) {
            String count = String.valueOf(newCounter);
            pendingDeliveryCounter1.setText(String.valueOf(count.charAt(0)));
            pendingDeliveryCounter2.setText(String.valueOf(count.charAt(1)));
            counterBox.getStyleClass().clear();
            counterBox.getStyleClass().add(COUNTERBOX_STYLE);
        }
    }
}
