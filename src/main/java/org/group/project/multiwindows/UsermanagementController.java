package org.group.project.multiwindows;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.group.project.MainAppMultiWindows;
import org.group.project.classes.DataManager;
import org.group.project.classes.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UsermanagementController {
    @FXML
    ListView<String> userDisplayList;
    @FXML
    Button editButton;
    @FXML
    Button deleteButton;

    // The main list that will store all our countries.
    private List<String> rawUserList = new ArrayList<>();
    private List<User> userList = new ArrayList<>();

    public void initialize() throws FileNotFoundException {
        // Get user list from database.
        rawUserList = DataManager.allDataFromFile("USERS");
        rawUserList.add(rawUserList.get(0));
        rawUserList.add(rawUserList.get(1));
        rawUserList.add(rawUserList.get(2));
        rawUserList.add(rawUserList.get(3));
        rawUserList.add(rawUserList.get(4));
        rawUserList.add(rawUserList.get(0));
        rawUserList.add(rawUserList.get(1));
        rawUserList.add(rawUserList.get(2));
        rawUserList.add(rawUserList.get(3));
        rawUserList.add(rawUserList.get(4));
        for (String rawData : rawUserList) {
            userList.add(User.convertStringToUser(rawData));
        }

        // Setup actions on buttons
        deleteButton.setOnAction(e -> {
            handleDeleteButtonAction();
        });

        editButton.setOnAction(e -> {
            handleEditButtonAction();
        });

        // Display the countries
        refreshList();
    }

    /**
     * Refresh list
     */
    private void refreshList() {
        // Clear the displayed list
        userDisplayList.getItems().clear();

        // Add each country to the displayed list
        for (User user : userList) {
            userDisplayList.getItems().add(user.getDataForListDisplay());
        }
    }

    private void handleDeleteButtonAction() {
        // Get the index of the selected item in the displayed list
        int selectedIndex = userDisplayList.getSelectionModel().getSelectedIndex();

        // Check if user selected an item
        if (selectedIndex < 0) {
            // Show a message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: Cannot delete country");
            alert.setHeaderText(null);
            alert.setContentText("Cannot delete country as no country is selected. Please select a country first.");
            alert.showAndWait();
        } else {
            // Remove the country at the selected index.
//            userDisplayList.remove(selectedIndex);
        }

        // We might have deleted a country - so we must refresh the displayed list
        refreshList();
    }

    private void handleEditButtonAction() {
        // Get the index of the selected item in the displayed list
        int selectedIndex = userDisplayList.getSelectionModel().getSelectedIndex();

        // Check if user selected an item
        if (selectedIndex < 0) {
            // Show a message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: Cannot edit country");
            alert.setHeaderText(null);
            alert.setContentText("Cannot edit country as no country is selected. Please select a country first.");
            alert.showAndWait();
            return;
        }

        // Can only get to this line if user has selected a country
//        User selectedCountry = userDisplayList.get(selectedIndex);

        // We need to use a try-catch block as the loading of the FXML file can fail.
        try {
            // Create a FXML loader for loading the Edit Country FXML file.
            FXMLLoader fxmlLoader =
                    new FXMLLoader(MainAppMultiWindows.class.getResource("multiwindow-test/EditCountry.fxml"));

            // Run the loader
            BorderPane editRoot = (BorderPane) fxmlLoader.load();
            // Access the controller that was created by the FXML loader
            EditUserController editController = fxmlLoader.<EditUserController>getController();

            //*************
            //* Important *
            //*************
            // Tell the controller which country we are editing.
            // Remember we are passing arrows (i.e., references) around.
            // This means that the edit controller's changes to the country will be reflected here (in our list).
//            editController.setCountryForEditing(selectedCountry);

            // Create a scene based on the loaded FXML scene graph
            Scene editScene = new Scene(editRoot, MainAppMultiWindows.EDIT_WINDOW_WIDTH,
                    MainAppMultiWindows.EDIT_WINDOW_HEIGHT);

            // Create a new stage (i.e., window) based on the edit scene
            Stage editStage = new Stage();
            editStage.setScene(editScene);
            editStage.setTitle(MainAppMultiWindows.EDIT_WINDOW_TITLE);

            // Make the stage a modal window.
            // This means that it must be closed before you can interact with any other window from this application.
            editStage.initModality(Modality.APPLICATION_MODAL);

            // Show the edit scene and wait for it to be closed
            editStage.showAndWait();

            // The above method only returns when the window is closed.

            // Any changes that the edit scene made to the country will have taken place to the country object.
            // This object is part of our country list.
            // So we just need to refresh the JavaFX ListView.
            refreshList();

        } catch (IOException e) {
            e.printStackTrace();
            // Quit the program (with an error code)
            System.exit(-1);
        }
    }
}
