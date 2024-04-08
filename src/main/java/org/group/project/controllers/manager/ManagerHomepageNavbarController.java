package org.group.project.controllers.manager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.Main;
import org.group.project.classes.HelperMethods;
import org.group.project.classes.ImageLoader;
import org.group.project.scenes.MainScenes;
import org.group.project.scenes.ManagerMapsMain;
import org.group.project.scenes.ManagerScenesMap;

import java.io.IOException;
import java.net.URISyntaxException;

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

    public void initialize() throws URISyntaxException {

        ImageLoader.setUpGraphicButton(managementButton, 25, 25,
                "users");

        ImageLoader.setUpGraphicButton(reportButton, 25, 25, "report");

        ImageLoader.setUpGraphicButton(settingButton, 25, 25, "settings");

        ImageLoader.setUpGraphicButton(userButton, 25, 25, "user");

        ImageLoader.setUpGraphicButton(logOffButton, 25, 25, "power");

        managementButton.setOnMousePressed(e -> {
            ManagerScenesMap.getManagerStage().setScene(ManagerScenesMap.getScenes().get(ManagerMapsMain.MANAGEMENT));
        });

        reportButton.setOnMousePressed(e -> {
            ManagerScenesMap.getManagerStage().setScene(ManagerScenesMap.getScenes().get(ManagerMapsMain.REPORT));
        });

        settingButton.setOnMousePressed(e -> {
            // TODO setting scene?
        });

        userButton.setOnMousePressed(e -> {
            // TODO settinf scene?
        });

        logOffButton.setOnMousePressed(e -> {
            // TODO remove all active user info here
            // TODO try catch
            try {
                HelperMethods.clearActiveUser();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Main.getStage().setScene(Main.getScenes().get(MainScenes.LOGIN));
        });

    }


}
