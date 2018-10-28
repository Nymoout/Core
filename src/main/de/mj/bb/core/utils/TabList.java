package main.de.mj.bb.core.utils;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */
public class TabList {

    private final CoreSpigot coreSpigot;
    private static Scoreboard scoreboard;

    public TabList(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
    }

    public void loadTabList() {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        scoreboard.registerNewTeam("a").setPrefix("§4§lAdmin §8|§7 ");
        scoreboard.registerNewTeam("b").setPrefix("§c§lSrMod §8|§7 ");
        scoreboard.registerNewTeam("c").setPrefix("§c§lMod §8|§7 ");
        scoreboard.registerNewTeam("d").setPrefix("§3§lSrDev §8|§7 ");
        scoreboard.registerNewTeam("e").setPrefix("§3§lDev §8|§7 ");
        scoreboard.registerNewTeam("f").setPrefix("§2§lSrB §8|§7 ");
        scoreboard.registerNewTeam("g").setPrefix("§2§lBuild §8|§7 ");
        scoreboard.registerNewTeam("h").setPrefix("§9§lSrSup §8|§7 ");
        scoreboard.registerNewTeam("i").setPrefix("§9§lSup §8|§7 ");
        scoreboard.registerNewTeam("j").setPrefix("§b§lC §8|§7 ");
        scoreboard.registerNewTeam("k").setPrefix("§5§lYT §8|§7 ");
        scoreboard.registerNewTeam("l").setPrefix("§eVIP+ §8|§7 ");
        if (!(coreSpigot.getModuleManager().getServerType().equals(ServerType.CITY_BUILD) || coreSpigot.getModuleManager().getServerType().equals(ServerType.SKY_PVP))) {
            scoreboard.registerNewTeam("m").setPrefix("§bDiamond §8|§7 ");
            scoreboard.registerNewTeam("n").setPrefix("§aEmerald §8|§7 ");
            scoreboard.registerNewTeam("o").setPrefix("§6Gold §8|§7 ");
        } else if (coreSpigot.getModuleManager().getServerType().equals(ServerType.CITY_BUILD)) {
            scoreboard.registerNewTeam("m").setPrefix("§cSupreme §8|§7 ");
            scoreboard.registerNewTeam("n").setPrefix("§bPrime §8|§7 ");
            scoreboard.registerNewTeam("o").setPrefix("§6Premium §8|§7 ");
        } else if (coreSpigot.getModuleManager().getServerType().equals(ServerType.SKY_PVP)) {
            scoreboard.registerNewTeam("m").setPrefix("§cKing §8|§7 ");
            scoreboard.registerNewTeam("n").setPrefix("§dElite §8|§7 ");
            scoreboard.registerNewTeam("o").setPrefix("§bUltra §8|§7 ");
        }
        scoreboard.registerNewTeam("p").setPrefix("§7");
    }

    public void setPrefix(Player player) {
        String team = "p";
        //if (!coreSpigot.getNickManager().isDisguised(player)) {
            if (!(coreSpigot.getModuleManager().getServerType().equals(ServerType.CITY_BUILD) || coreSpigot.getModuleManager().getServerType().equals(ServerType.SKY_PVP))) {
                if (player.hasPermission("group." + "administrator"))
                    team = "a";
                else if (player.hasPermission("group." + "srmoderator"))
                    team = "b";
                else if (player.hasPermission("group." + "moderator"))
                    team = "c";
                else if (player.hasPermission("group." + "srdeveloper"))
                    team = "d";
                else if (player.hasPermission("group." + "developer"))
                    team = "e";
                else if (player.hasPermission("group." + "srbuilder"))
                    team = "f";
                else if (player.hasPermission("group." + "builder"))
                    team = "g";
                else if (player.hasPermission("group." + "srsupporter"))
                    team = "h";
                else if (player.hasPermission("group." + "supporter"))
                    team = "i";
                else if (player.hasPermission("group." + "content"))
                    team = "j";
                else if (player.hasPermission("group." + "youtuber"))
                    team = "k";
                else if (player.hasPermission("group." + "vip+"))
                    team = "l";
                else if (player.hasPermission("group." + "diamond"))
                    team = "m";
                else if (player.hasPermission("group." + "emerald"))
                    team = "n";
                else if (player.hasPermission("group." + "gold"))
                    team = "o";
            } else if (coreSpigot.getModuleManager().getServerType().equals(ServerType.CITY_BUILD)) {
                if (player.hasPermission("group." + "administrator"))
                    team = "a";
                else if (player.hasPermission("group." + "srmoderator"))
                    team = "b";
                else if (player.hasPermission("group." + "moderator"))
                    team = "c";
                else if (player.hasPermission("group." + "srdeveloper"))
                    team = "d";
                else if (player.hasPermission("group." + "developer"))
                    team = "e";
                else if (player.hasPermission("group." + "srbuilder"))
                    team = "f";
                else if (player.hasPermission("group." + "builder"))
                    team = "g";
                else if (player.hasPermission("group." + "srsupporter"))
                    team = "h";
                else if (player.hasPermission("group." + "supporter"))
                    team = "i";
                else if (player.hasPermission("group." + "content"))
                    team = "j";
                else if (player.hasPermission("group." + "youtuber"))
                    team = "k";
                else if (player.hasPermission("group." + "vip+"))
                    team = "l";
                else if (player.hasPermission("group." + "supreme"))
                    team = "m";
                else if (player.hasPermission("group." + "prime"))
                    team = "n";
                else if (player.hasPermission("group." + "premium"))
                    team = "o";
            } else if (coreSpigot.getModuleManager().getServerType().equals(ServerType.SKY_PVP)) {
                if (player.hasPermission("group." + "administrator"))
                    team = "a";
                else if (player.hasPermission("group." + "srmoderator"))
                    team = "b";
                else if (player.hasPermission("group." + "moderator"))
                    team = "c";
                else if (player.hasPermission("group." + "srdeveloper"))
                    team = "d";
                else if (player.hasPermission("group." + "developer"))
                    team = "e";
                else if (player.hasPermission("group." + "srbuilder"))
                    team = "f";
                else if (player.hasPermission("group." + "builder"))
                    team = "g";
                else if (player.hasPermission("group." + "srsupporter"))
                    team = "h";
                else if (player.hasPermission("group." + "supporter"))
                    team = "i";
                else if (player.hasPermission("group." + "content"))
                    team = "j";
                else if (player.hasPermission("group." + "youtuber"))
                    team = "k";
                else if (player.hasPermission("group." + "vip+"))
                    team = "l";
                else if (player.hasPermission("group." + "king"))
                    team = "m";
                else if (player.hasPermission("group." + "elite"))
                    team = "n";
                else if (player.hasPermission("group." + "ultra"))
                    team = "o";
            }
        //}

        scoreboard.getTeam(team).addEntry(player.getName());
        String name = scoreboard.getTeam(team).getPrefix() + player.getName() + " ";
        //if (coreSpigot.getNickManager().isDisguised(player))
        //name = scoreboard.getTeam(team).getPrefix() + coreSpigot.getNickManager().getPlayerName().get(player) + " ";
        player.setDisplayName(scoreboard.getTeam(team).getPrefix().split("§")[1] + player.getName());
        player.setPlayerListName(name);

        for (Player all : Bukkit.getOnlinePlayers()) {
            all.setScoreboard(scoreboard);
        }
    }
}
