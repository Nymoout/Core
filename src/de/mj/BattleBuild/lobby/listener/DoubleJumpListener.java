/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.lobby.listener;

import de.mj.BattleBuild.lobby.Lobby;
import de.mj.BattleBuild.lobby.utils.Particle;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

public class DoubleJumpListener implements Listener {

    private final Lobby lobby;

    public DoubleJumpListener(Lobby lobby) {
        this.lobby = lobby;
        lobby.setListener(this);
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onMove(PlayerMoveEvent moveEvent) {
        Player player = moveEvent.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE && SettingsListener.doppelsprung.contains(player)) {
            if (player.getLocation().add(0, -1, 0).getBlock().getType() != Material.AIR || player.getLocation().add(0, -1, 0).getBlock().getType() != Material.WATER) {
                if (player.isOnGround()) {
                    player.setAllowFlight(true);
                    player.setFlying(false);
                }
            }
        }
    }

    @EventHandler
    public void onDoubleJump(PlayerToggleFlightEvent flightEvent) {
        Player player = flightEvent.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE && SettingsListener.doppelsprung.contains(player)) {
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
