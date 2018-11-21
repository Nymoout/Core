/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package main.de.mj.bb.core.listener;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.jetbrains.annotations.NotNull;

public class CancelListener implements Listener {

    public CancelListener(@NotNull CoreSpigot coreSpigot) {
        coreSpigot.setListener(this);
    }

    @EventHandler
    public void Itemmove(InventoryMoveItemEvent moveItemEvent) {
        moveItemEvent.setCancelled(true);
    }

    @EventHandler
    public void drop(PlayerDropItemEvent dropItemEvent) {
        dropItemEvent.setCancelled(true);
    }

    @EventHandler
    public void drop(PlayerFishEvent fishEvent) {
        fishEvent.setCancelled(false);
    }

    @EventHandler
    public void pickup(PlayerPickupItemEvent pickupItemEvent) {
        pickupItemEvent.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void damage(EntityDamageEvent damageEvent) {
        damageEvent.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void damageByBlock(EntityDamageByBlockEvent entityDamageByBlockEvent) {
        entityDamageByBlockEvent.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void hangingBreakEvent(HangingBreakEvent hangingBreakEvent) {
        hangingBreakEvent.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void hangingBreakEventEnity(HangingBreakByEntityEvent breakByEntityEvent) {
        breakByEntityEvent.setCancelled(true);
    }

    @EventHandler
    public void food(FoodLevelChangeEvent levelChangeEvent) {
        levelChangeEvent.setCancelled(true);
    }

    @EventHandler
    public void spawn(CreatureSpawnEvent spawnEvent) {
        if (spawnEvent.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL || spawnEvent.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CHUNK_GEN) {
            spawnEvent.setCancelled(true);
        }
    }
}
