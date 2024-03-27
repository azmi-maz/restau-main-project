package org.group.project.stackscenes.presenter;

import org.group.project.stackscenes.view.ControllerView;
import org.group.project.stackscenes.view.SecondController;

public class SecondPresenter extends Presenter {
    private static final int GO_TO_THIRD_SCENE_BTN = 1;

    private SecondController view;

    private int clickedBtn;

    public SecondPresenter(StackManager presenterStack) {
        super(presenterStack);

        view = new SecondController(this);
    }

    public void goToThirdScene() {
        clickedBtn = GO_TO_THIRD_SCENE_BTN;
        notifyManagerToAddNewPresenter();
    }

    @Override
    public Presenter getDerivedPresenter() {
        if(clickedBtn == GO_TO_THIRD_SCENE_BTN) {
            return new ThirdPresenter(getPresenterStack());
        }
        return null;
    }

    public void returnToFirstScene() {
        notifyManagerToDeletePresenter();
    }


    @Override
    public ControllerView getPresenterView() {
        return view;
    }
}
