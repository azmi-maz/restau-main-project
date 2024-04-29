package org.group.project.controllers.manager;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.classes.Manager;
import org.group.project.classes.Staff;
import org.group.project.classes.User;
import org.group.project.classes.UserManagement;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.exceptions.TextFileNotFoundException;
import org.group.project.scenes.WindowSize;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

public class ManagerStaffManagementViewController {

    @FXML
    private TableColumn<Staff, String> firstNameColumn;

    @FXML
    private TableColumn<Staff, String> lastNameColumn;

    @FXML
    private TableColumn<Staff, Integer> hoursLeftColumn;

    @FXML
    private TableColumn<Staff, Integer> totalHoursColumn;

    @FXML
    private TableColumn<Staff, String> positionColumn;

    @FXML
    private TableColumn<Staff, Button> actionButtonColumn;

    @FXML
    private TableColumn<Staff, Button> actionButtonColumn1;

    @FXML
    private TableColumn<Staff, Button> actionButtonColumn2;

    @FXML
    private TableView<Staff> staffListTable = new TableView<>();
    private ObservableList<Staff> data =
            FXCollections.observableArrayList();

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button addStaffButton;

    public void initialize() throws URISyntaxException {

        Image bgImage = new Image(Main.class.getResource("images" +
                "/background/manager-main" +
                ".jpg").toURI().toString());

        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO,
                BackgroundSize.AUTO, false,
                false, true, true);

