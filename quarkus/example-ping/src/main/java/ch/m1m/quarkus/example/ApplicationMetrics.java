package ch.m1m.quarkus.example;

import org.eclipse.microprofile.metrics.ConcurrentGauge;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.Tag;
import org.eclipse.microprofile.metrics.annotation.RegistryType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class ApplicationMetrics {

    private static final Logger log = LoggerFactory.getLogger(ApplicationMetrics.class);

    @Inject
    @RegistryType(type = MetricRegistry.Type.APPLICATION)
    MetricRegistry metricRegistry;

    private static AtomicInteger currentRequests = new AtomicInteger();

    @PostConstruct
    public void onInit() {

    }

    public void requestStart() {
        int current = currentRequests.incrementAndGet();
        log.info("reqStart() current pending requests {}", current);
    }

    public void requestEnd() {
        int current = currentRequests.decrementAndGet();
        log.info("reqEnd() current pending requests {}", current);
    }

    public ConcurrentGauge getPendingRequestsGauge(String inPathInfo) {
        String tagPath = "path";
        log.debug("creating tag({}, {})", tagPath, inPathInfo);
        Tag tag = new Tag(tagPath, inPathInfo);
        ConcurrentGauge gauge = metricRegistry.concurrentGauge("pending_requests", tag);
        log.info("TRACE gauge returned: {}", gauge);
        return gauge;
    }
}
