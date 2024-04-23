package org.group.project.controllers.waiter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.classes.FoodDrink;
import org.group.project.classes.auxiliary.AlertPopUpWindow;

import java.util.List;

public class WaiterDineinEditOrderController {

    @FXML
    private VBox vbox;

    @FXML
    private ComboBox<String> comboItemName;

    @FXML
    private TextField quantityTextField;

    @FXML
    private Button saveChangesButton;

    @FXML
    private Button cancelButton;

    private List<FoodDrink> orderList;

    // TODO comment
    public void initialize() {

        quantityTextField.setOnAction(e -> {
            int quantityValue = Integer.parseInt(quantityTextField.getText());
            if (quantityValue < 0) {
                AlertPopUpWindow.displayErrorWindow(
                        "Error",
                        "Quantity cannot be less than 0"
                );
                quantityTextField.setText("1");
            }
        });

        setTextFieldToDisabled();

        saveChangesButton.setOnAction(e -> {

            if (
                    Integer.parseInt(quantityTextField.getText()) > 0
            ) {

                String currentItemName = comboItemName.getValue();
                // TODO handle invalid quantity
                int updatedQty = Integer.parseInt(quantityTextField.getText());

                for (FoodDrink item : orderList) {
                    if (currentItemName.equalsIgnoreCase(item.getItemName())) {
                        item.setQuantity(updatedQty);
                    }
                }

                closeWindow();

            } else {
                AlertPopUpWindow.displayErrorWindow(
                        "Error",
                        "Please enter valid number."
                );
                quantityTextField.setText("1");
            }
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
        comboItemName.setValue(itemName);
        quantityTextField.setText(itemQuantity);
        this.orderList = orderList;
    }

    // TODO comment
    public void setTextFieldToDisabled() {
        comboItemName.setOnMousePressed(e -> {
            comboItemName.setDisable(true);
        });

        comboItemName.setOnMouseReleased(e -> {
            comboItemName.setDisable(false);
        });

    }

    public void closeWindow() {
        Stage stage = (Stage) vbox.getScene().getWindow();
        stage.close();
    }
}
