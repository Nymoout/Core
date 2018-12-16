package main.de.mj.bb.core.utils;

import main.de.mj.bb.core.CoreBungee;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BungeeTabList implements Listener {

    private final CoreBungee coreBungee;
    private Map<ProxiedPlayer, Short> design = new HashMap<>();
    private Map<ProxiedPlayer, String> color = new HashMap<>();

    public BungeeTabList(@NotNull CoreBungee coreBungee) {
        this.coreBungee = coreBungee;
        coreBungee.registerListener(this);
    }

    private void setTabList(ProxiedPlayer proxiedPlayer, String color) {
        TextComponent header = new TextComponent();
        header.setText("\n §7§7§m------------------------------ \n§8✘ §" + color + "§lBattleBuild.net - Dein Netzwerk§7 §8✘ \n §8» §7Spieler §8➵ §" + color + coreBungee.getProxy().getOnlineCount() + " §7| §" + color + coreBungee.getProxy().getConfig().getPlayerLimit() + " §8« \n");
        TextComponent footer = new TextComponent();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String server = " ";
        try {
            server = proxiedPlayer.getServer().getInfo().getName();
        } catch (Exception ignored) {}
        footer.setText("\n §7Uhrzeit §8➵ §" + color + dateFormat.format(date) + "Uhr §8✕ §7Server §8➵ §" + color + server + " \n §7TS §8➵ §" + color + "battlebuild.net §8✕ §7Discord §8➵ §" + color + "discord.battlebuild.net \n §7Forum §8➵ §" + color + "forum.battlebuild.net \n §7§m------------------------------ \n");
        proxiedPlayer.setTabHeader(header, footer);
    }

    public void schedule() {
        coreBungee.getProxy().getScheduler().schedule(coreBungee, new Runnable() {
            @Override
            public void run() {
                for (ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                    coreBungee.getModuleManager().getBungeeAPI().getColor(all);
                    setTabList(all, color.get(all));
                }
            }
        }, 0L, 2L, TimeUnit.SECONDS);
    }

    public Map<ProxiedPlayer, Short> getDesign() {
        return design;
    }

    public void ItemColToString(ProxiedPlayer p) {
        if (design.containsKey(p)) {
            if (design.get(p) == 0) {
                color.put(p, "f");
            } else if (design.get(p) == 1) {
                color.put(p, "6");
            } else if (design.get(p) == 2) {
                color.put(p, "5");
            } else if (design.get(p) == 3) {
                color.put(p, "b");
            } else if (design.get(p) == 4) {
                color.put(p, "e");
            } else if (design.get(p) == 5) {
                color.put(p, "a");
            } else if (design.get(p) == 6) {
                color.put(p, "d");
            } else if (design.get(p) == 7) {
                color.put(p, "8");
            } else if (design.get(p) == 8) {
                color.put(p, "7");
            } else if (design.get(p) == 9) {
                color.put(p, "3");
            } else if (design.get(p) == 10) {
                color.put(p, "5");
            } else if (design.get(p) == 11) {
                color.put(p, "9");
            } else if (design.get(p) == 12) {
                color.put(p, "f");
            } else if (design.get(p) == 13) {
                color.put(p, "2");
            } else if (design.get(p) == 14) {
                color.put(p, "c");
            } else if (design.get(p) == 15) {
                color.put(p, "0");
            }
        }
    }
}
