package main.de.mj.bb.core.commands;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import cloud.timo.TimoCloud.api.objects.ServerGroupObject;
import cloud.timo.TimoCloud.api.objects.ServerObject;
import main.de.mj.bb.core.CoreBungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CloudCommand extends Command {

    private final CoreBungee coreBungee;

    public CloudCommand(CoreBungee coreBungee) {
        super("cloud", "core.cloud");
        this.coreBungee = coreBungee;
        coreBungee.registerCommand(this);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (commandSender instanceof ProxiedPlayer) {
            if (strings.length == 2)
                if (strings[0].equalsIgnoreCase("restart"))
                    if (TimoCloudAPI.getUniversalAPI().getServerGroup(strings[1]) != null) {
                        ServerGroupObject serverGroupObject = TimoCloudAPI.getUniversalAPI().getServerGroup(strings[1]);
                        for (ServerObject serverObject : serverGroupObject.getServers()) {
                            serverObject.stop();
                        }
                    } else if (TimoCloudAPI.getUniversalAPI().getServer(strings[1]) != null)
                        TimoCloudAPI.getUniversalAPI().getServer(strings[1]).stop();
                    else if (TimoCloudAPI.getUniversalAPI().getProxy(strings[1]) != null)
                        TimoCloudAPI.getUniversalAPI().getProxy(strings[1]).stop();
                    else if (strings[1].equalsIgnoreCase("all")) {
                        TimoCloudAPI.getUniversalAPI().getServerGroups().forEach(serverGroupObject ->
                                TimoCloudAPI.getUniversalAPI().getServerGroup(serverGroupObject.getName()).getServers().forEach(ServerObject::stop));
                    }
                    else commandSender.sendMessage(new TextComponent(coreBungee.getModuleManager().getData().getPrefix() + "Die Servergruppe / der Server / die Proxy §c" + strings[1] + "§7 existiert nicht!"));
                else commandSender.sendMessage(new TextComponent(coreBungee.getModuleManager().getData().getPrefix() + "Bitte benutze /cloud restart <Gruppe/Proxy/Server>"));
            else  commandSender.sendMessage(new TextComponent(coreBungee.getModuleManager().getData().getPrefix() + "Bitte benutze /cloud restart <Gruppe/Proxy/Server>"));
        } else commandSender.sendMessage(new TextComponent(coreBungee.getModuleManager().getData().getOnlyPlayer()));
    }
}
