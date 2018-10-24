import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class App {

    public static void main(String[] args) throws Exception {
        Form form = new Form();
        HttpServer server = HttpServer.create(new InetSocketAddress(9000), 0);
        server.createContext("/", new RootController(form));
        server.setExecutor(null);
        server.start();
    }


}