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

@ApplicationScoped
public class ApplicationMetrics {

    private static final Logger log = LoggerFactory.getLogger(ApplicationMetrics.class);

    @Inject
    @RegistryType(type = MetricRegistry.Type.APPLICATION)
    private MetricRegistry metricRegistry;

    @PostConstruct
    public void onInit() {

    }

    public ConcurrentGauge getPendingRequestsGauge(String inPathInfo) {
        String tagPath = "path";
        log.debug("creating tag({}, {})", tagPath, inPathInfo);
        Tag tag = new Tag(tagPath, inPathInfo);
        ConcurrentGauge gauge = metricRegistry.concurrentGauge("pending_requests", tag);
        return gauge;
    }

}
