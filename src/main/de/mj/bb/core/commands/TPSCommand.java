package main.de.mj.bb.core.commands;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class TPSCommand implements CommandExecutor {

    private final CoreSpigot coreSpigot;

    public TPSCommand(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setCommand(this, "tps");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender.hasPermission("server.tps")) {
            DecimalFormat tpsFormat = new DecimalFormat("#.##");
            commandSender.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "§cTPS: " + tpsFormat.format(coreSpigot.getModuleManager().getTicksPerSecond().getTPS()));
        } else {
            commandSender.sendMessage(coreSpigot.getModuleManager().getData().getNoPerm());
        }
        return false;
    }
}
