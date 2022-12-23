package dev.tarasa24.mc.httpauth.managers;

import java.util.HashMap;

import dev.tarasa24.mc.httpauth.PlayerObject;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerManager extends HashMap<String, PlayerObject> {
  public PlayerObject get(ServerPlayerEntity player) {
    String username = player.getEntityName();
    if (containsKey(username))
      return super.get(username);
    PlayerObject playerObj = new PlayerObject(player);
    put(username, playerObj);
    return playerObj;
  }
}
