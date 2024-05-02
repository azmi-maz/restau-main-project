package org.group.project.scenes;

import javafx.scene.Scene;

/**
 * This class ensures all controllers involved in a stack implements the
 * given method.
 *
 * @author azmi_maz
 */
public interface ControllerView {

    /**
     * This method gets the view scene from the presenter.
     *
     * @return
     */
    public Scene getViewScene();
}
