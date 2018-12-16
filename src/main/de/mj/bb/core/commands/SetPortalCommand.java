package main.de.mj.bb.core.commands;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetPortalCommand implements CommandExecutor {

    private final CoreSpigot coreSpigot;

    public SetPortalCommand(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setCommand(this, "setportal");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("set.portal")) {
                if (args.length == 2) {
                    if (args[1].equals("1")) {
                        coreSpigot.getModuleManager().getPortalManager().setPortal(player.getLocation(), 1, args[0]);
                        player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix()+ "Portal-Edge 1 wurde gesetzt!");
                    } else if (args[1].equals("2")) {
                        coreSpigot.getModuleManager().getPortalManager().setPortal(player.getLocation(), 2, args[0]);
                        player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "Portal-Edge 2 wurde gesetzt!");
                    } else {
                        player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "Bitte nutze /setportal name <1|2>");
                    }
                } else {
                    player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "Bitte nutze /setportal name <1|2>");
                }
            } else player.sendMessage(coreSpigot.getModuleManager().getData().getNoPerm());
        } else {
            coreSpigot.getServer().getLogger().warning("Du musst ein Spieler sein um diesen Befehl nutzen zu können!");
        }
        return false;
    }
}
