package ch.m1m.ldap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonMain {

    public static void main(String ...args) throws IOException {
        System.out.println("starting...");

        JsonPersContainer container1 = newContainer();

        ObjectMapper mapper = new ObjectMapper();

        // format object to json
        String jsonPretty1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(container1);

        System.out.println("json output container1:");
        System.out.println(jsonPretty1);


        // read back a new object

        JsonPersContainer container2 = mapper.readValue(jsonPretty1, JsonPersContainer.class);

        String jsonPretty2 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(container2);

        System.out.println("json output container2:");
        System.out.println(jsonPretty2);

        if (jsonPretty1.equals(jsonPretty2)) {
            System.out.println("success: both buffers are the same");
        } else {
            System.err.println("ERROR: the two json buffers do not match");
        }
    }

    private static JsonPersContainer newContainer() {

        JsonPersContainer persCont = new JsonPersContainer();

        JsonPerson pers1 = new JsonPerson();
        pers1.setFirstName("Markus");
        pers1.setLastName("Meier");
        JsonAddress addr1 = new JsonAddress();
        addr1.setCityName("Basel");
        addr1.setZipCode("4051");
        pers1.setAddress(addr1);
        persCont.getPersonList().add(pers1);

        JsonPerson pers2 = new JsonPerson();
        pers2.setFirstName("Simon");
        pers2.setLastName("Lang");
        JsonAddress addr2 = new JsonAddress();
        addr2.setCityName("Bottmingen");
        addr2.setZipCode("4103");
        pers2.setAddress(addr2);
        persCont.getPersonList().add(pers2);

        return persCont;
    }

}
