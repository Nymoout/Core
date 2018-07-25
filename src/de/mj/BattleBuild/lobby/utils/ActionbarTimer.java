package de.mj.BattleBuild.lobby.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class ActionbarTimer {

    public static HashMap<Player, Boolean> action = new HashMap<>();
    Plugin plugin;
    SchedulerSaver schedulerSaver = new SchedulerSaver();
    Title title = new Title(plugin);

    public ActionbarTimer () {}
    public ActionbarTimer(Plugin plugin) {
        this.plugin = plugin;
    }

    public void setActionBar() {
        schedulerSaver.createScheduler(
                new BukkitRunnable() {

                    int msg = 2;

                    @Override
                    public void run() {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            if (action.get(all) == false) {
                                title.sendActionBar(all, "§f§lDein Scoreboard wird geladen, bitte warte einen Moment...");
                            }
                        }
                        switch (msg) {
                            case 0:
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    if (action.get(all) == true) {
                                        title.sendActionBar(all, "§c§lJetzt neu: §6§lBedWars");
                                    }
                                }
                                msg = 2;
                                break;
                            case 1:
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    if (action.get(all) == true) {
                                        title.sendActionBar(all, "§2§lBaue dir jetzt deinen eigenen Plot auf §b§lCityBuild");
                                    }
                                }
                                break;
                            case 2:
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    if (action.get(all) == true) {
                                        title.sendActionBar(all, "§6§lWillkommen auf §9§lBattleBuild!");
                                    }
                                }
                                break;
                            default:
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    if (action.get(all) == true) {
                                        title.sendActionBar(all, "§6§lWillkommen auf §9§lBattleBuild!");
                                    }
                                }
                        }
                        msg--;
                    }

                }.runTaskTimer(this.plugin, 0L, 20L * 5)
        );
    }
}
