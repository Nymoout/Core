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

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            Inventory inv = Bukkit.createInventory(null, 45, "§8\u00BB§7§lNavigator§8\u00AB");
            for (int i = inv.getSize() - 1; i >= 0; i--) {
                inv.setItem(i, coreSpigot.getModuleManager().getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, coreSpigot.getModuleManager().getSettingsListener().getDesign().get(player), 1, " "));
            }
            inv.setItem(20, coreSpigot.getModuleManager().getItemCreator().createItemWithMaterial(Material.IRON_SWORD, 0, 1, "§9§lMinigames"));
            inv.setItem(24, coreSpigot.getModuleManager().getItemCreator().createItemWithMaterial(Material.IRON_PICKAXE, 0, 1, "§2§lBuildServer"));
            List<String> BWlore = new ArrayList<>();
            ServerGroupObject serverGroupObjectBW4x4 = TimoCloudAPI.getUniversalAPI().getServerGroup("BedWars4x4");
            if (TimoCloudAPI.getUniversalAPI().getServerGroup("BedWars4x4") != null) {
                BWlore.add("§7Derzeit sind §a" + getOnlinePlayerCount(serverGroupObjectBW4x4) + "§7 Spieler");
                BWlore.add("§7online.");
            } else
                BWlore.add("§cDiese Server sind derzeit offline.");
            inv.setItem(11,
                    moduleManager.getItemCreator().createItemWithSkull("e08f40f53f1c27b36b5721c1474d6da695c7d4e0d6d0ffb343b9a56ca3c515fe", getOnlinePlayerCount(serverGroupObjectBW4x4), "§3BedWars", BWlore, true));

            List<String> CBlore = new ArrayList<>();
            ServerGroupObject serverGroupObjectCityBuild = TimoCloudAPI.getUniversalAPI().getServerGroup("CityBuild");
            if (TimoCloudAPI.getUniversalAPI().getServerGroup("CityBuild") != null) {
                CBlore.add("§7Derzeit sind §a" + getOnlinePlayerCount(serverGroupObjectCityBuild) + "§7 Spieler");
                CBlore.add("§7online.");
                CBlore.add("§7Verbinde dich optional mit");
                CBlore.add("§7➟ §6citybuild.battlebuild.net");
            } else
                CBlore.add("§cDiese Server sind derzeit offline.");
            inv.setItem(23,
                    moduleManager.getItemCreator().createItemWithSkull("b25b27ce62ca88743840a95d1c39868f43ca60696a84f564fbd7dda259be00fe", getOnlinePlayerCount(serverGroupObjectCityBuild), "§aCityBuild", CBlore, true));

            List<String> sPvPlore = new ArrayList<>();
            ServerGroupObject serverGroupObjectSkyPvP = TimoCloudAPI.getUniversalAPI().getServerGroup("SkyPvP");
            if (TimoCloudAPI.getUniversalAPI().getServerGroup("SkyPvP") != null) {
                sPvPlore.add("§7Derzeit sind §a" + getOnlinePlayerCount(serverGroupObjectSkyPvP) + "§7 Spieler");
                sPvPlore.add("§7online.");
                sPvPlore.add("§7Verbinde dich optional mit");
                sPvPlore.add("§7➟ §6skypvp.battlebuild.net");
            } else
                sPvPlore.add("§cDiese Server sind derzeit offline.");
            inv.setItem(19, moduleManager.getItemCreator().createItemWithSkull("b5a93f17392806026fcfb2db7718da246c881895b30ecb1284d83f987eb8", getOnlinePlayerCount(serverGroupObjectSkyPvP), "§3SkyPvP", sPvPlore, true));

            List<String> lobbyLore = new ArrayList<>();
            ServerGroupObject serverGroupObjectLobby = TimoCloudAPI.getUniversalAPI().getServerGroup("Lobby");
            lobbyLore.add("§7Derzeit sind §a" + getOnlinePlayerCount(serverGroupObjectLobby) + "§7 Spieler");
            lobbyLore.add("§7online.");
            inv.setItem(31, moduleManager.getItemCreator().createItemWithSkull("4ef4874eb050a3d3e46f69ae40c3b59a3596f71664a0db6ca600d5c67f47e4", getOnlinePlayerCount(serverGroupObjectLobby), "§f§lSpawn", lobbyLore, true));

            List<String> SW8x1lore = new ArrayList<>();
            ServerGroupObject serverGroupObjectSW8x1 = TimoCloudAPI.getUniversalAPI().getServerGroup("SkyWars8x1");
            if (TimoCloudAPI.getUniversalAPI().getServerGroup("SkyWars8x1") != null) {
                SW8x1lore.add("§7Derzeit sind §a" + getOnlinePlayerCount(serverGroupObjectSW8x1) + "§7 Spieler");
                SW8x1lore.add("§7online.");
            } else
                SW8x1lore.add("§cDiese Server sind derzeit offline.");
            inv.setItem(21, moduleManager.getItemCreator().createItemWithSkull("cd6d133e1dff1420f5ec80cd1f3e7ba222fc8c33fa044931f65e3bc6973a48", getOnlinePlayerCount(serverGroupObjectSW8x1), "§3SkyWars", SW8x1lore, true));
            List<String> VBlore = new ArrayList<>();
            ServerObject serverObject = TimoCloudAPI.getUniversalAPI().getServer("Vorbauen");
            if ( TimoCloudAPI.getUniversalAPI().getServer("Vorbauen") != null) {
                VBlore.add("§7Derzeit sind §a" + getOnlinePlayerCount(serverObject) + "§7 Spieler");
                VBlore.add("§7online.");
                VBlore.add("§7Verbinde dich optional mit");
                VBlore.add("§7➟ §6vorbauen.battlebuild.net");
            }
            inv.setItem(25, moduleManager.getItemCreator().createItemWithSkull("27f545a12725b7a38ee13f32b4dd12cc879732d17b6484450ef512aa2a5488", getOnlinePlayerCount(serverObject), "§aVorbauen", VBlore, true));
            if (player.hasPermission("player.team")) {
                inv.setItem(44, moduleManager.getItemCreator().createItemWithSkull("16c0de7dd65387482c3ab5d3cb4eaa921a6aeeddf38e3df20e04fd961faef43", getOnlinePlayerCount(TimoCloudAPI.getUniversalAPI().getServerGroup("BauServer")), "§cBauServer", true));
                inv.setItem(36, moduleManager.getItemCreator().createItemWithSkull("84ac57b2a2d5c6321895c6f8e7fbc67262ca097243918aa594cb2458a7040", getOnlinePlayerCount(TimoCloudAPI.getUniversalAPI().getServerGroup("PluginTestServer")), "§cPluginTestServer", true));
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
            if (!(displayName.contains("Spawn") || displayName.contains("CityBuild") || displayName.contains("BedWars") || displayName.contains("SkyPvP") || displayName.contains("SkyWars") || displayName.contains("Vorbauen") || displayName.contains("BauServer") || displayName.contains("PluginTestServer")))
                return;
            if (displayName.contains("§f§lSpawn")) {
                scheduler(player, moduleManager.getLocationsUtil().getSpawn().clone().add(0, 1, 0));
                player.sendMessage(moduleManager.getData().getPrefix() + "Du wurdest zum §fServer-Spawn §7teleportiert.");
                player.sendTitle("§8»§f§lSpawn§8«", "");
                Particle particle = new Particle(EnumParticle.FLAME, player.getLocation().add(0, 2.25, 0), true, 0.25f, 0.25f, 0.25f, 0, 10000);
                particle.sendAll();
                player.closeInventory();
            } else if (displayName.contains("§aCityBuild")) {
                scheduler(player, moduleManager.getLocationsUtil().getCitybuild().clone().add(0, 1, 0));
                player.sendMessage(moduleManager.getData().getPrefix() + "Du wurdest zu §aCitybuild §7teleportiert.");
                player.sendTitle("§8»§aCityBuild§8«", "");
                Particle particle = new Particle(EnumParticle.FLAME, player.getLocation().add(0, 2.25, 0), true, 0.25f, 0.25f, 0.25f, 0, 10000);
                particle.sendAll();
                player.closeInventory();
            } else if (displayName.contains("§3BedWars")) {
                scheduler(player, moduleManager.getLocationsUtil().getBedwars().clone().add(0, 1, 0));
                player.sendMessage(moduleManager.getData().getPrefix() + "Du wurdest zu §3Bedwars §7teleportiert.");
                player.sendTitle("§8»§3BedWars§8«", "");
                Particle particle = new Particle(EnumParticle.FLAME, player.getLocation().add(0, 2.25, 0), true, 0.25f, 0.25f, 0.25f, 0, 10000);
                particle.sendAll();
                player.closeInventory();
            } else if (displayName.contains("§3SkyPvP")) {
                scheduler(player, moduleManager.getLocationsUtil().getGungame().clone().add(0, 1, 0));
                player.sendMessage(moduleManager.getData().getPrefix() + "Du wurdest zu §3SkyPvP §7teleportiert.");
                player.sendTitle("§8»§3SkyPvP§8«", "");
                Particle particle = new Particle(EnumParticle.FLAME, player.getLocation().add(0, 2.25, 0), true, 0.25f, 0.25f, 0.25f, 0, 10000);
                particle.sendAll();
                player.closeInventory();
            } else if (displayName.contains("§3SkyWars")) {
                scheduler(player, moduleManager.getLocationsUtil().getSkywars().clone().add(0, 1, 0));
                player.sendMessage(moduleManager.getData().getPrefix() + "Du wurdest zu §3SkyWars §7teleportiert.");
                player.sendTitle("§8»§3SkyWars§8«", "");
                Particle particle = new Particle(EnumParticle.FLAME, player.getLocation().add(0, 2.25, 0), true, 0.25f, 0.25f, 0.25f, 0, 10000);
                particle.sendAll();
                player.closeInventory();
            } else if (displayName.contains("Vorbauen")) {
                scheduler(player, moduleManager.getLocationsUtil().getVorbauen().clone().add(0, 1, 0));
                player.sendMessage(moduleManager.getData().getPrefix() + "Du wurdest zu §aVorbauen §7teleportiert.");
                player.sendTitle("§8»§aVorbauen§8«", "");
                Particle particle = new Particle(EnumParticle.FLAME, player.getLocation().add(0, 2.25, 0), true, 0.25f, 0.25f, 0.25f, 0, 10000);
                particle.sendAll();
                player.closeInventory();
            } else if (displayName.contains("BauServer"))
                sendToServer(player, "BauServer");
            else if (displayName.contains("PluginTestServer"))
                sendToServer(player, "PluginTestServer");
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

    private void sendToServer(Player player, String server) {
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
}
