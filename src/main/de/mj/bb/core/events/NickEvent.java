package main.de.mj.bb.core.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@Setter
public class NickEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private Player player;
    private String nickName;

    public NickEvent(Player player, String nickName) {
        this.player = player;
        this.nickName = nickName;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}
