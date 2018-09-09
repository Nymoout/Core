package main.de.mj.bb.core.commands;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadConfigCommand implements CommandExecutor {

    private final CoreSpigot coreSpigot;

    public ReloadConfigCommand(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setCommand(this, "bbreload");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender.hasPermission("config.reload")) {
            coreSpigot.getServerManager().getFileManager().reloadConfig();
            coreSpigot.getServerManager().reInit();
            commandSender.sendMessage(coreSpigot.getServerManager().getData().getPrefix() + "§aDu hast die Config erfolgreich neugeladen!");
        } else commandSender.sendMessage(coreSpigot.getServerManager().getData().getNoPerm());
        return false;
    }
}
