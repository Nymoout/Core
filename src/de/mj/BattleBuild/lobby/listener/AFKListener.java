package de.mj.BattleBuild.lobby.listener;

import de.mj.BattleBuild.lobby.Lobby;
import de.mj.BattleBuild.lobby.utils.SchedulerSaver;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;

public class AFKListener implements Listener {

    private static HashMap<Player, Location> locations = new HashMap<>();
    private static HashMap<Player, BukkitTask> runs = new HashMap<>();
    private static ArrayList<Player> afkmover = new ArrayList<>();

    private final Lobby lobby;

    public AFKListener(Lobby lobby) {
        this.lobby = lobby;
        lobby.setListener(this);
    }

    public void AFKTimer(Player p) {
        runs.put(p, new BukkitRunnable() {
            int counter = 300;
            @Override
            public void run() {
                if (p.getLocation().equals(locations.get(p))) {
                    if (counter == 0) {
                        if (!afkmover.contains(p)) {
                            Bukkit.getServer().broadcastMessage("§a" + p.getName() + " §eist nun AFKListener!");
                            afkmover.add(p);
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
        }.runTaskTimer(this.lobby, 0L, 20L));
    }

    public void AFKWorker() {
        for (Player all : Bukkit.getOnlinePlayers()){
            if (all.getLocation().equals(locations.get(all))) {
                if (!afkmover.contains(all)) {
                    AFKTimer(all);
                }
            }
        }
    }

    public void saveLocation() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (locations.containsKey(all)) {
                locations.remove(all);
            }
            locations.put(all, all.getLocation());
        }
    }
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (afkmover.contains(e.getPlayer())) {
            afkmover.remove(e.getPlayer());
            Bukkit.getServer().broadcastMessage("§a" + e.getPlayer().getName() + " §eist nicht mehr AFKListener!");
            runs.get(e.getPlayer()).cancel();
            runs.remove(e.getPlayer());
        }
    }

    public void LocationTimer() {
        lobby.getSchedulerSaver().createScheduler(
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
                }.runTaskTimer(this.lobby, 0L, 20L * 10)
        );
    }

    public static void setAfkmover (Player p) {
        afkmover.add(p);
    }

    public static ArrayList getAfkmover() {
        return afkmover;
    }

}
