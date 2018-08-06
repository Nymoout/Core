/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.lobby.listener;

import de.mj.BattleBuild.lobby.Lobby;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    private final Lobby lobby;

    public QuitListener(Lobby lobby) {
        this.lobby = lobby;
        lobby.setListener(this);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onQuit(PlayerQuitEvent quitEvent) {
        lobby.getMinionListener().rmMini(quitEvent.getPlayer());
        quitEvent.setQuitMessage(null);
    }
}
