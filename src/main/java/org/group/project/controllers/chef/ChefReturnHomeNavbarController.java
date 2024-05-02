package org.group.project.controllers.chef;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.scenes.chef.ChefMapsMain;
import org.group.project.scenes.chef.ChefScenesMap;
import org.group.project.scenes.main.ChefView;

/**
 * This class loads up the navigation bar used to return back to chef home page.
 *
 * @author azmi_maz
 */
public class ChefReturnHomeNavbarController {

    @FXML
    private Button homeButton;

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        ImageLoader.setUpGraphicButton(homeButton,
                25, 25, "undo");

        homeButton.setOnMousePressed(e -> {
            ChefView.chefOutstandingOrdersNavbarCounterController
                    .refreshOutstandingCounter();
            ChefScenesMap.getChefStage().setScene(
                    ChefScenesMap.getScenes().get(ChefMapsMain.HOME));
        });

    }

}
