/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package main.de.mj.bb.core.listener;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import cloud.timo.TimoCloud.api.objects.ServerGroupObject;
import cloud.timo.TimoCloud.api.objects.ServerObject;
import main.de.mj.bb.core.CoreSpigot;
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
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LobbySwitcherListener implements Listener {

    private final CoreSpigot coreSpigot;
    private Inventory inv = Bukkit.createInventory(null, 9, "§f§lLobby-Switcher");

    public LobbySwitcherListener(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setListener(this);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent interactEvent) {
        Player player = interactEvent.getPlayer();
        if (player.getItemInHand() == null || player.getItemInHand().getType().equals(Material.AIR)) return;
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
                        starting("Lobby-" + size, size);
                    }
                    size--;
                    player.openInventory(this.inv);
                    player.updateInventory();
                }
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent clickEvent) {
        if (clickEvent.getClickedInventory() == null) return;
        if (clickEvent.getClickedInventory().getType() == null) return;
        if (clickEvent.getCurrentItem() == null) return;
        if (clickEvent.getCurrentItem().getType() == null) return;
        if (clickEvent.getCurrentItem().getType().equals(Material.AIR)) return;
        if (!clickEvent.getCurrentItem().getType().equals(Material.SKULL_ITEM)) return;
        Player player = (Player) clickEvent.getWhoClicked();
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
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                    dataOutputStream.writeUTF("Connect");
                    dataOutputStream.writeUTF(server);
                    player.sendPluginMessage(this.coreSpigot, "BungeeCord", byteArrayOutputStream.toByteArray());
                } catch (IOException ex) {
                    player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + " §cDies ist derzeit leider nicht möglich!");
                }
            }
        } else return;
    }

    @SuppressWarnings("deprecation")
    private void Inv(String name, int online, int where, String curServer) {
        String[] name2 = curServer.split("-");
        int size2 = Integer.parseInt(name2[1]);
        String o = "§7Online Spieler : §6" + online;
        List<String> list = new ArrayList<>();
        list.add(o);
        list.add("§7Server ist §6§lOnline§7.");
        if (size2 == where) {
            list.add("§cDerzeit verbunden!");
        } else {
            list.add("§aKlicke zum verbinden!");
        }
        where--;
        this.inv.setItem(where, coreSpigot.getModuleManager().getItemCreator().createItemWithSkull("4ef4874eb050a3d3e46f69ae40c3b59a3596f71664a0db6ca600d5c67f47e4", online, "§a" + name, list, true));
    }

    private void starting(String name, int where) {
        List<String> list = new ArrayList<>();
        list.add("§7Server wird §e§lgestartet§7.");
        where--;
        this.inv.setItem(where, coreSpigot.getModuleManager().getItemCreator().createItemWithSkull("4d48e75ff55cb57533c7b904be887a374925f93832f7ae16b7923987e970", 1, "§c" + name, list, false));
    }
}
