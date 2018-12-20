/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package main.de.mj.bb.core.listener;

import main.de.mj.bb.core.CoreSpigot;
import main.de.mj.bb.core.sql.SettingsAPI;
import main.de.mj.bb.core.utils.ImageChar;
import main.de.mj.bb.core.utils.ImageMessage;
import main.de.mj.bb.core.utils.PlayerLevel;
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
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class JoinListener implements Listener {

    private final CoreSpigot coreSpigot;

    public JoinListener(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setListener(this);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent joinEvent) {
        Player player = joinEvent.getPlayer();
        ServerType serverType = coreSpigot.getModuleManager().getServerType();
        SettingsListener settingsListener = coreSpigot.getModuleManager().getSettingsListener();
        SettingsAPI settingsAPI = coreSpigot.getModuleManager().getSettingsAPI();
        //coreSpigot.getGameAPI().getCoinsAPI().registerPlayer(player.getUniqueId());
        if (serverType.equals(ServerType.BAU_SERVER) || serverType.equals(ServerType.PLUGIN_TEST_SERVER)) {
            if (!player.hasPermission("player.team")) {
                player.kickPlayer("");
            }
        }
        if (serverType.equals(ServerType.LOBBY)) {
            //noinspection deprecation
            player.sendTitle("§c✘ §6BattleBuild§c ✘", "✘ Willkommen " + player.getName() + " ✘");
            coreSpigot.getModuleManager().getSettingsListener().loadSkulls(player);
            player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "Bitte warte einen Moment, deine §eEinstellungen §7werden geladen!");
            if (!settingsAPI.checkPlayer(player)) {
                settingsListener.getRideState().add(player);
                settingsListener.getColor().put(player, "6");
                settingsListener.getScoreClan().add(player);
                settingsListener.getScoreCoins().add(player);
                settingsListener.getScoreFriends().add(player);
                settingsListener.getScoreRank().add(player);
                settingsListener.getScoreServer().add(player);
                settingsListener.getJumpPads().add(player);
                settingsListener.getWeather().add(player);
                settingsListener.getSpawnLocation().add(player);
                settingsListener.getPlayerLevel().put(player, PlayerLevel.YEAR);
                player.setPlayerWeather(WeatherType.CLEAR);
                coreSpigot.getHookManager().getEconomy().depositPlayer(player, 1000);
            }
            player.setGameMode(GameMode.ADVENTURE);
            joinEvent.setJoinMessage(null);
            try {
                settingsAPI.createPlayer(player);
                settingsAPI.createScorePlayer(player);
                settingsAPI.getColor(player);
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
                settingsAPI.getLevel(player);
                if (coreSpigot.getModuleManager().getSettingsAPI().getLevel(player).equals(PlayerLevel.LOBBY)) {
                    String[] serverName = player.getServer().getServerName().split("-");
                    int server = Integer.parseInt(serverName[1]);
                    player.setLevel(server);
                } else if (coreSpigot.getModuleManager().getSettingsAPI().getLevel(player).equals(PlayerLevel.SCROLL))
                    player.setLevel(1);
                else if (coreSpigot.getModuleManager().getSettingsAPI().getLevel(player).equals(PlayerLevel.YEAR)) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy#DDD");
                    String[] yearData = format.format(new Date(System.currentTimeMillis())).split("#");
                    player.setExp(Float.valueOf(yearData[1]) / 365f);
                    player.setLevel(Integer.valueOf(yearData[0]));
                } else if (coreSpigot.getModuleManager().getSettingsAPI().getLevel(player).equals(PlayerLevel.COINS))
                    player.setLevel((int) coreSpigot.getHookManager().getEconomy().getBalance(player));
                if (!coreSpigot.getModuleManager().getSettingsAPI().getRadio(player))
                    player.performCommand("radio");
                if (!coreSpigot.getModuleManager().getSettingsAPI().getSpawn(player))
                    player.teleport(coreSpigot.getModuleManager().getSpawnLocationAPI().getSpawnLocation(player.getUniqueId()));
                else player.teleport(coreSpigot.getModuleManager().getLocationsUtil().getSpawn());
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
                    coreSpigot.getModuleManager().getItemCreator().createItemWithMaterial(Material.COMPASS, 0, 1, "§8\u00BB§7§lNavigator§8\u00AB"));
            player.getInventory().setItem(1, coreSpigot.getModuleManager().getItemCreator().createItemWithMaterial(Material.REDSTONE_COMPARATOR, 0, 1,
                    "§8\u00BB§c§lEinstellungen§8\u00AB"));
            player.getInventory().setItem(2, coreSpigot.getModuleManager().getItemCreator().createItemWithSkull("148a8c55891dec76764449f57ba677be3ee88a06921ca93b6cc7c9611a7af", 1, "§8\u00BB§a§lRadio§8\u00AB", false));
            player.getInventory().setItem(7,
                    coreSpigot.getModuleManager().getItemCreator().createItemWithSkull("cf40942f364f6cbceffcf1151796410286a48b1aeba77243e218026c09cd1", 1, "§8\u00BB§f§lLobby-Switcher§8\u00AB", false));
            player.getInventory().setItem(0, coreSpigot.getModuleManager().getItemCreator().createItemWithMaterial(Material.ARMOR_STAND, 0, 1, "§8\u00BB§3§lDein Minion§8\u00AB"));

            ItemStack is = coreSpigot.getModuleManager().getItemCreator().createItemWithPlayer(player.getUniqueId().toString(), 1, "§8\u00BB§9§lDein Profil§8\u00AB");
            player.getInventory().setItem(8, is);

            coreSpigot.getModuleManager().getScoreboardManager().sendStartScoreboard(player);
            coreSpigot.getModuleManager().getScoreboardManager().sendScoreboard(player);
            Bukkit.getOnlinePlayers().forEach(all -> coreSpigot.getModuleManager().getScoreboardManager().updatePrefix(all));

            try {
                URL head = new URL("https://minotar.net/avatar/" + player.getName() + "/8.png");
                BufferedImage image = ImageIO.read(head);
                new ImageMessage(image, 8, ImageChar.BLOCK.getChar()).appendText(" ", " ", " ", "§f[§9§kl§f] §a§lWILLKOMMEN! §f[§9§kl§f]", "§8§l" + player.getName(), " ", " ", " ").sendToPlayer(player);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (serverType.equals(ServerType.VORBAUEN)) {
            joinEvent.setJoinMessage(null);
            player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "§aWillkommen auf dem Vorbau Server!");
            player.getInventory().clear();
            player.teleport(coreSpigot.getModuleManager().getLocationsUtil().getSpawn());
            player.setGameMode(GameMode.CREATIVE);
        }

        if (!serverType.equals(ServerType.LOBBY)) {
            coreSpigot.getModuleManager().getFileManager().getColorConfig().set(player.getUniqueId().toString(), getFinalColor(coreSpigot.getModuleManager().getSettingsAPI().getColorString(player)));
            try {
                coreSpigot.getModuleManager().getFileManager().getColorConfig().save(coreSpigot.getModuleManager().getFileManager().getColorFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        summonFireWork(player);
        coreSpigot.getModuleManager().getNickAPI().getPlayerNick(player);
    }

    private void summonFireWork(Player player) {
        Firework firework = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();

        Random random = new Random();

        int rt = random.nextInt(5) + 1;
        FireworkEffect.Type type = FireworkEffect.Type.BALL;
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

    private String getFinalColor(Short color) {
        if (color == 0)
            return "f";
        if (color == 1)
            return "6";
        if (color == 2)
            return "5";
        if (color == 3)
            return "b";
        if (color == 4)
            return "e";
        if (color == 5)
            return "a";
        if (color == 6)
            return "d";
        if (color == 7)
            return "8";
        if (color == 8)
            return "7";
        if (color == 9)
            return "3";
        if (color == 10)
            return "5";
        if (color == 11)
            return "9";
        if (color == 12)
            return "f";
        if (color == 13)
            return "2";
        if (color == 14)
            return "c";
        if (color == 15)
            return "0";
        return "6";
    }
}
