/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package main.de.mj.bb.core.listener;

import lombok.Getter;
import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
public class AFKListener implements Listener {

    private Map<Player, Location> locations = new HashMap<>();
    private Map<Player, BukkitTask> runs = new HashMap<>();
    private Set<Player> afkMover = new HashSet<>();

    private final CoreSpigot coreSpigot;

    public AFKListener(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setListener(this);
    }

    public void setAfkMover(Player player) {
        afkMover.add(player);
    }

    public void LocationTimer() {
        coreSpigot.getModuleManager().getSchedulerSaver().createScheduler(
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
                }.runTaskTimerAsynchronously(coreSpigot, 0L, 20L)
        );
    }

    private void saveLocation() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            locations.remove(all);
            locations.put(all, all.getLocation());
        }
    }

    private void AFKWorker() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (all.getLocation().equals(locations.get(all))) {
                if (!afkMover.contains(all)) {
                    AFKTimer(all);
                }
            }
        }
    }

    private void AFKTimer(Player player) {
        runs.put(player, new BukkitRunnable() {
            int counter = 300;

            @Override
            public void run() {
                if (player.getLocation().equals(locations.get(player))) {
                    if (counter == 0) {
                        if (Bukkit.getOnlinePlayers().contains(player)) {
                            if (!afkMover.contains(player)) {
                                Bukkit.getServer().broadcastMessage("§a" + player.getName() + " §eist nun AFK!");
                                afkMover.add(player);
                                cancel();
                            } else cancel();
                        } else cancel();
                    } else {
                        if (!locations.get(player).equals(player.getLocation())) cancel();
                    }
                    counter--;
                } else cancel();
            }
        }.runTaskTimer(this.coreSpigot, 0L, 20L));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMove(PlayerMoveEvent moveEvent) {
        if (afkMover.contains(moveEvent.getPlayer())) {
            afkMover.remove(moveEvent.getPlayer());
            Bukkit.getServer().broadcastMessage("§a" + moveEvent.getPlayer().getName() + " §eist nicht mehr AFK!");
            runs.get(moveEvent.getPlayer()).cancel();
            runs.remove(moveEvent.getPlayer());
        }
    }

}
