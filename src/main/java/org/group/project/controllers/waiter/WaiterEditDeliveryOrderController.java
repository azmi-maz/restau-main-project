package org.group.project.controllers.waiter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.classes.Customer;
import org.group.project.classes.DeliveryOrder;
import org.group.project.classes.Driver;

import java.io.FileNotFoundException;
import java.io.IOException;

public class WaiterEditDeliveryOrderController {

    @FXML
    private TextField customerIdTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField orderStatusTextField;

    @FXML
    private TextField deliveryTimeTextField;

    @FXML
    private ComboBox<Driver> assignedDriverComboBox;

    @FXML
    private Button saveButton;

    @FXML
    private Button returnButton;

    @FXML
    private VBox vbox;

    private DeliveryOrder currentOrder;

    public void initialize() {

        setTextFieldToDisabled();

        // TODO comment & try catch
        try {
            Driver.getUpdatedDriverList(assignedDriverComboBox);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        saveButton.setOnAction(e -> {

            // TODO try catch
            // TODO if no driver chosen, throws error here and need to catch it
            // TODO or just onChange and notify the user before save button
            String driverName = assignedDriverComboBox
                    .getValue().getUsername();
            String searchDriverId;
            try {
                searchDriverId = currentOrder.getAssignedDriverId(driverName);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            String driverId = searchDriverId;

            // TODO try catch
            try {
                currentOrder.approveDeliverOrder(driverId);
                currentOrder.notifyCustomer(
                        currentOrder.getCustomer(),
                        true
                );

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            closeWindow();
        });

        returnButton.setOnAction(e -> {
            closeWindow();
        });

    }

    public void populateOrderDetails(
            DeliveryOrder deliveryOrder
    ) {
        currentOrder = deliveryOrder;
        Customer customer = currentOrder.getCustomer();
        customerIdTextField.setText(
                String.valueOf(customer.getCustomerId())
        );
        firstNameTextField.setText(
                customer.getFirstNameForDisplay()
        );
        lastNameTextField.setText(
                customer.getLastNameForDisplay()
        );
        addressTextField.setText(
                customer.getDeliveryAddress()
        );
        orderStatusTextField.setText(currentOrder.getOrderStatus());

        if (currentOrder.getDeliveryTime() != null) {
            deliveryTimeTextField.setText(currentOrder.getDeliveryTimeInFormat());
        } else {
            deliveryTimeTextField.setText("");
        }

        assignedDriverComboBox.setPromptText("Select driver");
    }


    // TODO comment
    public void setTextFieldToDisabled() {
        customerIdTextField.setOnMousePressed(e -> {
            customerIdTextField.setDisable(true);
        });

        customerIdTextField.setOnMouseReleased(e -> {
            customerIdTextField.setDisable(false);
        });

        firstNameTextField.setOnMousePressed(e -> {
            firstNameTextField.setDisable(true);
        });

        firstNameTextField.setOnMouseReleased(e -> {
            firstNameTextField.setDisable(false);
        });

        lastNameTextField.setOnMousePressed(e -> {
            lastNameTextField.setDisable(true);
        });

        lastNameTextField.setOnMouseReleased(e -> {
            lastNameTextField.setDisable(false);
        });

        addressTextField.setOnMousePressed(e -> {
            addressTextField.setDisable(true);
        });

        addressTextField.setOnMouseReleased(e -> {
            addressTextField.setDisable(false);
        });

        orderStatusTextField.setOnMousePressed(e -> {
            orderStatusTextField.setDisable(true);
        });

        orderStatusTextField.setOnMouseReleased(e -> {
            orderStatusTextField.setDisable(false);
        });

        deliveryTimeTextField.setOnMousePressed(e -> {
            deliveryTimeTextField.setDisable(true);
        });

        deliveryTimeTextField.setOnMouseReleased(e -> {
            deliveryTimeTextField.setDisable(false);
        });

    }

    public void closeWindow() {
        Stage stage = (Stage) vbox.getScene().getWindow();
        stage.close();
    }

}
