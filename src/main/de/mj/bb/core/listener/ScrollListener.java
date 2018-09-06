package main.de.mj.bb.core.listener;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.jetbrains.annotations.NotNull;

public class ScrollListener implements Listener {

    public ScrollListener(@NotNull CoreSpigot coreSpigot) {
        coreSpigot.setListener(this);
    }

    @EventHandler
    public void onScroll(PlayerItemHeldEvent heldEvent) {
        Player player = heldEvent.getPlayer();
        for (Player all : Bukkit.getOnlinePlayers())
            all.playSound(player.getLocation(), Sound.NOTE_STICKS, 1, 1);
    }
}
