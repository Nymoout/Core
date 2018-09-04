/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.core.listener;

import de.mj.BattleBuild.core.CoreSpigot;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;

public class AFKListener implements Listener {

    private static HashMap<Player, Location> locations = new HashMap<>();
    private static HashMap<Player, BukkitTask> runs = new HashMap<>();
    private static HashSet<Player> afkmover = new HashSet<>();

    private final CoreSpigot coreSpigot;

    public AFKListener(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setListener(this);
    }

    @Contract(pure = true)
    public static HashSet<Player> getAfkmover() {
        return afkmover;
    }

    public void setAfkmover(Player player) {
        afkmover.add(player);
    }

    public void AFKTimer(Player player) {
        runs.put(player, new BukkitRunnable() {
            int counter = 5;

            @Override
            public void run() {
                if (player.getLocation().equals(locations.get(player))) {
                    if (counter == 0) {
                        if (!afkmover.contains(player)) {
                            Bukkit.getServer().broadcastMessage("§a" + player.getName() + " §eist nun AFK!");
                            afkmover.add(player);
                            cancel();
                        } else {
                            cancel();
                        }
                    }
                    counter--;
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(this.coreSpigot, 0L, 20L * 60));
    }

    public void AFKWorker() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (all.getLocation().equals(locations.get(all))) {
                if (!afkmover.contains(all)) {
                    AFKTimer(all);
                }
            }
        }
    }

    public void saveLocation() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            locations.remove(all);
            locations.put(all, all.getLocation());
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent moveEvent) {
        if (afkmover.contains(moveEvent.getPlayer())) {
            afkmover.remove(moveEvent.getPlayer());
            Bukkit.getServer().broadcastMessage("§a" + moveEvent.getPlayer().getName() + " §eist nicht mehr AFK!");
            runs.get(moveEvent.getPlayer()).cancel();
            runs.remove(moveEvent.getPlayer());
        }
    }

    public void LocationTimer() {
        coreSpigot.getServerManager().getSchedulerSaver().createScheduler(
                new BukkitRunnable() {
                    int counter = 2;

                    @Override
                    public void run() {
                        if (counter == 1) {
                            saveLocation();
                        } else if (counter == 0) {
                            AFKWorker();
                            counter = 2;
                        }
                        counter--;
                    }
                }.runTaskTimer(this.coreSpigot, 0L, 20L * 10)
        );
    }

}
