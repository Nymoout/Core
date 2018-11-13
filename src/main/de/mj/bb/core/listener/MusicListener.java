package main.de.mj.bb.core.listener;

import lombok.Getter;
import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

@Getter
public class MusicListener implements Listener {

    private final CoreSpigot coreSpigot;
    private Set<Player> radioOff = new HashSet<>();

    public MusicListener(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
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
            if (radioOff.contains(player)) {
                coreSpigot.getModuleManager().getSettingsAPI().setRadio(player, true);
                radioOff.remove(player);
            } else {
                coreSpigot.getModuleManager().getSettingsAPI().setRadio(player, false);
                radioOff.add(player);
            }
        }
    }
}
