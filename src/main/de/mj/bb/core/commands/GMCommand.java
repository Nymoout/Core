package main.de.mj.bb.core.commands;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.Bukkit;
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
                        player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "Dein Spielmodus wurde zu §e" + player.getGameMode() + "§7 geändert!");
                    } else
                        player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "Bitte benutze §c/gm <0-3>");
                } else if (args.length == 2) {
                    if (isInteger(args[0])) {
                        int gm = Integer.parseInt(args[0]);
                        if (Bukkit.getPlayerExact(args[1]).isOnline()) {
                            Player target = Bukkit.getPlayerExact(args[1]);
                            if (gm == 0) target.setGameMode(GameMode.SURVIVAL);
                            if (gm == 1) target.setGameMode(GameMode.CREATIVE);
                            if (gm == 2) target.setGameMode(GameMode.ADVENTURE);
                            if (gm == 3) target.setGameMode(GameMode.SPECTATOR);
                            player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "Du hast den Spieler §e" + target.getName() + "§7 in den Spielmodus §e" + target.getGameMode() + "§7 gesetzt!");
                            target.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "Dein Spielmodus wurde zu §e" + target.getGameMode() + "§7 von §e" + player.getName() + "§7 geändert!");
                        } else
                            player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "Dieser Spieler ist nicht online");
                    } else
                        player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "Bitte benutze §c/gm <0-3> <name>");
                }
            } else commandSender.sendMessage(coreSpigot.getModuleManager().getData().getNoPerm());
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
