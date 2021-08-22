package ch.m1m;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App 
{
    public static void main( String[] args )
    {
        EventBus eventBus = new AsyncEventBus(Executors.newCachedThreadPool());

        App app = new App();
        // register all subscribers
        eventBus.register(app);

        // fire events
        //
        eventBus.post(new String("hello"));
        eventBus.post(BigDecimal.valueOf(99));
        eventBus.post(new Object());

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }

        System.out.println( "### end ###" );
    }

    @Subscribe
    public void subString(String inString) {
        System.out.println("subString() received: " + inString);
    }

    @Subscribe
    public void subBigDecimal(BigDecimal inDecimal) {
        System.out.println("subBigDecimal() received: " + inDecimal.toString());
    }

    @Subscribe
    public void subForDeadEvents(DeadEvent deadEvent) {
        System.out.println("DeadEvent Subscriber: ");
        Object obj = deadEvent.getEvent();
        if (obj instanceof BigDecimal) {
            System.out.println("  obj is BigDecimal");
        } else {
            System.out.println("  obj is Object");
        }
    }
}
