package org.group.project.controllers.waiter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.classes.*;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.exceptions.TextFileNotFoundException;

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

        // TODO comment
        try {

            UserManagement userManagement = new UserManagement();
            userManagement.updateDriverList(assignedDriverComboBox);

        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    "Error",
                    e.getMessage()
            );
            e.printStackTrace();
        }

        saveButton.setOnAction(e -> {

            try {

                Waiter waiter = (Waiter) Main.getCurrentUser();
                Driver selectedDriver = assignedDriverComboBox
                        .getValue();
                int searchDriverId = selectedDriver.getUserId();
                int driverId = searchDriverId;

                boolean isSuccessful = waiter.approveDeliveryOrder(
                        currentOrder,
                        driverId
                );
                if (isSuccessful) {
                    AlertPopUpWindow.displayInformationWindow(
                            "Delivery Order Update",
                            String.format("Delivery order no.%d " +
                                            "was approved successfully.",
                                    currentOrder.getOrderId()),
                            "Ok"
                    );
                }

            } catch (TextFileNotFoundException ex) {
                AlertPopUpWindow.displayErrorWindow(
                        "Error",
                        ex.getMessage()
                );
                ex.printStackTrace();
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
