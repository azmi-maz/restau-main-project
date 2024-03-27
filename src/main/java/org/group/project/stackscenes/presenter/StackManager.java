package org.group.project.stackscenes.presenter;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.group.project.stackscenes.view.ControllerView;

import java.util.Stack;

public class StackManager {

    private Stack<Presenter> stack;
    private Stage stage;

    public StackManager(Stage stage) {
        stack = new Stack<>();
        this.stage = stage;
    }

    public void pushDerivedPresenter() {

        if(stack.isEmpty()) {
            throw new RuntimeException("There are not presenters.");
        }

        Presenter topPresenter = stack.peek();

        Presenter derivedPresenter = topPresenter.getDerivedPresenter();
        ControllerView derivedView = derivedPresenter.getPresenterView();

        stage.setScene(derivedView.getViewScene());
        stack.add(derivedPresenter);
    }


    public void setInitialPresenter(Presenter presenter) {
        if(stack.size() != 0) {
            throw new RuntimeException("There is already an initial presenter.");
        }
        stack.add(presenter);
        stage.setScene(presenter.getPresenterView().getViewScene());
    }

    public void popPresenter() {
        if(stack.isEmpty()) {
            throw new RuntimeException("There are not presenters.");
        }

        stack.pop();

        if(stack.isEmpty()) {
            Platform.exit();
            System.exit(0);
        }

        Presenter currentTopPresenter = stack.peek();
        ControllerView currentTopView = currentTopPresenter.getPresenterView();

        stage.setScene(currentTopView.getViewScene());

    }

    public void show() {
        if(stack.isEmpty()) {
            throw new RuntimeException("There are not presenters.");
        }
        stage.show();
    }

}
