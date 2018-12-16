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
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class AFKCommand implements CommandExecutor {

    private final CoreSpigot coreSpigot;
    private Map<Player, BukkitTask> coolDown = new HashMap<>();
    private Map<Player, Integer> time = new HashMap<>();

    public AFKCommand(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setCommand(this, "afk");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!coolDown.containsKey(player)) {
                if (!coreSpigot.getModuleManager().getAfkListener().getAfkMover().contains(player)) {
                    setCoolDown(player);
                    coreSpigot.getModuleManager().getAfkListener().setAfkMover(player);
                    for (Player all : Bukkit.getOnlinePlayers())
                        all.sendMessage("§a" + player.getName() + " §eist nun AFK!");
                } else
                    player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "§cDu bist bereits AFK!");
            } else {
                player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "§7Du musst noch §c" + time.get(player) + " Sekunden §7warten, bevor du diesen Befehl erneut nutzen kannst!");
            }
        } else
            sender.sendMessage(coreSpigot.getModuleManager().getData().getOnlyPlayer());
        return false;
    }

    private void setCoolDown(Player player) {
        coolDown.put(player, new BukkitRunnable() {
            int timer = 60;

            @Override
            public void run() {
                if (timer == 60) {
                    time.put(player, 60);
                }
                if (timer == 0) {
                    cancel();
                    coolDown.remove(player);
                    time.remove(player);
                }
                timer--;
                time.replace(player, timer);
            }
        }.runTaskTimer(coreSpigot, 0L, 20L));
    }
}