package main.de.mj.bb.core.commands;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AutoBanCommand implements CommandExecutor {

    private final CoreSpigot coreSpigot;

    public AutoBanCommand(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setCommand(this, "autoBan");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender.hasPermission("server.autoBan")) {

        } else commandSender.sendMessage(coreSpigot.getModuleManager().getData().getNoPerm());
        return false;
    }
}
