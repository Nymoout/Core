/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.core.listener;

import de.mj.BattleBuild.core.CoreSpigot;
import de.mj.BattleBuild.core.utils.Data;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class StopReloadRestartListener implements Listener {

    private static boolean isrestarting = false;
    private final CoreSpigot coreSpigot;
    String prefix = new Data().getPrefix();

    public StopReloadRestartListener(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setListener(this);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void OverrideCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String msg = e.getMessage().toLowerCase();
        if (p.hasPermission("group.administrator")) {
            if (msg.startsWith("/stop") || msg.startsWith("/reload") || msg.startsWith("/restart")) {
                e.setCancelled(true);
                Inventory inv = Bukkit.createInventory(null, 9, "§4§lSERVERNEUSTART?");
                ItemStack ClayNein = new ItemStack(Material.STAINED_CLAY, 1, (short) 14);
                ItemMeta ClayNeinMeta = ClayNein.getItemMeta();
                ClayNeinMeta.setDisplayName("§cNein");
                ClayNein.setItemMeta(ClayNeinMeta);
                inv.setItem(0, ClayNein);

                ItemStack ClayJa = new ItemStack(Material.STAINED_CLAY, 1, (short) 5);
                ItemMeta ClayJaMeta = ClayJa.getItemMeta();
                ClayJaMeta.setDisplayName("§aJa");
                ClayJa.setItemMeta(ClayJaMeta);
                inv.setItem(8, ClayJa);

                p.openInventory(inv);
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;
        if (e.getCurrentItem().getType().equals(Material.AIR)) return;
        if (e.getInventory().getTitle().contains("SERVERNEUSTART")) {
            Player p = (Player) e.getWhoClicked();
            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aJa")) {
                p.closeInventory();
                p.sendMessage("§aDu hast einen Serverneustart initialisiert!");
                if (!isrestarting) shutdown();
                isrestarting = true;
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cNein")) {
                p.closeInventory();
            }
        }
    }

    private void shutdown() {
        Bukkit.broadcastMessage(prefix + "§c§lDer Server muss neu gestartet werden.");
        Bukkit.broadcastMessage(prefix + "§c" + Bukkit.getServerName() + " wird gleich wieder verf\u00FCgbar sein.");
        new BukkitRunnable() {
            int time = 10;

            @Override
            public void run() {
                if (time == 10) Bukkit.broadcastMessage(prefix + "§7Neustart in §6§l" + time + " Sekunden§7.");
                if (time == 5) Bukkit.broadcastMessage(prefix + "§7Neustart in §6§l" + time + " Sekunden§7.");
                if (time <= 3 && time != 1)
                    Bukkit.broadcastMessage(prefix + "§7Neustart in §6§l" + time + " Sekunden§7.");
                if (time == 1) Bukkit.broadcastMessage(prefix + "§7Neustart in §6§l" + time + " Sekunde§7.");
                time--;
                if (time == 0) {
                    cancel();
                    Bukkit.shutdown();
                }

            }
        }.runTaskTimer(coreSpigot, 0L, 20L);
    }
}
