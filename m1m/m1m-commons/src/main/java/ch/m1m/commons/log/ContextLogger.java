package ch.m1m.commons.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContextLogger {

    private boolean isLogging;

    ContextLogger() {
        this.isLogging = false;
    }

    ContextLogger(boolean isLogging) {
        this.isLogging = isLogging;
    }

    public boolean isEnabled() {
        return isLogging;
    }

    public void log(Class clazz, String message, Object... args) {
        if (isLogging) {
            String loggerName = clazz.getName();
            this.log(loggerName, message, args);
        }
    }

    public void log(String loggerName, String message, Object... args) {
        if (isLogging) {
            Logger log = LoggerFactory.getLogger(loggerName);
            log.info(message, args);
        }
    }
}
