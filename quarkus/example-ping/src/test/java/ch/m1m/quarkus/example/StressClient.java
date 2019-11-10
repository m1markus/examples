package ch.m1m.quarkus.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.Vector;

public class StressClient {

    private static final Logger log = LoggerFactory.getLogger(StressClient.class);

    public static void main(String... args) {

        StressClient sc = new StressClient();
        int rc = sc.run(args);
        System.exit(rc);
    }

    public int run(String... args) {
        int rc = 0;
        Vector<StressRunnerThread> threadList = new Vector<>();

        StressPluginEcho stressPlugin = new StressPluginEcho();

        long numIterations = 100;
        int numThreads = 10;

        // calc how many requests every thread must execute
        //
        long numIterationsPerThread = -1;
        long numIterationsOnTopForLastThread = 0;

        if (numIterations > 0) {
            numIterationsPerThread = numIterations / numThreads;
            numIterationsOnTopForLastThread = numIterations % numThreads;
            if (numIterationsPerThread == 0) {
                numThreads = 1;
            }
        }

        // prepare thread list
        //
        for (int ii = 0; ii < numThreads; ii++) {

            long numIterationsParam = numIterationsPerThread;

            // if last thread add the remainder
            //
            if (ii + 1 == numThreads) {
                numIterationsParam += numIterationsOnTopForLastThread;
            }

            StressRunnerThread thread = new StressRunnerThread(stressPlugin, numIterationsParam);
            threadList.add(thread);
        }

        Instant runStart = Instant.now();

        // start them all
        //
        threadList.forEach((thread) -> thread.start());

        // join them all
        //
        for (StressRunnerThread thread : threadList) {

            try {
                thread.join();
                rc = thread.getReturnCode();
            } catch (InterruptedException e) {
                log.error("failed to join plugin thread", e);
                rc = 20;
            }
        }

        Instant runEnd = Instant.now();
        Duration duration = Duration.between(runStart, runEnd);
        long milliseconds = duration.toMillis();

        logSummary(rc, numIterations, numThreads, milliseconds);
        return rc;
    }

    private void logSummary(int rc, long numIterations, int numThreads, long milliseconds) {
        String statusAsText = "success";
        if (rc != 0) {
            statusAsText = "failed";
        }
        log.info("{} run iterations={} with thread concurrency={} finished with return code={} after milliseconds={}",
                statusAsText, numIterations, numThreads, rc, milliseconds);
    }

}
