/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.lobby.listener;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import cloud.timo.TimoCloud.api.objects.PlayerObject;
import cloud.timo.TimoCloud.api.objects.ServerGroupObject;
import cloud.timo.TimoCloud.api.objects.ServerObject;
import de.mj.BattleBuild.lobby.Lobby;
import de.mj.BattleBuild.lobby.utils.Particle;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class CompassListener implements Listener {

    private final Lobby lobby;

    public CompassListener(Lobby lobby) {
        this.lobby = lobby;
        lobby.setListener(this);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent interactEvent) {

        Player player = interactEvent.getPlayer();
        if (player.isOp() == true) {
            interactEvent.setCancelled(false);
            return;
        } else {
            interactEvent.setCancelled(true);
        }
        if (interactEvent.getAction() == Action.RIGHT_CLICK_AIR | interactEvent.getAction() == Action.LEFT_CLICK_AIR
                | interactEvent.getAction() == Action.LEFT_CLICK_BLOCK | interactEvent.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (interactEvent.getMaterial() == Material.COMPASS) {

                Inventory inv = Bukkit.createInventory(null, 54, "§8\u00BB§7§lNavigator§8\u00AB");

                for (int i = 8; i >= 0; i--) {
                    if (lobby.getSettingsListener().design.containsKey(player)) {
                        inv.setItem(i, lobby.getItemCreator().CreateItemwithMaterial(Material.STAINED_GLASS_PANE,
                                lobby.getSettingsListener().design.get(player), 1, null, null));
                    } else {
                        inv.setItem(i,
                                lobby.getItemCreator().CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 0, 1, null, null));
                    }
                }
                ServerGroupObject serverGroupObjectBW4x4 = TimoCloudAPI.getUniversalAPI().getServerGroup("BedWars4x4");
                ArrayList<String> BWlore = new ArrayList<>();
                BWlore.add("§7Derzeit sind §a" + getOnlinePlayerCount(serverGroupObjectBW4x4) + "§7 Spieler");
                BWlore.add("§7online.");
                inv.setItem(11, lobby.getItemCreator().CreateItemwithMaterial(Material.BED, 0, 1, "§3§lBedWars", BWlore));

                ServerGroupObject serverGroupObjectCityBuild = TimoCloudAPI.getUniversalAPI().getServerGroup("CityBuild");
                ArrayList<String> CBlore = new ArrayList<>();
                CBlore.add("§7Derzeit sind §a" + getOnlinePlayerCount(serverGroupObjectCityBuild) + "§7 Spieler");
                CBlore.add("§7online.");
                inv.setItem(15,
                        lobby.getItemCreator().CreateItemwithMaterial(Material.DIAMOND_BLOCK, 0, 1, "§6§lCityBuild", CBlore));

                inv.setItem(19, lobby.getItemCreator().CreateItemwithMaterial(Material.IRON_AXE, 0, 1, "§9§lGunGame", null));
                inv.setItem(22, lobby.getItemCreator().CreateItemwithMaterial(Material.NETHER_STAR, 0, 1, "§a§lSpawn", null));

                ServerGroupObject serverGroupObjectSW8x1 = TimoCloudAPI.getUniversalAPI().getServerGroup("SkyWars8x1");
                ArrayList<String> SW8x1lore = new ArrayList<>();
                SW8x1lore.add("§7Derzeit sind §a" + getOnlinePlayerCount(serverGroupObjectSW8x1) + "§7 Spieler");
                SW8x1lore.add("§7online.");
                inv.setItem(25, lobby.getItemCreator().CreateItemwithMaterial(Material.IRON_SWORD, 0, 1, "§f§lSkyWars", SW8x1lore));
                inv.setItem(28,
                        lobby.getItemCreator().CreateItemwithMaterial(Material.TNT, 0, 1, "§4§lT§f§lN§4§lT§f§l-§4§lRun", null));
                inv.setItem(34, lobby.getItemCreator().CreateItemwithMaterial(Material.SIGN, 0, 1, "§8§lComing Soon", null));

                for (int a = 53; a >= 45; a--) {
                    if (lobby.getSettingsListener().design.containsKey(player)) {
                        inv.setItem(a, lobby.getItemCreator().CreateItemwithMaterial(Material.STAINED_GLASS_PANE,
                                lobby.getSettingsListener().design.get(player), 1, null, null));
                    } else {
                        inv.setItem(a,
                                lobby.getItemCreator().CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 0, 1, null, null));
                    }
                }

                interactEvent.getPlayer().openInventory(inv);
            }
        }

    }

    public Integer getOnlinePlayerCount(ServerGroupObject serverGroupObject) {
        Integer playerCount = 0;
        for (ServerObject serverObject : serverGroupObject.getServers()) {
            for (PlayerObject playerObject : serverObject.getOnlinePlayers()) {
                playerCount++;
            }
        }
        return playerCount;
    }

    @EventHandler
    public void onClick(InventoryClickEvent clickEvent) {
        Player player = (Player) clickEvent.getWhoClicked();
        if (player.isOp() == true) {
            clickEvent.setCancelled(false);
            return;
        } else {
            clickEvent.setCancelled(true);
        }
        if (clickEvent.getInventory().getName().contains("§8\u00BB§7§lNavigator§8\u00AB")) {

            if (clickEvent.getCurrentItem().getItemMeta().getDisplayName().contains("§a§lSpawn")) {

                player.teleport(lobby.getLocationsUtil().getSpawn());
                player.sendMessage(lobby.getData().getPrefix() + "Du wurdest zum §6Server-Spawn §7teleportiert.");
                Particle particle = new Particle(EnumParticle.FLAME, player.getLocation().add(0, 2.25, 0), true, 0.25f, 0.25f, 0.25f, 0, 10000);
                particle.sendAll();


            } else if (clickEvent.getCurrentItem().getItemMeta().getDisplayName().contains("§6§lCityBuild")) {

                player.teleport(lobby.getLocationsUtil().getCitybuild());
                player.sendMessage(lobby.getData().getPrefix() + "Du wurdest zu §6Citybuild §7teleportiert.");
                Particle particle = new Particle(EnumParticle.FLAME, player.getLocation().add(0, 2.25, 0), true, 0.25f, 0.25f, 0.25f, 0, 10000);
                particle.sendAll();


            } else if (clickEvent.getCurrentItem().getItemMeta().getDisplayName().contains("§3§lBedWars")) {

                player.teleport(lobby.getLocationsUtil().getBedwars());
                player.sendMessage(lobby.getData().getPrefix() + "Du wurdest zu §6Bedwars §7teleportiert.");
                Particle particle = new Particle(EnumParticle.FLAME, player.getLocation().add(0, 2.25, 0), true, 0.25f, 0.25f, 0.25f, 0, 10000);
                particle.sendAll();


            } else if (clickEvent.getCurrentItem().getItemMeta().getDisplayName().contains("§9§lGunGame")) {


                player.teleport(lobby.getLocationsUtil().getGungame());
                player.sendMessage(lobby.getData().getPrefix() + "Du wurdest zu §6GunGame §7teleportiert.");
                Particle particle = new Particle(EnumParticle.FLAME, player.getLocation().add(0, 2.25, 0), true, 0.25f, 0.25f, 0.25f, 0, 10000);
                particle.sendAll();


            } else if (clickEvent.getCurrentItem().getItemMeta().getDisplayName().contains("§f§lSkyWars")) {

                player.teleport(lobby.getLocationsUtil().getSkywars());
                player.sendMessage(lobby.getData().getPrefix() + "Du wurdest zu §6SkyWars §7teleportiert.");
                Particle particle = new Particle(EnumParticle.FLAME, player.getLocation().add(0, 2.25, 0), true, 0.25f, 0.25f, 0.25f, 0, 10000);
                particle.sendAll();


            } else if (clickEvent.getCurrentItem().getItemMeta().getDisplayName().contains("§4§lT§f§lN§4§lT§f§l-§4§lRun")) {

                player.sendMessage(lobby.getData().getPrefix() + "Dieser Modus ist noch nicht verfügbar!");
                Particle particle = new Particle(EnumParticle.FLAME, player.getLocation().add(0, 2.25, 0), true, 0.25f, 0.25f, 0.25f, 0, 10000);
                particle.sendAll();

            } else if (clickEvent.getCurrentItem().getItemMeta().getDisplayName().contains("§8Coming Soon")) {

                player.sendMessage(lobby.getData().getPrefix() + "Dieser Modus ist noch nicht verfügbar!");


            } else {
                return;
            }
        }
    }
}
