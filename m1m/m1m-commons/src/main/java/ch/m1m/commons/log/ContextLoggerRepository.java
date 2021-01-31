package ch.m1m.commons.log;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ContextLoggerRepository {

    private Set<String> syncAddNames = Collections.synchronizedSet(new HashSet<>());

    public ContextLogger getContextLogger(String userid) {
        Objects.requireNonNull(userid);
        ContextLogger ctxLog = null;
        if (syncAddNames.contains(userid.toLowerCase())) {
            ctxLog = new ContextLogger(true);
        }
        if (ctxLog == null) ctxLog = new ContextLogger();
        return ctxLog;
    }

    public void addName(String name) {
        Objects.requireNonNull(name);
        syncAddNames.add(name.toLowerCase());
    }

    public void removeName(String name) {
        Objects.requireNonNull(name);
        syncAddNames.remove(name.toLowerCase());
    }

    public void clearAll() {
        syncAddNames.clear();
    }
}
