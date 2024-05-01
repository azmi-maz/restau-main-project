package org.group.project.controllers.waiter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.Main;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.classes.auxiliary.DataManager;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.controllers.main.UserProfileView;
import org.group.project.exceptions.ClearFileFailedException;
import org.group.project.scenes.MainScenes;
import org.group.project.scenes.waiter.WaiterMapsMain;
import org.group.project.scenes.waiter.WaiterScenesMap;
import org.group.project.scenes.waiter.mainViews.BookingView;
import org.group.project.scenes.waiter.mainViews.DineInView;

import java.net.URISyntaxException;

/**
 * This class loads up the home page navigation bar for waiter.
 */
public class WaiterHomepageNavbarController {

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
     *
     * @throws URISyntaxException // TODO
     */
    public void initialize() throws URISyntaxException {

        ImageLoader.setUpGraphicButton(dineInOrderButton,
                25, 25, "menu");

        ImageLoader.setUpGraphicButton(pendingApprovalButton,
                25, 25, "pending");

        ImageLoader.setUpGraphicButton(orderHistoryButton,
                25, 25, "history");

        ImageLoader.setUpGraphicButton(userButton,
                25, 25, "user");

        ImageLoader.setUpGraphicButton(logOffButton,
                25, 25, "power");

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
                DataManager.clearFileData("ACTIVE_USER");
            } catch (ClearFileFailedException ex) {
                AlertPopUpWindow.displayErrorWindow(
                        "Error",
                        ex.getMessage()
                );
                ex.printStackTrace();
            }
            Main.getStage().setScene(Main.getScenes().get(MainScenes.LOGIN));
        });

    }
}
