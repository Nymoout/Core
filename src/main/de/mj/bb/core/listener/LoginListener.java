package main.de.mj.bb.core.listener;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import cloud.timo.TimoCloud.api.objects.ServerGroupObject;
import main.de.mj.bb.core.CoreBungee;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class LoginListener implements Listener {

    public LoginListener(CoreBungee coreBungee) {
        coreBungee.registerListener(this);
    }

    @EventHandler
    public void onLogin(PostLoginEvent loginEvent) {
        ProxiedPlayer player = loginEvent.getPlayer();
        ServerGroupObject serverGroupObject = TimoCloudAPI.getUniversalAPI().getServerGroup("Lobby");
        if (!(serverGroupObject.getOnlineAmount() > 0)) {
            player.disconnect(new TextComponent("§7[§6§lBattleBuild§7] \n §cUnser System wird gerade hochgefahren, \n §3bitte warte einen Moment \n §3bevor du dich erneut verbindest!"));
        }
    }
}
