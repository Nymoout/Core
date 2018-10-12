package main.de.mj.bb.core.commands;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GMCommand implements CommandExecutor {

    private final CoreSpigot coreSpigot;

    public GMCommand(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setCommand(this, "gm");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("server.gamemode")) {
                if (args.length == 1) {
                    if (isInteger(args[0])) {
                        int gm = Integer.parseInt(args[0]);
                        if (gm == 0) player.setGameMode(GameMode.SURVIVAL);
                        if (gm == 1) player.setGameMode(GameMode.CREATIVE);
                        if (gm == 2) player.setGameMode(GameMode.ADVENTURE);
                        if (gm == 3) player.setGameMode(GameMode.SPECTATOR);
                    } else
                        player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "Bitte benutze /gm <0-3>");
                } else
                    player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "Bitte benutze /gm <0-3>");
            }
        } else
            commandSender.sendMessage(coreSpigot.getModuleManager().getData().getOnlyPlayer());
        return false;
    }

    private boolean isInteger(Object object) {
        if (object instanceof Integer) {
            return true;
        } else {
            String string = object.toString();
            try {
                Integer.parseInt(string);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
}
