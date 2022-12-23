package dev.tarasa24.mc.httpauth;

import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class PlayerObject {
  private ServerPlayerEntity player;
  private boolean authenticated = false;
  private boolean isOnline = false;

  public PlayerObject(ServerPlayerEntity player) {
    this.player = player;
  }

  public void authenticate() {
    this.player.setInvulnerable(false);
    this.player.setInvisible(false);
    this.player.removeStatusEffect(StatusEffects.BLINDNESS);
    this.authenticated = true;

    this.player.sendMessage(
        Text.literal("You have been authenticated!")
            .setStyle(Style.EMPTY.withColor(net.minecraft.util.Formatting.GREEN)),
        true);
  }

  public void deauthenticate() {
    this.authenticated = false;
  }

  public boolean isAuthenticated() {
    return authenticated;
  }

  public ServerPlayerEntity getPlayer() {
    return player;
  }

  public void updatePlayer(ServerPlayerEntity player) {
    if (this.player != player) {
      this.player = player;
    }
  }

  public boolean isOnline() {
    return isOnline;
  }

  public void setOnline(boolean online) {
    isOnline = online;
  }
}