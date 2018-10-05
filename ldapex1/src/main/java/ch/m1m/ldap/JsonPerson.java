package ch.m1m.ldap;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;

public class JsonPerson {

    private String firstName;
    private String lastName;

    private JsonAddress address;

    private Date today;

    private LocalDate birthday;

    private LocalDateTime lastAccess;

    private OffsetDateTime nextPwChange;


    public JsonPerson() {

    }

    public JsonPerson(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

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

    public Date getToday() {
        return today;
    }

    public void setToday(Date today) {
        this.today = today;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDateTime getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(LocalDateTime lastAccess) {
        this.lastAccess = lastAccess;
    }

    public OffsetDateTime getNextPwChange() {
        return nextPwChange;
    }

    public void setNextPwChange(OffsetDateTime nextPwChange) {
        this.nextPwChange = nextPwChange;
    }

    @Override
    public String toString() {
        return "JsonPerson{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address=" + address +
                '}';
    }
}
