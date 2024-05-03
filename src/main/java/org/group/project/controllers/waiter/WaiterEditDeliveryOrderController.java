package org.group.project.controllers.waiter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.classes.*;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.exceptions.TextFileNotFoundException;
import org.group.project.scenes.MainScenesMap;

/**
 * This class enables the waiter to assign driver and approve a delivery order.
 *
 * @author azmi_maz
 */
public class WaiterEditDeliveryOrderController {
    private static final String SUCCESS_UPDATE_TITLE = "Delivery Order Update";
    private static final String SUCCESS_UPDATE_MESSAGE = "Delivery order " +
            "no.%d was approved successfully.";
    private static final String OK = "Ok";
    private static final String SELECT_DRIVER = "Select driver";
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

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        setTextFieldToDisabled();

        try {

            UserManagement userManagement = new UserManagement();
            userManagement.updateDriverList(assignedDriverComboBox);

        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }

        saveButton.setOnAction(e -> {

            try {

                Waiter waiter = (Waiter) MainScenesMap.getCurrentUser();
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
                            SUCCESS_UPDATE_TITLE,
                            String.format(SUCCESS_UPDATE_MESSAGE,
                                    currentOrder.getOrderId()), OK
                    );
                }

            } catch (TextFileNotFoundException ex) {
                AlertPopUpWindow.displayErrorWindow(
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

    /**
     * This method populates the selected delivery order details.
     *
     * @param deliveryOrder - the selected delivery order.
     */
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
            deliveryTimeTextField.setText(
                    currentOrder.getDeliveryTimeInFormat());
        } else {
            deliveryTimeTextField.setText("");
        }

        assignedDriverComboBox.setPromptText(SELECT_DRIVER);
    }

    // This disables the text fields so waiter does not edit them.
    private void setTextFieldToDisabled() {
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

    private void closeWindow() {
        Stage stage = (Stage) vbox.getScene().getWindow();
        stage.close();
    }

}
