package org.group.project.controllers.chef;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.Main;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.classes.auxiliary.DataManager;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.controllers.main.UserProfileView;
import org.group.project.exceptions.ClearFileFailedException;
import org.group.project.scenes.MainScenes;
import org.group.project.scenes.chef.ChefMapsMain;
import org.group.project.scenes.chef.ChefScenesMap;
import org.group.project.scenes.chef.mainViews.OutstandingView;

/**
 * This class is to load the navigation bar in chef home page.
 */
public class ChefHomepageNavbarController {

    @FXML
    private Button outstandingOrderButton;

    @FXML
    private Button orderHistoryButton;

    @FXML
    private Button menuButton;

    @FXML
    private Button userButton;

    @FXML
    private Button logOffButton;

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        ImageLoader.setUpGraphicButton(outstandingOrderButton,
                25, 25,
                "pending");

        ImageLoader.setUpGraphicButton(orderHistoryButton,
                25, 25, "history");

        ImageLoader.setUpGraphicButton(menuButton,
                25, 25, "menu");

        ImageLoader.setUpGraphicButton(userButton,
                25, 25, "user");

        ImageLoader.setUpGraphicButton(logOffButton,
                25, 25, "power");

        outstandingOrderButton.setOnMousePressed(e -> {
            OutstandingView.controller.refreshOutstandingOrdersList();
            ChefScenesMap.getChefStage().setScene(
                    ChefScenesMap.getScenes().get(ChefMapsMain.OUTSTANDING));
        });

        orderHistoryButton.setOnMousePressed(e -> {
            // Purposely not implemented
        });

        menuButton.setOnMousePressed(e -> {
            ChefScenesMap.getChefStage().setScene(
                    ChefScenesMap.getScenes().get(ChefMapsMain.MENU));
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
            Main.getStage().setScene(Main.getScenes()
                    .get(MainScenes.LOGIN));
        });

    }


}
