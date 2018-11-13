package main.de.mj.bb.core.listener;

import com.intellectualcrafters.plot.flag.FlagManager;
import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class VorbauenListener implements Listener {
    private final CoreSpigot coreSpigot;

    public VorbauenListener(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setListener(this);
    }

    @EventHandler
    public void onFinish(InventoryClickEvent clickEvent) {
        if (clickEvent.getClickedInventory() == null) return;
        if (clickEvent.getClickedInventory().getType() == null) return;
        if (clickEvent.getCurrentItem() == null) return;
        if (clickEvent.getCurrentItem().getType() == null) return;
        if (clickEvent.getCurrentItem().getType().equals(Material.AIR)) return;
        Player player = (Player) clickEvent.getWhoClicked();
        if (clickEvent.getInventory().getName().contains("Finish")) {
            if (clickEvent.getCurrentItem().getItemMeta().getDisplayName().contains("§aYES")) {
                player.closeInventory();
                player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "§7Du hast dein Plot als fertig makiert!");
                coreSpigot.getHookManager().getPlotAPI().getPlot(player).setFlag(FlagManager.getFlag("place"), false);
                coreSpigot.getHookManager().getPlotAPI().getPlot(player).setFlag(FlagManager.getFlag("break"), false);
                coreSpigot.getHookManager().getPlotAPI().getPlot(player).setFlag(FlagManager.getFlag("use"), false);
                if (!coreSpigot.getModuleManager().getFileManager().getFinishConfig().isConfigurationSection("Players"))
                    coreSpigot.getModuleManager().getFileManager().getFinishConfig().createSection("Players");
                coreSpigot.getModuleManager().getFileManager().getFinishConfig().set("Players", player.getUniqueId());
                try {
                    coreSpigot.getModuleManager().getFileManager().getFinishConfig().save(coreSpigot.getModuleManager().getFileManager().getFinishFile());
                } catch (IOException io) {
                    io.printStackTrace();
                }
            } else {
                player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "§7Du hast den Vorgang abgebrochen!");
                player.closeInventory();
            }
        }

    }
}
