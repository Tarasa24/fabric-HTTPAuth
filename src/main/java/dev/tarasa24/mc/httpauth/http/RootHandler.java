package dev.tarasa24.mc.httpauth.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import dev.tarasa24.mc.httpauth.PlayerObject;
import static dev.tarasa24.mc.httpauth.HttpAuth.playerManager;
import static dev.tarasa24.mc.httpauth.HttpAuth.LOGGER;
import static dev.tarasa24.mc.httpauth.HttpAuth.config;

public class RootHandler implements HttpHandler {
  private boolean auth(String nickname) {
    PlayerObject playerObject = playerManager.get(nickname);
    if (playerObject == null || playerObject.isAuthenticated() || !playerObject.isOnline()) {
      return false;
    } else {
      playerObject.authenticate();
      LOGGER.info("Player " + nickname + " logged in");
      return true;
    }
  }

  @Override
  public void handle(HttpExchange xchg) throws IOException {
    InputStream is = getClass().getResourceAsStream("/assets/httpauth/index.html");
    String html = new String(is.readAllBytes());

    if (xchg.getRequestHeaders().getFirst(config.nicknameHeader) != null) {
      String nickname = xchg.getRequestHeaders().getFirst(config.nicknameHeader);

      if (auth(nickname)) {
        html = html.replace("{{MESSAGE}}", "You are now authenticated as " + nickname);
      } else {
        html = html.replace("{{MESSAGE}}", "Authentication failed");
      }
      byte[] buffer = html.getBytes();
      xchg.sendResponseHeaders(200, buffer.length);

      OutputStream os = xchg.getResponseBody();
      os.write(buffer);
      os.close();
    } else {
      // Redirect to Set-Nickname flow
      xchg.getResponseHeaders().add("Location", config.redirectUrl);
      xchg.sendResponseHeaders(302, -1);

      xchg.close();
    }
  }
}
