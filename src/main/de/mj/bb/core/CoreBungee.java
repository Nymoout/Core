package main.de.mj.bb.core;

import main.de.mj.bb.core.managers.BungeeHookManager;
import main.de.mj.bb.core.managers.BungeeModuleManager;
import main.de.mj.bb.core.utils.BungeeType;
import main.de.mj.bb.core.utils.Data;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Logger;

public class CoreBungee extends Plugin {

    private Logger logger = this.getLogger();
    private Data data = new Data();
    private BungeeHookManager hookManager;
    private BungeeModuleManager moduleManager;
    private Configuration configuration;

    @Override
    public void onEnable() {
        hookManager = new BungeeHookManager(this);
        hookManager.hook();
        moduleManager = new BungeeModuleManager(this);
        ProxyServer.getInstance().registerChannel("ban");
        if (getServerName().equalsIgnoreCase("CBProxy")) {
            moduleManager.init(BungeeType.CBPROXY);
            return;
        }
        if (getServerName().equalsIgnoreCase("VorbauProxy")) {
            moduleManager.init(BungeeType.VORBAUPROXY);
            return;
        }
        if (getServerName().equalsIgnoreCase("SPvPProxy")) {
            moduleManager.init(BungeeType.SPVPPROXY);
            return;
        }
        if (getServerName().equalsIgnoreCase("Proxy"))
            moduleManager.init(BungeeType.PROXY);
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

    public String getServerName() {
        if (!this.getDataFolder().exists())
            this.getDataFolder().mkdir();

        File file = new File(this.getDataFolder(), "type.yml");


        if (!file.exists()) {
            try (InputStream in = this.getResourceAsStream("type.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(this.getDataFolder(), "type.yml"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return configuration.getString("Name");
    }
}
