package main.de.mj.bb.core.managers;


import cloud.timo.TimoCloud.api.TimoCloudAPI;
import cloud.timo.TimoCloud.api.objects.PlayerObject;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import main.de.mj.bb.core.CoreSpigot;
import main.de.mj.bb.core.sql.SettingsAPI;
import main.de.mj.bb.core.utils.ServerType;
import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.User;
import me.lucko.luckperms.api.context.ContextManager;
import org.bukkit.Bukkit;
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
    private final SettingsAPI settingsAPI;
    private final LuckPermsApi luckPermsApi;
    private Map<Player, Scoreboard> playerScoreboards = new HashMap<>();

    ScoreboardManager(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        luckPermsApi = coreSpigot.getHookManager().getLuckPermsApi();
        settingsAPI = coreSpigot.getModuleManager().getSettingsAPI();
    }

    public void setScoreboard(final Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("aaa", "bbb");
        String color = getFinalColor(settingsAPI.getColorString(player));

        User user = coreSpigot.getHookManager().getLuckPermsApi().getUser(player.getUniqueId());
        ContextManager cm = coreSpigot.getHookManager().getLuckPermsApi().getContextManager();

        PAFPlayer pafPlayer = coreSpigot.getHookManager().getPafPlayerManager().getPlayer(player.getUniqueId());
        String clan = "§" + color + "none";
        String clanTag = null;
        if (coreSpigot.getHookManager().getClansManager().getClan(coreSpigot.getHookManager().getPafPlayerManager().getPlayer(player.getUniqueId())) != null) {
            int i = 0;
            clanTag = coreSpigot.getHookManager().getClansManager().getClan(pafPlayer).getClanTag();
            try {
                for (PAFPlayer all : coreSpigot.getHookManager().getClansManager().getClan(pafPlayer).getAllPlayers()) {
                    for (PlayerObject online : TimoCloudAPI.getUniversalAPI().getProxy("Proxy").getOnlinePlayers()) {
                        if (online.getUuid().equals(all.getUniqueId())) {
                            i++;
                        }
                    }
                }
            } catch (NullPointerException ex) {
            }
            clan = coreSpigot.getHookManager().getClansManager().getClan(coreSpigot.getHookManager().getPafPlayerManager().getPlayer(player.getUniqueId())).getClanTag() + "§7 §" + color + i + "§7┊§" + color + coreSpigot.getHookManager().getClansManager().getClan(coreSpigot.getHookManager().getPafPlayerManager().getPlayer(player.getUniqueId())).getAllPlayers().size() + "";
        }

        if (coreSpigot.getModuleManager().getServerType().equals(ServerType.LOBBY)) {
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName("§7\u00bb §" + color + "§lBattleBuild §7\u00ab");


            Team scoreRang = scoreboard.registerNewTeam("rang");
            scoreRang.addEntry("§a§" + color);
            scoreRang.setPrefix("§7➟ ");
            scoreRang.setSuffix("");


            Team scoreCoins = scoreboard.registerNewTeam("coins");
            scoreCoins.addEntry("§b§" + color);
            scoreCoins.setPrefix("§7➟ ");
            scoreCoins.setSuffix(String.valueOf(coreSpigot.getHookManager().getEconomy().getBalance(player)));


            Team scoreTime = scoreboard.registerNewTeam("time");
            scoreTime.addEntry("§c§" + color);
            scoreTime.setPrefix("§7➟ ");
            String time = ((int) Math.floor(coreSpigot.getHookManager().getNetworkManagerPlugin().getPlayer(player.getUniqueId()).getPlaytime() / 1000 / 60 / 60)) + "h";
            scoreTime.setSuffix(time);

            Team scoreClan = scoreboard.registerNewTeam("clan");
            scoreClan.addEntry("§d§" + color);
            scoreClan.setPrefix("§7➟ ");
            scoreClan.setSuffix(clan);


            Team scoreServer = scoreboard.registerNewTeam("server");
            scoreServer.addEntry("§e§" + color);
            scoreServer.setPrefix("§7➟ ");
            scoreServer.setSuffix(player.getServer().getServerName());

            Team scoreFriends = scoreboard.registerNewTeam("friends");
            scoreFriends.addEntry("§f§" + color);
            scoreFriends.setPrefix("§7➟ ");
            int f = 0;
            for (PAFPlayer friends : pafPlayer.getFriends()) {
                for (PlayerObject online : TimoCloudAPI.getUniversalAPI().getProxy("Proxy").getOnlinePlayers()) {
                    if (online.getUuid().equals(friends.getUniqueId())) {
                        f++;
                    }
                }
            }
            if (pafPlayer.getFriends() != null) {
                scoreFriends.setSuffix(f + "§7┊§r§" + color + pafPlayer.getFriends().size());
            } else {
                scoreFriends.setSuffix("0§7┊§r§" + color + "0");
            }


            if (settingsAPI.getRang(player)) {
                objective.getScore("● §7Dein Rang").setScore(17);
                objective.getScore("§a§" + color).setScore(16);
                objective.getScore("§5").setScore(15);
            }
            if (settingsAPI.getCoins(player)) {
                objective.getScore("● §7Deine Coins").setScore(14);
                objective.getScore("§b§" + color).setScore(13);
                objective.getScore("§4").setScore(12);
            }
            if (settingsAPI.getTime(player)) {
                objective.getScore("● §7Spielzeit").setScore(11);
                objective.getScore("§c§" + color).setScore(10);
                objective.getScore("§3").setScore(9);
            }
            if (settingsAPI.getClan(player)) {
                objective.getScore("● §7Dein Clan").setScore(8);
                objective.getScore("§d§" + color).setScore(7);
                objective.getScore("§2").setScore(6);
            }
            if (settingsAPI.getServer(player)) {
                objective.getScore("● §7Server").setScore(5);
                objective.getScore("§e§" + color).setScore(4);
                objective.getScore("§1").setScore(3);
            }
            if (settingsAPI.getFriends(player)) {
                objective.getScore("● §7Freunde").setScore(2);
                objective.getScore("§f§" + color).setScore(1);
            }
        }
        registerTeams(scoreboard);

        String playersteam = "15-Player";
        assert user != null;
        String group = user.getPrimaryGroup();
        if (!coreSpigot.getModuleManager().getNickManager().isDisguised(player)) {
            if (group.equalsIgnoreCase("ultra") || group.equalsIgnoreCase("premium") || group.equalsIgnoreCase("gold")) {
                playersteam = "14-Rank3";
            }
            if (group.equalsIgnoreCase("elite") || group.equalsIgnoreCase("prime") || group.equalsIgnoreCase("diamond")) {
                playersteam = "13-Rank2";
            }
            if (group.equalsIgnoreCase("king") || group.equalsIgnoreCase("Supreme") || group.equalsIgnoreCase("emerald")) {
                playersteam = "12-Rank1";
            }
            if (group.equalsIgnoreCase("vip+")) {
                playersteam = "11-VIP+";
            }
            if (group.equalsIgnoreCase("youtuber")) {
                playersteam = "10-YT";
            }
            if (group.equalsIgnoreCase("content")) {
                playersteam = "09-C";
            }
            if (group.equalsIgnoreCase("supporter") || group.equalsIgnoreCase("junior")) {
                playersteam = "08-Sup";
            }
            if (group.equalsIgnoreCase("srsupporter")) {
                playersteam = "07-SrSup";
            }
            if (group.equalsIgnoreCase("builder")) {
                playersteam = "06-B";
            }
            if (group.equalsIgnoreCase("srbuilder")) {
                playersteam = "05-SrB";
            }
            if (group.equalsIgnoreCase("developer")) {
                playersteam = "04-Dev";
            }
            if (group.equalsIgnoreCase("srdeveloper")) {
                playersteam = "03-SrDev";
            }
            if (group.equalsIgnoreCase("moderator")) {
                playersteam = "02-Mod";
            }
            if (group.equalsIgnoreCase("srmoderator")) {
                playersteam = "01-SrMod";
            }
            if (group.equalsIgnoreCase("administrator")) {
                playersteam = "00-Admin";
            }
        }

        player.setScoreboard(scoreboard);

        for (Player all : Bukkit.getOnlinePlayers()) {
            all.getScoreboard().getTeam(playersteam).addEntry(player.getName());
            all.setScoreboard(all.getScoreboard());
            if (all.getName().equalsIgnoreCase(player.getName()))
                return;
            User allUser = luckPermsApi.getUser(all.getUniqueId());
            assert allUser != null;
            String allGroup = allUser.getPrimaryGroup();
            if (!coreSpigot.getModuleManager().getNickManager().isDisguised(all)) {
                if (allGroup.equalsIgnoreCase("ultra") || allGroup.equalsIgnoreCase("premium") || allGroup.equalsIgnoreCase("gold")) {
                    player.getScoreboard().getTeam("14-Rank3").addEntry(all.getName());
                } else if (allGroup.equalsIgnoreCase("elite") || allGroup.equalsIgnoreCase("prime") || allGroup.equalsIgnoreCase("diamond")) {
                    player.getScoreboard().getTeam("13-Rank2").addEntry(all.getName());
                } else if (allGroup.equalsIgnoreCase("king") || allGroup.equalsIgnoreCase("Supreme") || allGroup.equalsIgnoreCase("emerald")) {
                    player.getScoreboard().getTeam("12-Rank1").addEntry(all.getName());
                } else if (allGroup.equalsIgnoreCase("vip+")) {
                    player.getScoreboard().getTeam("11-VIP+").addEntry(all.getName());
                } else if (allGroup.equalsIgnoreCase("youtuber")) {
                    player.getScoreboard().getTeam("10-YT").addEntry(all.getName());
                } else if (allGroup.equalsIgnoreCase("content")) {
                    player.getScoreboard().getTeam("09-C").addEntry(all.getName());
                } else if (allGroup.equalsIgnoreCase("supporter") || allGroup.equalsIgnoreCase("junior")) {
                    player.getScoreboard().getTeam("08-Sup").addEntry(all.getName());
                } else if (allGroup.equalsIgnoreCase("srsupporter")) {
                    player.getScoreboard().getTeam("07-SrSup").addEntry(all.getName());
                } else if (allGroup.equalsIgnoreCase("builder")) {
                    player.getScoreboard().getTeam("06-B").addEntry(all.getName());
                } else if (allGroup.equalsIgnoreCase("srbuilder")) {
                    player.getScoreboard().getTeam("05-SrB").addEntry(all.getName());
                } else if (allGroup.equalsIgnoreCase("developer")) {
                    player.getScoreboard().getTeam("04-Dev").addEntry(all.getName());
                } else if (allGroup.equalsIgnoreCase("srdeveloper")) {
                    player.getScoreboard().getTeam("03-SrDev").addEntry(all.getName());
                } else if (allGroup.equalsIgnoreCase("moderator")) {
                    player.getScoreboard().getTeam("02-Mod").addEntry(all.getName());
                } else if (allGroup.equalsIgnoreCase("srmoderator")) {
                    player.getScoreboard().getTeam("01-SrMod").addEntry(all.getName());
                } else if (allGroup.equalsIgnoreCase("administrator")) {
                    player.getScoreboard().getTeam("00-Admin").addEntry(all.getName());
                } else player.getScoreboard().getTeam("15-Player").addEntry(all.getName());
            } else player.getScoreboard().getTeam("15-Player").addEntry(all.getName());
            player.setScoreboard(player.getScoreboard());
        }
    }

    public void renewScoreboard(final Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective("aaa");
        String color = getFinalColor(coreSpigot.getModuleManager().getSettingsAPI().getColorString(player));
        PAFPlayer pafPlayer = coreSpigot.getHookManager().getPafPlayerManager().getPlayer(player.getUniqueId());
        String clan = "§" + color + "none";
        if (coreSpigot.getHookManager().getClansManager().getClan(coreSpigot.getHookManager().getPafPlayerManager().getPlayer(player.getUniqueId())) != null) {
            int i = 0;
            try {
                for (PAFPlayer all : coreSpigot.getHookManager().getClansManager().getClan(pafPlayer).getAllPlayers()) {
                    for (PlayerObject online : TimoCloudAPI.getUniversalAPI().getProxy("Proxy").getOnlinePlayers()) {
                        if (online.getUuid().equals(all.getUniqueId())) {
                            i++;
                        }
                    }
                }
            } catch (NullPointerException ex) {
            }
            clan = coreSpigot.getHookManager().getClansManager().getClan(coreSpigot.getHookManager().getPafPlayerManager().getPlayer(player.getUniqueId())).getClanTag() + "§7 §" + color + i + "§7┊§" + color + coreSpigot.getHookManager().getClansManager().getClan(coreSpigot.getHookManager().getPafPlayerManager().getPlayer(player.getUniqueId())).getAllPlayers().size() + "";
        }

        Team scoreRang = scoreboard.getTeam("rang");
        String rang = "§" + color + "Player";
        for (Team team : scoreboard.getTeams()) {
            if (team.getEntries().contains(player.getName())) {
                rang = team.getName().split("-")[1];
                break;
            }
        }
        scoreRang.setSuffix(rang);


        Team scoreCoins = scoreboard.getTeam("coins");
        scoreCoins.setSuffix(String.valueOf(coreSpigot.getHookManager().getEconomy().getBalance(player)));


        Team scoreTime = scoreboard.getTeam("time");
        String time = ((int) Math.floor(coreSpigot.getHookManager().getNetworkManagerPlugin().getPlayer(player.getUniqueId()).getPlaytime() / 1000 / 60 / 60)) + "h";
        scoreTime.setSuffix(time);

        Team scoreClan = scoreboard.getTeam("clan");
        scoreClan.setSuffix(clan);


        Team scoreServer = scoreboard.getTeam("server");
        scoreServer.setSuffix(player.getServer().getServerName());

        Team scoreFriends = scoreboard.getTeam("friends");
        int f = 0;
        for (PAFPlayer friends : pafPlayer.getFriends()) {
            for (PlayerObject online : TimoCloudAPI.getUniversalAPI().getProxy("Proxy").getOnlinePlayers()) {
                if (online.getUuid().equals(friends.getUniqueId())) {
                    f++;
                }
            }
        }
        if (pafPlayer.getFriends() != null) {
            scoreFriends.setSuffix(f + "§7┊§r§" + color + pafPlayer.getFriends().size());
        } else {
            scoreFriends.setSuffix("0§7┊§r§" + color + "0");
        }


        if (settingsAPI.getRang(player)) {
            objective.getScore("● §7Dein Rang").setScore(17);
            objective.getScore("§a§" + color).setScore(16);
            objective.getScore("§5").setScore(15);
        } else {
            scoreboard.resetScores("● §7Dein Rang");
            scoreboard.resetScores("§a§" + color);
            scoreboard.resetScores("§5");
        }
        if (settingsAPI.getCoins(player)) {
            objective.getScore("● §7Deine Coins").setScore(14);
            objective.getScore("§b§" + color).setScore(13);
            objective.getScore("§4").setScore(12);
        } else {
            scoreboard.resetScores("● §7Deine Coins");
            scoreboard.resetScores("§b§" + color);
            scoreboard.resetScores("§4");
        }
        if (settingsAPI.getTime(player)) {
            objective.getScore("● §7Spielzeit").setScore(11);
            objective.getScore("§c§" + color).setScore(10);
            objective.getScore("§3").setScore(9);
        } else {
            scoreboard.resetScores("● §7Spielzeit");
            scoreboard.resetScores("§c§" + color);
            scoreboard.resetScores("§3");
        }
        if (settingsAPI.getClan(player)) {
            objective.getScore("● §7Dein Clan").setScore(8);
            objective.getScore("§d§" + color).setScore(7);
            objective.getScore("§2").setScore(6);
        } else {
            scoreboard.resetScores("● §7Dein Clan");
            scoreboard.resetScores("§d§" + color);
            scoreboard.resetScores("§2");
        }
        if (settingsAPI.getServer(player)) {
            objective.getScore("● §7Server").setScore(5);
            objective.getScore("§e§" + color).setScore(4);
            objective.getScore("§1").setScore(3);
        } else {
            scoreboard.resetScores("● §7Server");
            scoreboard.resetScores("§e§" + color);
            scoreboard.resetScores("§1");
        }
        if (settingsAPI.getFriends(player)) {
            objective.getScore("● §7Freunde").setScore(2);
            objective.getScore("§f§" + color).setScore(1);
        } else {
            scoreboard.resetScores("● §7Freunde");
            scoreboard.resetScores("§f§" + color);
        }
        player.setScoreboard(scoreboard);
    }

    public void resetPrefix(final Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        String currentTeam = "15-Player";
        for (Team team : scoreboard.getTeams()) {
            if (team.getEntries().contains(player.getName())) {
                currentTeam = team.getName();
            }
        }
        String playersteam = "15-Player";
        String group = LuckPerms.getApi().getUser(player.getUniqueId()).getPrimaryGroup();
        if (!coreSpigot.getModuleManager().getNickManager().isDisguised(player)) {
            if (group.equalsIgnoreCase("ultra") || group.equalsIgnoreCase("premium") || group.equalsIgnoreCase("gold")) {
                playersteam = "14-Rank3";
            }
            if (group.equalsIgnoreCase("elite") || group.equalsIgnoreCase("prime") || group.equalsIgnoreCase("diamond")) {
                playersteam = "13-Rank2";
            }
            if (group.equalsIgnoreCase("king") || group.equalsIgnoreCase("Supreme") || group.equalsIgnoreCase("emerald")) {
                playersteam = "12-Rank1";
            }
            if (group.equalsIgnoreCase("vip+")) {
                playersteam = "11-VIP+";
            }
            if (group.equalsIgnoreCase("youtuber")) {
                playersteam = "10-YT";
            }
            if (group.equalsIgnoreCase("content")) {
                playersteam = "09-C";
            }
            if (group.equalsIgnoreCase("supporter") || group.equalsIgnoreCase("junior")) {
                playersteam = "08-Sup";
            }
            if (group.equalsIgnoreCase("srsupporter")) {
                playersteam = "07-SrSup";
            }
            if (group.equalsIgnoreCase("builder")) {
                playersteam = "06-B";
            }
            if (group.equalsIgnoreCase("srbuilder")) {
                playersteam = "05-SrB";
            }
            if (group.equalsIgnoreCase("developer")) {
                playersteam = "04-Dev";
            }
            if (group.equalsIgnoreCase("srdeveloper")) {
                playersteam = "03-SrDev";
            }
            if (group.equalsIgnoreCase("moderator")) {
                playersteam = "02-Mod";
            }
            if (group.equalsIgnoreCase("srmoderator")) {
                playersteam = "01-SrMod";
            }
            if (group.equalsIgnoreCase("administrator")) {
                playersteam = "00-Admin";
            }
        }

        player.setScoreboard(scoreboard);

        for (Player all : Bukkit.getOnlinePlayers()) {
            all.getScoreboard().getTeam(currentTeam).removeEntry(player.getName());
            all.getScoreboard().getTeam(playersteam).addEntry(player.getName());
            all.setScoreboard(all.getScoreboard());
            if (all.getName().equalsIgnoreCase(player.getName()))
                return;
            User allUser = luckPermsApi.getUser(all.getUniqueId());
            assert allUser != null;
            String allGroup = allUser.getPrimaryGroup();
            if (!coreSpigot.getModuleManager().getNickManager().isDisguised(all)) {
                if (allGroup.equalsIgnoreCase("ultra") || allGroup.equalsIgnoreCase("premium") || allGroup.equalsIgnoreCase("gold")) {
                    player.getScoreboard().getTeam("14-Rank3").addEntry(all.getName());
                } else if (allGroup.equalsIgnoreCase("elite") || allGroup.equalsIgnoreCase("prime") || allGroup.equalsIgnoreCase("diamond")) {
                    player.getScoreboard().getTeam("13-Rank2").addEntry(all.getName());
                } else if (allGroup.equalsIgnoreCase("king") || allGroup.equalsIgnoreCase("Supreme") || allGroup.equalsIgnoreCase("emerald")) {
                    player.getScoreboard().getTeam("12-Rank1").addEntry(all.getName());
                } else if (allGroup.equalsIgnoreCase("vip+")) {
                    player.getScoreboard().getTeam("11-VIP+").addEntry(all.getName());
                } else if (allGroup.equalsIgnoreCase("youtuber")) {
                    player.getScoreboard().getTeam("10-YT").addEntry(all.getName());
                } else if (allGroup.equalsIgnoreCase("content")) {
                    player.getScoreboard().getTeam("09-C").addEntry(all.getName());
                } else if (allGroup.equalsIgnoreCase("supporter") || allGroup.equalsIgnoreCase("junior")) {
                    player.getScoreboard().getTeam("08-Sup").addEntry(all.getName());
                } else if (allGroup.equalsIgnoreCase("srsupporter")) {
                    player.getScoreboard().getTeam("07-SrSup").addEntry(all.getName());
                } else if (allGroup.equalsIgnoreCase("builder")) {
                    player.getScoreboard().getTeam("06-B").addEntry(all.getName());
                } else if (allGroup.equalsIgnoreCase("srbuilder")) {
                    player.getScoreboard().getTeam("05-SrB").addEntry(all.getName());
                } else if (allGroup.equalsIgnoreCase("developer")) {
                    player.getScoreboard().getTeam("04-Dev").addEntry(all.getName());
                } else if (allGroup.equalsIgnoreCase("srdeveloper")) {
                    player.getScoreboard().getTeam("03-SrDev").addEntry(all.getName());
                } else if (allGroup.equalsIgnoreCase("moderator")) {
                    player.getScoreboard().getTeam("02-Mod").addEntry(all.getName());
                } else if (allGroup.equalsIgnoreCase("srmoderator")) {
                    player.getScoreboard().getTeam("01-SrMod").addEntry(all.getName());
                } else if (allGroup.equalsIgnoreCase("administrator")) {
                    player.getScoreboard().getTeam("00-Admin").addEntry(all.getName());
                } else player.getScoreboard().getTeam("15-Player").addEntry(all.getName());
            } else player.getScoreboard().getTeam("15-Player").addEntry(all.getName());
            player.setScoreboard(player.getScoreboard());
        }
    }

    private void registerTeams(final Scoreboard scoreboard) {
        Team admin = scoreboard.registerNewTeam("00-Admin");
        Team srMod = scoreboard.registerNewTeam("01-SrMod");
        Team mod = scoreboard.registerNewTeam("02-Mod");
        Team srDev = scoreboard.registerNewTeam("03-SrDev");
        Team dev = scoreboard.registerNewTeam("04-Dev");
        Team srB = scoreboard.registerNewTeam("05-SrB");
        Team b = scoreboard.registerNewTeam("06-B");
        Team srSup = scoreboard.registerNewTeam("07-SrSup");
        Team sup = scoreboard.registerNewTeam("08-Sup");
        Team c = scoreboard.registerNewTeam("09-C");
        Team yt = scoreboard.registerNewTeam("10-YT");
        Team vip = scoreboard.registerNewTeam("11-VIP+");
        Team rank1 = scoreboard.registerNewTeam("12-Rank1");
        Team rank2 = scoreboard.registerNewTeam("13-Rank2");
        Team rank3 = scoreboard.registerNewTeam("14-Rank3");
        Team player = scoreboard.registerNewTeam("15-Player");
        admin.setPrefix("§4§lAdmin §8|§7 ");
        srMod.setPrefix("§c§lSrMod §8|§7 ");
        mod.setPrefix("§c§lMod §8|§7 ");
        srDev.setPrefix("§3§lSrDev §8|§7 ");
        dev.setPrefix("§3§lDev §8|§7 ");
        srB.setPrefix("§2§lSrB §8|§7 ");
        b.setPrefix("§2§lB §8|§7 ");
        srSup.setPrefix("§9§lSrSup §8|§7 ");
        sup.setPrefix("§9§lSup §8|§7 ");
        c.setPrefix("§b§lC §8|§7 ");
        yt.setPrefix("§5§lYT §8|§7 ");
        vip.setPrefix("§eVIP+ §8|§7 ");
        if (!(coreSpigot.getModuleManager().getServerType().equals(ServerType.CITY_BUILD) || coreSpigot.getModuleManager().getServerType().equals(ServerType.SKY_PVP))) {
            rank1.setPrefix("§aEmerald §8|§7 ");
            rank2.setPrefix("§bDiamond §8|§7 ");
            rank3.setPrefix("§6Gold §8|§7 ");
        } else if (coreSpigot.getModuleManager().getServerType().equals(ServerType.CITY_BUILD)) {
            rank1.setPrefix("§cSupreme §8|§7 ");
            rank2.setPrefix("§bPrime §8|§7 ");
            rank3.setPrefix("§6Premium §8|§7 ");
        } else if (coreSpigot.getModuleManager().getServerType().equals(ServerType.SKY_PVP)) {
            rank1.setPrefix("§cKing §8|§7 ");
            rank2.setPrefix("§dElite §8|§7 ");
            rank3.setPrefix("§bUltra §8|§7 ");
        }
        player.setPrefix("§7");
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