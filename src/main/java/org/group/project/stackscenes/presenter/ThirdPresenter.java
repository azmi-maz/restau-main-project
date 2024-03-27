package org.group.project.stackscenes.presenter;

import org.group.project.stackscenes.view.PresenterView;
import org.group.project.stackscenes.view.ThirdView;

public class ThirdPresenter extends AbstractPresenter {
    private ThirdView view;

    public ThirdPresenter(PresenterManager presenterStack) {
        super(presenterStack);

        view = new ThirdView(this);

    }

    @Override
    public AbstractPresenter getDerivedPresenter() {
        // TODO Auto-generated method stub
        return null;
    }

    public void returnToSecondScene() {
        notifyManagerToDeletePresenter();
    }

    @Override
    public PresenterView getPresenterView() {
        return view;
    }
}
