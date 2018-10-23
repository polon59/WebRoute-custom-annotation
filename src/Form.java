import com.sun.net.httpserver.HttpExchange;  
import com.sun.net.httpserver.HttpHandler;  

import java.io.*;  
import java.net.URLDecoder;  
import java.util.HashMap;  
import java.util.Map;  


public class Form{  

    String response = ""; 

    @WebRoute(method = "GET", path = "/first")
    void onTest(HttpExchange httpExchange) throws IOException {
        response = "<input type=\"submit\" value=\"Submit\">\n" + " First Response ";

        httpExchange.sendResponseHeaders(200, response.length());  
        OutputStream os = httpExchange.getResponseBody();  
        os.write(response.getBytes());  
        os.close();
    }

    @WebRoute(method = "GET", path = "/second")
    void onAnotherTest(HttpExchange httpExchange) throws IOException {
        response = "<input type=\"submit\" value=\"Submit\">\n" + " Second Response ";

        httpExchange.sendResponseHeaders(200, response.length());  
        OutputStream os = httpExchange.getResponseBody();  
        os.write(response.getBytes());  
        os.close();
    }

    @WebRoute(method = "POST", path = "/first")
    void onPostTest(HttpExchange httpExchange) throws IOException {
        redirectToPath(httpExchange, "/second");

        httpExchange.sendResponseHeaders(200, response.length());  
        OutputStream os = httpExchange.getResponseBody();  
        os.write(response.getBytes());  
        os.close();
    }

    @WebRoute(method = "POST", path = "/second")
    void onPostAnotherTest(HttpExchange httpExchange) throws IOException {
        redirectToPath(httpExchange, "/first");

        httpExchange.sendResponseHeaders(200, response.length());  
        OutputStream os = httpExchange.getResponseBody();  
        os.write(response.getBytes());  
        os.close();
    }

    private void redirectToPath(HttpExchange httpExchange, String path) throws IOException {
        httpExchange.getResponseHeaders().add("Location", path);
        httpExchange.sendResponseHeaders(301, -1);
    }


    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {  
        Map<String, String> map = new HashMap<>();  
        String[] pairs = formData.split("&");  
        for(String pair : pairs){  
            String[] keyValue = pair.split("=");  
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms  
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");  
            map.put(keyValue[0], value);  
        }  
        return map;  
    }  
}
