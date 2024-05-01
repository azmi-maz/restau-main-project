package org.group.project.scenes.customer.mainViews;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group.project.scenes.ViewMaker;
import org.group.project.scenes.presenter.StackManager;
import org.group.project.scenes.customer.stackViews.MenuPresenter;

import java.io.IOException;

public class MenuOrderView implements ViewMaker {

    private StackManager manager;

    private MenuPresenter menuPresenter;


    /**
     * This constructor sets up the stage from the main one.
     *
     * @param stage - the main stage.
     */
    public MenuOrderView(Stage stage) {

        manager = new StackManager(stage);
        menuPresenter = new MenuPresenter(manager);

        manager.setInitialPresenter(menuPresenter);

    }

    @Override
    public Scene getScene() throws IOException {

        return menuPresenter.getPresenterView().getViewScene();

    }
}
