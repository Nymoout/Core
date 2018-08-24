/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.core.utils;

import org.bukkit.Location;

public class LocationsUtil {

    private static Location bedwars;
    private static Location citybuild;
    private static Location gungame;
    private static Location skywars;
    private static Location spawn;

    public LocationsUtil() {
    }

    public Location getBedwars() {
        return bedwars;
    }

    public void setBedwars(Location bedwars) {
        LocationsUtil.bedwars = bedwars;
    }

    public Location getCitybuild() {
        return citybuild;
    }

    public void setCitybuild(Location citybuild) {
        LocationsUtil.citybuild = citybuild;
    }

    public Location getGungame() {
        return gungame;
    }

    public void setGungame(Location gungame) {
        LocationsUtil.gungame = gungame;
    }

    public Location getSkywars() {
        return skywars;
    }

    public void setSkywars(Location skywars) {
        LocationsUtil.skywars = skywars;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        LocationsUtil.spawn = spawn;
    }
}
