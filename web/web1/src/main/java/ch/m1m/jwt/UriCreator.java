package ch.m1m.jwt;

import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;

public class UriCreator {

    public static void main(String... args) {

        try {
            String newURI = new URIBuilder("http://m1m.ch/token")
                    .addParameter("out", "cookie <todo>")
                    .toString();
                    //.build();   // URI

            System.out.println("final uri: " + newURI);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
