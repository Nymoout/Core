package de.mj.BattleBuild.core.commands;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.mj.BattleBuild.core.CoreSpigot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("Connect");
                out.writeUTF(args[0]);
                player.sendPluginMessage(this.coreSpigot, "BungeeCord", out.toByteArray());
            }
        }
        return false;
    }
}
