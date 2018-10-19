package main.de.mj.bb.core.managers;

import lombok.Getter;
import main.de.mj.bb.core.CoreBungee;
import nl.chimpgamer.networkmanagerapi.NetworkManagerPlugin;

@Getter
public class BungeeHookManager {

    private final CoreBungee coreBungee;
    private NetworkManagerPlugin networkManagerPlugin;

    public BungeeHookManager(CoreBungee coreBungee) {
        this.coreBungee = coreBungee;
    }

    public void hook() {
        if (coreBungee.getProxy().getPluginManager().getPlugin("NetworkManager") != null) {
            this.networkManagerPlugin = (NetworkManagerPlugin) coreBungee.getProxy().getPluginManager().getPlugin("NetworkManager");
        }
    }
}
