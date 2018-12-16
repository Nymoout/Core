package main.de.mj.bb.core.commands;

import main.de.mj.bb.core.CoreSpigot;
import main.de.mj.bb.core.utils.UUIDFetcher;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class HeadCommand implements CommandExecutor {

    private final CoreSpigot coreSpigot;

    public HeadCommand(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setCommand(this, "head");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            if (strings.length == 1 || strings.length == 2) {
                int amount = 1;
                if (strings.length == 2 && isInteger(strings[1])) amount = Integer.parseInt(strings[1]);
                Player player = (Player) commandSender;
                if (coreSpigot.getModuleManager().getSettingsAPI().getValue(UUIDFetcher.getUUID(strings[0])) != null) {
                    player.getInventory().addItem(coreSpigot.getModuleManager().getItemCreator().createItemWithPlayer(UUIDFetcher.getUUID(strings[0]).toString(), amount, strings[0]));
                } else {
                    ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, amount, (short) 3);
                    SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
                    skullMeta.setOwner(strings[0]);
                    skullMeta.setDisplayName(strings[0]);
                    itemStack.setItemMeta(skullMeta);
                    player.getInventory().addItem(itemStack);
                }
            } else commandSender.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "Bitte verwende /head <playerName> <amount>");
        } else commandSender.sendMessage(coreSpigot.getModuleManager().getData().getOnlyPlayer());
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
