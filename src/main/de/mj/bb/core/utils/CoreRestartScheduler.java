package main.de.mj.bb.core.utils;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import cloud.timo.TimoCloud.api.messages.objects.PluginMessage;
import cloud.timo.TimoCloud.api.objects.PlayerObject;
import cloud.timo.TimoCloud.api.objects.ServerGroupObject;
import cloud.timo.TimoCloud.api.objects.ServerObject;
import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CoreRestartScheduler {

    private final CoreSpigot coreSpigot;

    public CoreRestartScheduler(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
    }

    public void coreRestart() {
        new BukkitRunnable() {
            int timer = 20;

            @Override
            public void run() {
                for (ServerGroupObject serverGroupObject : TimoCloudAPI.getUniversalAPI().getServerGroups()) {
                    for (ServerObject serverObject : serverGroupObject.getServers()) {
                        serverObject.sendPluginMessage(new PluginMessage("CoreRestart").set("message", ""));
                        for (PlayerObject playerObject : serverObject.getOnlinePlayers()) {
                            Player player = (Player) playerObject;
                            coreSpigot.getModuleManager().getTitle().sendTitle(player, 1, 2, 1, "§cKritischer Fehler", "Code wird analysiert...");
                            player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                            player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                            player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                            player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                            player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                            player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                            player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                            player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                        }
                    }
                }
                if (timer == 20) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        coreSpigot.getModuleManager().getTitle().sendTitle(player, 1, 2, 1, "§cKritischer Fehler", "Code wird analysiert...");
                        player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                        player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                        player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                        player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                        player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                        player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                        player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                        player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                    }
                } else if (timer == 17) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        coreSpigot.getModuleManager().getTitle().sendTitle(player, 1, 2, 1, "§cCode wird neugeordnet", "prüfe ob Fehler behoben wurde...");
                        player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                        player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                        player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                        player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                        player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                        player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                        player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                        player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                    }
                } else if (timer == 15) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        coreSpigot.getModuleManager().getTitle().sendTitle(player, 1, 2, 1, "§cKeine weiteren Fehler gefunden", "Code wird compiled...");
                        player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                        player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                        player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                        player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                        player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                        player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                        player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                        player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                    }
                } else if (timer == 13) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        coreSpigot.getModuleManager().getTitle().sendTitle(player, 1, 2, 1, "§cPlane Systemneustart", "");
                        player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                        player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                        player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                        player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                        player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                        player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                        player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                        player.sendMessage("§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka§b§ka");
                    }
                } else if (timer <= 10 && timer > 0) {
                    String prefix = coreSpigot.getModuleManager().getData().getPrefix();
                    if (timer == 10) Bukkit.broadcastMessage(prefix + "§7Neustart in §6§l" + timer + " Sekunden§7.");
                    if (timer == 5) Bukkit.broadcastMessage(prefix + "§7Neustart in §6§l" + timer + " Sekunden§7.");
                    if (timer <= 3 && timer != 1)
                        Bukkit.broadcastMessage(prefix + "§7Neustart in §6§l" + timer + " Sekunden§7.");
                    if (timer == 1) Bukkit.broadcastMessage(prefix + "§7Neustart in §6§l" + timer + " Sekunde§7.");
                } else if (timer == 0) {
                    coreSpigot.getModuleManager().getStopReloadRestartListener().setRestarting(true);
                    for (ServerGroupObject serverGroupObject : TimoCloudAPI.getUniversalAPI().getServerGroups()) {
                        for (ServerObject serverObject : serverGroupObject.getServers()) {
                            serverObject.stop();
                        }
                    }
                    cancel();
                }
                timer--;
            }
        }.runTaskTimer(coreSpigot, 0L, 20L);
    }
}
