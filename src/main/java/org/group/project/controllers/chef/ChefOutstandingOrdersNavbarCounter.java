package org.group.project.controllers.chef;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.group.project.classes.auxiliary.DataFileStructure;
import org.group.project.classes.auxiliary.DataManager;

import java.io.FileNotFoundException;
import java.util.List;

public class ChefOutstandingOrdersNavbarCounter {

    @FXML
    private HBox counterBox;

    @FXML
    private Label outstandingCounter1;

    @FXML
    private Label outstandingCounter2;

    public void refreshOutstandingCounter() throws FileNotFoundException {

        int newCounter = 0;

        List<String> outstandingList = DataManager.allDataFromFile("ORDERS");

        for (String outstanding : outstandingList) {
            List<String> outstandingDetails = List.of(outstanding.split(","));

            // order status
            String orderStatus = outstandingDetails.get(DataFileStructure.getIndexByColName("ORDERS", "orderStatus"));

            // TODO filter user id here
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
