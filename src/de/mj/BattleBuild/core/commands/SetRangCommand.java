package de.mj.BattleBuild.core.commands;

import de.mj.BattleBuild.core.Core;
import me.lucko.luckperms.api.Group;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetRangCommand implements CommandExecutor {

    private final Core core;

    public SetRangCommand(Core core) {
        this.core = core;
        core.setCommand(this, "setrank");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender.hasPermission("core.setrank")) {
            if (args.length == 2) {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (args[0].equalsIgnoreCase(all.getName())) {
                        for (Group group : core.getHookManager().getLuckPermsApi().getGroups()) {
                            if (args[1].equalsIgnoreCase(group.getName())) {
                                core.getHookManager().getLuckPermsApi().getUserManager().getUser(all.getUniqueId()).setPrimaryGroup(args[1]);
                                commandSender.sendMessage(core.getServerManager().getData().getPrefix() + "§aDu hast dem Spieler §e" + all.getName() + "§a die Gruppe §2" + args[1] + "§a erfolgreich gesetzt!");
                                return true;
                            }
                        }
                    }
                }
            } else return false;
        } else
            commandSender.sendMessage(core.getServerManager().getData().getNoperm());
        return false;
    }
}