        borderPane.setBackground(new Background(new BackgroundImage(bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));

        refreshStaffList();

        firstNameColumn.setText("First Name");
        firstNameColumn.setMinWidth(150);
        firstNameColumn.setStyle("-fx-alignment: CENTER;");
        firstNameColumn.setCellValueFactory(cellData -> {
            String firstName = cellData.getValue().getFirstNameForDisplay();
            return new SimpleObjectProperty<>(firstName);
        });

        lastNameColumn.setText("Last Name");
        lastNameColumn.setMinWidth(150);
        lastNameColumn.setStyle("-fx-alignment: CENTER;");
        lastNameColumn.setCellValueFactory(cellData -> {
            String lastName = cellData.getValue().getLastNameForDisplay();
            return new SimpleObjectProperty<>(lastName);
        });

        hoursLeftColumn.setText("Hours Left");
        hoursLeftColumn.setMinWidth(150);
        hoursLeftColumn.setStyle("-fx-alignment: CENTER;");
        hoursLeftColumn.setCellValueFactory(
                new PropertyValueFactory<>("numOfHoursToWork"));

        totalHoursColumn.setText("Total Hours Worked");
        totalHoursColumn.setMinWidth(150);
        totalHoursColumn.setStyle("-fx-alignment: CENTER;");
        totalHoursColumn.setCellValueFactory(
                new PropertyValueFactory<>("numOfTotalHoursWorked"));

        positionColumn.setText("Position");
        positionColumn.setMinWidth(150);
        positionColumn.setStyle("-fx-alignment: CENTER;");
        positionColumn.setCellValueFactory(cellData -> {

            UserManagement userManagement = null;
            try {
                userManagement = new UserManagement();
            } catch (TextFileNotFoundException e) {
                AlertPopUpWindow.displayErrorWindow(
                        "Error",
                        e.getMessage()
                );
                e.printStackTrace();
            }
            User user = userManagement.getUserByUsername(
                    cellData.getValue().getUsername()
            );
            String userType = userManagement.getStaffClass(user);
            if (userType != "") {
                String temp = userType;
                userType = temp.substring(0, 1).toUpperCase()
                        + temp.substring(1);
            }

            return new SimpleObjectProperty<>(userType);
        });

        actionButtonColumn.setText("Action");

        actionButtonColumn1.setMinWidth(35);
        actionButtonColumn1.setStyle("-fx-alignment: CENTER;");
        actionButtonColumn1.setCellValueFactory(cellData -> {
            Button editButton = new Button();
            ImageLoader.setUpGraphicButton(editButton,
                    15, 15, "edit");

            try {

                UserManagement userManagement = new UserManagement();
                Staff staff = (Staff) userManagement.getUserByUsername(
                        cellData.getValue().getUsername()
                );
                int searchStaffId = -1;
                searchStaffId = userManagement.getUserIdByUsername(
                        staff.getUsername()
                );
                String staffId = String.valueOf(searchStaffId);

                editButton.setOnAction(e -> {

                    try {
                        FXMLLoader fxmlLoader =
                                new FXMLLoader(Main.class.getResource(
                                        "smallwindows/manager-edit-user" +
                                                ".fxml"));

                        VBox vbox = fxmlLoader.load();

                        ManagerStaffManagementDetailsController controller =
                                fxmlLoader.getController();

                        controller.setStaffDetails(
                                staffId,
                                staff
                        );

                        Scene editScene = new Scene(vbox,
                                WindowSize.SMALL.WIDTH,
                                WindowSize.SMALL.HEIGHT);

                        Stage editStage = new Stage();
                        editStage.setScene(editScene);
                        // TODO Should final variable this
                        editStage.setTitle("Edit Details");

                        editStage.initModality(Modality.APPLICATION_MODAL);

                        editStage.showAndWait();

                        refreshStaffList();

                    } catch (IOException ex) {
                        AlertPopUpWindow.displayErrorWindow(
                                "Error",
                                ex.getMessage()
                        );
                        ex.printStackTrace();
                    }

                });

            } catch (TextFileNotFoundException e) {
                AlertPopUpWindow.displayErrorWindow(
                        "Error",
                        e.getMessage()
                );
                e.printStackTrace();
            }


            return new SimpleObjectProperty<>(editButton);
        });

        actionButtonColumn2.setMinWidth(35);
        actionButtonColumn2.setStyle("-fx-alignment: CENTER;");
        actionButtonColumn2.setCellValueFactory(cellData -> {
            Button deleteButton = new Button();
            ImageLoader.setUpGraphicButton(deleteButton,
                    15, 15, "delete");
            String firstName = cellData.getValue().getFirstNameForDisplay();
            User currentUser = cellData.getValue();

            int searchUserId = -1;
            try {
                searchUserId = currentUser.getUserId();
            } catch (TextFileNotFoundException e) {
                AlertPopUpWindow.displayErrorWindow(
                        "Error",
                        e.getMessage()
                );
                e.printStackTrace();
            }

            if (searchUserId == -1) {
                AlertPopUpWindow.displayErrorWindow(
                        "Error",
                        "This user does not exist."
                );
            }

            // TODO comment
            int selectedUserId = searchUserId;
            deleteButton.setOnAction(e -> {
                Manager manager = (Manager) Main.getCurrentUser();

                Optional<ButtonType> userChoice =
                        promptForUserAcknowledgement(firstName);

                if (userChoice.get()
                        .getButtonData().toString()
                        .equalsIgnoreCase("OK_DONE")) {

                    try {
                        boolean isSuccessful = manager
                                .removeStaffMember(selectedUserId);
                        if (isSuccessful) {
                            AlertPopUpWindow.displayInformationWindow(
                                    "Staff Update",
                                    String.format(
                                            "%s was deleted successfully.",
                                            firstName
                                    ),
                                    "Ok"
                            );
                        }
                        refreshStaffList();
                    } catch (TextFileNotFoundException ex) {
                        AlertPopUpWindow.displayErrorWindow(
                                "Error",
                                ex.getMessage()
                        );
                        ex.printStackTrace();
                    }
                }

            });

            return new SimpleObjectProperty<>(deleteButton);
        });

        staffListTable.setItems(data);

        addStaffButton.setOnAction(e -> {

            try {
                FXMLLoader fxmlLoader =
                        new FXMLLoader(Main.class.getResource(
                                "smallwindows/manager-add-user" +
                                        ".fxml"));

                VBox vbox = fxmlLoader.load();

                Scene editScene = new Scene(vbox,
                        WindowSize.SMALL.WIDTH,
                        WindowSize.SMALL.HEIGHT);

                Stage editStage = new Stage();
                editStage.setScene(editScene);
                // TODO Should final variable this
                editStage.setTitle("Add Staff");

                editStage.initModality(Modality.APPLICATION_MODAL);

                editStage.showAndWait();

                refreshStaffList();

            } catch (IOException ex) {
                AlertPopUpWindow.displayErrorWindow(
                        "Error",
                        ex.getMessage()
                );
                ex.printStackTrace();
            }

        });
    }

    private void refreshStaffList() {

        // TODO comment that this clears up the list everytime it refresh
        staffListTable.getItems().clear();
        data.clear();

        try {

            UserManagement userManagement = new UserManagement();
            userManagement.getStaffData(data);

        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    "Error",
                    e.getMessage()
            );
            e.printStackTrace();
        }

    }

    public Optional<ButtonType> promptForUserAcknowledgement(String user) {
        return AlertPopUpWindow.displayConfirmationWindow(
                "Delete User Request",
                "Do you want to delete " + user + "?"
        );
    }


}
