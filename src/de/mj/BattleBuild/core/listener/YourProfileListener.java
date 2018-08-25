/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.core.listener;

import de.mj.BattleBuild.core.Core;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class YourProfileListener implements Listener {

    private final Core core;

    public YourProfileListener(Core core) {
        this.core = core;
        core.setListener(this);
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
