package org.group.project.scenes.customer.stackViews;

import org.group.project.scenes.presenter.Presenter;
import org.group.project.scenes.presenter.StackManager;
import org.group.project.scenes.ControllerView;

/**
 * This allows the customer to go from this order details page,
 * order confirmation or return back to the menu.
 *
 * @author azmi_maz
 */
public class OrderDetailsPresenter extends Presenter {
    private static final int GO_TO_ORDER_CONFIRMATION_BTN = 1;
    private OrderDetailsController view;
    private int clickedBtn;

    /**
     * This constructor sets up the presenter manager from the main one.
     *
     * @param presenterStack - the main presenter manager.
     */
    public OrderDetailsPresenter(StackManager presenterStack) {
        super(presenterStack);

        view = new OrderDetailsController(this);
    }

    /**
     * This method adds the order confirmation presenter to the stack.
     */
    public void goToOrderConfirmation() {
        clickedBtn = GO_TO_ORDER_CONFIRMATION_BTN;
        notifyManagerToAddNewPresenter();
    }

    /**
     * This method gets the presenter of the next presenter, view cart.
     *
     * @return - the order confirmation presenter.
     */
    @Override
    public Presenter getDerivedPresenter() {
        if (clickedBtn == GO_TO_ORDER_CONFIRMATION_BTN) {
            return new OrderConfirmationPresenter(getPresenterStack());
        }
        return null;
    }

    /**
     * This method deletes the order details presenter from the stack and
     * return the customer back to the menu.
     */
    public void returnToMenu() {
        notifyManagerToDeletePresenter();
    }

    /**
     * This gets the current controller view.
     *
     * @return - the order details controller view.
     */
    @Override
    public ControllerView getPresenterView() {
        return view;
    }
}
