package main.de.mj.bb.core.commands;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetSpeedCommand implements CommandExecutor {

    private final CoreSpigot coreSpigot;

    public SetSpeedCommand(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setCommand(this, "setSpeed");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender.hasPermission("anticheat")) {
            if (strings.length == 2) {
                if (strings[0].equalsIgnoreCase("speed")) {
                    if (isDouble(strings[1])) {
                        coreSpigot.getModuleManager().getEvents().setSpeed(Double.parseDouble(strings[1]));
                        commandSender.sendMessage(strings[1]);
                    }
                } else if (strings[0].equalsIgnoreCase("sprint")) {
                    if (isDouble(strings[1])) {
                        coreSpigot.getModuleManager().getEvents().setSprint(Double.parseDouble(strings[1]));
                        commandSender.sendMessage(strings[1]);
                    }
                } else if (strings[0].equalsIgnoreCase("speed2")) {
                    if (isDouble(strings[1])) {
                        coreSpigot.getModuleManager().getEvents().setSpeed2(Double.parseDouble(strings[1]));
                        commandSender.sendMessage(strings[1]);
                    }
                } else if (strings[0].equalsIgnoreCase("sprint2")) {
                    if (isDouble(strings[1])) {
                        coreSpigot.getModuleManager().getEvents().setSprint2(Double.parseDouble(strings[1]));
                        commandSender.sendMessage(strings[1]);
                    }
                }
            }
        }
        return false;
    }

    private boolean isDouble(Object object) {
        if (object instanceof Double) {
            return true;
        } else {
            String string = object.toString();
            try {
                Double.parseDouble(string);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
}
