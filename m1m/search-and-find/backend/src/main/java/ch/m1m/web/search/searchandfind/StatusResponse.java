package ch.m1m.web.search.searchandfind;

public class StatusResponse {

    private String status;

    private String key;

    public StatusResponse(String status, String key) {
        this.status = status;
        this.key = key;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
