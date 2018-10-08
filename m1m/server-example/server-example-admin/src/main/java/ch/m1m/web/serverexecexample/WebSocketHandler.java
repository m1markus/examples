package ch.m1m.web.serverexecexample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/*

https://www.baeldung.com/java-websockets

https://medium.com/@lejinkr/ldap-authentication-with-spring-boot-7b2d3fd08277

 */

@ServerEndpoint(
        value = "/websockets")

public class WebSocketHandler {

    static Logger log = LoggerFactory.getLogger(WebSocketHandler.class);

    private Session session;
    private static Set<WebSocketHandler> chatEndpoints = new CopyOnWriteArraySet<>();
    private static HashMap<String, String> users = new HashMap<>();

    @OnOpen
    public void onOpen(Session session) throws IOException {

        log.warn("onOpen() called");

        this.session = session;
        chatEndpoints.add(this);

        users.put(session.getId(), "remote");

        log.info("THIS IS NOT AN ERROR: onOpen() got called");

        /*
        Message message = new Message();
        message.setFrom(username);
        message.setContent("Connected!");
        */
        String msg = "Connected";

        broadcast(msg);
    }

    @OnMessage
    public void onMessage(String message, Session session)
            throws IOException {

        log.info("onMessage() called: {}", message);

        // message.setFrom(users.get(session.getId()));

        broadcast(message);
    }

    @OnClose
    public void onClose(Session session) throws IOException {

        log.warn("onClose() called");

        chatEndpoints.remove(this);
        /*
        Message message = new Message();
        message.setFrom(users.get(session.getId()));
        message.setContent("Disconnected!");
        */
        String msg = users.get(session.getId()) + ": Disconnected";
        broadcast(msg);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {

        log.error("onError() called");
        // Do error handling here
    }

    private static void broadcast(String message) {

        chatEndpoints.forEach(endpoint -> {
            synchronized (endpoint) {
                try {
                    endpoint.session.getBasicRemote().sendObject(message);
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

/*


 */
