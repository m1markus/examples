package ch.m1m.ldap;

import java.util.HashMap;
import java.util.Map;

public class JsonAddress2 {
    private String state;
    private String cityName;
    private String zipCode;

    private Map<String, String> country = new HashMap<>();


    public JsonAddress2() {
        country.put("long", "Switzerland");
        country.put("short", "CH");
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Map<String, String> getCountry() {
        return country;
    }

    public void setCountry(Map<String, String> country) {
        this.country = country;
    }
}
