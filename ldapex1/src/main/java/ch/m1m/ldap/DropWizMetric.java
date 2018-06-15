package ch.m1m.ldap;

import com.codahale.metrics.*;
import java.util.concurrent.TimeUnit;

public class DropWizMetric {
    static final MetricRegistry metrics = new MetricRegistry();
    public static void main(String args[]) {
        startReport();
        Meter requests = metrics.meter("requests");
        requests.mark();

        waitSeconds(1);

        requests.mark();
        requests.mark();
        requests.mark();
        requests.mark();

        waitSeconds(1);

        requests.mark();
        requests.mark();

        waitSeconds(2);

    }

    static void startReport() {
        LocalReporter reporter = LocalReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();

        reporter.start(1, TimeUnit.SECONDS);
    }

    static void waitSeconds(int seconds) {
        try {
            Thread.sleep(seconds*1000);
        }
        catch(InterruptedException e) {}
    }
}