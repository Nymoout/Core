/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.core.listener;

import de.mj.BattleBuild.core.CoreSpigot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
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
        coreSpigot.getServerManager().getMinionListener().rmMini(quitEvent.getPlayer());
        quitEvent.setQuitMessage(null);
    }
}
