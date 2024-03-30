package org.group.project.stackscenes.presenter;

import org.group.project.stackscenes.view.ControllerView;
import org.group.project.stackscenes.view.ThirdController;

public class ThirdPresenter extends Presenter {
    private ThirdController view;

    public ThirdPresenter(StackManager presenterStack) {
        super(presenterStack);

        view = new ThirdController(this);

    }

    @Override
    public Presenter getDerivedPresenter() {
        return null;
    }

    public void returnToSecondScene() {
        notifyManagerToDeletePresenter();
    }

    @Override
    public ControllerView getPresenterView() {
        return view;
    }
}
