package main.de.mj.bb.core.managers;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import main.de.mj.bb.core.CoreSpigot;
import main.de.mj.bb.core.commands.*;
import main.de.mj.bb.core.listener.*;
import main.de.mj.bb.core.sql.*;
import main.de.mj.bb.core.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class ModuleManager {

    private final CoreSpigot coreSpigot;
    private final String prefix = new Data().getPrefix();
    private final ConsoleCommandSender sender;

    private final ServerType serverType;

    private TPSCommand tpsCommand;
    private NickCommand nickCommand;

    //Listener
    private AFKListener afkListener;
    private BlockRedstoneListener blockRedstoneListener;
    private FlyListener flyListener;
    private MinionListener minionListener;
    private MusicListener musicListener;
    private SettingsListener settingsListener;
    private StopReloadRestartListener stopReloadRestartListener;

    //managers
    private FileManager fileManager;
    private PortalManager portalManager;
    private NickManager nickManager;
    private ScoreboardManager scoreboardManager;

    //sql
    private AsyncMySQL asyncMySQL;
    private NickAPI nickAPI;
    private SettingsAPI settingsAPI;
    private SpawnLocationAPI spawnLocationAPI;

    //Utlis
    private AutomaticClearLag automaticClearLag;
    private CrashFixer crashFixer;
    private ItemCreator itemCreator;
    private LobbyParticle lobbyParticle;
    private LocationsUtil locationsUtil;
    private PlayerRealTime playerRealTime;
    private SchedulerSaver schedulerSaver;
    private SetLocations setLocations;
    //private TabList tabList;
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
        MySQLLoader mySQLLoader = new MySQLLoader(coreSpigot);
        //serverStatsAPI = new ServerStatsAPI(coreSpigot);
        //serverStatsAPI.createTable();
        schedulerSaver = new SchedulerSaver();
        fileManager = new FileManager(coreSpigot);
        fileManager.loadConfigFile();
        fileManager.loadColorConfig();
        ticksPerSecond = new TicksPerSecond();
        tpsCommand = new TPSCommand(coreSpigot);
        new SetRangCommand(coreSpigot);
        settingsAPI = new SettingsAPI(coreSpigot);
        new InvseeCommand(coreSpigot);
        if (fileManager.getBooleanFormConfig("Clearlag")) {
            Bukkit.getScheduler().scheduleSyncRepeatingTask(coreSpigot, ticksPerSecond, 100L, 1L);
            automaticClearLag = new AutomaticClearLag(coreSpigot);
            automaticClearLag.clearLagScheduler();
        }
        new ServerInfoCommand(coreSpigot);

        crashFixer = new CrashFixer(coreSpigot);
        blockRedstoneListener = new BlockRedstoneListener(coreSpigot);
        new UnknownCommandListener(coreSpigot);
        nickAPI = new NickAPI(coreSpigot);
        itemCreator = new ItemCreator(coreSpigot);
        nickCommand = new NickCommand(coreSpigot);
        nickManager = new NickManager(coreSpigot);
        scoreboardManager = new ScoreboardManager(coreSpigot);
        if (serverType.equals(ServerType.LOBBY)) {

            ticksPerSecond = new TicksPerSecond();
            Bukkit.getScheduler().scheduleSyncRepeatingTask(coreSpigot, ticksPerSecond, 100L, 1L);
            tpsCommand = new TPSCommand(coreSpigot);
            automaticClearLag = new AutomaticClearLag(coreSpigot);
            automaticClearLag.clearLagScheduler();
            new SetPortalCommand(coreSpigot);
            portalManager = new PortalManager(coreSpigot);
            new PlayerPortalListener(coreSpigot);
            new Portal();
            fileManager.loadPortalConfig();
            portalManager.loadPortals();

            //init commands
            sender.sendMessage(prefix + "§fload Commands");
            //Commands
            new AFKCommand(coreSpigot);
            new FlyCommand(coreSpigot);
            new SetLocCommand(coreSpigot);
            new SetRangCommand(coreSpigot);
            new SpawnCommand(coreSpigot);

            //init listener
            sender.sendMessage(prefix + "§fLoad Listener");
            afkListener = new AFKListener(coreSpigot);
            new CancelListener(coreSpigot);
            new CancelWeatherListener(coreSpigot);
            new ChatListener(coreSpigot);
            new CompassListener(coreSpigot);
            flyListener = new FlyListener(coreSpigot);
            new JoinListener(coreSpigot);
            new LobbySwitcherListener(coreSpigot);
            minionListener = new MinionListener(coreSpigot);
            musicListener = new MusicListener(coreSpigot);
            new PlayerMoveListener(coreSpigot);
            new QuitListener(coreSpigot);
            new ScrollListener(coreSpigot);
            new ServerListener(coreSpigot);
            stopReloadRestartListener = new StopReloadRestartListener(coreSpigot);
            new YourProfileListener(coreSpigot);
            settingsListener = new SettingsListener(coreSpigot);

            settingsAPI = new SettingsAPI(coreSpigot);
            spawnLocationAPI = new SpawnLocationAPI(coreSpigot);

            portalManager = new PortalManager(coreSpigot);

            //Utils
            sender.sendMessage(prefix + "§fload Utils");
            itemCreator = new ItemCreator(coreSpigot);
            lobbyParticle = new LobbyParticle(coreSpigot);
            locationsUtil = new LocationsUtil();
            new Particle();
            playerRealTime = new PlayerRealTime(coreSpigot);
            setLocations = new SetLocations(coreSpigot);
            fileManager.loadPortalConfig();
            afkListener.LocationTimer();
            mySQLLoader.loadConf();
            mySQLLoader.loadMySQL();
            setLocations.saveLocs();
            playerRealTime.setPlayerRealTime();
            lobbyParticle.playEnderSignal();
            lobbyParticle.playEnchantment();
        } else if (serverType.equals(ServerType.DEFAULT) || serverType.equals(ServerType.PLUGIN_TEST_SERVER)) {
            data = new Data();
            //tabList = new TabList(coreSpigot);
            //tabList.createTabList();
            new JoinListener(coreSpigot);
            new ChatListener(coreSpigot);
            stopReloadRestartListener = new StopReloadRestartListener(coreSpigot);
            mySQLLoader.loadMySQL();
            /*
            getSchedulerSaver().createScheduler(
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Bukkit.getOnlinePlayers().forEach(all -> tabList.setTabList(all));
                        }
                    }.runTaskTimer(coreSpigot, 0L, 20L)
            );
            */
        } else if (serverType.equals(ServerType.SKY_PVP)) {
            data = new Data();
            //tabList = new TabList(coreSpigot);
            //tabList.createTabList();
            new JoinListener(coreSpigot);
            new ChatListener(coreSpigot);
            stopReloadRestartListener = new StopReloadRestartListener(coreSpigot);
        } else if (serverType.equals(ServerType.CITY_BUILD)) {
            data = new Data();
            //tabList = new TabList(coreSpigot);
            //tabList.createTabList();
            new JoinListener(coreSpigot);
            new ChatListener(coreSpigot);
            stopReloadRestartListener = new StopReloadRestartListener(coreSpigot);
            fileManager = new FileManager(coreSpigot);
            new ChestCommand(coreSpigot);
            new BlockPlaceChestListener(coreSpigot);
            /*
            getSchedulerSaver().createScheduler(
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Bukkit.getOnlinePlayers().forEach(all -> tabList.setTabList(all));
                        }
                    }.runTaskTimer(coreSpigot, 0L, 20L * 5)
            );
            */
        } else if (serverType.equals(ServerType.BAU_SERVER)) {
            new CancelWeatherListener(coreSpigot);
            new FlyWalkSpeedCommand(coreSpigot);
            data = new Data();
            //tabList = new TabList(coreSpigot);
            //tabList.createTabList();
            new SetPortalCommand(coreSpigot);
            portalManager = new PortalManager(coreSpigot);
            new Portal();
            new GMCommand(coreSpigot);
            tpsCommand = new TPSCommand(coreSpigot);
            new ChestCommand(coreSpigot);
            new BlockPlaceChestListener(coreSpigot);
            new HeadCommand(coreSpigot);
            new JoinListener(coreSpigot);
            new ChatListener(coreSpigot);
            stopReloadRestartListener = new StopReloadRestartListener(coreSpigot);
            locationsUtil = new LocationsUtil();
            setLocations = new SetLocations(coreSpigot);
            new SetLocCommand(coreSpigot);
            fileManager.loadPortalConfig();
            portalManager.loadPortals();
            /*
            getSchedulerSaver().createScheduler(
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Bukkit.getOnlinePlayers().forEach(all -> tabList.setTabList(all));
                        }
                    }.runTaskTimer(coreSpigot, 0L, 20L * 5)
            );
            */
        } else if (serverType.equals(ServerType.BED_WARS)) {
            new CancelWeatherListener(coreSpigot);
            data = new Data();
            new JoinListener(coreSpigot);
            new ChatListener(coreSpigot);
            stopReloadRestartListener = new StopReloadRestartListener(coreSpigot);
        } else if (serverType.equals(ServerType.VORBAUEN)) {
            new CancelWeatherListener(coreSpigot);
            data = new Data();
            //tabList = new TabList(coreSpigot);
            //tabList.createTabList();
            new GMCommand(coreSpigot);
            tpsCommand = new TPSCommand(coreSpigot);
            new ChestCommand(coreSpigot);
            new TippCommand(coreSpigot);
            new BlockPlaceChestListener(coreSpigot);
            new JoinListener(coreSpigot);
            new QuitListener(coreSpigot);
            new ChatListener(coreSpigot);
            stopReloadRestartListener = new StopReloadRestartListener(coreSpigot);
            locationsUtil = new LocationsUtil();
            setLocations = new SetLocations(coreSpigot);
            new SetLocCommand(coreSpigot);
            new SpawnCommand(coreSpigot);
            setLocations.saveSpawn();
        }
    }

    public void stopServer() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            int online = ThreadLocalRandom.current().nextInt(1, TimoCloudAPI.getUniversalAPI().getServerGroup("Lobby").getOnlineAmount() + 1);
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                dataOutputStream.writeUTF("Connect");
                dataOutputStream.writeUTF("Lobby-" + online);
                all.sendPluginMessage(this.coreSpigot, "BungeeCord", byteArrayOutputStream.toByteArray());
            } catch (IOException ignored) {
            }
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

    public ServerType getServerType() {
        return serverType;
    }

    public NickCommand getNickCommand() {
        return nickCommand;
    }

    public AFKListener getAfkListener() {
        return afkListener;
    }

    public BlockRedstoneListener getBlockRedstoneListener() {
        return blockRedstoneListener;
    }

    public FlyListener getFlyListener() {
        return flyListener;
    }

    public MinionListener getMinionListener() {
        return minionListener;
    }

    public MusicListener getMusicListener() {
        return musicListener;
    }

    public SettingsListener getSettingsListener() {
        return settingsListener;
    }

    public StopReloadRestartListener getStopReloadRestartListener() {
        return stopReloadRestartListener;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public PortalManager getPortalManager() {
        return portalManager;
    }

    public NickManager getNickManager() {
        return nickManager;
    }

    public AsyncMySQL getAsyncMySQL() {
        return asyncMySQL;
    }

    public NickAPI getNickAPI() {
        return nickAPI;
    }

    public SettingsAPI getSettingsAPI() {
        return settingsAPI;
    }

    public SpawnLocationAPI getSpawnLocationAPI() {
        return spawnLocationAPI;
    }

    public CrashFixer getCrashFixer() {
        return crashFixer;
    }

    public ItemCreator getItemCreator() {
        return itemCreator;
    }

    public LocationsUtil getLocationsUtil() {
        return locationsUtil;
    }

    public SchedulerSaver getSchedulerSaver() {
        return schedulerSaver;
    }

    public SetLocations getSetLocations() {
        return setLocations;
    }

    //public TabList getTabList() {
    //    return tabList;
    //}

    public TicksPerSecond getTicksPerSecond() {
        return ticksPerSecond;
    }

    public Data getData() {
        return data;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }
}
