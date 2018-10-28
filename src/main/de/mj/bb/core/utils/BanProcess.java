package main.de.mj.bb.core.utils;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.io.*;

public class BanProcess implements PluginMessageListener {

    private final CoreSpigot coreSpigot;
    private Block block;
    private int time = 110;

    public BanProcess(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
    }

    @Override
    public synchronized void onPluginMessageReceived(String channel, Player player, byte[] message) {
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(message));
        try {
            String[] data = dataInputStream.readUTF().split(":");
            player.setAllowFlight(true);
            Vector vector = new Vector(0, 0.1, 0);
            new BukkitRunnable() {
                @SuppressWarnings("deprecation")
                @Override
                public void run() {
                    if (time > 20)
                        player.setVelocity(vector);
                    if (time % 2 == 0 && time > 20) {
                        player.playSound(player.getLocation(), Sound.NOTE_BASS, 1F, 2F);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 200));
                        player.closeInventory();
                    } else {
                        player.removePotionEffect(PotionEffectType.BLINDNESS);
                    }
                    if (time == 20) {
                        block = player.getWorld().getBlockAt(new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() - 2, player.getLocation().getZ()));
                        block.setType(Material.BARRIER);
                        player.setAllowFlight(false);
                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1F, 1F);
                    }
                    if (time == 0) {
                        time = 110;
                        block = player.getWorld().getBlockAt(new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() - 1, player.getLocation().getZ()));
                        block.setType(Material.AIR);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                        try {
                            dataOutputStream.writeUTF("finalBan");
                            player.sendPluginMessage(coreSpigot, "BungeeCord", byteArrayOutputStream.toByteArray());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        cancel();
                    }
                    time--;
                }
            }.runTaskTimer(coreSpigot, 0L, 1L);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
