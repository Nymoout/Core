package main.de.mj.bb.core.commands;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlyCommand implements CommandExecutor {

    private CoreSpigot coreSpigot;

    public FlyCommand(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setCommand(this, "fly");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("server.fly")) {
                if (coreSpigot.getModuleManager().getFlyListener().getFly().contains(player)) {
                    player.setAllowFlight(false);
                    coreSpigot.getModuleManager().getFlyListener().getFly().remove(player);
                    player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "§7Du hast Fly §cdeaktiviert!");
                } else {
                    player.setAllowFlight(true);
                    coreSpigot.getModuleManager().getFlyListener().getFly().add(player);
                    player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "§7Du hast Fly §aaktiviert!");
                }
            } else {
                commandSender.sendMessage(coreSpigot.getModuleManager().getData().getNoPerm());
            }
        } else {
            commandSender.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "§cDu musst ein Spieler sein um diesen Befehl nutzen zu können!");
        }
        return false;
    }
}
