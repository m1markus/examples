package ch.m1m.examples;


import brave.Span;
import brave.Tracer;
import brave.Tracing;
import brave.propagation.SamplingFlags;
import zipkin.reporter.AsyncReporter;
import zipkin.reporter.okhttp3.OkHttpSender;

/**
 * docker run -d -p 9411:9411 openzipkin/zipkin
 *
 * browser url: http://localhost:9411/
 *
 * brave API: https://github.com/openzipkin/brave/tree/master/brave
 *
 *
 */
public class App {

    public static Tracer tracer;

    public static void level1(Span parent) throws InterruptedException {

        Span spanChild = tracer.newChild(parent.context()).name("level-1").start();
        spanChild.tag("uuid", "1");
        spanChild.tag("2", "");

        System.out.println("in level1()");

        Thread.sleep(100);

        level2(spanChild);

        spanChild.finish();
    }


    public static void level2(Span parent) throws InterruptedException {

        Span spanChild = tracer.newChild(parent.context()).name("level-2").start();

        System.out.println("in level2()");

        Thread.sleep(200);

        level3(spanChild);

        spanChild.finish();
    }


    public static void level3(Span parent) throws InterruptedException {

        Span spanChild = tracer.newChild(parent.context()).name("level-3").start();

        System.out.println("in level3()");

        Thread.sleep(300);

        spanChild.finish();
    }


    public static void main(String[] args) throws InterruptedException {
        System.out.println("begin zipkin/brave demo");

        // Configure a reporter, which controls how often spans are sent
        //   (the dependency is io.zipkin.reporter:zipkin-sender-okhttp3)
        OkHttpSender sender = OkHttpSender.create("http://localhost:9411/api/v1/spans");
        AsyncReporter reporter = AsyncReporter.create(sender);
        // Create a tracing component with the service name you want to see in Zipkin.
        Tracing tracing = Tracing.newBuilder()
                .localServiceName("zipkin-brave-demo")
                .reporter(reporter)
                .build();

        // Tracing exposes objects you might need, most importantly the tracer
        tracer = tracing.tracer();

        // set flags e.g. SamplingFlags.DEBUG;
        // .EMPTY
        // .SAMPLED
        // .NOT_SAMPLED
        SamplingFlags traceFlags = SamplingFlags.SAMPLED;

        Span spanMain = tracer.newTrace(traceFlags).name("main").start();

        spanMain.tag("clnt/finagle.version", "6.36.0");
        spanMain.tag("cache", "false");

        // just to play and explore the propagation interface
        //
        //tracing.propagation().


        level1(spanMain);

        spanMain.finish();

        // When all tracing tasks are complete, close the tracing component and reporter
        // This might be a shutdown hook for some users
        tracing.close();
        reporter.close();

        System.out.println("end");
    }
}
