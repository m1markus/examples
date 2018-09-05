package ch.m1m.server.micronaut;

import io.micronaut.http.annotation.*;
import io.micronaut.session.Session;

import java.util.Optional;

@Controller("/hello")
public class HelloController {

    private static final String SESS_KEY_USERNAME = "SESS_KEY_USERNAME";

    @Get("/")
    public String onlyHello() {

        return "Hello World only from micronaut";
    }

    @Get("/world")
    public String myWorld(Session session) {
        session.put(SESS_KEY_USERNAME, "user_world");

        return "Hello World with session";
    }

    @Get("/session")
    public String mySession(Session session) {

        String user_name = "user-not-set";
        Optional<String> name = session.get(SESS_KEY_USERNAME, String.class);

        if (name.isPresent()) {
            user_name = name.get();
        }

        String msg = String.format("user from session is: %s", user_name);
        return msg;
    }

}

/*

    Cart addItem(Session session, @NotBlank String name) {
        Cart cart = session.get(ATTR_CART, Cart.class).orElseGet(() -> {
            Cart newCart = new Cart();
            session.put(ATTR_CART, newCart);
            return newCart;
        });
        cart.getItems().add(name);
        return cart;
    }

 */