package org.group.project.scenes.customer.stackViews;


import org.group.project.scenes.presenter.Presenter;
import org.group.project.scenes.presenter.StackManager;
import org.group.project.scenes.ControllerView;

public class OrderConfirmationPresenter extends Presenter {

    private OrderConfirmationController view;

    public OrderConfirmationPresenter(StackManager presenterStack) {
        super(presenterStack);
        view = new OrderConfirmationController(this);
    }

    @Override
    public Presenter getDerivedPresenter() {
        return null;
    }

    public void returnToOrderDetails() {
        // TODO how to return back to Home and refresh order list
        notifyManagerToDeletePresenter();
    }

    @Override
    public ControllerView getPresenterView() {
        return view;
    }
}
