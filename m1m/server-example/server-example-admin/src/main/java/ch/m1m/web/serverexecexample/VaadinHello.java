package ch.m1m.web.serverexecexample;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Route("")
public class VaadinHello extends Div {

    static Logger log = LoggerFactory.getLogger(VaadinHello.class);

    public VaadinHello() {
        setText("Hello Vaadin!");

        log.info("vaadin test log message");
    }
}