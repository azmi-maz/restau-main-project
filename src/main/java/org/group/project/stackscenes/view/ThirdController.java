package org.group.project.stackscenes.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.group.project.MainAppStack;
import org.group.project.stackscenes.presenter.ThirdPresenter;

import java.io.IOException;

public class ThirdController implements ControllerView {

    private Scene scene;

    private ThirdPresenter presenter;

    @FXML
    private Label label;

    @FXML
    private Button returnButton;


    public ThirdController(ThirdPresenter presenter) {
        this.presenter = presenter;


        try {
            FXMLLoader fxmlLoader =
                    new FXMLLoader(MainAppStack.class.getResource("stack" +
                            "-test/third" +
                            ".fxml"));
            fxmlLoader.setController(this);
            scene = new Scene(fxmlLoader.load(), 400, 400);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        returnButton.setOnAction(e -> {
            presenter.returnToSecondScene();
        });

    }

    @Override
    public Scene getViewScene() {
        return scene;
    }
}
