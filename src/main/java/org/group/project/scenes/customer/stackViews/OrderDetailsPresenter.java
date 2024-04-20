package org.group.project.scenes.customer.stackViews;

import org.group.project.scenes.presenter.Presenter;
import org.group.project.scenes.presenter.StackManager;
import org.group.project.scenes.ControllerView;

public class OrderDetailsPresenter extends Presenter {

    private static final int GO_TO_THIRD_SCENE_BTN = 1;

    private OrderDetailsController view;

    private int clickedBtn;

    public OrderDetailsPresenter(StackManager presenterStack) {
        super(presenterStack);

        view = new OrderDetailsController(this);
    }

    public void goToOrderConfirmation() {
        clickedBtn = GO_TO_THIRD_SCENE_BTN;
        notifyManagerToAddNewPresenter();
    }

    @Override
    public Presenter getDerivedPresenter() {
        if (clickedBtn == GO_TO_THIRD_SCENE_BTN) {
            return new OrderConfirmationPresenter(getPresenterStack());
        }
        return null;
    }

    public void returnToMenu() {
        notifyManagerToDeletePresenter();
    }

    @Override
    public ControllerView getPresenterView() {
        return view;
    }
}
