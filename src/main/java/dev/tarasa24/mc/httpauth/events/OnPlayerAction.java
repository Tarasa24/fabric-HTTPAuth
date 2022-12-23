package dev.tarasa24.mc.httpauth.events;

import static dev.tarasa24.mc.httpauth.HttpAuth.playerManager;

import dev.tarasa24.mc.httpauth.PlayerObject;
import net.minecraft.server.network.ServerPlayerEntity;

public class OnPlayerAction {
  public static boolean canInteract(ServerPlayerEntity player) {
    PlayerObject playerObject = playerManager.get(player);
    return playerObject.isAuthenticated();
  }
}
