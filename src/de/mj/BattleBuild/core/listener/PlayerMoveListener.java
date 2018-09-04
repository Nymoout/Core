package de.mj.BattleBuild.core.listener;

import de.mj.BattleBuild.core.CoreSpigot;
import de.mj.BattleBuild.core.utils.Particle;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class PlayerMoveListener implements Listener {

    public final CoreSpigot coreSpigot;

    public PlayerMoveListener(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setListener(this);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent moveEvent) {
        Player player = moveEvent.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE) {
            if (coreSpigot.getServerManager().getSettingsListener().getWaterJump().contains(player)) {
                Location loc = player.getLocation();
                Block block = loc.getBlock();
                if (block.isLiquid()) {
                    player.setAllowFlight(true);
                    player.setVelocity(player.getLocation().getDirection().setY(4));
                    player.setAllowFlight(false);
                }
            }
            if (coreSpigot.getServerManager().getSettingsListener().getDoubleJump().contains(player)) {
                if (player.getLocation().add(0, -1, 0).getBlock().getType() != Material.AIR || player.getLocation().add(0, -1, 0).getBlock().getType() != Material.WATER) {
                    if (player.isOnGround()) {
                        player.setAllowFlight(true);
                        player.setFlying(false);
                    }
                }
            }
            if ((player.getLocation().getBlock().getType() == Material.IRON_PLATE)) {
                if (coreSpigot.getServerManager().getSettingsListener().getJumpPads().contains(player)) {
                    Vector v = player.getLocation().getDirection().multiply(2.0D).setY(1.0D);
                    player.setVelocity(v);
                    player.playSound(player.getLocation(), Sound.DIG_SAND, 1, 1);
                    player.playEffect(player.getLocation(), Effect.LAVA_POP, null);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDoubleJump(PlayerToggleFlightEvent flightEvent) {
        Player player = flightEvent.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE && coreSpigot.getServerManager().getSettingsListener().getDoubleJump().contains(player)) {
            flightEvent.setCancelled(true);
            player.setAllowFlight(false);
            player.setFlying(false);
            player.setVelocity(player.getLocation().getDirection().multiply(0.9D).add(new Vector(0, 0.9, 0)));
            player.playSound(player.getLocation(), Sound.LAVA_POP, 1, 1);
            Particle particle = new Particle(EnumParticle.FIREWORKS_SPARK, player.getLocation().add(0, 2.25, 0), true, 0.25f, 0.25f, 0.25f, 0, 100);
            particle.sendAll();
        }
    }
}
