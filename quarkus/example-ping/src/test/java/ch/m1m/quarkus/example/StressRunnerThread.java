package ch.m1m.quarkus.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StressRunnerThread extends Thread {

    private static final Logger log = LoggerFactory.getLogger(StressRunnerThread.class);

    private long maxIterations;
    private StressPlugin stressPlugin;
    private int rc = 0;

    public StressRunnerThread(StressPlugin inStressPlugin, long inIterations) {
        stressPlugin = inStressPlugin;
        maxIterations = inIterations;
    }

    public int getReturnCode() {
        return rc;
    }

    public void run() {

        log.info("StressRunnerThread running");

        for (long ii = 1; ; ii++) {

            log.info("StressRunnerThread test iteration #{}", ii);

            rc = stressPlugin.execute();
            if (rc != 0) {
                log.error("client ended because of plugin error rc={}", rc);
                break;
            }

            // check if we can finish
            //
            if (maxIterations != -1 && maxIterations != 0) {
                if (ii >= maxIterations) {
                    break;
                }
            }
        }

        log.info("StressRunnerThread end with rc={}", rc);
    }
}
