/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.lobby.commands;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import cloud.timo.TimoCloud.api.objects.ServerGroupObject;
import cloud.timo.TimoCloud.api.objects.ServerObject;
import de.mj.BattleBuild.lobby.Lobby;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class ServerCommand implements CommandExecutor {

    private final Lobby lobby;

    public ServerCommand(Lobby lobby) {
        this.lobby = lobby;
        lobby.setCommand(this, "server");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("lobby.server")) {
                int serversize = 0;
                ArrayList<String> servername = new ArrayList<>();
                for (ServerGroupObject serverGroupObjects : TimoCloudAPI.getUniversalAPI().getServerGroups()) {
                    for (ServerObject serverObject : serverGroupObjects.getServers()) {
                        serversize++;
                        servername.add(serverObject.getName());
                    }
                }
                Inventory inventory = Bukkit.createInventory(null, getInventorySize(serversize), "§9§lOnline Server");
                int inventorysize = serversize - 1;
                for (String name : servername) {
                    inventory.setItem(inventorysize, lobby.getItemCreator().CreateItemwithMaterial(Material.COMPASS, 0, 1, name, null));
                    inventorysize--;
                }
                player.openInventory(inventory);
            }
        }
        return false;
    }

    public int getInventorySize(int n) {
        int rows = n / 9;
        if (rows * 9 < n) {
            rows++;
        }
        return rows * 9;
    }
}
