package ch.m1m.server.micronaut;

import io.micronaut.runtime.Micronaut;

// https://docs.micronaut.io/latest/guide/index.html
//
//
// http://localhost:8080/hello/world
// http://localhost:8080/hello/session
// http://localhost:8080/static
//

public class Main {

    public static void main(String[] args) {

        Micronaut.run(Main.class);
    }
}