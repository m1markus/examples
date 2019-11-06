package ch.m1m.commons;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AddPersonRequest {

    private String requestId;

    private Person person;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
