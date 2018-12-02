package main.de.mj.bb.core.managers;

import main.de.mj.bb.core.CoreSpigot;
import main.de.mj.bb.core.commands.NickCommand;
import main.de.mj.bb.core.events.NickEvent;
import main.de.mj.bb.core.events.UnnickEvent;
import main.de.mj.bb.core.mysql.NickAPI;
import main.de.mj.bb.core.utils.Nickname;
import main.de.mj.bb.core.utils.SkinChanger;
import main.de.mj.bb.core.utils.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class NickManager {

    private final CoreSpigot coreSpigot;

    private Map<Player, String> playerName = new HashMap<>();
    private Set<UUID> cooldown = new HashSet<>();
    private List<String> lines = null;
    private File file = new File(CoreSpigot.getInstance().getDataFolder(), "players.txt");

    private NickAPI nickAPI;
    private NickCommand nickCommand;

    public NickManager(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        nickAPI = new NickAPI(coreSpigot);
        nickCommand = new NickCommand(coreSpigot);
    }

    public boolean isDisguised(Player player) {
        if (!this.playerName.containsKey(player)) {
            return false;
        }
        return SkinChanger.isNicked(player);
    }

    public void disguise(final Player player) {
        if (player == null) {
            return;
        }
        if (this.cooldown.contains(player.getUniqueId())) {
            //Messages.error(player, "pleasewait", new Object[0]);
            return;
        }
        if (this.playerName.containsKey(player)) {
            undisguise(player);
        }
        //Messages.info(player, "loading", new Object[0]);
        String n = getRandomPlayer();
        NickEvent nickEvent = new NickEvent(player, n);
        Bukkit.getPluginManager().callEvent(nickEvent);
        if (nickEvent.isCancelled()) {
            return;
        }
        String name;
        name = nickEvent.getNickName();
        nickAPI.setPlayer(player, name);
        this.playerName.put(player, name);
        UUID id = UUIDFetcher.getUUID(name);
        final String nick = name;
        if (id != null) {
            this.cooldown.add(player.getUniqueId());
            Bukkit.getScheduler().runTaskLater(CoreSpigot.getInstance(), () -> cooldown.remove(player.getUniqueId()), 20 * 5);
            Bukkit.getScheduler().runTaskLater(CoreSpigot.getInstance(), () -> {
                try {
                    SkinChanger.nick(player, new Nickname(UUIDFetcher.getUUID(nick), ChatColor.getByChar("7"), nick));
                    //Messages.success(player, "disguised", new Object[]{nick});
                    nickAPI.setPlayer(player, nick);
                    new BukkitRunnable() {
                        int timer = 1;

                        @Override
                        public void run() {
                            if (timer == 0) {
                                coreSpigot.getModuleManager().getTabList().setTabList(player);
                                cancel();
                            } else timer--;
                        }
                    }.runTaskTimer(coreSpigot, 0L, 10L);
                } catch (Exception ex) {
                    //Messages.error(player, "error", new Object[0]);
                    undisguise(player);
                    ex.printStackTrace();
                }
            }, 10L);
        } else {
            undisguise(player, false, false);
            //Messages.error(player, "noplayer", new Object[]{name});
        }
    }

    public void undisguise(Player player) {
        undisguise(player, false, false);
    }


    public void undisguise(Player player, boolean msg, boolean disconnect) {
        if (player == null) {
            return;
        }
        if (!this.playerName.containsKey(player)) {
            return;
        }
        UnnickEvent unnickEvent = new UnnickEvent(player, this.playerName.get(player));
        Bukkit.getPluginManager().callEvent(unnickEvent);
        if (unnickEvent.isCancelled()) {
            return;
        }
        this.playerName.remove(player);
        if (SkinChanger.isNicked(player)) {
            SkinChanger.unNick(player);
        }
        nickAPI.deletePlayer(player);
        new BukkitRunnable() {
            int timer = 2;

            @Override
            public void run() {
                if (timer == 0) {
                    coreSpigot.getModuleManager().getTabList().setTabList(player);
                    cancel();
                } else timer--;
            }
        }.runTaskTimer(coreSpigot, 0L, 20L);
    }

    private int getRandomNumber(int min, int max) {
        return min + (int) (Math.random() * (max - min + 1));
    }

    private String getRandomPlayer() {
        try {
            this.lines = Files.readAllLines(Paths.get(this.file.getAbsolutePath()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (this.lines.isEmpty()) {
            return null;
        }
        int rand = getRandomNumber(0, this.lines.size() - 1);
        return this.lines.get(rand);
    }

    public CoreSpigot getCoreSpigot() {
        return this.coreSpigot;
    }

    public Map<Player, String> getPlayerName() {
        return this.playerName;
    }

    public void setPlayerName(Map<Player, String> playerName) {
        this.playerName = playerName;
    }

    public Set<UUID> getCooldown() {
        return this.cooldown;
    }

    public void setCooldown(Set<UUID> cooldown) {
        this.cooldown = cooldown;
    }

    public List<String> getLines() {
        return this.lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public NickAPI getNickAPI() {
        return this.nickAPI;
    }

    public void setNickAPI(NickAPI nickAPI) {
        this.nickAPI = nickAPI;
    }

    public NickCommand getNickCommand() {
        return this.nickCommand;
    }

    public void setNickCommand(NickCommand nickCommand) {
        this.nickCommand = nickCommand;
    }


}
