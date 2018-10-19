package main.de.mj.bb.core;

import lombok.Getter;
import main.de.mj.bb.core.commands.BanCommand;
import main.de.mj.bb.core.managers.BungeeHookManager;
import main.de.mj.bb.core.utils.Data;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.logging.Logger;

@Getter
public class CoreBungee extends Plugin {

    private Logger logger = this.getLogger();
    private Data data = new Data();
    private BungeeHookManager hookManager;

    public void onEnable() {
        hookManager = new BungeeHookManager(this);
        new BanCommand(this);
    }

    public void setCommand(Command command) {
        getProxy().getPluginManager().registerCommand(this, command);
    }
}
