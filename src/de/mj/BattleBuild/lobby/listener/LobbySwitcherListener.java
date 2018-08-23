/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.lobby.listener;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import cloud.timo.TimoCloud.api.objects.ServerGroupObject;
import cloud.timo.TimoCloud.api.objects.ServerObject;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.mj.BattleBuild.lobby.Lobby;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class LobbySwitcherListener implements Listener {

    private final Lobby lobby;
    private Inventory inv = Bukkit.createInventory(null, 9, "§f§lLobby-Switcher");

    public LobbySwitcherListener(Lobby lobby) {
        this.lobby = lobby;
        lobby.setListener(this);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent interactEvent) {
        Player player = interactEvent.getPlayer();
        try {
            if (((interactEvent.getAction() == Action.RIGHT_CLICK_BLOCK) || (interactEvent.getAction() == Action.RIGHT_CLICK_AIR))
                    && (interactEvent.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8\u00BB§f§lLobby-Switcher§8\u00AB"))) {
                player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1.0F, 1.0F);
                ServerGroupObject go = TimoCloudAPI.getUniversalAPI().getServerGroup("Lobby");
                int i = go.getServers().size();
                int size = i;
                if (size == 1) {
                    player.sendMessage("§7[§6§lBattleBuild§7] §7Derzeit sind keine weiteren Lobby-Server online!");
                } else {
                    String curServer = player.getServer().getServerName();
                    while (size > 0) {
                        ServerObject so = TimoCloudAPI.getUniversalAPI().getServer("Lobby-" + size);
                        int c = so.getOnlinePlayerCount();
                        if (so.getState().equals("ONLINE")) {
                            Inv("Lobby-" + size, c, size, curServer);
                        } else {
                            Strating("Lobby-" + size, size);
                        }
                        size--;
                        player.openInventory(this.inv);
                        player.updateInventory();
                    }
                }
            }
        } catch (NullPointerException localNullPointerException) {
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent clickEvent) {
        if (clickEvent.getWhoClicked() == null) {
            return;
        }
        Player player = (Player) clickEvent.getWhoClicked();
        try {
            if (clickEvent.getCurrentItem().getItemMeta().getDisplayName().contains("Lobby")
                    && !clickEvent.getCurrentItem().getItemMeta().getDisplayName().contains("Silent")) {
                String[] lobby = clickEvent.getCurrentItem().getItemMeta().getDisplayName().split("-");
                String server = "Lobby-" + lobby[1];
                String[] splitserver = player.getServer().getServerName().split("-");
                int serverid = Integer.parseInt(splitserver[1]);
                int lobbyid = Integer.parseInt(lobby[1]);
                if (lobbyid == serverid) {
                    player.sendMessage("§7[§6§lBattleBuild§7] §7Du bist bereits mit diesem Server verbunden!");
                } else {
                    player.sendMessage("§7[§6§lBattleBuild§7] §7§7Du betrittst nun den Server §6§lLobby-" + lobby[1]);
                    player.closeInventory();
                    ByteArrayDataOutput out = ByteStreams.newDataOutput();
                    out.writeUTF("Connect");
                    out.writeUTF(server);
                    player.sendPluginMessage(this.lobby, "BungeeCord", out.toByteArray());
                }
            }
        } catch (Exception ex) {
        }
    }

    @SuppressWarnings("deprecation")
    private void Inv(String name, int online, int where, String curServer) {
        String[] name2 = curServer.split("-");
        int size2 = Integer.parseInt(name2[1]);
        ItemStack emer = new ItemStack(351, 1, (short) 10);
        ItemMeta emerm = emer.getItemMeta();
        emerm.setDisplayName("§a" + name);
        String o = "§7Online Spieler : §6" + online;
        ArrayList<String> list = new ArrayList<String>();
        list.add(o);
        list.add("§7Server im §6§lOnlineModus§7.");
        if (size2 == where) {
            list.add("§cDerzeit verbunden!");
        } else {
            list.add("§aKlicke zum verbinden!");
        }
        emerm.setLore(list);
        emer.setItemMeta(emerm);
        where--;
        this.inv.setItem(where, emer);
    }

    public void Strating(String name, int where) {
        ItemStack emer = new ItemStack(Material.INK_SACK, 1, (short) 8);
        ItemMeta emerm = emer.getItemMeta();
        emerm.setDisplayName("§c" + name);
        ArrayList<String> list = new ArrayList<String>();
        list.add("§7Server wird §e§lgestartet§7.");
        emerm.setLore(list);
        emer.setItemMeta(emerm);
        where--;
        this.inv.setItem(where, emer);
    }
}
