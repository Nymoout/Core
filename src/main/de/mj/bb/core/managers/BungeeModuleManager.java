package main.de.mj.bb.core.managers;

import main.de.mj.bb.core.CoreBungee;
import main.de.mj.bb.core.commands.BanCommand;
import main.de.mj.bb.core.listener.DisconnectListener;
import main.de.mj.bb.core.listener.LoginListener;
import main.de.mj.bb.core.listener.ServerSwitchListener;
import main.de.mj.bb.core.utils.BungeeTabList;
import main.de.mj.bb.core.utils.Data;
import main.de.mj.bb.core.utils.FinalBan;

public class BungeeModuleManager {

    private final CoreBungee coreBungee;
    private Data data = new Data();
    private BungeeTabList bungeeTablist;
    private FinalBan finalBan;

    public BungeeModuleManager(CoreBungee coreBungee) {
        this.coreBungee = coreBungee;
    }

    public void init() {
        new BanCommand(coreBungee);
        new LoginListener(coreBungee);
        new ServerSwitchListener(coreBungee);
        new DisconnectListener(coreBungee);
        bungeeTablist = new BungeeTabList(coreBungee);
        finalBan = new FinalBan(coreBungee);
    }

    public CoreBungee getCoreBungee() {
        return coreBungee;
    }

    public Data getData() {
        return data;
    }

    public BungeeTabList getBungeeTablist() {
        return bungeeTablist;
    }

    public FinalBan getFinalBan() {
        return finalBan;
    }
}
