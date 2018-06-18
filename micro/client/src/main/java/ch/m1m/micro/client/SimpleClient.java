package ch.m1m.micro.client;


import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

// http://square.github.io/okhttp/
//
public class SimpleClient {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static OkHttpClient client = new OkHttpClient();

    public static void main(String...args) {
        System.out.println("### sarting...");

        try {

            //test_jackson();
            //System.exit(1);

            String url = "http://localhost:8080/hello/world";
            //url = "http://x";
            //url = "http://tvvvgslwls039.sympany-test.ads:8001/em/";
            String resp = get(url);
            System.out.println(String.format("success: url=%s resp=%s", url, resp));


        } catch(IOException ex) {
            System.out.println(ex);
            System.exit(1);
        }
    }


    static void test_jackson() throws IOException {

        ObjectMapper mapper = new ObjectMapper();


        String jsonInput = "{\"id\":0,\"firstName\":\"Robin\",\"lastName\":\"Wilson\"}";
        Person q = mapper.readValue(jsonInput, Person.class);
        System.out.println("Read and parsed Person from JSON: " + q);

        Person p = new Person( 1,"Roger", "Rabbit");
        System.out.print("Person object " + p + " as JSON = ");
        mapper.writeValue(System.out, p);

    }


    static String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }


    static String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
