package org.group.project.exceptions;

import java.io.IOException;

/**
 * This class is used to inform the user that a text file failed to be
 * cleared up of data.
 *
 * @author azmi_maz
 */
public class ClearFileFailedException extends IOException {
    private static final String errorMessage =
            "Failed to clear textfile.";

    public ClearFileFailedException() {
        super(errorMessage);
    }
}
