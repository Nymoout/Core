package main.de.mj.bb.core.managers;

import main.de.mj.bb.core.CoreSpigot;
import main.de.mj.bb.core.utils.Portal;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;

public class PortalManager {

    private final CoreSpigot coreSpigot;

    public PortalManager(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
    }

    public void setPortal(Location location, Integer edge, String name) {
        YamlConfiguration portalConfig = coreSpigot.getModuleManager().getFileManager().getPortalConfig();
        portalConfig.getConfigurationSection("Portals").set(name + "." + edge + ".world", location.getWorld().getName());
        portalConfig.getConfigurationSection("Portals").set(name + "." + edge + ".x", location.getBlockX());
        portalConfig.getConfigurationSection("Portals").set(name + "." + edge + ".y", location.getBlockY());
        portalConfig.getConfigurationSection("Portals").set(name + "." + edge + ".z", location.getBlockZ());

        try {
            portalConfig.save(coreSpigot.getModuleManager().getFileManager().getPortalFile());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loadPortals() {
        YamlConfiguration portalConfig = coreSpigot.getModuleManager().getFileManager().getPortalConfig();
        for (String path : portalConfig.getConfigurationSection("Portals").getKeys(false)) {
            //PortalLoc1
            int x1 = portalConfig.getInt("Portals." + path + "." + 1 + ".x");
            int y1 = portalConfig.getInt("Portals." + path + "." + 1 + ".y");
            int z1 = portalConfig.getInt("Portals." + path + "." + 1 + ".z");
            Location location1 = new Location(Bukkit.getWorld("world"), x1, y1, z1);

            //PortalLoc2
            int x2 = portalConfig.getInt("Portals." + path + "." + 2 + ".x");
            int y2 = portalConfig.getInt("Portals." + path + "." + 2 + ".y");
            int z2 = portalConfig.getInt("Portals." + path + "." + 2 + ".z");
            Location location2 = new Location(Bukkit.getWorld("world"), x2, y2, z2);

            Portal.setPortalLocation1(path, location1);
            Portal.setPortalLocation2(path, location2);
        }
    }
}
