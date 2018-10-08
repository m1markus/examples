package ch.m1m.web.tomcat;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import java.io.File;

public class Main {
    private static final String STATIC_DIR = "src/main/static/";

    public static void main(String[] args) throws ServletException, LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        File staticDir = new File(STATIC_DIR);
        tomcat.addWebapp("/", staticDir.getAbsolutePath());

        tomcat.start();
        tomcat.getServer().await();
    }
}
