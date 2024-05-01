package org.group.project.scenes.customer.stackViews;


import org.group.project.scenes.presenter.Presenter;
import org.group.project.scenes.presenter.StackManager;
import org.group.project.scenes.ControllerView;

/**
 * This class allows the customer to go back and forth from this first scene
 * to the cart scene.
 */
public class MenuPresenter extends Presenter {

    private static final int GO_TO_CART_BUTTON = 1;
    private MenuController view;
    private int btn_clicked = 0;

    /**
     * This constructor sets up the presenter manager from the main one.
     *
     * @param presenterStack - the main presenter manager.
     */
    public MenuPresenter(StackManager presenterStack) {
        super(presenterStack);
        view = new MenuController(this);
    }

    /**
     * This method gets the presenter of the next presenter, view cart.
     *
     * @return - the view cart presenter.
     */
    @Override
    public Presenter getDerivedPresenter() {
        if (btn_clicked == GO_TO_CART_BUTTON) {
            return new OrderDetailsPresenter(getPresenterStack());
        }
        return null;
    }

    /**
     * This method adds the view cart presenter to the stack.
     */
    public void goToCart() {
        btn_clicked = GO_TO_CART_BUTTON;
        notifyManagerToAddNewPresenter();
    }

    /**
     * This deletes the stack presenter in the stack manager and exits
     * to the customer main home page.
     */
    public void exit() {
        notifyManagerToDeletePresenter();
    }

    /**
     * This gets the current controller view.
     *
     * @return the menu controller view.
     */
    @Override
    public ControllerView getPresenterView() {
        return view;
    }
}
