package ch.m1m.quarkus.example;

import org.eclipse.microprofile.metrics.ConcurrentGauge;
import org.eclipse.microprofile.metrics.MetricRegistry;
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

    private ConcurrentGauge gaugePendingRequests;


    @PostConstruct
    public void onInit() {
        gaugePendingRequests = metricRegistry.concurrentGauge("pending_requests");
    }

    public ConcurrentGauge getPendingRequestsGauge() {
        return gaugePendingRequests;
    }


}
