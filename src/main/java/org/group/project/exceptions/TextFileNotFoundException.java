package org.group.project.exceptions;

import java.io.IOException;

public class TextFileNotFoundException extends IOException {

    private static final String template = "%s text file is not found.";

    public TextFileNotFoundException(String fileName) {
        super(String.format(
                template,
                fileName
        ));
    }
}
