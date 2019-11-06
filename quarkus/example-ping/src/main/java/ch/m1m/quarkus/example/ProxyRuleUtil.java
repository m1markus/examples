package ch.m1m.quarkus.example;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProxyRuleUtil {

    private static final Logger log = LoggerFactory.getLogger(ProxyRuleUtil.class);

    private static final String fileName = "/tmp/proxy_persisted_table.json";

    private static List<ProxyRuleEntry> mapTargets;

    static {
        mapTargets = new ArrayList<>();
        ProxyRuleEntry target = null;

        load();

        target = new ProxyRuleEntry();
        target.setSourcePath("/api/v1/junit/ping");
        target.setTargetURL("http://localhost:8081/api/v1/junit/ping");
        addProxyEntry(target);

        target = new ProxyRuleEntry();
        target.setSourcePath("/api/v1/ping");
        target.setTargetURL("http://localhost:8080/api/v1/ping");
        addProxyEntry(target);

        target = new ProxyRuleEntry();
        target.setSourcePath("/api/v1/echo");
        target.setTargetURL("http://localhost:8080/api/v1/echo");
        addProxyEntry(target);
    }

    public static ProxyRuleEntry getTarget(String inPathInfo) {

        ProxyRuleEntry retEntry = null;

        for(ProxyRuleEntry targetEntry: mapTargets) {
            if (inPathInfo.startsWith(targetEntry.getSourcePath())) {
                retEntry = targetEntry;
                break;
            }
        }

        return retEntry;
    }

    // API operations
    //
    public static List<ProxyRuleEntry> getAll() {
        return mapTargets;
    }

    public static int addProxyEntry(ProxyRuleEntry inEntry) {

        int rc = -1;

        ProxyRuleEntry matchingEntry = getTarget(inEntry.getSourcePath());
        if (matchingEntry == null) {
            mapTargets.add(inEntry);
            rc = 200;
        } else {
            // make an update
            matchingEntry.setTargetURL(inEntry.getTargetURL());
            rc = 200;
        }

        log.info("addProxyEntry() return {}", rc);
        persist();

        return rc;
    }

    public static int updateProxyEntry(ProxyRuleEntry inEntry) {

        int rc = -1;

        ProxyRuleEntry matchingEntry = getTarget(inEntry.getSourcePath());
        if (matchingEntry == null) {
            rc = 404;
        } else {
            matchingEntry.setTargetURL(inEntry.getTargetURL());
            rc = 200;
        }

        log.info("updateProxyEntry() return {}", rc);
        persist();

        return rc;
    }

    public static int deleteProxyEntry(ProxyRuleEntry inEntry) {

        int rc = -1;

        ProxyRuleEntry matchingEntry = getTarget(inEntry.getSourcePath());
        if (matchingEntry == null) {
            rc = 404;
        } else {
            mapTargets.remove(matchingEntry);
            rc = 200;
        }

        log.info("deleteProxyEntry() return {}", rc);
        persist();

        return rc;
    }

    public static int deleteAll() {

        int rc = 200;
        mapTargets.clear();
        log.info("deleteAll() return {}", rc);
        persist();
        return rc;
    }

    public static int replaceAll(List<ProxyRuleEntry> inAll) {
        int rc = 200;
        mapTargets = inAll;
        log.info("replaceAll() return {}", rc);
        persist();
        return rc;
    }

    // persist / load from the Filesystem
    //

    public static int persist() {
        int rc = -1;
        ProxyRulePersitence prp = new ProxyRulePersitence();
        prp.setMapTargets(mapTargets);

        ObjectMapper mapper = new ObjectMapper();

        try {
            // String jsonInString = mapper.writeValueAsString(user);
            mapper.writeValue(new File(fileName), prp);
            log.info("persisted to file={}", fileName);
            rc = 0;

        } catch (JsonGenerationException e) {
            rc = 1;
            log.error("persist failed with", e);

        } catch (JsonMappingException e) {
            rc = 2;
            log.error("persist failed with", e);

        } catch (IOException e) {
            rc = 3;
            log.error("persist failed with", e);
        }

        return rc;
    }

    public static int load() {

        int rc = -1;
        ObjectMapper mapper = new ObjectMapper();

        try {
            ProxyRulePersitence prp = mapper.readValue(new File(fileName), ProxyRulePersitence.class);
            mapTargets = prp.getMapTargets();
            log.info("loaded from file={}", fileName);
            rc = 0;

        } catch (IOException e) {
            rc = 1;
            log.error("load failed with", e);
        }

        return rc;
    }

}
