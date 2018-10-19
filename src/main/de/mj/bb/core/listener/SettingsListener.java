/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package main.de.mj.bb.core.listener;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import lombok.Getter;
import main.de.mj.bb.core.CoreSpigot;
import main.de.mj.bb.core.managers.ModuleManager;
import main.de.mj.bb.core.utils.PlayerLevel;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.WeatherType;
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

@Getter
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
    private Set<Player> time = new HashSet<>();
    private Set<Player> realTime = new HashSet<>();
    private Set<Player> day = new HashSet<>();
    private Set<Player> weather = new HashSet<>();

    private Map<Player, Inventory> invent1 = new HashMap<>();
    private Map<Player, Inventory> invent2 = new HashMap<>();
    private Map<Player, Inventory> invent3 = new HashMap<>();
    private Map<Player, Inventory> invent4 = new HashMap<>();
    private Map<Player, Inventory> score1 = new HashMap<>();
    private Map<Player, Inventory> score2 = new HashMap<>();

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
    public void onInteract(PlayerInteractEvent interactEvent) {
        if (interactEvent.getItem() == null) return;
        if (interactEvent.getItem().getType() == null) return;
        if (interactEvent.getItem().getType().equals(Material.AIR)) return;
        if (!(interactEvent.getAction().equals(Action.RIGHT_CLICK_AIR) || interactEvent.getAction().equals(Action.RIGHT_CLICK_BLOCK) || interactEvent.getAction().equals(Action.LEFT_CLICK_AIR) || interactEvent.getAction().equals(Action.LEFT_CLICK_BLOCK)))
            return;
        Player player = interactEvent.getPlayer();
        if (player.getItemInHand().getType().equals(Material.REDSTONE_COMPARATOR) && player.getItemInHand().getItemMeta().getDisplayName().equals("§8\u00BB§c§lEinstellungen§8\u00AB")) {
            if (invent1.containsKey(player)) {
                player.openInventory(invent1.get(player));
            } else {
                setInv(player);
                player.openInventory(invent1.get(player));
            }
        }
    }

    private void setInv(Player player) {
        ModuleManager lobby = this.coreSpigot.getModuleManager();
        invent1.remove(player);
        player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, 1);
        Inventory inv = Bukkit.createInventory(null, 54, "§8\u00BB§c§lEinstellungen§8\u00AB");
        Inventory inv2 = Bukkit.createInventory(null, 54, "§8\u00BB§c§lEinstellungen§8\u00AB");
        Inventory inv3 = Bukkit.createInventory(null, 54, "§8\u00BB§c§lEinstellungen§8\u00AB");
        Inventory inv4 = Bukkit.createInventory(null, 54, "§8\u00BB§c§lEinstellungen§8\u00AB");
        for (int i = 8; i >= 0; i--) {
            if (design.containsKey(player)) {
                inv.setItem(i,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, design.get(player), 1));
            } else {
                inv.setItem(i, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, 0, 1));
            }
        }
        ArrayList<String> designLore = new ArrayList<String>();
        designLore.add("§7Ändere das Design der Inventare und des Scoreboards in der Lobby");
        inv.setItem(9, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS, 0, 1, "§9§lDesign", designLore));
        if (design.containsKey(player)) {
            inv.setItem(17, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, design.get(player), 1));
        } else {
            inv.setItem(17, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 0, 1));
        }
        ArrayList<String> silentLore = new ArrayList<String>();
        silentLore.add("§7Mache dich anderen Spielern gegenüber unsichtbar und");
        silentLore.add("§7mache andere Spieler für dich unsichtbar");
        silentLore.add("§cDERZEIT NICHT VERFÜGBAR!");
        inv.setItem(18, lobby.getItemCreator().createItemWithMaterial(Material.ARROW, 0, 1, "§2§lSilent-Lobby", silentLore));
        if (silentState.contains(player)) {
            inv.setItem(26, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT"));
        } else {
            inv.setItem(26, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT"));
        }
        ArrayList<String> hideLore = new ArrayList<String>();
        hideLore.add("§7Verstecke bestimmte Spielergruppen");
        inv.setItem(27, lobby.getItemCreator().createItemWithMaterial(Material.BLAZE_ROD, 0, 1, "§6§lVerstecken", hideLore));
        ArrayList<String> ridelore = new ArrayList<String>();
        ridelore.add("§7Lasse Spieler auf dir reiten");
        inv.setItem(27, lobby.getItemCreator().createItemWithMaterial(Material.TRIPWIRE_HOOK, 0, 1, "§3§lRide on me", ridelore));
        if (rideState.contains(player)) {
            inv.setItem(35, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT"));
        } else {
            inv.setItem(35, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT"));
        }

        ItemStack ArrowR = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemMeta ArrowRM = ArrowR.getItemMeta();
        ArrowRM.setDisplayName("§6Nächste Seite");
        ArrowR.setItemMeta(ArrowRM);
        SkullMeta SM = (SkullMeta) ArrowR.getItemMeta();
        SM.setOwner("MHF_ArrowRight");
        ArrowR.setItemMeta(SM);
        inv.setItem(44, ArrowR);
        inv3.setItem(44, ArrowR);
        inv.setItem(40, lobby.getItemCreator().createItemWithMaterial(Material.PAPER, 0, 1, "§6§lSeite §a§l1§6§l/§a§l2"));

        for (int a = 53; a >= 45; a--) {
            if (design.containsKey(player)) {
                inv.setItem(a,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, design.get(player), 1));
            } else {
                inv.setItem(a, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, 0, 1));
            }
        }
        invent1.put(player, inv);

        // inv2
        for (int i = 8; i >= 0; i--) {
            if (design.containsKey(player)) {
                inv2.setItem(i,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, design.get(player), 1));
            } else {
                inv2.setItem(i, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, 0, 1));
            }
        }

        ArrayList<String> scoreLore = new ArrayList<String>();
        scoreLore.add("§7Ändere das Aussehen deines Scoreboards");
        inv2.setItem(9, lobby.getItemCreator().createItemWithMaterial(Material.SIGN, 0, 1, "§5§lScorboard", scoreLore));

        ArrayList<String> realTimeLore = new ArrayList<String>();
        realTimeLore.add("§7Wechsle zwischen Echtzeit, Tag oder Nacht in der Lobby");
        inv2.setItem(18, lobby.getItemCreator().createItemWithMaterial(Material.WATCH, 0, 1, "§6§lEcht§9§lzeit", realTimeLore));
        if (realTime.contains(player)) {
            inv2.setItem(26,
                    lobby.getItemCreator().createItemWithMaterial(Material.DAYLIGHT_DETECTOR, 0, 1, "§6Echt§9zeit"));
        } else if (day.contains(player)) {
            inv2.setItem(26, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 4, 1, "§6Tag"));
        } else {
            inv2.setItem(26, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 11, 1, "§9Nacht"));
        }

        ArrayList<String> weatherLore = new ArrayList<String>();
        weatherLore.add("§7Wechsle zwichen Sonne oder Regen/Schnee");
        inv2.setItem(27, lobby.getItemCreator().createItemWithMaterial(Material.WATER_BUCKET, 0, 1, "§b§lWetter", weatherLore));
        if (weather.contains(player)) {
            inv2.setItem(35, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 4, 1, "§6Sonne"));
        } else {
            inv2.setItem(35, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 11, 1, "§9Regen/Schnee"));
        }

        inv2.setItem(44, ArrowR);
        inv.setItem(40, lobby.getItemCreator().createItemWithMaterial(Material.PAPER, 0, 1, "§6§lSeite §a§l1§6§l/§a§l4"));
        ItemStack ArrowL = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemMeta ArrowLM = ArrowL.getItemMeta();
        ArrowLM.setDisplayName("§6Vorherige Seite");
        ArrowL.setItemMeta(ArrowLM);
        SkullMeta SLM = (SkullMeta) ArrowL.getItemMeta();
        SLM.setOwner("MHF_ArrowLeft");
        ArrowL.setItemMeta(SLM);
        inv2.setItem(36, ArrowL);
        inv3.setItem(36, ArrowL);
        inv4.setItem(36, ArrowL);
        inv2.setItem(40, lobby.getItemCreator().createItemWithMaterial(Material.PAPER, 0, 1, "§6§lSeite §a§l2§6§l/§a§l4"));
        inv3.setItem(40, lobby.getItemCreator().createItemWithMaterial(Material.PAPER, 0, 1, "§6§lSeite §a§l3§6§l/§a§l4"));
        inv4.setItem(40, lobby.getItemCreator().createItemWithMaterial(Material.PAPER, 0, 1, "§6§lSeite §a§l4§6§l/§a§l4"));

        for (int a = 53; a >= 45; a--) {
            if (design.containsKey(player)) {
                inv2.setItem(a,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, design.get(player), 1));
            } else {
                inv2.setItem(a, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, 0, 1));
            }
        }
        invent2.put(player, inv2);

        // inv3

        for (int i = 8; i >= 0; i--) {
            if (design.containsKey(player)) {
                inv3.setItem(i,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, design.get(player), 1));
            } else {
                inv3.setItem(i, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, 0, 1));
            }
        }

        ArrayList<String> jumpPadLore = new ArrayList<String>();
        jumpPadLore.add("§7Schalte JumpPads an oder aus");
        inv3.setItem(9, lobby.getItemCreator().createItemWithMaterial(Material.GOLD_PLATE, 0, 1, "§5§lJumpPad", jumpPadLore));
        if (jumpPads.contains(player)) {
            inv3.setItem(17, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT"));
        } else {
            inv3.setItem(17, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT"));
        }

        ArrayList<String> doubleJumpLore = new ArrayList<String>();
        doubleJumpLore.add("§7Schalte DoubleJump an oder aus");
        inv3.setItem(18, lobby.getItemCreator().createItemWithMaterial(Material.FEATHER, 0, 1, "§f§lDoubleJump", doubleJumpLore));
        if (doubleJump.contains(player)) {
            inv3.setItem(26, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT"));
        } else {
            inv3.setItem(26, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT"));
        }

        ArrayList<String> liquidBounceLore = new ArrayList<String>();
        inv3.setItem(27,
                lobby.getItemCreator().createItemWithMaterial(Material.LAVA_BUCKET, 0, 1, "§2§lJump on Liquid", liquidBounceLore));
        if (waterJump.contains(player)) {
            inv3.setItem(35, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT"));
        } else {
            inv3.setItem(35, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT"));
        }

        for (int a = 53; a >= 45; a--) {
            if (design.containsKey(player)) {
                inv3.setItem(a,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, design.get(player), 1));
            } else {
                inv3.setItem(a, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, 0, 1));
            }
        }
        invent3.put(player, inv3);

        //inv4
        for (int i = 8; i >= 0; i--) {
            if (design.containsKey(player)) {
                inv4.setItem(i,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, design.get(player), 1));
            } else {
                inv4.setItem(i, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, 0, 1));
            }
        }

        ArrayList<String> levelLore = new ArrayList<>();
        levelLore.add("§7Lass deine Levelleiste entweder Jahr, Lobby, oder Slot anzeigen!");
        inv4.setItem(9, lobby.getItemCreator().createItemWithMaterial(Material.EXP_BOTTLE, 0, 1, "§a§lLevelleiste", levelLore));
        if (playerLevel.containsKey(player)) {
            if (playerLevel.get(player).equals(PlayerLevel.LOBBY))
                inv4.setItem(17, lobby.getItemCreator().createItemWithMaterial(Material.NETHER_STAR, 0, 1, "§fLobby"));
            else if (playerLevel.get(player).equals(PlayerLevel.SCROLL))
                inv4.setItem(17, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§aSlot"));
            else if (playerLevel.get(player).equals(PlayerLevel.YEAR))
                inv4.setItem(17, lobby.getItemCreator().createItemWithMaterial(Material.FIREWORK, 0, 1, "§cJahr"));
        }

        for (int a = 53; a >= 45; a--) {
            if (design.containsKey(player)) {
                inv4.setItem(a,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, design.get(player), 1));
            } else {
                inv4.setItem(a, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, 0, 1));
            }
        }
        invent4.put(player, inv4);
    }

    @EventHandler
    public void SettingsMenue(InventoryClickEvent clickEvent) {
        if (clickEvent.getClickedInventory() == null) return;
        if (clickEvent.getClickedInventory().getType() == null) return;
        if (clickEvent.getCurrentItem() == null) return;
        if (clickEvent.getCurrentItem().getType() == null) return;
        if (clickEvent.getCurrentItem().getType().equals(Material.AIR)) return;
        ModuleManager lobby = this.coreSpigot.getModuleManager();
        Player player = (Player) clickEvent.getWhoClicked();
        lobby.getScoreboardManager().setScoreboard(player);
        if (clickEvent.getCurrentItem().getItemMeta().getDisplayName() == null) return;
        if (!(clickEvent.getCurrentItem().getItemMeta().getDisplayName().contains("Main"))
                && clickEvent.getCurrentItem().getType().equals(Material.ARROW)) {
            if (player.hasPermission("coreSpigot.silent")) {
                if (!silentState.contains(player)) {
                    silentState.add(player);
                    lobby.getSettingsAPI().setSilent(player, true);
                    Inventory inv = invent1.get(player);
                    inv.setItem(26,
                            lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT"));
                    player.updateInventory();
                    player.sendMessage(lobby.getData().getPrefix() + "§aDu hast die §2Silent-Lobby §abetreten!");
                } else {
                    lobby.getSettingsAPI().setSilent(player, false);
                    silentState.remove(player);
                    Inventory inv = invent1.get(player);
                    inv.setItem(26, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 14, 1,
                            "§cDEAKTIVIERT", null));
                    player.updateInventory();
                    player.sendMessage(lobby.getData().getPrefix() + "§cDu hast die §4Silen-Lobby §cverlassen!");
                }
            } else {
                player.sendMessage(lobby.getData().getPrefix()
                        + "§cDu benötigst mindestens den Rang VIP+, um dieses Feature nutzen zu können!");
            }
        } else if (clickEvent.getCursor().getType().equals(Material.BLAZE_ROD)
                || clickEvent.getCurrentItem().getType().equals(Material.BLAZE_ROD) && !clickEvent.getCurrentItem().getItemMeta().getDisplayName().contains("Fun")) {
            player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, 1);
            player.performCommand("hide");
        } else if (clickEvent.getCurrentItem().getType().equals(Material.STAINED_GLASS)) {
            player.openInventory(Design());
        } else if (clickEvent.getCurrentItem().getType().equals(Material.TRIPWIRE_HOOK)
                && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("§3§lRide on me")) {
            if (rideState.contains(player)) {
                player.sendMessage(lobby.getData().getPrefix() + "§cDu hast das §4Ride on me §cFeature deaktiviert!");
                rideState.remove(player);
                invent1.get(player).setItem(35,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT"));
                lobby.getSettingsAPI().setRide(player, false);
                player.updateInventory();
            } else {
                player.sendMessage(lobby.getData().getPrefix() + "§aDu hast das §2Ride on me §aFeature aktiviert!");
                rideState.add(player);
                invent1.get(player).setItem(35,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT"));
                lobby.getSettingsAPI().setRide(player, true);
                player.updateInventory();
            }
        } else if (clickEvent.getCurrentItem().getType().equals(Material.WATCH)
                && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lEcht§9§lzeit")) {
            if (player.getOpenInventory().getItem(26).getItemMeta().getDisplayName().equalsIgnoreCase("§6Echt§9zeit")) {
                realTime.remove(player);
                day.add(player);
                invent2.get(player).setItem(26,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 4, 1, "§6Tag"));
                lobby.getSettingsAPI().setRealTime(player, false, true);
                player.updateInventory();
            } else if (player.getOpenInventory().getItem(26).getItemMeta().getDisplayName().equalsIgnoreCase("§6Tag")) {
                day.remove(player);
                invent2.get(player).setItem(26,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 11, 1, "§9Nacht"));
                lobby.getSettingsAPI().setRealTime(player, false, false);
                player.updateInventory();
            } else if (player.getOpenInventory().getItem(26).getItemMeta().getDisplayName().equalsIgnoreCase("§9Nacht")) {
                realTime.add(player);
                invent2.get(player).setItem(26,
                        lobby.getItemCreator().createItemWithMaterial(Material.DAYLIGHT_DETECTOR, 0, 1, "§6Echt§9zeit"));
                lobby.getSettingsAPI().setRealTime(player, true, false);
                player.updateInventory();
            }
        } else if (clickEvent.getCurrentItem().getType().equals(Material.SKULL_ITEM)
                && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("§6Nächste Seite")) {
            if (player.getOpenInventory().getItem(40).getItemMeta().getDisplayName().equalsIgnoreCase("§6§lSeite §a§l1§6§l/§a§l4"))
                player.openInventory(invent2.get(player));
            else if (player.getOpenInventory().getItem(40).getItemMeta().getDisplayName().equalsIgnoreCase("§6§lSeite §a§l2§6§l/§a§l4"))
                player.openInventory(invent3.get(player));
            else if (player.getOpenInventory().getItem(40).getItemMeta().getDisplayName().equalsIgnoreCase("§6§lSeite §a§l3§6§l/§a§l4"))
                player.openInventory(invent4.get(player));
            else
                player.openInventory(invent1.get(player));
        } else if (clickEvent.getCurrentItem().getType().equals(Material.SKULL_ITEM)
                && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("§6Vorherige Seite")) {
            if (player.getOpenInventory().getItem(40).getItemMeta().getDisplayName().equalsIgnoreCase("§6§lSeite §a§l2§6§l/§a§l4"))
                player.openInventory(invent1.get(player));
            else if (player.getOpenInventory().getItem(40).getItemMeta().getDisplayName().equalsIgnoreCase("§6§lSeite §a§l3§6§l/§a§l4"))
                player.openInventory(invent2.get(player));
            else if (player.getOpenInventory().getItem(40).getItemMeta().getDisplayName().equalsIgnoreCase("§6§lSeite §a§l4§6§l/§a§l4"))
                player.openInventory(invent3.get(player));
            else
                player.openInventory(invent4.get(player));
        } else if (clickEvent.getCurrentItem().getType().equals(Material.WATER_BUCKET)
                && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("§b§lWetter")) {
            if (weather.contains(player)) {
                weather.remove(player);
                invent2.get(player).setItem(35,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 11, 1, "§9Regen/Schnee"));
                player.setPlayerWeather(WeatherType.DOWNFALL);
                lobby.getSettingsAPI().setWeather(player, false);
                player.updateInventory();
            } else {
                weather.add(player);
                invent2.get(player).setItem(35,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 4, 1, "§6Sonne"));
                player.setPlayerWeather(WeatherType.CLEAR);
                lobby.getSettingsAPI().setWeather(player, true);
                player.updateInventory();
            }
        } else if (clickEvent.getCurrentItem().getType().equals(Material.LAVA_BUCKET)
                && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("§2§lJump on Liquid")) {
            if (waterJump.contains(player)) {
                waterJump.remove(player);
                invent3.get(player).setItem(35,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT"));
                lobby.getSettingsAPI().setWJUMP(player, false);
                player.updateInventory();
            } else {
                waterJump.add(player);
                invent3.get(player).setItem(35,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT"));
                lobby.getSettingsAPI().setWJUMP(player, true);
                player.updateInventory();
            }
        } else if (clickEvent.getCurrentItem().getType().equals(Material.FEATHER)
                && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("§f§lDoubleJump")) {
            if (doubleJump.contains(player)) {
                doubleJump.remove(player);
                invent3.get(player).setItem(26,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT"));
                lobby.getSettingsAPI().setDJUMP(player, false);
                player.updateInventory();
            } else {
                doubleJump.add(player);
                invent3.get(player).setItem(26,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT"));
                lobby.getSettingsAPI().setDJUMP(player, true);
                player.updateInventory();
            }
        } else if (clickEvent.getCurrentItem().getType().equals(Material.EXP_BOTTLE) && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("§a§lLevelleiste")) {
            if (playerLevel.get(player).equals(PlayerLevel.LOBBY)) {
                playerLevel.replace(player, PlayerLevel.YEAR);
                lobby.getSettingsAPI().setLEVEL(player, PlayerLevel.YEAR);
                invent4.get(player).setItem(17, lobby.getItemCreator().createItemWithMaterial(Material.FIREWORK, 0, 1, "§cJahr"));
                player.setLevel(Calendar.getInstance().get(Calendar.YEAR));
                player.updateInventory();
            } else if (playerLevel.get(player).equals(PlayerLevel.YEAR)) {
                playerLevel.replace(player, PlayerLevel.SCROLL);
                lobby.getSettingsAPI().setLEVEL(player, PlayerLevel.SCROLL);
                invent4.get(player).setItem(17, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, 15, 1, "§aSlot"));
                player.setLevel(1);
                player.updateInventory();
            } else if (playerLevel.get(player).equals(PlayerLevel.SCROLL)) {
                playerLevel.replace(player, PlayerLevel.LOBBY);
                lobby.getSettingsAPI().setLEVEL(player, PlayerLevel.LOBBY);
                invent4.get(player).setItem(17, lobby.getItemCreator().createItemWithMaterial(Material.NETHER_STAR, 0, 1, "§fLobby"));
                String[] serverName = player.getServer().getServerName().split("-");
                int server = Integer.parseInt(serverName[1]);
                player.setLevel(server);
                player.updateInventory();
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
                && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("§5§lScorboard")) {
            if (score1.containsKey(player)) {
                player.openInventory(score1.get(player));
            } else {
                setScore(player);
                player.openInventory(score1.get(player));
            }
        } else if (clickEvent.getCurrentItem().getType().equals(Material.SKULL_ITEM)
                && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("§aNächste Seite")) {
            player.openInventory(score2.get(player));
        } else if (clickEvent.getCurrentItem().getType().equals(Material.SKULL_ITEM)
                && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("§aVorherige Seite")) {
            player.openInventory(score1.get(player));
        } else if (clickEvent.getCurrentItem().getType().equals(Material.SKULL_ITEM)
                && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("§9§lFreunde")) {
            if (scoreFriends.contains(player)) {
                scoreFriends.remove(player);
                score1.get(player).setItem(17,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT"));
                lobby.getSettingsAPI().setFriends(player, false);
                player.updateInventory();
            } else {
                scoreFriends.add(player);
                score1.get(player).setItem(17,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT"));
                lobby.getSettingsAPI().setFriends(player, true);
                player.updateInventory();
            }
        } else if (clickEvent.getCurrentItem().getType().equals(Material.GOLD_INGOT)
                && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lCoins")) {
            if (scoreCoins.contains(player)) {
                scoreCoins.remove(player);
                score1.get(player).setItem(26,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT"));
                lobby.getSettingsAPI().setCoins(player, false);
                player.updateInventory();
            } else {
                scoreCoins.add(player);
                score1.get(player).setItem(26,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT"));
                lobby.getSettingsAPI().setCoins(player, true);
                player.updateInventory();
            }
        } else if (clickEvent.getCurrentItem().getType().equals(Material.REDSTONE)
                && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("§c§lRang")) {
            if (scoreRank.contains(player)) {
                scoreRank.remove(player);
                score1.get(player).setItem(35,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT"));
                lobby.getSettingsAPI().setRang(player, false);
                player.updateInventory();
            } else {
                scoreRank.add(player);
                score1.get(player).setItem(35,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT"));
                lobby.getSettingsAPI().setRang(player, true);
                player.updateInventory();
            }
        } else if (clickEvent.getCurrentItem().getType().equals(Material.ENDER_CHEST)
                && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("§2§lClan")) {
            if (scoreClan.contains(player)) {
                scoreClan.remove(player);
                score2.get(player).setItem(17,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT"));
                lobby.getSettingsAPI().setClan(player, false);
                player.updateInventory();
            } else {
                scoreClan.add(player);
                score2.get(player).setItem(17,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT"));
                lobby.getSettingsAPI().setClan(player, true);
                player.updateInventory();
            }
        } else if (clickEvent.getCurrentItem().getType().equals(Material.NETHER_STAR)
                && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("§f§lServer")) {
            if (scoreServer.contains(player)) {
                scoreServer.remove(player);
                score2.get(player).setItem(26,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT"));
                lobby.getSettingsAPI().setServer(player, false);
                player.updateInventory();
            } else {
                scoreServer.add(player);
                score2.get(player).setItem(26,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT"));
                lobby.getSettingsAPI().setServer(player, true);
                player.updateInventory();
            }
        } else if (clickEvent.getCurrentItem().getType().equals(Material.GOLD_PLATE)
                && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("§5§lJumpPad")) {
            if (jumpPads.contains(player)) {
                jumpPads.remove(player);
                invent3.get(player).setItem(17,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT"));
                lobby.getSettingsAPI().setPJUMP(player, false);

            } else {
                jumpPads.add(player);
                invent3.get(player).setItem(17,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT"));
                lobby.getSettingsAPI().setPJUMP(player, true);
            }
        } else if (clickEvent.getCurrentItem().getType().equals(Material.WATCH) && clickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Online-Zeit")) {
            if (time.contains(player)) {
                time.remove(player);
                score2.get(player).setItem(35, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT"));
                lobby.getSettingsAPI().setTime(player, false);
            } else {
                time.add(player);
                score2.get(player).setItem(35, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT"));
                lobby.getSettingsAPI().setTime(player, true);
            }
        }
    }

    private void setScore(Player player) {
        // Score 1
        ModuleManager lobby = this.coreSpigot.getModuleManager();
        Inventory inv1 = Bukkit.createInventory(null, 54, "§9§lScoreboard");

        for (int i = 8; i >= 0; i--) {
            if (design.containsKey(player)) {
                inv1.setItem(i,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, design.get(player), 1));
            } else {
                inv1.setItem(i, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, 0, 1));
            }
        }

        ItemStack playerStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemMeta playerMeta = playerStack.getItemMeta();
        playerMeta.setDisplayName("§9§lFreunde");
        playerStack.setItemMeta(playerMeta);
        SkullMeta playerSM = (SkullMeta) playerStack.getItemMeta();
        playerSM.setOwner(player.getName());
        playerStack.setItemMeta(playerSM);
        inv1.setItem(9, playerStack);

        if (scoreFriends.contains(player)) {
            inv1.setItem(17, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT"));
        } else {
            inv1.setItem(17, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT"));
        }

        inv1.setItem(18, lobby.getItemCreator().createItemWithMaterial(Material.GOLD_INGOT, 0, 1, "§6§lCoins"));
        if (scoreCoins.contains(player)) {
            inv1.setItem(26, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT"));
        } else {
            inv1.setItem(26, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT"));
        }

        inv1.setItem(27, lobby.getItemCreator().createItemWithMaterial(Material.REDSTONE, 0, 1, "§c§lRang"));
        if (scoreRank.contains(player)) {
            inv1.setItem(35, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT"));
        } else {
            inv1.setItem(35, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT"));
        }

        ItemStack ArrowR = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemMeta ArrowRM = ArrowR.getItemMeta();
        ArrowRM.setDisplayName("§aNächste Seite");
        ArrowR.setItemMeta(ArrowRM);
        SkullMeta SM = (SkullMeta) ArrowR.getItemMeta();
        SM.setOwner("MHF_ArrowRight");
        ArrowR.setItemMeta(SM);
        inv1.setItem(44, ArrowR);
        inv1.setItem(40, lobby.getItemCreator().createItemWithMaterial(Material.PAPER, 0, 1, "§6§lSeite §a§l1§6§l/§a§l2"));

        for (int a = 53; a >= 45; a--) {
            if (design.containsKey(player)) {
                inv1.setItem(a,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, design.get(player), 1));
            } else {
                inv1.setItem(a, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, 0, 1));
            }
        }

        score1.put(player, inv1);

        // Score2
        Inventory inv2 = Bukkit.createInventory(null, 54, "§9§lScoreboard");
        for (int i = 8; i >= 0; i--) {
            if (design.containsKey(player)) {
                inv2.setItem(i,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, design.get(player), 1));
            } else {
                inv2.setItem(i, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, 0, 1));
            }
        }

        inv2.setItem(9, lobby.getItemCreator().createItemWithMaterial(Material.ENDER_CHEST, 0, 1, "§2§lClan"));
        if (scoreClan.contains(player)) {
            inv2.setItem(17, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT"));
        } else {
            inv2.setItem(17, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT"));
        }

        inv2.setItem(18, lobby.getItemCreator().createItemWithMaterial(Material.NETHER_STAR, 0, 1, "§f§lServer"));
        if (scoreServer.contains(player)) {
            inv2.setItem(26, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT"));
        } else {
            inv2.setItem(26, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT"));
        }

        inv2.setItem(27, lobby.getItemCreator().createItemWithMaterial(Material.WATCH, 0, 1, "§6Online-Zeit"));
        if (time.contains(player)) {
            inv2.setItem(35, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 5, 1, "§aAKTIVIERT"));
        } else {
            inv2.setItem(35, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_CLAY, 14, 1, "§cDEAKTIVIERT"));
        }
        ItemStack ArrowL = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemMeta ArrowLM = ArrowL.getItemMeta();
        ArrowLM.setDisplayName("§aVorherige Seite");
        ArrowL.setItemMeta(ArrowLM);
        SkullMeta SLM = (SkullMeta) ArrowL.getItemMeta();
        SLM.setOwner("MHF_ArrowLeft");
        ArrowL.setItemMeta(SLM);
        inv2.setItem(36, ArrowL);
        inv2.setItem(40, lobby.getItemCreator().createItemWithMaterial(Material.PAPER, 0, 1, "§6§lSeite §a§l2§6§l/§a§l2"));

        for (int a = 53; a >= 45; a--) {
            if (design.containsKey(player)) {
                inv2.setItem(a,
                        lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, design.get(player), 1));
            } else {
                inv2.setItem(a, lobby.getItemCreator().createItemWithMaterial(Material.STAINED_GLASS_PANE, 0, 1));
            }
        }

        score2.put(player, inv2);

    }

    @EventHandler
    public void rideOnMe(PlayerInteractAtEntityEvent interactAtEntityEvent) {
        if (interactAtEntityEvent.getRightClicked() instanceof Player) {
            Player horse = (Player) interactAtEntityEvent.getRightClicked();
            Player rider = interactAtEntityEvent.getPlayer();
            ActionBarAPI.sendActionBar(horse, "§6§l" + rider.getName() + " §f§lsitzt nun auf dir!");
            if (rideState.contains(horse)) {
                horse.setPassenger(rider);
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
            int place = 0;

            @Override
            public void run() {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (all.getOpenInventory().getTitle().contains("Einstellungen") || all.getOpenInventory().getTitle().contains("Navigator")) {
                        int des = design.get(all) - 1;
                        if (des < 0) des = 15;
                        ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) des);
                        if (place == 8)
                            all.getOpenInventory().setItem(place - 1, new ItemStack(Material.STAINED_GLASS_PANE, 1, design.get(all)));
                        if (place == 0)
                            all.getOpenInventory().setItem(8, new ItemStack(Material.STAINED_GLASS_PANE, 1, design.get(all)));
                        all.getOpenInventory().setItem(place, glass);
                    }
                }
                if (place == 8) place = 0;
                place++;
            }
        }.runTaskTimer(coreSpigot, 0L, 20L));
    }
}