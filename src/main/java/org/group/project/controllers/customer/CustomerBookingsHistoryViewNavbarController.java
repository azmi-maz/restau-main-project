package org.group.project.controllers.customer;

import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.scenes.customer.CustomerMapsMain;
import org.group.project.scenes.customer.CustomerScenesMap;
import org.group.project.scenes.main.CustomerView;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CustomerBookingsHistoryViewNavbarController {

    private static final String UNDO_BUTTON = "undo";
    private static final String RESERVATION_BUTTON = "reservation";
    private static final int BUTTON_WIDTH = 25;
    private static final int BUTTON_HEIGHT = 25;

    @FXML
    private Button returnButton;

    @FXML
    private Button bookingTablesButton;

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        ImageLoader.setUpGraphicButton(returnButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, UNDO_BUTTON);

        ImageLoader.setUpGraphicButton(bookingTablesButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, RESERVATION_BUTTON);

        returnButton.setOnMousePressed(e -> {
            CustomerView.customerNotificationNavbarController
                    .refreshNotificationCounter();
            CustomerScenesMap.getCustomerStage().setScene(
                    CustomerScenesMap.getScenes()
                            .get(CustomerMapsMain.HOME));
        });

        bookingTablesButton.setOnMousePressed(e -> {
            CustomerScenesMap.getCustomerStage().setScene(
                    CustomerScenesMap.getScenes()
                            .get(CustomerMapsMain.TABLES));
        });

    }
    
}
