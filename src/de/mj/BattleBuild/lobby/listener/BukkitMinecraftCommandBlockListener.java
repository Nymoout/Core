/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.lobby.listener;

import de.mj.BattleBuild.lobby.Lobby;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class BukkitMinecraftCommandBlockListener implements Listener {

    private final Lobby lobby;

    public BukkitMinecraftCommandBlockListener(Lobby lobby) {
        this.lobby = lobby;
        lobby.setListener(this);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCMD(PlayerCommandPreprocessEvent preprocessEvent) {
        Player player = preprocessEvent.getPlayer();
        String[] cmd = preprocessEvent.getMessage().substring(1).split(" ");
        if (player.hasPermission("lobby.commands")) {
            if ((cmd[0].startsWith("bukkit") || cmd[0].startsWith("minecraft")) && (!player.isOp())) {
                player.sendMessage(lobby.getData().getNoperm());
                preprocessEvent.setCancelled(true);
                return;
            }
            if (((cmd[0].equalsIgnoreCase("?")) || (cmd[0].equalsIgnoreCase("help")))) {
                player.sendMessage(lobby.getData().getPrefix() + "§7Server sagt: §c§lNEIN!");
                preprocessEvent.setCancelled(true);
            }
            if (((cmd[0].equalsIgnoreCase("ver")) || (cmd[0].equalsIgnoreCase("version"))
                    || (cmd[0].equalsIgnoreCase("about")))) {
                player.sendMessage(lobby.getData().getNoperm());
                preprocessEvent.setCancelled(true);
            }
            if ((cmd[0].equalsIgnoreCase("aac"))) {
                player.sendMessage(lobby.getData().getNoperm());
                preprocessEvent.setCancelled(true);
            }
            if (cmd[0].equalsIgnoreCase("tell")) {
                player.sendMessage(lobby.getData().getNoperm());
                preprocessEvent.setCancelled(true);
            }
            if (cmd[0].equalsIgnoreCase("plugins") || cmd[0].equalsIgnoreCase("plugin") || cmd[0].equalsIgnoreCase("pl")) {
                player.sendMessage(lobby.getData().getPrefix() + "§7Server sagt: §c§lNEIN!");
                preprocessEvent.setCancelled(true);
            }
        }
    }
}
