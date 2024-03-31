package org.group.project.controller.waiter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.ImageLoader;
import org.group.project.scenes.WaiterMapsMain;
import org.group.project.scenes.WaiterScenesMap;

import java.net.URISyntaxException;

public class WaiterDineinOrderNavbarController {

    @FXML
    private Button homeButton;

    @FXML
    private Button addItemButton;

    public void initialize() throws URISyntaxException {

        ImageLoader.setUpGraphicButton(homeButton, 25, 25,
                "undo");

        ImageLoader.setUpGraphicButton(addItemButton, 25, 25,
                "circle-plus");

        homeButton.setOnMousePressed(e -> {
            WaiterScenesMap.getWaiterStage().setScene(WaiterScenesMap.getScenes().get(WaiterMapsMain.HOME));
        });

        addItemButton.setOnMousePressed(e -> {
            // TODO link with add item
        });


    }


}
