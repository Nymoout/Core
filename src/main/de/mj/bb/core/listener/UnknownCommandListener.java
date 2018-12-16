package main.de.mj.bb.core.listener;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;

public class UnknownCommandListener implements Listener {

    private final CoreSpigot coreSpigot;

    public UnknownCommandListener(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setListener(this);
    }

    @EventHandler
    public void onPlayerUnknownCommand(PlayerCommandPreprocessEvent preprocessEvent) {
        if (!preprocessEvent.isCancelled()) {
            Player player = preprocessEvent.getPlayer();
            String command = preprocessEvent.getMessage().split(" ")[0];
            HelpTopic topic = Bukkit.getServer().getHelpMap().getHelpTopic(command);
            if (topic == null) {
                player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "§7Der Befehl §6" + command + " §7konnte nicht gefunden werden!");
                preprocessEvent.setCancelled(true);
            }
        }
    }
}
