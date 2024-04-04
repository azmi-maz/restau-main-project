package org.group.project.controllers.manager;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.group.project.Main;
import org.group.project.classes.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
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

    private String userId;

    private List<String> staffList;

    @FXML
    private TableView<Staff> staffListTable = new TableView<>();
    private ObservableList<Staff> data =
            FXCollections.observableArrayList();

    @FXML
    private BorderPane borderPane;

    @FXML
    private VBox vbox;

    @FXML
    private ImageView bgImage;

    public void initialize() throws URISyntaxException, FileNotFoundException {

        Image bgImage = new Image(Main.class.getResource("images" +
                "/background/staff-main" +
                ".jpg").toURI().toString());

        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO,
                BackgroundSize.AUTO, false, false, true, true);

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
            // TODO try catch
            String userType = "";
            try {
                List<String> userDetails = HelperMethods.getUserDataByUsername(cellData.getValue().getUsername());
                userType = userDetails.get(DataFileStructure.getIndexByColName("USERS", "userType"));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            if (userType != "") {
                String temp = userType;
                userType = "";
                userType = temp.substring(0,1).toUpperCase()
                        + temp.substring(1);
            }

            return new SimpleObjectProperty<>(userType);
        });

        actionButtonColumn.setText("Action");

        actionButtonColumn1.setMinWidth(35);
        actionButtonColumn1.setStyle("-fx-alignment: CENTER;");
        actionButtonColumn1.setCellValueFactory(cellData -> {
            Button editButton = new Button();
            ImageLoader.setUpGraphicButton(editButton, 15, 15, "edit");




            return new SimpleObjectProperty<>(editButton);
        });

        actionButtonColumn2.setMinWidth(35);
        actionButtonColumn2.setStyle("-fx-alignment: CENTER;");
        actionButtonColumn2.setCellValueFactory(cellData -> {
            Button deleteButton = new Button();
            ImageLoader.setUpGraphicButton(deleteButton, 15, 15, "delete");
            String firstName = cellData.getValue().getFirstNameForDisplay();
            // TODO try catch
            int searchUserId = -1;
            try {
                searchUserId = HelperMethods.findUserIdByUsername(cellData.getValue().getUsername());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            if (searchUserId == -1) {
                // TODO throw error?
            }

            // TODO comment
            int selectedUserId = searchUserId;
            deleteButton.setOnAction(e -> {
                Optional<ButtonType> userChoice =
                        promptForUserAcknowledgement(firstName);

                if (userChoice.get()
                        .getButtonData().toString()
                        .equalsIgnoreCase("OK_DONE")) {
                    deleteStaff(selectedUserId);

                    // TODO try catch
                    try {
                        refreshStaffList();
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            });

            return new SimpleObjectProperty<>(deleteButton);
        });

        staffListTable.setItems(data);
    }

    private void refreshStaffList() throws FileNotFoundException {

        // TODO comment that this clears up the list everytime it refresh
        staffListTable.getItems().clear();
        data.clear();

        // TODO to filter based on staff only, no customer
        staffList = DataManager.allDataFromFile("USERS");

        for (String user : staffList) {
            List<String> userDetails = List.of(user.split(","));
            String firstName = userDetails.get(DataFileStructure.getIndexByColName("USERS", "firstName"));
            String lastName = userDetails.get(DataFileStructure.getIndexByColName("USERS", "lastName"));
            String username = userDetails.get(DataFileStructure.getIndexByColName("USERS", "username"));
            boolean hasAdminRight = Boolean.parseBoolean(userDetails.get(DataFileStructure.getIndexByColName("USERS", "hasAdminRight")));


            // TODO try catch
            String searchUserType = "";
            try {
                List<String> getUserData = HelperMethods.getUserDataByUsername(username);
                searchUserType = getUserData.get(DataFileStructure.getIndexByColName("USERS", "userType"));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            String userType = searchUserType;

            if (!userType.equalsIgnoreCase("customer")
            && !userType.equalsIgnoreCase("manager")) {
                data.add(new Staff(
                        firstName,
                        lastName,
                        username,
                        hasAdminRight
                ));
            }
        }
    }

    public Optional<ButtonType> promptForUserAcknowledgement(String user) {
        return AlertPopUpWindow.displayConfirmationWindow(
                "Delete User Request",
                "Do you want to delete " + user +"?"
        );
    }

    public void deleteStaff(int userId) {
        // TODO try catch
        try {
            DataManager.deleteUniqueIdFromFile("USERS",
                    userId);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


}