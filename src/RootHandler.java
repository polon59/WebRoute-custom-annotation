import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class RootHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange t) throws IOException {
        String response = "<h1>Server start success if you see this message</h1>" + "<h1>Port: 9000</h1>";;
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}