/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package main.de.mj.bb.core.commands;

import main.de.mj.bb.core.CoreSpigot;
import main.de.mj.bb.core.managers.ModuleManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetLocCommand implements CommandExecutor {

    private final CoreSpigot coreSpigot;

    public SetLocCommand(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setCommand(this, "set");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("coreSpigot.setlocs")) {
                if (strings.length == 1) {
                    setLocation(strings[0], player);
                }
            } else commandSender.sendMessage(coreSpigot.getModuleManager().getData().getNoPerm());
        } else commandSender.sendMessage(coreSpigot.getModuleManager().getData().getOnlyPlayer());
        return false;
    }

    private void setLocation(String path, Player player) {
        Location loc = player.getLocation();
        ModuleManager moduleManager = coreSpigot.getModuleManager();
        moduleManager.getSetLocations().getYamlConfiguration().set("bb." + path + ".world", loc.getWorld().getName());
        moduleManager.getSetLocations().getYamlConfiguration().set("bb." + path + ".world", loc.getWorld().getName());
        moduleManager.getSetLocations().getYamlConfiguration().set("bb." + path + ".x", loc.getX());
        moduleManager.getSetLocations().getYamlConfiguration().set("bb." + path + ".y", loc.getY());
        moduleManager.getSetLocations().getYamlConfiguration().set("bb." + path + ".z", loc.getZ());
        moduleManager.getSetLocations().getYamlConfiguration().set("bb." + path + ".yaw", loc.getYaw());
        moduleManager.getSetLocations().getYamlConfiguration().set("bb." + path + ".pitch", loc.getPitch());
        try {
            moduleManager.getSetLocations().getYamlConfiguration().save(moduleManager.getSetLocations().getFile());
            player.sendMessage(moduleManager.getData().getPrefix() + "§aLocation wurde erfolgreich gespeichert!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
