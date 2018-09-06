/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package main.de.mj.bb.core.utils;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public class TabList {

    private final CoreSpigot coreSpigot;
    static Scoreboard scoreboard;

    public TabList(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
    }

    public void loadTablist() {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        scoreboard.registerNewTeam("a").setPrefix("§4§lAdmin §8|§7 ");
        scoreboard.registerNewTeam("b").setPrefix("§c§lSrMod §8|§7 ");
        scoreboard.registerNewTeam("c").setPrefix("§c§lMod §8|§7 ");
        scoreboard.registerNewTeam("d").setPrefix("§3§lSrDev §8|§7 ");
        scoreboard.registerNewTeam("e").setPrefix("§3§lDev §8|§7 ");
        scoreboard.registerNewTeam("f").setPrefix("§2§lSrB §8|§7 ");
        scoreboard.registerNewTeam("g").setPrefix("§2§lB §8|§7 ");
        scoreboard.registerNewTeam("h").setPrefix("§9§lSrSup §8|§7 ");
        scoreboard.registerNewTeam("i").setPrefix("§9§lSup §8|§7 ");
        scoreboard.registerNewTeam("j").setPrefix("§5§lYT §8|§7 ");
        scoreboard.registerNewTeam("k").setPrefix("§eVIP+ §8|§7 ");
        scoreboard.registerNewTeam("l").setPrefix("§bDiamond §8|§7 ");
        scoreboard.registerNewTeam("m").setPrefix("§aEmerald §8|§7 ");
        scoreboard.registerNewTeam("n").setPrefix("§6Gold §8|§7 ");
        scoreboard.registerNewTeam("o").setPrefix("§7");
    }

    public void setPrefix(Player p) {
        String team;
        if (p.hasPermission("group." + "owner")) {
            team = "a";
        } else if (p.hasPermission("group." + "srmoderator")) {
            team = "b";
        } else if (p.hasPermission("group." + "moderator")) {
            team = "c";
        } else if (p.hasPermission("group." + "srdeveloper")) {
            team = "d";
        } else if (p.hasPermission("group." + "developer")) {
            team = "e";
        } else if (p.hasPermission("group." + "srbuilder")) {
            team = "f";
        } else if (p.hasPermission("group." + "builder")) {
            team = "g";
        } else if (p.hasPermission("group." + "srsupporter")) {
            team = "h";
        } else if (p.hasPermission("group." + "supporter")) {
            team = "i";
        } else if (p.hasPermission("group." + "youtuber")) {
            team = "j";
        } else if (p.hasPermission("group." + "vip+")) {
            team = "k";
        } else if (p.hasPermission("group." + "diamond")) {
            team = "l";
        } else if (p.hasPermission("group." + "emerald")) {
            team = "m";
        } else if (p.hasPermission("group." + "gold")) {
            team = "n";
        } else {
            team = "o";
        }

        scoreboard.getTeam(team).addEntry(p.getName());
        String name = scoreboard.getTeam(team).getPrefix() + p.getName() + " ";
        if (name.length() > 16) {
            String subName = name.substring(0, 16);
            p.setDisplayName(subName);
        } else {
            p.setDisplayName(name);
        }

        for (Player a : Bukkit.getOnlinePlayers()) {
            a.setScoreboard(scoreboard);
        }
    }
}
