package org.group.project.controllers.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.ImageLoader;
import org.group.project.scenes.CustomerMapsMain;
import org.group.project.scenes.CustomerScenesMap;

import java.net.URISyntaxException;

public class CustomerOrderDetailsNavbarController {

    @FXML
    private Button homeButton;

    @FXML
    private Button returnToMenuButton;

    public void initialize() throws URISyntaxException {

        ImageLoader.setUpGraphicButton(homeButton, 25, 25, "home");

        homeButton.setOnMousePressed(e -> {
            CustomerScenesMap.getCustomerStage().setScene(CustomerScenesMap.getScenes().get(CustomerMapsMain.HOME));
        });

        ImageLoader.setUpGraphicButton(returnToMenuButton, 25, 25, "undo");

        returnToMenuButton.setOnMousePressed(e -> {
            CustomerScenesMap.getCustomerStage().setScene(CustomerScenesMap.getScenes().get(CustomerMapsMain.MENU));
        });

    }


}
