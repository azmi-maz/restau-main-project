package org.group.project.controllers.waiter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.Main;
import org.group.project.classes.auxiliary.HelperMethods;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.scenes.MainScenes;
import org.group.project.scenes.waiter.WaiterMapsMain;
import org.group.project.scenes.waiter.WaiterScenesMap;
import org.group.project.scenes.waiter.mainViews.BookingView;
import org.group.project.scenes.waiter.mainViews.DineInView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

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

    public void initialize() throws URISyntaxException {

        ImageLoader.setUpGraphicButton(dineInOrderButton, 25, 25,
                "menu");

        ImageLoader.setUpGraphicButton(pendingApprovalButton, 25, 25,
                "pending");

        ImageLoader.setUpGraphicButton(orderHistoryButton, 25, 25, "history");

        ImageLoader.setUpGraphicButton(userButton, 25, 25, "user");

        ImageLoader.setUpGraphicButton(logOffButton, 25, 25, "power");

        dineInOrderButton.setOnMousePressed(e -> {
            // TODO try catch
            try {
                DineInView.controller.refreshCustomerList();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            WaiterScenesMap.getWaiterStage().setScene(WaiterScenesMap.getScenes().get(WaiterMapsMain.DINEIN));
        });

        pendingApprovalButton.setOnMousePressed(e -> {
            // TODO try catch
            try {
                BookingView.controller.refreshReservationList();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            WaiterScenesMap.getWaiterStage().setScene(WaiterScenesMap.getScenes().get(WaiterMapsMain.BOOKING));
        });

        orderHistoryButton.setOnMousePressed(e -> {
            // TODO history scene
        });

        userButton.setOnMousePressed(e -> {
           // TODO user scene?
        });

        logOffButton.setOnMousePressed(e -> {
            // TODO remove all active user info here
            // TODO try catch
            try {
                HelperMethods.clearActiveUser();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Main.getStage().setScene(Main.getScenes().get(MainScenes.LOGIN));
        });

    }


}
