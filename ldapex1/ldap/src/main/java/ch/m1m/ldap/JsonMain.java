package ch.m1m.ldap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;

public class JsonMain {


    public static void main(String ...args) throws IOException {

        //test_PersContainer();

        test_Address_compatability();

        //test_Date_jsr310();

        //test_LocalDate_plain();
    }


    public static void test_LocalDate_plain() throws IOException {

        System.out.println("starting... test_LocalDate_plain()");

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        //mapper.registerModule(new Jdk8Module());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        LocalDate date = LocalDate.now();
        String jsonPretty1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(date);
        printJson("LocalDate 1", jsonPretty1);

        LocalDate date2 = mapper.readValue(jsonPretty1, LocalDate.class);
        String jsonPretty2 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(date2);
        printJson("LocalDate 2", jsonPretty2);
    }


    public static void test_Date_jsr310() throws JsonProcessingException {

        System.out.println("starting... test_Date_jsr310()");

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        JsonPerson pers = new JsonPerson();
        LocalDate birthDay = LocalDate.now();
        pers.setBirthday(birthDay);

        LocalDateTime lacc = LocalDateTime.now();
        pers.setLastAccess(lacc);

        OffsetDateTime pwChange = OffsetDateTime.now();
        pers.setNextPwChange(pwChange);

        Date today = new Date();
        pers.setToday(today);

        String jsonPretty1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pers);
        printJson("Person", jsonPretty1);
    }

    public static void printJson(String what, String jsonPayload) {
        StringBuilder sb = new StringBuilder();
        sb.append("Json Output: ");
        sb.append(what);
        sb.append("\n");
        sb.append(jsonPayload);
        System.out.println(sb.toString());
    }


    public static void test_Address_compatability() throws IOException {

        System.out.println("starting... test_Address_compatability()");

        JsonAddress2 addr2 = new JsonAddress2();
        addr2.setState("BL");
        addr2.setCityName("Binningen");

        ObjectMapper mapper = new ObjectMapper();
        //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // format object to json
        String jsonPretty1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(addr2);

        System.out.println("json output addr2:");
        System.out.println(jsonPretty1);

        // read back a new object

        JsonAddress addr = mapper.readValue(jsonPretty1, JsonAddress.class);

        String jsonPretty2 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(addr);

        System.out.println("json output addr:");
        System.out.println(jsonPretty2);
    }


    public static void test_PersContainer() throws IOException {

        System.out.println("starting... test_PersContainer()");

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

        JsonPersContainer persCont = JsonPersContainer.newInstance();

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
