package org.example.demo.utils;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.logging.Logger;

@ApplicationScoped
public class AppLogger {

    protected Logger logger = Logger.getLogger(this.getClass().getName());

    public void log(String msg) {
        logger.warning(msg);
    }
}
