package ch.m1m.web.springvaadin;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

@Route("")
public class HelloVaadin extends Div {
    public HelloVaadin(){
        setText("Hello Vaadin!");
    }
}