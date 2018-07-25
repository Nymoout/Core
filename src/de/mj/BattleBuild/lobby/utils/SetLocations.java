package de.mj.BattleBuild.lobby.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class SetLocations {

    private static File file = new File("/plugins/BBLobby/", "locations.yml");
    private static YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
    LocationsUtil locationsUtil = new LocationsUtil();

    public SetLocations () {}

    public void saveLocs() {
        locationsUtil.setBedwars(initLocs("bedwars"));
        locationsUtil.setCitybuild(initLocs("citybuild"));
        locationsUtil.setGungame(initLocs("gungame"));
        locationsUtil.setSkywars(initLocs("skywars"));
        locationsUtil.setSpawn(initLocs("spawn"));
    }

    private static Location initLocs(String path) {
        World world = Bukkit.getWorld("world");
        double x = yamlConfiguration.getDouble("bb." + path + ".x");
        double y = yamlConfiguration.getDouble("bb." + path + ".y");
        double z = yamlConfiguration.getDouble("bb." + path + ".z");
        float yaw = yamlConfiguration.getLong("bb." + path + ".yaw");
        float pitch = yamlConfiguration.getLong("bb." + path + ".yaw");
        return new Location(world, x, y, z, yaw, pitch);
    }
}
