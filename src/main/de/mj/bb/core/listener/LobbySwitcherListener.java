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
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

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
        if (!clickEvent.getCurrentItem().getType().equals(Material.INK_SACK)) return;
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
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("Connect");
                out.writeUTF(server);
                player.sendPluginMessage(this.coreSpigot, "BungeeCord", out.toByteArray());
            }
        } else return;
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
        list.add("§7Server ist §6§lOnline§7.");
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

    private void starting(String name, int where) {
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
