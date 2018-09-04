/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.core.listener;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import cloud.timo.TimoCloud.api.objects.PlayerObject;
import de.mj.BattleBuild.core.CoreSpigot;
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
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class JoinListener implements Listener {

    private final CoreSpigot coreSpigot;

    public JoinListener(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setListener(this);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent joinEvent) {
        Player player = joinEvent.getPlayer();
        System.out.println(coreSpigot.getServerManager().getServerType());
        if (coreSpigot.getServerManager().getServerType().equals(ServerType.LOBBY)) {
            player.teleport(coreSpigot.getServerManager().getLocationsUtil().getSpawn());
            if (!player.hasPlayedBefore()) {
                coreSpigot.getServerManager().getSettingsListener().getRideState().add(player);
                coreSpigot.getServerManager().getSettingsListener().getColor().put(player, "6");
                coreSpigot.getServerManager().getSettingsListener().getSclan().add(player);
                coreSpigot.getServerManager().getSettingsListener().getScoins().add(player);
                coreSpigot.getServerManager().getSettingsListener().getSfriends().add(player);
                coreSpigot.getServerManager().getSettingsListener().getSrang().add(player);
                coreSpigot.getServerManager().getSettingsListener().getSserver().add(player);
                coreSpigot.getServerManager().getSettingsListener().getJumpPads().add(player);
                coreSpigot.getServerManager().getSettingsListener().getSweather().add(player);
                player.setPlayerWeather(WeatherType.DOWNFALL);
            }
            player.setGameMode(GameMode.ADVENTURE);
            joinEvent.setJoinMessage(null);
            try {
                coreSpigot.getServerManager().getSettingsAPI().createPlayer(player);
                coreSpigot.getServerManager().getSettingsAPI().createScorePlayer(player);
                coreSpigot.getServerManager().getSettingsAPI().getColor(player);
                coreSpigot.getServerManager().getSettingsAPI().getSilent(player);
                coreSpigot.getServerManager().getSettingsAPI().getRide(player);
                coreSpigot.getServerManager().getSettingsAPI().getFriends(player);
                coreSpigot.getServerManager().getSettingsAPI().getRang(player);
                coreSpigot.getServerManager().getSettingsAPI().getServer(player);
                coreSpigot.getServerManager().getSettingsAPI().getClan(player);
                coreSpigot.getServerManager().getSettingsAPI().getCoins(player);
                coreSpigot.getServerManager().getSettingsAPI().getRealTime(player);
                coreSpigot.getServerManager().getSettingsAPI().getWeather(player);
                coreSpigot.getServerManager().getSettingsAPI().getDoubleJump(player);
                coreSpigot.getServerManager().getSettingsAPI().getWjump(player);
                coreSpigot.getServerManager().getSettingsAPI().getJumPlate(player);
                coreSpigot.getServerManager().getSettingsAPI().getTime(player);
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
                        if (coreSpigot.getServerManager().getSettingsListener().getSweather().contains(player)) {
                            player.setPlayerWeather(WeatherType.CLEAR);
                        } else {
                            player.setPlayerWeather(WeatherType.DOWNFALL);
                        }
                    }
                }
            }.runTaskTimer(coreSpigot, 0L, 20L * 5);

            player.getInventory().clear();

            player.getInventory().setItem(4,
                    coreSpigot.getServerManager().getItemCreator().createItemWithMaterial(Material.COMPASS, 0, 1, "§8\u00BB§7§lNavigator§8\u00AB", null));
            player.getInventory().setItem(1, coreSpigot.getServerManager().getItemCreator().createItemWithMaterial(Material.REDSTONE_COMPARATOR, 0, 1,
                    "§8\u00BB§6§lEinstellungen§8\u00AB", null));
            player.getInventory().setItem(7,
                    coreSpigot.getServerManager().getItemCreator().createItemWithMaterial(Material.NETHER_STAR, 0, 1, "§8\u00BB§f§lLobby-Switcher§8\u00AB", null));
            player.getInventory().setItem(0, coreSpigot.getServerManager().getItemCreator().createItemWithMaterial(Material.ARMOR_STAND, 0, 1, "§8\u00BB§3§lDein Minion§8\u00AB", null));

            ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName("§8\u00BB§9§lDein Profil§8\u00AB");
            is.setItemMeta(im);
            SkullMeta sm = (SkullMeta) is.getItemMeta();
            sm.setOwner(player.getName());
            is.setItemMeta(sm);
            player.getInventory().setItem(8, is);

            coreSpigot.getServerManager().getScoreboardManager().setScoreboard(player);

            ArrayList<String> friends = new ArrayList<>();
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
        } else if (coreSpigot.getServerManager().getServerType().equals(ServerType.DEFAULT)) {
            coreSpigot.getServerManager().getTabList().setPrefix(player);
        }
        waitMySQL(player, coreSpigot.getServerManager().getServerType());
        coreSpigot.getServerManager().getTabList().setPrefix(player);
    }

    private void waitMySQL(Player player, ServerType serverType) {
        coreSpigot.getServerManager().getSchedulerSaver().createScheduler(new BukkitRunnable() {
            @Override
            public void run() {
                if (serverType.equals(ServerType.LOBBY)) {
                    if (coreSpigot.getServerManager().getServerStatsAPI().getMaxServer().containsKey(player) && coreSpigot.getServerManager().getServerStatsAPI().getMaxServer().get(player) != null) {
                        IChatBaseComponent icb = ChatSerializer
                                .a("{\"text\":\"§2Du spielst öfters auf dem Server\",\"extra\":[{\"text\":\"§b "
                                        + coreSpigot.getServerManager().getServerStatsAPI().getMaxServer().get(player)
                                        + ". §2Wenn §2du §2dich §2mit §2diesem §2verbinden §2willst, §2dann §2klick §2einfach §2hier!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§aKlicke hier um diesen Server zu betreten!\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gotoserver " + coreSpigot.getServerManager().getServerStatsAPI().getMaxServer().get(player) + "\"}}]}");
                        PacketPlayOutChat packet = new PacketPlayOutChat(icb);
                        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                        cancel();
                    } else {
                        coreSpigot.getServerManager().getServerStatsAPI().getMaxPlayed(player);
                    }
                } else {
                    try {
                        coreSpigot.getServerManager().getServerStatsAPI().updatePlayed(player, coreSpigot.getServerManager().getServerStatsAPI().getPlayedInt(player, Bukkit.getServerName()) + 1, Bukkit.getServerName());
                        cancel();
                    } catch (NullPointerException e) {
                        coreSpigot.getServerManager().getServerStatsAPI().createPlayer(player);
                        coreSpigot.getServerManager().getServerStatsAPI().getPlayed(player);
                    }
                }
            }
        }.runTaskTimer(coreSpigot, 0L, 20L));
    }
}
