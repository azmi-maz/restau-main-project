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

public class CustomerMenuOrderAddEditItemController {

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
        menuImage.setImage(new Image(Main.class.getResource(imageUrl).toURI().toString()));
        itemNameLabel.setText(itemName);
        this.mainList = mainList;
    }

    public void addNewItem(String itemName) {

        String menuItemName = itemName.toLowerCase();
        // TODO need to retrieve type from database
        String itemType = "food";
        int itemQuantity = Integer.parseInt(quantityTextField.getText());

        // TODO handle not integer value and negative integer

        for (int i = 0; i < itemQuantity; i++) {
            mainList.add(new FoodDrink(menuItemName, itemType));
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
        System.out.println("Quantity Field changed to " + quantityTextField.getText());
    }


}
