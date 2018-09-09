/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package main.de.mj.bb.core.listener;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import cloud.timo.TimoCloud.api.objects.PlayerObject;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayerManager;
import main.de.mj.bb.core.CoreSpigot;
import main.de.mj.bb.core.utils.ServerType;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

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
                coreSpigot.getServerManager().getSettingsListener().getScoreClan().add(player);
                coreSpigot.getServerManager().getSettingsListener().getScoreCoins().add(player);
                coreSpigot.getServerManager().getSettingsListener().getScoreFriends().add(player);
                coreSpigot.getServerManager().getSettingsListener().getScoreRank().add(player);
                coreSpigot.getServerManager().getSettingsListener().getScoreServer().add(player);
                coreSpigot.getServerManager().getSettingsListener().getJumpPads().add(player);
                coreSpigot.getServerManager().getSettingsListener().getWeather().add(player);
                player.setPlayerWeather(WeatherType.CLEAR);
                coreSpigot.getHookManager().getEconomy().depositPlayer(player, 1000);
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
                        if (coreSpigot.getServerManager().getSettingsListener().getWeather().contains(player)) {
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
                net.minecraft.server.v1_8_R3.IChatBaseComponent icb = net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer
                        .a("{\"text\":\"§6Derzeit ist folgender deiner Freunde online:\",\"extra\":[{\"text\":\"§a§l "
                                + friends
                                + "\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§9Klicke hier für mehr Informationen!\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/friendsgui\"}}]}");
                net.minecraft.server.v1_8_R3.PacketPlayOutChat packet = new net.minecraft.server.v1_8_R3.PacketPlayOutChat(icb);
                ((org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
            }
            if (friends.size() > 1) {
                net.minecraft.server.v1_8_R3.IChatBaseComponent icb = net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer
                        .a("{\"text\":\"§6Derzeit sind folgende deiner Freunde online:\",\"extra\":[{\"text\":\"§a§l "
                                + friends
                                + "\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§9Klicke hier für mehr Informationen!\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/friendsgui\"}}]}");
                net.minecraft.server.v1_8_R3.PacketPlayOutChat packet = new net.minecraft.server.v1_8_R3.PacketPlayOutChat(icb);
                ((org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
            }
        } else if (coreSpigot.getServerManager().getServerType().equals(ServerType.DEFAULT)) {
            coreSpigot.getServerManager().getTabList().setPrefix(player);
        }
        summonFireWork(player);
        waitMySQL(player, coreSpigot.getServerManager().getServerType());
        coreSpigot.getServerManager().getTabList().setPrefix(player);
    }

    private void waitMySQL(Player player, ServerType serverType) {
        coreSpigot.getServerManager().getSchedulerSaver().createScheduler(new BukkitRunnable() {
            @Override
            public void run() {
                if (serverType.equals(ServerType.LOBBY)) {
                    if (coreSpigot.getServerManager().getServerStatsAPI().getMaxServer().containsKey(player) && coreSpigot.getServerManager().getServerStatsAPI().getMaxServer().get(player) != null) {
                        net.minecraft.server.v1_8_R3.IChatBaseComponent icb = net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer
                                .a("{\"text\":\"§2Du spielst öfters auf dem Server\",\"extra\":[{\"text\":\"§b "
                                        + coreSpigot.getServerManager().getServerStatsAPI().getMaxServer().get(player)
                                        + ". §2Wenn §2du §2dich §2mit §2diesem §2verbinden §2willst, §2dann §2klick §2einfach §2hier!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§aKlicke hier um diesen Server zu betreten!\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gotoserver " + coreSpigot.getServerManager().getServerStatsAPI().getMaxServer().get(player) + "\"}}]}");
                        net.minecraft.server.v1_8_R3.PacketPlayOutChat packet = new net.minecraft.server.v1_8_R3.PacketPlayOutChat(icb);
                        ((org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
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

    private void summonFireWork(Player player) {
        Firework firework = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();

        Random random = new Random();

        int rt = random.nextInt(5) + 1;
        FireworkEffect.Type type = FireworkEffect.Type.BALL;
        if (rt == 1) type = FireworkEffect.Type.BALL;
        if (rt == 2) type = FireworkEffect.Type.BALL_LARGE;
        if (rt == 3) type = FireworkEffect.Type.BURST;
        if (rt == 4) type = FireworkEffect.Type.CREEPER;
        if (rt == 5) type = FireworkEffect.Type.STAR;

        int r1i = random.nextInt(17) + 1;
        int r2i = random.nextInt(17) + 1;
        Color c1 = getColor(r1i);
        Color c2 = getColor(r2i);

        FireworkEffect fireworkEffect = FireworkEffect.builder().flicker(random.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(random.nextBoolean()).build();
        fireworkMeta.addEffect(fireworkEffect);
        int rp = random.nextInt(2) + 1;
        fireworkMeta.setPower(rp);
        firework.setFireworkMeta(fireworkMeta);

    }

    private Color getColor(int i) {
        Color c = null;
        if (i == 1) c = Color.AQUA;
        if (i == 2) c = Color.BLACK;
        if (i == 3) c = Color.BLUE;
        if (i == 4) c = Color.FUCHSIA;
        if (i == 5) c = Color.GRAY;
        if (i == 6) c = Color.GREEN;
        if (i == 7) c = Color.LIME;
        if (i == 8) c = Color.MAROON;
        if (i == 9) c = Color.NAVY;
        if (i == 10) c = Color.OLIVE;
        if (i == 11) c = Color.ORANGE;
        if (i == 12) c = Color.PURPLE;
        if (i == 13) c = Color.RED;
        if (i == 14) c = Color.SILVER;
        if (i == 15) c = Color.TEAL;
        if (i == 16) c = Color.WHITE;
        if (i == 17) c = Color.YELLOW;

        return c;
    }
}
