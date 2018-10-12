package main.de.mj.bb.core.commands;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class StatsHologramCommand implements CommandExecutor {

    private final CoreSpigot coreSpigot;

    public StatsHologramCommand(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setCommand(this, "setStats");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("server.setStats")) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("BedWars")) {
                        ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
                        armorStand.setGravity(false);
                        armorStand.setVisible(false);
                        armorStand.setCustomName("BedWars");
                        armorStand.setCustomNameVisible(true);
                    }
                } else
                    player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "Bitte nutze /setstats <game>");
            } else player.sendMessage(coreSpigot.getModuleManager().getData().getNoPerm());
        } else commandSender.sendMessage(coreSpigot.getModuleManager().getData().getOnlyPlayer());
        return false;
    }
}
