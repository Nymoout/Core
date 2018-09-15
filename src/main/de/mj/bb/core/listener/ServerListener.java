/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package main.de.mj.bb.core.listener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public class ServerListener implements Listener {

    private final CoreSpigot coreSpigot;

    public ServerListener(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setListener(this);
    }

    @EventHandler
    public void onServerClick(InventoryClickEvent clickEvent) {
        if (clickEvent.getClickedInventory() == null) return;
        if (clickEvent.getClickedInventory().getType() == null) return;
        if (clickEvent.getCurrentItem() == null) return;
        if (clickEvent.getCurrentItem().getType() == null) return;
        if (clickEvent.getCurrentItem().getType().equals(Material.AIR)) return;
        Player player = (Player) clickEvent.getWhoClicked();
        if (clickEvent.getClickedInventory().getTitle().equals("§9§lOnline Server")) {
            String server = clickEvent.getCurrentItem().getItemMeta().getDisplayName();
            System.out.println(server);
            player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "§7§7Du betrittst nun den Server §6§l" + server);
            player.closeInventory();
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(server);
            player.sendPluginMessage(this.coreSpigot, "BungeeCord", out.toByteArray());
        }
        return;
    }
}
