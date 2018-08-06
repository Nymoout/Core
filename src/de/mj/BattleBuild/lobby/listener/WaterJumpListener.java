package de.mj.BattleBuild.lobby.listener;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class WaterJumpListener implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode().equals(GameMode.ADVENTURE) || p.getGameMode().equals(GameMode.SURVIVAL)) {
            if (SettingsListener.waterjump.contains(p)) {
                Location loc = p.getLocation();
                Block block = loc.getBlock();
                if (block.isLiquid()) {
                    p.setAllowFlight(true);
                    p.setVelocity(p.getLocation().getDirection().setY(4));
                    p.setAllowFlight(false);
                }
            }
        }
    }
}
