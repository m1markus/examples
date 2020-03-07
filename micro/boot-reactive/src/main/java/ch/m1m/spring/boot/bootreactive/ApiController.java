package ch.m1m.spring.boot.bootreactive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@RestController
public class ApiController {

    private static final Logger LOG = LoggerFactory.getLogger(ApiController.class);

    @GetMapping("/hello")
    public Mono<String> getHelloA() {
        return Mono.just("Hello from Spring-Boot");
    }

    @GetMapping("/hellob")
    public Mono<String> getHelloB() {
        return Mono.just("B Hello from Spring-Boot")
                .flatMap(s -> Mono.just(s + ", version B"));
    }

    @GetMapping("/helloc")
    public Mono<String> getHelloC() {

        LOG.info("req start");
        Duration delay = Duration.ofSeconds(5);

        Mono<String> result = Mono.just("v1 C Hello from Spring-Boot")
                .log()
                .delayElement(delay)
                .subscribeOn(Schedulers.parallel())
                .doOnTerminate(() -> LOG.info("req terminated"));

        LOG.info("req ended");
        return result;
    }

}
