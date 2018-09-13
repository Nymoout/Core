package main.de.mj.bb.core.listener;

import com.intellectualcrafters.plot.object.PlotPlayer;
import lombok.Getter;
import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

@Getter
public class BorderListener implements Listener {

    private final CoreSpigot coreSpigot;
    private Inventory borderInv = Bukkit.createInventory(null, 9, "§6§lWähle deinen Rand");

    public BorderListener(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setListener(this);
        loadBorderInventory();
    }

    @EventHandler
    public void onChooseBorder(InventoryClickEvent clickEvent) {
        if (clickEvent.getClickedInventory() == null || clickEvent.getClickedInventory().getType() == null) return;
        if (clickEvent.getCurrentItem().getType() == null || clickEvent.getCurrentItem() == null) return;
        Player player = (Player) clickEvent.getWhoClicked();
        PlotPlayer plotPlayer = PlotPlayer.wrap(player);
        if (plotPlayer.getCurrentPlot().isOwner(player.getUniqueId())) {
            //not finished yet
        }

    }

    private void loadBorderInventory() {
        borderInv.setItem(0, coreSpigot.getModuleManager().getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, 0, 1));
        borderInv.setItem(1, coreSpigot.getModuleManager().getItemCreator().createItemWithMaterial(Material.STONE_SLAB2, 0, 1));
        borderInv.setItem(2, coreSpigot.getModuleManager().getItemCreator().createItemWithMaterial(Material.STONE_SLAB2, 1, 1));
        borderInv.setItem(3, coreSpigot.getModuleManager().getItemCreator().createItemWithMaterial(Material.STONE_SLAB2, 3, 1));
        borderInv.setItem(4, coreSpigot.getModuleManager().getItemCreator().createItemWithMaterial(Material.STONE_SLAB2, 4, 1));
        borderInv.setItem(5, coreSpigot.getModuleManager().getItemCreator().createItemWithMaterial(Material.WOOD_STEP, 0, 1));
        borderInv.setItem(6, coreSpigot.getModuleManager().getItemCreator().createItemWithMaterial(Material.EMERALD_BLOCK, 0, 1));
        borderInv.setItem(7, coreSpigot.getModuleManager().getItemCreator().createItemWithMaterial(Material.DIAMOND_BLOCK, 0, 1));
        borderInv.setItem(8, coreSpigot.getModuleManager().getItemCreator().createItemWithMaterial(Material.BEACON, 0, 1));
    }

}
