package de.mj.BattleBuild.lobby.utils;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import cloud.timo.TimoCloud.api.TimoCloudUniversalAPI;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.FieldAccessException;
import de.mj.BattleBuild.lobby.Lobby;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.spigot.clans.api.ClansManager;
import me.BukkitPVP.VIPHide.VIPHide;
import me.Dunios.NetworkManagerBridge.spigot.NetworkManagerBridge;
import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.LuckPermsApi;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;

public class HookManager {

    private static Economy econ;
    private final Lobby lobby;
    private final ConsoleCommandSender sender;
    private final String prefix = new Data().getPrefix();
    private ProtocolManager protocolManager;
    private TimoCloudUniversalAPI timoCloudUniversalAPI;
    private NetworkManagerBridge networkManagerBridge;
    private LuckPermsApi luckPermsApi;
    private PAFPlayerManager pafPlayerManager;
    private ClansManager clansManager;
    private VIPHide vipHide;

    public HookManager(Lobby lobby) {
        this.lobby = lobby;
        sender = lobby.getSender();
    }

    public void hook() {
        sender.sendMessage(prefix + "§2try to hook in Vault...");
        if (!setupEconomy()) {
            sender.sendMessage(String.format("§c[%s] - Vault-Dependecy wasn't found - disable Plugin!", lobby.getDescription().getName()));
            lobby.getServer().getPluginManager().disablePlugin(lobby);
            return;
        } else {
            sender.sendMessage(prefix + "§2hooked into: Vault");
            sender.sendMessage(prefix + "§2hooked into Economy-Plugin: " + econ.getName());
        }

        sender.sendMessage(prefix + "§etry to hook into TimoCloud...");
        if (lobby.getServer().getPluginManager().getPlugin("TimoCloud") != null) {
            this.timoCloudUniversalAPI = TimoCloudAPI.getUniversalAPI();
            sender.sendMessage(prefix + "§ehooked into: TimoCloud");
        } else {
            sender.sendMessage(String.format("§c[%s] - TimoCloud wasn't found - disable Plugin!", lobby.getDescription().getName()));
            lobby.getServer().getPluginManager().disablePlugin(lobby);
            return;
        }

        sender.sendMessage(prefix + "§dtry to hook into LuckPerms...");
        if (lobby.getServer().getPluginManager().getPlugin("LuckPerms") != null) {
            this.luckPermsApi = LuckPerms.getApi();
            sender.sendMessage(prefix + "§dhooked into: LuckPerms");
        } else {
            sender.sendMessage(String.format("§c[%s] - LuckPerms wasn't found - disable Plugin!", lobby.getDescription().getName()));
            lobby.getServer().getPluginManager().disablePlugin(lobby);
            return;
        }

        sender.sendMessage(prefix + "§6try to hook into FriendsAPIForPartyAndFriends...");
        if (lobby.getServer().getPluginManager().getPlugin("FriendsAPIForPartyAndFriends") != null) {
            this.pafPlayerManager = PAFPlayerManager.getInstance();
            sender.sendMessage(prefix + "§6hooked into: FriendsAPIForPartyAndFriends");
        } else {
            sender.sendMessage(String.format("§c[%s] - FriendsAPIForPartyAndFriends wasn't found - disable Plugin!", lobby.getDescription().getName()));
            lobby.getServer().getPluginManager().disablePlugin(lobby);
            return;
        }

        sender.sendMessage(prefix + "§5try to hook into Clans-Spigot-Part...");
        if (lobby.getServer().getPluginManager().getPlugin("Clans-Spigot-Part") != null) {
            this.clansManager = ClansManager.getInstance();
            sender.sendMessage(prefix + "§5hooked into: Clans-Spigot-Part");
        } else {
            sender.sendMessage(String.format("§c[%s] - Clans-Spigot-Part wasn't found - disable Plugin!", lobby.getDescription().getName()));
            lobby.getServer().getPluginManager().disablePlugin(lobby);
            return;
        }

        sender.sendMessage(prefix + "§atry to hook into NetworkManagerBridge...");
        if (lobby.getServer().getPluginManager().getPlugin("NetworkManagerBridge") != null) {
            this.networkManagerBridge = (NetworkManagerBridge) lobby.getServer().getPluginManager().getPlugin("NetworkManagerBridge");
            sender.sendMessage(prefix + "§ahooked into: NetworkManagerBridge");
        } else {
            sender.sendMessage(String.format("§c[%s] - NetworkManagerBridge wasn't found - disable Plugin!", lobby.getDescription().getName()));
            lobby.getServer().getPluginManager().disablePlugin(lobby);
        }

        sender.sendMessage(prefix + "§etry to hook into ProtocolLib...");
        if (lobby.getServer().getPluginManager().getPlugin("ProtocolLib") != null) {
            this.protocolManager = ProtocolLibrary.getProtocolManager();
            sender.sendMessage(prefix + "§ehooked into: ProtocolLib");
            blockTabComplete();
            sender.sendMessage(prefix + "§eBlockTabComplete-Module was successfully enabled!");
        } else {
            sender.sendMessage(String.format("§c[%s] - ProtocolLib wasn't found - disable TabComplete!", lobby.getDescription().getName()));
            lobby.getServer().getPluginManager().disablePlugin(lobby);
        }

        sender.sendMessage(prefix + "§btry to hook into VIPHide...");
        if (lobby.getServer().getPluginManager().getPlugin("VIPHide") != null) {
            this.vipHide = VIPHide.instance;
            sender.sendMessage(prefix + "§bhooked into: VIPHide");
        } else {
            sender.sendMessage(String.format("§c[%s] - VIPHide wasn't found - disable Plugin", lobby.getDescription().getName()));
            lobby.getServer().getPluginManager().disablePlugin(lobby);
        }
    }

    private boolean setupEconomy() {
        if (lobby.getServer().getPluginManager().getPlugin("Vault") == null) {
            System.out.println("Vault nicht gefunden");
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = lobby.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            System.out.println("RegisteredServiceProvider Fehler");
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private void blockTabComplete() {
        this.protocolManager.addPacketListener(new PacketAdapter(lobby, ListenerPriority.HIGHEST, PacketType.Play.Client.TAB_COMPLETE) {
            public void onPacketReceiving(PacketEvent packetEvent) {
                if (packetEvent.getPacketType() == PacketType.Play.Client.TAB_COMPLETE) {
                    try {
                        PacketContainer packetContainer = packetEvent.getPacket();
                        String message = (packetContainer.getSpecificModifier(String.class).read(0)).toLowerCase();
                        if ((message.startsWith("")) && (!message.startsWith("  "))) {
                            packetEvent.setCancelled(true);
                        }
                    } catch (FieldAccessException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    public TimoCloudUniversalAPI getTimoCloudUniversalAPI() {
        return timoCloudUniversalAPI;
    }

    public NetworkManagerBridge getNetworkManagerBridge() {
        return networkManagerBridge;
    }

    public LuckPermsApi getLuckPermsApi() {
        return luckPermsApi;
    }

    public PAFPlayerManager getPafPlayerManager() {
        return pafPlayerManager;
    }

    public ClansManager getClansManager() {
        return clansManager;
    }

    public VIPHide getVipHide() {
        return vipHide;
    }

    public Economy getEconomy() {
        return econ;
    }
}
