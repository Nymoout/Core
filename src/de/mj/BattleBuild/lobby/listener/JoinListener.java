/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.lobby.listener;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import cloud.timo.TimoCloud.api.objects.PlayerObject;
import de.mj.BattleBuild.lobby.Lobby;
import de.mj.BattleBuild.lobby.mySQL.SettingsAPI;
import de.mj.BattleBuild.lobby.utils.ActionbarTimer;
import de.mj.BattleBuild.lobby.utils.ServerManager;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayerManager;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.WeatherType;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class JoinListener implements Listener {

    private final Lobby lobby;
    private SettingsListener settingsListener;
    private SettingsAPI settingsAPI;
    private ServerManager serverManager;

    public JoinListener(Lobby lobby) {
        this.lobby = lobby;
        lobby.setListener(this);
        settingsListener = lobby.getServerManager().getSettingsListener();
        settingsAPI = lobby.getServerManager().getSettingsAPI();
        serverManager = lobby.getServerManager();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent joinEvent) {
        Player player = joinEvent.getPlayer();
        player.teleport(serverManager.getLocationsUtil().getSpawn());
        System.out.println(serverManager.getLocationsUtil().getSpawn().toString());
        if (!player.hasPlayedBefore()) {
            SettingsListener.ridestate.add(player);
            SettingsListener.color.put(player, "6");
            SettingsListener.sclan.add(player);
            SettingsListener.scoins.add(player);
            SettingsListener.sfriends.add(player);
            SettingsListener.srang.add(player);
            SettingsListener.sserver.add(player);
            SettingsListener.jumppads.add(player);
        }

        player.setGameMode(GameMode.ADVENTURE);
        ActionbarTimer.action.put(player, false);
        joinEvent.setJoinMessage(null);
        try {
            settingsAPI.createPlayer(player);
            settingsAPI.createScorePlayer(player);
            settingsAPI.getColor(player);
            settingsAPI.getSilent(player);
            settingsAPI.getRide(player);
            settingsAPI.getFriends(player);
            settingsAPI.getRang(player);
            settingsAPI.getServer(player);
            settingsAPI.getClan(player);
            settingsAPI.getCoins(player);
            settingsAPI.getRealTime(player);
            settingsAPI.getWeather(player);
            settingsAPI.getDoubleJump(player);
            settingsAPI.getWjump(player);
            settingsAPI.getJumPlate(player);
            settingsAPI.getTime(player);
        } catch (Exception e1) {
        }
        new BukkitRunnable() {
            int i = 1;

            @Override
            public void run() {
                if (i > 0) {
                    i--;
                } else {
                    if (SettingsListener.sweather.contains(player)) {
                        player.setPlayerWeather(WeatherType.CLEAR);
                    } else {
                        player.setPlayerWeather(WeatherType.DOWNFALL);
                    }
                }
            }
        }.runTaskTimer(lobby, 0L, 20L * 5);

        player.getInventory().clear();
        lobby.getServerManager().getTabList().setPrefix(player);

        player.getInventory().setItem(4,
                lobby.getServerManager().getItemCreator().CreateItemwithMaterial(Material.COMPASS, 0, 1, "§8\u00BB§7§lNavigator§8\u00AB", null));
        player.getInventory().setItem(1, lobby.getServerManager().getItemCreator().CreateItemwithMaterial(Material.REDSTONE_COMPARATOR, 0, 1,
                "§8\u00BB§6§lEinstellungen§8\u00AB", null));
        player.getInventory().setItem(7,
                lobby.getServerManager().getItemCreator().CreateItemwithMaterial(Material.NETHER_STAR, 0, 1, "§8\u00BB§f§lLobby-Switcher§8\u00AB", null));
        player.getInventory().setItem(0, lobby.getServerManager().getItemCreator().CreateItemwithMaterial(Material.ARMOR_STAND, 0, 1, "§8\u00BB§3§lDein Minion§8\u00AB", null));

        ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("§8\u00BB§9§lDein Profil§8\u00AB");
        is.setItemMeta(im);
        SkullMeta sm = (SkullMeta) is.getItemMeta();
        sm.setOwner(player.getName());
        is.setItemMeta(sm);
        player.getInventory().setItem(8, is);

        lobby.getServerManager().getScoreboardManager().setBoardLOBBY(player);

        ArrayList<String> friends = new ArrayList<String>();
        PAFPlayer pafp = PAFPlayerManager.getInstance().getPlayer(player.getUniqueId());
        for (PlayerObject all : TimoCloudAPI.getUniversalAPI().getProxy("Proxy").getOnlinePlayers()) {
            for (PAFPlayer fr : pafp.getFriends()) {
                if (fr.getUniqueId().equals(all.getUuid())) {
                    friends.add(all.getName());
                }
            }
        }
        if (friends.size() == 1) {
            IChatBaseComponent icb = ChatSerializer
                    .a("{\"text\":\"§6Derzeit ist folgender deiner Freunde online:\",\"extra\":[{\"text\":\"§a§l "
                            + friends
                            + "\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§9Klicke hier für mehr Informationen!\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/friendsgui\"}}]}");
            PacketPlayOutChat packet = new PacketPlayOutChat(icb);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }
        if (friends.size() > 1) {
            IChatBaseComponent icb = ChatSerializer
                    .a("{\"text\":\"§6Derzeit sind folgende deiner Freunde online:\",\"extra\":[{\"text\":\"§a§l "
                            + friends
                            + "\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§9Klicke hier für mehr Informationen!\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/friendsgui\"}}]}");
            PacketPlayOutChat packet = new PacketPlayOutChat(icb);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }
    }
}
