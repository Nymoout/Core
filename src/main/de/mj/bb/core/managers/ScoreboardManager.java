/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package main.de.mj.bb.core.managers;


import cloud.timo.TimoCloud.api.TimoCloudAPI;
import cloud.timo.TimoCloud.api.objects.PlayerObject;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import main.de.mj.bb.core.CoreSpigot;
import main.de.mj.bb.core.listener.SettingsListener;
import main.de.mj.bb.core.utils.SchedulerSaver;
import me.lucko.luckperms.api.Contexts;
import me.lucko.luckperms.api.User;
import me.lucko.luckperms.api.caching.MetaData;
import me.lucko.luckperms.api.context.ContextManager;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class ScoreboardManager {

    static SettingsListener settingsListener;
    private final CoreSpigot coreSpigot;
    SchedulerSaver schedulerSaver;

    public ScoreboardManager(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        settingsListener = coreSpigot.getModuleManager().getSettingsListener();
        schedulerSaver = coreSpigot.getModuleManager().getSchedulerSaver();
    }

    private static void sendPacket(@SuppressWarnings("rawtypes") Packet packet, Player p) {
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }

    public void setScoreboard(Player p) {
        HookManager lobby = this.coreSpigot.getHookManager();
        Scoreboard scoreboard = new Scoreboard();
        String color;
        if (coreSpigot.getModuleManager().getSettingsListener().getColor().containsKey(p)) {
            color = coreSpigot.getModuleManager().getSettingsListener().getColor().get(p);
        } else {
            coreSpigot.getModuleManager().getSettingsListener().ItemColToString(p);
            color = "6";
        }
        ScoreboardObjective obj = scoreboard.registerObjective("zagd", IScoreboardCriteria.b);
        obj.setDisplayName("§7§l× §" + color + "§lBattleBuild§7§l ×");
        PacketPlayOutScoreboardObjective createPacket = new PacketPlayOutScoreboardObjective(obj, 0);
        PacketPlayOutScoreboardDisplayObjective display = new PacketPlayOutScoreboardDisplayObjective(1, obj);
        ScoreboardScore s1 = new ScoreboardScore(scoreboard, obj, "§a§lDeine Coins");
        ScoreboardScore s2;
        s2 = new ScoreboardScore(scoreboard, obj, "§7➟ §" + color + String.valueOf(coreSpigot.getHookManager().getEconomy().getBalance(p)));
        ScoreboardScore s3 = new ScoreboardScore(scoreboard, obj, "§8§7");

        ScoreboardScore s4 = new ScoreboardScore(scoreboard, obj, "§a§lDein Rang");
        ScoreboardScore s5;
        User user = lobby.getLuckPermsApi().getUser(p.getUniqueId());
        ContextManager cm = lobby.getLuckPermsApi().getContextManager();
        Contexts contexts = cm.lookupApplicableContexts(user).orElse(cm.getStaticContexts());
        MetaData md = user.getCachedData().getMetaData(contexts);
        String prefix;
        if (md.getPrefix() != null) {
            prefix = md.getPrefix().replace("&", "§").replace("»", " ");
        } else {
            prefix = "";
        }
        s5 = new ScoreboardScore(scoreboard, obj, "§7➟ §" + color + prefix);
        ScoreboardScore s6 = new ScoreboardScore(scoreboard, obj, "§7§9 ");

        ScoreboardScore s8 = new ScoreboardScore(scoreboard, obj, "§a§lDein Clan");
        ScoreboardScore s9;
        PAFPlayer pafp = lobby.getPafPlayerManager().getPlayer(p.getUniqueId());
        int i = 0;
        try {
            for (PAFPlayer all : lobby.getClansManager().getClan(pafp).getAllPlayers()) {
                for (PlayerObject online : TimoCloudAPI.getUniversalAPI().getProxy("Proxy").getOnlinePlayers()) {
                    if (online.getUuid().equals(all.getUniqueId())) {
                        i++;
                    }
                }
            }
        } catch (NullPointerException ex) {
        }
        if (lobby.getClansManager().getClan(pafp) != null) {
            s9 = new ScoreboardScore(scoreboard, obj,
                    "§7➟ §" + color + lobby.getClansManager().getClan(pafp).getClanTag() + "§7 [§" + color + i
                            + "§7§k|§r§" + color + lobby.getClansManager().getClan(pafp).getAllPlayers().size() + "§7]");
        } else {
            s9 = new ScoreboardScore(scoreboard, obj, "§7➟ §7");
        }
        ScoreboardScore s7 = new ScoreboardScore(scoreboard, obj, "§7§f ");

        ScoreboardScore s11 = new ScoreboardScore(scoreboard, obj, "§a§lServer");
        ScoreboardScore s12 = new ScoreboardScore(scoreboard, obj, "§7➟ §" + color + p.getServer().getServerName());
        ScoreboardScore s13 = new ScoreboardScore(scoreboard, obj, "§7§5 ");

        ScoreboardScore s14 = new ScoreboardScore(scoreboard, obj, "§a§lOnline Freunde");
        ScoreboardScore s15;
        int f = 0;
        for (PAFPlayer friends : pafp.getFriends()) {
            for (PlayerObject online : TimoCloudAPI.getUniversalAPI().getProxy("Proxy").getOnlinePlayers()) {
                if (online.getUuid().equals(friends.getUniqueId())) {
                    f++;
                }
            }
        }
        if (pafp.getFriends() != null) {
            s15 = new ScoreboardScore(scoreboard, obj, "§7➟ §" + color + f + "§7§k|§r§" + color + pafp.getFriends().size());
        } else {
            s15 = new ScoreboardScore(scoreboard, obj, "§7➟ §" + color + "0§7§k|§r§" + color + "0");
        }
        ScoreboardScore s16 = new ScoreboardScore(scoreboard, obj, "§7§6 ");
        ScoreboardScore s17 = new ScoreboardScore(scoreboard, obj, "§a§lOnline-Zeit");
        int playtime = lobby.getNetworkManagerBridge().getPlayer(p.getUniqueId()).get().getPlaytime();
        float tosecond = playtime / 1000;
        float tominute = tosecond / 60;
        float tohour = tominute / 60;
        int finalhour = (int) Math.floor(tohour);
        ScoreboardScore s18 = new ScoreboardScore(scoreboard, obj, "§7➟ §" + color + finalhour + "h");
        s1.setScore(16);
        s2.setScore(15);
        s3.setScore(14);
        s4.setScore(13);
        s5.setScore(12);
        s6.setScore(11);
        s8.setScore(10);
        s9.setScore(9);
        s7.setScore(8);
        s11.setScore(7);
        s12.setScore(6);
        s13.setScore(5);
        s14.setScore(4);
        s15.setScore(3);
        s16.setScore(2);
        s17.setScore(1);
        s18.setScore(0);

        PacketPlayOutScoreboardObjective removePacket = new PacketPlayOutScoreboardObjective(obj, 1);
        PacketPlayOutScoreboardScore pa1 = new PacketPlayOutScoreboardScore(s1);
        PacketPlayOutScoreboardScore pa2 = new PacketPlayOutScoreboardScore(s2);
        PacketPlayOutScoreboardScore pa3 = new PacketPlayOutScoreboardScore(s3);
        PacketPlayOutScoreboardScore pa4 = new PacketPlayOutScoreboardScore(s4);
        PacketPlayOutScoreboardScore pa5 = new PacketPlayOutScoreboardScore(s5);
        PacketPlayOutScoreboardScore pa6 = new PacketPlayOutScoreboardScore(s6);
        PacketPlayOutScoreboardScore pa7 = new PacketPlayOutScoreboardScore(s7);
        PacketPlayOutScoreboardScore pa8 = new PacketPlayOutScoreboardScore(s8);
        PacketPlayOutScoreboardScore pa9 = new PacketPlayOutScoreboardScore(s9);
        PacketPlayOutScoreboardScore pa11 = new PacketPlayOutScoreboardScore(s11);
        PacketPlayOutScoreboardScore pa12 = new PacketPlayOutScoreboardScore(s12);
        PacketPlayOutScoreboardScore pa13 = new PacketPlayOutScoreboardScore(s13);
        PacketPlayOutScoreboardScore pa14 = new PacketPlayOutScoreboardScore(s14);
        PacketPlayOutScoreboardScore pa15 = new PacketPlayOutScoreboardScore(s15);
        PacketPlayOutScoreboardScore pa16 = new PacketPlayOutScoreboardScore(s16);
        PacketPlayOutScoreboardScore pa17 = new PacketPlayOutScoreboardScore(s17);
        PacketPlayOutScoreboardScore pa18 = new PacketPlayOutScoreboardScore(s18);

        sendPacket(removePacket, p);
        sendPacket(createPacket, p);
        sendPacket(display, p);

        if (coreSpigot.getModuleManager().getSettingsListener().getScoreCoins().contains(p)) {
            sendPacket(pa1, p);
            sendPacket(pa2, p);
            sendPacket(pa3, p);
        }
        if (coreSpigot.getModuleManager().getSettingsListener().getScoreRank().contains(p)) {
            sendPacket(pa4, p);
            sendPacket(pa5, p);
            sendPacket(pa6, p);
        }
        if (coreSpigot.getModuleManager().getSettingsListener().getScoreClan().contains(p)) {
            sendPacket(pa8, p);
            sendPacket(pa9, p);
            sendPacket(pa7, p);
        }
        if (coreSpigot.getModuleManager().getSettingsListener().getScoreServer().contains(p)) {
            sendPacket(pa11, p);
            sendPacket(pa12, p);
            sendPacket(pa13, p);
        }
        if (coreSpigot.getModuleManager().getSettingsListener().getScoreFriends().contains(p)) {
            sendPacket(pa14, p);
            sendPacket(pa15, p);
        }
        if (coreSpigot.getModuleManager().getSettingsListener().getScoreClan().contains(p)) {
            sendPacket(pa16, p);
            sendPacket(pa17, p);
            sendPacket(pa18, p);
        }
    }

    public void ScoreboardActu() {
        schedulerSaver.createScheduler(
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            setScoreboard(all);
                        }
                    }
                }.runTaskTimerAsynchronously(this.coreSpigot, 0L, 20L)
        );
    }
}
