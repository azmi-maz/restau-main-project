package org.group.project.controllers.main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.classes.User;
import org.group.project.classes.UserManagement;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.exceptions.ClearFileFailedException;
import org.group.project.exceptions.TextFileNotFoundException;
import org.group.project.scenes.MainScenes;
import org.group.project.scenes.MainScenesMap;
import org.group.project.scenes.WindowSize;
import org.group.project.scenes.main.*;

import java.io.IOException;

/**
 * This controller class handles login-view fxml events.
 *
 * @author azmi_maz
 */
public class LoginController {
    private static final String ERROR_STYLE = "error";
    private static final String SUCCESS_STYLE = "success";
    private static final String HIDDEN_STYLE = "hidden-label";
    private static final String LOGIN_SUCCESS = "Login successful!";
    private static final String MANAGER = "manager";
    private static final String WAITER = "waiter";
    private static final String CHEF = "chef";
    private static final String DRIVER = "driver";
    private static final String LOGIN_FAILED = "Login unsuccessful. " +
            "Please try again.";
    private static final String NEW_CUSTOMER = "smallwindows" +
            "/new-customer-registration.fxml";
    private static final String NEW_CUSTOMER_TITLE = "New Customer " +
            "Registration";
    @FXML
    private TextField loginUsername;

    @FXML
    private Label resultLabel;

    // This handles the login event if user is already registered.
    @FXML
    protected void onLoginButtonClick() {

        try {

            UserManagement userManagement = new UserManagement();

            String inputUsername = loginUsername.getText();

            boolean isUsernameExist = userManagement.isUsernameAlreadyExist(
                    inputUsername
            );

            if (isUsernameExist) {

                // These remove error, success, hidden-label.
                resultLabel.getStyleClass().removeAll(ERROR_STYLE);
                resultLabel.getStyleClass().removeAll(SUCCESS_STYLE);
                resultLabel.getStyleClass().removeAll(HIDDEN_STYLE);

                // This adds success css
                resultLabel.getStyleClass().add(SUCCESS_STYLE);

                // The success message
                resultLabel.setText(LOGIN_SUCCESS);

                User currentUser = userManagement.getUserByUsername(
                        inputUsername
                );

                String userType =
                        userManagement.getStaffClass(currentUser).toLowerCase();

                userManagement.persistActiveUserData(currentUser);

                MainScenesMap.setCurrentUser(userManagement.getActiveUser());

                switch (userType) {
                    case MANAGER:
                        ManagerView.controller.welcomeManager();
                        MainScenesMap.getStage().setScene(
                                MainScenesMap.getScenes()
                                        .get(MainScenes.MANAGER));
                        break;
                    case WAITER:
                        WaiterView.controller.welcomeWaiter();
                        WaiterView.waiterMainCounterController
                                .refreshMainCounter();
                        MainScenesMap.getStage().setScene(
                                MainScenesMap.getScenes()
                                        .get(MainScenes.WAITER));
                        break;
                    case CHEF:
                        ChefView.controller.welcomeChef();
                        ChefView.chefOutstandingOrdersNavbarCounterController
                                .refreshOutstandingCounter();
                        MainScenesMap.getStage().setScene(
                                MainScenesMap.getScenes()
                                        .get(MainScenes.CHEF));
                        break;
                    case DRIVER:
                        DriverView.controller.welcomeDriver();
                        DriverView.driverPendingDeliveryNavbarCounterController
                                .refreshPendingDeliveryCounter();
                        MainScenesMap.getStage().setScene(
                                MainScenesMap.getScenes()
                                        .get(MainScenes.DRIVER));
                        break;
                    default:
                        CustomerView.controller.welcomeCustomer();
                        CustomerView.customerNotificationNavbarController
                                .refreshNotificationCounter();
                        MainScenesMap.getStage().setScene(
                                MainScenesMap.getScenes()
                                        .get(MainScenes.CUSTOMER));
                        break;
                }

                // Reset the loginUsername textfield and resultLabel
                resultLabel.setText("");
                resultLabel.getStyleClass().removeAll(SUCCESS_STYLE);
                loginUsername.setText("");


            } else {

                // The error message
                resultLabel.setText(LOGIN_FAILED);

                // These remove hidden-label, success and error.
                resultLabel.getStyleClass().removeAll(HIDDEN_STYLE);
                resultLabel.getStyleClass().removeAll(SUCCESS_STYLE);
                resultLabel.getStyleClass().removeAll(ERROR_STYLE);

                // Adds the error css
                resultLabel.getStyleClass().add(ERROR_STYLE);
            }

        } catch (TextFileNotFoundException |
                 ClearFileFailedException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }
    }

    // This opens up the new user registration form.
    @FXML
    protected void onNewFoodieClick() {

        try {

            FXMLLoader fxmlLoader =
                    new FXMLLoader(Main.class.getResource(
                            NEW_CUSTOMER));

            Scene newCustomerRegistration = new Scene(fxmlLoader.load(),
                    WindowSize.SMALL.WIDTH,
                    WindowSize.SMALL.HEIGHT);

            Stage newCustomerRegistrationStage = new Stage();
            newCustomerRegistrationStage.setScene(newCustomerRegistration);
            newCustomerRegistrationStage.setTitle(
                    NEW_CUSTOMER_TITLE);

            newCustomerRegistrationStage.initModality(
                    Modality.APPLICATION_MODAL);

            newCustomerRegistrationStage.showAndWait();


        } catch (IOException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }
    }


}
