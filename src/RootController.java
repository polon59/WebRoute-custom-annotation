import java.lang.reflect.InvocationTargetException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class RootController implements HttpHandler {
    
        private Form form;
    
        RootController(Form form) {
            this.form = form;
        }
    
        public void handle(HttpExchange httpExchange) {
    
            String path = httpExchange.getRequestURI().getPath();
            String firstSegment = getFirstSegmentOfURI(path);
            HttpMethod httpMethod = httpExchange.getRequestMethod().equals("GET") ? HttpMethod.GET : HttpMethod.POST;
    
            Class<Form> aClass = Form.class;
    
            for (Method method : aClass.getDeclaredMethods()) {
    
                if (method.isAnnotationPresent(WebRoute.class)) {
    
                    Annotation annotation = method.getAnnotation(WebRoute.class);
                    WebRoute webRoute = (WebRoute) annotation;
    
                    if (webRoute.method().equals(httpMethod) && webRoute.path().equals(firstSegment)) {
    
                        try {
                            method.invoke(form, httpExchange);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    
        private String getFirstSegmentOfURI(String path) {
            String[] segments = path.split("/");
            return segments.length == 0 ? "/" : "/" + segments[1];
        }
    }