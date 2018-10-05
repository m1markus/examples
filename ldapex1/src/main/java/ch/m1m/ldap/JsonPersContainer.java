package ch.m1m.ldap;

import java.util.ArrayList;
import java.util.List;

public class JsonPersContainer {

    private List<JsonPerson> personList = new ArrayList<>();

    private JsonPersContainer() {}

    public static JsonPersContainer newInstance() {
        System.err.println("JsonPersContainer # newInstance() called");
        return new JsonPersContainer();
    }

    public List<JsonPerson> getPersonList() {
        return personList;
    }

    public void setPersonList(List<JsonPerson> personList) {
        this.personList = personList;
    }
}
