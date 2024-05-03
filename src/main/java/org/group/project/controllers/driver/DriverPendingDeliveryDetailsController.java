package org.group.project.controllers.driver;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.classes.Customer;
import org.group.project.classes.DeliveryOrder;
import org.group.project.classes.Driver;
import org.group.project.classes.Order;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.exceptions.TextFileNotFoundException;
import org.group.project.scenes.MainScenesMap;

/**
 * This class enables the driver to view a delivery order details.
 *
 * @author azmi_maz
 */
public class DriverPendingDeliveryDetailsController {
    private static final String SUCCESS = "Order Delivery";
    private static final String OK = "Ok";
    private static final String SUCCESS_MESSAGE = "Delivery is successful " +
            "for order no.%d!";
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

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        deliveryCompletedButton.setOnAction(e -> {
            Driver driver = (Driver) MainScenesMap.getCurrentUser();

            try {
                boolean isSuccessful = driver.confirmDeliveryOrder(
                        currentOrder);
                if (isSuccessful) {
                    currentOrder.notifyCustomer(
                            currentOrder.getCustomer(),
                            true
                    );
                    promptDeliverySuccessful(currentOrder);
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
     * This method sets up the selected order details.
     *
     * @param currentOrder - the selected order.
     */
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

    private void promptDeliverySuccessful(
            DeliveryOrder currentOrder) {
        AlertPopUpWindow.displayInformationWindow(
                SUCCESS,
                String.format(
                        SUCCESS_MESSAGE,
                        currentOrder.getOrderId()),
                OK
        );
    }

    private void closeWindow() {
        Stage stage = (Stage) vbox.getScene().getWindow();
        stage.close();
    }
}
