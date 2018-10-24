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

    public void setTabList() {
        for (ProxiedPlayer proxiedPlayer : coreBungee.getProxy().getPlayers()) {
            TextComponent header = new TextComponent();
            header.setText("§6§lWillkommen auf §a§lBattlebuild.net! \n §aDerzeit sind §e" + coreBungee.getProxy().getOnlineCount() + " | " + coreBungee.getProxy().getConfig().getPlayerLimit() + " §aSpieler online. \n");
            TextComponent footer = new TextComponent();
            DateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Date date = new Date();
            footer.setText("\n§6Derzeit ist es §e" + dateFormat.format(date) + " Uhr!\n" + "§bDu befindest dich derzeit auf dem Server §9§l " + proxiedPlayer.getServer().getInfo().getName() + ".");
            proxiedPlayer.setTabHeader(header, footer);
        }
    }

    public void schedule() {
        coreBungee.getProxy().getScheduler().schedule(coreBungee, this::setTabList, 0L, 10, TimeUnit.SECONDS);
    }
}
