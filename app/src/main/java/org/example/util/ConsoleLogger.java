package org.example.util;

import java.util.logging.*;

public class ConsoleLogger implements Logger {
    private static final Logger logger = Logger.getLogger(ConsoleLogger.class.getName());

    @Override
    public void log(String message, Level level) {
        logger.log(level, message);
    }
}