package main.de.mj.bb.core.commands;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class GoToServerCommand implements CommandExecutor {

    private final CoreSpigot coreSpigot;

    public GoToServerCommand(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setCommand(this, "gotoserver");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            if (args.length == 1) {
                Player player = (Player) commandSender;
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                    dataOutputStream.writeUTF("Connect");
                    dataOutputStream.writeUTF(args[0]);
                    player.sendPluginMessage(this.coreSpigot, "BungeeCord", byteArrayOutputStream.toByteArray());
                } catch (IOException ex) {
                    player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + " §cDies ist derzeit leider nicht möglich!");
                }
            }
        }
        return false;
    }
}
