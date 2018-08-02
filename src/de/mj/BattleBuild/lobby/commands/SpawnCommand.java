package de.mj.BattleBuild.lobby.commands;

import de.mj.BattleBuild.lobby.Lobby;
import de.mj.BattleBuild.lobby.utils.LocationsUtil;
import de.mj.BattleBuild.lobby.utils.Data;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    private final Lobby lobby;

    public SpawnCommand(Lobby lobby) {
        this.lobby = lobby;
        lobby.setCommand(this, "spawn");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            p.teleport(lobby.getLocationsUtil().getSpawn());
            p.sendMessage(lobby.getData().getPrefix() + "Du wurdest zum Spawn teleportiert!");
        }
        return false;
    }
}
