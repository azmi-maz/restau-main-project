package org.group.project.scenes.customer.stackViews;


import org.group.project.scenes.presenter.Presenter;
import org.group.project.scenes.presenter.StackManager;
import org.group.project.scenes.ControllerView;

/**
 * This allows the customer to go back and forth from this order
 * confirmation page and the previous order details page.
 */
public class OrderConfirmationPresenter extends Presenter {
    private OrderConfirmationController view;

    /**
     * This constructor sets up the presenter manager from the main one.
     *
     * @param presenterStack - the main presenter manager.
     */
    public OrderConfirmationPresenter(StackManager presenterStack) {
        super(presenterStack);
        view = new OrderConfirmationController(this);
    }

    /**
     * This method returns null as order confirmation is the last of the
     * stack.
     *
     * @return null by default.
     */
    @Override
    public Presenter getDerivedPresenter() {
        return null;
    }

    /**
     * This method deletes the stack presenter in the stack manager and exits
     * to the previous order details page.
     */
    public void returnToOrderDetails() {
        notifyManagerToDeletePresenter();
    }

    /**
     * This gets the current controller view.
     *
     * @return the order confirmation controller view.
     */
    @Override
    public ControllerView getPresenterView() {
        return view;
    }
}
