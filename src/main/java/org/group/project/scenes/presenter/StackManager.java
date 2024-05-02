package org.group.project.scenes.presenter;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.scenes.ControllerView;

import java.util.Stack;

/**
 * This class handles the stack of presenters used for handling switching
 * between scenes put in a stack.
 * <p>
 * Modified code cited from:
 * https://github.com/R0land013/multiple-scenes-javafx
 *
 * @author azmi_maz
 */
public class StackManager {

    private Stack<Presenter> stack;
    private Stage stage;

    /**
     * This constructor sets up the stack manager with the main stage.
     *
     * @param stage - the main stage.
     */
    public StackManager(Stage stage) {
        stack = new Stack<>();
        this.stage = stage;
    }

    /**
     * This method push a presenter to the stack.
     */
    public void pushDerivedPresenter() {

        if (stack.isEmpty()) {
            AlertPopUpWindow.displayErrorWindow(
                    "There are no more presenters."
            );
        }

        Presenter topPresenter = stack.peek();

        Presenter derivedPresenter = topPresenter.getDerivedPresenter();
        ControllerView derivedView = derivedPresenter.getPresenterView();

        stage.setScene(derivedView.getViewScene());
        stack.add(derivedPresenter);
    }

    /**
     * This method sets the presenter and display it to the user.
     *
     * @param presenter - the initial presenter that starts the stack.
     */
    public void setInitialPresenter(Presenter presenter) {
        if (stack.size() != 0) {
            AlertPopUpWindow.displayErrorWindow(
                    "There is already an initial presenter."
            );
        }
        stack.add(presenter);
        stage.setScene(presenter.getPresenterView().getViewScene());
    }

    /**
     * This method pops up a presenter from the stack and display the
     * previous presenter in the stack.
     */
    public void popPresenter() {
        if (stack.isEmpty()) {
            AlertPopUpWindow.displayErrorWindow(
                    "There are no more presenters."
            );
        }

        stack.pop();

        if (stack.isEmpty()) {
            Platform.exit();
            System.exit(0);
        }

        Presenter currentTopPresenter = stack.peek();
        ControllerView currentTopView = currentTopPresenter
                .getPresenterView();

        stage.setScene(currentTopView.getViewScene());

    }
}
