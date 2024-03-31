package org.group.project.controllers.chef;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.ImageLoader;
import org.group.project.scenes.ChefMapsMain;
import org.group.project.scenes.ChefScenesMap;

import java.net.URISyntaxException;

public class ChefHomepageNavbarController {

    @FXML
    private Button outstandingOrderButton;

    @FXML
    private Button orderHistoryButton;

    @FXML
    private Button menuButton;

    @FXML
    private Button userButton;

    public void initialize() throws URISyntaxException {

        ImageLoader.setUpGraphicButton(outstandingOrderButton, 25, 25,
                "pending");

        ImageLoader.setUpGraphicButton(orderHistoryButton, 25, 25, "history");

        ImageLoader.setUpGraphicButton(menuButton, 25, 25, "menu");

        ImageLoader.setUpGraphicButton(userButton, 25, 25, "user");

        outstandingOrderButton.setOnMousePressed(e -> {
            ChefScenesMap.getChefStage().setScene(ChefScenesMap.getScenes().get(ChefMapsMain.OUTSTANDING));
        });

        orderHistoryButton.setOnMousePressed(e -> {
            // TODO order history?
        });

        menuButton.setOnMousePressed(e -> {
            ChefScenesMap.getChefStage().setScene(ChefScenesMap.getScenes().get(ChefMapsMain.MENU));
        });

        userButton.setOnMousePressed(e -> {
           // TODO user scene?
        });

    }


}
