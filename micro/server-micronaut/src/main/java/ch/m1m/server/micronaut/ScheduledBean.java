package ch.m1m.server.micronaut;

import io.micronaut.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;

@Singleton
public class ScheduledBean {

    private static final Logger log = LoggerFactory.getLogger(ScheduledBean.class);

    //@Scheduled(fixedRate = "1m")
    @Scheduled(fixedRate = "10s")
    void everyOnceDuringTheDay() {
        String msg = String.format("begin: Executing scheduled function()");
        log.warn("begin: " + msg);

        /*
        try {
            Thread.sleep( 60 * 1000);
            log.warn("end: " + msg);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
    }

    @PostConstruct
    void postConstruct() {
        log.warn("on postConstruct()");
    }

}
