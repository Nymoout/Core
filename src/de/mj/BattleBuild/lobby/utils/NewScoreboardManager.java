package de.mj.BattleBuild.lobby.utils;

public class NewScoreboardManager {
    /*
    private final Lobby plugin;
    private SettingsListener settingsListener = new SettingsListener();

    public NewScoreboardManager(Lobby plugin) {
        this.plugin = plugin;
    }

    public Scoreboard getScoreboard(Player player) {
        if (player.hasMetadata("scoreboard"))
            return (Scoreboard) player.getMetadata("scoreboard").get(0).value();
        Scoreboard scoreboard = plugin.getServer().getScoreboardManager().getNewScoreboard();
        plugin.setMetadata(player, "scoreboard", scoreboard);
        return scoreboard;
    }

    public void setSidebar(Player player, HashMap<String, Integer> sidebar, String displayname) {
        Scoreboard scoreboard = getScoreboard(player);
        Objective objective = scoreboard.getObjective(player.getName());
        objective.setDisplayName(displayname);
        if (objective != null)
            objective.unregister();
        objective = scoreboard.registerNewObjective(player.getName(), "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        for (String str : sidebar.keySet())
            objective.getScore(str).setScore(sidebar.get(str));
        player.setScoreboard(scoreboard);
    }

    public void createTeam(Player player, String name, String entry) {
        Scoreboard scoreboard = getScoreboard(player);
        Team team = scoreboard.getTeam(name);
        if (team == null)
            team = scoreboard.registerNewTeam(name);
        team.addEntry(entry);
    }

    public void setScoreboardEntrys(Player p) {
        String color;
        if (settingsListener.color.containsKey(p)) {
            color = "§" + settingsListener.color.get(p);
            ActionbarTimer.action.replace(p, true);
        } else {
            settingsListener.ItemColToString(p);
            color = "§6";
        }
        if (settingsListener.scoins.contains(p)) {
            createTeam(p, "coinstitle", "§f§lDeine Coins §8:");
            createTeam(p, "coins", "");
        }
        if (settingsListener.srang.contains(p)) {
            createTeam(p, "rangtitle", "§f§lDein Rang §8:");
            createTeam(p, "rang", "");
        }
        if (settingsListener.sclan.contains(p)) {
            createTeam(p, "clantitle", "§f§lDein Clan §8:");
            createTeam(p, "clan", "");
        }
        if (settingsListener.sserver.contains(p)) {
            createTeam(p, "servertitle", "§f§lServer §8:");
            createTeam(p, "server", "");
        }
        if (settingsListener.sfriends.contains(p)) {
            createTeam(p, "friendstitle", "§f§lFreunde §8:");
            createTeam(p, "friends", "");
        }
        if (settingsListener.szeit.contains(p)) {
            createTeam(p, "timetitle", "§f§lSpielzeit §8:");
            createTeam(p, "time", "");
        }
        ScoreboardScore s1 = new ScoreboardScore(scoreboard, obj, "§a§lDeine Coins §8:");
        ScoreboardScore s2 = null;
        s2 = new ScoreboardScore(scoreboard, obj, "§8\u00BB §" + color + String.valueOf(Lobby.getEconomy().getBalance(p.getName())));
        ScoreboardScore s3 = new ScoreboardScore(scoreboard, obj, "§8§7");

        ScoreboardScore s4 = new ScoreboardScore(scoreboard, obj, "§a§lDein Rang §8:");
        ScoreboardScore s5 = null;
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
        ScoreboardScore s9 = null;
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
        ScoreboardScore s15 = null;
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
    }

    public void actuScoreboard() {
        new BukkitRunnable() {
            int sec = 10;
            public void run() {
                if (sec == 9) {
                    for (Player all : Bukkit.getOnlinePlayers()) {

                    }
                }
                if (sec == 5) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        setScoreboardEntrys(all);
                    }
                }
                if (sec == 0)
                    sec = 10;
                sec--;
            }
        }.runTaskTimerAsynchronously(plugin, 0L, 20L);
    }
    */
}
