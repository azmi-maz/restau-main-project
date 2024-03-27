package org.group.project;

import javafx.application.Application;
import javafx.stage.Stage;
import org.group.project.stackscenes.presenter.FirstPresenter;
import org.group.project.stackscenes.presenter.PresenterManager;

import java.io.IOException;

public class MainAppStack extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        primaryStage.setTitle("Multiple Scenes JavaFX");

        PresenterManager manager = new PresenterManager(primaryStage);
        FirstPresenter firstPresenter = new FirstPresenter(manager);

        manager.setInitialPresenter(firstPresenter);
        manager.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
