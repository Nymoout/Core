package main.de.mj.bb.core.managers;

import main.de.mj.bb.core.CoreBungee;
import main.de.mj.bb.core.commands.BanCommand;
import main.de.mj.bb.core.commands.CloudCommand;
import main.de.mj.bb.core.commands.LobbyCommand;
import main.de.mj.bb.core.commands.MaintenanceCommand;
import main.de.mj.bb.core.listener.LoginListener;
import main.de.mj.bb.core.listener.PingListener;
import main.de.mj.bb.core.sql.AsyncBungeeSQL;
import main.de.mj.bb.core.sql.BungeeAPI;
import main.de.mj.bb.core.sql.BungeeSQLLoader;
import main.de.mj.bb.core.utils.BungeeTabList;
import main.de.mj.bb.core.utils.BungeeType;
import main.de.mj.bb.core.utils.Data;
import main.de.mj.bb.core.utils.FinalBan;

public class BungeeModuleManager {

    private final CoreBungee coreBungee;
    private BungeeType bungeeType;
    private Data data = new Data();
    private BungeeTabList bungeeTablist;
    private FinalBan finalBan;
    private BungeeSQLLoader bungeeSQLLoader;
    private AsyncBungeeSQL asyncBungeeSQL;
    private BungeeAPI bungeeAPI;
    private MaintenanceCommand maintenanceCommand;

    public BungeeModuleManager(CoreBungee coreBungee) {
        this.coreBungee = coreBungee;
    }

    public void init(BungeeType bungeeType) {
        this.bungeeType = bungeeType;
        new BanCommand(coreBungee);
        new LoginListener(coreBungee);
        new LobbyCommand(coreBungee);
        new CloudCommand(coreBungee);
        bungeeTablist = new BungeeTabList(coreBungee);
        finalBan = new FinalBan(coreBungee);
        bungeeSQLLoader = new BungeeSQLLoader(coreBungee);
        asyncBungeeSQL = new AsyncBungeeSQL(coreBungee);
        bungeeAPI = new BungeeAPI(coreBungee);
        maintenanceCommand = new MaintenanceCommand(coreBungee);
        new PingListener(coreBungee);
        bungeeSQLLoader.createConfig();
        bungeeSQLLoader.loadConfig();
        bungeeSQLLoader.loadMySQL();
        bungeeTablist.schedule();
        maintenanceCommand.setMaintenance(bungeeAPI.isMaintenance());
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

    public MaintenanceCommand getMaintenanceCommand() {
        return maintenanceCommand;
    }

    public BungeeType getBungeeType() {
        return bungeeType;
    }
}
