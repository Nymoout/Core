package main.de.mj.bb.core.commands;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyWalkSpeedCommand implements CommandExecutor {

    private final CoreSpigot coreSpigot;

    public FlyWalkSpeedCommand(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setCommand(this, "flyspeed");
        coreSpigot.setCommand(this, "walkspeed");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("server.walkfly")) {
                if (args.length == 1) {
                    if (isInteger(args[0])) {
                        if (command.getName().equals("flyspeed")) {
                            float speed = Integer.parseInt(args[0]);
                            speed = speed / 10;
                            player.setFlySpeed(speed);
                            player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "Dein Flyspeed wurde auf " + args[0] + " gesetzt!");
                        } else if (command.getName().equals("walkspeed")) {
                            float speed = Integer.parseInt(args[0]);
                            speed = speed / 10;
                            player.setWalkSpeed(speed);
                            player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "Dein Walkspeed wurde auf " + args[0] + " gesetzt!");
                        }
                    } else
                        player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "Bitte benutze /<fly/walk>speed 1-10");
                } else
                    player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "Bitte benutze /<fly/walk>speed 1-10");
            } else player.sendMessage(coreSpigot.getModuleManager().getData().getNoPerm());
        } else commandSender.sendMessage(coreSpigot.getModuleManager().getData().getOnlyPlayer());
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
