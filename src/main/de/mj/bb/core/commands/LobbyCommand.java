package main.de.mj.bb.core.commands;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import main.de.mj.bb.core.CoreBungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.concurrent.ThreadLocalRandom;

public class LobbyCommand extends Command {

    private final CoreBungee coreBungee;

    public LobbyCommand(CoreBungee coreBungee) {
        super("lobby", "", "hub", "l");
        this.coreBungee = coreBungee;
        coreBungee.registerCommand(this);
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer proxiedPlayer = (ProxiedPlayer) commandSender;
            if (proxiedPlayer.getServer().getInfo().getName().contains("Lobby"))
                proxiedPlayer.sendMessage(new TextComponent(coreBungee.getData().getPrefix() + "Du befindest dich bereits auf einer Lobby!"));
            else {
                int online = ThreadLocalRandom.current().nextInt(1, TimoCloudAPI.getUniversalAPI().getServerGroup("Lobby").getOnlineAmount() + 1);
                ServerInfo serverInfo = ProxyServer.getInstance().getServers().get("Lobby-" + online);
                proxiedPlayer.connect(serverInfo);
                proxiedPlayer.sendMessage(new TextComponent(coreBungee.getData().getPrefix() + "Du wurdest erfolgreich auf §6Lobby-" + online + " §7verbunden!"));
            }
        } else commandSender.sendMessage(new TextComponent(coreBungee.getData().getOnlyPlayer()));
    }
}
