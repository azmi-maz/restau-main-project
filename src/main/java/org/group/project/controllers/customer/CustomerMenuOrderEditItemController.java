package org.group.project.controllers.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.classes.FoodDrink;

import java.net.URISyntaxException;
import java.util.List;

public class CustomerMenuOrderEditItemController {

    @FXML
    private ImageView menuImage;

    @FXML
    private Label itemNameLabel;

    @FXML
    private TextField quantityTextField;

    @FXML
    private BorderPane borderPane;

    private String imageUrl;

    private String itemName;

    @FXML
    private Button saveChangesButton;

    @FXML
    private Button cancelButton;

    @FXML
    private List<FoodDrink> mainList;

    public void initialize() {

        saveChangesButton.setOnMousePressed(e -> {
            editItem(itemNameLabel.getText());
        });

        cancelButton.setOnMousePressed(e -> {
            closeWindow();
        });

    }

    public void setItemToEdit(String imageUrl, String itemName,
                              List<FoodDrink> mainList) throws URISyntaxException {
        menuImage.setImage(new Image(Main.class.getResource(imageUrl).toURI().toString()));
        itemNameLabel.setText(itemName);
        this.mainList = mainList;
    }

    public void editItem(String itemName) {

        String menuItemName = itemName.toLowerCase();

        // TODO handle not integer value and negative integer
        int itemQuantity = Integer.parseInt(quantityTextField.getText());

        FoodDrink tempItem = null;

        for (FoodDrink foodItem : mainList) {
            if (foodItem.getItemName().equalsIgnoreCase(menuItemName)) {
                if (itemQuantity >= 1) {
                    foodItem.setQuantity(itemQuantity);
                } else if (itemQuantity == 0) {
                    tempItem = foodItem;
                }
            }
        }
        if (tempItem != null) {
            mainList.remove(tempItem);
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
