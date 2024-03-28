package org.group.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainAppMultiWindows extends Application {

    // Constants for the main window
    private static final int MAIN_WINDOW_WIDTH = 600;
    private static final int MAIN_WINDOW_HEIGHT = 400;
    private static final String WINDOW_TITLE = "User Management";

    // Constants for the edit window.
    // We make them public and allow the controllers to access these,
    // so that we keep all window constants in one place.
    public static final int EDIT_WINDOW_WIDTH = 400;
    public static final int EDIT_WINDOW_HEIGHT = 250;
    public static final String EDIT_WINDOW_TITLE = "Edit User";

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the main scene.
            FXMLLoader fxmlloader =
                    new FXMLLoader(MainAppMultiWindows.class.getResource(
                            "multiwindow-test/CountryViewer.fxml"));
            BorderPane root = (BorderPane) fxmlloader.load();
            Scene scene = new Scene(root, MAIN_WINDOW_WIDTH,
                    MAIN_WINDOW_HEIGHT);

            // Place the main scene on stage and show it.
            primaryStage.setScene(scene);
            primaryStage.setTitle(WINDOW_TITLE);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
