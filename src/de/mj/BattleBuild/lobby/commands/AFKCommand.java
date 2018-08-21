/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.lobby.commands;

import de.mj.BattleBuild.lobby.Lobby;
import de.mj.BattleBuild.lobby.listener.AFKListener;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AFKCommand implements CommandExecutor {

    private final Lobby lobby;

    public AFKCommand(Lobby lobby) {
        this.lobby = lobby;
        lobby.setCommand(this, "afk");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!AFKListener.getAfkmover().contains(player)) {
                lobby.getServerManager().getAfkListener().setAfkmover(player);
                for (Player all : Bukkit.getOnlinePlayers()) {
                    all.sendMessage("§a" + player.getName() + " §eist nun AFK!");
                }
            } else {
                player.sendMessage(lobby.getServerManager().getData().getPrefix() + "§cDu bist bereits AFK!");
            }
        } else
            sender.sendMessage(lobby.getServerManager().getData().getPrefix() + "§cDu musst ein Spieler sein um diesen Befehl ausführen zu können!");
        return false;
    }
}