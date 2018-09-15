package main.de.mj.bb.core.listener;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class MusicListener implements Listener {

    public MusicListener(CoreSpigot coreSpigot) {
        coreSpigot.setListener(this);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent interactEvent) {
        if (interactEvent.getItem() == null) return;
        if (interactEvent.getItem().getType() == null) return;
        if (interactEvent.getItem().getType().equals(Material.AIR)) return;
        if (!(interactEvent.getAction().equals(Action.RIGHT_CLICK_AIR) || interactEvent.getAction().equals(Action.RIGHT_CLICK_BLOCK) || interactEvent.getAction().equals(Action.LEFT_CLICK_AIR) || interactEvent.getAction().equals(Action.LEFT_CLICK_BLOCK)))
            return;
        if (interactEvent.getItem().getType().equals(Material.JUKEBOX)) {
            Player player = interactEvent.getPlayer();
            player.performCommand("radio");
        }
    }
}
