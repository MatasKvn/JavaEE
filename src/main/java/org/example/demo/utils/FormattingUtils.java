package org.example.demo.utils;

public final class FormattingUtils {
    public static String getShortClassName(Class<?> type) {
        try {
            String[] pathSequence = type
                    .getName()
                    .split("\\.");
            return pathSequence[pathSequence.length - 1];
        } catch (Exception e) {
            throw new RuntimeException("Could not determine entity name.", e);
        }
    }
}
