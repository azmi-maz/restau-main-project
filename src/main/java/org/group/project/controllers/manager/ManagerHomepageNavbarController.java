package org.group.project.controllers.manager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.Main;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.classes.auxiliary.DataManager;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.controllers.main.UserProfileView;
import org.group.project.exceptions.ClearFileFailedException;
import org.group.project.scenes.MainScenes;
import org.group.project.scenes.manager.ManagerMapsMain;
import org.group.project.scenes.manager.ManagerScenesMap;

/**
 * This class loads up the manager home page navigation bar.
 *
 * @author azmi_maz
 */
public class ManagerHomepageNavbarController {

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
                25, 25,
                "users");

        ImageLoader.setUpGraphicButton(reportButton,
                25, 25, "report");

        ImageLoader.setUpGraphicButton(settingButton,
                25, 25, "settings");

        ImageLoader.setUpGraphicButton(userButton,
                25, 25, "user");

        ImageLoader.setUpGraphicButton(logOffButton,
                25, 25, "power");

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
                DataManager.clearFileData("ACTIVE_USER");
            } catch (ClearFileFailedException ex) {
                AlertPopUpWindow.displayErrorWindow(
                        ex.getMessage()
                );
                ex.printStackTrace();
            }
            Main.getStage().setScene(Main.getScenes().get(MainScenes.LOGIN));
        });

    }


}
