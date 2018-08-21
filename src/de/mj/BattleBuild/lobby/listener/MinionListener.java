/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

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
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
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
    private final Lobby lobby;
    String prefix = new Data().getPrefix();

    public MinionListener(Lobby lobby) {
        this.lobby = lobby;
        lobby.setListener(this);
    }

    @EventHandler
    public void createMinion(PlayerInteractEvent interactEvent) {
        Action action = interactEvent.getAction();
        Player player = interactEvent.getPlayer();
        try {
            if (interactEvent.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§8\u00BB§3§lDein Minion§8\u00AB")) {
                if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) {
                    if (Minion.containsKey(player)) {
                        rmMini(player);
                    } else {
                        ArmorStand as = (ArmorStand) player.getLocation().getWorld().spawnEntity(player.getLocation(),
                                EntityType.ARMOR_STAND);
                        as.setSmall(true);
                        as.setGravity(false);
                        as.setArms(true);
                        as.setBasePlate(false);
                        ItemStack helm = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                        SkullMeta hm = (SkullMeta) helm.getItemMeta();
                        if (VIPHide.instance.isDisguised(player)) {
                            hm.setOwner(VIPHide.instance.getName(player));
                        } else hm.setOwner(player.getName());
                        helm.setItemMeta(hm);
                        as.setHelmet(helm);
                        ItemStack Boots = new ItemStack(Material.LEATHER_BOOTS);
                        Boots.setItemMeta(setColor(Boots, player));
                        as.setBoots(Boots);

                        ItemStack Leggins = new ItemStack(Material.LEATHER_LEGGINGS);
                        Leggins.setItemMeta(setColor(Leggins, player));
                        as.setLeggings(Leggins);

                        ItemStack Chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
                        Chestplate.setItemMeta(setColor(Chestplate, player));
                        as.setChestplate(Chestplate);
                        Minion.put(player, as);
                        player.sendMessage(
                                prefix + "§9§lM§e§li§9§ln§e§li§9§lo§e§ln §7wurde ausger\u00FCstet.");
                    }
                }
            }
        } catch (NullPointerException ex) {
        }
    }

    private ItemMeta setColor(ItemStack itemStack, Player player) {
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
        if (lobby.getHookManager().getVipHide().isDisguised(player)) return null;
        if (player.hasPermission("group.administrator")) {
            leatherArmorMeta.setColor(Color.fromRGB(170, 2, 0));
            return leatherArmorMeta;
        } else if (player.hasPermission("group.srmoderator")) {
            leatherArmorMeta.setColor(Color.fromRGB(254, 81, 83));
            return leatherArmorMeta;
        } else if (player.hasPermission("group.moderator")) {
            leatherArmorMeta.setColor(Color.fromRGB(254, 81, 83));
            return leatherArmorMeta;
        } else if (player.hasPermission("group.srdeveloper")) {
            leatherArmorMeta.setColor(Color.AQUA);
            return leatherArmorMeta;
        } else if (player.hasPermission("group.developer")) {
            leatherArmorMeta.setColor(Color.AQUA);
            return leatherArmorMeta;
        } else if (player.hasPermission("group.srbuilder")) {
            leatherArmorMeta.setColor(Color.GREEN);
            return leatherArmorMeta;
        } else if (player.hasPermission("group.builder")) {
            leatherArmorMeta.setColor(Color.GREEN);
            return leatherArmorMeta;
        } else if (player.hasPermission("group.srsupporter")) {
            leatherArmorMeta.setColor(Color.BLUE);
            return leatherArmorMeta;
        } else if (player.hasPermission("group.supporter")) {
            leatherArmorMeta.setColor(Color.BLUE);
            return leatherArmorMeta;
        } else if (player.hasPermission("group.youtuber")) {
            leatherArmorMeta.setColor(Color.PURPLE);
            return leatherArmorMeta;
        } else if (player.hasPermission("group.vip+")) {
            leatherArmorMeta.setColor(Color.YELLOW);
            return leatherArmorMeta;
        }
        return null;
    }

    @EventHandler
    public void TpMini(PlayerMoveEvent moveEvent) {
        Player player = moveEvent.getPlayer();
        if (Minion.containsKey(player)) {
            ArmorStand as = Minion.get(player);
            double nX;
            double nZ;
            float yaw = player.getLocation().getYaw() + 90;
            if (yaw < 0)
                yaw += 360;
            nX = Math.cos(Math.toRadians(yaw));
            nZ = Math.sin(Math.toRadians(yaw));
            as.teleport(new Location(player.getLocation().getWorld(), player.getLocation().getX() - nX, player.getLocation().getY(),
                    player.getLocation().getZ() - nZ, player.getLocation().getYaw(), player.getLocation().getPitch()));
            ItemStack boots = as.getBoots();
            boots.setItemMeta(setColor(boots, player));
            as.setBoots(boots);
            ItemStack leggins = as.getLeggings();
            leggins.setItemMeta(setColor(leggins, player));
            as.setLeggings(leggins);
            ItemStack chest = as.getChestplate();
            chest.setItemMeta(setColor(chest, player));
            as.setChestplate(chest);
            ItemStack helm = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta hm = (SkullMeta) helm.getItemMeta();
            if (lobby.getHookManager().getVipHide().isDisguised(player)) {
                hm.setOwner(lobby.getHookManager().getVipHide().getName(player));
                as.setHelmet(helm);
            }
        }
    }

    void rmMini(Player player) {
        try {
            ArmorStand armorStand = Minion.get(player);
            armorStand.remove();
            Minion.remove(player);
        } catch (NullPointerException localNullPointerException) {
        }
    }

    @EventHandler
    public void onASManipulate(PlayerArmorStandManipulateEvent armorStandManipulateEvent) {
        armorStandManipulateEvent.setCancelled(true);
    }
}
