package de.mj.BattleBuild.lobby.utils;

import de.mj.BattleBuild.lobby.Lobby;
import de.mj.BattleBuild.lobby.commands.AFKCommand;
import de.mj.BattleBuild.lobby.commands.ServerCommand;
import de.mj.BattleBuild.lobby.commands.SetLocCommand;
import de.mj.BattleBuild.lobby.commands.SpawnCommand;
import de.mj.BattleBuild.lobby.listener.*;
import de.mj.BattleBuild.lobby.mySQL.AsyncMySQL;
import de.mj.BattleBuild.lobby.mySQL.MySQLLoader;
import de.mj.BattleBuild.lobby.mySQL.SettingsAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ServerManager {

    private final Lobby lobby;
    private final String prefix = new Data().getPrefix();
    private final ConsoleCommandSender sender;

    private ServerType serverType;

    //Commands
    private AFKCommand afkCommand;
    private ServerCommand serverCommand;
    private SetLocCommand setLocCommand;
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

    public ServerManager(Lobby lobby) {
        this.lobby = lobby;
        sender = lobby.getSender();
    }

    public synchronized void init(ServerType serverType) {
        //loaded by default
        sender.sendMessage(prefix + "§fload Data");
        data = new Data();

        if (serverType.equals(ServerType.LOBBY)) {
            //init commands
            sender.sendMessage(prefix + "§fload Commands");
            afkCommand = new AFKCommand(lobby);
            serverCommand = new ServerCommand(lobby);
            setLocCommand = new SetLocCommand(lobby);
            spawnCommand = new SpawnCommand(lobby);

            //init listener
            sender.sendMessage(prefix + "§fLoad Listener");
            afkListener = new AFKListener(lobby);
            commandBlockListener = new BukkitMinecraftCommandBlockListener(lobby);
            cancelListener = new CancelListener(lobby);
            chatListener = new ChatListener(lobby);
            compassListener = new CompassListener(lobby);
            joinListener = new JoinListener(lobby);
            lobbySwitcherListener = new LobbySwitcherListener(lobby);
            minionListener = new MinionListener(lobby);
            playerMoveListener = new PlayerMoveListener(lobby);
            quitListener = new QuitListener(lobby);
            serverListener = new ServerListener(lobby);
            settingsListener = new SettingsListener(lobby);
            stopReloadRestartListener = new StopReloadRestartListener(lobby);
            yourProfileListener = new YourProfileListener(lobby);

            //mySQL
            sender.sendMessage(prefix + "§fload MySQL");
            asyncMySQL = new AsyncMySQL(lobby);
            mySQL = new AsyncMySQL.MySQL();
            mySQLLoader = new MySQLLoader(lobby);
            settingsAPI = new SettingsAPI(lobby);

            //Utils
            sender.sendMessage(prefix + "§fload Utils");
            actionbarTimer = new ActionbarTimer(lobby);
            itemCreator = new ItemCreator();
            locationsUtil = new LocationsUtil();
            particle = new Particle();
            playerRealTime = new PlayerRealTime(lobby);
            schedulerSaver = new SchedulerSaver();
            scoreboardManager = new ScoreboardManager(lobby);
            setLocations = new SetLocations(lobby);
            tabList = new TabList(lobby);
            title = new Title(lobby);

            mySQLLoader.loadConf();
            mySQLLoader.loadMySQL();
            afkListener.LocationTimer();
            tabList.loadTablist();
            setLocations.saveLocs();
            playerRealTime.setPlayerRealTime();
            actionbarTimer.setActionBar();
            scoreboardManager.ScoreboardActu();
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

    public void setServerType(ServerType serverType) {
        this.serverType = serverType;
    }
}
