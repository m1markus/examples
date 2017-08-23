package ch.m1m.soap.test;

import ch.m1m.xmlns.soap.hello.*;

import org.junit.Ignore;
import org.junit.Test;

import javax.xml.ws.BindingProvider;

public class HelloTest {

    public static final String endpUrlHello = "http://localhost:8080/SoapHello/services/SoapHello";

    @Test
    public void alwaysSuccess() {
    }


    @Test
    //@Ignore
    public void testSoapHello() {

        System.out.println("begin test ...");

        SoapHello_Service service = new SoapHello_Service();
        SoapHello port = service.getSoapHelloSOAP();

        BindingProvider bp = (BindingProvider) port;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpUrlHello);

        HelloRequestType input = new HelloRequestType();
        input.setFirstName("Markus");
        HelloResponseType output = port.getHello(input);

        System.out.println("response: output=" + output);
        System.out.println("lastname: " + output.getPerson().getLastName());
    }
}
