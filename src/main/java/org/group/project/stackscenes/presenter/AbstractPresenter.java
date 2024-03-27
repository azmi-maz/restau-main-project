package org.group.project.stackscenes.presenter;

import org.group.project.stackscenes.view.PresenterView;

public abstract class AbstractPresenter {

    private PresenterManager presenterStack;

    public AbstractPresenter(PresenterManager presenterStack) {
        this.presenterStack = presenterStack;
    }

    public abstract AbstractPresenter getDerivedPresenter();

    public abstract PresenterView getPresenterView();

    protected PresenterManager getPresenterStack() {
        return presenterStack;
    }

    protected void notifyManagerToAddNewPresenter() {
        presenterStack.pushDerivedPresenter();
    }

    protected void notifyManagerToDeletePresenter() {
        presenterStack.popPresenter();
    }
}
