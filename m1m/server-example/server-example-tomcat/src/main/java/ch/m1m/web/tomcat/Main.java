package ch.m1m.web.tomcat;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.Writer;

public class Main {
    private static final String STATIC_DIR = "src/main/static/";

    public static void main(String[] args) throws ServletException, LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        File staticDir = new File(STATIC_DIR);
        tomcat.addWebapp("/", staticDir.getAbsolutePath());


        Context ctx = tomcat.addContext("/", new File(".").getAbsolutePath());

        Tomcat.addServlet(ctx, "hello", new HttpServlet() {
            protected void service(HttpServletRequest req, HttpServletResponse resp)
                    throws ServletException, IOException {
                Writer w = resp.getWriter();
                w.write("Hello, World!");
                w.flush();
            }
        });
        ctx.addServletMapping("/hello", "hello");


        tomcat.start();
        tomcat.getServer().await();
    }
}
