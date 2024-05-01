package org.group.project.exceptions;

import java.io.IOException;

/**
 * This class is used to inform the user a specific text file is not
 * available or that it does not exist.
 */
public class TextFileNotFoundException extends IOException {

    private static final String template = "%s text file is not found.";

    public TextFileNotFoundException(String fileName) {
        super(String.format(
                template,
                fileName
        ));
    }
}
