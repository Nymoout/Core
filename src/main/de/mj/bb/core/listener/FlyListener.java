package main.de.mj.bb.core.listener;

import lombok.Getter;
import main.de.mj.bb.core.CoreSpigot;
import main.de.mj.bb.core.utils.Particle;
import main.de.mj.bb.core.utils.ServerType;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

@Getter
public class FlyListener implements Listener {

    private final CoreSpigot coreSpigot;
    private HashSet<Player> fly = new HashSet<>();

    public FlyListener(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setListener(this);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onToggleFlight(PlayerToggleFlightEvent flightEvent) {
        Player player = flightEvent.getPlayer();
        if (fly.contains(player)) {
            flightEvent.setCancelled(false);
        } else {
            if (coreSpigot.getServerManager().getServerType().equals(ServerType.LOBBY)) {
                if (player.getGameMode() != GameMode.CREATIVE && coreSpigot.getServerManager().getSettingsListener().getDoubleJump().contains(player)) {
                    player.setAllowFlight(false);
                    player.setFlying(false);
                    player.setVelocity(player.getLocation().getDirection().multiply(0.9D).add(new Vector(0, 0.9, 0)));
                    player.playSound(player.getLocation(), Sound.LAVA_POP, 1, 1);
                    Particle particle = new Particle(EnumParticle.FIREWORKS_SPARK, player.getLocation().add(0, 2.25, 0), true, 0.25f, 0.25f, 0.25f, 0, 100);
                    particle.sendAll();
                }
            }
            flightEvent.setCancelled(true);
        }
    }
}

