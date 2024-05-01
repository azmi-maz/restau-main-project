package org.group.project.controllers.driver;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.classes.Customer;
import org.group.project.classes.DeliveryOrder;
import org.group.project.classes.Driver;
import org.group.project.classes.Order;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.exceptions.TextFileNotFoundException;

/**
 * This class enables the driver to view a delivery order details.
 */
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

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        deliveryCompletedButton.setOnAction(e -> {
            Driver driver = (Driver) Main.getCurrentUser();

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
                "Order Delivery",
                String.format(
                        "Delivery is successful for order" +
                                " no.%d!",
                        currentOrder.getOrderId()),
                "Ok"
        );
    }

    private void closeWindow() {
        Stage stage = (Stage) vbox.getScene().getWindow();
        stage.close();
    }
}
