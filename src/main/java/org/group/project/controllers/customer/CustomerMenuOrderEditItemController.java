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
import org.group.project.classes.auxiliary.AlertPopUpWindow;

import java.net.URISyntaxException;
import java.util.List;

/**
 * This class enables customer to edit the selected item in cart.
 *
 * @author azmi_maz
 */
public class CustomerMenuOrderEditItemController {
    private static final String INVALID_QUANTITY = "Quantity cannot be " +
            "less than 0";
    private static final String DEFAULT_QUANTITY = "1";
    private static final int IMG_SUBTRACT = 10;
    private static final double IMG_DIVIDE = 1.5;
    @FXML
    private ImageView menuImage;

    @FXML
    private Label itemNameLabel;

    @FXML
    private TextField quantityTextField;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button saveChangesButton;

    @FXML
    private Button cancelButton;

    @FXML
    private List<FoodDrink> mainList;

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        quantityTextField.setOnAction(e -> {
            int quantityValue = Integer.parseInt(quantityTextField.getText());
            if (quantityValue < 0) {
                AlertPopUpWindow.displayErrorWindow(
                        INVALID_QUANTITY
                );
                quantityTextField.setText(DEFAULT_QUANTITY);
            }
        });

        saveChangesButton.setOnMousePressed(e -> {
            int quantityValue = Integer.parseInt(quantityTextField.getText());
            if (quantityValue < 0) {
                AlertPopUpWindow.displayErrorWindow(
                        INVALID_QUANTITY
                );
                quantityTextField.setText(DEFAULT_QUANTITY);
            } else {
                editItem(itemNameLabel.getText());
            }
        });

        cancelButton.setOnMousePressed(e -> {
            closeWindow();
        });

    }

    /**
     * This method sets up the selected item details to be edited.
     *
     * @param imageUrl - the image file uri.
     * @param itemName - the name of the item.
     * @param mainList - the list of ordered food/drink.
     */
    public void setItemToEdit(String imageUrl, String itemName,
                              List<FoodDrink> mainList) {

        try {
            menuImage.setImage(new Image(Main.class.getResource(
                    imageUrl).toURI().toString()));
        } catch (URISyntaxException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }

        menuImage.fitHeightProperty().bind(borderPane.heightProperty()
                .subtract(IMG_SUBTRACT).divide(IMG_DIVIDE));
        menuImage.fitWidthProperty().bind(borderPane.widthProperty()
                .subtract(IMG_SUBTRACT).divide(IMG_DIVIDE));
        menuImage.setPreserveRatio(true);
        itemNameLabel.setText(itemName);
        this.mainList = mainList;
        getCurrentQty(itemName);
    }

    /**
     * This method edits the selected item quantity.
     *
     * @param itemName - the selected item name.
     */
    public void editItem(String itemName) {

        String menuItemName = itemName.toLowerCase();

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

    private void closeWindow() {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
    }

    // This updates the quantity field to the current quantity.
    private void getCurrentQty(String itemName) {

        for (FoodDrink foodItem : mainList) {
            if (foodItem.getItemName().equalsIgnoreCase(itemName)) {
                quantityTextField.setText(String
                        .valueOf(foodItem
                                .getQuantity()));
            }
        }
    }


}
