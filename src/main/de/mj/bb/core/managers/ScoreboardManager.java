package main.de.mj.bb.core.managers;


import cloud.timo.TimoCloud.api.TimoCloudAPI;
import cloud.timo.TimoCloud.api.objects.PlayerObject;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import main.de.mj.bb.core.CoreSpigot;
import main.de.mj.bb.core.utils.ServerType;
import me.lucko.luckperms.api.Contexts;
import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.User;
import me.lucko.luckperms.api.caching.MetaData;
import me.lucko.luckperms.api.context.ContextManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;

/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

@SuppressWarnings("ALL")
public class ScoreboardManager {

    private final CoreSpigot coreSpigot;
    private Map<Player, Scoreboard> playerScoreboards = new HashMap<>();
    private LuckPermsApi luckPermsApi;

    ScoreboardManager(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        luckPermsApi = coreSpigot.getHookManager().getLuckPermsApi();
    }

    public void sendStartScoreboard(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        String color = getFinalColor(coreSpigot.getModuleManager().getSettingsAPI().getColorString(player));
        board.registerNewTeam("00-Admin").setPrefix("§4§lAdmin §8|§7 ");
        board.registerNewTeam("01-SrMod").setPrefix("§c§lSrMod §8|§7 ");
        board.registerNewTeam("02-Mod").setPrefix("§c§lMod §8|§7 ");
        board.registerNewTeam("03-SrDev").setPrefix("§3§lSrDev §8|§7 ");
        board.registerNewTeam("04-Dev").setPrefix("§3§lDev §8|§7 ");
        board.registerNewTeam("05-SrB").setPrefix("§2§lSrB §8|§7 ");
        board.registerNewTeam("06-B").setPrefix("§2§lB §8|§7 ");
        board.registerNewTeam("07-SrSup").setPrefix("§9§lSrSup §8|§7 ");
        board.registerNewTeam("08-Sup").setPrefix("§9§lSup §8|§7 ");
        board.registerNewTeam("09-C").setPrefix("§b§lC §8|§7 ");
        board.registerNewTeam("10-YT").setPrefix("§5§lYT §8|§7 ");
        board.registerNewTeam("11-VIP+").setPrefix("§eVIP+ §8|§7 ");
        if (!(coreSpigot.getModuleManager().getServerType().equals(ServerType.CITY_BUILD) || coreSpigot.getModuleManager().getServerType().equals(ServerType.SKY_PVP))) {
            board.registerNewTeam("12-Emerald").setPrefix("§aEmerald §8|§7 ");
            board.registerNewTeam("13-Diamond").setPrefix("§bDiamond §8|§7 ");
            board.registerNewTeam("14-Gold").setPrefix("§6Gold §8|§7 ");
        } else if (coreSpigot.getModuleManager().getServerType().equals(ServerType.CITY_BUILD)) {
            board.registerNewTeam("12-Supreme").setPrefix("§cSupreme §8|§7 ");
            board.registerNewTeam("13-Prime").setPrefix("§bPrime §8|§7 ");
            board.registerNewTeam("14-Premium").setPrefix("§6Premium §8|§7 ");
        } else if (coreSpigot.getModuleManager().getServerType().equals(ServerType.SKY_PVP)) {
            board.registerNewTeam("12-King").setPrefix("§cKing §8|§7 ");
            board.registerNewTeam("13-Elite").setPrefix("§dElite §8|§7 ");
            board.registerNewTeam("14-Ultra").setPrefix("§bUltra §8|§7 ");
        }
        board.registerNewTeam("15-Player").setPrefix("§7");
        for (Player players : Bukkit.getOnlinePlayers()) {
            board.getTeams().forEach(teams -> {
                if (teams.hasPlayer(players)) {
                    teams.removePlayer(players);
                }
            });
            String team = "15-Player";
            User user = luckPermsApi.getUser(players.getUniqueId());
            assert user != null;
            String group = user.getPrimaryGroup();
            if (!coreSpigot.getModuleManager().getNickManager().isDisguised(players)) {
                if (coreSpigot.getModuleManager().getServerType().equals(ServerType.SKY_PVP)) {
                    if (group.equalsIgnoreCase("ultra")) {
                        team = "14-Ultra";
                    }
                    if (group.equalsIgnoreCase("elite")) {
                        team = "13-Elite";
                    }
                    if (group.equalsIgnoreCase("king")) {
                        team = "12-King";
                    }
                } else if (coreSpigot.getModuleManager().getServerType().equals(ServerType.CITY_BUILD)) {
                    if (group.equalsIgnoreCase("premium")) {
                        team = "14-Premium";
                    }
                    if (group.equalsIgnoreCase("prime")) {
                        team = "13-Prime";
                    }
                    if (group.equalsIgnoreCase("Supreme")) {
                        team = "12-Supreme";
                    }
                }
                if (group.equalsIgnoreCase("vip+")) {
                    team = "11-VIP+";
                }
                if (group.equalsIgnoreCase("youtuber")) {
                    team = "10-YT";
                }
                if (group.equalsIgnoreCase("content")) {
                    team = "09-C";
                }
                if (group.equalsIgnoreCase("supporter") || group.equalsIgnoreCase("junior")) {
                    team = "08-Sup";
                }
                if (group.equalsIgnoreCase("srsupporter")) {
                    team = "07-SrSup";
                }
                if (group.equalsIgnoreCase("builder")) {
                    team = "06-B";
                }
                if (group.equalsIgnoreCase("srbuilder")) {
                    team = "05-SrB";
                }
                if (group.equalsIgnoreCase("developer")) {
                    team = "04-Dev";
                }
                if (group.equalsIgnoreCase("srdeveloper")) {
                    team = "03-SrDev";
                }
                if (group.equalsIgnoreCase("moderator")) {
                    team = "02-Mod";
                }
                if (group.equalsIgnoreCase("srmoderator")) {
                    team = "01-SrMod";
                }
                if (group.equalsIgnoreCase("administrator")) {
                    team = "00-Admin";
                }
                Team t = board.getTeam(team);
                t.addPlayer(players);
                players.setDisplayName(t.getPrefix() + players.getName());
                if (coreSpigot.getHookManager().getClansManager().getClan(coreSpigot.getHookManager().getPafPlayerManager().getPlayer(players.getUniqueId())) != null) {
                    players.setPlayerListName(t.getPrefix() + players.getName() + " \u00a77[\u00a7e" + ChatColor.translateAlternateColorCodes('&', coreSpigot.getHookManager().getClansManager().getClan(coreSpigot.getHookManager().getPafPlayerManager().getPlayer(players.getUniqueId())).getClanTag()) + "\u00a77]");
                    continue;
                }
                players.setPlayerListName(t.getPrefix() + players.getName());
                continue;
            }
            board.getTeam("015-Player").addPlayer(players);
            players.setPlayerListName("§7" + players.getName());
            players.setDisplayName("§7" + players.getName());
        }
        player.setScoreboard(board);
        if (playerScoreboards.containsKey(player)) {
            Team coins;
            Objective obj = board.getObjective("Info");
            if (obj == null) {
                obj = board.registerNewObjective("Info", "dummy");
                obj.setDisplayName("§7\u00bb §" + color + "BattleBuild §7\u00ab");
                obj.setDisplaySlot(DisplaySlot.SIDEBAR);
            }
            User user = coreSpigot.getHookManager().getLuckPermsApi().getUser(player.getUniqueId());
            ContextManager cm = coreSpigot.getHookManager().getLuckPermsApi().getContextManager();
            Contexts contexts = cm.lookupApplicableContexts(user).orElse(cm.getStaticContexts());
            MetaData md = user.getCachedData().getMetaData(contexts);
            String rang = md.getPrefix().replace("&", "§").replace("|", "");
            String clan = "\u00a76Kein Clan";
            if (coreSpigot.getHookManager().getClansManager().getClan(coreSpigot.getHookManager().getPafPlayerManager().getPlayer(player.getUniqueId())) != null) {
                int i = 0;
                try {
                    for (PAFPlayer all : coreSpigot.getHookManager().getClansManager().getClan(coreSpigot.getHookManager().getPafPlayerManager().getPlayer(player.getUniqueId())).getAllPlayers()) {
                        for (PlayerObject online : TimoCloudAPI.getUniversalAPI().getProxy("Proxy").getOnlinePlayers()) {
                            if (online.getUuid().equals(all.getUniqueId())) {
                                i++;
                            }
                        }
                    }
                } catch (NullPointerException ex) {
                }
                clan = "§" + color + coreSpigot.getHookManager().getClansManager().getClan(coreSpigot.getHookManager().getPafPlayerManager().getPlayer(player.getUniqueId())).getClanTag() + "§7 [§" + color + i + "§7┊§" + color + coreSpigot.getHookManager().getClansManager().getClan(coreSpigot.getHookManager().getPafPlayerManager().getPlayer(player.getUniqueId())).getAllPlayers().size() + "§7]";
            }
            if ((coins = board.getTeam("coins")) == null) {
                coins = board.registerNewTeam("coins");
                coins.setPrefix("§7➟ ");
                coins.addEntry("§" + color);
            }
            coins.setSuffix(String.valueOf(coreSpigot.getHookManager().getEconomy().getBalance(player)));
            Team onlinetime = board.getTeam("onlinetime");
            if (onlinetime == null) {
                onlinetime = board.registerNewTeam("onlinetime");
                onlinetime.addEntry("§" + color + " ");
                onlinetime.setPrefix("§7➟ §" + color + " ");
            }
            onlinetime.setSuffix("\u00a76" + coreSpigot.getHookManager().getNetworkManagerPlugin().getPlayer(player.getUniqueId()).getPlaytime());
            if (coreSpigot.getModuleManager().getSettingsAPI().getRang(player)) {
                obj.getScore("● §7Dein Rang").setScore(17);
                obj.getScore("§7➟ " + rang).setScore(16);
                obj.getScore("\u00a77").setScore(15);
            }
            if (coreSpigot.getModuleManager().getSettingsAPI().getCoins(player)) {
                obj.getScore("● §7Deine Coins").setScore(14);
                obj.getScore("§7➟ §" + color + coreSpigot.getHookManager().getEconomy().getBalance(player)).setScore(13);
                obj.getScore("\u00a7a").setScore(12);
            }
            if (coreSpigot.getModuleManager().getSettingsAPI().getTime(player)) {
                obj.getScore("● §7Spielzeit").setScore(11);
                obj.getScore("§7➟ §" + color + (int) Math.floor(coreSpigot.getHookManager().getNetworkManagerPlugin().getPlayer(player.getUniqueId()).getPlaytime() / 1000 / 60 / 60) + "h").setScore(10);
                obj.getScore("\u00a71").setScore(9);
            }
            if (coreSpigot.getModuleManager().getSettingsAPI().getClan(player)) {
                obj.getScore("● §7Dein Clan").setScore(8);
                obj.getScore("§7➟ §" + color + clan).setScore(7);
                obj.getScore("§2").setScore(6);
            }
            if (coreSpigot.getModuleManager().getSettingsAPI().getServer(player)) {
                obj.getScore("● §7Server").setScore(5);
                obj.getScore("§7➟ §" + color + player.getServer().getServerName()).setScore(4);
                obj.getScore("§3").setScore(3);
            }
            if (coreSpigot.getModuleManager().getSettingsAPI().getFriends(player)) {
                obj.getScore("● §7Freunde").setScore(2);
                PAFPlayer pafPlayer = coreSpigot.getHookManager().getPafPlayerManager().getPlayer(player.getUniqueId());
                int f = 0;
                for (PAFPlayer friends : pafPlayer.getFriends()) {
                    for (PlayerObject online : TimoCloudAPI.getUniversalAPI().getProxy("Proxy").getOnlinePlayers()) {
                        if (online.getUuid().equals(friends.getUniqueId())) {
                            f++;
                        }
                    }
                }
                if (pafPlayer.getFriends() != null) {
                    obj.getScore("§7➟ §" + color + f + "§7┊§r§" + color + pafPlayer.getFriends().size()).setScore(1);
                } else {
                    obj.getScore("§7➟ §" + color + "0§7┊§r§" + color + "0").setScore(1);
                }
            }
        }
        for (Player players : Bukkit.getOnlinePlayers()) {
            Scoreboard scoreboard = players.getScoreboard();
            scoreboard.getTeams().forEach(teams -> {
                if (teams.hasPlayer(player)) {
                    teams.removePlayer(player);
                }
            });
            String team = "15-Player";
            User user = luckPermsApi.getUser(players.getUniqueId());
            assert user != null;
            String group = user.getPrimaryGroup();
            if (!coreSpigot.getModuleManager().getNickManager().isDisguised(players)) {
                if (coreSpigot.getModuleManager().getServerType().equals(ServerType.SKY_PVP)) {
                    if (group.equalsIgnoreCase("ultra")) {
                        team = "14-Ultra";
                    }
                    if (group.equalsIgnoreCase("elite")) {
                        team = "13-Elite";
                    }
                    if (group.equalsIgnoreCase("king")) {
                        team = "12-King";
                    }
                } else if (coreSpigot.getModuleManager().getServerType().equals(ServerType.CITY_BUILD)) {
                    if (group.equalsIgnoreCase("premium")) {
                        team = "14-Premium";
                    }
                    if (group.equalsIgnoreCase("prime")) {
                        team = "13-Prime";
                    }
                    if (group.equalsIgnoreCase("Supreme")) {
                        team = "12-Supreme";
                    }
                }
                if (group.equalsIgnoreCase("vip+")) {
                    team = "11-VIP+";
                }
                if (group.equalsIgnoreCase("youtuber")) {
                    team = "10-YT";
                }
                if (group.equalsIgnoreCase("content")) {
                    team = "09-C";
                }
                if (group.equalsIgnoreCase("supporter") || group.equalsIgnoreCase("junior")) {
                    team = "08-Sup";
                }
                if (group.equalsIgnoreCase("srsupporter")) {
                    team = "07-SrSup";
                }
                if (group.equalsIgnoreCase("builder")) {
                    team = "06-B";
                }
                if (group.equalsIgnoreCase("srbuilder")) {
                    team = "05-SrB";
                }
                if (group.equalsIgnoreCase("developer")) {
                    team = "04-Dev";
                }
                if (group.equalsIgnoreCase("srdeveloper")) {
                    team = "03-SrDev";
                }
                if (group.equalsIgnoreCase("moderator")) {
                    team = "02-Mod";
                }
                if (group.equalsIgnoreCase("srmoderator")) {
                    team = "01-SrMod";
                }
                if (group.equalsIgnoreCase("administrator")) {
                    team = "00-Admin";
                }
                Team t = scoreboard.getTeam(team);
                t.addPlayer(player);
                player.setDisplayName(t.getPrefix() + player.getName());
                if (coreSpigot.getHookManager().getClansManager().getClan(coreSpigot.getHookManager().getPafPlayerManager().getPlayer(player.getUniqueId())) != null) {
                    player.setPlayerListName(t.getPrefix() + player.getName() + " \u00a77[\u00a7e" + ChatColor.translateAlternateColorCodes((char) '&', (String) coreSpigot.getHookManager().getClansManager().getClan(coreSpigot.getHookManager().getPafPlayerManager().getPlayer(player.getUniqueId())).getClanTag()) + "\u00a77]");
                } else {
                    player.setPlayerListName(t.getPrefix() + player.getName());
                }
            } else {
                scoreboard.getTeam(team).addPlayer(player);
                player.setPlayerListName("§7" + player.getName());
                player.setDisplayName("§7" + player.getName());
            }
            players.setScoreboard(scoreboard);
            playerScoreboards.put(player, scoreboard);
        }
    }

