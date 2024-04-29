package org.group.project.controllers.waiter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.classes.FoodDrink;
import org.group.project.classes.Menu;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.exceptions.TextFileNotFoundException;

import java.util.List;

public class WaiterDineinAddOrderController {

    @FXML
    private VBox vbox;

    @FXML
    private ComboBox<String> comboItemName;

    @FXML
    private TextField quantityTextField;

    @FXML
    private Button addItemButton;

    @FXML
    private Button cancelButton;

    private List<FoodDrink> orderList;

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

        comboItemName.setValue("Choose Item");

        try {

            Menu menu = new Menu();
            menu.updateDineinMenuChoiceBox(
                    comboItemName
            );

        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    "Error",
                    e.getMessage()
            );
            e.printStackTrace();
        }

        addItemButton.setOnAction(e -> {

            if (
                    !comboItemName.getValue().equals("Choose Item")
                            && Integer.parseInt(quantityTextField.getText()) > 0
            ) {

                String selectedItemName = comboItemName.getValue().toString();
                // TODo get food type from database
                String selectedItemType = "food";
                int enteredQuantity = Integer.parseInt(quantityTextField.getText());

                orderList.add(new FoodDrink(
                        selectedItemName,
                        selectedItemType,
                        enteredQuantity
                ));

                closeWindow();

            } else {
                AlertPopUpWindow.displayErrorWindow(
                        "Error",
                        "Please enter valid inputs."
                );
                quantityTextField.setText("1");
            }

        });

        cancelButton.setOnAction(e -> {
            closeWindow();
        });

    }

    public void getCurrentOrderList(
            List<FoodDrink> orderList
    ) {
        this.orderList = orderList;

    }

    public void closeWindow() {
        Stage stage = (Stage) vbox.getScene().getWindow();
        stage.close();
    }

}
