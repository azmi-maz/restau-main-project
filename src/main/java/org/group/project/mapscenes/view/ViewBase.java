package org.group.project.mapscenes.view;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.group.project.MainAppMap;
import org.group.project.scenes.WindowSize;

import java.io.IOException;

public class ViewBase implements ViewMaker {

    private Stage stage;
    private String labelText;
    private EventHandler<? super MouseEvent> handler;

    public ViewBase(Stage stage, String labelText, EventHandler<? super MouseEvent> handler) {
        if (stage == null) {
            throw new IllegalArgumentException("Stage cannot be null");
        }

        if (handler == null) {
            throw new IllegalArgumentException("Handler cannot be null");
        }

        this.stage = stage;
        this.labelText = labelText;
        this.handler = handler;
    }

    @Override
    public Scene getScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(MainAppMap.class.getResource(
                "map-test/test" +
                "-small.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WindowSize.MAIN.HEIGHT,
                WindowSize.MAIN.WIDTH);

        return scene;
    }
}
