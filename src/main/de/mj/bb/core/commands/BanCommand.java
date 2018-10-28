package main.de.mj.bb.core.commands;

import main.de.mj.bb.core.CoreBungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import nl.chimpgamer.networkmanagerapi.NetworkManagerPlugin;
import nl.chimpgamer.networkmanagerapi.modules.punishments.Punishment;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
                                    sendToServer(player, "Test:" + sender.getName() + ":10000");
                                    punish(player, (ProxiedPlayer) sender, "Hacking", 1000);
                                    break;
                                case 2:
                                    sendToServer(player, "Test:" + sender.getName() + ":10000");
                                    punish(player, (ProxiedPlayer) sender, "Trolling", 1000);
                                    break;
                                case 3:
                                    sendToServer(player, "Test:" + sender.getName() + ":10000");
                                    punish(player, (ProxiedPlayer) sender, "Name", 1000);
                                    break;
                                case 4:
                                    sendToServer(player, "Test:" + sender.getName() + ":10000");
                                    punish(player, (ProxiedPlayer) sender, "Skin", 1000 );
                                    break;
                                case 5:
                                    sendToServer(player, "Test:" + sender.getName() + ":10000");
                                    punish(player, (ProxiedPlayer) sender, "Bug-Using", 1000);
                                    break;
                                case 6:
                                    sendToServer(player, "Test:" + sender.getName() + ":10000");
                                    punish(player, (ProxiedPlayer) sender, "Rassismus", 1000);
                                    break;
                                case 7:
                                    sendToServer(player, "Test:" + sender.getName() + ":10000");
                                    punish(player, (ProxiedPlayer) sender, "Sexsismus", 1000);
                                    break;
                                default:
                                    reasons(sender);
                            }

                        } else {
                            sender.sendMessage(new TextComponent(coreBungee.getData().getPrefix() + "§7Bitte verwende: </rban> <Player> <Zahl>"));
                            reasons(sender);
                        }
                    } else {
                        sender.sendMessage(new TextComponent(coreBungee.getData().getPrefix() + "§7Die Zahl: §b" + args[1] + " §7Existiert nicht!"));
                        reasons(sender);
                    }
                } else {
                    sender.sendMessage(new TextComponent(coreBungee.getData().getPrefix() + "§7Du benutzt zu wenige Argumente! Verwende: </rban> <player> <Zahl>"));
                    reasons(sender);
                }
            }
        }
    }

    private void sendToServer (ProxiedPlayer player, String message) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF(message);
            player.getServer().sendData("ban", byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void punish(ProxiedPlayer player, ProxiedPlayer punisher, String reason, long time) {
        Map<ProxiedPlayer, String> map1 = new HashMap<>();
        map1.put(player, reason);
        Map<ProxiedPlayer, Long> longMap = new HashMap<>();
        longMap.put(player, time);
        Map<ProxiedPlayer, ProxiedPlayer> playerMap = new HashMap<>();
        playerMap.put(player, punisher);
        coreBungee.getFinalBan().setPunisher(playerMap);
        coreBungee.getFinalBan().setReason(map1);
        coreBungee.getFinalBan().setTime(longMap);
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
    private void reasons(CommandSender sender){
        sender.sendMessage(new TextComponent("---------- " + coreBungee.getData().getPrefix() + " ----------"));
        sender.sendMessage(new TextComponent("§7Folgende Gründe existieren:"));
        sender.sendMessage(new TextComponent("§71 §a➟ §7Hacking"));
        sender.sendMessage(new TextComponent("§72 §a➟ §7Trolling"));
        sender.sendMessage(new TextComponent("§73 §a➟ §7Name"));
        sender.sendMessage(new TextComponent("§74 §a➟ §7Skin"));
        sender.sendMessage(new TextComponent("§75 §a➟ §7Bug-Using"));
        sender.sendMessage(new TextComponent("§76 §a➟ §7Rassismus"));
        sender.sendMessage(new TextComponent("§77 §a➟ §7Sexsismus"));
    }
}
