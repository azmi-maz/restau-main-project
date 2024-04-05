package org.group.project.controllers.waiter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.classes.FoodDrink;

import java.util.List;

public class WaiterDineinAddOrderController {

    @FXML
    private VBox vbox;

    @FXML
    private ComboBox comboItemName;

    @FXML
    private TextField quantityTextField;

    @FXML
    private Button addItemButton;

    @FXML
    private Button cancelButton;

    private List<FoodDrink> orderList;

    public void initialize() {

        comboItemName.setValue("Choose Item");

        // TODO definitely get from database to populate this
        // TODO capitalize words
        // TODO arrange alphabetically
        comboItemName.getItems().add("steak au poivre");
        comboItemName.getItems().add("marseilles shrimp stew");
        comboItemName.getItems().add("duck a lorange");
        comboItemName.getItems().add("lyon chicken");
        comboItemName.getItems().add("coq au vin");
        comboItemName.getItems().add("stuffed pork tenderloin");
        comboItemName.getItems().add("lobster thermidor");
        comboItemName.getItems().add("strip steak frite");
        comboItemName.getItems().add("classic duck confit");
        comboItemName.getItems().add("pear tarte tatin");
        comboItemName.getItems().add("chocolate souffle");
        comboItemName.getItems().add("classic creme brulee");
        comboItemName.getItems().add("croquembouche");
        comboItemName.getItems().add("manhattan");
        comboItemName.getItems().add("margarita");
        comboItemName.getItems().add("cosmopolitan");
        comboItemName.getItems().add("bloody mary");
        comboItemName.getItems().add("white russian");
        comboItemName.getItems().add("mojito");
        comboItemName.getItems().add("negroni");
        comboItemName.getItems().add("daiquiri");
        comboItemName.getItems().add("martini");
        comboItemName.getItems().add("pina colada");
        comboItemName.getItems().add("irish coffee");
        comboItemName.getItems().add("long island iced tea");

        addItemButton.setOnAction(e -> {

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
