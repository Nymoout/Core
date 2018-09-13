/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package main.de.mj.bb.core.commands;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AFKCommand implements CommandExecutor {

    private final CoreSpigot coreSpigot;

    public AFKCommand(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setCommand(this, "afk");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!coreSpigot.getModuleManager().getAfkListener().getAfkMover().contains(player)) {
                coreSpigot.getModuleManager().getAfkListener().setAfkMover(player);
                for (Player all : Bukkit.getOnlinePlayers())
                    all.sendMessage("§a" + player.getName() + " §eist nun AFK!");
            } else
                player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "§cDu bist bereits AFK!");
        } else
            sender.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "§cDu musst ein Spieler sein um diesen Befehl ausführen zu können!");
        return false;
    }
}