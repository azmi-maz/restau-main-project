package org.group.project.controllers.manager;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.group.project.Main;
import org.group.project.classes.*;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.classes.auxiliary.DataFileStructure;
import org.group.project.classes.auxiliary.DataManager;
import org.group.project.classes.auxiliary.HelperMethods;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class ManagerReportViewController {

    @FXML
    private VBox vbox;

    @FXML
    private Label headerLabel;

    @FXML
    private ImageView bgImage;

    @FXML
    private BorderPane borderPane;

    @FXML
    private ChoiceBox<String> reportTypeChoiceBox;

    @FXML
    private TextArea reportTextArea;

    @FXML
    private ListView<Report> generatedReportList;

    private User currentUser;

    public void initialize() throws URISyntaxException, FileNotFoundException {

        // TODO enum?
        reportTypeChoiceBox.getItems().add("Busiest periods");
        reportTypeChoiceBox.getItems().add("Most active customer");
        reportTypeChoiceBox.getItems().add("Most popular item");
        reportTypeChoiceBox.getItems().add("Outstanding orders");
        reportTypeChoiceBox.getItems().add("Staff worked hours");

        reportTypeChoiceBox.setValue("Choose report type");

        Image bgImage = new Image(Main.class.getResource("images" +
                "/background/manager-main" +
                ".jpg").toURI().toString());

        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO,
                BackgroundSize.AUTO, false, false, true, true);

        borderPane.setBackground(new Background(new BackgroundImage(bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));

        refreshReportList();

        reportTypeChoiceBox.setOnAction(e -> {

            updateUserData();

            String reportChosen = reportTypeChoiceBox.getValue().toString();

            switch (reportChosen) {
                case "Most popular item":
                    KitchenReport mostPopularItem = new KitchenReport(
                            "Most popular item",
                            currentUser
                    );
                    reportTextArea.setText(
                            mostPopularItem.generateReport()
                    );

                    // TODO
                    try {
                        mostPopularItem.saveReportToDatabase();
                        refreshReportList();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    break;

                case "Busiest periods":
                    FloorReport busiestPeriod = new FloorReport(
                            "Busiest periods",
                            currentUser
                    );
                    reportTextArea.setText(
                            busiestPeriod.generateReport()
                    );

                    // TODO
                    try {
                        busiestPeriod.saveReportToDatabase();
                        refreshReportList();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    break;

                case "Most active customer":
                    FloorReport mostActive = new FloorReport(
                            "Most active customer",
                            currentUser
                    );
                    KitchenReport mostLoyal = new KitchenReport(
                            "Most active customer",
                            currentUser
                    );
                    mostActive.appendReportData(
                            mostLoyal
                    );
                    reportTextArea.setText(
                            mostActive.generateReport()
                    );

                    // TODO
                    try {
                        mostActive.saveReportToDatabase();
                        refreshReportList();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    break;

                case "Staff worked hours":
                    UserReport userReport = new UserReport(
                            "Staff worked hours",
                            currentUser
                    );
                    reportTextArea.setText(
                            userReport.generateReport()
                    );

                    // TODO
                    try {
                        userReport.saveReportToDatabase();
                        refreshReportList();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    break;

                case "Outstanding orders":
                    KitchenReport outstandingOrders = new KitchenReport(
                            "Outstanding orders",
                            currentUser
                    );
                    reportTextArea.setText(
                            outstandingOrders.generateReport()
                    );

                    // TODO
                    try {
                        outstandingOrders.saveReportToDatabase();
                        refreshReportList();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    break;

                default:
                    reportTextArea.setText("No report chosen.");
                    break;

            }
        });

        generatedReportList.setOnMouseClicked(e -> {
            Report selectedReport = generatedReportList.getSelectionModel().getSelectedItem();
            Optional<ButtonType> userChoice = AlertPopUpWindow.displayChoiceWindow(
                    "Option",
                    "Do you want to display this report or delete it?"
            );

            // To display report
            if (userChoice.get()
                    .getText()
                    .equalsIgnoreCase("display")) {
                reportTextArea.setText(
                        selectedReport.generateReport()
                );
            } else {
                // Delete selected
                Optional<ButtonType> userChoiceToDelete = AlertPopUpWindow
                        .displayConfirmationWindow(
                                "Confirmation",
                                "Are you sure you want to delete this report?"
                        );

                // Confirmed yes
                if (userChoiceToDelete.get()
                        .getText()
                        .equalsIgnoreCase("yes")) {
                    // TODO
                    try {
                        deleteGeneratedReport(selectedReport.getReportId());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

    }

    // TODO
    private void updateUserData() {

        if (Main.getCurrentUser() == null) {
            return;
        }

        currentUser = Main.getCurrentUser();

    }

    // TODO
    private void refreshReportList() throws FileNotFoundException {
        generatedReportList.getItems().clear();

        List<String> reportList = DataManager
                .allDataFromFile("REPORTS");
        for (String report : reportList) {
            List<String> reportDetails = List.of(report.split(","));
            int reportId = Integer.parseInt(reportDetails.get(
                    DataFileStructure.getIndexByColName(
                            "REPORTS",
                            "reportId"
                    )));

            String reportType = reportDetails.get(
                    DataFileStructure.getIndexByColName(
                            "REPORTS",
                            "reportType"
                    ));

            String reportData = reportDetails.get(
                    DataFileStructure.getIndexByColName(
                            "REPORTS",
                            "reportData"
                    ));

            String generatedBy = "";
            String generatedById = reportDetails.get(
                    DataFileStructure.getIndexByColName(
                            "REPORTS",
                            "generatedBy"
                    ));
            List<String> generatedByDetails = HelperMethods.getDataById(
                    "USERS",
                    generatedById
            );

            User generatedByUser = new User(
                    generatedByDetails.get(
                            DataFileStructure.getIndexByColName(
                                    "USERS",
                                    "firstName"
                            )),
                    generatedByDetails.get(
                            DataFileStructure.getIndexByColName(
                                    "USERS",
                                    "lastName"
                            )),
                    generatedByDetails.get(
                            DataFileStructure.getIndexByColName(
                                    "USERS",
                                    "username"
                            ))
            );

            generatedBy = generatedByUser.getDataForListDisplay();

            List<String> reportDateDetails = List.of(reportDetails.get(
                    DataFileStructure.getIndexByColName(
                            "REPORTS",
                            "generatedOnDate"
                    )).split("-"));

            LocalDate generatedOnDate = LocalDate.of(Integer.parseInt(reportDateDetails.get(0)),
                    Integer.parseInt(reportDateDetails.get(1)),
                    Integer.parseInt(reportDateDetails.get(2)));

            List<String> reportTimeDetails = List.of(reportDetails.get(
                    DataFileStructure.getIndexByColName(
                            "REPORTS",
                            "generatedOnTime"
                    )).split("-"));

            LocalTime generatedOnTime = LocalTime.of(Integer.parseInt(reportTimeDetails.get(0)),
                    Integer.parseInt(reportTimeDetails.get(1)));

            generatedReportList.getItems().add(
                    new Report(
                            reportId,
                            reportType,
                            reportData,
                            generatedBy,
                            generatedOnDate,
                            generatedOnTime
                    ));
        }
    }

    // TODO
    private void deleteGeneratedReport(int reportId) throws IOException {

        DataManager.deleteUniqueIdFromFile(
                "REPORTS",
                reportId
        );

        refreshReportList();
    }
}
