package org.group.project.stackscenes.presenter;

import org.group.project.stackscenes.view.ControllerView;

public abstract class Presenter {

    private StackManager presenterStack;

    public Presenter(StackManager presenterStack) {
        this.presenterStack = presenterStack;
    }

    public abstract Presenter getDerivedPresenter();

    public abstract ControllerView getPresenterView();

    protected StackManager getPresenterStack() {
        return presenterStack;
    }

    protected void notifyManagerToAddNewPresenter() {
        presenterStack.pushDerivedPresenter();
    }

    protected void notifyManagerToDeletePresenter() {
        presenterStack.popPresenter();
    }
}
