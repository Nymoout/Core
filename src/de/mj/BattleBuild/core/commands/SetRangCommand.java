package de.mj.BattleBuild.core.commands;

import de.mj.BattleBuild.core.CoreSpigot;
import me.lucko.luckperms.api.Group;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetRangCommand implements CommandExecutor {

    private final CoreSpigot coreSpigot;

    public SetRangCommand(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setCommand(this, "setrank");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender.hasPermission("coreSpigot.setrank")) {
            if (args.length == 2) {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (args[0].equalsIgnoreCase(all.getName())) {
                        for (Group group : coreSpigot.getHookManager().getLuckPermsApi().getGroups()) {
                            if (args[1].equalsIgnoreCase(group.getName())) {
                                coreSpigot.getHookManager().getLuckPermsApi().getUserManager().getUser(all.getUniqueId()).setPrimaryGroup(args[1]);
                                commandSender.sendMessage(coreSpigot.getServerManager().getData().getPrefix() + "§aDu hast dem Spieler §e" + all.getName() + "§a die Gruppe §2" + args[1] + "§a erfolgreich gesetzt!");
                                return true;
                            }
                        }
                    }
                }
            } else return false;
        } else
            commandSender.sendMessage(coreSpigot.getServerManager().getData().getNoperm());
        return false;
    }
}
