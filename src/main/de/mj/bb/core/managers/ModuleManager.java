package main.de.mj.bb.core.managers;

import main.de.mj.bb.core.CoreSpigot;
import main.de.mj.bb.core.commands.*;
import main.de.mj.bb.core.listener.*;
import main.de.mj.bb.core.mysql.AsyncMySQL;
import main.de.mj.bb.core.mysql.MySQLLoader;
import main.de.mj.bb.core.mysql.ServerStatsAPI;
import main.de.mj.bb.core.mysql.SettingsAPI;
import main.de.mj.bb.core.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ModuleManager {

    private final CoreSpigot coreSpigot;
    private final String prefix = new Data().getPrefix();
    private final ConsoleCommandSender sender;

    private final ServerType serverType;

    //Commands
    private AFKCommand afkCommand;
    private FlyCommand flyCommand;
    private GMCommand gmCommand;
    private SetLocCommand setLocCommand;
    private SetPortalCommand portalCommand;
    private SetRangCommand setRangCommand;
    private SpawnCommand spawnCommand;
    private TPSCommand tpsCommand;

    //Listener
    private AFKListener afkListener;
    private BlockRedstoneListener blockRedstoneListener;
    private BukkitMinecraftCommandBlockListener commandBlockListener;
    private CancelListener cancelListener;
    private ChatListener chatListener;
    private CompassListener compassListener;
    private FlyListener flyListener;
    private JoinListener joinListener;
    private LobbySwitcherListener lobbySwitcherListener;
    private LogBlockListener logBlockListener;
    private MinionListener minionListener;
    private MusicListener musicListener;
    private PlayerMoveListener playerMoveListener;
    private PlayerPortalListener playerPortalListener;
    private QuitListener quitListener;
    private ScrollListener scrollListener;
    private ServerListener serverListener;
    private SettingsListener settingsListener;
    private StopReloadRestartListener stopReloadRestartListener;
    private YourProfileListener yourProfileListener;

    //managers
    private FileManager fileManager;
    private PortalManager portalManager;
    private ScoreboardManager scoreboardManager;

    //mysql
    private AsyncMySQL asyncMySQL;
    private AsyncMySQL.MySQL mySQL;
    private MySQLLoader mySQLLoader;
    private ServerStatsAPI serverStatsAPI;
    private SettingsAPI settingsAPI;

    //Utlis
    private AutomaticClearLag automaticClearLag;
    private CrashFixer crashFixer;
    private ItemCreator itemCreator;
    private LobbyParticle lobbyParticle;
    private LocationsUtil locationsUtil;
    private Particle particle;
    private PlayerRealTime playerRealTime;
    private Portal portal;
    private SchedulerSaver schedulerSaver;
    private SetLocations setLocations;
    private TabList tabList;
    private TicksPerSecond ticksPerSecond;
    private Data data;

    public ModuleManager(@NotNull CoreSpigot coreSpigot, ServerType serverType) {
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
        fileManager = new FileManager(coreSpigot);
        fileManager.loadConfigFile();
        ticksPerSecond = new TicksPerSecond();
        tpsCommand = new TPSCommand(coreSpigot);
        if (fileManager.getBooleanFormConfig("Clearlag")) {
            Bukkit.getScheduler().scheduleSyncRepeatingTask(coreSpigot, ticksPerSecond, 100L, 1L);
            automaticClearLag = new AutomaticClearLag(coreSpigot);
            automaticClearLag.clearLagScheduler();
        }
        new ServerInfoCommand(coreSpigot);

        crashFixer = new CrashFixer(coreSpigot);
        blockRedstoneListener = new BlockRedstoneListener(coreSpigot);

        if (serverType.equals(ServerType.LOBBY)) {

            ticksPerSecond = new TicksPerSecond();
            Bukkit.getScheduler().scheduleSyncRepeatingTask(coreSpigot, ticksPerSecond, 100L, 1L);
            tpsCommand = new TPSCommand(coreSpigot);
            automaticClearLag = new AutomaticClearLag(coreSpigot);
            automaticClearLag.clearLagScheduler();
            portalCommand = new SetPortalCommand(coreSpigot);
            portalManager = new PortalManager(coreSpigot);
            playerPortalListener = new PlayerPortalListener(coreSpigot);
            portal = new Portal();
            fileManager.loadPortalConfig();
            portalManager.loadPortals();

            //init commands
            sender.sendMessage(prefix + "§fload Commands");
            new GoToServerCommand(coreSpigot);
            afkCommand = new AFKCommand(coreSpigot);
            flyCommand = new FlyCommand(coreSpigot);
            setLocCommand = new SetLocCommand(coreSpigot);
            setRangCommand = new SetRangCommand(coreSpigot);
            spawnCommand = new SpawnCommand(coreSpigot);

            //init listener
            sender.sendMessage(prefix + "§fLoad Listener");
            afkListener = new AFKListener(coreSpigot);
            commandBlockListener = new BukkitMinecraftCommandBlockListener(coreSpigot);
            cancelListener = new CancelListener(coreSpigot);
            chatListener = new ChatListener(coreSpigot);
            compassListener = new CompassListener(coreSpigot);
            flyListener = new FlyListener(coreSpigot);
            joinListener = new JoinListener(coreSpigot);
            lobbySwitcherListener = new LobbySwitcherListener(coreSpigot);
            minionListener = new MinionListener(coreSpigot);
            musicListener = new MusicListener(coreSpigot);
            playerMoveListener = new PlayerMoveListener(coreSpigot);
            quitListener = new QuitListener(coreSpigot);
            scrollListener = new ScrollListener(coreSpigot);
            serverListener = new ServerListener(coreSpigot);
            settingsListener = new SettingsListener(coreSpigot);
            stopReloadRestartListener = new StopReloadRestartListener(coreSpigot);
            yourProfileListener = new YourProfileListener(coreSpigot);

            settingsAPI = new SettingsAPI(coreSpigot);

            portalManager = new PortalManager(coreSpigot);

            //Utils
            sender.sendMessage(prefix + "§fload Utils");
            itemCreator = new ItemCreator();
            lobbyParticle = new LobbyParticle(coreSpigot);
            locationsUtil = new LocationsUtil();
            particle = new Particle();
            playerRealTime = new PlayerRealTime(coreSpigot);
            scoreboardManager = new ScoreboardManager(coreSpigot);
            setLocations = new SetLocations(coreSpigot);
            tabList = new TabList(coreSpigot);

            fileManager.loadPortalConfig();
            afkListener.LocationTimer();
            mySQLLoader.loadConf();
            mySQLLoader.loadMySQL();
            tabList.loadTabList();
            setLocations.saveLocs();
            playerRealTime.setPlayerRealTime();
            scoreboardManager.ScoreboardActu();
            lobbyParticle.playEnderSignal();
            lobbyParticle.playEnchantment();
            settingsListener.invTimer();
        } else if (serverType.equals(ServerType.DEFAULT)) {
            data = new Data();
            tabList = new TabList(coreSpigot);
            joinListener = new JoinListener(coreSpigot);
            chatListener = new ChatListener(coreSpigot);
            commandBlockListener = new BukkitMinecraftCommandBlockListener(coreSpigot);
            stopReloadRestartListener = new StopReloadRestartListener(coreSpigot);
            tabList.loadTabList();
        } else if (serverType.equals(ServerType.SKY_PVP)) {
            data = new Data();
            tabList = new TabList(coreSpigot);
            joinListener = new JoinListener(coreSpigot);
            chatListener = new ChatListener(coreSpigot);
            commandBlockListener = new BukkitMinecraftCommandBlockListener(coreSpigot);
            stopReloadRestartListener = new StopReloadRestartListener(coreSpigot);
            tabList.loadTabList();
        } else if (serverType.equals(ServerType.CITY_BUILD)) {
            data = new Data();
            tabList = new TabList(coreSpigot);
            joinListener = new JoinListener(coreSpigot);
            chatListener = new ChatListener(coreSpigot);
            commandBlockListener = new BukkitMinecraftCommandBlockListener(coreSpigot);
            stopReloadRestartListener = new StopReloadRestartListener(coreSpigot);
            fileManager = new FileManager(coreSpigot);
            tabList.loadTabList();
        } else if (serverType.equals(ServerType.BAU_SERVER)) {
            data = new Data();
            tabList = new TabList(coreSpigot);
            portalCommand = new SetPortalCommand(coreSpigot);
            portalManager = new PortalManager(coreSpigot);
            playerPortalListener = new PlayerPortalListener(coreSpigot);
            portal = new Portal();
            gmCommand = new GMCommand(coreSpigot);
            tpsCommand = new TPSCommand(coreSpigot);
            joinListener = new JoinListener(coreSpigot);
            chatListener = new ChatListener(coreSpigot);
            commandBlockListener = new BukkitMinecraftCommandBlockListener(coreSpigot);
            stopReloadRestartListener = new StopReloadRestartListener(coreSpigot);
            locationsUtil = new LocationsUtil();
            setLocations = new SetLocations(coreSpigot);
            setLocCommand = new SetLocCommand(coreSpigot);
            fileManager.loadPortalConfig();
            portalManager.loadPortals();
            tabList.loadTabList();
        } else if (serverType.equals(ServerType.BED_WARS)) {
            data = new Data();
            joinListener = new JoinListener(coreSpigot);
            chatListener = new ChatListener(coreSpigot);
            commandBlockListener = new BukkitMinecraftCommandBlockListener(coreSpigot);
            stopReloadRestartListener = new StopReloadRestartListener(coreSpigot);
        } else if (serverType.equals(ServerType.VORBAUEN)) {
            data = new Data();
            tabList = new TabList(coreSpigot);
            new GMCommand(coreSpigot);
            tpsCommand = new TPSCommand(coreSpigot);
            new ChestCommand(coreSpigot);
            new TippCommand(coreSpigot);
            new BlockPlaceChestListener(coreSpigot);
            joinListener = new JoinListener(coreSpigot);
            chatListener = new ChatListener(coreSpigot);
            commandBlockListener = new BukkitMinecraftCommandBlockListener(coreSpigot);
            stopReloadRestartListener = new StopReloadRestartListener(coreSpigot);
            locationsUtil = new LocationsUtil();
            setLocations = new SetLocations(coreSpigot);
            setLocCommand = new SetLocCommand(coreSpigot);
            spawnCommand = new SpawnCommand(coreSpigot);
            setLocations.saveSpawn();
            tabList.loadTabList();
        }
    }

    public void stopServer() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            //if (nickManager.isDisguised(all)) {
            //    nickManager.undisguise(all, false, true);
            //}
            all.kickPlayer("");
        }
        schedulerSaver.cancelSchedulers();
    }

    public void reInit() {
        if (fileManager.getBooleanFormConfig("Clearlag")) {
            ticksPerSecond = new TicksPerSecond();
            Bukkit.getScheduler().scheduleSyncRepeatingTask(coreSpigot, ticksPerSecond, 100L, 1L);
            tpsCommand = new TPSCommand(coreSpigot);
            automaticClearLag = new AutomaticClearLag(coreSpigot);
            automaticClearLag.clearLagScheduler();
        }
    }

    public CoreSpigot getCoreSpigot() {
        return this.coreSpigot;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public ConsoleCommandSender getSender() {
        return this.sender;
    }

    public ServerType getServerType() {
        return this.serverType;
    }

    public AFKCommand getAfkCommand() {
        return this.afkCommand;
    }

    public FlyCommand getFlyCommand() {
        return this.flyCommand;
    }

    public GMCommand getGmCommand() {
        return this.gmCommand;
    }

    public SetLocCommand getSetLocCommand() {
        return this.setLocCommand;
    }

    public SetPortalCommand getPortalCommand() {
        return this.portalCommand;
    }

    public SetRangCommand getSetRangCommand() {
        return this.setRangCommand;
    }

    public SpawnCommand getSpawnCommand() {
        return this.spawnCommand;
    }

    public TPSCommand getTpsCommand() {
        return this.tpsCommand;
    }

    public AFKListener getAfkListener() {
        return this.afkListener;
    }

    public BlockRedstoneListener getBlockRedstoneListener() {
        return this.blockRedstoneListener;
    }

    public BukkitMinecraftCommandBlockListener getCommandBlockListener() {
        return this.commandBlockListener;
    }

    public CancelListener getCancelListener() {
        return this.cancelListener;
    }

    public ChatListener getChatListener() {
        return this.chatListener;
    }

    public CompassListener getCompassListener() {
        return this.compassListener;
    }

    public FlyListener getFlyListener() {
        return this.flyListener;
    }

    public JoinListener getJoinListener() {
        return this.joinListener;
    }

    public LobbySwitcherListener getLobbySwitcherListener() {
        return this.lobbySwitcherListener;
    }

    public LogBlockListener getLogBlockListener() {
        return this.logBlockListener;
    }

    public MinionListener getMinionListener() {
        return this.minionListener;
    }

    public MusicListener getMusicListener() {
        return this.musicListener;
    }

    public PlayerMoveListener getPlayerMoveListener() {
        return this.playerMoveListener;
    }

    public PlayerPortalListener getPlayerPortalListener() {
        return this.playerPortalListener;
    }

    public QuitListener getQuitListener() {
        return this.quitListener;
    }

    public ScrollListener getScrollListener() {
        return this.scrollListener;
    }

    public ServerListener getServerListener() {
        return this.serverListener;
    }

    public SettingsListener getSettingsListener() {
        return this.settingsListener;
    }

    public StopReloadRestartListener getStopReloadRestartListener() {
        return this.stopReloadRestartListener;
    }

    public YourProfileListener getYourProfileListener() {
        return this.yourProfileListener;
    }

    public FileManager getFileManager() {
        return this.fileManager;
    }

    public PortalManager getPortalManager() {
        return this.portalManager;
    }

    public ScoreboardManager getScoreboardManager() {
        return this.scoreboardManager;
    }

    public AsyncMySQL getAsyncMySQL() {
        return this.asyncMySQL;
    }

    public AsyncMySQL.MySQL getMySQL() {
        return this.mySQL;
    }

    public MySQLLoader getMySQLLoader() {
        return this.mySQLLoader;
    }

    public ServerStatsAPI getServerStatsAPI() {
        return this.serverStatsAPI;
    }

    public SettingsAPI getSettingsAPI() {
        return this.settingsAPI;
    }

    public AutomaticClearLag getAutomaticClearLag() {
        return this.automaticClearLag;
    }

    public CrashFixer getCrashFixer() {
        return this.crashFixer;
    }

    public ItemCreator getItemCreator() {
        return this.itemCreator;
    }

    public LobbyParticle getLobbyParticle() {
        return this.lobbyParticle;
    }

    public LocationsUtil getLocationsUtil() {
        return this.locationsUtil;
    }

    public Particle getParticle() {
        return this.particle;
    }

    public PlayerRealTime getPlayerRealTime() {
        return this.playerRealTime;
    }

    public Portal getPortal() {
        return this.portal;
    }

    public SchedulerSaver getSchedulerSaver() {
        return this.schedulerSaver;
    }

    public SetLocations getSetLocations() {
        return this.setLocations;
    }

    public TabList getTabList() {
        return this.tabList;
    }

    public TicksPerSecond getTicksPerSecond() {
        return this.ticksPerSecond;
    }

    public Data getData() {
        return this.data;
    }
}
