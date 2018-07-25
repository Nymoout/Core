package de.mj.BattleBuild.lobby.utils;

import org.bukkit.Location;

public class LocationsUtil {

    private static Location bedwars;
    private static Location citybuild;
    private static Location gungame;
    private static Location skywars;
    private static Location spawn;

    public LocationsUtil() {}

    public Location getBedwars() {
        return bedwars;
    }

    public Location getCitybuild() {
        return citybuild;
    }

    public Location getGungame() {
        return gungame;
    }

    public Location getSkywars() {
        return skywars;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setBedwars(Location bedwars) {
        this.bedwars = bedwars;
    }

    public void setCitybuild(Location citybuild) {
        this.citybuild = citybuild;
    }

    public void setGungame(Location gungame) {
        this.gungame = gungame;
    }

    public void setSkywars(Location skywars) {
        this.skywars = skywars;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }
}
