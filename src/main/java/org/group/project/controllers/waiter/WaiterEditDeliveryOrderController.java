package org.group.project.controllers.waiter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.classes.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    private int orderId;

    public void initialize() {

        setTextFieldToDisabled();

        // TODO add drivers from database, create drivers with updated data
        List<String> driverList;
        // TODO try catch
        // TODO note; initialize or other 'main' function should not throw error
        try {
            driverList = HelperMethods.getDataById("USERS", "4");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (driverList != null) {
            // TODO foreach loop
            // TODO make sure to display name properly - displayed ok
            assignedDriverComboBox.getItems().add(new Driver(
                    Integer.parseInt(driverList
                            .get(DataFileStructure
                                    .getIndexColOfUniqueId("USERS"))),
                    driverList.get(DataFileStructure
                            .getIndexByColName("USERS", "firstName")),
                    driverList.get(DataFileStructure
                            .getIndexByColName("USERS", "lastName")),
                    driverList.get(DataFileStructure
                            .getIndexByColName("USERS", "username"))
            ));
        }

        saveButton.setOnAction(e -> {
            // TODO try catch
            int searchDriverId = -1;
            try {
                // TODO if no driver chosen, throws error here and need to catch it
                // TODO or just onChange and notify the user before save button
                searchDriverId = HelperMethods
                        .findUserIdByUsername(assignedDriverComboBox
                                .getValue().getUsername());
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            String newOrderStatus = "pending-kitchen";
            String driverId = String.valueOf(searchDriverId);
            // TODO note h-m if it can be applied elsewhere
            String estimatedDeliveryTime = estimatedDeliveryTime()
                    .format(DateTimeFormatter.ofPattern("h-m"));

            // TODO try catch
            try {
                DataManager.editColumnDataByUniqueId("ORDERS",
                        orderId, "orderStatus",
                        newOrderStatus);
                DataManager.editColumnDataByUniqueId("ORDERS",
                        orderId, "assignedDriver",
                        driverId);
                DataManager.editColumnDataByUniqueId("ORDERS",
                        orderId, "deliveryTime",
                        estimatedDeliveryTime);
                // TODO notify customer here after approval

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
            int orderId,
            Customer customer,
            String orderStatus,
            LocalTime deliveryTime
    ) {
        this.orderId = orderId;
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
        orderStatusTextField.setText(orderStatus);

        if (deliveryTime != null) {
            deliveryTimeTextField.setText(deliveryTime.format(DateTimeFormatter.ofPattern("hh:mm a")));
        } else {
            deliveryTimeTextField.setText("pending-approval");
        }

        assignedDriverComboBox.setPromptText("Select driver");
    }

    // TODO comment
    public LocalTime estimatedDeliveryTime() {
        // TODO final variable for added time 30 minutes
        LocalTime timeOfApproval = LocalTime.now();
        return timeOfApproval.plusMinutes(30);
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
