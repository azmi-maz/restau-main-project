package org.group.project.scenes.customer.mainViews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.mapscenes.view.ViewMaker;
import org.group.project.scenes.WindowSize;

import java.io.IOException;

public class BookingsView implements ViewMaker {

    private Stage stage;

    public BookingsView(Stage stage) {

        this.stage = stage;
    }

    @Override
    public Scene getScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
                "customerscenes/mapscenes/customer-viewbookings.fxml"));
        return new Scene(fxmlLoader.load(), WindowSize.MAIN.WIDTH,
                WindowSize.MAIN.HEIGHT);
    }
}
