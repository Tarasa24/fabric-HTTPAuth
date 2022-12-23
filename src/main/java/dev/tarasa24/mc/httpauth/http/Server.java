package dev.tarasa24.mc.httpauth.http;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.*;

public class Server {
  private static HttpServer server;

  public static void start(int port) throws IOException {
    try {
      server = HttpServer.create(new InetSocketAddress(port), 0);

      server.createContext("/", new RootHandler());
      server.setExecutor(null);
      server.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
