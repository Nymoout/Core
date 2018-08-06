/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.lobby.utils;

import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public class SchedulerSaver {

    private ArrayList<BukkitTask> schedulerlist = new ArrayList<>();

    public SchedulerSaver() {
    }

    public void cancelSchedulers() {
        for (BukkitTask task : schedulerlist) {
            task.cancel();
        }
    }

    public void createScheduler(BukkitTask bukkitTask) {
        schedulerlist.add(bukkitTask);
    }
}
