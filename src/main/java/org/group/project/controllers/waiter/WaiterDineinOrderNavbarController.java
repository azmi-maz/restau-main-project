package org.group.project.controllers.waiter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.scenes.main.WaiterView;
import org.group.project.scenes.waiter.WaiterMapsMain;
import org.group.project.scenes.waiter.WaiterScenesMap;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class WaiterDineinOrderNavbarController {

    @FXML
    private Button homeButton;

    @FXML
    private Button addItemButton;

    public void initialize() throws URISyntaxException {

        ImageLoader.setUpGraphicButton(homeButton, 25, 25,
                "undo");

        homeButton.setOnMousePressed(e -> {
            // TODO try catch
            try {
                WaiterView.waiterMainCounterController.refreshMainCounter();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            WaiterScenesMap.getWaiterStage().setScene(WaiterScenesMap.getScenes().get(WaiterMapsMain.HOME));
        });

    }


}
