package de.mj.BattleBuild.lobby.commands;

import de.mj.BattleBuild.lobby.listener.AFKListener;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mj.BattleBuild.lobby.Variabeln.Var;

public class AFKCommand implements CommandExecutor {
    String prefix = new Var().getPrefix();
    AFKListener afkListener = new AFKListener();
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!afkListener.getAfkmover().contains(p)) {
                afkListener.setAfkmover(p);
                for (Player all : Bukkit.getOnlinePlayers()) {
                    all.sendMessage("§a" + p.getName() + " §eist nun AFK!");
                }
            } else {
                p.sendMessage(prefix +"§cDu bist bereits AFK!");
            }
        } else sender.sendMessage(prefix + "§cDu musst ein Spieler sein um diesen Befehl ausführen zu können!");
        return false;
    }
}