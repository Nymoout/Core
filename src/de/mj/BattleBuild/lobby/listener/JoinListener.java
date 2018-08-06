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

    public JoinListener(Lobby lobby) {
        this.lobby = lobby;
        lobby.setListener(this);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent joinEvent) {
        Player player = joinEvent.getPlayer();
        player.teleport(lobby.getLocationsUtil().getSpawn());
        System.out.println(lobby.getLocationsUtil().getSpawn().toString());
        if (!player.hasPlayedBefore()) {
            lobby.getSettingsListener().ridestate.add(player);
            lobby.getSettingsListener().color.put(player, "6");
            lobby.getSettingsListener().sclan.add(player);
            lobby.getSettingsListener().scoins.add(player);
            lobby.getSettingsListener().sfriends.add(player);
            lobby.getSettingsListener().srang.add(player);
            lobby.getSettingsListener().sserver.add(player);
            lobby.getSettingsListener().jumppads.add(player);
        }

        player.setGameMode(GameMode.ADVENTURE);
        lobby.getActionbarTimer().action.put(player, false);
        joinEvent.setJoinMessage(null);
        try {
            lobby.getSettingsAPI().createPlayer(player);
            lobby.getSettingsAPI().createScorePlayer(player);
            lobby.getSettingsAPI().getColor(player);
            lobby.getSettingsAPI().getSilent(player);
            lobby.getSettingsAPI().getRide(player);
            lobby.getSettingsAPI().getFriends(player);
            lobby.getSettingsAPI().getRang(player);
            lobby.getSettingsAPI().getServer(player);
            lobby.getSettingsAPI().getClan(player);
            lobby.getSettingsAPI().getCoins(player);
            lobby.getSettingsAPI().getRealTime(player);
            lobby.getSettingsAPI().getWeather(player);
            lobby.getSettingsAPI().getDoubleJump(player);
            lobby.getSettingsAPI().getWjump(player);
            lobby.getSettingsAPI().getJumPlate(player);
            lobby.getSettingsAPI().getTime(player);
        } catch (Exception e1) {
        }
        new BukkitRunnable() {
            int i = 1;

            @Override
            public void run() {
                if (i > 0) {
                    i--;
                } else {
                    if (lobby.getSettingsListener().sweather.contains(player)) {
                        player.setPlayerWeather(WeatherType.CLEAR);
                    } else {
                        player.setPlayerWeather(WeatherType.DOWNFALL);
                    }
                }
            }
        }.runTaskTimer(lobby, 0L, 20L * 5);

        player.getInventory().clear();
        lobby.getTabList().setPrefix(player);

        player.getInventory().setItem(4,
                lobby.getItemCreator().CreateItemwithMaterial(Material.COMPASS, 0, 1, "§8\u00BB§7§lNavigator§8\u00AB", null));
        player.getInventory().setItem(1, lobby.getItemCreator().CreateItemwithMaterial(Material.REDSTONE_COMPARATOR, 0, 1,
                "§8\u00BB§6§lEinstellungen§8\u00AB", null));
        player.getInventory().setItem(7,
                lobby.getItemCreator().CreateItemwithMaterial(Material.NETHER_STAR, 0, 1, "§8\u00BB§f§lLobby-Switcher§8\u00AB", null));
        player.getInventory().setItem(0, lobby.getItemCreator().CreateItemwithMaterial(Material.ARMOR_STAND, 0, 1, "§8\u00BB§3§lDein Minion§8\u00AB", null));

        ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("§8\u00BB§9§lDein Profil§8\u00AB");
        is.setItemMeta(im);
        SkullMeta sm = (SkullMeta) is.getItemMeta();
        sm.setOwner(player.getName());
        is.setItemMeta(sm);
        player.getInventory().setItem(8, is);

        lobby.getScoreboardManager().setBoardLOBBY(player);

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
