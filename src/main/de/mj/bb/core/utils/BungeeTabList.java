package main.de.mj.bb.core.utils;

import main.de.mj.bb.core.CoreBungee;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BungeeTabList implements Listener {

    private final CoreBungee coreBungee;

    public BungeeTabList(@NotNull CoreBungee coreBungee) {
        this.coreBungee = coreBungee;
        coreBungee.registerListener(this);
    }

    public void setTabList(ProxiedPlayer proxiedPlayer) {
        TextComponent header = new TextComponent();
        header.setText("\n§8✘ §7Willkommen auf §6§lBattlebuild§7 §8✘ \n §8» §7Spieler §8➵ §e" + coreBungee.getProxy().getOnlineCount() + " §7| §e" + coreBungee.getProxy().getConfig().getPlayerLimit() + " §8« \n");
        TextComponent footer = new TextComponent();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String server = proxiedPlayer.getServer().getInfo().getName();
        footer.setText("\n §7Uhrzeit §8➵ §b" + dateFormat.format(date) + "Uhr §8✕ §7Server §8➵ §b" + server + " \n §7TS §8➵ §3battlebuild.net §8✕ §7Discord §8➵ §3discord.battlebuild.net \n ");
        proxiedPlayer.setTabHeader(header, footer);

    }

}