    public void sendScoreboard(Player player) {
        String color = getFinalColor(coreSpigot.getModuleManager().getSettingsAPI().getColorString(player));
        Team coins;
        Scoreboard board = player.getScoreboard();
        Objective obj = board.getObjective("Info");
        if (obj == null) {
            obj = board.registerNewObjective("Info", "dummy");
            obj.setDisplayName("§7\u00bb §" + color + "BattleBuild §7\u00ab");
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        }
        User user = coreSpigot.getHookManager().getLuckPermsApi().getUser(player.getUniqueId());
        ContextManager cm = coreSpigot.getHookManager().getLuckPermsApi().getContextManager();
        Contexts contexts = cm.lookupApplicableContexts(user).orElse(cm.getStaticContexts());
        MetaData md = user.getCachedData().getMetaData(contexts);
        String rang = md.getPrefix().replace("&", "§").replace("|", "");
        String clan = "\u00a76Kein Clan";
        if (coreSpigot.getHookManager().getClansManager().getClan(coreSpigot.getHookManager().getPafPlayerManager().getPlayer(player.getUniqueId())) != null) {
            int i = 0;
            try {
                for (PAFPlayer all : coreSpigot.getHookManager().getClansManager().getClan(coreSpigot.getHookManager().getPafPlayerManager().getPlayer(player.getUniqueId())).getAllPlayers()) {
                    for (PlayerObject online : TimoCloudAPI.getUniversalAPI().getProxy("Proxy").getOnlinePlayers()) {
                        if (online.getUuid().equals(all.getUniqueId())) {
                            i++;
                        }
                    }
                }
            } catch (NullPointerException ex) {
            }
            clan = "§" + color + coreSpigot.getHookManager().getClansManager().getClan(coreSpigot.getHookManager().getPafPlayerManager().getPlayer(player.getUniqueId())).getClanTag() + "§7 [§" + color + i + "§7┊§" + color + coreSpigot.getHookManager().getClansManager().getClan(coreSpigot.getHookManager().getPafPlayerManager().getPlayer(player.getUniqueId())).getAllPlayers().size() + "§7]";
        }
        if ((coins = board.getTeam("coins")) == null) {
            coins = board.registerNewTeam("coins");
            coins.setPrefix("§7➟ ");
            coins.addEntry("§" + color);
        }
        coins.setSuffix(String.valueOf(coreSpigot.getHookManager().getEconomy().getBalance(player)));
        Team onlinetime = board.getTeam("onlinetime");
        if (onlinetime == null) {
            onlinetime = board.registerNewTeam("onlinetime");
            onlinetime.addEntry("§" + color + " ");
            onlinetime.setPrefix("§7➟ §" + color + " ");
        }
        onlinetime.setSuffix("\u00a76" + coreSpigot.getHookManager().getNetworkManagerPlugin().getPlayer(player.getUniqueId()).getPlaytime());
        if (coreSpigot.getModuleManager().getSettingsAPI().getRang(player)) {
            obj.getScore("● §7Dein Rang").setScore(17);
            obj.getScore("§7➟ " + rang).setScore(16);
            obj.getScore("\u00a77").setScore(15);
        }
        if (coreSpigot.getModuleManager().getSettingsAPI().getCoins(player)) {
            obj.getScore("● §7Deine Coins").setScore(14);
            obj.getScore("§7➟ §" + color + coreSpigot.getHookManager().getEconomy().getBalance(player)).setScore(13);
            obj.getScore("\u00a7a").setScore(12);
        }
        if (coreSpigot.getModuleManager().getSettingsAPI().getTime(player)) {
            obj.getScore("● §7Spielzeit").setScore(11);
            obj.getScore("§7➟ §" + color + (int) Math.floor(coreSpigot.getHookManager().getNetworkManagerPlugin().getPlayer(player.getUniqueId()).getPlaytime() / 1000 / 60 / 60) + "h").setScore(10);
            obj.getScore("\u00a71").setScore(9);
        }
        if (coreSpigot.getModuleManager().getSettingsAPI().getClan(player)) {
            obj.getScore("● §7Dein Clan").setScore(8);
            obj.getScore("§7➟ §" + color + clan).setScore(7);
            obj.getScore("§2").setScore(6);
        }
        if (coreSpigot.getModuleManager().getSettingsAPI().getServer(player)) {
            obj.getScore("● §7Server").setScore(5);
            obj.getScore("§7➟ §" + color + player.getServer().getServerName()).setScore(4);
            obj.getScore("§3").setScore(3);
        }
        if (coreSpigot.getModuleManager().getSettingsAPI().getFriends(player)) {
            obj.getScore("● §7Freunde").setScore(2);
            PAFPlayer pafPlayer = coreSpigot.getHookManager().getPafPlayerManager().getPlayer(player.getUniqueId());
            int f = 0;
            for (PAFPlayer friends : pafPlayer.getFriends()) {
                for (PlayerObject online : TimoCloudAPI.getUniversalAPI().getProxy("Proxy").getOnlinePlayers()) {
                    if (online.getUuid().equals(friends.getUniqueId())) {
                        f++;
                    }
                }
            }
            if (pafPlayer.getFriends() != null) {
                obj.getScore("§7➟ §" + color + f + "§7┊§r§" + color + pafPlayer.getFriends().size()).setScore(1);
            } else {
                obj.getScore("§7➟ §" + color + "0§7┊§r§" + color + "0").setScore(1);
            }
        }
    }

