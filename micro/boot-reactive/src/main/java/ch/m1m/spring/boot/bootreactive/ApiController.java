package ch.m1m.spring.boot.bootreactive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

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

        Mono<String> result = Mono.just("C Hello from Spring-Boot")
                .delayElement(delay)
                .doOnTerminate(() -> LOG.info("req terminated"));

        LOG.info("req ended");
        return result;
    }

}
