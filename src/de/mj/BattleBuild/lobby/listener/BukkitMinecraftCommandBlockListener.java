package de.mj.BattleBuild.lobby.listener;

import de.mj.BattleBuild.lobby.Variabeln.Var;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class BukkitMinecraftCommandBlockListener implements Listener {

    Var var = new Var();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCMD(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String[] cmd = e.getMessage().substring(1).split(" ");
        if ((cmd[0].startsWith("bukkit") || cmd[0].startsWith("minecraft")) && (!p.isOp())) {
            p.sendMessage(var.getNoperm());
            e.setCancelled(true);
            return;
        }
        if (((cmd[0].equalsIgnoreCase("?")) || (cmd[0].equalsIgnoreCase("help"))) && (!p.isOp())) {
            p.sendMessage(var.getPrefix() + "§7Server sagt: §c§lNEIN!");
            e.setCancelled(true);
        }
        if (((cmd[0].equalsIgnoreCase("ver")) || (cmd[0].equalsIgnoreCase("version"))
                || (cmd[0].equalsIgnoreCase("about"))) && (!p.isOp())) {
            p.sendMessage(var.getNoperm());
            e.setCancelled(true);
        }
        if ((cmd[0].equalsIgnoreCase("aac")) && (!p.isOp())) {
            p.sendMessage(var.getNoperm());
            e.setCancelled(true);
        }
        if (cmd[0].equalsIgnoreCase("tell") && (!p.isOp())) {
            p.sendMessage(var.getNoperm());
            e.setCancelled(true);
        }
        if (cmd[0].equalsIgnoreCase("plugins") || cmd[0].equalsIgnoreCase("plugin") || cmd[0].equalsIgnoreCase("pl")) {
            p.sendMessage(var.getPrefix() + "§7Server sagt: §c§lNEIN!");
            e.setCancelled(true);
        }
    }
}
