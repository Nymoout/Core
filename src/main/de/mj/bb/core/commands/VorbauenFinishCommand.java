package main.de.mj.bb.core.commands;

import com.intellectualcrafters.plot.object.PlotPlayer;
import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class VorbauenFinishCommand implements CommandExecutor {
    private final CoreSpigot coreSpigot;

    public VorbauenFinishCommand(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setCommand(this, "finish");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                PlotPlayer plotPlayer = coreSpigot.getHookManager().getPlotAPI().wrapPlayer(player.getUniqueId());
                if (!coreSpigot.getHookManager().getPlotAPI().getPlot(player).getPlayersInPlot().contains(plotPlayer)) {
                    List<String> yeslore = new ArrayList<>();
                    yeslore.add("§aJa ich bin mir sicher");
                    ItemStack yes = new ItemStack(Material.STAINED_CLAY, 1, (short) 5);
                    ItemMeta yesmeta = yes.getItemMeta();
                    yesmeta.setDisplayName("§aYES");
                    yesmeta.setLore(yeslore);
                    yes.setItemMeta(yesmeta);

                    List<String> noLore = new ArrayList<>();
                    noLore.add("§cNein doch noch nicht");
                    ItemStack no = new ItemStack(Material.STAINED_CLAY, 1, (short) 14);
                    ItemMeta nometa = no.getItemMeta();
                    nometa.setDisplayName("§cNO");
                    nometa.setLore(noLore);
                    no.setItemMeta(nometa);


                    Inventory inventory = Bukkit.createInventory(null, 9, "§6§lFinish");
                    inventory.setItem(3, yes);
                    inventory.setItem(5, no);
                    player.openInventory(inventory);
                } else
                    player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "§7Du stehst nicht auf deinem Plot!");

            } else
                player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "§cBitte nutze: </finish>");
        } else
            sender.sendMessage(coreSpigot.getModuleManager().getData().getOnlyPlayer());
        return false;
    }
}
