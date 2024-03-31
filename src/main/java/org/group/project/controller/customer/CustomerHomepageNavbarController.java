package org.group.project.controller.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.ImageLoader;
import org.group.project.scenes.CustomerMapsMain;
import org.group.project.scenes.CustomerScenesMap;

import java.net.URISyntaxException;

public class CustomerHomepageNavbarController {

    @FXML
    private Button menuButton;

    @FXML
    private Button reservationButton;

    @FXML
    private Button historyButton;

    @FXML
    private Button notificationButton;

    @FXML
    private Button helpButton;

    @FXML
    private Button settingButton;

    @FXML
    private Button userButton;

    public void initialize() throws URISyntaxException {

        ImageLoader.setUpGraphicButton(menuButton, 25, 25, "menu");

        ImageLoader.setUpGraphicButton(reservationButton, 25, 25, "reservation");

        ImageLoader.setUpGraphicButton(historyButton, 25, 25, "history");

        ImageLoader.setUpGraphicButton(notificationButton, 25, 25, "notification");

        ImageLoader.setUpGraphicButton(helpButton, 25, 25, "help");

        ImageLoader.setUpGraphicButton(settingButton, 25, 25, "settings");

        ImageLoader.setUpGraphicButton(userButton, 25, 25, "user");

        menuButton.setOnMousePressed(e -> {
            CustomerScenesMap.getCustomerStage().setScene(CustomerScenesMap.getScenes().get(CustomerMapsMain.MENU));
        });

        reservationButton.setOnMousePressed(e -> {
            CustomerScenesMap.getCustomerStage().setScene(CustomerScenesMap.getScenes().get(CustomerMapsMain.BOOKING));
        });

        historyButton.setOnMousePressed(e -> {
            CustomerScenesMap.getCustomerStage().setScene(CustomerScenesMap.getScenes().get(CustomerMapsMain.ORDER));
        });

        notificationButton.setOnMousePressed(e -> {
            CustomerScenesMap.getCustomerStage().setScene(CustomerScenesMap.getScenes().get(CustomerMapsMain.NOTIFICATION));
        });

        helpButton.setOnMousePressed(e -> {
            // TODO help scene?
        });

        settingButton.setOnMousePressed(e -> {
            // TODO setting scene
        });

        userButton.setOnMousePressed(e -> {
            // TODO user window
        });

    }


}
