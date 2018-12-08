package main.de.mj.bb.core.commands;

import main.de.mj.bb.core.CoreBungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class MaintenanceCommand extends Command {

    private final CoreBungee coreBungee;
    private boolean maintenance = false;

    public MaintenanceCommand(CoreBungee coreBungee) {
        super("maintenance", "maintenance.toggle");
        coreBungee.registerCommand(this);
        this.coreBungee = coreBungee;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (strings.length == 1) {
            if (strings[0].equalsIgnoreCase("enable") || strings[0].equalsIgnoreCase("on")) {
                if (maintenance)
                    commandSender.sendMessage(new TextComponent(coreBungee.getData().getPrefix() + "Wartungsmodus bereits §aaktiv§7!"));
                else {
                    commandSender.sendMessage(new TextComponent(coreBungee.getData().getPrefix() + "Wartungmodus wurde erfolgreich §aaktiviert§7!"));
                    this.maintenance = true;
                    coreBungee.getProxy().getPlayers().forEach(all -> {
                        if (!all.hasPermission("player.team"))
                            all.disconnect(new TextComponent(coreBungee.getData().getPrefix() + "\n§cDer Wartungsmodus wurde aktiviert!\n§7Unser Netzwerk wird nun gewartet\n§aBitte versuche es zu einem späteren Zeitpunkt erneut!"));
                    });
                    coreBungee.getModuleManager().getBungeeAPI().setMaintenance(true);
                }
            } else if (strings[0].equalsIgnoreCase("disable") || strings[0].equalsIgnoreCase("off")) {
                if (!maintenance)
                    commandSender.sendMessage(new TextComponent(coreBungee.getData().getPrefix() + "Wartungsmodus bereits §cdeaktiviert§7!"));
                else {
                    commandSender.sendMessage(new TextComponent(coreBungee.getData().getPrefix() + "Wartungmodus wurde erfolgreich §cdeaktiviert§7!"));
                    this.maintenance = false;
                    coreBungee.getModuleManager().getBungeeAPI().setMaintenance(false);
                }
            }
        } else {
            if (maintenance)
                commandSender.sendMessage(new TextComponent(coreBungee.getData().getPrefix() + "Wartungsmodus ist §aaktiv§7!"));
            else
                commandSender.sendMessage(new TextComponent(coreBungee.getData().getPrefix() + "Wartungsmodus ist §cnicht aktiviert§7!"));
        }
    }

    public boolean isMaintenance() {
        return maintenance;
    }

    public void setMaintenance(boolean maintenance) {
        this.maintenance = maintenance;
    }
}
