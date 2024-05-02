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
 *
 * @author azmi_maz
 */
public class ChefHomepageNavbarController {
    private static final String PENDING_BUTTON = "pending";
    private static final String HISTORY_BUTTON = "history";
    private static final String MENU_BUTTON = "menu";
    private static final String USER_BUTTON = "user";
    private static final String POWER_BUTTON = "power";
    private static final int BUTTON_WIDTH = 25;
    private static final int BUTTON_HEIGHT = 25;
    private static final String ACTIVE_USER_FILE = "ACTIVE_USER";
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
                BUTTON_WIDTH, BUTTON_HEIGHT,
                PENDING_BUTTON);

        ImageLoader.setUpGraphicButton(orderHistoryButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, HISTORY_BUTTON);

        ImageLoader.setUpGraphicButton(menuButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, MENU_BUTTON);

        ImageLoader.setUpGraphicButton(userButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, USER_BUTTON);

        ImageLoader.setUpGraphicButton(logOffButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, POWER_BUTTON);

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
                DataManager.clearFileData(ACTIVE_USER_FILE);
            } catch (ClearFileFailedException ex) {
                AlertPopUpWindow.displayErrorWindow(
                        ex.getMessage()
                );
                ex.printStackTrace();
            }
            Main.getStage().setScene(Main.getScenes()
                    .get(MainScenes.LOGIN));
        });

    }
}
