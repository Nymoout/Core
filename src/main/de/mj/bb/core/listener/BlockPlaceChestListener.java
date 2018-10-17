package main.de.mj.bb.core.listener;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceChestListener implements Listener {

    public BlockPlaceChestListener(CoreSpigot coreSpigot) {
        coreSpigot.setListener(this);
    }

    @EventHandler
    public void onPlaceChest(BlockPlaceEvent placeEvent) {
        Player user = placeEvent.getPlayer();
        if (user.getItemInHand().getType().equals(Material.CHEST)) {
            String name = user.getItemInHand().getItemMeta().getDisplayName();
            switch (name) {
                case "BIRCH":
                    Block birch = placeEvent.getBlock();
                    birch.setTypeIdAndData(17, (byte) 14, false);
                    break;
                case "SPRUCE":
                    Block spruce = placeEvent.getBlock();
                    spruce.setTypeIdAndData(17, (byte) 13, false);
                    break;
                case "JUNGLEE":
                    Block junglee = placeEvent.getBlock();
                    junglee.setTypeIdAndData(17, (byte) 15, false);
                    break;
                case "AKAZIA":
                    Block akazia = placeEvent.getBlock();
                    akazia.setTypeIdAndData(162, (byte) 12, false);
                    break;
                case "DARK_OAK":
                    Block doak = placeEvent.getBlock();
                    doak.setTypeIdAndData(162, (byte) 13, false);
                    break;
                default:
                    Block oak = placeEvent.getBlock();
                    oak.setTypeIdAndData(17, (byte) 12, false);

            }
        }
    }

}
