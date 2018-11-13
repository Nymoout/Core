package main.de.mj.bb.core.commands;

import main.de.mj.bb.core.CoreSpigot;
import main.de.mj.bb.core.utils.Data;
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

public class ChestCommand implements CommandExecutor {

    private final CoreSpigot coreSpigot;
    private final Data data;

    public ChestCommand(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        this.data = coreSpigot.getModuleManager().getData();
        coreSpigot.setCommand(this, "chest");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player user = (Player) sender;
            if (user.hasPermission("vorbau.chest") || user.hasPermission("vorbau.*") || user.isOp()) {
                ItemStack OAK = new ItemStack(Material.CHEST);
                ItemMeta OAKMeta = OAK.getItemMeta();
                OAKMeta.setDisplayName("OAK");
                OAK.setItemMeta(OAKMeta);
                ItemStack Birch = new ItemStack(Material.CHEST);
                ItemMeta birchMeta = Birch.getItemMeta();
                birchMeta.setDisplayName("BIRCH");
                Birch.setItemMeta(birchMeta);
                ItemStack spruce = new ItemStack(Material.CHEST);
                ItemMeta spruceMeta = spruce.getItemMeta();
                spruceMeta.setDisplayName("SPRUCE");
                spruce.setItemMeta(spruceMeta);
                ItemStack jungle = new ItemStack(Material.CHEST);
                ItemMeta jungleeMeta = jungle.getItemMeta();
                jungleeMeta.setDisplayName("JUNGLEE");
                jungle.setItemMeta(jungleeMeta);
                ItemStack akazia = new ItemStack(Material.CHEST);
                ItemMeta akaziaMeta = akazia.getItemMeta();
                akaziaMeta.setDisplayName("AKAZIA");
                akazia.setItemMeta(akaziaMeta);
                ItemStack darkoak = new ItemStack(Material.CHEST);
                ItemMeta dMeta = darkoak.getItemMeta();
                dMeta.setDisplayName("DARK_OAK");
                darkoak.setItemMeta(dMeta);
                if (args.length == 1 && args[0].equalsIgnoreCase("OAK")) {
                    user.getInventory().addItem(OAK);
                } else if (args.length == 1 && args[0].equalsIgnoreCase("birch")) {
                    user.getInventory().addItem(Birch);
                } else if (args.length == 1 && args[0].equalsIgnoreCase("spruce")) {
                    user.getInventory().addItem(spruce);
                } else if (args.length == 1 && args[0].equalsIgnoreCase("jungle")) {
                    user.getInventory().addItem(jungle);
                } else if (args.length == 1 && args[0].equalsIgnoreCase("akazia")) {
                    user.getInventory().addItem(akazia);
                } else if (args.length == 1 && args[0].equalsIgnoreCase("darkoak")) {
                    user.getInventory().addItem(darkoak);
                } else if (args.length == 1 && args[0].equals("all")) {
                    ItemStack chestall = new ItemStack(Material.CHEST);
                    ItemMeta Metaall = chestall.getItemMeta();
                    Metaall.setDisplayName("§cWoods All");

                    Inventory inventar = Bukkit.createInventory(null, 9, "§cWoods All");
                    inventar.setItem(0, OAK);
                    inventar.setItem(1, Birch);
                    inventar.setItem(2, spruce);
                    inventar.setItem(3, jungle);
                    inventar.setItem(4, akazia);
                    inventar.setItem(5, darkoak);
                    user.openInventory(inventar);
                } else if (args.length == 1 && args[0].equals("clear")) {
                    Inventory inv = Bukkit.createInventory(user, 9, "Clear");
                    user.openInventory(inv);
                    user.sendMessage(data.getPrefix() + "§aDie Chest's wurden erfolgreich gecleart.");
                } else if (args.length == 1 && args[0].equals("list")) {
                    user.sendMessage("§7Benutze;");
                    user.sendMessage("§cOAK,Birch,DarkOak,Akazia,Jungle,Spruce");

                } else if (args.length >= 1 && args[0].equalsIgnoreCase("help")) {
                    if (args.length == 2 && args[1].equalsIgnoreCase("chest")) {
                        user.sendMessage("§7List of chest Commands:");
                        user.sendMessage("§7/chest");
                        user.sendMessage("§7/chest list");
                        user.sendMessage("§7/chest all");
                        user.sendMessage("§7/chest help");
                        user.sendMessage("§7/chest help <chest/Permissions");
                    } else if (args.length == 2 && args[1].equalsIgnoreCase("Permissions")) {
                        if (user.hasPermission("vorbau.getpermlist") || user.isOp()) {
                            user.sendMessage("§7=====(§cPermissions§7)=====");
                            user.sendMessage("§7GrundPerm: §bvorbau");
                            user.sendMessage("§7OpPerm: §avorbau.*");
                            user.sendMessage("§7GustPerm: §avorbau.chest");
                            user.sendMessage("§7PermissionsPerm: §avorbau.getpermlist");
                        }
                    } else {
                        user.sendMessage("§7=====§aHelp§7=====");
                        user.sendMessage("§7/chest list » Zeigt dir eine Liste von Verfügbaren Eingaben an.");
                        user.sendMessage("§7/chest help » Zeigt dir diese Liste an!");
                        user.sendMessage("§7/chest help <Permissions> » Zeigt dir eine Liste von Permissions an.");
                    }
                } else {
                    user.sendMessage(data.getPrefix() + "§cBenutze: </chest> <Chest-Name>");
                    user.sendMessage(data.getPrefix() + "Du kannst auch </chest all> benutzen, um eine Liste mit den verfügbaren Eingaben zu erhalten!");
                }
            } else
                user.sendMessage(data.getNoPerm());
        } else sender.sendMessage(data.getOnlyPlayer());
        return false;
    }
}
