package main.de.mj.bb.core.commands;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvseeCommand implements CommandExecutor {

    private final CoreSpigot coreSpigot;

    public InvseeCommand(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setCommand(this, "invsee");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            if (commandSender.hasPermission("server.invsee")) {
                if (strings.length == 1) {
                    if (Bukkit.getPlayer(strings[0]) != null) {
                        Player player = Bukkit.getPlayer(strings[0]);
                        ((Player) commandSender).openInventory(player.getInventory());
                    } else
                        commandSender.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "Der Spieler §e" + strings[0] + " §7ist nicht online!");
                } else
                    commandSender.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "Bitte benutze /invsee <PlayerName>");
            } else commandSender.sendMessage(coreSpigot.getModuleManager().getData().getNoPerm());
        } else commandSender.sendMessage(coreSpigot.getModuleManager().getData().getOnlyPlayer());
        return false;
    }
}
