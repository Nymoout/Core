/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.core.commands;

import de.mj.BattleBuild.core.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    private final Core core;

    public SpawnCommand(Core core) {
        this.core = core;
        core.setCommand(this, "spawn");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            player.teleport(core.getServerManager().getLocationsUtil().getSpawn());
            player.sendMessage(core.getServerManager().getData().getPrefix() + "Du wurdest zum Spawn teleportiert!");
        }
        return false;
    }
}
