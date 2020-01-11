package ch.m1m.jwt;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

// http://localhost:8080/web1/hello

@WebServlet("/hello")
public class ServletSimple extends HttpServlet {
	
	private static String message = null;
	private static final Logger LOGGER = Logger.getLogger(ServletSimple.class);

	public void init() throws ServletException {
		// Do required initialization
		message = "Hello World";
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Set response content type
		response.setContentType("text/html");
		
		LOGGER.error("dummy message for testing only.");
		LOGGER.info("dummy message for testing only.");

		// Actual logic goes here.
		PrintWriter out = response.getWriter();
		out.println("<h1>" + message + "</h1>");
	}

	public void destroy() {
		// do nothing.
	}

}
