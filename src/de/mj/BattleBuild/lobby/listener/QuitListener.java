package de.mj.BattleBuild.lobby.listener;

import de.mj.BattleBuild.lobby.main.Lobby;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    private final Lobby lobby;
    private MinionListener minionListener;

    public QuitListener(Lobby lobby) {
        this.lobby = lobby;
        lobby.setListener(this);
        minionListener = lobby.getMinionListener();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onQuit(PlayerQuitEvent e) {
        minionListener.rmMini(e.getPlayer());
        e.setQuitMessage(null);
    }
}
