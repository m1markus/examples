package ch.m1m.commons.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class ContextLoggerTest {

    private static final String LOGGER_NAME = ContextLogger.class.getName();

    private static MemoryAppender memoryAppender;

    @BeforeAll
    public static void setup() {
        Logger logger = (Logger) LoggerFactory.getLogger(LOGGER_NAME);
        memoryAppender = new MemoryAppender();
        memoryAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        logger.setLevel(Level.DEBUG);
        logger.addAppender(memoryAppender);
        memoryAppender.start();
    }

    @Test
    public void givenAContextLogger_whenCalligLog_thenItGetsLoggedWithDefaultClassName_ContextLogger() {
        // GIVEN
        ContextLoggerRepository repo = createContextLoggerRepository();
        ContextLogger ctxLog = repo.getContextLogger("mue");
        memoryAppender.reset();

        // WHEN
        ctxLog.log(LOGGER_NAME, "this is my trace message... a={} b={}", "1", 2);

        // THEN
        assertThat(memoryAppender.countEventsForLogger(LOGGER_NAME)).isEqualTo(1);
        assertThat(memoryAppender.contains("this is my trace message...", Level.INFO)).isTrue();
        assertThat(ctxLog.isEnabled()).isEqualTo(true);
    }

    @Test
    public void givenAContextLogger_whenCalligLogWithClass_thenItGetsLoggedWithThatClassName() {
        // GIVEN
        ContextLoggerRepository repo = createContextLoggerRepository();
        ContextLogger ctxLog = repo.getContextLogger("mue");
        memoryAppender.reset();

        // WHEN
        ctxLog.log(ContextLogger.class, "log with class as class name");

        // THEN
        assertThat(memoryAppender.countEventsForLogger(LOGGER_NAME)).isEqualTo(1);
        assertThat(memoryAppender.contains("log with class as class name", Level.INFO)).isTrue();
        assertThat(ctxLog.isEnabled()).isTrue();
    }

    @Test
    public void givenAContextLogger_whenCalligLogWithString_thenItGetsLoggedWithThatStringAsClassName() {
        // GIVEN
        ContextLoggerRepository repo = createContextLoggerRepository();
        ContextLogger ctxLog = repo.getContextLogger("mue");
        memoryAppender.reset();

        // WHEN
        ctxLog.log(ContextLogger.class.getName(), "log with string as class name");

        // THEN
        assertThat(memoryAppender.countEventsForLogger(LOGGER_NAME)).isEqualTo(1);
        assertThat(memoryAppender.contains("log with string as class name", Level.INFO)).isTrue();
        assertThat(ctxLog.isEnabled()).isTrue();
    }

    @Test
    public void givenAWrongUser_whenCallingLog_thenItGetsIgnored() {
        // GIVEN
        ContextLoggerRepository repo = createContextLoggerRepository();
        ContextLogger ctxLog = repo.getContextLogger("Anonymous");
        memoryAppender.reset();

        // WHEN
        ctxLog.log(LOGGER_NAME, "this message should not be displayed");

        // THEN
        assertThat(memoryAppender.countEventsForLogger(LOGGER_NAME)).isEqualTo(0);
        assertThat(memoryAppender.contains("this message should not be displayed", Level.INFO)).isFalse();
        assertThat(ctxLog.isEnabled()).isFalse();
    }

    @Test
    public void calling_more_functions_to_get_the_coverage() {
        // GIVEN, ...
        ContextLoggerRepository repo = createContextLoggerRepository();
        repo.removeName("frontend-service");
        repo.clearAll();
    }

    private ContextLoggerRepository createContextLoggerRepository() {
        ContextLoggerRepository repo = new ContextLoggerRepository();
        repo.addName("MUE");
        return repo;
    }
}
