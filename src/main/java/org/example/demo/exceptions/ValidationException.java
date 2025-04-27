package org.example.demo.exceptions;

import org.example.demo.utils.FormattingUtils;

public class ValidationException extends Exception {
    public ValidationException(Class<?> type, String fieldName, String message) {
        super(String.format("Validation error in %s.%s: %s", FormattingUtils.getShortClassName(type), fieldName, message));
    }
}
