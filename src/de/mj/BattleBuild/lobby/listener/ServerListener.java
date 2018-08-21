/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.lobby.listener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.mj.BattleBuild.lobby.Lobby;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ServerListener implements Listener {

    private final Lobby lobby;

    public ServerListener(Lobby lobby) {
        this.lobby = lobby;
        lobby.setListener(this);
    }

    @EventHandler
    public void onServerClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getClickedInventory().getTitle().equals("§9§lOnline Server")) {
            String server = event.getCurrentItem().getItemMeta().getDisplayName();
            System.out.println(server);
            player.sendMessage(lobby.getServerManager().getData().getPrefix() + "§7§7Du betrittst nun den Server §6§l" + server);
            player.closeInventory();
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(server);
            player.sendPluginMessage(this.lobby, "BungeeCord", out.toByteArray());
        }
        return;
    }
}
