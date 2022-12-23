package dev.tarasa24.mc.httpauth.events;

import static dev.tarasa24.mc.httpauth.HttpAuth.playerManager;

import dev.tarasa24.mc.httpauth.PlayerObject;
import net.minecraft.server.network.ServerPlayerEntity;

public class OnCommandExecution {
  public static boolean canSendCommand(ServerPlayerEntity player, String command) {
    PlayerObject playerObject = playerManager.get(player);
    return playerObject.isAuthenticated();
  }
}
