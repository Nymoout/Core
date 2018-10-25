package main.de.mj.bb.core.listener;

import main.de.mj.bb.core.CoreBungee;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.jetbrains.annotations.NotNull;

public class DisconnectListener implements Listener {

    private final CoreBungee coreBungee;

    public DisconnectListener(@NotNull CoreBungee coreBungee) {
        this.coreBungee = coreBungee;
        coreBungee.registerListener(this);
    }

    @EventHandler
    public void onDisconnect (PlayerDisconnectEvent disconnectEvent) {
        coreBungee.getBungeeTablist().setTabList();
    }
}
