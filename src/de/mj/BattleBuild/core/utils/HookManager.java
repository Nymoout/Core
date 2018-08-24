package de.mj.BattleBuild.core.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.FieldAccessException;
import de.mj.BattleBuild.core.Core;
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
    private final Core core;
    private final ConsoleCommandSender sender;
    private final String prefix = new Data().getPrefix();
    private ProtocolManager protocolManager;
    private NetworkManagerBridge networkManagerBridge;
    private LuckPermsApi luckPermsApi;
    private PAFPlayerManager pafPlayerManager;
    private ClansManager clansManager;
    private VIPHide vipHide;

    public HookManager(Core core) {
        this.core = core;
        sender = core.getSender();
    }

    /**
     * @param serverType
     */
    public void hook(ServerType serverType) {
        if (serverType.equals(ServerType.LOBBY)) {
            sender.sendMessage(prefix + "§2try to hook in Vault...");
            if (!setupEconomy()) {
                sender.sendMessage(String.format("§c[%s] - Vault-Dependecy wasn't found - disable Plugin!", core.getDescription().getName()));
                core.getServer().getPluginManager().disablePlugin(core);
                return;
            } else {
                sender.sendMessage(prefix + "§2hooked into: Vault");
                sender.sendMessage(prefix + "§2hooked into Economy-Plugin: " + econ.getName());
            }

            sender.sendMessage(prefix + "§etry to hook into TimoCloud...");
            if (core.getServer().getPluginManager().getPlugin("TimoCloud") != null) {
                sender.sendMessage(prefix + "§ehooked into: TimoCloud");
            } else {
                sender.sendMessage(String.format("§c[%s] - TimoCloud wasn't found - disable Plugin!", core.getDescription().getName()));
                core.getServer().getPluginManager().disablePlugin(core);
            }

            sender.sendMessage(prefix + "§dtry to hook into LuckPerms...");
            if (core.getServer().getPluginManager().getPlugin("LuckPerms") != null) {
                this.luckPermsApi = LuckPerms.getApi();
                sender.sendMessage(prefix + "§dhooked into: LuckPerms");
            } else {
                sender.sendMessage(String.format("§c[%s] - LuckPerms wasn't found - disable Plugin!", core.getDescription().getName()));
                core.getServer().getPluginManager().disablePlugin(core);
            }

            sender.sendMessage(prefix + "§6try to hook into FriendsAPIForPartyAndFriends...");
            if (core.getServer().getPluginManager().getPlugin("FriendsAPIForPartyAndFriends") != null) {
                this.pafPlayerManager = PAFPlayerManager.getInstance();
                sender.sendMessage(prefix + "§6hooked into: FriendsAPIForPartyAndFriends");
            } else {
                sender.sendMessage(String.format("§c[%s] - FriendsAPIForPartyAndFriends wasn't found - disable Plugin!", core.getDescription().getName()));
                core.getServer().getPluginManager().disablePlugin(core);
            }

            sender.sendMessage(prefix + "§5try to hook into Clans-Spigot-Part...");
            if (core.getServer().getPluginManager().getPlugin("Clans-Spigot-Part") != null) {
                this.clansManager = ClansManager.getInstance();
                sender.sendMessage(prefix + "§5hooked into: Clans-Spigot-Part");
            } else {
                sender.sendMessage(String.format("§c[%s] - Clans-Spigot-Part wasn't found - disable Plugin!", core.getDescription().getName()));
                core.getServer().getPluginManager().disablePlugin(core);
            }

            sender.sendMessage(prefix + "§atry to hook into NetworkManagerBridge...");
            if (core.getServer().getPluginManager().getPlugin("NetworkManagerBridge") != null) {
                this.networkManagerBridge = (NetworkManagerBridge) core.getServer().getPluginManager().getPlugin("NetworkManagerBridge");
                sender.sendMessage(prefix + "§ahooked into: NetworkManagerBridge");
            } else {
                sender.sendMessage(String.format("§c[%s] - NetworkManagerBridge wasn't found - disable Plugin!", core.getDescription().getName()));
                core.getServer().getPluginManager().disablePlugin(core);
            }

            sender.sendMessage(prefix + "§etry to hook into ProtocolLib...");
            if (core.getServer().getPluginManager().getPlugin("ProtocolLib") != null) {
                this.protocolManager = ProtocolLibrary.getProtocolManager();
                sender.sendMessage(prefix + "§ehooked into: ProtocolLib");
                blockTabComplete();
                sender.sendMessage(prefix + "§eBlockTabComplete-Module was successfully enabled!");
            } else {
                sender.sendMessage(String.format("§c[%s] - ProtocolLib wasn't found - disable TabComplete!", core.getDescription().getName()));
                core.getServer().getPluginManager().disablePlugin(core);
            }

            sender.sendMessage(prefix + "§btry to hook into VIPHide...");
            if (core.getServer().getPluginManager().getPlugin("VIPHide") != null) {
                this.vipHide = VIPHide.instance;
                sender.sendMessage(prefix + "§bhooked into: VIPHide");
            } else {
                sender.sendMessage(String.format("§c[%s] - VIPHide wasn't found - disable Plugin", core.getDescription().getName()));
                core.getServer().getPluginManager().disablePlugin(core);
            }
        }
        if (serverType.equals(ServerType.DEFAULT)) {
            sender.sendMessage(prefix + "§dtry to hook into LuckPerms...");
            if (core.getServer().getPluginManager().getPlugin("LuckPerms") != null) {
                this.luckPermsApi = LuckPerms.getApi();
                sender.sendMessage(prefix + "§dhooked into: LuckPerms");
            } else {
                sender.sendMessage(String.format("§c[%s] - LuckPerms wasn't found - disable Plugin!", core.getDescription().getName()));
                core.getServer().getPluginManager().disablePlugin(core);
            }
            sender.sendMessage(prefix + "§btry to hook into VIPHide...");
            if (core.getServer().getPluginManager().getPlugin("VIPHide") != null) {
                this.vipHide = VIPHide.instance;
                sender.sendMessage(prefix + "§bhooked into: VIPHide");
            } else {
                sender.sendMessage(String.format("§c[%s] - VIPHide wasn't found - disable Plugin", core.getDescription().getName()));
                core.getServer().getPluginManager().disablePlugin(core);
            }
            sender.sendMessage(prefix + "§etry to hook into ProtocolLib...");
            if (core.getServer().getPluginManager().getPlugin("ProtocolLib") != null) {
                this.protocolManager = ProtocolLibrary.getProtocolManager();
                sender.sendMessage(prefix + "§ehooked into: ProtocolLib");
                blockTabComplete();
                sender.sendMessage(prefix + "§eBlockTabComplete-Module was successfully enabled!");
            } else {
                sender.sendMessage(String.format("§c[%s] - ProtocolLib wasn't found - disable TabComplete!", core.getDescription().getName()));
                core.getServer().getPluginManager().disablePlugin(core);
            }
        }
    }

    private boolean setupEconomy() {
        if (core.getServer().getPluginManager().getPlugin("Vault") == null) {
            System.out.println("Vault nicht gefunden");
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = core.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            System.out.println("RegisteredServiceProvider Fehler");
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private void blockTabComplete() {
        this.protocolManager.addPacketListener(new PacketAdapter(core, ListenerPriority.HIGHEST, PacketType.Play.Client.TAB_COMPLETE) {
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
