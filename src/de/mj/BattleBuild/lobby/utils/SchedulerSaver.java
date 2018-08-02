package de.mj.BattleBuild.lobby.utils;

import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public class SchedulerSaver {

    private ArrayList<BukkitTask> schedulerlist = new ArrayList<>();

    public SchedulerSaver() {}

    public void cancelSchedulers() {
        for (BukkitTask task : schedulerlist) {
            task.cancel();
        }
    }

    public void createScheduler(BukkitTask bukkitTask) {
        schedulerlist.add(bukkitTask);
    }
}
