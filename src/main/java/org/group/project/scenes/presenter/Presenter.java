package org.group.project.scenes.presenter;

import org.group.project.scenes.ControllerView;

/**
 * This class ensures each presenter used has the essential methods.
 * <p>
 * Modified code cited from:
 * https://github.com/R0land013/multiple-scenes-javafx
 */
public abstract class Presenter {

    private StackManager presenterStack;

    /**
     * This constructor is used to communicate with the stack manager to
     * perform the necessary functions.
     *
     * @param presenterStack
     */
    public Presenter(
            StackManager presenterStack) {
        this.presenterStack = presenterStack;
    }

    /**
     * This method gets the current presenter in the stack.
     *
     * @return the current presenter.
     */
    public abstract Presenter getDerivedPresenter();

    /**
     * This method gets the controller view from the presenter.
     *
     * @return - the controller view.
     */
    public abstract ControllerView getPresenterView();

    /**
     * This method gets the stack manager to help prepare for a new presenter.
     *
     * @return the current state of the stack manager.
     */
    protected StackManager getPresenterStack() {
        return presenterStack;
    }

    /**
     * This method push a new presenter to the stack manager.
     */
    protected void notifyManagerToAddNewPresenter() {
        presenterStack.pushDerivedPresenter();
    }

    /**
     * This method pops a presenter from the stack manager.
     */
    protected void notifyManagerToDeletePresenter() {
        presenterStack.popPresenter();
    }
}
