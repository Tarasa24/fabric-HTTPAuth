package dev.tarasa24.mc.httpauth.events;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

import static dev.tarasa24.mc.httpauth.HttpAuth.playerManager;
import static dev.tarasa24.mc.httpauth.HttpAuth.config;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import dev.tarasa24.mc.httpauth.PlayerObject;
import net.minecraft.text.Text;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;

public class JoinListener {
  private static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

  public static Runnable validateLoginRunnable(ServerPlayNetworkHandler handler) {
    return () -> {
      PlayerObject playerObject = playerManager.get(handler.player);
      if (!playerObject.isAuthenticated()) {
        handler.disconnect(Text.of("You have been disconnected for not logging in."));
      }
    };
  }

  public static void listen(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) {
    var player = handler.player;

    PlayerObject playerObject = playerManager.get(player);
    playerObject.updatePlayer(player);
    playerObject.setOnline(true);
    var name = player.getEntityName();

    player.sendMessage(Text.of(String
        .format("Welcome %s! \nThis server is protected by HTTPAuth", name)));

    var url = config.authUrl;
    var clickable = Text.literal("HERE")
        .setStyle(Style.EMPTY
            .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url))
            .withColor(net.minecraft.util.Formatting.BLUE)
            .withBold(true)
            .withUnderline(true));

    player.sendMessage(
        Text.literal("Click ")
            .append(clickable)
            .append(Text.of(" to authenticate yourself."))
            .append(Text.of("\nYou have 60 seconds to do so before getting kicked.")));

    player.setInvulnerable(true);
    player.setInvisible(true);
    // set blidness
    player.setStatusEffect(
        new StatusEffectInstance(
            StatusEffects.BLINDNESS,
            Integer.MAX_VALUE,
            0,
            false,
            false),
        null);

    // Star 60 seconds timer to check if player is authenticated
    executor.schedule(validateLoginRunnable(handler), 60, java.util.concurrent.TimeUnit.SECONDS);
  }

}
