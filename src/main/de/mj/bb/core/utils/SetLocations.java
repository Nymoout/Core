/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package main.de.mj.bb.core.utils;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class SetLocations {

    private static File file = new File("plugins/BBLobby/", "locations.yml");
    private static YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
    private final CoreSpigot coreSpigot;

    public SetLocations(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
    }

    private static Location initLocs(String path) {
        double x = yamlConfiguration.getDouble("bb." + path + ".x");
        double y = yamlConfiguration.getDouble("bb." + path + ".y");
        double z = yamlConfiguration.getDouble("bb." + path + ".z");
        float yaw = yamlConfiguration.getLong("bb." + path + ".yaw");
        float pitch = yamlConfiguration.getLong("bb." + path + ".pitch");
        Location loc = new Location(Bukkit.getWorld(yamlConfiguration.getString("bb.spawn.world")), x, y, z, yaw, pitch);
        return loc;
    }

    void saveLocs() {
        LocationsUtil locationsUtil = coreSpigot.getServerManager().getLocationsUtil();
        locationsUtil.setBedwars(initLocs("bedwars"));
        locationsUtil.setCitybuild(initLocs("citybuild"));
        locationsUtil.setGungame(initLocs("gungame"));
        locationsUtil.setSkywars(initLocs("skywars"));
        locationsUtil.setSpawn(initLocs("spawn"));
    }

    public File getFile() {
        return file;
    }

    public YamlConfiguration getYamlConfiguration() {
        return yamlConfiguration;
    }
}
