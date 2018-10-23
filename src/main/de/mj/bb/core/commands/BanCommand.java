package main.de.mj.bb.core.commands;

import main.de.mj.bb.core.CoreBungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import nl.chimpgamer.networkmanagerapi.NetworkManagerPlugin;
import nl.chimpgamer.networkmanagerapi.modules.punishments.Punishment;

import java.util.UUID;

public class BanCommand extends Command {

    private final CoreBungee coreBungee;

    public BanCommand(CoreBungee coreBungee) {
        super("rban");
        this.coreBungee = coreBungee;
        coreBungee.registerCommand(this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender.hasPermission("server.ban")) {
            if (sender instanceof ProxiedPlayer) {
                if (args.length == 2) {
                    if (ProxyServer.getInstance().getPlayer(args[0]) != null) {
                        if (isInteger(args[1])) {
                            int reasonInt = Integer.parseInt(args[1]);
                            ProxiedPlayer player = ProxyServer.getInstance().getPlayer(args[0]);
                            switch (reasonInt) {
                                case 1:
                                    punish(player, (ProxiedPlayer) sender, "Du bist dumm", 10000);
                                    break;
                                case 2:
                                    break;
                                default:
                                    sender.sendMessage(new TextComponent("---------- " + coreBungee.getData().getPrefix() + " ----------"));
                                    sender.sendMessage(new TextComponent("Folgende Gründe existieren:"));
                                    sender.sendMessage(new TextComponent("1 ➟ "));
                            }
                        } else {
                            //TODO Zahl<1-x>!
                        }
                    } else {
                        //TODO exist nicht
                    }
                } else {
                    //TODO not enough args
                }
            }
        }
    }

    private void punish(ProxiedPlayer player, ProxiedPlayer punisher, String reason, long time) {
        if (coreBungee.getHookManager().getNetworkManagerPlugin().getPlayer(player.getUniqueId()).isOnline()) {
            Punishment punishment = new Punishment() {
                @Override
                public void punish() {
                    player.disconnect(new TextComponent(reason));
                }

                @Override
                public void unban(UUID uuid) {
                    coreBungee.getHookManager().getNetworkManagerPlugin().getPlayer(uuid).getActiveBan().unban(uuid);
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
                    return punisher.getUniqueId();
                }

                @Override
                public UUID getUnbanner() {
                    return null;
                }

                @Override
                public long getTime() {
                    return time;
                }

                @Override
                public long getEnd() {
                    return 0;
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
                    return reason;
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
        }
    }

    private boolean isInteger(Object object) {
        if (object instanceof Integer) {
            return true;
        } else {
            String string = object.toString();
            try {
                Integer.parseInt(string);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
}
