package dev.tarasa24.mc.httpauth;

import java.io.IOException;
import static dev.tarasa24.mc.httpauth.HttpAuth.LOGGER;
import com.google.gson.Gson;

public class Config {
  public Number httpPort = 8080;
  public String nicknameHeader = "X-App-User";
  public String authUrl = "https://mc.dormlab.tarasa24.dev";
  public String redirectUrl = "https://auth.dormlab.tarasa24.dev/if/flow/set-minecraft_nickname/";

  public Config(String filename) {
    String pwd = System.getProperty("user.dir");
    String path = pwd + "/mods/" + filename;

    String json;
    try {
      json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(path)));
    } catch (IOException e) {
      LOGGER.error("Failed to read config file, using defaults");
      return;
    }
    Config config = new Gson().fromJson(json, Config.class);

    // config can be incomplete
    this.httpPort = config.httpPort != null ? config.httpPort : this.httpPort;
    this.nicknameHeader = config.nicknameHeader != null ? config.nicknameHeader : this.nicknameHeader;
    this.redirectUrl = config.redirectUrl != null ? config.redirectUrl : this.redirectUrl;

    LOGGER.info("Config loaded from " + path);
  }
}
