package main.de.mj.bb.core.utils;

import main.de.mj.bb.core.CoreSpigot;
import me.badbones69.blockparticles.api.BlockParticles;
import me.badbones69.blockparticles.api.Particles;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

public class LobbyParticle {

    private final CoreSpigot coreSpigot;
    private Location beaconLocation = new Location(Bukkit.getWorld("world"), 0, 65, 0);
    private Location rewardLocation = new Location(Bukkit.getWorld("world"), -15, 66, 8);

    public LobbyParticle(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
    }

    public void playEnderSignal() {
        new BukkitRunnable() {
            Location location = LobbyParticle.this.beaconLocation.add(0.5D, 0.0D, 0.5D);

            public void run() {
                Bukkit.getWorld("world").playEffect(location, Effect.ENDER_SIGNAL, 1);
                Bukkit.getWorld("world").playEffect(location, Effect.ENDER_SIGNAL, 1);
                Bukkit.getWorld("world").playEffect(location, Effect.ENDER_SIGNAL, 1);
                Bukkit.getWorld("world").playEffect(location, Effect.ENDER_SIGNAL, 1);
            }
        }.runTaskTimer(coreSpigot, 0L, 8L);
    }

    public void playEnchantment() {
        BlockParticles blockParticles = BlockParticles.getInstance();
        blockParticles.setParticle(Particles.ENCHANT, rewardLocation, "Reward");
    }
}
