package main.de.mj.bb.core;

import lombok.Getter;
import main.de.mj.bb.core.managers.BungeeHookManager;
import main.de.mj.bb.core.managers.BungeeModuleManager;
import main.de.mj.bb.core.utils.Data;
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
    private BungeeModuleManager moduleManager;

    public void onEnable() {
        hookManager = new BungeeHookManager(this);
        hookManager.hook();
        moduleManager.init();
        ProxyServer.getInstance().registerChannel("ban");
    }

    public void registerListener(Listener listener) {
        getProxy().getPluginManager().registerListener(this, listener);
    }

    public void registerCommand(Command command) {
        getProxy().getPluginManager().registerCommand(this, command);
    }
}
