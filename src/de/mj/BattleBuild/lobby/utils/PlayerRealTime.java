/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.lobby.utils;

import de.mj.BattleBuild.lobby.Lobby;
import de.mj.BattleBuild.lobby.listener.SettingsListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Calendar;

public class PlayerRealTime {

    private final Lobby lobby;

    public PlayerRealTime(Lobby lobby) {
        this.lobby = lobby;
    }

    private static double secToTicks(long sec) {
        return Math.floor(0.2777778F * (float) sec);
    }

    public void setPlayerRealTime() {
        lobby.getServerManager().getSchedulerSaver().createScheduler(
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            Calendar calendar = Calendar.getInstance();
                            int h = calendar.get(11);
                            h -= 6;
                            if (h < 0) {
                                h += 24;
                            }
                            int m = calendar.get(12);
                            int s = calendar.get(13);
                            long seconds = h * 60 * 60 + m * 60 + s;
                            long ticks = (long) secToTicks(seconds);
                            if (SettingsListener.srealtime.contains(all)) {
                                all.setPlayerTime(ticks, false);
                            } else if (SettingsListener.sday.contains(all)) {
                                all.setPlayerTime(6000, false);
                            } else {
                                all.setPlayerTime(18000, false);
                            }
                        }
                    }
                }.runTaskTimerAsynchronously(lobby, 0L, 20L)
        );
    }
}
