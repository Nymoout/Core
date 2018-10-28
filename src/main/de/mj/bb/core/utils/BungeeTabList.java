package main.de.mj.bb.core.utils;

import main.de.mj.bb.core.CoreBungee;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BungeeTabList implements Listener {

    private final CoreBungee coreBungee;

    public BungeeTabList(CoreBungee coreBungee) {
        this.coreBungee = coreBungee;
        coreBungee.registerListener(this);
    }

    private int i = 6;
    public void setTabList() {
        if (i == 6) {
            for (ProxiedPlayer proxiedPlayer : coreBungee.getProxy().getPlayers()) {
                TextComponent header = new TextComponent();
                header.setText("§6§lWillkommen auf §a§lBattlebuild.net! \n §aDerzeit sind §e" + coreBungee.getProxy().getOnlineCount() + " | " + coreBungee.getProxy().getConfig().getPlayerLimit() + " §aSpieler online. \n");
                TextComponent footer = new TextComponent();
                DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                Date date = new Date();
                String server = proxiedPlayer.getServer().getInfo().getName();
                footer.setText("\n§6Derzeit ist es §e" + dateFormat.format(date) + " Uhr!\n" + "§bDu befindest dich derzeit auf dem Server §9§l " + server + ".");
                proxiedPlayer.setTabHeader(header, footer);
            }
        } else if (i == 5) {
            for (ProxiedPlayer proxiedPlayer : coreBungee.getProxy().getPlayers()) {
                TextComponent header = new TextComponent();
                header.setText("§6§l§kWillkommen auf §a§l§kBattlebuild.net!§r \n §aDerzeit sind §e" + coreBungee.getProxy().getOnlineCount() + " | " + coreBungee.getProxy().getConfig().getPlayerLimit() + " §aSpieler online. \n");
                TextComponent footer = new TextComponent();
                DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                Date date = new Date();
                String server = proxiedPlayer.getServer().getInfo().getName();
                footer.setText("\n§6Derzeit ist es §e" + dateFormat.format(date) + " Uhr!\n" + "§bDu befindest dich derzeit auf dem Server §9§l " + server + ".");
                proxiedPlayer.setTabHeader(header, footer);
            }
        } else if (i == 4) {
            for (ProxiedPlayer proxiedPlayer : coreBungee.getProxy().getPlayers()) {
                TextComponent header = new TextComponent();
                header.setText("§6§l§kViel §a§l§kSpaß!§r \n §aDerzeit sind §e" + coreBungee.getProxy().getOnlineCount() + " | " + coreBungee.getProxy().getConfig().getPlayerLimit() + " §aSpieler online. \n");
                TextComponent footer = new TextComponent();
                DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                Date date = new Date();
                String server = proxiedPlayer.getServer().getInfo().getName();
                footer.setText("\n§6Derzeit ist es §e" + dateFormat.format(date) + " Uhr!\n" + "§bDu befindest dich derzeit auf dem Server §9§l " + server + ".");
                proxiedPlayer.setTabHeader(header, footer);
            }
        } else if (i == 3) {
            for (ProxiedPlayer proxiedPlayer : coreBungee.getProxy().getPlayers()) {
                TextComponent header = new TextComponent();
                header.setText("§6§lViel §a§lSpaß! \n §aDerzeit sind §e" + coreBungee.getProxy().getOnlineCount() + " | " + coreBungee.getProxy().getConfig().getPlayerLimit() + " §aSpieler online. \n");
                TextComponent footer = new TextComponent();
                DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                Date date = new Date();
                String server = proxiedPlayer.getServer().getInfo().getName();
                footer.setText("\n§6Derzeit ist es §e" + dateFormat.format(date) + " Uhr!\n" + "§bDu befindest dich derzeit auf dem Server §9§l " + server + ".");
                proxiedPlayer.setTabHeader(header, footer);
            }
        } else if (i == 2) {
            for (ProxiedPlayer proxiedPlayer : coreBungee.getProxy().getPlayers()) {
                TextComponent header = new TextComponent();
                header.setText("§6§l§kViel §a§l§kSpaß!§r \n §aDerzeit sind §e" + coreBungee.getProxy().getOnlineCount() + " | " + coreBungee.getProxy().getConfig().getPlayerLimit() + " §aSpieler online. \n");
                TextComponent footer = new TextComponent();
                DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                Date date = new Date();
                String server = proxiedPlayer.getServer().getInfo().getName();
                footer.setText("\n§6Derzeit ist es §e" + dateFormat.format(date) + " Uhr!\n" + "§bDu befindest dich derzeit auf dem Server §9§l " + server + ".");
                proxiedPlayer.setTabHeader(header, footer);
            }
        } else if (i == 1) {
            for (ProxiedPlayer proxiedPlayer : coreBungee.getProxy().getPlayers()) {
                TextComponent header = new TextComponent();
                header.setText("§6§l§kWillkommen auf §a§l§kBattlebuild.net!§r \n §aDerzeit sind §e" + coreBungee.getProxy().getOnlineCount() + " | " + coreBungee.getProxy().getConfig().getPlayerLimit() + " §aSpieler online. \n");
                TextComponent footer = new TextComponent();
                DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                Date date = new Date();
                String server = proxiedPlayer.getServer().getInfo().getName();
                footer.setText("\n§6Derzeit ist es §e" + dateFormat.format(date) + " Uhr!\n" + "§bDu befindest dich derzeit auf dem Server §9§l " + server + ".");
                proxiedPlayer.setTabHeader(header, footer);
            }
        } else if (i == 0) i = 7;
        i--;
    }

    public void schedule() {
        coreBungee.getProxy().getScheduler().schedule(coreBungee, this::setTabList, 0L, 2, TimeUnit.SECONDS);
    }
}
