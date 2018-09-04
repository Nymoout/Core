package de.mj.BattleBuild.core.utils;

import de.mj.BattleBuild.core.CoreSpigot;
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
import org.jetbrains.annotations.NotNull;

@Getter
public class ServerManager {

    private final CoreSpigot coreSpigot;
    private final String prefix = new Data().getPrefix();
    private final ConsoleCommandSender sender;

    private final ServerType serverType;

    //Commands
    private AFKCommand afkCommand;
    private BorderCommand borderCommand;
    private SetLocCommand setLocCommand;
    private SetRangCommand setRangCommand;
    private SpawnCommand spawnCommand;
    //Listener
    private AFKListener afkListener;
    private BorderListener borderListener;
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

    public ServerManager(@NotNull CoreSpigot coreSpigot, ServerType serverType) {
        this.coreSpigot = coreSpigot;
        this.serverType = serverType;
        sender = coreSpigot.getSender();
    }

    public void init() {
        //loaded by default
        sender.sendMessage(prefix + "§fload Data");
        data = new Data();
        asyncMySQL = new AsyncMySQL(coreSpigot);
        mySQL = new AsyncMySQL.MySQL();
        mySQLLoader = new MySQLLoader(coreSpigot);
        serverStatsAPI = new ServerStatsAPI(coreSpigot);
        serverStatsAPI.createTable();
        schedulerSaver = new SchedulerSaver();
        new ServerInfoCommand(coreSpigot);


        if (serverType.equals(ServerType.LOBBY)) {
            //init commands
            sender.sendMessage(prefix + "§fload Commands");
            new GoToServerCommand(coreSpigot);
            setLocCommand = new SetLocCommand(coreSpigot);
            setRangCommand = new SetRangCommand(coreSpigot);
            spawnCommand = new SpawnCommand(coreSpigot);

            //init listener
            sender.sendMessage(prefix + "§fLoad Listener");
            commandBlockListener = new BukkitMinecraftCommandBlockListener(coreSpigot);
            cancelListener = new CancelListener(coreSpigot);
            chatListener = new ChatListener(coreSpigot);
            compassListener = new CompassListener(coreSpigot);
            joinListener = new JoinListener(coreSpigot);
            lobbySwitcherListener = new LobbySwitcherListener(coreSpigot);
            minionListener = new MinionListener(coreSpigot);
            playerMoveListener = new PlayerMoveListener(coreSpigot);
            quitListener = new QuitListener(coreSpigot);
            scrollListener = new ScrollListener(coreSpigot);
            serverListener = new ServerListener(coreSpigot);
            settingsListener = new SettingsListener(coreSpigot);
            stopReloadRestartListener = new StopReloadRestartListener(coreSpigot);
            yourProfileListener = new YourProfileListener(coreSpigot);

            settingsAPI = new SettingsAPI(coreSpigot);

            //Utils
            sender.sendMessage(prefix + "§fload Utils");
            itemCreator = new ItemCreator();
            locationsUtil = new LocationsUtil();
            particle = new Particle();
            playerRealTime = new PlayerRealTime(coreSpigot);
            scoreboardManager = new ScoreboardManager(coreSpigot);
            setLocations = new SetLocations(coreSpigot);
            tabList = new TabList(coreSpigot);

            mySQLLoader.loadConf();
            mySQLLoader.loadMySQL();
            tabList.loadTablist();
            setLocations.saveLocs();
            playerRealTime.setPlayerRealTime();
            scoreboardManager.ScoreboardActu();
        } else if (serverType.equals(ServerType.DEFAULT)) {
            data = new Data();
            tabList = new TabList(coreSpigot);
            joinListener = new JoinListener(coreSpigot);
            chatListener = new ChatListener(coreSpigot);
            commandBlockListener = new BukkitMinecraftCommandBlockListener(coreSpigot);
            stopReloadRestartListener = new StopReloadRestartListener(coreSpigot);
            tabList.loadTablist();
        } else if (serverType.equals(ServerType.SKY_PVP)) {
            data = new Data();
            tabList = new TabList(coreSpigot);
            joinListener = new JoinListener(coreSpigot);
            chatListener = new ChatListener(coreSpigot);
            commandBlockListener = new BukkitMinecraftCommandBlockListener(coreSpigot);
            stopReloadRestartListener = new StopReloadRestartListener(coreSpigot);
            tabList.loadTablist();
        } else if (serverType.equals(ServerType.CITY_BUILD)) {
            data = new Data();
            tabList = new TabList(coreSpigot);
            borderCommand = new BorderCommand(coreSpigot);
            borderListener = new BorderListener(coreSpigot);
            joinListener = new JoinListener(coreSpigot);
            chatListener = new ChatListener(coreSpigot);
            commandBlockListener = new BukkitMinecraftCommandBlockListener(coreSpigot);
            stopReloadRestartListener = new StopReloadRestartListener(coreSpigot);
            tabList.loadTablist();
        } else if (serverType.equals(ServerType.BAU_SERVER)) {
            data = new Data();
            tabList = new TabList(coreSpigot);
            joinListener = new JoinListener(coreSpigot);
            chatListener = new ChatListener(coreSpigot);
            commandBlockListener = new BukkitMinecraftCommandBlockListener(coreSpigot);
            stopReloadRestartListener = new StopReloadRestartListener(coreSpigot);
            tabList.loadTablist();
        } else if (serverType.equals(ServerType.BED_WARS)) {
            data = new Data();
            joinListener = new JoinListener(coreSpigot);
            chatListener = new ChatListener(coreSpigot);
            commandBlockListener = new BukkitMinecraftCommandBlockListener(coreSpigot);
            stopReloadRestartListener = new StopReloadRestartListener(coreSpigot);
        }
    }

    public void stopServer() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.kickPlayer("");
        }
        schedulerSaver.cancelSchedulers();
    }

}
