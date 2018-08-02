package de.mj.BattleBuild.lobby.utils;

import de.mj.BattleBuild.lobby.main.Lobby;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class ActionbarTimer {

    public static HashMap<Player, Boolean> action = new HashMap<>();
    private final Lobby lobby;
    private Title title;

    public ActionbarTimer(Lobby lobby) {
        this.lobby = lobby;
        title = lobby.getTitle();
    }

    public void setActionBar() {
        lobby.getSchedulerSaver().createScheduler(
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

                }.runTaskTimer(lobby, 0L, 20L * 5)
        );
    }
}
