package main.de.mj.bb.core.listener;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
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
            if (coreSpigot.getModuleManager().getSettingsListener().getWaterJump().contains(player)) {
                Location loc = player.getLocation();
                Block block = loc.getBlock();
                if (block.isLiquid()) {
                    player.setAllowFlight(true);
                    player.setVelocity(player.getLocation().getDirection().setY(4));
                    player.setAllowFlight(false);
                }
            }
            if (coreSpigot.getModuleManager().getSettingsListener().getDoubleJump().contains(player)) {
                if (player.getLocation().add(0, -1, 0).getBlock().getType() != Material.AIR || player.getLocation().add(0, -1, 0).getBlock().getType() != Material.WATER) {
                    if (player.isOnGround()) {
                        player.setAllowFlight(true);
                        player.setFlying(false);
                    }
                }
            }
            if ((player.getLocation().getBlock().getType() == Material.IRON_PLATE)) {
                if (coreSpigot.getModuleManager().getSettingsListener().getJumpPads().contains(player)) {
                    Vector v = player.getLocation().getDirection().multiply(2.0D).setY(1.0D);
                    player.setVelocity(v);
                    player.playSound(player.getLocation(), Sound.DIG_SAND, 1, 1);
                    player.playEffect(player.getLocation(), Effect.LAVA_POP, null);
                }
            }
        }
        if (player.getGameMode().equals(GameMode.CREATIVE)) {
            if (coreSpigot.getModuleManager().getSettingsListener().getWaterJump().contains(player)) {
                Location loc = player.getLocation();
                Block block = loc.getBlock();
                if (block.isLiquid()) {
                    player.setVelocity(player.getLocation().getDirection().setY(4));
                }
            }
            if ((player.getLocation().getBlock().getType() == Material.IRON_PLATE)) {
                if (coreSpigot.getModuleManager().getSettingsListener().getJumpPads().contains(player)) {
                    Vector v = player.getLocation().getDirection().multiply(2.0D).setY(1.0D);
                    player.setVelocity(v);
                    player.playSound(player.getLocation(), Sound.DIG_SAND, 1, 1);
                    player.playEffect(player.getLocation(), Effect.LAVA_POP, null);
                }
            }
        }
    }

}
