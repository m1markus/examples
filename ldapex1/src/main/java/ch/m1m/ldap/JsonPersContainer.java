package ch.m1m.ldap;

import java.util.ArrayList;
import java.util.List;

public class JsonPersContainer {

    private List<JsonPerson> personList = new ArrayList<>();

    public List<JsonPerson> getPersonList() {
        return personList;
    }

    public void setPersonList(List<JsonPerson> personList) {
        this.personList = personList;
    }
}
