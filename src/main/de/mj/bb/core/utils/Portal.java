package main.de.mj.bb.core.utils;

import org.bukkit.Location;

import java.util.HashMap;

public class Portal {

    private static HashMap<String, Location> portalLocation1 = new HashMap<>();
    private static HashMap<String, Location> portalLocation2 = new HashMap<>();

    public static void setPortalLocation1(String name, Location location1) {
        if (!portalLocation1.containsKey(name))
            portalLocation1.put(name, location1);
        else portalLocation1.replace(name, portalLocation1.get(name), location1);
    }

    public static void setPortalLocation2(String name, Location location2) {
        if (!portalLocation2.containsKey(name))
            portalLocation2.put(name, location2);
        else portalLocation2.replace(name, portalLocation2.get(name), location2);
    }

    public static HashMap<String, Location> getPortalLocation1() {
        return portalLocation1;
    }

    public static HashMap<String, Location> getPortalLocation2() {
        return portalLocation2;
    }
}
