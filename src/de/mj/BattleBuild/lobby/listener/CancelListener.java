package de.mj.BattleBuild.lobby.listener;

import de.mj.BattleBuild.lobby.Lobby;
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

    private Lobby lobby;

    public CancelListener(Lobby lobby) {
        this.lobby = lobby;
        lobby.setListener(this);
    }

    @EventHandler
    public void Itemmove(InventoryMoveItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void drop(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void drop(PlayerFishEvent e) {
        e.setCancelled(false);
    }

    @EventHandler
    public void pickup(PlayerPickupItemEvent e) {
            e.setCancelled(true);
    }

    @EventHandler
    public void damage(EntityDamageEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void food(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void spawn(CreatureSpawnEvent e) {
        if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL || e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CHUNK_GEN) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void wetter(WeatherChangeEvent e) {
        e.setCancelled(true);
    }
}
