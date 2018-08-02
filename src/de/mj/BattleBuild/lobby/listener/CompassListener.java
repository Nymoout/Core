package de.mj.BattleBuild.lobby.listener;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import cloud.timo.TimoCloud.api.objects.PlayerObject;
import cloud.timo.TimoCloud.api.objects.ServerGroupObject;
import cloud.timo.TimoCloud.api.objects.ServerObject;
import de.mj.BattleBuild.lobby.Lobby;
import de.mj.BattleBuild.lobby.utils.ItemCreator;
import de.mj.BattleBuild.lobby.utils.LocationsUtil;
import de.mj.BattleBuild.lobby.utils.Particle;
import de.mj.BattleBuild.lobby.utils.Data;
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
    public void onInteract(PlayerInteractEvent e) {

        Player p = e.getPlayer();
        if (p.isOp() == true) {
            e.setCancelled(false);
            return;
        } else {
            e.setCancelled(true);
        }
        if (e.getAction() == Action.RIGHT_CLICK_AIR | e.getAction() == Action.LEFT_CLICK_AIR
                | e.getAction() == Action.LEFT_CLICK_BLOCK | e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getMaterial() == Material.COMPASS) {

                Inventory inv = Bukkit.createInventory(null, 54, "§8\u00BB§7§lNavigator§8\u00AB");

                for (int i = 8; i >= 0; i--) {
                    if (lobby.getSettingsListener().design.containsKey(p)) {
                        inv.setItem(i, lobby.getItemCreator().CreateItemwithMaterial(Material.STAINED_GLASS_PANE,
                                lobby.getSettingsListener().design.get(p), 1, null, null));
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
                    if (lobby.getSettingsListener().design.containsKey(p)) {
                        inv.setItem(a, lobby.getItemCreator().CreateItemwithMaterial(Material.STAINED_GLASS_PANE,
                                lobby.getSettingsListener().design.get(p), 1, null, null));
                    } else {
                        inv.setItem(a,
                                lobby.getItemCreator().CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 0, 1, null, null));
                    }
                }

                e.getPlayer().openInventory(inv);
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
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(p.isOp() == true) {
            e.setCancelled(false);
            return;
        } else {
            e.setCancelled(true);
        }
        if (e.getInventory().getName().contains("§8\u00BB§7§lNavigator§8\u00AB")) {

            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§a§lSpawn")) {

                p.teleport(lobby.getLocationsUtil().getSpawn());
                p.sendMessage(lobby.getData().getPrefix() + "Du wurdest zum §6Server-Spawn §7teleportiert.");
                Particle particle = new Particle(EnumParticle.FLAME, p.getLocation().add(0,2.25,0), true, 0.25f, 0.25f, 0.25f, 0, 10000);
                particle.sendAll();



            } else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§6§lCityBuild")) {

                p.teleport(lobby.getLocationsUtil().getCitybuild());
                p.sendMessage(lobby.getData().getPrefix() + "Du wurdest zu §6Citybuild §7teleportiert.");
                Particle particle = new Particle(EnumParticle.FLAME, p.getLocation().add(0,2.25,0), true, 0.25f, 0.25f, 0.25f, 0, 10000);
                particle.sendAll();




            } else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§3§lBedWars")) {

                p.teleport(lobby.getLocationsUtil().getBedwars());
                p.sendMessage(lobby.getData().getPrefix() + "Du wurdest zu §6Bedwars §7teleportiert.");
                Particle particle = new Particle(EnumParticle.FLAME, p.getLocation().add(0,2.25,0), true, 0.25f, 0.25f, 0.25f, 0, 10000);
                particle.sendAll();




            } else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§9§lGunGame")) {


                p.teleport(lobby.getLocationsUtil().getGungame());
                p.sendMessage(lobby.getData().getPrefix() + "Du wurdest zu §6GunGame §7teleportiert.");
                Particle particle = new Particle(EnumParticle.FLAME, p.getLocation().add(0,2.25,0), true, 0.25f, 0.25f, 0.25f, 0, 10000);
                particle.sendAll();



            } else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§f§lSkyWars")) {

                p.teleport(lobby.getLocationsUtil().getSkywars());
                p.sendMessage(lobby.getData().getPrefix() + "Du wurdest zu §6SkyWars §7teleportiert.");
                Particle particle = new Particle(EnumParticle.FLAME, p.getLocation().add(0,2.25,0), true, 0.25f, 0.25f, 0.25f, 0, 10000);
                particle.sendAll();


            } else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§4§lT§f§lN§4§lT§f§l-§4§lRun")) {

                p.sendMessage(lobby.getData().getPrefix() + "Dieser Modus ist noch nicht verfügbar!");
                Particle particle = new Particle(EnumParticle.FLAME, p.getLocation().add(0,2.25,0), true, 0.25f, 0.25f, 0.25f, 0, 10000);
                particle.sendAll();

            } else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§8Coming Soon")) {

                p.sendMessage(lobby.getData().getPrefix() + "Dieser Modus ist noch nicht verfügbar!");


            } else {
                return;
            }
        }
    }
}
