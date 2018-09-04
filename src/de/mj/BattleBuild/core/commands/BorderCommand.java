package de.mj.BattleBuild.core.commands;

import de.mj.BattleBuild.core.CoreSpigot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BorderCommand implements CommandExecutor {

    private final CoreSpigot coreSpigot;

    public BorderCommand(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setCommand(this, "rand");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("citybuild.border"))
                player.openInventory(coreSpigot.getServerManager().getBorderListener().getBorderInv());
            else
                player.sendMessage(coreSpigot.getServerManager().getData().getNoperm());
        } else commandSender.sendMessage(coreSpigot.getServerManager().getData().getNoperm());
        return false;
    }
}
