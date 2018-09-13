package main.de.mj.bb.core.utils;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;

public class AutomaticClearLag {

    private final CoreSpigot coreSpigot;

    public AutomaticClearLag(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
    }

    public void clearLagScheduler() {
        coreSpigot.getModuleManager().getSchedulerSaver().createScheduler(
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (coreSpigot.getModuleManager().getServerType().equals(ServerType.BAU_SERVER)) {
                            if (coreSpigot.getModuleManager().getTicksPerSecond().getTPS() < 19) {
                                coreSpigot.getServer().getLogger().warning("TPS unter 19 ... Ausgewählte Entities werden gelöscht!");
                                for (World world : Bukkit.getWorlds()) {
                                    for (Entity entity : Bukkit.getWorld(world.getName()).getEntities()) {
                                        if (coreSpigot.getModuleManager().getFileManager().getBooleanFormConfig("Animals")) {
                                            if (entity instanceof Animals) entity.remove();
                                        }
                                        if (coreSpigot.getModuleManager().getFileManager().getBooleanFormConfig("Monster")) {
                                            if (entity instanceof Monster) entity.remove();
                                        }
                                        if (coreSpigot.getModuleManager().getFileManager().getBooleanFormConfig("PrimedTNT")) {
                                            if (entity instanceof TNTPrimed) entity.remove();
                                        }
                                        if (coreSpigot.getModuleManager().getFileManager().getBooleanFormConfig("Arrows")) {
                                            if (entity instanceof Arrow) entity.remove();
                                        }
                                        if (coreSpigot.getModuleManager().getFileManager().getBooleanFormConfig("Items")) {
                                            if (entity instanceof Item) entity.remove();
                                        }
                                    }
                                }
                            }
                        }
                        if (coreSpigot.getModuleManager().getServerType().equals(ServerType.LOBBY)) {
                            if (coreSpigot.getModuleManager().getTicksPerSecond().getTPS() < 16) {
                                if (!coreSpigot.getModuleManager().getStopReloadRestartListener().isRestarting()) {
                                    coreSpigot.getModuleManager().getStopReloadRestartListener().setRestarting(true);
                                    coreSpigot.getModuleManager().getStopReloadRestartListener().shutdown();
                                }
                            }
                        }
                    }
                }.runTaskTimer(coreSpigot, 0L, 20L)
        );
    }
}
