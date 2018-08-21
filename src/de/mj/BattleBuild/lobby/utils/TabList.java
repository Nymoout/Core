/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.lobby.utils;

import de.mj.BattleBuild.lobby.Lobby;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public class TabList {

    private final Lobby lobby;
    static Scoreboard scoreboard;

    public TabList(Lobby lobby) {
        this.lobby = lobby;
    }

    public void loadTablist() {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        scoreboard.registerNewTeam("a").setPrefix("§4§lA §8|§7 ");
        scoreboard.registerNewTeam("b").setPrefix("§c§lSrM §8|§7 ");
        scoreboard.registerNewTeam("c").setPrefix("§c§lM §8|§7 ");
        scoreboard.registerNewTeam("d").setPrefix("§3§lSrD §8|§7 ");
        scoreboard.registerNewTeam("e").setPrefix("§3§lD §8|§7 ");
        scoreboard.registerNewTeam("f").setPrefix("§2§lSrB §8|§7 ");
        scoreboard.registerNewTeam("g").setPrefix("§2§lB §8|§7 ");
        scoreboard.registerNewTeam("h").setPrefix("§9§lSrS §8|§7 ");
        scoreboard.registerNewTeam("i").setPrefix("§9§lS §8|§7 ");
        scoreboard.registerNewTeam("j").setPrefix("§5§lYT §8|§7 ");
        scoreboard.registerNewTeam("k").setPrefix("§6VIP+ §8|§7 ");
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
        PAFPlayer pafPlayer = lobby.getHookManager().getPafPlayerManager().getPlayer(p.getUniqueId());
        String clan;
        if (lobby.getHookManager().getClansManager().getClan(pafPlayer) != null) {
            clan = "§e" + lobby.getHookManager().getClansManager().getClan(pafPlayer).getClanTag();
        } else {
            clan = "";
        }
        String name = scoreboard.getTeam(team).getPrefix() + p.getName() + " ";
        if (name.length() > 16) {
            name.substring(0, 16);
            p.setDisplayName(name + clan);
        } else {
            p.setDisplayName(name + clan);
        }

        for (Player a : Bukkit.getOnlinePlayers()) {
            a.setScoreboard(scoreboard);
        }
    }
}
