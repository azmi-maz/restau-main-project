package org.group.project.controllers.chef;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.classes.auxiliary.DataManager;
import org.group.project.classes.auxiliary.HelperMethods;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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

            if (
                    !itemNameTextField.getText().isBlank()
                            && !itemTypeChoiceBox.getValue().equals("Choose type")
            ) {

                String itemName = itemNameTextField.getText().toLowerCase();
                String itemType = itemTypeChoiceBox.getValue().toLowerCase();

                List<String> newMenuItem = null;
                try {
                    newMenuItem = getPresetItem(itemName);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

                // TODO handle try catch
                try {
                    DataManager.appendDataToFile("MENU", newMenuItem);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                closeWindow();

            } else {
                AlertPopUpWindow.displayErrorWindow(
                        "Error",
                        "Please complete the form."
                );
            }

            });

            cancelButton.setOnAction(e -> {
                closeWindow();
            });
        }

        public void setItemList (List < String > menuItemList) {
            this.menuItemList = menuItemList;
        }

        public List<String> getPresetItem (String itemName) throws
        FileNotFoundException {
            List<String> presetItem = new ArrayList<>();
            switch (itemName) {
                case "pizza", "pepsi", "coke", "spaghetti", "soup":
                    presetItem =
                            HelperMethods
                                    .getDataById("PRESET_ITEMS", itemName);
                    break;
                default:
                    presetItem =
                            HelperMethods
                                    .getDataById("PRESET_ITEMS", "pizza");
                    break;
            }
            return presetItem;
        }

        private void closeWindow () {
            Stage stage = (Stage) vbox.getScene().getWindow();
            stage.close();
        }

    }
