/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.core.listener;

import de.mj.BattleBuild.core.Core;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class CancelListener implements Listener {

    private Core core;

    public CancelListener(Core core) {
        this.core = core;
        core.setListener(this);
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

    @EventHandler
    public void damage(EntityDamageEvent damageEvent) {
        damageEvent.setCancelled(true);
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

    @EventHandler
    public void wetter(WeatherChangeEvent weatherChangeEvent) {
        weatherChangeEvent.setCancelled(true);
    }
}
