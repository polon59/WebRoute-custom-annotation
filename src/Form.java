import com.sun.net.httpserver.HttpExchange;  
import com.sun.net.httpserver.HttpHandler;  

import java.io.*;  
import java.net.URLDecoder;  
import java.util.HashMap;  
import java.util.Map;  


public class Form{  

    String response = ""; 

    @WebRoute(method = HttpMethod.GET, path = "/first")
    void onTest(HttpExchange httpExchange) throws IOException {
        response = "<input type=\"submit\" value=\"Submit\">\n" + " First Response ";

        System.out.println("FORM: Entered +++GET+++ FIRST");

        httpExchange.sendResponseHeaders(200, response.length());  
        OutputStream os = httpExchange.getResponseBody();  
        os.write(response.getBytes());  
        os.close();
    }

    @WebRoute(method = HttpMethod.GET, path = "/second")
    void onAnotherTest(HttpExchange httpExchange) throws IOException {
        response = "<input type=\"submit\" value=\"Submit\">\n" + " Second Response ";

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

        // httpExchange.sendResponseHeaders(200, response.length());  
        // OutputStream os = httpExchange.getResponseBody();  
        // os.write(response.getBytes());  
        // os.close();
    }

    @WebRoute(method = HttpMethod.POST, path = "/second")
    void onPostAnotherTest(HttpExchange httpExchange) throws IOException {
        redirectToPath(httpExchange, "/first");

        System.out.println("FORM: Entered +++POST+++ SECOND");

        // httpExchange.sendResponseHeaders(200, response.length());  
        // OutputStream os = httpExchange.getResponseBody();  
        // os.write(response.getBytes());  
        // os.close();
    }

    private void redirectToPath(HttpExchange httpExchange, String path) throws IOException {
        httpExchange.getResponseHeaders().add("Location", path);
        httpExchange.sendResponseHeaders(301, -1);
    }

    // if(method.equals("POST")){  
    //     InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");  
    //     BufferedReader br = new BufferedReader(isr);  
    //     String formData = br.readLine();  

    //     System.out.println(formData);  
    //     Map inputs = parseFormData(formData);  

    //     response = "<html><body>" +  
    //             "<h1>Hello " +  
    //             inputs.get("firstname") + " " + inputs.get("lastname") +  
    //             "!</h1>" +  
    //             "</body><html>";  
    // }  


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
