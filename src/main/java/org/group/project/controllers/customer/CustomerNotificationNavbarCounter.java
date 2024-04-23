package org.group.project.controllers.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.group.project.Main;
import org.group.project.classes.auxiliary.DataFileStructure;
import org.group.project.classes.auxiliary.DataManager;
import org.group.project.classes.auxiliary.HelperMethods;

import java.io.FileNotFoundException;
import java.util.List;

public class CustomerNotificationNavbarCounter {

    private int userId;

    @FXML
    private HBox counterBox;

    @FXML
    private Label notificationCounter1;

    @FXML
    private Label notificationCounter2;

    private void getUserId() throws FileNotFoundException {
        userId = HelperMethods
                .findUserIdByUsername(Main
                        .getCurrentUser().getUsername());
    }

    public void refreshNotificationCounter() throws FileNotFoundException {

        int newCounter = 0;
        getUserId();

        List<String> notificationList = DataManager.allDataFromFile("NOTIFICATION");

        for (String notification : notificationList) {
            List<String> notificationDetails = List.of(notification.split(","));

            // userId
            int currentUserId = Integer.parseInt(notificationDetails.get(DataFileStructure.getIndexByColName("NOTIFICATION", "userId")));

            // read status
            boolean readStatus = Boolean.parseBoolean(notificationDetails.get(DataFileStructure.getIndexByColName("NOTIFICATION", "readStatus")));

            // TODO filter user id here
            if (currentUserId == userId && !readStatus) {
                newCounter++;
            }
        }
        
        if (newCounter == 0) {
            notificationCounter1.setText("");
            notificationCounter2.setText("");
            counterBox.getStyleClass().clear();
        } else if (newCounter > 0 && newCounter <= 9) {
            notificationCounter1.setText(String.valueOf(newCounter));
            notificationCounter2.setText("");
            counterBox.getStyleClass().clear();
            counterBox.getStyleClass().add("counterBox");
        } else if (newCounter > 9 && newCounter <= 99) {
            String count = String.valueOf(newCounter);
            notificationCounter1.setText(String.valueOf(count.charAt(0)));
            notificationCounter2.setText(String.valueOf(count.charAt(1)));
            counterBox.getStyleClass().clear();
            counterBox.getStyleClass().add("counterBox");
        }
    }
}
