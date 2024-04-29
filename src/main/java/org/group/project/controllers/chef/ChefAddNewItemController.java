package org.group.project.controllers.chef;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.classes.Chef;
import org.group.project.classes.Menu;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.exceptions.TextFileNotFoundException;

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

        try {

            Menu menu = new Menu();
            menu.updateItemTypeChoiceBox(
                    itemTypeChoiceBox
            );

        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    "Error",
                    e.getMessage()
            );
            e.printStackTrace();
        }


        addNewItemButton.setOnAction(e -> {

            if (
                    !itemNameTextField.getText().isBlank()
                            && !itemTypeChoiceBox.getValue()
                            .equals("Choose type")
            ) {

                String itemName = itemNameTextField.getText().toLowerCase();
                String itemType = itemTypeChoiceBox.getValue().toLowerCase();
                Chef chef = (Chef) Main.getCurrentUser();

                try {
                    boolean isSuccessful = chef.createNewMenuItem(itemName);
                    if (isSuccessful) {
                        promptItemAddedSuccessfully();
                    } else {
                        promptItemAddFailed();
                    }
                } catch (TextFileNotFoundException ex) {
                    AlertPopUpWindow.displayErrorWindow(
                            "Error",
                            ex.getMessage()
                    );
                    ex.printStackTrace();
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

    public void setItemList(List<String> menuItemList) {
        this.menuItemList = menuItemList;
    }

    private void promptItemAddedSuccessfully() {
        AlertPopUpWindow.displayInformationWindow(
                "New Item",
                "Your new creation was added successfully!",
                "Ok"
        );
    }

    private void promptItemAddFailed() {
        AlertPopUpWindow.displayErrorWindow(
                "Error",
                "Something went wrong. Please do it again."
        );
    }

    private void closeWindow() {
        Stage stage = (Stage) vbox.getScene().getWindow();
        stage.close();
    }

}
