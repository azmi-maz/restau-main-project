package org.group.project.controllers.manager;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.group.project.Main;

import java.net.URISyntaxException;

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
    private ChoiceBox reportTypeChoiceBox;

    @FXML
    private TextArea reportTextArea;

    public void initialize() throws URISyntaxException {

        // TODO enum?
        reportTypeChoiceBox.getItems().add("Most popular item");
        reportTypeChoiceBox.getItems().add("Busiest period");
        reportTypeChoiceBox.getItems().add("Most active customer");
        reportTypeChoiceBox.getItems().add("Staff worked hours");

        reportTypeChoiceBox.setValue("Choose report type");

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

        reportTypeChoiceBox.setOnAction(e -> {
            String reportChosen = reportTypeChoiceBox.getValue().toString();

            switch (reportChosen) {
                case "Most popular item":
                    reportTextArea.setText("Most popular item");
                    break;

                case "Busiest period":
                    reportTextArea.setText("Busiest period");
                    break;

                case "Most active customer":
                    reportTextArea.setText("Most active customer");
                    break;

                case "Staff worked hours":
                    reportTextArea.setText("Staff worked hours");
                    break;

                default:
                    reportTextArea.setText("No report chosen.");
                    break;

            }
        });

    }
}
