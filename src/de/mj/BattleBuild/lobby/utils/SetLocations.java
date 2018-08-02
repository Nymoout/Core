package de.mj.BattleBuild.lobby.utils;

import de.mj.BattleBuild.lobby.Lobby;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class SetLocations {

    private final Lobby lobby;
    private static File file = new File("/plugins/BBLobby/", "locations.yml");
    private static YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    public SetLocations (Lobby lobby) {
        this.lobby = lobby;
    }

    public void saveLocs() {
        lobby.getLocationsUtil().setBedwars(initLocs("bedwars"));
        lobby.getLocationsUtil().setCitybuild(initLocs("citybuild"));
        lobby.getLocationsUtil().setGungame(initLocs("gungame"));
        lobby.getLocationsUtil().setSkywars(initLocs("skywars"));
        lobby.getLocationsUtil().setSpawn(initLocs("spawn"));
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
