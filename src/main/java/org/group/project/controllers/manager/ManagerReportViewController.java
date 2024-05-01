package org.group.project.controllers.manager;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import org.group.project.Main;
import org.group.project.classes.Manager;
import org.group.project.classes.Report;
import org.group.project.classes.ReportManager;
import org.group.project.classes.User;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.exceptions.TextFileNotFoundException;

import java.net.URISyntaxException;
import java.util.Optional;

/**
 * This class allows manager to view reports.
 */
public class ManagerReportViewController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private ChoiceBox<String> reportTypeChoiceBox;

    @FXML
    private TextArea reportTextArea;

    @FXML
    private ListView<Report> generatedReportList;

    private User currentUser;

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        try {

            ReportManager reportManager = new ReportManager();
            reportManager.updateReportTypeChoiceBox(
                    reportTypeChoiceBox
            );

        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    "Error",
                    e.getMessage()
            );
            e.printStackTrace();
        }

        Image bgImage = null;
        try {
            bgImage = new Image(Main.class.getResource("images" +
                    "/background/manager-main" +
                    ".jpg").toURI().toString());
        } catch (URISyntaxException e) {
            AlertPopUpWindow.displayErrorWindow(
                    "Error",
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

        refreshReportList();

        reportTypeChoiceBox.setOnAction(e -> {

            updateUserData();
            Manager manager = (Manager) Main.getCurrentUser();

            String reportChosen = reportTypeChoiceBox
                    .getValue().toString();

            try {
                manager.viewReports(
                        currentUser,
                        reportChosen,
                        reportTextArea
                );
            } catch (TextFileNotFoundException ex) {
                AlertPopUpWindow.displayErrorWindow(
                        "Error",
                        ex.getMessage()
                );
                ex.printStackTrace();
            }
            refreshReportList();

        });

        generatedReportList.setOnMouseClicked(e -> {
            Report selectedReport = generatedReportList
                    .getSelectionModel().getSelectedItem();
            Manager manager = (Manager) Main.getCurrentUser();
            Optional<ButtonType> userChoice = AlertPopUpWindow
                    .displayChoiceWindow(
                            "Option",
                            "Do you want to display this " +
                                    "report or delete it?"
                    );

            // To display report
            if (userChoice.get()
                    .getText()
                    .equalsIgnoreCase("display")) {
                reportTextArea.setText(
                        selectedReport.generateReport()
                );
            } else {

                // Deletes selected report
                Optional<ButtonType> userChoiceToDelete = AlertPopUpWindow
                        .displayConfirmationWindow(
                                "Confirmation",
                                "Are you sure you want to delete " +
                                        "this report?"
                        );

                // Confirmed yes
                if (userChoiceToDelete.get()
                        .getText()
                        .equalsIgnoreCase("yes")) {
                    try {
                        manager.deleteReportFromList(
                                selectedReport.getReportId()
                        );
                    } catch (TextFileNotFoundException ex) {
                        AlertPopUpWindow.displayErrorWindow(
                                "Error",
                                ex.getMessage()
                        );
                        ex.printStackTrace();
                    }
                    refreshReportList();
                }
            }
        });

    }

    private void updateUserData() {

        if (Main.getCurrentUser() == null) {
            return;
        }

        currentUser = Main.getCurrentUser();

    }

    private void refreshReportList() {
        generatedReportList.getItems().clear();

        try {

            ReportManager reportManager = new ReportManager();
            reportManager.updateReportList(generatedReportList);

        } catch (TextFileNotFoundException e) {
            AlertPopUpWindow.displayErrorWindow(
                    "Error",
                    e.getMessage()
            );
            e.printStackTrace();
        }

    }

}
