package org.group.project.controllers.driver;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.classes.*;

import java.io.IOException;

public class DriverPendingDeliveryDetailsController {

    @FXML
    private VBox vbox;

    @FXML
    private TextField customerIdTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private Button deliveryCompletedButton;

    @FXML
    private Button returnButton;

    private DeliveryOrder currentOrder;

    public void initialize() {

        deliveryCompletedButton.setOnAction(e -> {

            // TODO handle try catch
            try {
                currentOrder.confirmDeliverOrder();
                currentOrder.notifyCustomer(
                        currentOrder.getCustomer(),
                        true
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            promptDeliverySuccessful();

            closeWindow();
        });

        returnButton.setOnAction(e -> {
            closeWindow();
        });

    }

    public void setOrderDetails(
            Order currentOrder
    ) {
        Customer customer = currentOrder.getCustomer();
        customerIdTextField.setText(String.valueOf(customer.getCustomerId()));
        firstNameTextField.setText(customer.getFirstNameForDisplay());
        lastNameTextField.setText(customer.getLastNameForDisplay());
        addressTextField.setText(customer.getDeliveryAddress());
        this.currentOrder = (DeliveryOrder) currentOrder;
    }

    public void promptDeliverySuccessful() {
        AlertPopUpWindow.displayInformationWindow(
                "Order Delivery",
                "Delivery is successful!",
                "Ok"
        );
    }

    private void closeWindow() {
        Stage stage = (Stage) vbox.getScene().getWindow();
        stage.close();
    }
}
