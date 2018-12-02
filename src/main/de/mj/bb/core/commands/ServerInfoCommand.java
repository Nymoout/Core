package main.de.mj.bb.core.commands;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ServerInfoCommand implements CommandExecutor {

    private final CoreSpigot coreSpigot;

    public ServerInfoCommand(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setCommand(this, "serverinfo");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("Server.Info")) {
            if (cmd.getName().equalsIgnoreCase("serverinfo")) {
                sender.sendMessage("§8§m---------------§8[§9ServerInfo§8]§8§m---------------§r");
                long cram = Runtime.getRuntime().freeMemory() / 1048576L;
                long mram = Runtime.getRuntime().maxMemory() / 1048576L;
                ArrayList<String> names = new ArrayList<>();
                for (Player all : Bukkit.getOnlinePlayers()) {
                    names.add(all.getName());
                }
                sender.sendMessage("§9§lCurrent RAM-Usage: §a" + cram + " MB §9 of §3" + mram + " MB " + "(" + mram / 100 * cram / 100 + "%)");
                sender.sendMessage("§e§lPort: §6" + Bukkit.getPort());
                sender.sendMessage("§bServername: §3" + Bukkit.getServerName());
                sender.sendMessage("§5§lVersion: §b" + Bukkit.getBukkitVersion());
                sender.sendMessage("§6§lPlugin-Version: §e" + coreSpigot.getDescription().getVersion());
                sender.sendMessage("§8§m--------------------------------------------§r");
                return true;
            }
        } else {
            sender.sendMessage(coreSpigot.getModuleManager().getData().getNoPerm());
        }
        return false;
    }
}
