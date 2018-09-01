package de.mj.BattleBuild.core.listener;

import de.mj.BattleBuild.core.Core;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class ScrollListener implements Listener {

    public ScrollListener(Core core) {
        core.setListener(this);
    }

    @EventHandler
    public void onScroll(PlayerItemHeldEvent heldEvent) {
        Player player = heldEvent.getPlayer();
        player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1, 1);
    }
}
