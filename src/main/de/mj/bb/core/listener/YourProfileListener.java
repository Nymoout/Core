/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package main.de.mj.bb.core.listener;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

public class YourProfileListener implements Listener {

    private final CoreSpigot coreSpigot;

    public YourProfileListener(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setListener(this);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent interactEvent) {
        if (interactEvent.getItem() == null) return;
        if (interactEvent.getItem().getType() == null || interactEvent.getItem().getType().equals(Material.AIR)) return;
        Player player = interactEvent.getPlayer();
        if (interactEvent.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8\u00BB§9§lDein Profil§8\u00AB")) {
            player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, 1);
            player.performCommand("friendsgui");
        }
    }
}
