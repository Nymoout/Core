/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.lobby.listener;

import de.mj.BattleBuild.lobby.Lobby;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class JumpPadListener implements Listener {

    private final Lobby lobby;

    public JumpPadListener(Lobby lobby) {
        this.lobby = lobby;
        lobby.setListener(this);
    }
    
    @EventHandler
    public void onMove(PlayerMoveEvent moveEvent) {
        Player player = moveEvent.getPlayer();
        if ((player.getLocation().getBlock().getType() == Material.IRON_PLATE)) {
            if (SettingsListener.jumppads.contains(player)) {
                Vector v = player.getLocation().getDirection().multiply(2.0D).setY(1.0D);
                player.setVelocity(v);
                player.playSound(player.getLocation(), Sound.DIG_SAND, 1, 1);
                player.playEffect(player.getLocation(), Effect.LAVA_POP, null);
            }
        }
    }
}
