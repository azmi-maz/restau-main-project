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
import org.group.project.scenes.WindowSize;
import org.group.project.scenes.main.*;

import java.io.IOException;

/**
 * This controller class handles login-view fxml events.
 *
 * @author azmi_maz
 */
public class LoginController {

    @FXML
    private TextField loginUsername;

    @FXML
    private Label resultLabel;

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
                resultLabel.getStyleClass().removeAll("error");
                resultLabel.getStyleClass().removeAll("success");
                resultLabel.getStyleClass().removeAll("hidden-label");

                // This adds success css
                resultLabel.getStyleClass().add("success");

                // The success message
                resultLabel.setText("Login successful!");

                User currentUser = userManagement.getUserByUsername(
                        inputUsername
                );

                String userType =
                        userManagement.getStaffClass(currentUser).toLowerCase();

                userManagement.persistActiveUserData(currentUser);

                Main.setCurrentUser(userManagement.getActiveUser());

                switch (userType) {
                    case "manager":
                        ManagerView.controller.welcomeManager();
                        Main.getStage().setScene(Main.getScenes()
                                .get(MainScenes.MANAGER));
                        break;
                    case "waiter":
                        WaiterView.controller.welcomeWaiter();
                        WaiterView.waiterMainCounterController
                                .refreshMainCounter();
                        Main.getStage().setScene(Main.getScenes()
                                .get(MainScenes.WAITER));
                        break;
                    case "chef":
                        ChefView.controller.welcomeChef();
                        ChefView.chefOutstandingOrdersNavbarCounterController
                                .refreshOutstandingCounter();
                        Main.getStage().setScene(Main.getScenes()
                                .get(MainScenes.CHEF));
                        break;
                    case "driver":
                        DriverView.controller.welcomeDriver();
                        DriverView.driverPendingDeliveryNavbarCounterController
                                .refreshPendingDeliveryCounter();
                        Main.getStage().setScene(Main.getScenes()
                                .get(MainScenes.DRIVER));
                        break;
                    default:
                        CustomerView.controller.welcomeCustomer();
                        CustomerView.customerNotificationNavbarController
                                .refreshNotificationCounter();
                        Main.getStage().setScene(Main.getScenes()
                                .get(MainScenes.CUSTOMER));
                        break;
                }

                // Reset the loginUsername textfield and resultLabel
                resultLabel.setText("");
                resultLabel.getStyleClass().removeAll("success");
                loginUsername.setText("");


            } else {

                // The error message
                resultLabel.setText("Login unsuccessful. Please try again.");

                // These remove hidden-label, success and error.
                resultLabel.getStyleClass().removeAll("hidden-label");
                resultLabel.getStyleClass().removeAll("success");
                resultLabel.getStyleClass().removeAll("error");

                // Adds the error css
                resultLabel.getStyleClass().add("error");
            }

        } catch (TextFileNotFoundException |
                 ClearFileFailedException e) {
            AlertPopUpWindow.displayErrorWindow(
                    "Error",
                    e.getMessage()
            );
            e.printStackTrace();
        }
    }

    @FXML
    protected void onNewFoodieClick() {

        try {

            FXMLLoader fxmlLoader =
                    new FXMLLoader(Main.class.getResource(
                            "smallwindows" +
                                    "/new-customer-registration.fxml"));

            Scene newCustomerRegistration = new Scene(fxmlLoader.load(),
                    WindowSize.SMALL.WIDTH,
                    WindowSize.SMALL.HEIGHT);

            Stage newCustomerRegistrationStage = new Stage();
            newCustomerRegistrationStage.setScene(newCustomerRegistration);
            newCustomerRegistrationStage.setTitle(
                    "New Customer Registration");

            newCustomerRegistrationStage.initModality(
                    Modality.APPLICATION_MODAL);

            newCustomerRegistrationStage.showAndWait();


        } catch (IOException e) {
            AlertPopUpWindow.displayErrorWindow(
                    "Error",
                    e.getMessage()
            );
            e.printStackTrace();
        }
    }


}
