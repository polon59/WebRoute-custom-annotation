import com.sun.net.httpserver.HttpExchange;  
import com.sun.net.httpserver.HttpHandler;  

import java.io.*;  
import java.net.URLDecoder;  
import java.util.HashMap;  
import java.util.Map;  
import java.io.OutputStream;


public class Form{  

    String response = ""; 

    @WebRoute(method = HttpMethod.GET, path = "/first")
    void onTest(HttpExchange httpExchange) throws IOException {

        response = "<html><body>" +
        "<br><form method=\"POST\">\n" +
        "<h1>First</h1>"+
        "<input type=\"submit\" value=\"SUBMIT\">\n" +
        "</form> " +
        "</body></html>";

        System.out.println("FORM: Entered +++GET+++ FIRST");

        httpExchange.sendResponseHeaders(200, response.length());  
        OutputStream os = httpExchange.getResponseBody();  
        os.write(response.getBytes());  
        os.close();
    }

    @WebRoute(method = HttpMethod.GET, path = "/second")
    void onAnotherTest(HttpExchange httpExchange) throws IOException {

        response = "<html><body>" +
        "<br><form method=\"POST\">\n" +
        "<h1>Second</h1>"+
        "<input type=\"submit\" value=\"SUBMIT\">\n" +
        "</form> " +
        "</body></html>";

        System.out.println("FORM: Entered +++GET+++ SECOND");

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    @WebRoute(method = HttpMethod.POST, path = "/first")
    void onPostTest(HttpExchange httpExchange) throws IOException {
        redirectToPath(httpExchange, "/second");

        System.out.println("FORM: Entered +++POST+++ FIRST");
    }

    @WebRoute(method = HttpMethod.POST, path = "/second")
    void onPostAnotherTest(HttpExchange httpExchange) throws IOException {
        redirectToPath(httpExchange, "/first");

        System.out.println("FORM: Entered +++POST+++ SECOND");
    }

    private void redirectToPath(HttpExchange httpExchange, String path) throws IOException {
        httpExchange.getResponseHeaders().add("Location", path);
        httpExchange.sendResponseHeaders(301, -1);
    }
}
