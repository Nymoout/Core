/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package main.de.mj.bb.core.listener;

import main.de.mj.bb.core.CoreSpigot;
import main.de.mj.bb.core.utils.ServerType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class QuitListener implements Listener {

    private final CoreSpigot coreSpigot;

    public QuitListener(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setListener(this);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onQuit(PlayerQuitEvent quitEvent) {
        clearPlayer(quitEvent.getPlayer());
        quitEvent.setQuitMessage(null);
    }

    @EventHandler
    public void onKick(PlayerKickEvent kickEvent) {
        clearPlayer(kickEvent.getPlayer());
        kickEvent.setLeaveMessage(null);
    }

    private void clearPlayer(Player player) {
        if (coreSpigot.getModuleManager().getServerType().equals(ServerType.LOBBY)) {
            coreSpigot.getModuleManager().getMinionListener().rmMini(player);
            coreSpigot.getModuleManager().getAfkListener().getAfkMover().remove(player);
            if (coreSpigot.getModuleManager().getAfkListener().getRuns().containsKey(player)) {
                coreSpigot.getModuleManager().getAfkListener().getRuns().get(player).cancel();
                coreSpigot.getModuleManager().getAfkListener().getRuns().remove(player);
            }
            coreSpigot.getModuleManager().getFlyListener().getFly().remove(player);
        }
    }
}
