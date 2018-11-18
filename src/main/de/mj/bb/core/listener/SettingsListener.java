/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package main.de.mj.bb.core.listener;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import main.de.mj.bb.core.CoreSpigot;
import main.de.mj.bb.core.managers.ModuleManager;
import main.de.mj.bb.core.utils.PlayerLevel;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.WeatherType;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SettingsListener implements Listener {

    private Set<Player> silentState = new HashSet<>();
    private Set<Player> rideState = new HashSet<>();
    private Map<Player, Short> design = new HashMap<>();
    private Map<Player, String> color = new HashMap<>();
    private Set<Player> jumpPads = new HashSet<>();
    private Set<Player> doubleJump = new HashSet<>();
    private Set<Player> waterJump = new HashSet<>();
    private Map<Player, PlayerLevel> playerLevel = new HashMap<>();

    private Set<Player> scoreFriends = new HashSet<>();
    private Set<Player> scoreRank = new HashSet<>();
    private Set<Player> scoreServer = new HashSet<>();
    private Set<Player> scoreClan = new HashSet<>();
    private Set<Player> scoreCoins = new HashSet<>();
    private Set<Player> scoreTime = new HashSet<>();
    private Set<Player> realTime = new HashSet<>();
    private Set<Player> day = new HashSet<>();
    private Set<Player> weather = new HashSet<>();

    private Map<Player, Inventory> invent1 = new HashMap<>();
    private Map<Player, Inventory> invent2 = new HashMap<>();
    private Map<Player, Inventory> score1 = new HashMap<>();

    private Map<String, net.minecraft.server.v1_8_R3.ItemStack> skulls = new HashMap<>();

    private final CoreSpigot coreSpigot;

    public SettingsListener(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setListener(this);
    }

    public void ItemColToString(Player p) {
        if (design.containsKey(p)) {
            if (design.get(p) == 0) {
                color.put(p, "f");
            } else if (design.get(p) == 1) {
                color.put(p, "6");
            } else if (design.get(p) == 2) {
                color.put(p, "5");
            } else if (design.get(p) == 3) {
                color.put(p, "b");
            } else if (design.get(p) == 4) {
                color.put(p, "e");
            } else if (design.get(p) == 5) {
                color.put(p, "a");
            } else if (design.get(p) == 6) {
                color.put(p, "d");
            } else if (design.get(p) == 7) {
                color.put(p, "8");
            } else if (design.get(p) == 8) {
                color.put(p, "7");
            } else if (design.get(p) == 9) {
                color.put(p, "3");
            } else if (design.get(p) == 10) {
                color.put(p, "5");
            } else if (design.get(p) == 11) {
                color.put(p, "9");
            } else if (design.get(p) == 12) {
                color.put(p, "f");
            } else if (design.get(p) == 13) {
                color.put(p, "2");
            } else if (design.get(p) == 14) {
                color.put(p, "c");
            } else if (design.get(p) == 15) {
                color.put(p, "0");
            }
        }
    }

    @EventHandler
    public synchronized void onInteract(PlayerInteractEvent interactEvent) {
        if (interactEvent.getItem() == null) return;
        if (interactEvent.getItem().getType() == null) return;
        if (interactEvent.getItem().getType().equals(Material.AIR)) return;
        if (!(interactEvent.getAction().equals(Action.RIGHT_CLICK_AIR) || interactEvent.getAction().equals(Action.RIGHT_CLICK_BLOCK) || interactEvent.getAction().equals(Action.LEFT_CLICK_AIR) || interactEvent.getAction().equals(Action.LEFT_CLICK_BLOCK)))
            return;
        Player player = interactEvent.getPlayer();
        if (player.getItemInHand().getType().equals(Material.REDSTONE_COMPARATOR) && player.getItemInHand().getItemMeta().getDisplayName().equals("§8\u00BB§c§lEinstellungen§8\u00AB")) {
            if (invent1.containsKey(player)) {
                player.openInventory(invent1.get(player));
                player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, 1);
            } else {
                setInv(player);
                player.openInventory(invent1.get(player));
                player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, 1);
            }
        }
    }

    private void setInv(Player player) {
        ModuleManager lobby = this.coreSpigot.getModuleManager();
        invent1.remove(player);
        Inventory inv = Bukkit.createInventory(null, 36, "§8\u00BB§c§lEinstellungen§8\u00AB");
        Inventory inv2 = Bukkit.createInventory(null, 36, "§8\u00BB§c§lEinstellungen§8\u00AB");

        int size = 35;

        while (size >= 0) {
            inv.setItem(size, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, design.get(player), 1));
            inv2.setItem(size, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, design.get(player), 1));
            size--;
        }

        ArrayList<String> designLore = new ArrayList<>();
        designLore.add("§7Ändere das Design der Inventare und des Scoreboards in der Lobby");
        inv.setItem(10, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS, 0, 1, "§9§lDesign", designLore));
        if (design.containsKey(player)) {
            inv.setItem(19, lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, getDyeColor(design.get(player)), 1));
        } else {
            inv.setItem(19, lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 0, 1));
        }

        ArrayList<String> hideLore = new ArrayList<>();
        hideLore.add("§7Zeige nur bestimmte Spielergruppen an");
        inv.setItem(22, lobby.getItemCreator().createItemWithMaterial(Material.BLAZE_ROD, 0, 1, "§6§lVerstecke Spieler", hideLore));

        ArrayList<String> ridelore = new ArrayList<>();
        ridelore.add("§7Lasse Spieler auf dir reiten");
        inv.setItem(14, lobby.getItemCreator().createItemWithMaterial(Material.TRIPWIRE_HOOK, 0, 1, "§3§lRide on me", ridelore));
        if (rideState.contains(player)) {
            inv.setItem(23, lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 10, 1, "§aAktiviert ✔"));
        } else {
            inv.setItem(23, lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 8, 1, "§cDeaktiviert ✘"));
        }

        ArrayList<String> scoreLore = new ArrayList<>();
        scoreLore.add("§7Ändere das Aussehen deines Scoreboards");
        inv.setItem(13, lobby.getItemCreator().createItemWithMaterial(Material.SIGN, 0, 1, "§5§lScoreboard", scoreLore));

        ArrayList<String> realTimeLore = new ArrayList<>();
        realTimeLore.add("§7Wechsle zwischen Echtzeit, Tag oder Nacht in der Lobby");
        inv.setItem(12, lobby.getItemCreator().createItemWithMaterial(Material.WATCH, 0, 1, "§6§lEcht§9§lzeit", realTimeLore));
        if (realTime.contains(player)) {
            inv.setItem(21,
                    lobby.getItemCreator().createItemWithMaterial(Material.DAYLIGHT_DETECTOR, 0, 1, "§6Echt§9zeit"));
        } else if (day.contains(player)) {
            inv.setItem(21, lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 11, 1, "§6Tag ✷"));
        } else {
            inv.setItem(21, lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 6, 1, "§9Nacht ✩"));
        }

        ArrayList<String> weatherLore = new ArrayList<>();
        weatherLore.add("§7Wechsle zwichen Sonne oder Regen/Schnee");
        inv.setItem(11, lobby.getItemCreator().createItemWithMaterial(Material.WATER_BUCKET, 0, 1, "§b§lWetter", weatherLore));
        if (weather.contains(player)) {
            inv.setItem(20, lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 11, 1, "§6Sonne ✷"));
        } else {
            inv.setItem(20, lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 4, 1, "§9Regen/Schnee ☔"));
        }

        ArrayList<String> jumpPadLore = new ArrayList<>();
        jumpPadLore.add("§7Schalte JumpPads an oder aus");
        inv.setItem(15, lobby.getItemCreator().createItemWithMaterial(Material.GOLD_PLATE, 0, 1, "§5§lJumpPad", jumpPadLore));
        if (jumpPads.contains(player)) {
            inv.setItem(24, lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 10, 1, "§aAktiviert ✔"));
        } else {
            inv.setItem(24, lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 8, 1, "§cDeaktiviert ✘"));
        }

        ArrayList<String> doubleJumpLore = new ArrayList<>();
        doubleJumpLore.add("§7Schalte DoubleJump an oder aus");
        inv.setItem(16, lobby.getItemCreator().createItemWithMaterial(Material.FEATHER, 0, 1, "§f§lDoubleJump", doubleJumpLore));
        if (doubleJump.contains(player)) {
            inv.setItem(25, lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 10, 1, "§aAktiviert ✔"));
        } else {
            inv.setItem(25, lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 8, 1, "§cDeaktiviert ✘"));
        }

        inv.setItem(35, CraftItemStack.asBukkitCopy(skulls.get("next")));

        invent1.put(player, inv);

        ItemStack site = new ItemStack(Material.PAPER);
        ItemMeta siteMeta = site.getItemMeta();
        siteMeta.setDisplayName("§6Seite §a1§6|§a2");
        site.setItemMeta(siteMeta);
        inv.setItem(31, site);

        //Inv2

        ArrayList<String> liquidBounceLore = new ArrayList<>();
        inv2.setItem(12,
                lobby.getItemCreator().createItemWithMaterial(Material.LAVA_BUCKET, 0, 1, "§2§lJump on Liquid", liquidBounceLore));
        if (waterJump.contains(player)) {
            inv2.setItem(21, lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 10, 1, "§aAktiviert ✔"));
        } else {
            inv2.setItem(21, lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 8, 1, "§cDeaktiviert ✗"));
        }

        ArrayList<String> levelLore = new ArrayList<>();
        levelLore.add("§7Lass deine Levelleiste entweder Jahr, Lobby, oder Slot anzeigen!");
        inv2.setItem(14, lobby.getItemCreator().createItemWithMaterial(Material.EXP_BOTTLE, 0, 1, "§a§lLevelleiste", levelLore));
        if (playerLevel.containsKey(player)) {
            if (playerLevel.get(player).equals(PlayerLevel.LOBBY))
                inv2.setItem(23, lobby.getItemCreator().createItemWithMaterial(Material.NETHER_STAR, 0, 1, "§fLobby"));
            else if (playerLevel.get(player).equals(PlayerLevel.SCROLL))
                inv2.setItem(23, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§aSlot"));
            else if (playerLevel.get(player).equals(PlayerLevel.YEAR))
                inv2.setItem(23, lobby.getItemCreator().createItemWithMaterial(Material.FIREWORK, 0, 1, "§cJahr"));
        }

        inv2.setItem(27, CraftItemStack.asBukkitCopy(skulls.get("prev")));

        ItemStack site2 = new ItemStack(Material.PAPER);
        ItemMeta site2Meta = site2.getItemMeta();
        site2Meta.setDisplayName("§6Seite §a2§6|§a2");
        site2.setItemMeta(site2Meta);
        inv2.setItem(31, site2);

        invent2.put(player, inv2);
    }

    @EventHandler
    public void settingsMenue(InventoryClickEvent clickEvent) {
        if (clickEvent.getClickedInventory() == null) return;
        if (clickEvent.getClickedInventory().getType() == null) return;
        if (clickEvent.getCurrentItem() == null) return;
        if (clickEvent.getCurrentItem().getType() == null) return;
        if (clickEvent.getCurrentItem().getType().equals(Material.AIR)) return;
        if (clickEvent.getCurrentItem().getItemMeta().getDisplayName() == null) return;
        ModuleManager lobby = this.coreSpigot.getModuleManager();
        Player player = (Player) clickEvent.getWhoClicked();
        lobby.getScoreboardManager().setScoreboard(player);
        player.playSound(player.getLocation(), Sound.SHEEP_WALK, 1, 1);
        if (clickEvent.getCursor().getType().equals(Material.BLAZE_ROD)
                || clickEvent.getCurrentItem().getType().equals(Material.BLAZE_ROD) && !clickEvent.getCurrentItem().getItemMeta().getDisplayName().contains("Fun")) {
            player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, 1);
            player.performCommand("hide");
        } else if (clickEvent.getCurrentItem().getType().equals(Material.STAINED_GLASS)) {
            player.openInventory(Design());
        } else if (clickEvent.getCurrentItem().getType().equals(Material.TRIPWIRE_HOOK)
                && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("§3§lRide on me")) {
            if (rideState.contains(player)) {
                rideState.remove(player);
                invent1.get(player).setItem(23, lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 8, 1, "§cDeaktiviert ✘"));
                lobby.getSettingsAPI().setRide(player, false);
                player.updateInventory();
            } else {
                rideState.add(player);
                invent1.get(player).setItem(23, lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 10, 1, "§aAktiviert ✔"));
                lobby.getSettingsAPI().setRide(player, true);
                player.updateInventory();
            }
        } else if (clickEvent.getCurrentItem().getType().equals(Material.WATCH)
                && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lEcht§9§lzeit")) {
            if (player.getOpenInventory().getItem(21).getItemMeta().getDisplayName().equalsIgnoreCase("§6Echt§9zeit")) {
                realTime.remove(player);
                day.add(player);
                invent1.get(player).setItem(21,
                        lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 11, 1, "§6Tag ✷"));
                lobby.getSettingsAPI().setRealTime(player, false, true);
                player.updateInventory();
            } else if (player.getOpenInventory().getItem(21).getItemMeta().getDisplayName().equalsIgnoreCase("§6Tag ✷")) {
                day.remove(player);
                invent1.get(player).setItem(21,
                        lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 6, 1, "§9Nacht ✩"));
                lobby.getSettingsAPI().setRealTime(player, false, false);
                player.updateInventory();
            } else if (player.getOpenInventory().getItem(21).getItemMeta().getDisplayName().equalsIgnoreCase("§9Nacht ✩")) {
                realTime.add(player);
                invent1.get(player).setItem(21,
                        lobby.getItemCreator().createItemWithMaterial(Material.DAYLIGHT_DETECTOR, 0, 1, "§6Echt§9zeit"));
                lobby.getSettingsAPI().setRealTime(player, true, false);
                player.updateInventory();
            }
        } else if (clickEvent.getCurrentItem().getType().equals(Material.SKULL_ITEM)
                && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("§6Nächste Seite ⇾")) {
            if (player.getOpenInventory().getItem(31).getItemMeta().getDisplayName().equalsIgnoreCase("§6Seite §a1§6|§a2"))
                player.openInventory(invent2.get(player));
            else
                player.openInventory(invent1.get(player));
        } else if (clickEvent.getCurrentItem().getType().equals(Material.SKULL_ITEM)
                && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("§6⇽ Vorherige Seite")) {
            if (player.getOpenInventory().getItem(31).getItemMeta().getDisplayName().equalsIgnoreCase("§6Seite §a2§6|§a2"))
                player.openInventory(invent1.get(player));
        } else if (clickEvent.getCurrentItem().getType().equals(Material.WATER_BUCKET)
                && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("§b§lWetter")) {
            if (weather.contains(player)) {
                weather.remove(player);
                invent1.get(player).setItem(20,
                        lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 4, 1, "§9Regen/Schnee ☔"));
                player.setPlayerWeather(WeatherType.DOWNFALL);
                lobby.getSettingsAPI().setWeather(player, false);
                player.updateInventory();
            } else {
                weather.add(player);
                invent1.get(player).setItem(20,
                        lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 11, 1, "§6Sonne ✷"));
                player.setPlayerWeather(WeatherType.CLEAR);
                lobby.getSettingsAPI().setWeather(player, true);
                player.updateInventory();
            }
        } else if (clickEvent.getCurrentItem().getType().equals(Material.LAVA_BUCKET)
                && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("§2§lJump on Liquid")) {
            if (waterJump.contains(player)) {
                waterJump.remove(player);
                invent2.get(player).setItem(21,
                        lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 8, 1, "§cDeaktiviert ✘"));
                lobby.getSettingsAPI().setWJUMP(player, false);
                player.updateInventory();
            } else {
                waterJump.add(player);
                invent2.get(player).setItem(21,
                        lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 10, 1, "§aAktiviert ✔"));
                lobby.getSettingsAPI().setWJUMP(player, true);
                player.updateInventory();
            }
        } else if (clickEvent.getCurrentItem().getType().equals(Material.FEATHER)
                && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("§f§lDoubleJump")) {
            if (doubleJump.contains(player)) {
                doubleJump.remove(player);
                invent1.get(player).setItem(25,
                        lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 8, 1, "§cDeaktiviert ✘"));
                lobby.getSettingsAPI().setDJUMP(player, false);
                player.updateInventory();
            } else {
                doubleJump.add(player);
                invent1.get(player).setItem(25,
                        lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 10, 1, "§aAktiviert ✔"));
                lobby.getSettingsAPI().setDJUMP(player, true);
                player.updateInventory();
            }
        } else if (clickEvent.getCurrentItem().getType().equals(Material.EXP_BOTTLE) && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("§a§lLevelleiste")) {
            if (playerLevel.get(player).equals(PlayerLevel.LOBBY)) {
                playerLevel.replace(player, PlayerLevel.YEAR);
                lobby.getSettingsAPI().setLEVEL(player, PlayerLevel.YEAR);
                invent2.get(player).setItem(23, lobby.getItemCreator().createItemWithMaterial(Material.FIREWORK, 0, 1, "§cJahr"));
                player.setLevel(Calendar.getInstance().get(Calendar.YEAR));
                player.updateInventory();
            } else if (playerLevel.get(player).equals(PlayerLevel.YEAR)) {
                playerLevel.replace(player, PlayerLevel.SCROLL);
                lobby.getSettingsAPI().setLEVEL(player, PlayerLevel.SCROLL);
                invent2.get(player).setItem(23, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§aSlot"));
                player.setLevel(1);
                player.updateInventory();
            } else if (playerLevel.get(player).equals(PlayerLevel.SCROLL)) {
                playerLevel.replace(player, PlayerLevel.LOBBY);
                lobby.getSettingsAPI().setLEVEL(player, PlayerLevel.LOBBY);
                invent2.get(player).setItem(23, lobby.getItemCreator().createItemWithMaterial(Material.NETHER_STAR, 0, 1, "§fLobby"));
                String[] serverName = player.getServer().getServerName().split("-");
                int server = Integer.parseInt(serverName[1]);
                player.setLevel(server);
                player.updateInventory();
            }
        } else if (clickEvent.getCurrentItem().getType().equals(Material.GOLD_PLATE)
                && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("§5§lJumpPad")) {
            if (jumpPads.contains(player)) {
                jumpPads.remove(player);
                invent1.get(player).setItem(24,
                        lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 8, 1, "§cDeaktiviert ✘"));
                lobby.getSettingsAPI().setPJUMP(player, false);

            } else {
                jumpPads.add(player);
                invent1.get(player).setItem(24,
                        lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 10, 1, "§aAktiviert ✔"));
                lobby.getSettingsAPI().setPJUMP(player, true);
            }
        }
    }

    @EventHandler
    public void DesingMenue(InventoryClickEvent clickEvent) {
        if (clickEvent.getClickedInventory() == null) return;
        if (clickEvent.getClickedInventory().getType() == null) return;
        if (clickEvent.getCurrentItem() == null) return;
        if (clickEvent.getCurrentItem().getType() == null) return;
        if (clickEvent.getCurrentItem().getType().equals(Material.AIR)) return;
        Player player = (Player) clickEvent.getWhoClicked();
        if (clickEvent.getCurrentItem().getType().equals(Material.STAINED_GLASS)
                && !clickEvent.getCurrentItem().getItemMeta().getDisplayName().contains("Design")
                && !clickEvent.getCurrentItem().getItemMeta().getDisplayName().contains("Spawn")) {
            short i = clickEvent.getCurrentItem().getDurability();
            design.put(player, i);
            coreSpigot.getModuleManager().getSettingsAPI().setColor(player, i);
            ItemColToString(player);
            player.closeInventory();
            new BukkitRunnable() {
                int a = 1;

                @Override
                public void run() {
                    if (a > 0) {
                        setInv(player);
                        a--;
                    } else {
                        this.cancel();
                    }
                }
            }.runTaskTimer(coreSpigot, 0L, 10L);
            setInv(player);
            player.openInventory(invent1.get(player));
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onScore(InventoryClickEvent clickEvent) {
        if (clickEvent.getClickedInventory() == null) return;
        if (clickEvent.getClickedInventory().getType() == null) return;
        if (clickEvent.getCurrentItem() == null) return;
        if (clickEvent.getCurrentItem().getType() == null) return;
        if (clickEvent.getCurrentItem().getType().equals(Material.AIR)) return;
        ModuleManager lobby = this.coreSpigot.getModuleManager();
        Player player = (Player) clickEvent.getWhoClicked();
        if (clickEvent.getCurrentItem().getTypeId() == 323
                && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("§5§lScoreboard")) {
            if (score1.containsKey(player)) {
                player.openInventory(score1.get(player));
            } else {
                setScore(player);
                player.openInventory(score1.get(player));
            }
        } else if (clickEvent.getCurrentItem().getType().equals(Material.SKULL_ITEM)
                && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("§9§lFreunde")) {
            if (scoreFriends.contains(player)) {
                scoreFriends.remove(player);
                score1.get(player).setItem(19,
                        lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 8, 1, "§cDeaktiviert ✗"));
                lobby.getSettingsAPI().setFriends(player, false);
                player.updateInventory();
            } else {
                if (scoreClan.contains(player) && scoreCoins.contains(player) && scoreRank.contains(player) && scoreTime.contains(player) && scoreServer.contains(player)) {
                    player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "Du kannst nur maximal 5 Einträge aktivieren!");
                    return;
                }
                scoreFriends.add(player);
                score1.get(player).setItem(19,
                        lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 10, 1, "§aAktiviert ✔"));
                lobby.getSettingsAPI().setFriends(player, true);
                player.updateInventory();
            }
        } else if (clickEvent.getCurrentItem().getType().equals(Material.GOLD_INGOT)
                && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lCoins")) {
            if (scoreCoins.contains(player)) {
                scoreCoins.remove(player);
                score1.get(player).setItem(20,
                        lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 8, 1, "§cDeaktiviert ✗"));
                lobby.getSettingsAPI().setCoins(player, false);
                player.updateInventory();
            } else {
                if (scoreClan.contains(player) && scoreFriends.contains(player) && scoreRank.contains(player) && scoreTime.contains(player) && scoreServer.contains(player)) {
                    player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "Du kannst nur maximal 5 Einträge aktivieren!");
                    return;
                }
                scoreCoins.add(player);
                score1.get(player).setItem(20,
                        lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 10, 1, "§aAktiviert ✔"));
                lobby.getSettingsAPI().setCoins(player, true);
                player.updateInventory();
            }
        } else if (clickEvent.getCurrentItem().getType().equals(Material.REDSTONE)
                && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("§c§lRang")) {
            if (scoreRank.contains(player)) {
                scoreRank.remove(player);
                score1.get(player).setItem(21,
                        lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 8, 1, "§cDeaktiviert ✗"));
                lobby.getSettingsAPI().setRang(player, false);
                player.updateInventory();
            } else {
                if (scoreClan.contains(player) && scoreCoins.contains(player) && scoreFriends.contains(player) && scoreTime.contains(player) && scoreServer.contains(player)) {
                    player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "Du kannst nur maximal 5 Einträge aktivieren!");
                    return;
                }
                scoreRank.add(player);
                score1.get(player).setItem(21,
                        lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 10, 1, "§aAktiviert ✔"));
                lobby.getSettingsAPI().setRang(player, true);
                player.updateInventory();
            }
        } else if (clickEvent.getCurrentItem().getType().equals(Material.ENDER_CHEST)
                && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("§2§lClan")) {
            if (scoreClan.contains(player)) {
                scoreClan.remove(player);
                score1.get(player).setItem(23,
                        lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 8, 1, "§cDeaktiviert ✗"));
                lobby.getSettingsAPI().setClan(player, false);
                player.updateInventory();
            } else {
                if (scoreFriends.contains(player) && scoreCoins.contains(player) && scoreRank.contains(player) && scoreTime.contains(player) && scoreServer.contains(player)) {
                    player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "Du kannst nur maximal 5 Einträge aktivieren!");
                    return;
                }
                scoreClan.add(player);
                score1.get(player).setItem(23,
                        lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 10, 1, "§aAktiviert ✔"));
                lobby.getSettingsAPI().setClan(player, true);
                player.updateInventory();
            }
        } else if (clickEvent.getCurrentItem().getType().equals(Material.NETHER_STAR)
                && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("§f§lServer")) {
            if (scoreServer.contains(player)) {
                scoreServer.remove(player);
                score1.get(player).setItem(24,
                        lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 8, 1, "§cDeaktiviert ✗"));
                lobby.getSettingsAPI().setServer(player, false);
                player.updateInventory();
            } else {
                if (scoreClan.contains(player) && scoreCoins.contains(player) && scoreRank.contains(player) && scoreTime.contains(player) && scoreFriends.contains(player)) {
                    player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "Du kannst nur maximal 5 Einträge aktivieren!");
                    return;
                }
                scoreServer.add(player);
                score1.get(player).setItem(24,
                        lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 10, 1, "§aAktiviert ✔"));
                lobby.getSettingsAPI().setServer(player, true);
                player.updateInventory();
            }
        } else if (clickEvent.getCurrentItem().getType().equals(Material.WATCH) && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Online-Zeit")) {
            if (scoreTime.contains(player)) {
                scoreTime.remove(player);
                score1.get(player).setItem(25, lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 8, 1, "§cDeaktiviert ✗"));
                lobby.getSettingsAPI().setTime(player, false);
            } else {
                if (scoreClan.contains(player) && scoreCoins.contains(player) && scoreRank.contains(player) && scoreFriends.contains(player) && scoreServer.contains(player)) {
                    player.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "Du kannst nur maximal 5 Einträge aktivieren!");
                    return;
                }
                scoreTime.add(player);
                score1.get(player).setItem(25, lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 10, 1, "§aAktiviert ✔"));
                lobby.getSettingsAPI().setTime(player, true);
            }
        }
    }

    private void setScore(Player player) {
        // Score 1
        ModuleManager lobby = this.coreSpigot.getModuleManager();
        Inventory inv1 = Bukkit.createInventory(null, 36, "§8\u00BB§9§lScoreboard§8\u00AB");

        int size = 35;

        while (size >= 0) {
            inv1.setItem(size, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, design.get(player), 1));
            size--;
        }

        ItemStack friends = CraftItemStack.asBukkitCopy(skulls.get("friends"));
        inv1.setItem(10, friends);

        if (scoreFriends.contains(player)) {
            inv1.setItem(19, lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 10, 1, "§aAktiviert ✔"));
        } else {
            inv1.setItem(19, lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 8, 1, "§cDeaktiviert ✗"));
        }

        inv1.setItem(11, lobby.getItemCreator().createItemWithMaterial(Material.GOLD_INGOT, 0, 1, "§6§lCoins"));
        if (scoreCoins.contains(player)) {
            inv1.setItem(20, lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 10, 1, "§aAktiviert ✔"));
        } else {
            inv1.setItem(20, lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 8, 1, "§cDeaktiviert ✗"));
        }

        inv1.setItem(12, lobby.getItemCreator().createItemWithMaterial(Material.REDSTONE, 0, 1, "§c§lRang"));
        if (scoreRank.contains(player)) {
            inv1.setItem(21, lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 10, 1, "§aAktiviert ✔"));
        } else {
            inv1.setItem(21, lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 8, 1, "§cDeaktiviert ✗"));
        }

        inv1.setItem(14, lobby.getItemCreator().createItemWithMaterial(Material.ENDER_CHEST, 0, 1, "§2§lClan"));
        if (scoreClan.contains(player)) {
            inv1.setItem(23, lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 10, 1, "§aAktiviert ✔"));
        } else {
            inv1.setItem(23, lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 8, 1, "§cDeaktiviert ✗"));
        }

        inv1.setItem(15, lobby.getItemCreator().createItemWithMaterial(Material.NETHER_STAR, 0, 1, "§f§lServer"));
        if (scoreServer.contains(player)) {
            inv1.setItem(24, lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 10, 1, "§aAktiviert ✔"));
        } else {
            inv1.setItem(24, lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 8, 1, "§cDeaktiviert ✗"));
        }

        inv1.setItem(16, lobby.getItemCreator().createItemWithMaterial(Material.WATCH, 0, 1, "§6Online-Zeit"));
        if (scoreTime.contains(player)) {
            inv1.setItem(25, lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 10, 1, "§aAktiviert ✔"));
        } else {
            inv1.setItem(25, lobby.getItemCreator().createItemWithMaterial(Material.INK_SACK, 8, 1, "§cDeaktiviert ✗"));
        }

        score1.put(player, inv1);

    }

    @EventHandler
    public void rideOnMe(PlayerInteractAtEntityEvent interactAtEntityEvent) {
        if (interactAtEntityEvent.getRightClicked() instanceof Player) {
            Player horse = (Player) interactAtEntityEvent.getRightClicked();
            Player rider = interactAtEntityEvent.getPlayer();
            if (rideState.contains(horse)) {
                horse.setPassenger(rider);
                ActionBarAPI.sendActionBar(horse, "§6§l" + rider.getName() + " §f§lsitzt nun auf dir!");
            } else {
                rider.sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "§cDer Spieler hat das Ride on me Feature nicht aktiviert!");
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void hitRider(EntityDamageByEntityEvent damageByEntityEvent) {
        if (damageByEntityEvent.getDamager() == null) return;
        if (!(damageByEntityEvent.getEntity() instanceof Player)) return;
        if (damageByEntityEvent.getEntity() instanceof Player) {
            Player rider = (Player) damageByEntityEvent.getEntity();
            Player hider = (Player) damageByEntityEvent.getDamager();
            if (hider.getPassenger() == null) return;
            if (hider.getPassenger().equals(rider)) {
                Vector v = rider.getLocation().getDirection();
                v.setY(1.2);
                rider.leaveVehicle();
                rider.setVelocity(v);
            }
        }
        damageByEntityEvent.setCancelled(true);
    }

    private Inventory Design() {
        ModuleManager lobby = this.coreSpigot.getModuleManager();
        Inventory inv = Bukkit.createInventory(null, 18, "§9§lDesign");
        inv.setItem(1, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS, 0, 1, "§f§lWeiss"));
        inv.setItem(2, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS, 1, 1, "§6§lOrange"));
        inv.setItem(3, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS, 2, 1, "§5§lMagenta"));
        inv.setItem(4, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS, 3, 1, "§b§lHellblau"));
        inv.setItem(5, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS, 4, 1, "§e§lGelb"));
        inv.setItem(6, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS, 5, 1, "§a§lHellgrün"));
        inv.setItem(7, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS, 6, 1, "§d§lPink"));
        inv.setItem(9, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS, 7, 1, "§8§lDunkelgrau"));
        inv.setItem(10, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS, 8, 1, "§7§lHellgrau"));
        inv.setItem(11, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS, 9, 1, "§3§lAqua"));
        inv.setItem(12, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS, 10, 1, "§5§lLila"));
        inv.setItem(13, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS, 11, 1, "§9§lDunkelblau"));
        inv.setItem(14, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS, 12, 1, "§fBraun"));
        inv.setItem(15, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS, 13, 1, "§2§lDunkelgrün"));
        inv.setItem(16, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS, 14, 1, "§c§lRot"));
        inv.setItem(17, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS, 15, 1, "§0§lSchwarz"));
        return inv;
    }

    public void invTimer() {
        coreSpigot.getModuleManager().getSchedulerSaver().createScheduler(new BukkitRunnable() {
            int topPlace = -1;
            int downPlace = 44;

            @Override
            public void run() {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (all.getOpenInventory().getTitle().contains("§c§lEinstellungen") || all.getOpenInventory().getTitle().contains("Navigator")) {
                        int des = design.get(all) - 1;
                        if (des < 0) des = 15;
                        ItemStack topGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) des);
                        if (topPlace == 0)
                            all.getOpenInventory().setItem(8, new ItemStack(Material.STAINED_GLASS_PANE, 1, design.get(all)));
                        else
                            all.getOpenInventory().setItem(topPlace - 1, new ItemStack(Material.STAINED_GLASS_PANE, 1, design.get(all)));
                        all.getOpenInventory().setItem(topPlace, topGlass);

                        ItemStack downGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) des);
                        if (downPlace == 45)
                            all.getOpenInventory().setItem(53, new ItemStack(Material.STAINED_GLASS_PANE, 1, design.get(all)));
                        else
                            all.getOpenInventory().setItem(downPlace - 1, new ItemStack(Material.STAINED_GLASS_PANE, 1, design.get(all)));
                        all.getOpenInventory().setItem(downPlace, downGlass);
                    }
                }
                if (topPlace == 8) topPlace = -1;
                if (downPlace == 53) downPlace = 44;
                topPlace++;
                downPlace++;
            }
        }.runTaskTimer(coreSpigot, 0L, 10L));
    }

    private Integer getDyeColor(short color) {
        if (color == 0)
            return 15;
        if (color == 1)
            return 14;
        if (color == 2)
            return 13;
        if (color == 3)
            return 12;
        if (color == 4)
            return 11;
        if (color == 5)
            return 10;
        if (color == 6)
            return 9;
        if (color == 7)
            return 8;
        if (color == 8)
            return 7;
        if (color == 9)
            return 6;
        if (color == 10)
            return 5;
        if (color == 11)
            return 4;
        if (color == 12)
            return 3;
        if (color == 13)
            return 2;
        if (color == 14)
            return 1;
        if (color == 15)
            return 0;
        return 0;
    }

    public Set<Player> getSilentState() {
        return this.silentState;
    }

    public Set<Player> getRideState() {
        return this.rideState;
    }

    public Map<Player, Short> getDesign() {
        return this.design;
    }

    public Map<Player, String> getColor() {
        return this.color;
    }

    public Set<Player> getJumpPads() {
        return this.jumpPads;
    }

    public Set<Player> getDoubleJump() {
        return this.doubleJump;
    }

    public Set<Player> getWaterJump() {
        return this.waterJump;
    }

    public Map<Player, PlayerLevel> getPlayerLevel() {
        return this.playerLevel;
    }

    public Set<Player> getScoreFriends() {
        return this.scoreFriends;
    }

    public Set<Player> getScoreRank() {
        return this.scoreRank;
    }

    public Set<Player> getScoreServer() {
        return this.scoreServer;
    }

    public Set<Player> getScoreClan() {
        return this.scoreClan;
    }

    public Set<Player> getScoreCoins() {
        return this.scoreCoins;
    }

    public Set<Player> getScoreTime() {
        return this.scoreTime;
    }

    public Set<Player> getRealTime() {
        return this.realTime;
    }

    public Set<Player> getDay() {
        return this.day;
    }

    public Set<Player> getWeather() {
        return this.weather;
    }

    public Map<Player, Inventory> getInvent1() {
        return this.invent1;
    }

    public Map<Player, Inventory> getInvent2() {
        return this.invent2;
    }

    public CoreSpigot getCoreSpigot() {
        return this.coreSpigot;
    }

    public void loadSkulls(Player player) {
        ItemStack playerStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemMeta playerMeta = playerStack.getItemMeta();
        playerMeta.setDisplayName("§9§lFreunde");
        playerStack.setItemMeta(playerMeta);
        SkullMeta playerSM = (SkullMeta) playerStack.getItemMeta();
        playerSM.setOwner(player.getName());
        playerStack.setItemMeta(playerSM);
        skulls.put("friends", CraftItemStack.asNMSCopy(playerStack));

        ItemStack prev = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemMeta prevMeta = prev.getItemMeta();
        prevMeta.setDisplayName("§6⇽ Vorherige Seite");
        prev.setItemMeta(prevMeta);
        SkullMeta prevSkullMeta = (SkullMeta) prev.getItemMeta();
        prevSkullMeta.setOwner("MHF_ArrowLeft");
        prev.setItemMeta(prevSkullMeta);
        skulls.put("prev", CraftItemStack.asNMSCopy(prev));

        ItemStack next = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemMeta nextMeta = next.getItemMeta();
        nextMeta.setDisplayName("§6Nächste Seite ⇾");
        next.setItemMeta(nextMeta);
        SkullMeta nextSkullMeta = (SkullMeta) next.getItemMeta();
        nextSkullMeta.setOwner("MHF_ArrowRight");
        next.setItemMeta(nextSkullMeta);
        skulls.put("next", CraftItemStack.asNMSCopy(next));
    }
}