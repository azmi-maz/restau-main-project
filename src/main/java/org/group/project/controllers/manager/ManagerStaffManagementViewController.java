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
import org.group.project.scenes.MainScenesMap;
import org.group.project.scenes.WindowSize;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * This class allows the manager to view staff list.
 *
 * @author azmi_maz
 */
public class ManagerStaffManagementViewController {
    private static final String BG_IMAGE = "images" +
            "/background/manager-main" +
            ".jpg";
    private static final String FIRST_NAME_COLUMN = "First Name";
    private static final String CENTERED = "-fx-alignment: CENTER;";
    private static final String LAST_NAME_COLUMN = "Last Name";
    private static final String HOURS_LEFT_COLUMN = "Hours Left";
    private static final String TOTAL_HOURS_COLUMN = "Total Hours Worked";
    private static final String POSITION_COLUMN = "Position";
    private static final String TO_WORK = "numOfHoursToWork";
    private static final String TOTAL_HOURS = "numOfTotalHoursWorked";
    private static final int COLUMN_WIDTH_150 = 150;
    private static final int COLUMN_WIDTH_35 = 35;
    private static final String ACTION_COLUMN = "Action";
    private static final String EDIT_BUTTON = "edit";
    private static final int BUTTON_WIDTH = 15;
    private static final int BUTTON_HEIGHT = 15;
    private static final String EDIT_USER_WINDOW = "smallwindows/" +
            "manager-edit-user" +
            ".fxml";
    private static final String EDIT_USER_TITLE = "Edit Details";
    private static final String DELETE_BUTTON = "delete";
    private static final String USER_NOT_FOUND = "This user does not exist.";
    private static final String OK_DONE = "OK_DONE";
    private static final String UPDATE_TITLE = "Staff Update";
    private static final String UPDATE_MESSAGE = "%s was deleted " +
            "successfully.";
    private static final String OK = "Ok";
    private static final String ADD_STAFF_WINDOW = "smallwindows/" +
            "manager-add-user" +
            ".fxml";
    private static final String ADD_STAFF_TITLE = "Add Staff";
    private static final String DELETE_STAFF_TITLE = "Delete User Request";
    private static final String DELETE_STAFF_MESSAGE = "Do you want to " +
            "delete %s?";
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

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        Image bgImage = null;
        try {
            bgImage = new Image(Main.class
                    .getResource(BG_IMAGE).toURI().toString());
        } catch (URISyntaxException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }

        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO,
                BackgroundSize.AUTO, false,
                false, true, true);

        borderPane.setBackground(new Background(new BackgroundImage(bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));

        refreshStaffList();

        firstNameColumn.setText(FIRST_NAME_COLUMN);
        firstNameColumn.setMinWidth(COLUMN_WIDTH_150);
        firstNameColumn.setStyle(CENTERED);
        firstNameColumn.setCellValueFactory(cellData -> {
            String firstName = cellData.getValue().getFirstNameForDisplay();
            return new SimpleObjectProperty<>(firstName);
        });

        lastNameColumn.setText(LAST_NAME_COLUMN);
        lastNameColumn.setMinWidth(COLUMN_WIDTH_150);
        lastNameColumn.setStyle(CENTERED);
        lastNameColumn.setCellValueFactory(cellData -> {
            String lastName = cellData.getValue().getLastNameForDisplay();
            return new SimpleObjectProperty<>(lastName);
        });

        hoursLeftColumn.setText(HOURS_LEFT_COLUMN);
        hoursLeftColumn.setMinWidth(COLUMN_WIDTH_150);
        hoursLeftColumn.setStyle(CENTERED);
        hoursLeftColumn.setCellValueFactory(
                new PropertyValueFactory<>(TO_WORK));

        totalHoursColumn.setText(TOTAL_HOURS_COLUMN);
        totalHoursColumn.setMinWidth(COLUMN_WIDTH_150);
        totalHoursColumn.setStyle(CENTERED);
        totalHoursColumn.setCellValueFactory(
                new PropertyValueFactory<>(TOTAL_HOURS));

        positionColumn.setText(POSITION_COLUMN);
        positionColumn.setMinWidth(COLUMN_WIDTH_150);
        positionColumn.setStyle(CENTERED);
        positionColumn.setCellValueFactory(cellData -> {

            UserManagement userManagement = null;
            try {
                userManagement = new UserManagement();
            } catch (TextFileNotFoundException e) {
                AlertPopUpWindow.displayErrorWindow(
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

        actionButtonColumn.setText(ACTION_COLUMN);

        actionButtonColumn1.setMinWidth(COLUMN_WIDTH_35);
        actionButtonColumn1.setStyle(CENTERED);
        actionButtonColumn1.setCellValueFactory(cellData -> {
            Button editButton = new Button();
            ImageLoader.setUpGraphicButton(editButton,
                    BUTTON_WIDTH, BUTTON_HEIGHT, EDIT_BUTTON);

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
                                        EDIT_USER_WINDOW));

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

                        editStage.setTitle(EDIT_USER_TITLE);

                        editStage.initModality(Modality.APPLICATION_MODAL);

                        editStage.showAndWait();

                        refreshStaffList();

                    } catch (IOException ex) {
                        AlertPopUpWindow.displayErrorWindow(
                                ex.getMessage()
                        );
                        ex.printStackTrace();
                    }

                });

            } catch (TextFileNotFoundException e) {
                AlertPopUpWindow.displayErrorWindow(
                        e.getMessage()
                );
                e.printStackTrace();
            }


            return new SimpleObjectProperty<>(editButton);
        });

        actionButtonColumn2.setMinWidth(COLUMN_WIDTH_35);
        actionButtonColumn2.setStyle(CENTERED);
        actionButtonColumn2.setCellValueFactory(cellData -> {
            Button deleteButton = new Button();
            ImageLoader.setUpGraphicButton(deleteButton,
                    BUTTON_WIDTH, BUTTON_HEIGHT, DELETE_BUTTON);
            String firstName = cellData.getValue().getFirstNameForDisplay();
            User currentUser = cellData.getValue();

            int searchUserId = -1;
            try {
                searchUserId = currentUser.getUserId();
            } catch (TextFileNotFoundException e) {
                AlertPopUpWindow.displayErrorWindow(
                        e.getMessage()
                );
                e.printStackTrace();
            }

            if (searchUserId == -1) {
                AlertPopUpWindow.displayErrorWindow(
                        USER_NOT_FOUND
                );
            }

            int selectedUserId = searchUserId;
            deleteButton.setOnAction(e -> {
                Manager manager = (Manager) MainScenesMap.getCurrentUser();

                Optional<ButtonType> userChoice =
                        promptForUserAcknowledgement(firstName);

                if (userChoice.get()
                        .getButtonData().toString()
                        .equalsIgnoreCase(OK_DONE)) {

                    try {
                        boolean isSuccessful = manager
                                .removeStaffMember(selectedUserId);
                        if (isSuccessful) {
                            AlertPopUpWindow.displayInformationWindow(
                                    UPDATE_TITLE,
                                    String.format(
                                            UPDATE_MESSAGE,
                                            firstName
                                    ), OK
                            );
                        }
                        refreshStaffList();
                    } catch (TextFileNotFoundException ex) {
                        AlertPopUpWindow.displayErrorWindow(
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
                                ADD_STAFF_WINDOW));

                VBox vbox = fxmlLoader.load();

                Scene editScene = new Scene(vbox,
                        WindowSize.SMALL.WIDTH,
                        WindowSize.SMALL.HEIGHT);

                Stage editStage = new Stage();
                editStage.setScene(editScene);

                editStage.setTitle(ADD_STAFF_TITLE);

                editStage.initModality(Modality.APPLICATION_MODAL);

                editStage.showAndWait();

                refreshStaffList();

            } catch (IOException ex) {
                AlertPopUpWindow.displayErrorWindow(
                        ex.getMessage()
                );
                ex.printStackTrace();
            }

        });
    }

    private void refreshStaffList() {

        // This clears up the list everytime it refreshes.
        staffListTable.getItems().clear();
        data.clear();

        try {

            UserManagement userManagement = new UserManagement();
            userManagement.getStaffData(data);

        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }

    }

    private Optional<ButtonType> promptForUserAcknowledgement(String user) {
        return AlertPopUpWindow.displayConfirmationWindow(
                DELETE_STAFF_TITLE,
                String.format(
                        DELETE_STAFF_MESSAGE,
                        user
                )
        );
    }


}
