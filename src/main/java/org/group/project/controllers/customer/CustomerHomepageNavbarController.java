package org.group.project.controllers.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.Main;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.classes.auxiliary.DataManager;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.controllers.main.UserProfileView;
import org.group.project.exceptions.ClearFileFailedException;
import org.group.project.scenes.MainScenes;
import org.group.project.scenes.customer.CustomerMapsMain;
import org.group.project.scenes.customer.CustomerScenesMap;
import org.group.project.scenes.customer.mainViews.BookingsView;
import org.group.project.scenes.customer.mainViews.NotificationView;
import org.group.project.scenes.customer.mainViews.OrdersView;

/**
 * This class loads up the customer home page navigation bar.
 *
 * @author azmi_maz
 */
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

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        ImageLoader.setUpGraphicButton(menuButton,
                25, 25, "menu");

        ImageLoader.setUpGraphicButton(reservationButton,
                25, 25, "reservation");

        ImageLoader.setUpGraphicButton(historyButton,
                25, 25, "history");

        ImageLoader.setUpGraphicButton(notificationButton,
                25, 25, "notification");

        ImageLoader.setUpGraphicButton(helpButton,
                25, 25, "help");

        ImageLoader.setUpGraphicButton(settingButton,
                25, 25, "settings");

        ImageLoader.setUpGraphicButton(userButton,
                25, 25, "user");

        ImageLoader.setUpGraphicButton(logOffButton,
                25, 25, "power");

        menuButton.setOnMousePressed(e -> {
            CustomerScenesMap.getCustomerStage().setScene(
                    CustomerScenesMap.getScenes()
                            .get(CustomerMapsMain.MENU));
        });

        reservationButton.setOnMousePressed(e -> {
            BookingsView.controller.refreshReservationList();
            CustomerScenesMap.getCustomerStage()
                    .setScene(CustomerScenesMap.getScenes()
                            .get(CustomerMapsMain.BOOKING));
        });

        historyButton.setOnMousePressed(e -> {
            OrdersView.controller.refreshOrderHistoryList();
            CustomerScenesMap.getCustomerStage()
                    .setScene(CustomerScenesMap.getScenes()
                            .get(CustomerMapsMain.ORDER));
        });

        notificationButton.setOnMousePressed(e -> {
            NotificationView.controller.refreshNotificationList();
            CustomerScenesMap.getCustomerStage()
                    .setScene(CustomerScenesMap.getScenes()
                            .get(CustomerMapsMain.NOTIFICATION));
        });

        helpButton.setOnMousePressed(e -> {
            // Purposely not implemented
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
            Main.getStage().setScene(Main.getScenes()
                    .get(MainScenes.LOGIN));
        });

    }
}
