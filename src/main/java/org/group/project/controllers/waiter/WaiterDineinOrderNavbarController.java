package org.group.project.controllers.waiter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.scenes.main.WaiterView;
import org.group.project.scenes.waiter.WaiterMapsMain;
import org.group.project.scenes.waiter.WaiterScenesMap;

/**
 * This class loads up the navigation bar in the dine-in view for waiter.
 *
 * @author azmi_maz
 */
public class WaiterDineinOrderNavbarController {

    @FXML
    private Button homeButton;

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        ImageLoader.setUpGraphicButton(homeButton, 25, 25,
                "undo");

        homeButton.setOnMousePressed(e -> {
            WaiterView.waiterMainCounterController.refreshMainCounter();
            WaiterScenesMap.getWaiterStage().setScene(
                    WaiterScenesMap.getScenes().get(WaiterMapsMain.HOME));
        });

    }
}
