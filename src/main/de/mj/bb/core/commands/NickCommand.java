package main.de.mj.bb.core.commands;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NickCommand implements CommandExecutor {

    private final CoreSpigot coreSpigot;

    public NickCommand(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setCommand(this, "nick");
        coreSpigot.setCommand(this, "unnick");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("server.nick")) {
                if (command.getName().equals("nick")) {
                    if (coreSpigot.getNickManager().isDisguised(player)) {
                        player.sendMessage("ERROR");
                    } else {
                        coreSpigot.getNickManager().disguise(player);
                    }
                } else if (command.getName().equals("unnick")) {
                    if (coreSpigot.getNickManager().isDisguised(player)) {
                        coreSpigot.getNickManager().undisguise(player);
                    } else {
                        player.sendMessage("ERROR");
                    }
                }
            } else player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix());
        } else
            commandSender.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "Du musst ein Spieler sein um diesen Befehl nutzen zu können!");
        return false;
    }
}
