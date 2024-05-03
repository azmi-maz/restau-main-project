package org.group.project.controllers.manager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.classes.auxiliary.DataManager;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.controllers.main.UserProfileView;
import org.group.project.exceptions.ClearFileFailedException;
import org.group.project.scenes.MainScenes;
import org.group.project.scenes.MainScenesMap;
import org.group.project.scenes.manager.ManagerMapsMain;
import org.group.project.scenes.manager.ManagerScenesMap;

/**
 * This class loads up the manager home page navigation bar.
 *
 * @author azmi_maz
 */
public class ManagerHomepageNavbarController {
    private static final String USERS_BUTTON = "users";
    private static final String REPORT_BUTTON = "report";
    private static final String SETTINGS_BUTTON = "settings";
    private static final String USER_BUTTON = "user";
    private static final String POWER_BUTTON = "power";
    private static final int BUTTON_WIDTH = 25;
    private static final int BUTTON_HEIGHT = 25;
    private static final String ACTIVE_USER = "ACTIVE_USER";
    @FXML
    private Button managementButton;

    @FXML
    private Button reportButton;

    @FXML
    private Button settingButton;

    @FXML
    private Button userButton;

    @FXML
    private Button logOffButton;

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        ImageLoader.setUpGraphicButton(managementButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, USERS_BUTTON);

        ImageLoader.setUpGraphicButton(reportButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, REPORT_BUTTON);

        ImageLoader.setUpGraphicButton(settingButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, SETTINGS_BUTTON);

        ImageLoader.setUpGraphicButton(userButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, USER_BUTTON);

        ImageLoader.setUpGraphicButton(logOffButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, POWER_BUTTON);

        managementButton.setOnMousePressed(e -> {
            ManagerScenesMap.getManagerStage().setScene(
                    ManagerScenesMap.getScenes()
                            .get(ManagerMapsMain.MANAGEMENT));
        });

        reportButton.setOnMousePressed(e -> {
            ManagerScenesMap.getManagerStage().setScene(
                    ManagerScenesMap.getScenes()
                            .get(ManagerMapsMain.REPORT));
        });

        settingButton.setOnMousePressed(e -> {
            // Purposely not implemented
        });

        userButton.setOnMousePressed(e -> {
            UserProfileView userProfileView = new UserProfileView();
            userProfileView.showWindow();
        });

        logOffButton.setOnMousePressed(e -> {
            // Log off by removing active user info
            try {
                DataManager.clearFileData(ACTIVE_USER);
            } catch (ClearFileFailedException ex) {
                AlertPopUpWindow.displayErrorWindow(
                        ex.getMessage()
                );
                ex.printStackTrace();
            }
            MainScenesMap.getStage().setScene(
                    MainScenesMap.getScenes().get(MainScenes.LOGIN));
        });

    }


}
