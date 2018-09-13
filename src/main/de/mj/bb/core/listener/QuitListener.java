/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package main.de.mj.bb.core.listener;

import main.de.mj.bb.core.CoreSpigot;
import main.de.mj.bb.core.utils.ServerType;
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
        coreSpigot.getModuleManager().getMinionListener().rmMini(quitEvent.getPlayer());
        if (coreSpigot.getModuleManager().getServerType().equals(ServerType.LOBBY)) {
            coreSpigot.getModuleManager().getAfkListener().getAfkMover().remove(quitEvent.getPlayer());
            if (coreSpigot.getModuleManager().getAfkListener().getRuns().containsKey(quitEvent.getPlayer())) {
                coreSpigot.getModuleManager().getAfkListener().getRuns().get(quitEvent.getPlayer()).cancel();
                coreSpigot.getModuleManager().getAfkListener().getRuns().remove(quitEvent.getPlayer());
            }
        }
        quitEvent.setQuitMessage(null);
    }

    @EventHandler
    public void onKick(PlayerKickEvent kickEvent) {
        coreSpigot.getModuleManager().getMinionListener().rmMini(kickEvent.getPlayer());
        if (coreSpigot.getModuleManager().getServerType().equals(ServerType.LOBBY)) {
            coreSpigot.getModuleManager().getAfkListener().getAfkMover().remove(kickEvent.getPlayer());
            if (coreSpigot.getModuleManager().getAfkListener().getRuns().containsKey(kickEvent.getPlayer())) {
                coreSpigot.getModuleManager().getAfkListener().getRuns().get(kickEvent.getPlayer()).cancel();
                coreSpigot.getModuleManager().getAfkListener().getRuns().remove(kickEvent.getPlayer());
            }
        }
        kickEvent.setLeaveMessage(null);
    }
}
