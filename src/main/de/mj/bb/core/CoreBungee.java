package main.de.mj.bb.core;

import lombok.Getter;
import main.de.mj.bb.core.commands.BanCommand;
import main.de.mj.bb.core.listener.LoginListener;
import main.de.mj.bb.core.listener.ServerSwitchListener;
import main.de.mj.bb.core.managers.BungeeHookManager;
import main.de.mj.bb.core.utils.BungeeTabList;
import main.de.mj.bb.core.utils.Data;
import main.de.mj.bb.core.utils.FinalBan;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.logging.Logger;

@Getter
public class CoreBungee extends Plugin {

    private Logger logger = this.getLogger();
    private Data data = new Data();
    private BungeeHookManager hookManager;
    private BungeeTabList bungeeTablist;
    private FinalBan finalBan;

    public void onEnable() {
        hookManager = new BungeeHookManager(this);
        hookManager.hook();
        ProxyServer.getInstance().registerChannel("ban");
        new BanCommand(this);
        new LoginListener(this);
        bungeeTablist = new BungeeTabList(this);
        bungeeTablist.schedule();
        new ServerSwitchListener(this);
        finalBan = new FinalBan(this);
    }

    public void registerListener(Listener listener) {
        getProxy().getPluginManager().registerListener(this, listener);
    }

    public void registerCommand(Command command) {
        getProxy().getPluginManager().registerCommand(this, command);
    }
}
