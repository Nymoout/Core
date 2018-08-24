package de.mj.BattleBuild.core.utils;

import de.mj.BattleBuild.core.Core;
import de.mj.BattleBuild.core.commands.*;
import de.mj.BattleBuild.core.listener.*;
import de.mj.BattleBuild.core.mySQL.AsyncMySQL;
import de.mj.BattleBuild.core.mySQL.MySQLLoader;
import de.mj.BattleBuild.core.mySQL.SettingsAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ServerManager {

    private final Core core;
    private final String prefix = new Data().getPrefix();
    private final ConsoleCommandSender sender;

    private final ServerType serverType;

    //Commands
    private AFKCommand afkCommand;
    private ServerCommand serverCommand;
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
    private ServerListener serverListener;
    private SettingsListener settingsListener;
    private StopReloadRestartListener stopReloadRestartListener;
    private YourProfileListener yourProfileListener;
    //mySQL
    private AsyncMySQL asyncMySQL;
    private AsyncMySQL.MySQL mySQL;
    private MySQLLoader mySQLLoader;
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

        if (serverType.equals(ServerType.LOBBY)) {
            //init commands
            sender.sendMessage(prefix + "§fload Commands");
            afkCommand = new AFKCommand(core);
            serverCommand = new ServerCommand(core);
            setLocCommand = new SetLocCommand(core);
            setRangCommand = new SetRangCommand(core);
            spawnCommand = new SpawnCommand(core);

            //init listener
            sender.sendMessage(prefix + "§fLoad Listener");
            afkListener = new AFKListener(core);
            commandBlockListener = new BukkitMinecraftCommandBlockListener(core);
            cancelListener = new CancelListener(core);
            chatListener = new ChatListener(core);
            compassListener = new CompassListener(core);
            joinListener = new JoinListener(core);
            lobbySwitcherListener = new LobbySwitcherListener(core);
            minionListener = new MinionListener(core);
            playerMoveListener = new PlayerMoveListener(core);
            quitListener = new QuitListener(core);
            serverListener = new ServerListener(core);
            settingsListener = new SettingsListener(core);
            stopReloadRestartListener = new StopReloadRestartListener(core);
            yourProfileListener = new YourProfileListener(core);

            //mySQL
            sender.sendMessage(prefix + "§fload MySQL");
            asyncMySQL = new AsyncMySQL(core);
            mySQL = new AsyncMySQL.MySQL();
            mySQLLoader = new MySQLLoader(core);
            settingsAPI = new SettingsAPI(core);

            //Utils
            sender.sendMessage(prefix + "§fload Utils");
            actionbarTimer = new ActionbarTimer(core);
            itemCreator = new ItemCreator();
            locationsUtil = new LocationsUtil();
            particle = new Particle();
            playerRealTime = new PlayerRealTime(core);
            schedulerSaver = new SchedulerSaver();
            scoreboardManager = new ScoreboardManager(core);
            setLocations = new SetLocations(core);
            tabList = new TabList(core);
            title = new Title(core);

            mySQLLoader.loadConf();
            mySQLLoader.loadMySQL();
            afkListener.LocationTimer();
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
        }
    }

    public void stopServer() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.kickPlayer("");
        }
        schedulerSaver.cancelSchedulers();
    }

    public Data getData() {
        return data;
    }

    public ConsoleCommandSender getSender() {
        return sender;
    }

    public AFKCommand getAfkCommand() {
        return afkCommand;
    }

    public ServerCommand getServerCommand() {
        return serverCommand;
    }

    public SetLocCommand getSetLocCommand() {
        return setLocCommand;
    }

    public SpawnCommand getSpawnCommand() {
        return spawnCommand;
    }

    public AFKListener getAfkListener() {
        return afkListener;
    }

    public BukkitMinecraftCommandBlockListener getCommandBlockListener() {
        return commandBlockListener;
    }

    public CancelListener getCancelListener() {
        return cancelListener;
    }

    public ChatListener getChatListener() {
        return chatListener;
    }

    public CompassListener getCompassListener() {
        return compassListener;
    }

    public JoinListener getJoinListener() {
        return joinListener;
    }

    public LobbySwitcherListener getLobbySwitcherListener() {
        return lobbySwitcherListener;
    }

    public MinionListener getMinionListener() {
        return minionListener;
    }

    public QuitListener getQuitListener() {
        return quitListener;
    }

    public ServerListener getServerListener() {
        return serverListener;
    }

    public SettingsListener getSettingsListener() {
        return settingsListener;
    }

    public StopReloadRestartListener getStopReloadRestartListener() {
        return stopReloadRestartListener;
    }

    public YourProfileListener getYourProfileListener() {
        return yourProfileListener;
    }

    public AsyncMySQL getAsyncMySQL() {
        return asyncMySQL;
    }

    public AsyncMySQL.MySQL getMySQL() {
        return mySQL;
    }

    public MySQLLoader getMySQLLoader() {
        return mySQLLoader;
    }

    public SettingsAPI getSettingsAPI() {
        return settingsAPI;
    }

    public ActionbarTimer getActionbarTimer() {
        return actionbarTimer;
    }

    public ItemCreator getItemCreator() {
        return itemCreator;
    }

    public LocationsUtil getLocationsUtil() {
        return locationsUtil;
    }

    public Particle getParticle() {
        return particle;
    }

    public PlayerRealTime getPlayerRealTime() {
        return playerRealTime;
    }

    public SchedulerSaver getSchedulerSaver() {
        return schedulerSaver;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public SetLocations getSetLocations() {
        return setLocations;
    }

    public TabList getTabList() {
        return tabList;
    }

    public Title getTitle() {
        return title;
    }

    public ServerType getServerType() {
        return serverType;
    }

    public PlayerMoveListener getPlayerMoveListener() {
        return playerMoveListener;
    }
}
