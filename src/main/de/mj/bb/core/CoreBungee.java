package main.de.mj.bb.core;

import main.de.mj.bb.core.managers.BungeeHookManager;
import main.de.mj.bb.core.managers.BungeeModuleManager;
import main.de.mj.bb.core.utils.Data;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.logging.Logger;

public class CoreBungee extends Plugin {

    private Logger logger = this.getLogger();
    private Data data = new Data();
    private BungeeHookManager hookManager;
    private BungeeModuleManager moduleManager;

    public void onEnable() {
        hookManager = new BungeeHookManager(this);
        hookManager.hook();
        moduleManager = new BungeeModuleManager(this);
        moduleManager.init();
        ProxyServer.getInstance().registerChannel("ban");
    }

    public void registerListener(Listener listener) {
        getProxy().getPluginManager().registerListener(this, listener);
    }

    public void registerCommand(Command command) {
        getProxy().getPluginManager().registerCommand(this, command);
    }

    public Logger getLogger() {
        return this.logger;
    }

    public Data getData() {
        return this.data;
    }

    public BungeeHookManager getHookManager() {
        return this.hookManager;
    }

    public BungeeModuleManager getModuleManager() {
        return this.moduleManager;
    }
}
