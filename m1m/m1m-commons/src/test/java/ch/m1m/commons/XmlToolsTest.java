package ch.m1m.commons;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

class XmlToolsTest {

    private static final Logger log = LoggerFactory.getLogger(XmlToolsTest.class);

    @BeforeAll
    static void setupOnce() {
        log.info("@BeforeAll - executes once before all test methods in this class");
    }

    @BeforeEach
    void initBeforeEachMethod() {
        log.info("@BeforeEach - executes before each test method in this class");
    }

    @Test
    //@Disabled("Not implemented yet")
    //@Disable
    public void GivenPojo_whenConvertingToString_thenWeCanUnmarshall() throws JAXBException, UnsupportedEncodingException, XMLStreamException {
        Person origPerson = createNewPerson();
        String xml1 = XmlTools.objectToString(origPerson);
        log.info("origninal Person xml1={}", xml1);

        Person person2 = XmlTools.stringToObject(xml1, Person.class);
        String xml2 = XmlTools.objectToString(person2);
        log.info("unmarhalled Person xml2={}", xml2);

        assertTrue(origPerson.equals(person2));
    }

    @Test
    public void GivenPojo_whenUnmarshallingOnlySubpart_thenWeGetValidPojo() throws JAXBException, UnsupportedEncodingException, XMLStreamException {

        // and now with the request
        //
        AddPersonRequest addPersonRequest = new AddPersonRequest();
        Person origPerson = createNewPerson();
        addPersonRequest.setPerson(origPerson);

        String strAddPersonRequest = XmlTools.objectToString(addPersonRequest);
        log.info("addPersonRequest={}", strAddPersonRequest);

        Person person2 = XmlTools.stringToObject(strAddPersonRequest, Person.class, "person");

        assertTrue(origPerson.equals(person2));
    }

    @Test
    public void GivenInputFile_whenUnmashallingOnlySubpart_thenWeGetValidPojo() throws FileNotFoundException, JAXBException, XMLStreamException {
        String fileName = "/Users/mue/work/git/github_m1markus/examples/m1m/m1m-commons/src/test/resources/AddPersonRequest_1.xml";
        fileName = "src/test/resources/AddPersonRequest_1.xml";

        log.info("current directory: {}", System.getProperty("user.dir"));

        File file = new File(fileName);

        Person person = XmlTools.fileToObject(file, Person.class, "person");

        Person origPerson = createNewPerson();
        assertTrue(origPerson.equals(person));
    }

    @Test
    public void GivenInputStream_whenUnmashallingOnlySubpart_thenWeGetValidPojo() throws FileNotFoundException, JAXBException, XMLStreamException {
        String fileName = "AddPersonRequest_1.xml";

        InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName);
        log.info("input stream: {}", is);

        Person person = XmlTools.streamToObject(is, Person.class, "person");

        Person origPerson = createNewPerson();
        assertTrue(origPerson.equals(person));
    }

    @Test
    public void GivenInvalidXML_whenUnmarshalling_whenThrowException() throws JAXBException, UnsupportedEncodingException, XMLStreamException {

        Throwable exception = assertThrows(RuntimeException.class, () -> {

            String strAddPersonRequest = "<empty/>";
            Person person2 = XmlTools.stringToObject(strAddPersonRequest, Person.class, "person");

        });
        assertEquals(exception.getMessage(), "XmlTools#streamToObject() Element 'person' not found in input");
    }

    public static Person createNewPerson() {
        Person person = new Person();
        person.setFirstname("Markus");
        person.setLastname("Müller");
        return person;
    }

}

/*
    info: https://www.baeldung.com/junit-5

    assumeTrue(5 > 1);
    assumeFalse(5 < 1);

    assertEquals(5 + 2, 7);
*/
