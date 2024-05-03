package org.group.project;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.classes.auxiliary.DataManager;
import org.group.project.exceptions.ClearFileFailedException;
import org.group.project.scenes.MainScenes;
import org.group.project.scenes.MainScenesMap;

import java.util.Optional;

/**
 * This is main class that starts the application.
 *
 * @author azmi_maz
 */
public class Main extends Application {

    private static final String restaurantName =
            "Cafe94 Restaurant";
    private static final String OK = "OK_DONE";
    private static final String ACTIVE_USER_FILE = "ACTIVE_USER";
    private static final String EXIT = "Exit";
    private static final String CONFIRM_EXIT = "Do you want to exit " +
            "the program?";

    /**
     * This method starts the application.
     *
     * @param stage the primary stage for this application, onto which
     *              the application scene can be set.
     */
    @Override
    public void start(Stage stage) {

        // Starts with user log in.
        MainScenesMap loadMainScenesMaps = new MainScenesMap(stage);
        stage.setScene(MainScenesMap.getScenes().get(MainScenes.LOGIN));
        stage.setTitle(restaurantName);
        stage.show();

        // When user wants to close the application.
        stage.setOnCloseRequest(e -> {

            Optional<ButtonType> userChoice = promptForUserAcknowledgement();

            if (userChoice.get()
                    .getButtonData().toString()
                    .equalsIgnoreCase(OK)) {

                try {
                    DataManager.clearFileData(ACTIVE_USER_FILE);
                } catch (ClearFileFailedException ex) {
                    AlertPopUpWindow.displayErrorWindow(
                            ex.getMessage()
                    );
                    ex.printStackTrace();
                }
                Platform.exit();
                System.exit(0);
            }
            // This cancels the exit request.
            e.consume();

        });

    }

    private Optional<ButtonType> promptForUserAcknowledgement() {
        return AlertPopUpWindow.displayConfirmationWindow(
                EXIT,
                CONFIRM_EXIT
        );
    }

    /**
     * This the main method used to launch the application.
     *
     * @param args - empty/ not used for this application.
     */
    public static void main(String[] args) {
        launch();
    }


}