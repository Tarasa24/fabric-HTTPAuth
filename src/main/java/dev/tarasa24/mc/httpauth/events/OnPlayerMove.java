package dev.tarasa24.mc.httpauth.events;

import static dev.tarasa24.mc.httpauth.HttpAuth.playerManager;

import dev.tarasa24.mc.httpauth.PlayerObject;
import net.minecraft.server.network.ServerPlayerEntity;

public class OnPlayerMove {
  private static long lastPacket = 0;

  public static boolean canMove(ServerPlayerEntity player) {
    PlayerObject playerObject = playerManager.get(player);
    boolean authenticated = playerObject.isAuthenticated();
    if (!authenticated && System.nanoTime() >= (lastPacket + 5000000L)) {
      player.requestTeleport(player.getX(), player.getY(), player.getZ());
      lastPacket = System.nanoTime();
    }
    return authenticated;
  }
}