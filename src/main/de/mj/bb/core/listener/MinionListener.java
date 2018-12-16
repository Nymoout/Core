/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package main.de.mj.bb.core.listener;

import main.de.mj.bb.core.CoreSpigot;
import main.de.mj.bb.core.utils.Data;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class MinionListener implements Listener {

    private static Map<Player, ArmorStand> minion = new HashMap<>();
    private final CoreSpigot coreSpigot;
    private String prefix = new Data().getPrefix();

    public MinionListener(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setListener(this);
    }

    @EventHandler
    public void createMinion(PlayerInteractEvent interactEvent) {
        if (interactEvent.getItem() == null) return;
        if (interactEvent.getItem().getType() == null) return;
        if (interactEvent.getItem().getType().equals(Material.AIR)) return;
        if (!(interactEvent.getAction().equals(Action.RIGHT_CLICK_AIR) || interactEvent.getAction().equals(Action.RIGHT_CLICK_BLOCK) || interactEvent.getAction().equals(Action.LEFT_CLICK_AIR) || interactEvent.getAction().equals(Action.LEFT_CLICK_BLOCK)))
            return;
        Action action = interactEvent.getAction();
        Player player = interactEvent.getPlayer();
        if (interactEvent.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§8\u00BB§3§lDein minion§8\u00AB")) {
            if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) {
                player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, 1);
                if (minion.containsKey(player)) {
                    rmMini(player);
                } else {
                    ArmorStand as = (ArmorStand) player.getLocation().getWorld().spawnEntity(player.getLocation(),
                            EntityType.ARMOR_STAND);
                    as.setSmall(true);
                    as.setGravity(false);
                    as.setArms(true);
                    as.setBasePlate(false);
                    ItemStack helm = coreSpigot.getModuleManager().getItemCreator().createItemWithPlayer(player.getUniqueId().toString(), 1, "");
                    //if (coreSpigot.getNickManager().isDisguised(player)) {
                    //    hm.setOwner(coreSpigot.getNickManager().getPlayerName().get(player));
                    //} else hm.setOwner(player.getName());
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
                    minion.put(player, as);
                    player.sendMessage(
                            prefix + "Dein §3§lMinion §7wurde ausger\u00FCstet!");
                }
            }
        }
    }

    private ItemMeta setColor(ItemStack itemStack, Player player) {
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
        //if (coreSpigot.getNickManager().isDisguised(player)) return null;
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
    public void teleportMini(PlayerMoveEvent moveEvent) {
        Player player = moveEvent.getPlayer();
        if (minion.containsKey(player)) {
            ArmorStand as = minion.get(player);
            double nX;
            double nZ;
            float yaw = player.getLocation().getYaw() + 90;
            if (yaw < 0)
                yaw += 360;
            nX = Math.cos(Math.toRadians(yaw));
            nZ = Math.sin(Math.toRadians(yaw));
            as.teleport(new Location(player.getLocation().getWorld(), player.getLocation().getX() - nX, player.getLocation().getY(),
                    player.getLocation().getZ() - nZ, player.getLocation().getYaw(), player.getLocation().getPitch()));
            /*
            ItemStack boots = as.getBoots();
            boots.setItemMeta(setColor(boots, player));
            as.setBoots(boots);
            ItemStack leggins = as.getLeggings();
            leggins.setItemMeta(setColor(leggins, player));
            as.setLeggings(leggins);
            ItemStack chest = as.getChestplate();
            chest.setItemMeta(setColor(chest, player));
            as.setChestplate(chest);
            ItemStack helm = coreSpigot.getModuleManager().getItemCreator().createItemWithPlayer(player.getUniqueId().toString(), 1, "");
            as.setHelmet(helm);
            */
            //if (coreSpigot.getNickManager().isDisguised(player)) {
            //    hm.setOwner(coreSpigot.getNickManager().getPlayerName().get(player));
            //    as.setHelmet(helm);
            //}
        }
    }

    void rmMini(Player player) {
        if (minion.containsKey(player)) {
            ArmorStand armorStand = minion.get(player);
            armorStand.remove();
            minion.remove(player);
            player.sendMessage(
                    prefix + "Dein §3§lMinion §7wurde entfernt!");
        }
    }

    @EventHandler
    public void onASManipulate(PlayerArmorStandManipulateEvent armorStandManipulateEvent) {
        armorStandManipulateEvent.setCancelled(true);
    }
}