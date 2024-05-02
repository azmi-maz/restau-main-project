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
import org.group.project.classes.Menu;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.exceptions.TextFileNotFoundException;

import java.net.URISyntaxException;
import java.util.List;

/**
 * This class enables the customer to add item to their cart.
 *
 * @author azmi_maz
 */
public class CustomerMenuOrderAddItemController {

    @FXML
    private ImageView menuImage;

    @FXML
    private Label itemNameLabel;

    @FXML
    private TextField quantityTextField;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button addItemButton;

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
                        "Quantity cannot be less than 0"
                );
                quantityTextField.setText("1");
            }
        });

        addItemButton.setOnMousePressed(e -> {
            int quantityValue = Integer.parseInt(quantityTextField.getText());
            if (quantityValue < 0) {
                AlertPopUpWindow.displayErrorWindow(
                        "Quantity cannot be less than 0"
                );
                quantityTextField.setText("1");
            } else {
                addNewItem(itemNameLabel.getText());
            }
        });

        cancelButton.setOnMousePressed(e -> {
            closeWindow();
        });

    }

    /**
     * This sets up the item details to be added to cart.
     *
     * @param imageUrl - the image file name of the selected item.
     * @param itemName - the selected item name.
     * @param mainList - the list of food/drink items ordered.
     * @throws URISyntaxException - the image uri did not work.
     */
    public void setItemToEdit(String imageUrl, String itemName,
                              List<FoodDrink> mainList)
            throws URISyntaxException {
        menuImage.setImage(new Image(Main.class.getResource(
                imageUrl).toURI().toString()));

        menuImage.fitHeightProperty().bind(borderPane.heightProperty()
                .subtract(10).divide(1.5));
        menuImage.fitWidthProperty().bind(borderPane.widthProperty()
                .subtract(10).divide(1.5));
        menuImage.setPreserveRatio(true);
        itemNameLabel.setText(itemName);
        this.mainList = mainList;
    }

    /**
     * This method is used to add item to the customer cart.
     *
     * @param itemName - the item name to be added.
     */
    public void addNewItem(String itemName) {

        String menuItemName = itemName.toLowerCase();

        String itemType = "";
        try {

            Menu menu = new Menu();
            itemType = menu.findTypeByItemName(menuItemName);

        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }

        int itemQuantity = Integer.parseInt(quantityTextField.getText());

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
            FoodDrink newItem = new FoodDrink(menuItemName,
                    itemType, 1);
            if (itemQuantity > 1) {
                for (int i = 1; i < itemQuantity; i++) {
                    newItem.incrementQuantity();
                }
            }
            mainList.add(newItem);
        }

        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
    }


}
