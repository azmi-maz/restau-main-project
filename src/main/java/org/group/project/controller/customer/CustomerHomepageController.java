package org.group.project.controller.customer;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.group.project.Main;

import java.net.URISyntaxException;

public class CustomerHomepageController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Label mainTitle;

    @FXML
    private Button button1;

    @FXML
    private Label label1;

    @FXML
    private ImageView bgImage;

    public void initialize() throws URISyntaxException {

        mainTitle.setText("Welcome, John!");

        Image bgImage = new Image(Main.class.getResource("images" +
                "/background/main-bg" +
                ".jpg").toURI().toString());

        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO,
                BackgroundSize.AUTO, false, false, true, true);

        borderPane.setBackground(new Background(new BackgroundImage(bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));

    }

    public void testAlert(Event e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error: For Testing");
        alert.setHeaderText(null);
        alert.setContentText("More info here.");
        alert.showAndWait();
    }


}
