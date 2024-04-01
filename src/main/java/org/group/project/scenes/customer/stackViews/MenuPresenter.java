package org.group.project.scenes.customer.stackViews;

import org.group.project.stackscenes.presenter.Presenter;
import org.group.project.stackscenes.presenter.StackManager;
import org.group.project.stackscenes.view.ControllerView;

public class MenuPresenter extends Presenter {

    private MenuController view;

    private int btn_clicked = 0;

    private static final int GO_TO_SECOND_SCENE_BTN = 1;

    public MenuPresenter(StackManager presenterStack) {
        super(presenterStack);
        view = new MenuController(this);
    }

    @Override
    public Presenter getDerivedPresenter() {
        if (btn_clicked == GO_TO_SECOND_SCENE_BTN) {
            return new OrderDetailsPresenter(getPresenterStack());
        }
        return null;
    }

    public void goToCart() {
        btn_clicked = GO_TO_SECOND_SCENE_BTN;
        notifyManagerToAddNewPresenter();
    }

    public void exit() {
        notifyManagerToDeletePresenter();
    }

    @Override
    public ControllerView getPresenterView() {
        return view;
    }
}
