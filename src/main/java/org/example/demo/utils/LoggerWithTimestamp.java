package org.example.demo.utils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Specializes;

import java.time.LocalDateTime;

@ApplicationScoped
@Specializes
public class LoggerWithTimestamp extends AppLogger {
    private static final String CYAN = "\u001B[36m";
    private static final String RESET = "\u001B[0m";

    @Override
    public void log(String message) {
        logger.warning(CYAN + "[" + LocalDateTime.now() + "] " + RESET + message + "\n");
    }
}
