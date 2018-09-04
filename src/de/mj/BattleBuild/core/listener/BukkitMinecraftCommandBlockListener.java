/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.core.listener;

import de.mj.BattleBuild.core.CoreSpigot;
import de.mj.BattleBuild.core.utils.Data;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.jetbrains.annotations.NotNull;

public class BukkitMinecraftCommandBlockListener implements Listener {

    private final CoreSpigot coreSpigot;
    private Data data;

    public BukkitMinecraftCommandBlockListener(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setListener(this);
        data = coreSpigot.getServerManager().getData();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCMD(PlayerCommandPreprocessEvent preprocessEvent) {
        Player player = preprocessEvent.getPlayer();
        String[] cmd = preprocessEvent.getMessage().substring(1).split(" ");
        if (player.hasPermission("coreSpigot.commands")) {
            if ((cmd[0].startsWith("bukkit") || cmd[0].startsWith("minecraft")) && (!player.isOp())) {
                player.sendMessage(data.getNoperm());
                preprocessEvent.setCancelled(true);
                return;
            }
            if (((cmd[0].equalsIgnoreCase("?")) || (cmd[0].equalsIgnoreCase("help")))) {
                player.sendMessage(data.getPrefix() + "§7Server sagt: §c§lNEIN!");
                preprocessEvent.setCancelled(true);
            }
            if (((cmd[0].equalsIgnoreCase("ver")) || (cmd[0].equalsIgnoreCase("version"))
                    || (cmd[0].equalsIgnoreCase("about")))) {
                player.sendMessage(data.getNoperm());
                preprocessEvent.setCancelled(true);
            }
            if ((cmd[0].equalsIgnoreCase("aac"))) {
                player.sendMessage(data.getNoperm());
                preprocessEvent.setCancelled(true);
            }
            if (cmd[0].equalsIgnoreCase("tell")) {
                player.sendMessage(data.getNoperm());
                preprocessEvent.setCancelled(true);
            }
            if (cmd[0].equalsIgnoreCase("plugins") || cmd[0].equalsIgnoreCase("plugin") || cmd[0].equalsIgnoreCase("pl")) {
                player.sendMessage(data.getPrefix() + "§7Server sagt: §c§lNEIN!");
                preprocessEvent.setCancelled(true);
            }
        }
    }
}
