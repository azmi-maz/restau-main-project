package org.group.project.scenes;

/**
 * This enum class standardises the window size for all scenes used.
 *
 * @author azmi_maz
 */
public enum WindowSize {
    MAIN(1400, 700),
    MEDIUM(700, 500),
    SMALL(400, 400);

    public final int WIDTH;
    public final int HEIGHT;

    WindowSize(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
    }

}
