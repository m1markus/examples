package ch.m1m.web.springvaadin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringVaadinApplication extends SpringBootServletInitializer {

	static Logger log = LoggerFactory.getLogger(SpringVaadinApplication.class);

	public static void main(String[] args) {

		log.error("this is not an error, just a test message");

		SpringApplication.run(SpringVaadinApplication.class, args);
	}
}
