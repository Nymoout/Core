package de.mj.BattleBuild.core.utils;

import de.mj.BattleBuild.core.Core;
import de.mj.BattleBuild.core.commands.*;
import de.mj.BattleBuild.core.listener.*;
import de.mj.BattleBuild.core.mysql.AsyncMySQL;
import de.mj.BattleBuild.core.mysql.MySQLLoader;
import de.mj.BattleBuild.core.mysql.ServerStatsAPI;
import de.mj.BattleBuild.core.mysql.SettingsAPI;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

@Getter
public class ServerManager {

    private final Core core;
    private final String prefix = new Data().getPrefix();
    private final ConsoleCommandSender sender;

    private final ServerType serverType;

    //Commands
    private AFKCommand afkCommand;
    private SetLocCommand setLocCommand;
    private SetRangCommand setRangCommand;
    private SpawnCommand spawnCommand;
    //Listener
    private AFKListener afkListener;
    private BukkitMinecraftCommandBlockListener commandBlockListener;
    private CancelListener cancelListener;
    private ChatListener chatListener;
    private CompassListener compassListener;
    private JoinListener joinListener;
    private LobbySwitcherListener lobbySwitcherListener;
    private MinionListener minionListener;
    private PlayerMoveListener playerMoveListener;
    private QuitListener quitListener;
    private ScrollListener scrollListener;
    private ServerListener serverListener;
    private SettingsListener settingsListener;
    private StopReloadRestartListener stopReloadRestartListener;
    private YourProfileListener yourProfileListener;
    //mysql
    private AsyncMySQL asyncMySQL;
    private AsyncMySQL.MySQL mySQL;
    private MySQLLoader mySQLLoader;
    private ServerStatsAPI serverStatsAPI;
    private SettingsAPI settingsAPI;
    //Utlis
    private ActionbarTimer actionbarTimer;
    private ItemCreator itemCreator;
    private LocationsUtil locationsUtil;
    private Particle particle;
    private PlayerRealTime playerRealTime;
    private SchedulerSaver schedulerSaver;
    private ScoreboardManager scoreboardManager;
    private SetLocations setLocations;
    private TabList tabList;
    private Title title;
    private Data data;

    public ServerManager(Core core, ServerType serverType) {
        this.core = core;
        this.serverType = serverType;
        sender = core.getSender();
    }

    public void init() {
        //loaded by default
        sender.sendMessage(prefix + "§fload Data");
        data = new Data();
        asyncMySQL = new AsyncMySQL(core);
        mySQL = new AsyncMySQL.MySQL();
        mySQLLoader = new MySQLLoader(core);
        serverStatsAPI = new ServerStatsAPI(core);
        serverStatsAPI.createTable();
        schedulerSaver = new SchedulerSaver();
        new ServerInfoCommand(core);


        if (serverType.equals(ServerType.LOBBY)) {
            //init commands
            sender.sendMessage(prefix + "§fload Commands");
            new GoToServerCommand(core);
            setLocCommand = new SetLocCommand(core);
            setRangCommand = new SetRangCommand(core);
            spawnCommand = new SpawnCommand(core);

            //init listener
            sender.sendMessage(prefix + "§fLoad Listener");
            commandBlockListener = new BukkitMinecraftCommandBlockListener(core);
            cancelListener = new CancelListener(core);
            chatListener = new ChatListener(core);
            compassListener = new CompassListener(core);
            joinListener = new JoinListener(core);
            lobbySwitcherListener = new LobbySwitcherListener(core);
            minionListener = new MinionListener(core);
            playerMoveListener = new PlayerMoveListener(core);
            quitListener = new QuitListener(core);
            scrollListener = new ScrollListener(core);
            serverListener = new ServerListener(core);
            settingsListener = new SettingsListener(core);
            stopReloadRestartListener = new StopReloadRestartListener(core);
            yourProfileListener = new YourProfileListener(core);

            settingsAPI = new SettingsAPI(core);

            //Utils
            sender.sendMessage(prefix + "§fload Utils");
            actionbarTimer = new ActionbarTimer(core);
            itemCreator = new ItemCreator();
            locationsUtil = new LocationsUtil();
            particle = new Particle();
            playerRealTime = new PlayerRealTime(core);
            scoreboardManager = new ScoreboardManager(core);
            setLocations = new SetLocations(core);
            tabList = new TabList(core);
            title = new Title(core);

            mySQLLoader.loadConf();
            mySQLLoader.loadMySQL();
            tabList.loadTablist();
            setLocations.saveLocs();
            playerRealTime.setPlayerRealTime();
            actionbarTimer.setActionBar();
            scoreboardManager.ScoreboardActu();
        } else if (serverType.equals(ServerType.DEFAULT)) {
            data = new Data();
            tabList = new TabList(core);
            joinListener = new JoinListener(core);
            chatListener = new ChatListener(core);
            commandBlockListener = new BukkitMinecraftCommandBlockListener(core);
            stopReloadRestartListener = new StopReloadRestartListener(core);
            tabList.loadTablist();
        } else if (serverType.equals(ServerType.SKY_PVP)) {
            data = new Data();
            tabList = new TabList(core);
            joinListener = new JoinListener(core);
            chatListener = new ChatListener(core);
            commandBlockListener = new BukkitMinecraftCommandBlockListener(core);
            stopReloadRestartListener = new StopReloadRestartListener(core);
            tabList.loadTablist();
        } else if (serverType.equals(ServerType.CITY_BUILD)) {
            data = new Data();
            tabList = new TabList(core);
            joinListener = new JoinListener(core);
            chatListener = new ChatListener(core);
            commandBlockListener = new BukkitMinecraftCommandBlockListener(core);
            stopReloadRestartListener = new StopReloadRestartListener(core);
            tabList.loadTablist();
        } else if (serverType.equals(ServerType.BAU_SERVER)) {
            data = new Data();
            tabList = new TabList(core);
            joinListener = new JoinListener(core);
            chatListener = new ChatListener(core);
            commandBlockListener = new BukkitMinecraftCommandBlockListener(core);
            stopReloadRestartListener = new StopReloadRestartListener(core);
            tabList.loadTablist();
        }
    }

    public void stopServer() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.kickPlayer("");
        }
        schedulerSaver.cancelSchedulers();
    }

}
