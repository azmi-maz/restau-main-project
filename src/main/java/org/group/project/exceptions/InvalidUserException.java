package org.group.project.exceptions;

/**
 * This custom exception class handles username related errors.
 *
 * @author azmi_maz
 */
public class InvalidUserException extends Exception {
    public InvalidUserException(String errorMessage) {
        super(errorMessage);
    }
}
