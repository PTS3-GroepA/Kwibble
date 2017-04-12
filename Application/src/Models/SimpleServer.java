package Models;

import Data.Context.SpotifyContext;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by Max Meijer on 03/04/2017.
 * Fontys University of Applied Sciences, Eindhoven
 *
 * Class handles setting up a small http server for handling Spotify's authorisations
 * Spotify sends a code through a Http Post.
 * We currently get it by redirecting the URI code to this local server
 * This is probably insecure but we'll worry about that later.
 *
 * See https://developer.spotify.com/web-api/authorization-guide/ for more details.
 */

public class SimpleServer {

    private HttpServer server;
    private int port;
    Map <String,String> parms;
    private SpotifyContext context;
    private Quiz quiz;

    public SimpleServer(SpotifyContext context, Quiz quiz) {
        port = 9000;
        parms = null;
        this.context = context;
        this.quiz = quiz;

        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.createContext("/", new RootHandler());
        server.createContext("/spotify", new EchoPostHandler());
        server.setExecutor(null); // creates a default executor
    }

    public void start() {
        server.start();
    }

    public class RootHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange he) throws IOException {
            String response = "<h1>Server start success if you see this message</h1>" + "<h1>Port: " + port + "</h1>";
            he.sendResponseHeaders(200, response.length());
            OutputStream os = he.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    public class EchoPostHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange he) throws IOException {
            System.out.println("Got a post");
            parms = queryToMap(he.getRequestURI().getQuery());
            System.out.println(parms);

            if(parms != null) {
                context.authoriseCredentials(getCode());
                stop();

                if(context.checkAuthorization()) {
                    quiz.confirmAuthorisation();
                }
            }
        }
    }

    public static Map<String, String> queryToMap(String query){
        Map<String, String> result = new HashMap<String, String>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length>1) {
                result.put(pair[0], pair[1]);
            }else{
                result.put(pair[0], "");
            }
        }
        return result;
    }

    public String getCode() {
        return parms.get("code");
    }

    public void stop() {
        server.stop(0);
    }
}
