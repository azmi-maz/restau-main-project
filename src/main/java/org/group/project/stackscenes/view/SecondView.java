package org.group.project.stackscenes.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.group.project.MainAppStack;
import org.group.project.stackscenes.presenter.SecondPresenter;

import java.io.IOException;

public class SecondView implements PresenterView {

    private Scene scene;
    private SecondPresenter presenter;

    @FXML
    private Label label;

    @FXML
    private Button nextButton;

    @FXML
    private Button returnButton;

    public SecondView(SecondPresenter presenter) {

        this.presenter = presenter;


        try {
            FXMLLoader fxmlLoader =
                    new FXMLLoader(MainAppStack.class.getResource("second" +
                            ".fxml"));
            fxmlLoader.setController(this);
            scene = new Scene(fxmlLoader.load(), 400, 400);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        nextButton.setOnAction(e -> {
            presenter.goToThirdScene();
        });

        returnButton.setOnAction(e -> {
            presenter.returnToFirstScene();
        });

    }


    @Override
    public Scene getViewScene() {
        // TODO Auto-generated method stub
        return scene;
    }
}
