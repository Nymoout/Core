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
import main.de.mj.bb.core.managers.ModuleManager;
import main.de.mj.bb.core.utils.Particle;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CompassListener implements Listener {

    private final CoreSpigot coreSpigot;

    private ModuleManager moduleManager;

    public CompassListener(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setListener(this);
        this.moduleManager = coreSpigot.getModuleManager();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent interactEvent) {
        if (interactEvent.getItem() == null) return;
        if (interactEvent.getItem().getType() == null) return;
        if (interactEvent.getItem().getType().equals(Material.AIR)) return;
        if (!(interactEvent.getAction().equals(Action.RIGHT_CLICK_AIR) || interactEvent.getAction().equals(Action.RIGHT_CLICK_BLOCK) || interactEvent.getAction().equals(Action.LEFT_CLICK_AIR) || interactEvent.getAction().equals(Action.LEFT_CLICK_BLOCK)))
            return;
        Player player = interactEvent.getPlayer();
        if (interactEvent.getMaterial().equals(Material.COMPASS)) {
            player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, 1);
            Inventory inv = Bukkit.createInventory(null, 54, "§8\u00BB§7§lNavigator§8\u00AB");

            for (int i = 8; i >= 0; i--) {
                if (coreSpigot.getModuleManager().getSettingsListener().getDesign().containsKey(player)) {
                    inv.setItem(i, moduleManager.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE,
                            coreSpigot.getModuleManager().getSettingsListener().getDesign().get(player), 1, null, null));
                } else {
                    inv.setItem(i,
                            moduleManager.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, 0, 1, null, null));
                }
            }
            ArrayList<String> BWlore = new ArrayList<>();
            if (TimoCloudAPI.getUniversalAPI().getServerGroup("BedWars4x4") != null) {
                ServerGroupObject serverGroupObjectBW4x4 = TimoCloudAPI.getUniversalAPI().getServerGroup("BedWars4x4");
                BWlore.add("§7Derzeit sind §a" + getOnlinePlayerCount(serverGroupObjectBW4x4) + "§7 Spieler");
                BWlore.add("§7online.");
            } else
                BWlore.add("§cDiese Server sind derzeit offline.");
            inv.setItem(11, moduleManager.getItemCreator().createItemWithMaterial(Material.BED, 0, 1, "§3§lBedWars", BWlore));

            ArrayList<String> CBlore = new ArrayList<>();
            if (TimoCloudAPI.getUniversalAPI().getServerGroup("CityBuild") != null) {
                ServerGroupObject serverGroupObjectCityBuild = TimoCloudAPI.getUniversalAPI().getServerGroup("CityBuild");
                CBlore.add("§7Derzeit sind §a" + getOnlinePlayerCount(serverGroupObjectCityBuild) + "§7 Spieler");
                CBlore.add("§7online.");
                CBlore.add("§7Verbinde dich optional mit");
                CBlore.add("§7➟ §6citybuild.battlebuild.net");
            } else
                CBlore.add("§cDiese Server sind derzeit offline.");
            inv.setItem(15,
                    moduleManager.getItemCreator().createItemWithMaterial(Material.DIAMOND_BLOCK, 0, 1, "§6§lCityBuild", CBlore));

            ArrayList<String> sPvPlore = new ArrayList<>();
            if (TimoCloudAPI.getUniversalAPI().getServerGroup("SkyPvP") != null) {
                ServerGroupObject serverGroupObjectCityBuild = TimoCloudAPI.getUniversalAPI().getServerGroup("SkyPvP");
                sPvPlore.add("§7Derzeit sind §a" + getOnlinePlayerCount(serverGroupObjectCityBuild) + "§7 Spieler");
                sPvPlore.add("§7online.");
                sPvPlore.add("§7Verbinde dich optional mit");
                sPvPlore.add("§7➟ §6skypvp.battlebuild.net");
            } else
                sPvPlore.add("§cDiese Server sind derzeit offline.");
            inv.setItem(19, moduleManager.getItemCreator().createItemWithMaterial(Material.GRASS, 0, 1, "§9§lSkyPvP", sPvPlore));

            ArrayList<String> lobbyLore = new ArrayList<>();
            ServerGroupObject serverGroupObjectLobby = TimoCloudAPI.getUniversalAPI().getServerGroup("Lobby");
            lobbyLore.add("§7Derzeit sind §a" + getOnlinePlayerCount(serverGroupObjectLobby) + "§7 Spieler");
            lobbyLore.add("§7online.");
            inv.setItem(22, moduleManager.getItemCreator().createItemWithMaterial(Material.NETHER_STAR, 0, 1, "§a§lSpawn", lobbyLore));

            ArrayList<String> SW8x1lore = new ArrayList<>();
            if (TimoCloudAPI.getUniversalAPI().getServerGroup("SkyWars8x1") != null) {
                ServerGroupObject serverGroupObjectSW8x1 = TimoCloudAPI.getUniversalAPI().getServerGroup("SkyWars8x1");
                SW8x1lore.add("§7Derzeit sind §a" + getOnlinePlayerCount(serverGroupObjectSW8x1) + "§7 Spieler");
                SW8x1lore.add("§7online.");
            } else
                SW8x1lore.add("§cDiese Server sind derzeit offline.");
            inv.setItem(25, moduleManager.getItemCreator().createItemWithMaterial(Material.IRON_SWORD, 0, 1, "§f§lSkyWars", SW8x1lore));
            inv.setItem(28,
                    moduleManager.getItemCreator().createItemWithMaterial(Material.TNT, 0, 1, "§4§lT§f§lN§4§lT§f§l-§4§lRun", null));
            ArrayList<String> VBlore = new ArrayList<>();
            if ( TimoCloudAPI.getUniversalAPI().getServer("Vorbauen") != null) {
                ServerObject serverObject = TimoCloudAPI.getUniversalAPI().getServer("Vorbauen");
                VBlore.add("§7Derzeit sind §a" + getOnlinePlayerCount(serverObject) + "§7 Spieler");
                VBlore.add("§7online.");
            }
            inv.setItem(34, moduleManager.getItemCreator().createItemWithMaterial(Material.DIAMOND_PICKAXE, 0, 1, "§b§lVorbauen", VBlore));
            for (int a = 53; a >= 45; a--) {
                if (coreSpigot.getModuleManager().getSettingsListener().getDesign().containsKey(player)) {
                    inv.setItem(a, moduleManager.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE,
                            coreSpigot.getModuleManager().getSettingsListener().getDesign().get(player), 1, null, null));
                } else {
                    inv.setItem(a,
                            moduleManager.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, 0, 1, null, null));
                }
            }
            interactEvent.getPlayer().openInventory(inv);
        }

    }

    private Integer getOnlinePlayerCount(ServerGroupObject serverGroupObject) {
        final Integer[] playerCount = {0};
        serverGroupObject.getServers().forEach(serverObject -> playerCount[0] += serverObject.getOnlinePlayerCount());
        return playerCount[0];
    }
    private Integer getOnlinePlayerCount(ServerObject serverObject) {
        return serverObject.getOnlinePlayerCount();
    }

    @EventHandler
    public void onClick(InventoryClickEvent clickEvent) {
        if (clickEvent.getClickedInventory() == null) return;
        if (clickEvent.getClickedInventory().getType() == null) return;
        if (clickEvent.getCurrentItem() == null) return;
        if (clickEvent.getCurrentItem().getType() == null) return;
        if (clickEvent.getCurrentItem().getType().equals(Material.AIR)) return;
        Player player = (Player) clickEvent.getWhoClicked();
        if (player.isOp()) {
            clickEvent.setCancelled(false);
            return;
        } else {
            clickEvent.setCancelled(true);
        }
        if (clickEvent.getInventory().getName().contains("§8\u00BB§7§lNavigator§8\u00AB")) {
            String displayName = clickEvent.getCurrentItem().getItemMeta().getDisplayName();
            if (displayName == null) return;
            if (!(displayName.contains("Spawn") || displayName.contains("CityBuild") || displayName.contains("BedWars") || displayName.contains("SkyPvP") || displayName.contains("SkyWars") || displayName.contains("§4§lT§f§lN§4§lT§f§l-§4§lRun") || displayName.contains("Vorbauen")))
                return;
            if (displayName.contains("§a§lSpawn")) {
                scheduler(player, moduleManager.getLocationsUtil().getSpawn().clone().add(0, 1, 0));
                player.sendMessage(moduleManager.getData().getPrefix() + "Du wurdest zum §6Server-Spawn §7teleportiert.");
                player.sendTitle("§8»§a§lSpawn§8«", "");
                Particle particle = new Particle(EnumParticle.FLAME, player.getLocation().add(0, 2.25, 0), true, 0.25f, 0.25f, 0.25f, 0, 10000);
                particle.sendAll();
                player.closeInventory();
            } else if (displayName.contains("§6§lCityBuild")) {
                scheduler(player, moduleManager.getLocationsUtil().getCitybuild().clone().add(0, 1, 0));
                player.sendMessage(moduleManager.getData().getPrefix() + "Du wurdest zu §6Citybuild §7teleportiert.");
                player.sendTitle("§8»§6§lCityBuild§8«", "");
                Particle particle = new Particle(EnumParticle.FLAME, player.getLocation().add(0, 2.25, 0), true, 0.25f, 0.25f, 0.25f, 0, 10000);
                particle.sendAll();
                player.closeInventory();
            } else if (displayName.contains("§3§lBedWars")) {
                scheduler(player, moduleManager.getLocationsUtil().getBedwars().clone().add(0, 1, 0));
                player.sendMessage(moduleManager.getData().getPrefix() + "Du wurdest zu §6Bedwars §7teleportiert.");
                player.sendTitle("§8»§3§lBedWars§8«", "");
                Particle particle = new Particle(EnumParticle.FLAME, player.getLocation().add(0, 2.25, 0), true, 0.25f, 0.25f, 0.25f, 0, 10000);
                particle.sendAll();
                player.closeInventory();
            } else if (displayName.contains("§9§lSkyPvP")) {
                scheduler(player, moduleManager.getLocationsUtil().getGungame().clone().add(0, 1, 0));
                player.sendMessage(moduleManager.getData().getPrefix() + "Du wurdest zu §6SkyPvP §7teleportiert.");
                player.sendTitle("§8»§9§lSkyPvP§8«", "");
                Particle particle = new Particle(EnumParticle.FLAME, player.getLocation().add(0, 2.25, 0), true, 0.25f, 0.25f, 0.25f, 0, 10000);
                particle.sendAll();
                player.closeInventory();
            } else if (displayName.contains("§f§lSkyWars")) {
                scheduler(player, moduleManager.getLocationsUtil().getSkywars().clone().add(0, 1, 0));
                player.sendMessage(moduleManager.getData().getPrefix() + "Du wurdest zu §6SkyWars §7teleportiert.");
                player.sendTitle("§8»§f§lSkyWars§8«", "");
                Particle particle = new Particle(EnumParticle.FLAME, player.getLocation().add(0, 2.25, 0), true, 0.25f, 0.25f, 0.25f, 0, 10000);
                particle.sendAll();
                player.closeInventory();
            } else if (displayName.contains("Vorbauen")) {
                System.out.println("Vorbauen");
                scheduler(player, moduleManager.getLocationsUtil().getVorbauen().clone().add(0, 1, 0));
                player.sendMessage(moduleManager.getData().getPrefix() + "Du wurdest zu §6Vorbauen §7teleportiert.");
                player.sendTitle("§8»§b§lVorbauen§8«", "");
                Particle particle = new Particle(EnumParticle.FLAME, player.getLocation().add(0, 2.25, 0), true, 0.25f, 0.25f, 0.25f, 0, 10000);
                particle.sendAll();
                player.closeInventory();
            } else if (displayName.contains("§4§lT§f§lN§4§lT§f§l-§4§lRun")) {
                System.out.println("TNT");
                player.sendMessage(moduleManager.getData().getPrefix() + "Dieser Modus ist noch nicht verfügbar!");
                Particle particle = new Particle(EnumParticle.FLAME, player.getLocation().add(0, 2.25, 0), true, 0.25f, 0.25f, 0.25f, 0, 10000);
                particle.sendAll();
            }
        }
    }

    private void scheduler(Player player, Location location) {
        new BukkitRunnable() {
            int timer = 1;

            public void run() {
                if (timer == 1) {
                    player.setVelocity(new Vector(0, 1, 0));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1));
                }
                if (timer == 0) {
                    player.teleport(location);
                    player.removePotionEffect(PotionEffectType.BLINDNESS);
                    cancel();
                }
                timer--;
            }
        }.runTaskTimer(coreSpigot, 0L, 20L);
    }
}
