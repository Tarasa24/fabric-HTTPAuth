package dev.tarasa24.mc.httpauth;

import net.fabricmc.api.ModInitializer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.tarasa24.mc.httpauth.events.*;
import dev.tarasa24.mc.httpauth.http.Server;
import dev.tarasa24.mc.httpauth.managers.PlayerManager;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class HttpAuth implements ModInitializer {

  public static final Logger LOGGER = LoggerFactory.getLogger("httpauth");
  public static final PlayerManager playerManager = new PlayerManager();
  public static Config config;

  @Override
  public void onInitialize() {
    try {
      config = new Config("httpauth.json");
      Server.start(config.httpPort.intValue());
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Events
    ServerPlayConnectionEvents.JOIN.register(JoinListener::listen);
    ServerPlayConnectionEvents.DISCONNECT.register(LeaveListener::listen);

    LOGGER.info(
        "HTTP Auth initialized, listening at port " + config.httpPort + " for '" + config.nicknameHeader + "' header");
  }
}