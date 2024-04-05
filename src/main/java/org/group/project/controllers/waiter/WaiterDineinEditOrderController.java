package org.group.project.controllers.waiter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.classes.FoodDrink;

import java.util.List;

public class WaiterDineinEditOrderController {

    @FXML
    private VBox vbox;

    @FXML
    private TextField itemNameTextField;

    @FXML
    private TextField quantityTextField;

    @FXML
    private Button saveChangesButton;

    @FXML
    private Button cancelButton;

    private List<FoodDrink> orderList;

    // TODO comment
    public void initialize() {

        setTextFieldToDisabled();

        saveChangesButton.setOnAction(e -> {
            String currentItemName = itemNameTextField.getText();
            // TODO handle invalid quantity
            int updatedQty = Integer.parseInt(quantityTextField.getText());

            for (FoodDrink item : orderList) {
                if (currentItemName.equalsIgnoreCase(item.getItemName())) {
                    item.setQuantity(updatedQty);
                }
            }

            closeWindow();
        });

        cancelButton.setOnAction(e -> {
            closeWindow();
        });

    }

    // TODO comment
    public void setItemDetails(
            String itemName,
            String itemQuantity,
            List<FoodDrink> orderList
    ) {
        itemNameTextField.setText(itemName);
        quantityTextField.setText(itemQuantity);
        this.orderList = orderList;
    }

    // TODO comment
    public void setTextFieldToDisabled() {
        itemNameTextField.setOnMousePressed(e -> {
            itemNameTextField.setDisable(true);
        });

        itemNameTextField.setOnMouseReleased(e -> {
            itemNameTextField.setDisable(false);
        });

    }

    public void closeWindow() {
        Stage stage = (Stage) vbox.getScene().getWindow();
        stage.close();
    }
}
