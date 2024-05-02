package org.group.project.controllers.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.group.project.Main;
import org.group.project.classes.NotificationBoard;
import org.group.project.classes.UserManagement;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.exceptions.TextFileNotFoundException;

/**
 * This class loads up the notification counter of unread notifications by
 * specific customer.
 *
 * @author azmi_maz
 */
public class CustomerNotificationNavbarCounter {

    private int userId;

    @FXML
    private HBox counterBox;

    @FXML
    private Label notificationCounter1;

    @FXML
    private Label notificationCounter2;

    // This gets the current customer user id.
    private void getUserId() {

        try {

            UserManagement userManagement = new UserManagement();
            userId = userManagement.getUserIdByUsername(
                    Main.getCurrentUser().getUsername());

        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }
    }

    /**
     * This method refreshes the counter.
     */
    public void refreshNotificationCounter() {

        int newCounter = 0;
        getUserId();

        NotificationBoard notificationBoard = null;
        try {
            notificationBoard = new NotificationBoard();
        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }
        newCounter = notificationBoard.getUnreadNotificationByUserId(
                userId);

        notificationBoard.updatesNavbarCounter(
                newCounter,
                notificationCounter1,
                notificationCounter2,
                counterBox
        );
    }
}
