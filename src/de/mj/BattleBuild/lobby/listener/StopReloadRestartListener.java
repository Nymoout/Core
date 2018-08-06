package de.mj.BattleBuild.lobby.listener;

import de.mj.BattleBuild.lobby.main.Lobby;
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

import de.mj.BattleBuild.lobby.Variabeln.Var;

public class StopReloadRestartListener implements Listener {

    private static boolean isrestarting = false;
    String prefix = new Var().getPrefix();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void OverrideCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String msg = e.getMessage().toLowerCase();
        if (p.hasPermission("group.administrator")) {
            if (msg.startsWith("/stop") || msg.startsWith("/reload") || msg.startsWith("/restart")) {
                e.setCancelled(true);
                Inventory inv = Bukkit.createInventory(null, 9, "§4§lSERVERNEUSTART?");
                ItemStack ClayNein = new ItemStack(Material.STAINED_CLAY, 1, (short)14);
                ItemMeta ClayNeinMeta = ClayNein.getItemMeta();
                ClayNeinMeta.setDisplayName("§cNein");
                ClayNein.setItemMeta(ClayNeinMeta);
                inv.setItem(0, ClayNein);

                ItemStack ClayJa = new ItemStack(Material.STAINED_CLAY, 1, (short)5);
                ItemMeta ClayJaMeta = ClayJa.getItemMeta();
                ClayJaMeta.setDisplayName("§aJa");
                ClayJa.setItemMeta(ClayJaMeta);
                inv.setItem(8, ClayJa);

                p.openInventory(inv);
            }
        }
    }
    @EventHandler
    public void onClick (InventoryClickEvent e) {
        if (e.getInventory().getTitle().contains("SERVERNEUSTART")) {
            Player p = (Player) e.getWhoClicked();
            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aJa")) {
                p.closeInventory();
                p.sendMessage("§aDu hast einen Serverneustart initialisiert!");
                if (!isrestarting) Shutdown();
                isrestarting = true;
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cNein")) {
                p.closeInventory();
            }
        }
    }

    public void Shutdown() {
        Bukkit.broadcastMessage(prefix + "§c§lDer Server muss neu gestartet werden.");
        Bukkit.broadcastMessage(prefix + "§cDiese Lobby wird gleich wieder verf\u00FCgbar sein.");
        new BukkitRunnable() {
            int time = 10;
            @Override
            public void run() {
                if (time == 10) Bukkit.broadcastMessage(prefix + "§7Neustart in §6§l" + time + " Sekunden§7.");
                if (time == 5) Bukkit.broadcastMessage(prefix + "§7Neustart in §6§l" + time + " Sekunden§7.");
                if (time <=3 && time !=1) Bukkit.broadcastMessage(prefix + "§7Neustart in §6§l" + time + " Sekunden§7.");
                if (time == 1) Bukkit.broadcastMessage(prefix + "§7Neustart in §6§l" + time + " Sekunde§7.");
                time--;
                if (time == 0) {
                    cancel();
                    Bukkit.shutdown();
                }

            }
        }.runTaskTimer(Lobby.getPlugin(), 0L, 20L);
    }
}
