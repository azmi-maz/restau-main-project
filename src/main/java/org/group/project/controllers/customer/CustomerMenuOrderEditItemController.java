package org.group.project.controllers.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.group.project.classes.FoodDrink;

import java.net.URISyntaxException;
import java.util.List;

public class CustomerMenuOrderEditItemController {

    @FXML
    private Label itemNameLabel;

    @FXML
    private TextField quantityTextField;

    @FXML
    private BorderPane borderPane;

    private String imageUrl;

    private String itemName;

    @FXML
    private Button addItemButton;

    @FXML
    private Button cancelButton;

    @FXML
    private List<FoodDrink> mainList;

    public void initialize() {

        addItemButton.setOnMousePressed(e -> {
            addNewItem(itemNameLabel.getText());
        });

        cancelButton.setOnMousePressed(e -> {
            closeWindow();
        });

    }

    public void setItemToEdit(String imageUrl, String itemName,
                              List<FoodDrink> mainList) throws URISyntaxException {

    }

    public void addNewItem(String itemName) {

        String menuItemName = itemName.toLowerCase();
        // TODO need to retrieve type from database
        String itemType = "food";
        int itemQuantity = Integer.parseInt(quantityTextField.getText());

        // TODO handle not integer value and negative integer

        boolean isNewItem = true;
        for (FoodDrink foodItem : mainList) {
            if (foodItem.getItemName().equalsIgnoreCase(menuItemName)) {
                isNewItem = false;
                for (int i = 0; i < itemQuantity; i++) {
                    foodItem.incrementQuantity();
                }
            }
        }

        if (isNewItem) {
            FoodDrink newItem = new FoodDrink(menuItemName, itemType, 1);
            if (itemQuantity > 1) {
                for (int i = 1; i < itemQuantity; i++) {
                    newItem.incrementQuantity();
                }
            }
            mainList.add(newItem);
        }

        closeWindow();
    }

    /**
     * Close the window.
     */
    private void closeWindow() {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
    }

    public void onChange() {
        // TODO do the error check here for wrong numberformaterror add Alert
        //  here
//        System.out.println("Quantity Field changed to " + quantityTextField.getText());
    }


}
