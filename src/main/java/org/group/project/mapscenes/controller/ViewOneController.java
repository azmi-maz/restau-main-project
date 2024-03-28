package org.group.project.mapscenes.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.group.project.MainAppMap;
import org.group.project.classes.DataFileStructure;
import org.group.project.classes.HelperMethods;
import org.group.project.mapscenes.model.SceneName;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ViewOneController extends IllegalArgumentException {

    private Stage stage;

    @FXML
    private Label labelText;

    @FXML
    private Label fileName;

    @FXML
    private Button backButton;

    @FXML
    private Button closeButton;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    public ViewOneController(Stage stage) {
        if (stage == null) {
            throw new IllegalArgumentException("Stage cannot be null");
        }
        this.stage = stage;
    }

    public void handleMousePress(Event event) {
        stage.setScene(MainAppMap.getScenes().get(SceneName.MAIN));
    }

    public void searchUser(Event event) throws FileNotFoundException {
        String searchId = searchField.getText();
        List<String> labels = DataFileStructure.getValues("USERS");
        List<String> newData = new ArrayList<>();
        StringBuilder result = new StringBuilder();
        if (searchId != null) {
            newData = HelperMethods.getDataById("USERS",
                    searchId);
            int i = 0;
            for (String label : labels) {
                if (i == labels.size() - 1) {
                    result.append(label + ": " + newData.get(i));
                } else {
                    result.append(label + ": " + newData.get(i) + ", " + "\n");
                }
                i++;
            }
        }
        labelText.setText(String.valueOf(result));
        searchField.setText(null);
    }

    @FXML
    public void initialize() throws FileNotFoundException {
//        labelText.setText("This is scene 1");
        fileName.setText("Database: " + "USERS");
        labelText.setText("Enter a user id below.");
        backButton.setOnMousePressed(e -> handleMousePress(e));
        searchButton.setOnMousePressed(e -> {
            try {
                searchUser(e);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        closeButton.setOnMousePressed(e -> stage.close());
    }
}
