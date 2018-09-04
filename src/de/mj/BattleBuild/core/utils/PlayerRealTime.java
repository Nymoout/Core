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
import org.jetbrains.annotations.Contract;

import java.util.Calendar;

public class PlayerRealTime {

    private final CoreSpigot coreSpigot;

    public PlayerRealTime(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
    }

    @Contract(pure = true)
    private static double secToTicks(long sec) {
        return Math.floor(0.2777778F * (float) sec);
    }

    public void setPlayerRealTime() {
        coreSpigot.getServerManager().getSchedulerSaver().createScheduler(
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
                            if (coreSpigot.getServerManager().getSettingsListener().getSrealtime().contains(all)) {
                                all.setPlayerTime(ticks, false);
                            } else if (coreSpigot.getServerManager().getSettingsListener().getSday().contains(all)) {
                                all.setPlayerTime(6000, false);
                            } else {
                                all.setPlayerTime(18000, false);
                            }
                        }
                    }
                }.runTaskTimerAsynchronously(coreSpigot, 0L, 20L)
        );
    }
}
