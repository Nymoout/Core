/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.core.listener;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import cloud.timo.TimoCloud.api.objects.PlayerObject;
import de.mj.BattleBuild.core.Core;
import de.mj.BattleBuild.core.utils.ServerType;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayerManager;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
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

    private final Core core;

    public JoinListener(Core core) {
        this.core = core;
        core.setListener(this);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent joinEvent) {
        Player player = joinEvent.getPlayer();
        System.out.println(core.getServerManager().getServerType());
        if (core.getServerManager().getServerType().equals(ServerType.LOBBY)) {
            player.teleport(core.getServerManager().getLocationsUtil().getSpawn());
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
            joinEvent.setJoinMessage(null);
            try {
                core.getServerManager().getSettingsAPI().createPlayer(player);
                core.getServerManager().getSettingsAPI().createScorePlayer(player);
                core.getServerManager().getSettingsAPI().getColor(player);
                core.getServerManager().getSettingsAPI().getSilent(player);
                core.getServerManager().getSettingsAPI().getRide(player);
                core.getServerManager().getSettingsAPI().getFriends(player);
                core.getServerManager().getSettingsAPI().getRang(player);
                core.getServerManager().getSettingsAPI().getServer(player);
                core.getServerManager().getSettingsAPI().getClan(player);
                core.getServerManager().getSettingsAPI().getCoins(player);
                core.getServerManager().getSettingsAPI().getRealTime(player);
                core.getServerManager().getSettingsAPI().getWeather(player);
                core.getServerManager().getSettingsAPI().getDoubleJump(player);
                core.getServerManager().getSettingsAPI().getWjump(player);
                core.getServerManager().getSettingsAPI().getJumPlate(player);
                core.getServerManager().getSettingsAPI().getTime(player);
            } catch (Exception e1) {
                e1.printStackTrace();
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
            }.runTaskTimer(core, 0L, 20L * 5);

            player.getInventory().clear();

            player.getInventory().setItem(4,
                    core.getServerManager().getItemCreator().CreateItemwithMaterial(Material.COMPASS, 0, 1, "§8\u00BB§7§lNavigator§8\u00AB", null));
            player.getInventory().setItem(1, core.getServerManager().getItemCreator().CreateItemwithMaterial(Material.REDSTONE_COMPARATOR, 0, 1,
                    "§8\u00BB§6§lEinstellungen§8\u00AB", null));
            player.getInventory().setItem(7,
                    core.getServerManager().getItemCreator().CreateItemwithMaterial(Material.NETHER_STAR, 0, 1, "§8\u00BB§f§lLobby-Switcher§8\u00AB", null));
            player.getInventory().setItem(0, core.getServerManager().getItemCreator().CreateItemwithMaterial(Material.ARMOR_STAND, 0, 1, "§8\u00BB§3§lDein Minion§8\u00AB", null));

            ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName("§8\u00BB§9§lDein Profil§8\u00AB");
            is.setItemMeta(im);
            SkullMeta sm = (SkullMeta) is.getItemMeta();
            sm.setOwner(player.getName());
            is.setItemMeta(sm);
            player.getInventory().setItem(8, is);

            core.getServerManager().getScoreboardManager().setScoreboard(player);

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
        } else if (core.getServerManager().getServerType().equals(ServerType.DEFAULT)) {
            core.getServerManager().getTabList().setPrefix(player);
        }
        waitMySQL(player, core.getServerManager().getServerType());
        core.getServerManager().getTabList().setPrefix(player);
    }

    private void waitMySQL(Player player, ServerType serverType) {
        core.getServerManager().getSchedulerSaver().createScheduler(new BukkitRunnable() {
            @Override
            public void run() {
                if (serverType.equals(ServerType.LOBBY)) {
                    if (core.getServerManager().getServerStatsAPI().getMaxServer().containsKey(player)) {
                        IChatBaseComponent icb = ChatSerializer
                                .a("{\"text\":\"§2Du spielst öfters auf dem Server\",\"extra\":[{\"text\":\"§b "
                                        + core.getServerManager().getServerStatsAPI().getMaxServer().get(player)
                                        + ". §2Wenn §2du §2dich §2mit §2diesem §2verbinden §2willst, §2dann §2klick §2einfach §2hier!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§aKlicke hier um diesen Server zu betreten!\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gotoserver " + core.getServerManager().getServerStatsAPI().getMaxServer().get(player) + "\"}}]}");
                        PacketPlayOutChat packet = new PacketPlayOutChat(icb);
                        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                        cancel();
                    } else {
                        core.getServerManager().getServerStatsAPI().getMaxPlayed(player);
                    }
                } else {
                    if (!core.getServerManager().getServerStatsAPI().getPlayed().containsKey(player)) {
                        core.getServerManager().getServerStatsAPI().createPlayer(player);
                        core.getServerManager().getServerStatsAPI().getPlayed(player);
                    } else {
                        core.getServerManager().getServerStatsAPI().updatePlayed(player, core.getServerManager().getServerStatsAPI().getPlayedInt(player, Bukkit.getServerName()) + 1, Bukkit.getServerName());
                        cancel();
                    }
                }
            }
        }.runTaskTimer(core, 0L, 20L));
    }
}
