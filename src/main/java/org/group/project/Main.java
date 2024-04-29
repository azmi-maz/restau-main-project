package org.group.project;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.group.project.classes.User;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.classes.auxiliary.DataManager;
import org.group.project.exceptions.ClearFileFailedException;
import org.group.project.scenes.MainScenes;
import org.group.project.scenes.chef.ChefScenesMap;
import org.group.project.scenes.customer.CustomerScenesMap;
import org.group.project.scenes.driver.DriverScenesMap;
import org.group.project.scenes.main.LoginView;
import org.group.project.scenes.manager.ManagerScenesMap;
import org.group.project.scenes.waiter.WaiterScenesMap;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Main extends Application {

    /**
     * Holds the various scenes to switch between
     */
    private static Map<MainScenes, Scene> scenes = new HashMap<>();

    private static Stage stage;

    private static User currentUser;
    private static final String restaurantName =
            "Cafe94 Restaurant";

    @Override
    public void start(Stage stage) throws IOException {

        Main.stage = stage;

        scenes.put(MainScenes.LOGIN, new LoginView(stage).getScene());
        scenes.put(MainScenes.CUSTOMER, new CustomerScenesMap(stage).getScene());
        scenes.put(MainScenes.MANAGER, new ManagerScenesMap(stage).getScene());
        scenes.put(MainScenes.WAITER, new WaiterScenesMap(stage).getScene());
        scenes.put(MainScenes.CHEF, new ChefScenesMap(stage).getScene());
        scenes.put(MainScenes.DRIVER, new DriverScenesMap(stage).getScene());

        // Start with user log in
        stage.setScene(scenes.get(MainScenes.LOGIN));
        stage.setTitle(restaurantName);
        stage.show();

        stage.setOnCloseRequest(e -> {

            Optional<ButtonType> userChoice = promptForUserAcknowledgement();

            if (userChoice.get()
                    .getButtonData().toString()
                    .equalsIgnoreCase("OK_DONE")) {

                try {
                    DataManager.clearFileData("ACTIVE_USER");
                } catch (ClearFileFailedException ex) {
                    AlertPopUpWindow.displayErrorWindow(
                            "Error",
                            ex.getMessage()
                    );
                    ex.printStackTrace();
                }
                Platform.exit();
                System.exit(0);
            }
            // This cancels the exit
            e.consume();

        });

    }

    /**
     * This getter method return a Map of main scenes.
     *
     * @return Returns a Map of the scenes.
     */
    public static Map<MainScenes, Scene> getScenes() {
        return scenes;
    }

    public static Stage getStage() {
        return stage;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public Optional<ButtonType> promptForUserAcknowledgement() {
        return AlertPopUpWindow.displayConfirmationWindow(
                "Exit",
                "Do you want to exit the program?"
        );
    }

    public static void main(String[] args) {
        launch();
    }


}