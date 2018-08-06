/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.lobby.utils;

import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.spigot.clans.api.ClansManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public class TabList {
    static Scoreboard scoreboard;

    public TabList() {
    }

    public void loadTablist() {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        scoreboard.registerNewTeam("a").setPrefix("§4§lA §4| ");
        scoreboard.registerNewTeam("b").setPrefix("§c§lSrM §c| ");
        scoreboard.registerNewTeam("c").setPrefix("§c§lM §c| ");
        scoreboard.registerNewTeam("d").setPrefix("§3§lSrD §3| ");
        scoreboard.registerNewTeam("e").setPrefix("§3§lD §3| ");
        scoreboard.registerNewTeam("f").setPrefix("§2§lSrB §2| ");
        scoreboard.registerNewTeam("g").setPrefix("§2§lB §2| ");
        scoreboard.registerNewTeam("h").setPrefix("§9§lSrS §9| ");
        scoreboard.registerNewTeam("i").setPrefix("§9§lS §9| ");
        scoreboard.registerNewTeam("j").setPrefix("§5§lYT §5| ");
        scoreboard.registerNewTeam("k").setPrefix("§6VIP+ §6| ");
        scoreboard.registerNewTeam("l").setPrefix("§7");
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
        } else {
            team = "l";
        }

        scoreboard.getTeam(team).addEntry(p.getName());
        PAFPlayer pafPlayer = PAFPlayerManager.getInstance().getPlayer(p.getUniqueId());
        String clan;
        if (ClansManager.getInstance().getClan(pafPlayer) != null) {
            clan = "§e" + ClansManager.getInstance().getClan(pafPlayer).getClanTag();
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
