package main.de.mj.bb.core.listener;

import main.de.mj.bb.core.CoreBungee;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerSwitchListener implements Listener {

    private final CoreBungee coreBungee;

    public ServerSwitchListener(CoreBungee coreBungee) {
        this.coreBungee = coreBungee;
        coreBungee.registerListener(this);
    }

    @EventHandler
    public void onServerSwitch(ServerSwitchEvent switchEvent) {
        coreBungee.getBungeeTablist().setTabList();
    }
}
