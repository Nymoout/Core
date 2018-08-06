package de.mj.BattleBuild.lobby.listener;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class JumpPadListener implements Listener {

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if((p.getLocation().getBlock().getType() == Material.IRON_PLATE)){
            if(SettingsListener.jumppads.contains(p)) {
                Vector v = p.getLocation().getDirection().multiply(2.0D).setY(1.0D);
                p.setVelocity(v);
                p.playSound(p.getLocation(), Sound.DIG_SAND, 1, 1);
                p.playEffect(p.getLocation(), Effect.LAVA_POP, 100);
            }
        }
    }
}
