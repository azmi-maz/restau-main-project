package org.group.project.controllers.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.Main;
import org.group.project.classes.auxiliary.HelperMethods;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.controllers.main.UserProfileView;
import org.group.project.scenes.MainScenes;
import org.group.project.scenes.customer.CustomerMapsMain;
import org.group.project.scenes.customer.CustomerScenesMap;
import org.group.project.scenes.customer.mainViews.BookingsView;
import org.group.project.scenes.customer.mainViews.NotificationView;
import org.group.project.scenes.customer.mainViews.OrdersView;

import java.io.FileNotFoundException;
import java.io.IOException;
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

    @FXML
    private Button logOffButton;


    public void initialize() throws URISyntaxException {

        ImageLoader.setUpGraphicButton(menuButton, 25, 25, "menu");

        ImageLoader.setUpGraphicButton(reservationButton, 25, 25, "reservation");

        ImageLoader.setUpGraphicButton(historyButton, 25, 25, "history");

        ImageLoader.setUpGraphicButton(notificationButton, 25, 25, "notification");

        ImageLoader.setUpGraphicButton(helpButton, 25, 25, "help");

        ImageLoader.setUpGraphicButton(settingButton, 25, 25, "settings");

        ImageLoader.setUpGraphicButton(userButton, 25, 25, "user");

        ImageLoader.setUpGraphicButton(logOffButton, 25, 25, "power");

        menuButton.setOnMousePressed(e -> {
            CustomerScenesMap.getCustomerStage().setScene(CustomerScenesMap.getScenes().get(CustomerMapsMain.MENU));
        });

        reservationButton.setOnMousePressed(e -> {

            // TODO comment
            try {
                BookingsView.controller.refreshReservationList();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            CustomerScenesMap.getCustomerStage().setScene(CustomerScenesMap.getScenes().get(CustomerMapsMain.BOOKING));
        });

        historyButton.setOnMousePressed(e -> {

            // TODO comment
            try {
                OrdersView.controller.refreshOrderHistoryList();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            CustomerScenesMap.getCustomerStage().setScene(CustomerScenesMap.getScenes().get(CustomerMapsMain.ORDER));
        });

        notificationButton.setOnMousePressed(e -> {

            // TODO comment
            try {
                NotificationView.controller.refreshNotificationList();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            CustomerScenesMap.getCustomerStage().setScene(CustomerScenesMap.getScenes().get(CustomerMapsMain.NOTIFICATION));
        });

        helpButton.setOnMousePressed(e -> {
            // TODO help scene?
        });

        settingButton.setOnMousePressed(e -> {
            // TODO setting scene
        });

        userButton.setOnMousePressed(e -> {
            // TODO
            UserProfileView userProfileView = new UserProfileView();
            userProfileView.showWindow();
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
