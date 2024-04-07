package org.group.project.controllers.chef;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.classes.DataManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChefAddNewItemController {

    @FXML
    private VBox vbox;

    @FXML
    private TextField itemNameTextField;

    @FXML
    private ChoiceBox<String> itemTypeChoiceBox;

    @FXML
    private Button addNewItemButton;

    @FXML
    private Button cancelButton;

    private List<String> menuItemList;

    public void initialize() {

        itemTypeChoiceBox.setValue("Choose type");
        itemTypeChoiceBox.getItems().add("Food");
        itemTypeChoiceBox.getItems().add("Drink");

        addNewItemButton.setOnAction(e -> {

            String itemName = itemNameTextField.getText().toLowerCase();
            String itemType = itemTypeChoiceBox.getValue().toLowerCase();

            List<String> newMenuItem = new ArrayList<>(Arrays.asList(
                    itemName,
                    itemType,
                    "false"
            ));

            // TODO handle try catch
            try {
                DataManager.appendDataToFile("MENU", newMenuItem);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            closeWindow();
        });

        cancelButton.setOnAction(e -> {
            closeWindow();
        });
    }

    public void setItemList(List<String> menuItemList) {
        this.menuItemList = menuItemList;
    }

    private void closeWindow() {
        Stage stage = (Stage) vbox.getScene().getWindow();
        stage.close();
    }

}
