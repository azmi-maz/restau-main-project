package org.group.project.controller.waiter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.ImageLoader;
import org.group.project.scenes.WaiterMapsMain;
import org.group.project.scenes.WaiterScenesMap;

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

    public void initialize() throws URISyntaxException {

        ImageLoader.setUpGraphicButton(dineInOrderButton, 25, 25,
                "menu");

        ImageLoader.setUpGraphicButton(pendingApprovalButton, 25, 25,
                "pending");

        ImageLoader.setUpGraphicButton(orderHistoryButton, 25, 25, "history");

        ImageLoader.setUpGraphicButton(userButton, 25, 25, "user");


        dineInOrderButton.setOnMousePressed(e -> {
            WaiterScenesMap.getWaiterStage().setScene(WaiterScenesMap.getScenes().get(WaiterMapsMain.DINEIN));
        });

        pendingApprovalButton.setOnMousePressed(e -> {
            WaiterScenesMap.getWaiterStage().setScene(WaiterScenesMap.getScenes().get(WaiterMapsMain.BOOKING));
        });

        orderHistoryButton.setOnMousePressed(e -> {
            // TODO history scene
        });

        userButton.setOnMousePressed(e -> {
           // TODO user scene?
        });

    }


}
