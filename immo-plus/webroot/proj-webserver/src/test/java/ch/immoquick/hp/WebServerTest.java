package ch.immoquick.hp;

import io.vertx.ext.unit.TestOptions;
import io.vertx.ext.unit.TestSuite;
import io.vertx.ext.unit.report.ReportOptions;

public class WebServerTest {
    public static void main(String... args) {

        TestSuite suite = TestSuite.create(WebServerTest.class.getName());
        suite.before(context -> {
            System.out.println("...setup...code");
        });
        suite.beforeEach(context -> {
            System.out.println("...beforeEach...code");
        });
        suite.test("simple_equal_check-value-1", context -> {
            String s = "value-1";
            context.assertEquals("value-1", s);
        });8<<
        suite.test("simple_equal_check-value-2", context -> {
            String s = "value-2";
            context.assertEquals("value-2", s, "don't know why it has failed");
        });
        suite.afterEach(context -> {
            System.out.println("...afterEach...code");
        });
        suite.after(context -> {
            System.out.println("...cleanup...code");
        });
        suite.run(new TestOptions().addReporter(new ReportOptions().setTo("console")));
    }
}
