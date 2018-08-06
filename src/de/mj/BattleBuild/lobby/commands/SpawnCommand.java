package de.mj.BattleBuild.lobby.commands;

import de.mj.BattleBuild.lobby.Variabeln.Var;
import de.mj.BattleBuild.lobby.utils.LocationsUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    String prefix = new Var().getPrefix();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            p.teleport(new LocationsUtil().getSpawn());
            p.sendMessage(prefix + "Du wurdest zum Spawn teleportiert!");
        }
        return false;
    }
}
