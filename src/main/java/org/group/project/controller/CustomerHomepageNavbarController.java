package org.group.project.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.group.project.classes.ImageLoader;

import java.net.URISyntaxException;

public class CustomerHomepageNavbarController {

    @FXML
    private Button menuButton;

    @FXML
    private Label menuLabel;

    @FXML
    private Button reservationButton;

    @FXML
    private Label reservationLabel;

    @FXML
    private Button historyButton;

    @FXML
    private Label historyLabel;

    @FXML
    private Button notificationButton;

    @FXML
    private Label notificationLabel;

    @FXML
    private Button helpButton;

    @FXML
    private Label helpLabel;

    @FXML
    private Button settingButton;

    @FXML
    private Label settingLabel;

    @FXML
    private Button userButton;

    @FXML
    private Label userLabel;


    public void initialize() throws URISyntaxException {

        ImageLoader.setUpGraphicButton(menuButton, 25, 25, "cart");

        ImageLoader.setUpGraphicButton(reservationButton, 25, 25, "reservation");

        ImageLoader.setUpGraphicButton(historyButton, 25, 25, "history");

        ImageLoader.setUpGraphicButton(notificationButton, 25, 25, "notification");

        ImageLoader.setUpGraphicButton(helpButton, 25, 25, "help");

        ImageLoader.setUpGraphicButton(settingButton, 25, 25, "settings");

        ImageLoader.setUpGraphicButton(userButton, 25, 25, "user");

    }


}
