package org.group.project.controllers.waiter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.classes.auxiliary.DataManager;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.controllers.main.UserProfileView;
import org.group.project.exceptions.ClearFileFailedException;
import org.group.project.scenes.MainScenes;
import org.group.project.scenes.MainScenesMap;
import org.group.project.scenes.waiter.WaiterMapsMain;
import org.group.project.scenes.waiter.WaiterScenesMap;
import org.group.project.scenes.waiter.mainViews.BookingView;
import org.group.project.scenes.waiter.mainViews.DineInView;

/**
 * This class loads up the home page navigation bar for waiter.
 *
 * @author azmi_maz
 */
public class WaiterHomepageNavbarController {
    private static final String MENU_BUTTON = "menu";
    private static final String PENDING_BUTTON = "pending";
    private static final String HISTORY_BUTTON = "history";
    private static final String USER_BUTTON = "user";
    private static final String POWER_BUTTON = "power";
    private static final int BUTTON_WIDTH = 25;
    private static final int BUTTON_HEIGHT = 25;
    private static final String ACTIVE_USER = "ACTIVE_USER";
    @FXML
    private Button dineInOrderButton;

    @FXML
    private Button pendingApprovalButton;

    @FXML
    private Button orderHistoryButton;

    @FXML
    private Button userButton;

    @FXML
    private Button logOffButton;

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        ImageLoader.setUpGraphicButton(dineInOrderButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, MENU_BUTTON);

        ImageLoader.setUpGraphicButton(pendingApprovalButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, PENDING_BUTTON);

        ImageLoader.setUpGraphicButton(orderHistoryButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, HISTORY_BUTTON);

        ImageLoader.setUpGraphicButton(userButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, USER_BUTTON);

        ImageLoader.setUpGraphicButton(logOffButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, POWER_BUTTON);

        dineInOrderButton.setOnMousePressed(e -> {
            DineInView.controller.refreshCustomerList();
            WaiterScenesMap.getWaiterStage().setScene(
                    WaiterScenesMap.getScenes().get(WaiterMapsMain.DINEIN));
        });

        pendingApprovalButton.setOnMousePressed(e -> {
            BookingView.controller.refreshReservationList();
            BookingView.waiterDeliveryCounterController
                    .refreshDeliveryCounter();
            WaiterScenesMap.getWaiterStage().setScene(
                    WaiterScenesMap.getScenes().get(WaiterMapsMain.BOOKING));
        });

        orderHistoryButton.setOnMousePressed(e -> {
            // Purposely not implemented
        });

        userButton.setOnMousePressed(e -> {
            UserProfileView userProfileView = new UserProfileView();
            userProfileView.showWindow();
        });

        logOffButton.setOnMousePressed(e -> {
            // Log off by removing active user info
            try {
                DataManager.clearFileData(ACTIVE_USER);
            } catch (ClearFileFailedException ex) {
                AlertPopUpWindow.displayErrorWindow(
                        ex.getMessage()
                );
                ex.printStackTrace();
            }
            MainScenesMap.getStage().setScene(
                    MainScenesMap.getScenes().get(MainScenes.LOGIN));
        });

    }
}
