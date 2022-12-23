package dev.tarasa24.mc.httpauth.events;

import static dev.tarasa24.mc.httpauth.HttpAuth.playerManager;

import dev.tarasa24.mc.httpauth.PlayerObject;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;

public class LeaveListener {
  public static void listen(ServerPlayNetworkHandler handler, MinecraftServer server) {
    var player = handler.player;
    PlayerObject playerObject = playerManager.get(player);
    playerObject.deauthenticate();
    playerObject.setOnline(false);
  }
}
