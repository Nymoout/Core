package main.de.mj.bb.core.anticheat;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class Events implements Listener {

    private final CoreSpigot coreSpigot;
    private double speed = 0.29D;
    private double speed2 = 0.51;
    private double sprint = 0.62D;
    private double sprint2 = 0.75;

    public Events(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setListener(this);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMove(PlayerMoveEvent moveEvent) {
        Player player = moveEvent.getPlayer();

        //Speed
        Location to = moveEvent.getTo();
        Location from = moveEvent.getFrom();
        int infractions = 0;
        Vector vector = to.toVector().setY(0.0D);
        double i = vector.distance(from.toVector().setY(0.0D));
        if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.SPONGE)) return;
        if (player.getGameMode().equals(GameMode.CREATIVE)) return;
        if (player.getEntityId() == 100) return;
        if (player.getVehicle() != null) return;
        if (player.getAllowFlight()) return;
        infractions++;
        if ((!player.isSprinting()) && (i >= speed)) {
            infractions++;
            if (infractions < 1)
                infractions++;
            if (infractions < 2)
                infractions++;
            if (infractions < 3) {
                infractions++;
                if (player.isOnline()) {
                    player.sendMessage("Speed!!!");
                }
                player.teleport(moveEvent.getFrom());
                infractions = 0;
            }
        }
        Block block = player.getWorld().getBlockAt(new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() - 1, player.getLocation().getZ()));
        if (player.isSprinting() && !block.getType().equals(Material.AIR)) {
            if (moveEvent.getFrom().distance(moveEvent.getTo()) > sprint2) {
                if (player.isOnline()) {
                    player.sendMessage("Sprint2");
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        if (all.isOp())
                            all.sendMessage("Hacks2");
                    }
                }
                player.teleport(moveEvent.getFrom());
            }
        } else if (!player.isSprinting() && !block.getType().equals(Material.AIR)) {
            if (moveEvent.getFrom().distance(moveEvent.getTo()) > speed2) {
                if (player.isOnline()) {
                    player.sendMessage("Speed2");
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        if (all.isOp())
                            all.sendMessage("Hacks2");
                    }
                }
                player.teleport(moveEvent.getFrom());
            }
        }
        if ((player.isSprinting()) && (i >= sprint)) {
            infractions++;
            if (infractions < 1)
                infractions++;
            if (infractions < 2)
                infractions++;
            if (infractions < 3) {
                if (player.isOnline()) {
                    player.sendMessage("Kick");
                }
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (all.isOp())
                        all.sendMessage("Hacks");
                }
                player.teleport(moveEvent.getFrom());
                infractions = 0;
            }
        }

        //Glide
        if (moveEvent.getTo().getY() - moveEvent.getFrom().getY() == -0.125D && moveEvent.getTo().clone().subtract(0.0D, 1.0D, 0.0D).getBlock().getType().equals(Material.AIR)) {
            if (player.isOnline()) {
                player.sendMessage("Glide");
            }
            for (Player all : Bukkit.getOnlinePlayers()) {
                if (all.isOp())
                    all.sendMessage("Glidehack");
            }
            moveEvent.setCancelled(true);
        }
    }


    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setSprint(double sprint) {
        this.sprint = sprint;
    }

    public void setSpeed2(double speed2) {
        this.speed2 = speed2;
    }

    public void setSprint2(double sprint2) {
        this.sprint2 = sprint2;
    }
}
