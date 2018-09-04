/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.core.utils;

import de.mj.BattleBuild.core.CoreSpigot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ActionbarTimer {

    private final CoreSpigot coreSpigot;

    public ActionbarTimer(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
    }

    public void setActionBar() {
        ServerManager lobby = this.coreSpigot.getServerManager();
        lobby.getSchedulerSaver().createScheduler(
                new BukkitRunnable() {

                    int msg = 2;

                    @Override
                    public void run() {
                        switch (msg) {
                            case 0:
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    lobby.getTitle().sendActionBar(all, "§c§lJetzt neu: §6§lBedWars");
                                }
                                msg = 2;
                                break;
                            case 1:
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    lobby.getTitle().sendActionBar(all, "§2§lBaue dir jetzt deinen eigenen Plot auf §b§lCityBuild");
                                }
                                break;
                            case 2:
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    lobby.getTitle().sendActionBar(all, "§6§lWillkommen auf §9§lBattleBuild!");
                                }
                                break;
                            case 3:
                                String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(Calendar.getInstance().getTime());
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    lobby.getTitle().sendActionBar(all, "§6§lDatum + Uhrzeit: §b" + timeStamp);
                                }
                            default:
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    lobby.getTitle().sendActionBar(all, "§6§lWillkommen auf §9§lBattleBuild!");
                                }
                        }
                        msg--;
                    }

                }.runTaskTimer(this.coreSpigot, 0L, 20L * 5)
        );
    }
}
