package main.de.mj.bb.core.listener;

import lombok.Getter;
import lombok.Setter;
import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

@Getter
@Setter
public class BlockRedstoneListener implements Listener {

    private boolean blockRedStone = false;

    public BlockRedstoneListener(CoreSpigot coreSpigot) {
        coreSpigot.setListener(this);
    }

    @EventHandler
    public void onRedstone(BlockRedstoneEvent redStoneEvent) {
        if (blockRedStone)
            redStoneEvent.setNewCurrent(0);
    }
}
