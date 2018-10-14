package ch.m1m.web.domain;

import java.util.Collections;
import java.util.List;

public class LinkItem {

    private String url;

    private String shortDescription;

    private String longDescription;

    List<String> keywords = Collections.emptyList();


    public LinkItem(String url, String shortDescription, String longDescription) {
        this.url = url;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
