package ch.m1m.commons;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;

public class XmlTools {

    public static <T> T stringToObject(String inXmlString, Class<T> clazz) throws XMLStreamException, JAXBException, UnsupportedEncodingException {
        return stringToObject(inXmlString, clazz, null);
    }

    public static <T> T stringToObject(String inXmlString, Class<T> clazz, String nodeName) throws XMLStreamException, JAXBException, UnsupportedEncodingException {
        T retInstance = null;
        if (nodeName == null) {
            retInstance = JAXB.unmarshal(new StringReader(inXmlString), clazz);
        } else {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(inXmlString.getBytes("UTF-8"));
            retInstance = streamToObject(inputStream, clazz, nodeName);
        }
        return retInstance;
    }

    public static <T> T fileToObject(File inFile, Class<T> clazz) throws FileNotFoundException, JAXBException, XMLStreamException {
        return fileToObject(inFile, clazz, null);
    }

    public static <T> T fileToObject(File inFile, Class<T> clazz, String nodeName) throws FileNotFoundException, JAXBException, XMLStreamException {
        FileInputStream fis = new FileInputStream(inFile);
        return streamToObject(fis, clazz, nodeName);
    }

    public static String objectToString(Object pojo) throws JAXBException {
        StringWriter sw = new StringWriter();
        JAXB.marshal(pojo, sw);
        return sw.toString();
    }

    public static <T> T streamToObject(InputStream inputStream, Class<T> clazz, String nodeName) throws XMLStreamException, JAXBException {
        T retInstance = null;
        XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
        xmlInputFactory.setProperty("javax.xml.stream.isSupportingExternalEntities", Boolean.FALSE);
        xmlInputFactory.setProperty("javax.xml.stream.supportDTD", Boolean.FALSE);

        XMLStreamReader xmlReader = xmlInputFactory.createXMLStreamReader(inputStream);

        while (xmlReader.hasNext() && (!xmlReader.isStartElement() || !nodeName.equals(xmlReader.getLocalName()))) {
            xmlReader.next();
        }
        if (!xmlReader.isStartElement()) {
            String errorMessage = String.format("XmlTools#streamToObject() Element '%s' not found in input", nodeName);
            throw new RuntimeException(errorMessage);
        }
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        retInstance = (T) unmarshaller.unmarshal(xmlReader);
        return retInstance;
    }

}

/*

static RenderDocumentRequest getRenderDocumentRequestFromClasspathFile(String filename, String nodeName)
        throws JAXBException, URISyntaxException, XMLStreamException {

    final JAXBContext jaxbContext = JAXBContext.newInstance(RenderDocumentRequest.class);
    final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

    if (nodeName == null) {
        InputStream inputStream = TestUtils.class.getClassLoader().getResourceAsStream(filename);
        return (RenderDocumentRequest) unmarshaller.unmarshal(inputStream);
    } else {
        URL resourceURL = TestUtils.class.getClassLoader().getResource(filename);
        File file = Paths.get(resourceURL.toURI()).toFile();
        XMLStreamReader xmlStreamReader = XmlInputUtils.proceedToNode(file, nodeName);

        return (RenderDocumentRequest) unmarshaller.unmarshal(xmlStreamReader);
    }
}

*/
