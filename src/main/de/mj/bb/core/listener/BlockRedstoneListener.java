package main.de.mj.bb.core.listener;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

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

    public boolean isBlockRedStone() {
        return this.blockRedStone;
    }

    public void setBlockRedStone(boolean blockRedStone) {
        this.blockRedStone = blockRedStone;
    }
}
