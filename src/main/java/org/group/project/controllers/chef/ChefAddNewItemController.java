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
import org.group.project.scenes.MainScenesMap;

import java.util.List;

/**
 * This class allows chef to add a new item to the menu.
 *
 * @author azmi_maz
 */
public class ChefAddNewItemController {

    private static final String CHOOSE_TYPE = "Choose type";
    private static final String COMPLETE_FORM = "Please complete the form.";
    private static final String NEW_ITEM = "New Item";
    private static final String CREATION_SUCCESS = "Your new creation " +
            "was added successfully!";
    private static final String OK = "Ok";
    private static final String TRY_AGAIN = "Something went wrong. " +
            "Please do it again.";
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

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        try {

            Menu menu = new Menu();
            menu.updateItemTypeChoiceBox(
                    itemTypeChoiceBox
            );

        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }


        addNewItemButton.setOnAction(e -> {

            if (
                    !itemNameTextField.getText().isBlank()
                            && !itemTypeChoiceBox.getValue()
                            .equals(CHOOSE_TYPE)
            ) {

                String itemName = itemNameTextField.getText().toLowerCase();
                String itemType = itemTypeChoiceBox.getValue().toLowerCase();
                Chef chef = (Chef) MainScenesMap.getCurrentUser();

                try {
                    boolean isSuccessful = chef.createNewMenuItem(itemName);
                    if (isSuccessful) {
                        promptItemAddedSuccessfully();
                    } else {
                        promptItemAddFailed();
                    }
                } catch (TextFileNotFoundException ex) {
                    AlertPopUpWindow.displayErrorWindow(
                            ex.getMessage()
                    );
                    ex.printStackTrace();
                }

                closeWindow();

            } else {
                AlertPopUpWindow.displayErrorWindow(
                        COMPLETE_FORM
                );
            }

        });

        cancelButton.setOnAction(e -> {
            closeWindow();
        });
    }

    /**
     * This method sets up the item list.
     *
     * @param menuItemList - the item list from the main page.
     */
    public void setItemList(List<String> menuItemList) {
        this.menuItemList = menuItemList;
    }

    private void promptItemAddedSuccessfully() {
        AlertPopUpWindow.displayInformationWindow(
                NEW_ITEM,
                CREATION_SUCCESS,
                OK
        );
    }

    private void promptItemAddFailed() {
        AlertPopUpWindow.displayErrorWindow(
                TRY_AGAIN
        );
    }

    private void closeWindow() {
        Stage stage = (Stage) vbox.getScene().getWindow();
        stage.close();
    }

}
