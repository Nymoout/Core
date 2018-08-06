/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.lobby.listener;

import de.mj.BattleBuild.lobby.Lobby;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class WaterJumpListener implements Listener {

    private final Lobby lobby;

    public WaterJumpListener(Lobby lobby) {
        this.lobby = lobby;
        lobby.setListener(this);
    }

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
