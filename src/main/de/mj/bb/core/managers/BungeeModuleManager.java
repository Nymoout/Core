package main.de.mj.bb.core.managers;

import main.de.mj.bb.core.CoreBungee;
import main.de.mj.bb.core.commands.BanCommand;
import main.de.mj.bb.core.commands.LobbyCommand;
import main.de.mj.bb.core.listener.LoginListener;
import main.de.mj.bb.core.mysql.AsyncBungeeSQL;
import main.de.mj.bb.core.mysql.BungeeAPI;
import main.de.mj.bb.core.mysql.BungeeSQLLoader;
import main.de.mj.bb.core.utils.BungeeTabList;
import main.de.mj.bb.core.utils.Data;
import main.de.mj.bb.core.utils.FinalBan;

public class BungeeModuleManager {

    private final CoreBungee coreBungee;
    private Data data = new Data();
    private BungeeTabList bungeeTablist;
    private FinalBan finalBan;
    private BungeeSQLLoader bungeeSQLLoader;
    private AsyncBungeeSQL asyncBungeeSQL;
    private BungeeAPI bungeeAPI;

    public BungeeModuleManager(CoreBungee coreBungee) {
        this.coreBungee = coreBungee;
    }

    public void init() {
        new BanCommand(coreBungee);
        new LoginListener(coreBungee);
        new LobbyCommand(coreBungee);
        bungeeTablist = new BungeeTabList(coreBungee);
        finalBan = new FinalBan(coreBungee);
        bungeeSQLLoader = new BungeeSQLLoader(coreBungee);
        asyncBungeeSQL = new AsyncBungeeSQL(coreBungee);
        bungeeAPI = new BungeeAPI(coreBungee);
        bungeeSQLLoader.createConfig();
        bungeeSQLLoader.loadConfig();
        bungeeSQLLoader.loadMySQL();
        bungeeTablist.schedule();
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

    public BungeeSQLLoader getBungeeSQLLoader() {
        return bungeeSQLLoader;
    }

    public AsyncBungeeSQL getAsyncBungeeSQL() {
        return asyncBungeeSQL;
    }

    public BungeeAPI getBungeeAPI() {
        return bungeeAPI;
    }
}
