package org.group.project.exceptions;

import java.io.IOException;

public class ClearFileFailedException extends IOException {
    private static final String errorMessage =
            "Failed to clear textfile.";
    public ClearFileFailedException() {
        super(errorMessage);
    }
}
