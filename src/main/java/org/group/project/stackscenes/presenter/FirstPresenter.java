package org.group.project.stackscenes.presenter;

import org.group.project.stackscenes.view.FirstController;
import org.group.project.stackscenes.view.ControllerView;

public class FirstPresenter extends Presenter {

    private FirstController view;

    private int btn_clicked = 0;

    private static final int GO_TO_SECOND_SCENE_BTN = 1;

    public FirstPresenter(StackManager presenterStack) {
        super(presenterStack);
        view = new FirstController(this);
    }

    @Override
    public Presenter getDerivedPresenter() {
        if (btn_clicked == GO_TO_SECOND_SCENE_BTN) {
            return new SecondPresenter(getPresenterStack());
        }
        return null;
    }

    public void goToSecondScene() {
        btn_clicked = GO_TO_SECOND_SCENE_BTN;
        notifyManagerToAddNewPresenter();
    }

    public void exit() {
        notifyManagerToDeletePresenter();
    }

    @Override
    public ControllerView getPresenterView() {
        return view;
    }
}
