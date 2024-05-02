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

/**
 * This class allows the waiter to edit the current order.
 *
 * @author azmi_maz
 */
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

        setTextFieldToDisabled();

        saveChangesButton.setOnAction(e -> {

            if (
                    Integer.parseInt(quantityTextField.getText()) > 0
            ) {

                String currentItemName = comboItemName.getValue();
                int updatedQty = Integer.parseInt(quantityTextField.getText());

                for (FoodDrink item : orderList) {
                    if (currentItemName.equalsIgnoreCase(item.getItemName())) {
                        item.setQuantity(updatedQty);
                    }
                }

                closeWindow();

            } else {
                AlertPopUpWindow.displayErrorWindow(
                        "Please enter valid number."
                );
                quantityTextField.setText("1");
            }
        });

        cancelButton.setOnAction(e -> {
            closeWindow();
        });

    }

    /**
     * This populates the selected item details.
     *
     * @param itemName     - the name of the selected item.
     * @param itemQuantity - the current quantity of the item.
     * @param orderList    - the current order to be updated.
     */
    public void setItemDetails(
            String itemName,
            String itemQuantity,
            List<FoodDrink> orderList
    ) {
        comboItemName.setValue(itemName);
        quantityTextField.setText(itemQuantity);
        this.orderList = orderList;
    }

    private void setTextFieldToDisabled() {
        comboItemName.setOnMousePressed(e -> {
            comboItemName.setDisable(true);
        });

        comboItemName.setOnMouseReleased(e -> {
            comboItemName.setDisable(false);
        });

    }

    private void closeWindow() {
        Stage stage = (Stage) vbox.getScene().getWindow();
        stage.close();
    }
}