    public void updateScoreboard(Player p) {
        try {
            String color = getFinalColor(coreSpigot.getModuleManager().getSettingsAPI().getColorString(p));
            Scoreboard board = p.getScoreboard();
            Team coins = board.getTeam("coins");
            coins.setSuffix("§" + color + coreSpigot.getHookManager().getEconomy().getBalance(p));
            Team onlinetime = board.getTeam("onlinetime");
            onlinetime.setSuffix("§" + color + coreSpigot.getHookManager().getNetworkManagerPlugin().getPlayer(p.getUniqueId()).getPlaytime());
        } catch (Exception ignored) {
        }
    }

    public void updatePrefix(final Player p) {
        Scoreboard board = p.getScoreboard();
        Bukkit.getScheduler().runTaskLaterAsynchronously(coreSpigot, new Runnable() {

            @Override
            public void run() {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    Scoreboard scoreboard = players.getScoreboard();
                    scoreboard.getTeams().forEach(teams -> {
                        if (teams.hasPlayer(players)) {
                            teams.removePlayer(players);
                        }
                    });
                    String team = "15-Player";
                    User user = luckPermsApi.getUser(players.getUniqueId());
                    assert user != null;
                    String group = user.getPrimaryGroup();
                    if (!coreSpigot.getModuleManager().getNickManager().isDisguised(players)) {
                        if (coreSpigot.getModuleManager().getServerType().equals(ServerType.SKY_PVP)) {
                            if (group.equalsIgnoreCase("ultra")) {
                                team = "14-Ultra";
                            }
                            if (group.equalsIgnoreCase("elite")) {
                                team = "13-Elite";
                            }
                            if (group.equalsIgnoreCase("king")) {
                                team = "12-King";
                            }
                        } else if (coreSpigot.getModuleManager().getServerType().equals(ServerType.CITY_BUILD)) {
                            if (group.equalsIgnoreCase("premium")) {
                                team = "14-Premium";
                            }
                            if (group.equalsIgnoreCase("prime")) {
                                team = "13-Prime";
                            }
                            if (group.equalsIgnoreCase("Supreme")) {
                                team = "12-Supreme";
                            }
                        }
                        if (group.equalsIgnoreCase("vip+")) {
                            team = "11-VIP+";
                        }
                        if (group.equalsIgnoreCase("youtuber")) {
                            team = "10-YT";
                        }
                        if (group.equalsIgnoreCase("content")) {
                            team = "09-C";
                        }
                        if (group.equalsIgnoreCase("supporter") || group.equalsIgnoreCase("junior")) {
                            team = "08-Sup";
                        }
                        if (group.equalsIgnoreCase("srsupporter")) {
                            team = "07-SrSup";
                        }
                        if (group.equalsIgnoreCase("builder")) {
                            team = "06-B";
                        }
                        if (group.equalsIgnoreCase("srbuilder")) {
                            team = "05-SrB";
                        }
                        if (group.equalsIgnoreCase("developer")) {
                            team = "04-Dev";
                        }
                        if (group.equalsIgnoreCase("srdeveloper")) {
                            team = "03-SrDev";
                        }
                        if (group.equalsIgnoreCase("moderator")) {
                            team = "02-Mod";
                        }
                        if (group.equalsIgnoreCase("srmoderator")) {
                            team = "01-SrMod";
                        }
                        if (group.equalsIgnoreCase("administrator")) {
                            team = "00-Admin";
                        }
                        Team t = scoreboard.getTeam(team);
                        t.addPlayer(p);
                        p.setDisplayName(t.getPrefix() + p.getName());
                        if (coreSpigot.getHookManager().getClansManager().getClan(coreSpigot.getHookManager().getPafPlayerManager().getPlayer(players.getUniqueId())) != null) {
                            p.setPlayerListName(t.getPrefix() + p.getName() + " \u00a77[\u00a7e" + ChatColor.translateAlternateColorCodes('&', coreSpigot.getHookManager().getClansManager().getClan(coreSpigot.getHookManager().getPafPlayerManager().getPlayer(players.getUniqueId())).getClanTag() + "\u00a77]"));
                        } else {
                            p.setPlayerListName(t.getPrefix() + p.getName());
                        }
                    } else {
                        scoreboard.getTeam(team).addPlayer(p);
                        p.setPlayerListName("§7" + p.getName());
                        p.setDisplayName("§7" + p.getName());
                    }
                    players.setScoreboard(scoreboard);
                }
            }
        }, 4L);
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