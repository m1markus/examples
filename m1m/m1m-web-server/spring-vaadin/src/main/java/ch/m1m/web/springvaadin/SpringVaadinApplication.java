package ch.m1m.web.springvaadin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringVaadinApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SpringVaadinApplication.class, args);
	}
}
