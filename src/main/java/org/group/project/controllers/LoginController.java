package org.group.project.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.MainAppMultiWindows;
import org.group.project.classes.DataFileStructure;
import org.group.project.classes.DataManager;
import org.group.project.classes.HelperMethods;
import org.group.project.scenes.MainScenes;
import org.group.project.scenes.WindowSize;
import org.group.project.scenes.main.CustomerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This controller class handles login-view fxml events.
 *
 * @author azmi_maz
 */
public class LoginController {

    private Stage stage;

    @FXML
    private TextField loginUsername;

    @FXML
    private Label resultLabel;

    @FXML
    private Hyperlink newFoodie;

    @FXML
    protected void onLoginButtonClick() throws IOException {

        boolean isUsernameExist =
                HelperMethods.isUsernameExist(loginUsername.getText());

        if (isUsernameExist) {

//            System.out.println(resultLabel.getStyleClass());

            // These remove error, success, hidden-label. Without removing
            // all, the style name piles up
            resultLabel.getStyleClass().removeAll("error");
            resultLabel.getStyleClass().removeAll("success");
            resultLabel.getStyleClass().removeAll("hidden-label");

            // This adds success css
            resultLabel.getStyleClass().add("success");

            // The success message
            // TODO have to clean this off after sign in, else logged off still have this displayed
            resultLabel.setText("Login successful!");

            // TODO Try catch?
            List<String> registeredUserDetails =
                    HelperMethods.getUserDataByUsername(loginUsername.getText());

            List<String> currentUser = new ArrayList<>(Arrays.asList(
                    registeredUserDetails
                            .get(DataFileStructure.getIndexColOfUniqueId("USERS")),
                    registeredUserDetails.get(DataFileStructure
                            .getIndexByColName("USERS", "firstName")),
                    registeredUserDetails.get(DataFileStructure
                            .getIndexByColName("USERS", "lastName")),
                    registeredUserDetails.get(DataFileStructure
                            .getIndexByColName("USERS", "username")),
                    registeredUserDetails.get(DataFileStructure
                            .getIndexByColName("USERS", "userType"))
            ));

            DataManager.appendDataToFile("ACTIVE_USER", currentUser);

            String userType =
                    registeredUserDetails.get(DataFileStructure
                            .getIndexByColName("USERS", "userType"));

            Main.setCurrentUser(HelperMethods.getActiveUser());

            switch (userType) {
                case "manager":
                    Main.getStage().setScene(Main.getScenes().get(MainScenes.MANAGER));
                    break;
                case "waiter":
                    Main.getStage().setScene(Main.getScenes().get(MainScenes.WAITER));
                    break;
                case "chef":
                    Main.getStage().setScene(Main.getScenes().get(MainScenes.CHEF));
                    break;
                case "driver":
                    Main.getStage().setScene(Main.getScenes().get(MainScenes.DRIVER));
                    break;
                default:
                    CustomerView.controller.welcomeCustomer();
                    Main.getStage().setScene(Main.getScenes().get(MainScenes.CUSTOMER));
                    break;
            }

            // TODO reset the loginUsername textfield and resultLabel
            resultLabel.setText("");
            resultLabel.getStyleClass().removeAll("success");
            loginUsername.setText("");


        } else {

            // The error message
            resultLabel.setText("Login unsuccessful. Please try again.");

            // These remove hidden-label, success and error. Without removing
            // all, the style name piles up
            resultLabel.getStyleClass().removeAll("hidden-label");
            resultLabel.getStyleClass().removeAll("success");
            resultLabel.getStyleClass().removeAll("error");

            // The adds the error css
            resultLabel.getStyleClass().add("error");
        }
    }

    @FXML
    protected void onNewFoodieClick() {

        try {

            FXMLLoader fxmlLoader =
                    new FXMLLoader(MainAppMultiWindows.class.getResource(
                            "smallwindows" +
                                    "/new-customer-registration.fxml"));

            Scene newCustomerRegistration = new Scene(fxmlLoader.load(),
                    WindowSize.SMALL.WIDTH,
                    WindowSize.SMALL.HEIGHT);

            Stage newCustomerRegistrationStage = new Stage();
            newCustomerRegistrationStage.setScene(newCustomerRegistration);
            newCustomerRegistrationStage.setTitle("New Customer Registration");

            newCustomerRegistrationStage.initModality(Modality.APPLICATION_MODAL);

            newCustomerRegistrationStage.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }


}
