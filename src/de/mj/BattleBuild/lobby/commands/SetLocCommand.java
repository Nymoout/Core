package de.mj.BattleBuild.lobby.commands;

import de.mj.BattleBuild.lobby.Lobby;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class SetLocCommand implements CommandExecutor {

    private final Lobby lobby;

    public SetLocCommand (Lobby lobby) {
        this.lobby = lobby;
        lobby.setCommand(this, "set");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("lobby.setlocs")) {
                if (strings.length == 1) {
                    setLocation(strings[0], player);
                }
            }
        }
        return false;
    }

    public void setLocation(String path, Player player) {
        Location loc = player.getLocation();
        lobby.getSetLocations().getYamlConfiguration().set("bb." + path + ".world", loc.getWorld().getName());
        lobby.getSetLocations().getYamlConfiguration().set("bb." + path + ".world", loc.getWorld().getName());
        lobby.getSetLocations().getYamlConfiguration().set("bb." + path + ".x", loc.getX());
        lobby.getSetLocations().getYamlConfiguration().set("bb." + path + ".y", loc.getY());
        lobby.getSetLocations().getYamlConfiguration().set("bb." + path + ".z", loc.getZ());
        lobby.getSetLocations().getYamlConfiguration().set("bb." + path + ".yaw", loc.getYaw());
        lobby.getSetLocations().getYamlConfiguration().set("bb." + path + ".pitch", loc.getPitch());
        try {
            lobby.getSetLocations().getYamlConfiguration().save(lobby.getSetLocations().getFile());
            player.sendMessage(lobby.getData().getPrefix() + "§aLocation wurde erfolgreich gespeichert!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
