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
import org.jetbrains.annotations.Contract;
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
        System.out.println(coreSpigot.getModuleManager().getServerType());
        if (coreSpigot.getModuleManager().getServerType().equals(ServerType.LOBBY)) {
            welcomeScheduler(player);
            player.teleport(coreSpigot.getModuleManager().getLocationsUtil().getSpawn());
            if (!player.hasPlayedBefore()) {
                coreSpigot.getModuleManager().getSettingsListener().getRideState().add(player);
                coreSpigot.getModuleManager().getSettingsListener().getColor().put(player, "6");
                coreSpigot.getModuleManager().getSettingsListener().getScoreClan().add(player);
                coreSpigot.getModuleManager().getSettingsListener().getScoreCoins().add(player);
                coreSpigot.getModuleManager().getSettingsListener().getScoreFriends().add(player);
                coreSpigot.getModuleManager().getSettingsListener().getScoreRank().add(player);
                coreSpigot.getModuleManager().getSettingsListener().getScoreServer().add(player);
                coreSpigot.getModuleManager().getSettingsListener().getJumpPads().add(player);
                coreSpigot.getModuleManager().getSettingsListener().getWeather().add(player);
                player.setPlayerWeather(WeatherType.CLEAR);
                coreSpigot.getHookManager().getEconomy().depositPlayer(player, 1000);
            }
            player.setGameMode(GameMode.ADVENTURE);
            joinEvent.setJoinMessage(null);
            try {
                coreSpigot.getModuleManager().getSettingsAPI().createPlayer(player);
                coreSpigot.getModuleManager().getSettingsAPI().createScorePlayer(player);
                coreSpigot.getModuleManager().getSettingsAPI().getColor(player);
                coreSpigot.getModuleManager().getSettingsAPI().getSilent(player);
                coreSpigot.getModuleManager().getSettingsAPI().getRide(player);
                coreSpigot.getModuleManager().getSettingsAPI().getFriends(player);
                coreSpigot.getModuleManager().getSettingsAPI().getRang(player);
                coreSpigot.getModuleManager().getSettingsAPI().getServer(player);
                coreSpigot.getModuleManager().getSettingsAPI().getClan(player);
                coreSpigot.getModuleManager().getSettingsAPI().getCoins(player);
                coreSpigot.getModuleManager().getSettingsAPI().getRealTime(player);
                coreSpigot.getModuleManager().getSettingsAPI().getWeather(player);
                coreSpigot.getModuleManager().getSettingsAPI().getDoubleJump(player);
                coreSpigot.getModuleManager().getSettingsAPI().getWjump(player);
                coreSpigot.getModuleManager().getSettingsAPI().getJumPlate(player);
                coreSpigot.getModuleManager().getSettingsAPI().getTime(player);
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
                        if (coreSpigot.getModuleManager().getSettingsListener().getWeather().contains(player)) {
                            player.setPlayerWeather(WeatherType.CLEAR);
                        } else {
                            player.setPlayerWeather(WeatherType.DOWNFALL);
                        }
                    }
                }
            }.runTaskTimer(coreSpigot, 0L, 20L * 5);

            player.getInventory().clear();

            player.getInventory().setItem(4,
                    coreSpigot.getModuleManager().getItemCreator().createItemWithMaterial(Material.COMPASS, 0, 1, "§8\u00BB§7§lNavigator§8\u00AB", null));
            player.getInventory().setItem(1, coreSpigot.getModuleManager().getItemCreator().createItemWithMaterial(Material.REDSTONE_COMPARATOR, 0, 1,
                    "§8\u00BB§6§lEinstellungen§8\u00AB", null));
            player.getInventory().setItem(7,
                    coreSpigot.getModuleManager().getItemCreator().createItemWithMaterial(Material.NETHER_STAR, 0, 1, "§8\u00BB§f§lLobby-Switcher§8\u00AB", null));
            player.getInventory().setItem(0, coreSpigot.getModuleManager().getItemCreator().createItemWithMaterial(Material.ARMOR_STAND, 0, 1, "§8\u00BB§3§lDein Minion§8\u00AB", null));

            ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName("§8\u00BB§9§lDein Profil§8\u00AB");
            is.setItemMeta(im);
            SkullMeta sm = (SkullMeta) is.getItemMeta();
            sm.setOwner(player.getName());
            is.setItemMeta(sm);
            player.getInventory().setItem(8, is);

            coreSpigot.getModuleManager().getScoreboardManager().setScoreboard(player);

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
        } else if (coreSpigot.getModuleManager().getServerType().equals(ServerType.DEFAULT)) {
            coreSpigot.getModuleManager().getTabList().setPrefix(player);
        }
        summonFireWork(player);
        waitMySQL(player, coreSpigot.getModuleManager().getServerType());
        coreSpigot.getModuleManager().getTabList().setPrefix(player);
    }

    private void waitMySQL(Player player, ServerType serverType) {
        coreSpigot.getModuleManager().getSchedulerSaver().createScheduler(new BukkitRunnable() {
            @Override
            public void run() {
                if (serverType.equals(ServerType.LOBBY)) {
                    if (coreSpigot.getModuleManager().getServerStatsAPI().getMaxServer().containsKey(player) && coreSpigot.getModuleManager().getServerStatsAPI().getMaxServer().get(player) != null) {
                        net.minecraft.server.v1_8_R3.IChatBaseComponent icb = net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer
                                .a("{\"text\":\"§2Du spielst öfters auf dem Server\",\"extra\":[{\"text\":\"§b "
                                        + coreSpigot.getModuleManager().getServerStatsAPI().getMaxServer().get(player)
                                        + ". §2Wenn §2du §2dich §2mit §2diesem §2verbinden §2willst, §2dann §2klick §2einfach §2hier!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§aKlicke hier um diesen Server zu betreten!\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gotoserver " + coreSpigot.getModuleManager().getServerStatsAPI().getMaxServer().get(player) + "\"}}]}");
                        net.minecraft.server.v1_8_R3.PacketPlayOutChat packet = new net.minecraft.server.v1_8_R3.PacketPlayOutChat(icb);
                        ((org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                        cancel();
                    } else {
                        coreSpigot.getModuleManager().getServerStatsAPI().getMaxPlayed(player);
                    }
                } else {
                    try {
                        coreSpigot.getModuleManager().getServerStatsAPI().updatePlayed(player, coreSpigot.getModuleManager().getServerStatsAPI().getPlayedInt(player, Bukkit.getServerName()) + 1, Bukkit.getServerName());
                        cancel();
                    } catch (NullPointerException e) {
                        coreSpigot.getModuleManager().getServerStatsAPI().createPlayer(player);
                        coreSpigot.getModuleManager().getServerStatsAPI().getPlayed(player);
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

    @Contract(pure = true)
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

    @Deprecated
    private void welcomeScheduler(Player player) {
        new BukkitRunnable() {
            int time = 1;

            @Override
            public void run() {
                if (time == 1)
                    player.sendTitle("§c✘✘", "");
                if (time == 2)
                    player.sendTitle("§c✘ §6B§c ✘", "");
                if (time == 3)
                    player.sendTitle("§c✘ §6Ba§c ✘", "");
                if (time == 4)
                    player.sendTitle("§c✘ §6Bat§c ✘", "");
                if (time == 5)
                    player.sendTitle("§c✘ §6Batt§c ✘", "");
                if (time == 6)
                    player.sendTitle("§c✘ §6Battl§c ✘", "");
                if (time == 7)
                    player.sendTitle("§c✘ §6Battle§c ✘", "");
                if (time == 8)
                    player.sendTitle("§c✘ §6BattleB§c ✘", "");
                if (time == 9)
                    player.sendTitle("§c✘ §6BattleBu§c ✘", "");
                if (time == 10)
                    player.sendTitle("§c✘ §6BattleBui§c ✘", "");
                if (time == 11)
                    player.sendTitle("§c✘ §6BattleBuil§c ✘", "");
                if (time == 12)
                    player.sendTitle("§c✘ §6BattleBuild§c ✘", "");
                if (time == 13)
                    player.sendTitle("§c✘ §6BattleBuild§c ✘", "✘ Willkommen ✘");
                if (time == 14)
                    player.sendTitle("§c✘ §6BattleBuild§c ✘", "✘ Willkommen ✘");
                if (time == 15) {
                    player.sendTitle("§c✘ §6BattleBuild§c ✘", "✘ Willkommen ✘");
                    cancel();
                }
                time++;
            }
        }.runTaskTimer(coreSpigot, 0L, 5L);
    }
}
