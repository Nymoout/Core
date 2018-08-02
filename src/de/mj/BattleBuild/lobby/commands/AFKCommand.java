package de.mj.BattleBuild.lobby.commands;

import de.mj.BattleBuild.lobby.listener.AFKListener;
import de.mj.BattleBuild.lobby.main.Lobby;
import de.mj.BattleBuild.lobby.utils.Var;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AFKCommand implements CommandExecutor {

    private final Lobby lobby;
    private AFKListener afkListener;
    private Var var;

    public AFKCommand(Lobby lobby) {
        this.lobby = lobby;
        lobby.setCommand(this, "afk");
        afkListener = lobby.getAfkListener();
        var = lobby.getVar();
    }

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
                p.sendMessage(var.getPrefix() + "§cDu bist bereits AFK!");
            }
        } else
            sender.sendMessage(var.getPrefix() + "§cDu musst ein Spieler sein um diesen Befehl ausführen zu können!");
        return false;
    }
}