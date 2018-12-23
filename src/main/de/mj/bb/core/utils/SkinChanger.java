package main.de.mj.bb.core.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.*;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Multimap;
import com.mojang.authlib.GameProfile;
import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class SkinChanger {

    private static final CoreSpigot coreSpigot = CoreSpigot.getInstance();
    private static Map<UUID, Nickname> nicks = new HashMap<>();
    private static ProtocolManager protocolManager;
    private static Method fillProfilePropertiesMethod;
    private static Object sessionServiceObject;
    private static LoadingCache<String, Collection<WrappedSignedProperty>> cachedTextures;

    static {
        cachedTextures = CacheBuilder.newBuilder().expireAfterWrite(1L, TimeUnit.HOURS)
                .build(new CacheLoader<String, Collection<WrappedSignedProperty>>() {
                    public Collection<WrappedSignedProperty> load(String name)
                            throws ReflectiveOperationException {
                        Player player = Bukkit.getPlayer(name);
                        WrappedGameProfile profile;
                        if (player != null) {
                            profile = WrappedGameProfile.fromPlayer(player);
                        } else {
                            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);
                            profile = WrappedGameProfile.fromOfflinePlayer(offlinePlayer);
                            profile = fillProfileProperties(profile);
                        }
                        return profile.getProperties().get("textures");
                    }
                });
        protocolManager = ProtocolLibrary.getProtocolManager();
        listenToPlayerInfo();
    }

    public static void nick(Player player, Nickname nick) {
        GameProfile profile = nick.getProfile(player.getUniqueId());
        if (profile == null) {
            return;
        }
        List<Player> show = SkinChanger.despawnPlayer(player);
        nicks.put(player.getUniqueId(), nick);
        SkinChanger.spawnPlayer(player, nick.getColoredName());
        for (Player online : show) {
            online.showPlayer(player);
        }
        SkinChanger.sendRespawnPacket(player);
    }

    public static void unNick(Player player) {
        if (!SkinChanger.isNicked(player)) {
            throw new IllegalStateException("Player " + player.getName() + " is not nicked!");
        }
        List<Player> show = SkinChanger.despawnPlayer(player);
        nicks.remove(player.getUniqueId());

        SkinChanger.spawnPlayer(player, player.getName());
        for (Player online : show) {
            online.showPlayer(player);
        }
        SkinChanger.sendRespawnPacket(player);
    }

    private static void listenToPlayerInfo() {
        protocolManager.addPacketListener(new PacketAdapter(coreSpigot, PacketType.Play.Server.PLAYER_INFO) {
            public void onPacketSending(PacketEvent packetEvent) {
                try {
                    if (!(packetEvent.getPacket().getPlayerInfoAction().read(0)).equals(EnumWrappers.PlayerInfoAction.ADD_PLAYER)) {
                        return;
                    }
                    List<PlayerInfoData> playerInfoDataList = new ArrayList<>();
                    for (PlayerInfoData data : packetEvent.getPacket().getPlayerInfoDataLists().read(0)) {
                        WrappedGameProfile profile = data.getProfile();
                        UUID uuid = data.getProfile().getUUID();
                        if (!isNicked(uuid)) {
                            playerInfoDataList.add(data);
                        } else {
                            Nickname nick = SkinChanger.getNickname(uuid);
                            String tag;
                            tag = nick.getName();
                            String texturesName = nick.getName();

                            Collection<WrappedSignedProperty> textures = getTextures(texturesName);
                            if (tag == null) {
                                tag = profile.getName();
                            }
                            if (tag.length() > 16) {
                                tag = tag.substring(0, 15);
                            }
                            profile = profile.withName(tag);
                            Multimap<String, WrappedSignedProperty> properties = profile.getProperties();
                            properties.removeAll("textures");
                            properties.putAll("textures", textures);

                            playerInfoDataList.add(new PlayerInfoData(profile, data.getPing(), data.getGameMode(), data
                                    .getDisplayName()));
                        }
                    }
                    packetEvent.getPacket().getPlayerInfoDataLists().write(0, playerInfoDataList);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    protected static Collection<WrappedSignedProperty> getTextures(String name) {
        try {
            return cachedTextures.get(name);
        } catch (ExecutionException exception) {
            System.err.println("Unable to load textures for " + name + "!");
        }
        return null;
    }

    private static WrappedGameProfile fillProfileProperties(WrappedGameProfile profile)
            throws ReflectiveOperationException {
        if (fillProfilePropertiesMethod == null) {
            Server server = Bukkit.getServer();
            Object minecraftServerObject = server.getClass().getDeclaredMethod("getServer", new Class[0]).invoke(server);
            for (Method method : minecraftServerObject.getClass().getMethods()) {
                if (method.getReturnType().getSimpleName().equals("MinecraftSessionService")) {
                    sessionServiceObject = method.invoke(minecraftServerObject);
                    break;
                }
            }
            for (Method method : sessionServiceObject.getClass().getMethods()) {
                if (method.getName().equals("fillProfileProperties")) {
                    fillProfilePropertiesMethod = method;
                    break;
                }
            }
        }
        return WrappedGameProfile.fromHandle(fillProfilePropertiesMethod.invoke(sessionServiceObject, profile.getHandle(), Boolean.valueOf(true)));
    }

    public static boolean isNicked(Player player) {
        return SkinChanger.isNicked(player.getUniqueId());
    }

    public static boolean isNicked(UUID player) {
        return nicks.containsKey(player);
    }

    public static Nickname getNickname(UUID player) {
        return nicks.get(player);
    }

    private static void sendRespawnPacket(Player player) {
        Location loc = player.getLocation().clone();
        boolean allowFlight = player.getAllowFlight();
        boolean flying = player.isFlying();
        int slot = player.getInventory().getHeldItemSlot();
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.RESPAWN);
        packet.getIntegers().write(0, Integer.valueOf(player.getWorld().getEnvironment().getId()));
        packet.getDifficulties().write(0,
                EnumWrappers.Difficulty.valueOf(player.getWorld().getDifficulty().toString()));
        packet.getGameModes().write(0, EnumWrappers.NativeGameMode.fromBukkit(player.getGameMode()));
        packet.getWorldTypeModifier().write(0, player.getWorld().getWorldType());
        try {
            protocolManager.sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        player.teleport(loc);
        player.setAllowFlight(allowFlight);
        player.setFlying(flying);
        player.updateInventory();
        player.getInventory().setHeldItemSlot(slot);
    }

    private static void spawnPlayer(Player player, String displayName) {
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.PLAYER_INFO);
        packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);
        WrappedGameProfile wrappedProfile = new WrappedGameProfile(player.getUniqueId(), player.getName());
        EnumWrappers.NativeGameMode nativeGameMode = EnumWrappers.NativeGameMode.fromBukkit(player.getGameMode());
        packet.getPlayerInfoDataLists().write(0, Collections.singletonList(new PlayerInfoData(wrappedProfile, 20, nativeGameMode,
                WrappedChatComponent.fromText(displayName))));
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.canSee(player)) {
                try {
                    protocolManager.sendServerPacket(online, packet);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static List<Player> despawnPlayer(Player player) {
        List<Player> show = new ArrayList<>();
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.PLAYER_INFO);
        packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);
        WrappedGameProfile profile = new WrappedGameProfile(player.getUniqueId(), null);
        packet.getPlayerInfoDataLists().write(0, Collections.singletonList(new PlayerInfoData(profile, 0, EnumWrappers.NativeGameMode.SURVIVAL,
                WrappedChatComponent.fromText(""))));
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.canSee(player)) {
                show.add(online);
                online.hidePlayer(player);
            }
            try {
                protocolManager.sendServerPacket(online, packet);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return show;
    }
}
