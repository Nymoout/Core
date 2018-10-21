package main.de.mj.bb.core.commands;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TippCommand implements CommandExecutor {

    private final String prefix;
    private final String noPerm;
    private String prefixt = "§f[§aTipp§f]§7 ";

    public TippCommand(CoreSpigot coreSpigot) {
        prefix = coreSpigot.getModuleManager().getData().getPrefix();
        noPerm = coreSpigot.getModuleManager().getData().getNoPerm();
        coreSpigot.setCommand(this, "tipp");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player user = (Player) sender;
            if (user.hasPermission("vorbauen.tipp.send") || user.isOp()) {
                if (args.length == 2) {
                    Player target = Bukkit.getPlayerExact(args[0]);
                    if (target != null) {
                        if (isInteger(args[1])) {
                            int tipp = Integer.valueOf(args[1]);
                            switch (tipp) {
                                case 1:
                                    target.sendMessage(prefixt + "Baue mehr Strukturen mit ein, um eine höhere Chance auf eine Annahme zu bekommen!");
                                    user.sendMessage(prefix + "§aDu hast den Spieler: §b" + target.getName() + " §aden tipp: §b" + tipp + " §agegeben!");
                                    break;
                                case 2:
                                    target.sendMessage(prefixt + "Baue mehr Formen mit ein, damit dein Gebäude natürlicher Aussieht!");
                                    user.sendMessage(prefix + "§aDu hast den Spieler: §b" + target.getName() + " §aden tipp: §b" + tipp + " §agegeben!");

                                    break;
                                case 3:
                                    target.sendMessage(prefixt + "Füge mehr Terra hinzu, sonst sieht es zu unnatürlich aus!");
                                    user.sendMessage(prefix + "§aDu hast den Spieler: §b" + target.getName() + " §aden tipp: §b" + tipp + " §agegeben!");
                                    break;
                                case 4:
                                    target.sendMessage(prefixt + "Baue mehr Details mit ein, um eine höhere Chance auf eine Annahme zu bekommen!");
                                    user.sendMessage(prefix + "§aDu hast den Spieler: §b" + target.getName() + " §aden tipp: §b" + tipp + " §agegeben!");
                                    break;
                                case 5:
                                    target.sendMessage(prefixt + "Benutze das komplette Plot.");
                                    user.sendMessage(prefix + "§aDu hast den Spieler: §b" + target.getName() + " §aden tipp: §b" + tipp + " §agegeben!");
                                    break;
                                default:
                                    user.sendMessage(prefixt + "Kannst du keine Zahlen von 1-5 verwenden?!");
                                    user.sendMessage("§b1 §a» §7Mehr Strukturen.");
                                    user.sendMessage("§b2 §a» §7Mehr Formen.");
                                    user.sendMessage("§b3 §a» §7Mehr Terra.");
                                    user.sendMessage("§b4 §a» §7Mehr Details.");
                                    user.sendMessage("§b5 §a» §7Use the Plot.");
                            }
                        } else
                            user.sendMessage("§cVerwende eine gültige Zahl.");
                    } else
                        user.sendMessage(prefix + "§csry dein Bro, §b" + args[0] + " §cist nicht Online!");
                } else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
                    user.sendMessage("§b1 §a» §7Mehr Strukturen.");
                    user.sendMessage("§b2 §a» §7Mehr Formen.");
                    user.sendMessage("§b3 §a» §7Mehr Terra.");
                    user.sendMessage("§b4 §a» §7Mehr Details.");
                    user.sendMessage("§b5 §a» §7Use the Plot.");
                } else
                    user.sendMessage(prefix + "Benutze: </tipp> <player> <1-5>");
            } else
                user.sendMessage(noPerm);
        } else
            sender.sendMessage("Nein Nein Nein! Das geht nur Ingame!");
        return false;
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
