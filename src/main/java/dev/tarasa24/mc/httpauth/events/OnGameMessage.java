package dev.tarasa24.mc.httpauth.events;

import static dev.tarasa24.mc.httpauth.HttpAuth.playerManager;

import dev.tarasa24.mc.httpauth.PlayerObject;
import net.minecraft.server.network.ServerPlayerEntity;

public class OnGameMessage {
  public static boolean canSendMessage(ServerPlayerEntity player, String message) {
    PlayerObject playerObject = playerManager.get(player);
    return playerObject.isAuthenticated();
  }
}
