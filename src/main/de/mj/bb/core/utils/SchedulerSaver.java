/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package main.de.mj.bb.core.utils;

import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public class SchedulerSaver {

    private ArrayList<BukkitTask> schedulerList = new ArrayList<>();

    public SchedulerSaver() {
    }

    public void cancelSchedulers() {
        for (BukkitTask task : schedulerList) {
            task.cancel();
        }
    }

    public void createScheduler(BukkitTask bukkitTask) {
        schedulerList.add(bukkitTask);
    }
}
