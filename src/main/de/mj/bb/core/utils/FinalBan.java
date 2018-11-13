package main.de.mj.bb.core.utils;

import main.de.mj.bb.core.CoreBungee;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import nl.chimpgamer.networkmanagerapi.NetworkManagerPlugin;
import nl.chimpgamer.networkmanagerapi.modules.punishments.Punishment;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FinalBan implements Listener {

    private final CoreBungee coreBungee;
    private Map<ProxiedPlayer, String> reason = new HashMap<>();
    private Map<ProxiedPlayer, Long> time = new HashMap<>();
    private Map<ProxiedPlayer, ProxiedPlayer> punisher = new HashMap<>();

    public FinalBan(CoreBungee coreBungee) {
        this.coreBungee = coreBungee;
        coreBungee.registerListener(this);
    }

    @EventHandler
    public void onPluginMessage(PluginMessageEvent messageEvent) {
        if (messageEvent.getTag().equalsIgnoreCase("BungeeCord")) {
            DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(messageEvent.getData()));
            try {
                String channel = dataInputStream.readUTF();
                if (channel.equals("finalBan")) {
                    Map.Entry<ProxiedPlayer, String> entry = reason.entrySet().iterator().next();
                    ProxiedPlayer player = entry.getKey();
                    if (coreBungee.getHookManager().getNetworkManagerPlugin().getPlayer(player.getUniqueId()).isOnline()) {
                        Punishment punishment = new Punishment() {
                            public void punish() {
                            }

                            public void unban(UUID uuid) {
                            }

                            @Override
                            public int getId() {
                                return 0;
                            }

                            @Override
                            public Type getType() {
                                return Type.GBAN;
                            }

                            @Override
                            public UUID getUuid() {
                                return player.getUniqueId();
                            }

                            @Override
                            public UUID getPunisher() {
                                return punisher.get(player).getUniqueId();
                            }

                            @Override
                            public UUID getUnbanner() {
                                return null;
                            }

                            @Override
                            public long getTime() {
                                return time.get(player);
                            }

                            @Override
                            public long getEnd() {
                                return System.currentTimeMillis() + time.get(player);
                            }

                            @Override
                            public String getIp() {
                                return player.getAddress().toString();
                            }

                            @Override
                            public String getServer() {
                                return null;
                            }

                            @Override
                            public String getReason() {
                                return reason.get(player);
                            }

                            @Override
                            public boolean isActive() {
                                return true;
                            }

                            @Override
                            public NetworkManagerPlugin getNetworkManagerPlugin() {
                                return coreBungee.getHookManager().getNetworkManagerPlugin();
                            }
                        };
                        coreBungee.getHookManager().getNetworkManagerPlugin().getPlayer(player.getUniqueId()).generateBanMessage(punishment);
                        punishment.punish();
                        reason.clear();
                        punisher.clear();
                        time.clear();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setReason(Map<ProxiedPlayer, String> reason) {
        this.reason = reason;
    }

    public void setTime(Map<ProxiedPlayer, Long> time) {
        this.time = time;
    }

    public void setPunisher(Map<ProxiedPlayer, ProxiedPlayer> punisher) {
        this.punisher = punisher;
    }
}
