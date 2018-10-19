package main.de.mj.bb.core.commands;

import main.de.mj.bb.core.CoreBungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class BanCommand extends Command {

    private final CoreBungee coreBungee;

    public BanCommand(CoreBungee coreBungee) {
        super("rban");
        this.coreBungee = coreBungee;
        coreBungee.setCommand(this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender.hasPermission("server.ban")) {
            if (sender instanceof ProxiedPlayer) {
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
            }
        }
    }

    private void punish(ProxiedPlayer player, ProxiedPlayer punisher, String reason, long time) {
        /*
        if (!coreBungee.getHookManager().getNetworkManagerPlugin().getPlayer(player.getUniqueId()).get().isBanned())
        if (coreBungee.getHookManager().getNetworkManagerPlugin().getPlayer(player.getUniqueId()).isPresent()) {
            coreBungee.getHookManager().getNetworkManagerPlugin().getPlayer(player.getUniqueId()).get().generateBanMessage(new Punishment() {
                @Override
                public void punish() {
                    player.disconnect(new TextComponent(reason));
                }

                @Override
                public void unban(UUID uuid) {
                    coreBungee.getHookManager().getNetworkManagerPlugin().getPlayer(uuid).get().getActiveBan().unban(uuid);
                }

                @Override
                public int getId() {
                    return 0;
                }

                @Override
                public Type getType() {
                    return null;
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
            });
        }
        */
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
