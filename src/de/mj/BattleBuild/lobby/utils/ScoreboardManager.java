package de.mj.BattleBuild.lobby.utils;


import cloud.timo.TimoCloud.api.TimoCloudAPI;
import cloud.timo.TimoCloud.api.objects.PlayerObject;
import de.mj.BattleBuild.lobby.listener.SettingsListener;
import de.mj.BattleBuild.lobby.main.Lobby;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.spigot.clans.api.ClansManager;
import me.Dunios.NetworkManagerBridge.spigot.NetworkManagerBridge;
import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.Contexts;
import me.lucko.luckperms.api.User;
import me.lucko.luckperms.api.caching.MetaData;
import me.lucko.luckperms.api.context.ContextManager;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ScoreboardManager {

    private final Lobby lobby;
    static SettingsListener settingsListener;
    SchedulerSaver schedulerSaver;

    public ScoreboardManager(Lobby lobby) {
        this.lobby = lobby;
        settingsListener = lobby.getSettingsListener();
        schedulerSaver = lobby.getSchedulerSaver();
    }
    public void setBoardLOBBY(Player p) {
        Scoreboard scoreboard = new Scoreboard();
        String color;
        if (settingsListener.color.containsKey(p)) {
            color = settingsListener.color.get(p);
            ActionbarTimer.action.replace(p, true);
        } else {
            settingsListener.ItemColToString(p);
            color = "6";
        }
        ScoreboardObjective obj = scoreboard.registerObjective("zagd", IScoreboardCriteria.b);
        obj.setDisplayName("§" + color + "§lBattleBuild");
        PacketPlayOutScoreboardObjective createPacket = new PacketPlayOutScoreboardObjective(obj, 0);
        PacketPlayOutScoreboardDisplayObjective display = new PacketPlayOutScoreboardDisplayObjective(1, obj);
        ScoreboardScore s1 = new ScoreboardScore(scoreboard, obj, "§a§lDeine Coins §8:");
        ScoreboardScore s2;
        s2 = new ScoreboardScore(scoreboard, obj, "§8\u00BB §" + color + String.valueOf(lobby.getEconomy().getBalance(p)));
        ScoreboardScore s3 = new ScoreboardScore(scoreboard, obj, "§8§7");

        ScoreboardScore s4 = new ScoreboardScore(scoreboard, obj, "§a§lDein Rang §8:");
        ScoreboardScore s5;
        User user = LuckPerms.getApi().getUser(p.getUniqueId());
        ContextManager cm = LuckPerms.getApi().getContextManager();
        Contexts contexts = cm.lookupApplicableContexts(user).orElse(cm.getStaticContexts());
        MetaData md = user.getCachedData().getMetaData(contexts);
        String prefix;
        if (md.getPrefix() != null) {
            prefix = md.getPrefix().replace("&", "§").replace("»", " ");
        } else {
            prefix = "";
        }
        s5 = new ScoreboardScore(scoreboard, obj, "§8» §" + color + prefix);
        ScoreboardScore s6 = new ScoreboardScore(scoreboard, obj, "§7§9 ");

        ScoreboardScore s8 = new ScoreboardScore(scoreboard, obj, "§a§lDein Clan §8:");
        ScoreboardScore s9;
        PAFPlayer pafp = PAFPlayerManager.getInstance().getPlayer(p.getUniqueId());
        int i = 0;
        try {
            for (PAFPlayer all : ClansManager.getInstance().getClan(pafp).getAllPlayers()) {
                for (PlayerObject online : TimoCloudAPI.getUniversalAPI().getProxy("Proxy").getOnlinePlayers()) {
                    if (online.getUuid().equals(all.getUniqueId())) {
                        i++;
                    }
                }
            }
        } catch (NullPointerException ex) {
        }
        if (ClansManager.getInstance().getClan(pafp) != null) {
            s9 = new ScoreboardScore(scoreboard, obj,
                    "§8» §" + color + ClansManager.getInstance().getClan(pafp).getClanTag() + "§f [§" + color + i
                            + "§f/" + ClansManager.getInstance().getClan(pafp).getAllPlayers().size() + "]");
        } else {
            s9 = new ScoreboardScore(scoreboard, obj, "§8» §7");
        }
        ScoreboardScore s7 = new ScoreboardScore(scoreboard, obj, "§7§f ");

        ScoreboardScore s11 = new ScoreboardScore(scoreboard, obj, "§a§lServer");
        ScoreboardScore s12 = new ScoreboardScore(scoreboard, obj, "§8» §" + color + p.getServer().getServerName());
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
            s15 = new ScoreboardScore(scoreboard, obj, "§8» §" + color + f + "§f/§" + color + pafp.getFriends().size());
        } else {
            s15 = new ScoreboardScore(scoreboard, obj, "§8» §" + color + "0§f/§" + color + "0");
        }
        ScoreboardScore s16 = new ScoreboardScore(scoreboard, obj, "§7§6 ");
        ScoreboardScore s17 = new ScoreboardScore(scoreboard, obj, "§a§lOnline-Zeit");
        int playtime = NetworkManagerBridge.getInstance().getPlayer(p.getUniqueId()).getPlaytime();
        float tosecond = playtime / 1000;
        float tominute = tosecond / 60;
        float tohour = tominute / 60;
        int finalhour = (int) Math.floor(tohour);
        ScoreboardScore s18 = new ScoreboardScore(scoreboard, obj, "§8» §" + color + finalhour + "h");
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

        if (settingsListener.scoins.contains(p)) {
            sendPacket(pa1, p);
            sendPacket(pa2, p);
            sendPacket(pa3, p);
        }
        if (settingsListener.srang.contains(p)) {
            sendPacket(pa4, p);
            sendPacket(pa5, p);
            sendPacket(pa6, p);
        }
        if (settingsListener.sclan.contains(p)) {
            sendPacket(pa8, p);
            sendPacket(pa9, p);
            sendPacket(pa7, p);
        }
        if (settingsListener.sserver.contains(p)) {
            sendPacket(pa11, p);
            sendPacket(pa12, p);
            sendPacket(pa13, p);
        }
        if (settingsListener.sfriends.contains(p)) {
            sendPacket(pa14, p);
            sendPacket(pa15, p);
        }
        if (settingsListener.szeit.contains(p)) {
            sendPacket(pa16, p);
            sendPacket(pa17, p);
            sendPacket(pa18, p);
        }
    }

    private static void sendPacket(@SuppressWarnings("rawtypes") Packet packet, Player p) {
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }

    public void ScoreboardActu() {
        schedulerSaver.createScheduler(
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            setBoardLOBBY(all);
                        }
                    }
                }.runTaskTimer(this.lobby, 0L, 20L * 10)
        );
    }
}
