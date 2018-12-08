/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package main.de.mj.bb.core.listener;

import main.de.mj.bb.core.CoreSpigot;
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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
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
        //coreSpigot.getGameAPI().getCoinsAPI().registerPlayer(player.getUniqueId());
        if (coreSpigot.getModuleManager().getServerType().equals(ServerType.BAU_SERVER)) {
            if (!player.hasPermission("player.team")) {
                player.kickPlayer("");
            }
        }
        if (coreSpigot.getModuleManager().getServerType().equals(ServerType.LOBBY)) {
            player.sendTitle("§c✘ §6BattleBuild§c ✘", "✘ Willkommen " + player.getName() + " ✘");
            coreSpigot.getModuleManager().getSettingsListener().loadSkulls(player);
            player.teleport(coreSpigot.getModuleManager().getLocationsUtil().getSpawn());
            player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "Bitte warte einen Moment, deine §eEinstellungen §7werden geladen!");
            if (!coreSpigot.getModuleManager().getSettingsAPI().checkPlayer(player)) {
                coreSpigot.getModuleManager().getSettingsListener().getRideState().add(player);
                coreSpigot.getModuleManager().getSettingsListener().getColor().put(player, "6");
                coreSpigot.getModuleManager().getSettingsListener().getScoreClan().add(player);
                coreSpigot.getModuleManager().getSettingsListener().getScoreCoins().add(player);
                coreSpigot.getModuleManager().getSettingsListener().getScoreFriends().add(player);
                coreSpigot.getModuleManager().getSettingsListener().getScoreRank().add(player);
                coreSpigot.getModuleManager().getSettingsListener().getScoreServer().add(player);
                coreSpigot.getModuleManager().getSettingsListener().getJumpPads().add(player);
                coreSpigot.getModuleManager().getSettingsListener().getWeather().add(player);
                coreSpigot.getModuleManager().getSettingsListener().getSpawnLocation().add(player);
                coreSpigot.getModuleManager().getSettingsListener().getPlayerLevel().put(player, PlayerLevel.YEAR);
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
                coreSpigot.getModuleManager().getSettingsAPI().getLevel(player);
                coreSpigot.getModuleManager().getSettingsAPI().getRadio(player);
                coreSpigot.getModuleManager().getSettingsAPI().getSpawn(player);
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
            player.getInventory().setItem(2, coreSpigot.getModuleManager().getItemCreator().createItemWithMaterial(Material.JUKEBOX, 0, 1, "§8\u00BB§a§lRadio§8\u00AB"));
            player.getInventory().setItem(7,
                    coreSpigot.getModuleManager().getItemCreator().createItemWithMaterial(Material.NETHER_STAR, 0, 1, "§8\u00BB§f§lLobby-Switcher§8\u00AB"));
            player.getInventory().setItem(0, coreSpigot.getModuleManager().getItemCreator().createItemWithMaterial(Material.ARMOR_STAND, 0, 1, "§8\u00BB§3§lDein Minion§8\u00AB"));

            ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName("§8\u00BB§9§lDein Profil§8\u00AB");
            is.setItemMeta(im);
            SkullMeta sm = (SkullMeta) is.getItemMeta();
            sm.setOwner(player.getName());
            is.setItemMeta(sm);
            player.getInventory().setItem(8, is);

            try {
                URL head = new URL("https://minotar.net/avatar/" + player.getName() + "/8.png");
                BufferedImage image = ImageIO.read(head);
                new ImageMessage(image, 8, ImageChar.BLOCK.getChar()).appendText(" ", " ", " ", "§f[§9§kl§f] §a§lWILLKOMMEN! §f[§9§kl§f]", "§8§l" + player.getName(), " ", " ", " ").sendToPlayer(player);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (coreSpigot.getModuleManager().getServerType().equals(ServerType.VORBAUEN)) {
            joinEvent.setJoinMessage(null);
            player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "§aWillkommen auf dem Vorbau Server!");
            player.getInventory().clear();
            player.teleport(coreSpigot.getModuleManager().getLocationsUtil().getSpawn());
            player.setGameMode(GameMode.CREATIVE);
        }

        summonFireWork(player);
        waitMySQL(player, coreSpigot.getModuleManager().getServerType());
    }

    private void waitMySQL(Player player, ServerType serverType) {
        coreSpigot.getModuleManager().getSchedulerSaver().createScheduler(new BukkitRunnable() {
            @Override
            public void run() {
                if (serverType.equals(ServerType.LOBBY)) {
                    if (coreSpigot.getModuleManager().getSettingsListener().getPlayerLevel().containsKey(player) && coreSpigot.getModuleManager().getSettingsListener().getPlayerLevel().get(player) != null) {
                        if (coreSpigot.getModuleManager().getSettingsListener().getPlayerLevel().get(player).equals(PlayerLevel.LOBBY)) {
                            String[] serverName = player.getServer().getServerName().split("-");
                            int server = Integer.parseInt(serverName[1]);
                            player.setLevel(server);
                        } else if (coreSpigot.getModuleManager().getSettingsListener().getPlayerLevel().get(player).equals(PlayerLevel.SCROLL))
                            player.setLevel(1);
                        else if (coreSpigot.getModuleManager().getSettingsListener().getPlayerLevel().get(player).equals(PlayerLevel.YEAR)) {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy#DDD");
                            String[] yeardata = format.format(new Date(System.currentTimeMillis())).split("#");
                            player.setExp(Float.valueOf(yeardata[1]) / 365f);
                            player.setLevel(Integer.valueOf(yeardata[0]));
                        } else if (coreSpigot.getModuleManager().getSettingsListener().getPlayerLevel().get(player).equals(PlayerLevel.COINS))
                            player.setLevel(coreSpigot.getGameAPI().getCoinsAPI().getCoins(player.getUniqueId()));
                        if (coreSpigot.getModuleManager().getMusicListener().getRadioOff().contains(player))
                            player.performCommand("radio");
                        cancel();
                    }
                } else {
                    try {
                        coreSpigot.getModuleManager().getServerStatsAPI().updatePlayed(player, coreSpigot.getModuleManager().getServerStatsAPI().getPlayedInt(player, Bukkit.getServerName()) + 1, Bukkit.getServerName());
                    } catch (NullPointerException e) {
                        coreSpigot.getModuleManager().getServerStatsAPI().createPlayer(player);
                        coreSpigot.getModuleManager().getServerStatsAPI().getPlayed(player);
                    }
                    try {
                        coreSpigot.getModuleManager().getFileManager().getColorConfig().set(player.getUniqueId().toString(), getFinalColor(coreSpigot.getModuleManager().getSettingsAPI().getColorString(player)));
                        coreSpigot.getModuleManager().getFileManager().getColorConfig().save(coreSpigot.getModuleManager().getFileManager().getColorFile());
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                }
                if (coreSpigot.getModuleManager().getSettingsListener().getSpawnLocation().contains(player))
                    player.teleport(coreSpigot.getModuleManager().getSpawnLocationAPI().getSpawnLocation(player.getUniqueId()));
            }
        }.runTaskTimer(coreSpigot, 0L, 20L));
        coreSpigot.getModuleManager().getTabList().setTabList(player);
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
