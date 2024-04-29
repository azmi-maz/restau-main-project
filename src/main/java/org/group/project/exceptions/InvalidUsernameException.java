package org.group.project.exceptions;

/**
 * This custom exception class handles username related errors.
 *
 * @author azmi_maz
 */
public class InvalidUsernameException extends Exception {
    private static final String errorMessage =
            "Username already exist. " +
                    "Please " +
                    "use another username.";
    public InvalidUsernameException() {
        super(errorMessage);
    }
}
