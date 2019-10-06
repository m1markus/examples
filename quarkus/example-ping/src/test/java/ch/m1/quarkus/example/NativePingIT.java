package ch.m1.quarkus.example;

import io.quarkus.test.junit.SubstrateTest;

@SubstrateTest
public class NativePingIT extends PingTest {

    // Execute the same tests but in native mode.
}