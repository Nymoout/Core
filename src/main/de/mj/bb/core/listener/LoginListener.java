package main.de.mj.bb.core.listener;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import cloud.timo.TimoCloud.api.objects.ServerGroupObject;
import main.de.mj.bb.core.CoreBungee;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.jetbrains.annotations.NotNull;

public class LoginListener implements Listener {

    private final CoreBungee coreBungee;

    public LoginListener(@NotNull CoreBungee coreBungee) {
        this.coreBungee = coreBungee;
        coreBungee.registerListener(this);
    }

    @EventHandler
    public void onLogin(PostLoginEvent loginEvent) {
        ProxiedPlayer player = loginEvent.getPlayer();
        ServerGroupObject serverGroupObject = TimoCloudAPI.getUniversalAPI().getServerGroup("Lobby");
        if (coreBungee.getHookManager().getNetworkManagerPlugin().getPlayer(player.getUniqueId()) == null) {
            System.out.println("Player " + player.getName() + " is null");
            return;
        }
        if (!(serverGroupObject.getOnlineAmount() > 0)) {
            player.disconnect(new TextComponent("§8[§6§lBattleBuild§8] \n §cUnser System wird gerade hochgefahren, \n §3bitte warte einen Moment \n §3bevor du dich erneut verbindest!"));
            return;
        }
        if (coreBungee.getHookManager().getNetworkManagerPlugin().getPlayer(player.getUniqueId()).isGlobalBanned()) {
            player.disconnect(new TextComponent(coreBungee.getHookManager().getNetworkManagerPlugin().getPlayer(player.getUniqueId()).getActiveBan().getReason()));
        }
        if (!coreBungee.getModuleManager().getBungeeAPI().checkPlayer(player))
            coreBungee.getModuleManager().getBungeeAPI().createPlayer(player);
    }

    @EventHandler
    public void onServerConnect(ServerConnectEvent connectEvent) {
        if (connectEvent.getPlayer().getServer() == null) {
            if (coreBungee.getModuleManager().getMaintenanceCommand().isMaintenance())
                if (!connectEvent.getPlayer().hasPermission("group.vip+")) {
                    connectEvent.setCancelled(true);
                    connectEvent.getPlayer().disconnect(new TextComponent(coreBungee.getData().getPrefix() + "\n§cWir befinden uns derzeit im Wartungsmodus!\n§7Der Server wird am §302§7.§301§7.§32018 §7eröffnet!"));
                }
        }
    }
}
