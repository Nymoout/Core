package de.mj.BattleBuild.lobby.commands;

import de.mj.BattleBuild.lobby.main.Lobby;
import de.mj.BattleBuild.lobby.utils.LocationsUtil;
import de.mj.BattleBuild.lobby.utils.Var;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    private final Lobby lobby;
    private Var var;

    public SpawnCommand(Lobby lobby) {
        this.lobby = lobby;
        lobby.setCommand(this, "spawn");
        var = lobby.getVar();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            p.teleport(new LocationsUtil().getSpawn());
            p.sendMessage(var.getPrefix() + "Du wurdest zum Spawn teleportiert!");
        }
        return false;
    }
}
