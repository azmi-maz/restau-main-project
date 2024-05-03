package org.group.project.controllers.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.classes.auxiliary.DataManager;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.controllers.main.UserProfileView;
import org.group.project.exceptions.ClearFileFailedException;
import org.group.project.scenes.MainScenes;
import org.group.project.scenes.MainScenesMap;
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
    private static final String MENU_BUTTON = "menu";
    private static final String RESERVATION_BUTTON = "reservation";
    private static final String HISTORY_BUTTON = "history";
    private static final String NOTIFICATION_BUTTON = "notification";
    private static final String HELP_BUTTON = "help";
    private static final String SETTINGS_BUTTON = "settings";
    private static final String USER_BUTTON = "user";
    private static final String POWER_BUTTON = "power";
    private static final int BUTTON_WIDTH = 25;
    private static final int BUTTON_HEIGHT = 25;
    private static final String ACTIVE_USER = "ACTIVE_USER";
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
                BUTTON_WIDTH, BUTTON_HEIGHT, MENU_BUTTON);

        ImageLoader.setUpGraphicButton(reservationButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, RESERVATION_BUTTON);

        ImageLoader.setUpGraphicButton(historyButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, HISTORY_BUTTON);

        ImageLoader.setUpGraphicButton(notificationButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, NOTIFICATION_BUTTON);

        ImageLoader.setUpGraphicButton(helpButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, HELP_BUTTON);

        ImageLoader.setUpGraphicButton(settingButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, SETTINGS_BUTTON);

        ImageLoader.setUpGraphicButton(userButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, USER_BUTTON);

        ImageLoader.setUpGraphicButton(logOffButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, POWER_BUTTON);

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
                DataManager.clearFileData(ACTIVE_USER);
            } catch (ClearFileFailedException ex) {
                AlertPopUpWindow.displayErrorWindow(
                        ex.getMessage()
                );
                ex.printStackTrace();
            }
            MainScenesMap.getStage().setScene(MainScenesMap.getScenes()
                    .get(MainScenes.LOGIN));
        });

    }
}
