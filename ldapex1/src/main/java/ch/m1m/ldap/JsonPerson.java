package ch.m1m.ldap;

public class JsonPerson {

    private String firstName;
    private String lastName;

    private JsonAddress address;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public JsonAddress getAddress() {
        return address;
    }

    public void setAddress(JsonAddress address) {
        this.address = address;
    }
}
