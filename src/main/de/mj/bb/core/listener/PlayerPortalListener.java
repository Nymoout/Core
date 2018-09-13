package main.de.mj.bb.core.listener;

import main.de.mj.bb.core.CoreSpigot;
import main.de.mj.bb.core.utils.Portal;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class PlayerPortalListener implements Listener {

    private final CoreSpigot coreSpigot;

    public PlayerPortalListener(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setListener(this);
    }

    @EventHandler
    public void onPortal(PlayerPortalEvent portalEvent) {
        Player player = portalEvent.getPlayer();
        Map<String, Location> location1 = Portal.getPortalLocation1();
        Map<String, Location> location2 = Portal.getPortalLocation2();
        int playerX = player.getLocation().getBlockX();
        int playerY = player.getLocation().getBlockY();
        int playerZ = player.getLocation().getBlockZ();
        for (Map.Entry<String, Location> locationEntry1 : location1.entrySet()) {
            Location locationEntry2 = location2.get(locationEntry1.getKey());
            int x1 = locationEntry1.getValue().getBlockX();
            int x2 = locationEntry2.getBlockX();
            int y1 = locationEntry1.getValue().getBlockY();
            int y2 = locationEntry2.getBlockY();
            int z1 = locationEntry1.getValue().getBlockZ();
            int z2 = locationEntry2.getBlockZ();

            if (playerX <= x1 && playerX >= x2 && playerY >= y1 && playerY <= y2 && playerZ <= z1 && playerZ >= z2) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(outputStream);
                try {
                    out.writeUTF("Connect");
                    out.writeUTF(locationEntry1.getKey());
                    player.sendPluginMessage(this.coreSpigot, "BungeeCord", outputStream.toByteArray());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                portalEvent.setCancelled(true);
                break;
            }
        }
    }
}
