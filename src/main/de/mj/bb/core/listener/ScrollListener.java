package main.de.mj.bb.core.listener;

import main.de.mj.bb.core.CoreSpigot;
import main.de.mj.bb.core.utils.PlayerLevel;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.jetbrains.annotations.NotNull;

public class ScrollListener implements Listener {

    private final CoreSpigot coreSpigot;

    public ScrollListener(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setListener(this);
    }

    @EventHandler
    public void onScroll(PlayerItemHeldEvent heldEvent) {
        Player player = heldEvent.getPlayer();
        try {
            if (coreSpigot.getModuleManager().getSettingsListener().getPlayerLevel().get(player).equals(PlayerLevel.SCROLL)) {
                player.setLevel(heldEvent.getNewSlot() + 1);
                player.setExp((float) (heldEvent.getNewSlot() + 1) / 9);
            }
        } catch (Exception ignore) {}
        player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1, 1);
    }
}
