package org.group.project.scenes.customer.mainViews;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group.project.scenes.ViewMaker;
import org.group.project.scenes.customer.stackViews.MenuPresenter;
import org.group.project.scenes.presenter.StackManager;

/**
 * This class prepares the customer menu order view scene.
 *
 * @author azmi_maz
 */
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

    /**
     * This method gets the customer menu order view scene.
     *
     * @return the customer menu order view scene.
     */
    @Override
    public Scene getScene() {

        return menuPresenter.getPresenterView().getViewScene();

    }
}
