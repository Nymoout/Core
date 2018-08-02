package de.mj.BattleBuild.lobby.listener;

import de.mj.BattleBuild.lobby.Lobby;
import de.mj.BattleBuild.lobby.utils.Data;
import me.BukkitPVP.VIPHide.VIPHide;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class MinionListener implements Listener {

    public static HashMap<Player, ArmorStand> Minion = new HashMap<Player, ArmorStand>();
    public static HashMap<Player, Inventory> minioninv = new HashMap<Player, Inventory>();
    public static ArrayList<Player> ininv = new ArrayList<Player>();
    String prefix = new Data().getPrefix();

    private final Lobby lobby;

    public MinionListener(Lobby lobby) {
        this.lobby = lobby;
        lobby.setListener(this);
    }

    @EventHandler
    public void createMinion(PlayerInteractEvent e) {
        Action action = e.getAction();
        Player p = e.getPlayer();
        try {
            if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§8\u00BB§3§lDein Minion§8\u00AB")) {
                if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) {
                    if (Minion.containsKey(p)) {
                        rmMini(p);
                    } else {
                        ArmorStand as = (ArmorStand) p.getLocation().getWorld().spawnEntity(p.getLocation(),
                                EntityType.ARMOR_STAND);
                        as.setSmall(true);
                        as.setGravity(false);
                        as.setArms(true);
                        as.setBasePlate(false);
                        ItemStack helm = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                        SkullMeta hm = (SkullMeta) helm.getItemMeta();
                        if (VIPHide.instance.isDisguised(p)) {
                            hm.setOwner(VIPHide.instance.getName(p));
                        } else hm.setOwner(p.getName());
                        helm.setItemMeta(hm);
                        as.setHelmet(helm);
                        ItemStack Boots = new ItemStack(Material.LEATHER_BOOTS);
                        Boots.setItemMeta(setColor(Boots, p));
                        as.setBoots(Boots);

                        ItemStack Leggins = new ItemStack(Material.LEATHER_LEGGINGS);
                        Leggins.setItemMeta(setColor(Leggins, p));
                        as.setLeggings(Leggins);

                        ItemStack Chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
                        Chestplate.setItemMeta(setColor(Chestplate, p));
                        as.setChestplate(Chestplate);
                        Minion.put(p, as);
                        p.sendMessage(
                                prefix + "§9§lM§e§li§9§ln§e§li§9§lo§e§ln §7wurde ausger\u00FCstet.");
                    }
                }
            }
        } catch (NullPointerException ex) {}
    }

    public ItemMeta setColor (ItemStack is, Player p) {
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta)is.getItemMeta();
        if (VIPHide.instance.isDisguised(p)) return null;
        if (p.hasPermission("group.administrator")) {
            leatherArmorMeta.setColor(Color.fromRGB(170, 2, 0));
            return leatherArmorMeta;
        } else if (p.hasPermission("group.srmoderator")) {
            leatherArmorMeta.setColor(Color.fromRGB(254, 81, 83));
            return leatherArmorMeta;
        } else if (p.hasPermission("group.moderator")) {
            leatherArmorMeta.setColor(Color.fromRGB(254, 81, 83));
            return leatherArmorMeta;
        } else if (p.hasPermission("group.srdeveloper")) {
            leatherArmorMeta.setColor(Color.AQUA);
            return leatherArmorMeta;
        } else if (p.hasPermission("group.developer")) {
            leatherArmorMeta.setColor(Color.AQUA);
            return leatherArmorMeta;
        } else if (p.hasPermission("group.srbuilder")) {
            leatherArmorMeta.setColor(Color.GREEN);
            return leatherArmorMeta;
        } else if (p.hasPermission("group.builder")) {
            leatherArmorMeta.setColor(Color.GREEN);
            return leatherArmorMeta;
        } else if (p.hasPermission("group.srsupporter")) {
            leatherArmorMeta.setColor(Color.BLUE);
            return leatherArmorMeta;
        } else if (p.hasPermission("group.supporter")) {
            leatherArmorMeta.setColor(Color.BLUE);
            return leatherArmorMeta;
        } else if (p.hasPermission("group.youtuber")) {
            leatherArmorMeta.setColor(Color.PURPLE);
            return leatherArmorMeta;
        } else if (p.hasPermission("group.vip+")) {
            leatherArmorMeta.setColor(Color.YELLOW);
            return leatherArmorMeta;
        }
        return null;
    }

    @EventHandler
    public void TpMini(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (Minion.containsKey(p)) {
            ArmorStand as = Minion.get(p);
            double nX;
            double nZ;
            float yaw = p.getLocation().getYaw() + 90;
            if (yaw < 0)
                yaw += 360;
            nX = Math.cos(Math.toRadians(yaw));
            nZ = Math.sin(Math.toRadians(yaw));
            as.teleport(new Location(p.getLocation().getWorld(), p.getLocation().getX() - nX, p.getLocation().getY(),
                    p.getLocation().getZ() - nZ, p.getLocation().getYaw(), p.getLocation().getPitch()));
            ItemStack boots = as.getBoots();
            boots.setItemMeta(setColor(boots, p));
            as.setBoots(boots);
            ItemStack leggins = as.getLeggings();
            leggins.setItemMeta(setColor(leggins, p));
            as.setLeggings(leggins);
            ItemStack chest = as.getChestplate();
            chest.setItemMeta(setColor(chest, p));
            as.setChestplate(chest);
            ItemStack helm = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta hm = (SkullMeta) helm.getItemMeta();
            if (VIPHide.instance.isDisguised(p)) {
                hm.setOwner(VIPHide.instance.getName(p));
                as.setHelmet(helm);
            }
        }
    }

    public void rmMini(Player p) {
        try {
            ArmorStand as = Minion.get(p);
            as.remove();
            Minion.remove(p);
        } catch (NullPointerException localNullPointerException) {}
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        try {
            if (e.getInventory().getItem(0).equals(new ItemStack(Material.SKULL_ITEM))) {
                Inventory inv = e.getInventory();
                ArmorStand as = Minion.get(p);
                as.setHelmet(inv.getItem(0));
                as.setChestplate(inv.getItem(1));
                as.setBoots(inv.getItem(2));
                as.setItemInHand(inv.getItem(3));
                minioninv.remove(p);
                minioninv.put(p, inv);
            }
        } catch (NullPointerException ex) {

        }
    }
    @EventHandler
    public void atASInteract(PlayerInteractAtEntityEvent e) {
        if (e.getRightClicked().equals(EntityType.ARMOR_STAND)) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onASInteract(PlayerInteractEntityEvent e) {
        if (e.getRightClicked().equals(EntityType.ARMOR_STAND)) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onASManipulate(PlayerArmorStandManipulateEvent e) {
        e.setCancelled(true);
    }
}
