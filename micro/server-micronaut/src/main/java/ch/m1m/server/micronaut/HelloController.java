package ch.m1m.server.micronaut;

import io.micronaut.http.annotation.*;

@Controller("/hello")
public class HelloController {
    @Get("/world")
    public String index() {
        return "Hello World";
    }
}