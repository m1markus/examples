package ch.m1m.quarkus.example;

public class EchoBody {

    private String callerId;

    private String messageText;

    private String sleepDuration;

    private String serverId;

    public String getCallerId() {
        return callerId;
    }

    public void setCallerId(String callerId) {
        this.callerId = callerId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getSleepDuration() {
        return sleepDuration;
    }

    public void setSleepDuration(String sleepDuration) {
        this.sleepDuration = sleepDuration;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }
}
